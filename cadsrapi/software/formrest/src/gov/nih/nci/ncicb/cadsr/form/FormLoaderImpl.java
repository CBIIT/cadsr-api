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
	
	@Override
	public String loadForms(String xmlForm) {
		AuthorizationPolicy policy = (AuthorizationPolicy)mc.get(AuthorizationPolicy.class.getName());
		if( policy != null ) {
        	String username = policy.getUserName();
        	String password = policy.getPassword(); 
        }
		
		String xmlResult = "";
		String path = "/local/content/formloader/data/";
		String ext = "xml";
		File dir = new File(path);
		String name = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), ext);
		File xmlFile = new File(dir, name);
		
		//File xmlFile = new File(path + "test.xml");
		try {
			FileWriter fw = new FileWriter(xmlFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(xmlForm);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		@SuppressWarnings("resource")
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/applicationContext-service-db.xml");
		
		XmlValidationService xmlValidator = 
				(XmlValidationServiceImpl)applicationContext.getBean("xmlValidationService");
		ContentValidationService contentValidator = 
				(ContentValidationServiceImpl)applicationContext.getBean("contentValidationService");
		LoadingService loadService =  (LoadingService)applicationContext.getBean("loadService");
		
		try {
			FormCollection aColl = new FormCollection();
			aColl.setName("TestCollection");
			//aColl.setForms(forms);
			aColl.setCreatedBy("yangs");
			aColl.setXmlFileName(name);
			aColl.setXmlPathOnServer(path);
			
			//Step 1: call xml validation service
			aColl = xmlValidator.validateXml(aColl);
			List<FormDescriptor> forms = aColl.getForms();
			
			//Step 2: call content validation service
			forms.get(0).setSelected(true);
			aColl= contentValidator.validateXmlContent(aColl);
		
			//Step 3: call load service
			aColl = loadService.loadForms(aColl);
			
			//Step 4: check status
			String statusString = StatusFormatter.getStatusInXml(aColl);
			
			//Step 5: write status xml to file
			//StatusFormatter.writeStatusToXml(statusString, "LoadForm-Status.xml");
			
			

			
			//This is just to show what messages get generated during validation and load process
			//Client app will NOT need to do this
/*			forms = aColl.getForms();
			for (FormDescriptor form : forms) {
				List<String> msgs = form.getMessages();
				for (String msg : msgs) 
					System.out.println("=====" + msg);
				
				List<ModuleDescriptor> modules = form.getModules();
				for (ModuleDescriptor module : modules) {
					List<QuestionDescriptor> questions = module.getQuestions();
					for (QuestionDescriptor question : questions) {
						List<String> qmsgs = question.getMessages();
						for (String m : qmsgs)
							System.out.println("=====" + m);
					}
				}
			}
*/
			
			forms = aColl.getForms();
			StringBuffer buf = new StringBuffer();
			for (FormDescriptor form : forms) {
				FormStatus formStatus = form.getStructuredStatus();
				
				//Generate xml format status
				try {
					JAXBContext jaxbContext = JAXBContext.newInstance(FormStatus.class);
					Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

					final StringWriter stringWriter = new StringWriter();
					
					jaxbMarshaller.marshal(formStatus, stringWriter);
					buf.append(stringWriter.toString());
				} catch (JAXBException e) {
					e.printStackTrace();
				}			
				xmlResult = buf.toString();
			}
			
		} catch (FormLoaderServiceException fle) {
			System.out.println("=====" + fle.getMessage());
			xmlResult = "<?xml version='1.0' encoding='UTF-8' standalone='yes'?><formStatus xmlns:ns2='gov.nih.nci.cadsr.formloader.domain.Moduletatus' xmlns:ns3='gov.nih.nci.cadsr.formloader.domain.FormStatus'><message><![CDATA[" + fle.getMessage() + "]]></message></formStatus>";
		} catch (Exception fle) {
			System.out.println("=====" + fle.getMessage());
			xmlResult = "<?xml version='1.0' encoding='UTF-8' standalone='yes'?><formStatus xmlns:ns2='gov.nih.nci.cadsr.formloader.domain.Moduletatus' xmlns:ns3='gov.nih.nci.cadsr.formloader.domain.FormStatus'><message><![CDATA[" + fle.getMessage() + "]]></message></formStatus>";
		}
		
		return xmlResult;
	}
}
