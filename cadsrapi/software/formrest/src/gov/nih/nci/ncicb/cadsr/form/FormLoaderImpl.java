package gov.nih.nci.ncicb.cadsr.form;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.ws.rs.core.Context;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.domain.FormStatus;
import gov.nih.nci.cadsr.formloader.domain.ModuleDescriptor;
import gov.nih.nci.cadsr.formloader.domain.QuestionDescriptor;
import gov.nih.nci.cadsr.formloader.service.ContentValidationService;
import gov.nih.nci.cadsr.formloader.service.LoadingService;
import gov.nih.nci.cadsr.formloader.service.XmlValidationService;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderServiceException;
import gov.nih.nci.cadsr.formloader.service.common.StatusFormatter;
import gov.nih.nci.cadsr.formloader.service.impl.ContentValidationServiceImpl;
import gov.nih.nci.cadsr.formloader.service.impl.XmlValidationServiceImpl;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.UserManagerDAO;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FormLoaderImpl implements FormLoader {
	@Context MessageContext mc; 
	private String username;
	private String password;
	private String xmlResult = "";
	private String path = "/local/content/formloader/data/";
	private String ext = "xml";
	private String fileName = "";	
	private XmlValidationService xmlValidator;
	private ContentValidationService contentValidator;
	private LoadingService loadService;
	private FormCollection aColl;
	
	@Override
	public String loadForms(String xmlForm) {
		retrieveUserNameAndPassword();
		
		try {
			writeXmlFormToFolder(xmlForm);
			
			setupFormCollectionAndService();
			
			validateFormsAndReturnStatus();
		} catch (Exception fle) {
			System.out.println("=====" + fle.getMessage());
			xmlResult = "<?xml version='1.0' encoding='UTF-8' standalone='yes'?><formStatus xmlns:ns2='gov.nih.nci.cadsr.formloader.domain.Moduletatus' xmlns:ns3='gov.nih.nci.cadsr.formloader.domain.FormStatus'><message><![CDATA[" + fle.getMessage() + "]]></message></formStatus>";
		}
		
		System.out.println(xmlResult);
		return xmlResult;
	}

	private void validateFormsAndReturnStatus()
			throws FormLoaderServiceException {
		//Step 1: call xml validation service
		aColl = xmlValidator.validateXml(aColl);
		List<FormDescriptor> forms = aColl.getForms();
		
		//Step 2: call content validation service
		forms.get(0).setSelected(true);
		aColl= contentValidator.validateXmlContent(aColl);

		//Step 3: call load service
		//aColl.setSelectAllForms(true);
		for(FormDescriptor fd: forms)
		{
			fd.setSelected(true);
		}
		aColl = loadService.loadForms(aColl);
		
		//Step 4: check status
		xmlResult = StatusFormatter.getStatusInXml(aColl);
	}

	private void setupFormCollectionAndService() {
		@SuppressWarnings("resource")
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/applicationContext-service-db.xml");
		xmlValidator = (XmlValidationServiceImpl)applicationContext.getBean("xmlValidationService");
		contentValidator = (ContentValidationServiceImpl)applicationContext.getBean("contentValidationService");
		loadService =  (LoadingService)applicationContext.getBean("loadService");
		
		aColl = new FormCollection();
		aColl.setCreatedBy(username);
		aColl.setXmlFileName(fileName);
		aColl.setXmlPathOnServer(path);
		
		System.out.println("=====" + path);
		System.out.println("=====" + fileName);
		
	}

	private void writeXmlFormToFolder(String xmlForm) throws Exception{
		fileName = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), ext);
		File dir = new File(path);
		File xmlFile = new File(dir, fileName);

		FileWriter fw = new FileWriter(xmlFile.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(xmlForm);
		bw.close();
	}

	private void retrieveUserNameAndPassword() {
		AuthorizationPolicy policy = (AuthorizationPolicy)mc.get(AuthorizationPolicy.class.getName());
		if( policy != null ) {
        	username = policy.getUserName();
        	password = policy.getPassword(); 
        }
	}
}
