package gov.nih.nci.ncicb.cadsr.cdeservice;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import gov.nih.cadsr.transform.FilesTransformation;
import gov.nih.nci.ncicb.cadsr.common.cdebrowser.DESearchQueryBuilder;
import gov.nih.nci.ncicb.cadsr.common.cdebrowser.DataElementSearchBean;
import gov.nih.nci.ncicb.cadsr.common.util.DBUtil;
import gov.nih.nci.ncicb.cadsr.common.util.StringUtils;
import gov.nih.nci.ncicb.cadsr.common.SearchQueryBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import gov.nih.nci.ncicb.cadsr.cdeservice.CDESearchBo;
import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

@Component
@Path("/services")
public class CDERestService {
	public static String DEFAULT_SIZE = "10";
	public static final String GOV_NIH_NCI_TRANSFORM_PROPERTIES = "gov.nih.nci.transform.properties";
	public static final String DEFAULT_SIZE_KEY = "default.cde.size";
	
	@Autowired
	CDESearchBo cdeSearchBo;
	private @Autowired ApplicationContext applicationContext;

	@GET
	@Path("/getDataElement")
	//@Produces ({"application/xml","application/octet-stream","application/json"})
	public Response getDataElements(@Context HttpServletRequest httpRequest) {

		DataElementSearchBean desb = null;
		DESearchQueryBuilder queryBuilder = null;
//		Enumeration inputParameters = httpRequest.getParameterNames();
//		while(inputParameters.hasMoreElements())
//		{
//			if(!contains((String) inputParameters.nextElement()))
//				return Response.status(400).build();
//		}
		DBUtil dbUtil = new DBUtil();
		try
		{
			dbUtil.getConnectionFromContainer();
			desb = new DataElementSearchBean(httpRequest);
			desb.initSearchPreferences(dbUtil);
			// Need to the session Preference which is per session
		//setValuesFromOldSearchBean(desb);
			desb.setLOVLists(dbUtil);
			dbUtil.returnConnection();
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return Response.status(400).build();
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).build();
		}
        if(httpRequest.getParameter("context") != null)        
        	queryBuilder = new SearchQueryBuilder( httpRequest, "CONTEXT", httpRequest.getParameter("contextIdSeq") , httpRequest.getParameter("contextIdSeq"), desb);
        else if(httpRequest.getParameter("classification") != null)
        	queryBuilder = new SearchQueryBuilder( httpRequest, "CLASSIFICATION", httpRequest.getParameter("classificationIdSeq") , httpRequest.getParameter("classificationIdSeq"), desb);
        else        	
			queryBuilder = new SearchQueryBuilder( httpRequest, null, null , null, desb);
        
        int start = 1; 
        int size = setupSize(httpRequest.getParameter("size"));
        
        if(httpRequest.getParameter("start") != null)
        	start = Integer.parseInt(httpRequest.getParameter("start"));
//        if(httpRequest.getParameter("size") != null)
//        	size = Integer.parseInt(httpRequest.getParameter("size"));
        
        int end = start + size;
        	String query = queryBuilder.getSQLWithoutOrderBy();        	
			StringBuffer queryStmt = new StringBuffer();
			String orderBy = queryBuilder.getOrderBy();
			queryStmt.append("select * from ("+ query.replaceFirst("SELECT distinct", "SELECT distinct rownum rn,") + ") where rn >= " + start  + " and rn <= " + end);
			System.out.println("- Query stmt is " + queryStmt);

			String resultXML = cdeSearchBo.getDataElements(queryStmt+ " order by  " + orderBy,httpRequest);
			if(resultXML == null)
				 return Response.status(404).build();
			
			String format = (String) httpRequest.getParameter("format");
			if ( null != format && format.equals("XML")) {
				return Response.ok(resultXML).header("Content-Disposition", "application/xml").build();
			}
			else if (null != format &&  format.equals("CSV")) {
				String csvFile = FilesTransformation.transformCdeToCSV(resultXML);
				return Response.ok(csvFile).header("Content-Disposition", "attachment; filename=download.csv").build();
			}
			else {
				XMLSerializer xmlSerializer = new XMLSerializer();                 
				JSON json = xmlSerializer.read( resultXML );  
		        return Response.ok(json.toString(2)).type("application/json").build();
			}			
		//return Response.status(200).entity(json.toString(2)).type("text/XML").build();

	}
	
	public enum possibleInputParameters {
		 pablicID, name, classification, context; 
		}

	public static boolean contains(String input) {

	    for (possibleInputParameters c : possibleInputParameters.values()) {
	        if (c.name().equals(input)) {
	            return true;
	        }
	    }

	    return false;
	}
	
	private Integer setupSize(String size) {
		if ( !StringUtils.doesValueExist(size) ) {
			size = loadSizeProperty();
		}
		return Integer.valueOf(size);
	}
	
	private String loadSizeProperty() {
		  String propertiesFileName = System.getProperty(GOV_NIH_NCI_TRANSFORM_PROPERTIES);
		
		  //Load the the application properties and set them as system properties
		  Properties transformProperties = new Properties();
		   
		  FileInputStream in;
		  String size = "";
		  try {
			in = new FileInputStream(propertiesFileName);
		   
			  if (propertiesFileName == null || in == null ) {
				  return DEFAULT_SIZE;	
			  }
			transformProperties.load(in);
		    size = transformProperties.getProperty(DEFAULT_SIZE_KEY);
		    in.close();	
			} catch (IOException e) {
				  return DEFAULT_SIZE;	
			} catch (Exception e) {
				  return DEFAULT_SIZE;	
			}		  
		  
		    return size;
		}

}