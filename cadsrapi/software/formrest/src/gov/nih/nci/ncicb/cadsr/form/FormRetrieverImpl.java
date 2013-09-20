package gov.nih.nci.ncicb.cadsr.form;

import gov.nih.cadsr.transform.FilesTransformation;
import gov.nih.nci.cadsr.formloader.service.common.FormXMLConverter;
import gov.nih.nci.ncicb.cadsr.common.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCClassificationSchemeDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCContextDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCFormDAOV2;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCProtocolDAOV2;
import gov.nih.nci.ncicb.cadsr.common.resource.Classification;
import gov.nih.nci.ncicb.cadsr.common.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.common.resource.Context;
import gov.nih.nci.ncicb.cadsr.common.resource.FormV2;
import gov.nih.nci.ncicb.cadsr.common.util.StringUtils;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.URLConnectionInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class FormRetrieverImpl implements FormRetriever{
	
	public static String DEFAULT_SIZE = "1";
	
	public FormRetrieverImpl() {}
	
	@Override
	public String getForm(String formPublicId) {
		return formPublicId;
	}
	
	@Override
	public Response getForm(	String formPublicId, 
								String formLongName, 
								String context,
								String classification,
								String protocol,
								String createdBy,
								String workFlowStatus,
								String registrationStatus,
								String start,
								String size,
								String total,
								String format ) {
		String contextIdSeq = "";
		String version = "";
		String classificationIdSeq = "";
		String protocolIdSeq = "";
		Collection formCollection = null;
		
		boolean skip=false;
		if( skip ) {
			
			XMLSerializer xmlSerializer = new XMLSerializer();                 
			
			File f = new File("/local/content/transform/data/","CdBefYIp.txt");
			JSON json = xmlSerializer.readFromFile( f );  
			
			String jsonString = json.toString(2);
			
			File file = new File("/local/content/transform/data/","test.json");
			 
			FileWriter fw;
			try {
				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}
	 
				fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(jsonString);
				bw.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	        ResponseBuilder response = Response.ok(jsonString);
	        System.out.println(jsonString);
	        //response.header("Content-Disposition", "attachment; filename=\"download.json\"");
			response = Response.ok(jsonString).header("Content-Disposition", "text/json");
	        return response.build();
		}
		
		@SuppressWarnings("resource")
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/applicationContext-service-db.xml");
		
		JDBCFormDAOV2 formDAO = (JDBCFormDAOV2)applicationContext.getBean("formV2Dao");
		
		if ( !StringUtils.doesValueExist(start) ) {
			start="1";
		}
		
		if ( !StringUtils.doesValueExist(size) ) {
			size = DEFAULT_SIZE;
		}
		
		if ( StringUtils.doesValueExist(context) ) {
			JDBCContextDAOV2 contextDAO = (JDBCContextDAOV2)applicationContext.getBean("contextV2Dao");
			StringTokenizer contexttokenizer = new StringTokenizer(context, ",");
			 
			StringBuffer contextBuffer = new StringBuffer();
			while (contexttokenizer.hasMoreElements()) {
				if( contextBuffer.length() > 0 )
					contextBuffer.append(",");
				
				String idSeq = ((Context)contextDAO.getContextByName((String)contexttokenizer.nextElement())).getConteIdseq();
				
				if ( StringUtils.doesValueExist(idSeq) )
					contextBuffer.append("'").append(idSeq).append("'");
			}

			//contextIdSeq = ((Context)contextDAO.getContextByName(context)).getConteIdseq();
			contextIdSeq = contextBuffer.toString();
			version = "latestVersion";
			
			if ( !StringUtils.doesValueExist(workFlowStatus) )
				workFlowStatus = "RELEASED";
		}
		
		if ( StringUtils.doesValueExist(classification) ) {
			JDBCClassificationSchemeDAOV2 classificationDAO = (JDBCClassificationSchemeDAOV2)applicationContext.getBean("classificationV2Dao");
			StringTokenizer classtokenizer = new StringTokenizer(classification, ",");
			 
			StringBuffer classBuffer = new StringBuffer();
			while (classtokenizer.hasMoreElements()) {
				if( classBuffer.length() > 0 )
					classBuffer.append(",");
				
				String idSeq = ((Classification)classificationDAO.getClassificationByName((String)classtokenizer.nextElement())).getCsIdseq();
				
				if ( StringUtils.doesValueExist(idSeq) )
					classBuffer.append("'").append(idSeq).append("'");
			}
			classificationIdSeq = classBuffer.toString();
		}
		
		if ( StringUtils.doesValueExist(protocol) ) {
			JDBCProtocolDAOV2 protocolDAO = (JDBCProtocolDAOV2)applicationContext.getBean("protocolV2Dao");
			StringTokenizer protocoltokenizer = new StringTokenizer(protocol, ",");
			 
			StringBuffer protocolBuffer = new StringBuffer();
			while (protocoltokenizer.hasMoreElements()) {
				if( protocolBuffer.length() > 0 )
					protocolBuffer.append(",");
				
				String idSeq = ((Protocol)protocolDAO.getProtocolByName((String)protocoltokenizer.nextElement())).getProtoIdseq();
				
				if ( StringUtils.doesValueExist(idSeq) )
					protocolBuffer.append("'").append(idSeq).append("'");
			}
			//protocolIdSeq = ((Protocol)protocolDAO.getProtocolByName(protocol)).getProtoIdseq();
			protocolIdSeq = protocolBuffer.toString();
		}
		
		if ( !StringUtils.doesValueExist(total) ) {
			int count = 0;

			if ( StringUtils.doesValueExist(classification) ) {
				count = formDAO.getFormClassificationCount(classificationIdSeq);
			}
			else
			{
				count = formDAO.getFormCount(formLongName, protocolIdSeq, contextIdSeq, workFlowStatus, "", "", classificationIdSeq, "", formPublicId, version, "", "", createdBy);
			}
			total = String.valueOf(count);
		}
		
		if ( StringUtils.doesValueExist(classification) ) {
			formCollection = formDAO.getAllFormsForClassification(classificationIdSeq, start, size);
		}
		else {
			 formCollection = formDAO.getAllForms(formLongName, protocolIdSeq, contextIdSeq, workFlowStatus, "", "", classificationIdSeq, "", formPublicId, version, "", "", createdBy, start, size);
		}
		/* Retrieve One Form
		FormTransferObject formObject = ((FormTransferObject) ((ArrayList)formCollection).get(0));
		
		FormV2 form = formDAO.getFormDetailsV2(formObject.getFormIdseq());
		
		String xmlFile = "";
		try {
			xmlFile = FormXMLConverter.instance().convertFormToXML(form);
		}
		catch (Exception e) 
		{
			System.out.println(e);
		}
		
		End Retrieve One Form */
		int startPage = Integer.parseInt(start);
		int isize = Integer.parseInt(size);
		String nextPage= Integer.toString(startPage+1);
		String prevPage= Integer.toString(startPage-1);
		String nextParams = constructParams(formPublicId, formLongName, context, classification, protocol, createdBy, workFlowStatus, registrationStatus, nextPage, size, total, format);
		String prevParams = constructParams(formPublicId, formLongName, context, classification, protocol, createdBy, workFlowStatus, registrationStatus, prevPage, size, total, format);
		String currParams = constructParams(formPublicId, formLongName, context, classification, protocol, createdBy, workFlowStatus, registrationStatus, start, size, total, format);
		String url = AuthenticationHandler.URL.substring(0,AuthenticationHandler.URL.indexOf("/formrest"));
		
		StringBuffer xmlFileBuffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<formCollection>\n");
		if( Integer.valueOf(total) > startPage*isize) {
			xmlFileBuffer.append("<link ref='next' type='application/xml' href=").append("'").append(url).append("/formrest/services/formRetrieve?").append(nextParams).append("'/>\n");
		}
		if( startPage-1 > 0 ) {
			xmlFileBuffer.append("<link ref='prev' type='application/xml' href=").append("'http://localhost:8080").append("/formrest/services/formRetrieve?").append(prevParams).append("'/>\n");
		}
		xmlFileBuffer.append("<link ref='self' type='application/xml' href=").append("'http://localhost:8080").append("/formrest/services/formRetrieve?").append(currParams).append("'/>\n");
		//StringBuffer xmlFileBuffer = new StringBuffer("");
		for(Object formObject: formCollection) {
			FormV2 form = formDAO.getFormDetailsV2(((FormTransferObject)formObject).getFormIdseq());
			
			String xmlLocalFile = "";
			try {
				xmlLocalFile = FormXMLConverter.instance().convertFormToXML(form);
			}
			catch (Exception e) 
			{
				System.out.println(e);
			}
			
			xmlLocalFile = xmlLocalFile.substring(xmlLocalFile.indexOf('\n')+1);  // remove the first line (xml version)
			
			xmlFileBuffer.append(xmlLocalFile);
		}
		xmlFileBuffer.append("</formCollection>\n");
		
		if ( format.equals("XML")) {
			return Response.ok(xmlFileBuffer.toString()).header("Content-Disposition", "application/xml").build();
		}
		else if ( format.equals("CSV")) {
			String csvFile = FilesTransformation.transformFormToCSV(xmlFileBuffer.toString());
			return Response.ok(csvFile).header("Content-Disposition", "attachment; filename=download.csv").build();
			//return Response.ok(csvFile).header("Content-Disposition", "text/csv").build();
			
/*			File f = new File("/local/content/transform/data/","CdBefYIp.txt");

	        ResponseBuilder response = Response.ok((Object) f);
	        response.type("application/csv");
	        response.header("Content-Disposition", "attachment; filename=\"download.csv\"");
	        return response.build();		*/
		}
		else {
			XMLSerializer xmlSerializer = new XMLSerializer();                 
			JSON json = xmlSerializer.read( xmlFileBuffer.toString() );  
			//return Response.ok(json.toString(2)).header("Content-Disposition", "application/json").build();
			
			ResponseBuilder response = Response.ok(json.toString(2));
	        response.header("Content-Disposition", "attachment; filename=\"download.json\"");
			//response = Response.ok(json.toString(2)).header("Content-Disposition", "application/json");

	        return response.build();
		}
		
		//return xmlFileBuffer.toString();

	}
	
	private String constructParams(String formPublicId, 
								String formLongName, 
								String context,
								String classification,
								String protocol,
								String createdBy,
								String workFlowStatus,
								String registrationStatus,
								String start,
								String size,
								String total,
								String format) {
		StringBuffer paramBuffer = new StringBuffer("");
		
		if ( StringUtils.doesValueExist(formPublicId) ) {
			paramBuffer.append("formPublicId=").append(formPublicId);
		}
		
		if ( StringUtils.doesValueExist(formLongName) ) {
			if ( StringUtils.doesValueExist(paramBuffer.toString()) ) {
				paramBuffer.append("&amp;");
			}
			paramBuffer.append("formLongName=").append(formLongName);
		}
		
		if ( StringUtils.doesValueExist(context) ) {
			if ( StringUtils.doesValueExist(paramBuffer.toString()) ) {
				paramBuffer.append("&amp;");
			}
			paramBuffer.append("context=").append(context);
		}
		
		if ( StringUtils.doesValueExist(classification) ) {
			if ( StringUtils.doesValueExist(paramBuffer.toString()) ) {
				paramBuffer.append("&amp;");
			}
			paramBuffer.append("classification=").append(classification);
		}
		
		if ( StringUtils.doesValueExist(protocol) ) {
			if ( StringUtils.doesValueExist(paramBuffer.toString()) ) {
				paramBuffer.append("&amp;");
			}
			paramBuffer.append("protocol=").append(protocol);
		}
		
		if ( StringUtils.doesValueExist(createdBy) ) {
			if ( StringUtils.doesValueExist(paramBuffer.toString()) ) {
				paramBuffer.append("&amp;");
			}
			paramBuffer.append("createdBy=").append(createdBy);
		}
		
		if ( StringUtils.doesValueExist(workFlowStatus) ) {
			if ( StringUtils.doesValueExist(paramBuffer.toString()) ) {
				paramBuffer.append("&amp;");
			}
			paramBuffer.append("workFlowStatus=").append(workFlowStatus);
		}
		
		if ( StringUtils.doesValueExist(registrationStatus) ) {
			if ( StringUtils.doesValueExist(paramBuffer.toString()) ) {
				paramBuffer.append("&amp;");
			}
			paramBuffer.append("registrationStatus=").append(registrationStatus);
		}
		
		if ( StringUtils.doesValueExist(start) ) {
			if ( StringUtils.doesValueExist(paramBuffer.toString()) ) {
				paramBuffer.append("&amp;");
			}
			paramBuffer.append("start=").append(start);
		}
		
		if ( StringUtils.doesValueExist(size) ) {
			if ( StringUtils.doesValueExist(paramBuffer.toString()) ) {
				paramBuffer.append("&amp;");
			}
			paramBuffer.append("size=").append(size);
		}
		
		if ( StringUtils.doesValueExist(total) ) {
			if ( StringUtils.doesValueExist(paramBuffer.toString()) ) {
				paramBuffer.append("&amp;");
			}
			paramBuffer.append("total=").append(total);
		}
		
		if ( StringUtils.doesValueExist(format) ) {
			if ( StringUtils.doesValueExist(paramBuffer.toString()) ) {
				paramBuffer.append("&amp;");
			}
			paramBuffer.append("format=").append(format);
		}		
		
		return paramBuffer.toString();
	}

}