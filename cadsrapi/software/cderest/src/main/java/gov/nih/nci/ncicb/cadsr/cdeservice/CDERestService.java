package gov.nih.nci.ncicb.cadsr.cdeservice;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import javax.ws.rs.core.UriInfo;

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
	
	@Context
	private UriInfo context;
	
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
        {
        	String contextIdSeq = httpRequest.getParameter("contextIdSeq");
        	if( contextIdSeq == null || contextIdSeq.equals("")) 
        	{
        		StringBuffer xmlFileBuffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Message>No Records Found</Message>\n");
				return Response.ok(xmlFileBuffer.toString()).header("Content-Disposition", "application/xml").build();
        	}
        	queryBuilder = new SearchQueryBuilder( httpRequest, "CONTEXT", httpRequest.getParameter("contextIdSeq") , httpRequest.getParameter("contextIdSeq"), desb);
        }
        else if(httpRequest.getParameter("classification") != null)
        {
        	String classificationIdSeq = httpRequest.getParameter("classificationIdSeq");
	    	if( classificationIdSeq == null || classificationIdSeq.equals("")) 
	    	{
	    		StringBuffer xmlFileBuffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Message>No Records Found</Message>\n");
				return Response.ok(xmlFileBuffer.toString()).header("Content-Disposition", "application/xml").build();
	    	}
        	queryBuilder = new SearchQueryBuilder( httpRequest, "CLASSIFICATION", httpRequest.getParameter("classificationIdSeq") , httpRequest.getParameter("classificationIdSeq"), desb);
        }
        else        	
			queryBuilder = new SearchQueryBuilder( httpRequest, null, null , null, desb);
        
        int totalCount = 0;
        if(httpRequest.getParameter("total") != null) {
        	totalCount = Integer.valueOf(httpRequest.getParameter("total"));
        }
        else
        {
	        String sqlCountQuery = replaceSelectSqlWithCount(queryBuilder.getSQLWithoutOrderBy());
	        totalCount = getTotalCount(sqlCountQuery);
        }
        
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
			queryStmt.append("select * from ("+ query.replaceFirst("SELECT distinct", "SELECT distinct rownum rn,") + ") where rn >= " + start  + " and rn < " + end);
			System.out.println("- Query stmt is " + queryStmt);
			
			String nextPage= Integer.toString(start+size);
			String prevPage= Integer.toString(start-size);
			String nextParams = constructParams(httpRequest.getParameter("publicId"), httpRequest.getParameter("name"), httpRequest.getParameter("context"), httpRequest.getParameter("classification"), httpRequest.getParameter("classificationItem"), httpRequest.getParameter("workflowStatus"), httpRequest.getParameter("registrationStatus"), nextPage, httpRequest.getParameter("size"), String.valueOf(totalCount), httpRequest.getParameter("format"));
			String prevParams = constructParams(httpRequest.getParameter("publicId"), httpRequest.getParameter("name"), httpRequest.getParameter("context"), httpRequest.getParameter("classification"), httpRequest.getParameter("classificationItem"), httpRequest.getParameter("workflowStatus"), httpRequest.getParameter("registrationStatus"), prevPage, httpRequest.getParameter("size"), String.valueOf(totalCount), httpRequest.getParameter("format"));
			String currParams = constructParams(httpRequest.getParameter("publicId"), httpRequest.getParameter("name"), httpRequest.getParameter("context"), httpRequest.getParameter("classification"), httpRequest.getParameter("classificationItem"), httpRequest.getParameter("workflowStatus"), httpRequest.getParameter("registrationStatus"), String.valueOf(start), httpRequest.getParameter("size"), String.valueOf(totalCount), httpRequest.getParameter("format"));
			String url = context.getAbsolutePath().toASCIIString().substring(0,context.getAbsolutePath().toASCIIString().indexOf("/cderest"));
			
			StringBuffer xmlFileBuffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<DataElementsList>\n");
			if( Integer.valueOf(totalCount) >= start+size) {
				xmlFileBuffer.append("<link ref='next' type='application/xml' href=").append("'").append(url).append("/cderest/rest/services/getDataElement?").append(nextParams).append("'/>\n");
			}
			if( start-size > 0 ) {
				xmlFileBuffer.append("<link ref='prev' type='application/xml' href=").append("'").append(url).append("/cderest/rest/services/getDataElement?").append(prevParams).append("'/>\n");
			}
			xmlFileBuffer.append("<link ref='self' type='application/xml' href=").append("'").append(url).append("/cderest/rest/services/getDataElement?").append(currParams).append("'/>\n");


			String resultXML = cdeSearchBo.getDataElements(queryStmt+ " order by  " + orderBy,httpRequest);
			if(resultXML == null)
			{
				 //return Response.status(404).build();
				StringBuffer xmlNoFileBuffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Message>No Records Found</Message>\n");
				return Response.ok(xmlNoFileBuffer.toString()).header("Content-Disposition", "application/xml").build();
			}
			
			String format = (String) httpRequest.getParameter("format");
			if ( null != format && format.toUpperCase().equals("XML")) {
				int column = resultXML.indexOf("<DataElement ");
				if ( column > 0) {
					resultXML = xmlFileBuffer.append(resultXML.substring(column)).toString();
				}
				return Response.ok(resultXML).header("Content-Disposition", "application/xml").build();
			}
			else if (null != format &&  format.toUpperCase().equals("CSV")) {
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
	
	private String replaceSelectSqlWithCount(String sqlQuery) {
		int column = sqlQuery.indexOf("from");
		sqlQuery = "SELECT count(*) TOTAL_COUNT " + sqlQuery.substring(column);
		        
		return sqlQuery;
	}
	
	private int getTotalCount(String sqlQuery)
	{
		int total = 0;
		
		Connection conn = null;
		ResultSet deRows = null;
		PreparedStatement ps = null;
		DBUtil dbUtil = new DBUtil();
		try {
			dbUtil.getConnectionFromContainer();   
			conn = dbUtil.getConnection();
			ps = conn.prepareStatement(sqlQuery);
			deRows = ps.executeQuery();
			deRows.next();
			total = deRows.getInt("TOTAL_COUNT");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
				e.printStackTrace();
			}
		catch (Exception e) {
			e.printStackTrace();
		}finally 
		{
			try 
			 {
				if(ps!=null)
					ps.close();
		
				if(deRows!=null)
					deRows.close();
				if (conn != null)
				{
					conn = null;
					dbUtil.returnConnection();
				}	
	  		 }				
			 catch (Exception ex) 
			 {
				System.out.println("Exception Caught in getTotalCount() during cleaning up :" + ex.getMessage());
			 }
		}
		
		return total;
	}
	
	private String constructParams(String publicId, 
			String name, 
			String context,
			String classification,
			String workflowStatus,
			String registrationStatus,
			String classificationItem,
			String start,
			String size,
			String total,
			String format) {
		StringBuffer paramBuffer = new StringBuffer("");
		
		if ( StringUtils.doesValueExist(publicId) ) {
			paramBuffer.append("publicId=").append(publicId);
		}
		
		if ( StringUtils.doesValueExist(name) ) {
			if ( StringUtils.doesValueExist(paramBuffer.toString()) ) {
				paramBuffer.append("&amp;");
			}
			paramBuffer.append("name=").append(name);
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
		
		if ( StringUtils.doesValueExist(workflowStatus) ) {
			if ( StringUtils.doesValueExist(paramBuffer.toString()) ) {
				paramBuffer.append("&amp;");
			}
			paramBuffer.append("workflowStatus=").append(workflowStatus);
		}
		
		if ( StringUtils.doesValueExist(registrationStatus) ) {
			if ( StringUtils.doesValueExist(paramBuffer.toString()) ) {
				paramBuffer.append("&amp;");
			}
			paramBuffer.append("registrationStatus=").append(registrationStatus);
		}
		
		if ( StringUtils.doesValueExist(classificationItem) ) {
			if ( StringUtils.doesValueExist(paramBuffer.toString()) ) {
				paramBuffer.append("&amp;");
			}
			paramBuffer.append("classificationItem=").append(classificationItem);
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