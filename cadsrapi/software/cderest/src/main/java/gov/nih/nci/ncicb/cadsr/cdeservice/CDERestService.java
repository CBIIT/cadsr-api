package gov.nih.nci.ncicb.cadsr.cdeservice;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;

import gov.nih.cadsr.transform.FilesTransformation;
import gov.nih.nci.ncicb.cadsr.common.cdebrowser.DESearchQueryBuilder;
import gov.nih.nci.ncicb.cadsr.common.cdebrowser.DataElementSearchBean;
import gov.nih.nci.ncicb.cadsr.common.util.DBUtil;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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

	@Autowired
	CDESearchBo cdeSearchBo;
	private @Autowired ApplicationContext applicationContext;

	@GET
	@Path("/getDataElement")
	public Response getDataElements(@Context HttpServletRequest httpRequest) {

		DataElementSearchBean desb = null;
		DESearchQueryBuilder queryBuilder = null;
		Enumeration inputParameters = httpRequest.getParameterNames();
		//inputParameters.
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
        	queryBuilder = new DESearchQueryBuilder( httpRequest, "CONTEXT", httpRequest.getParameter("contextIdSeq") , httpRequest.getParameter("contextIdSeq"), desb);
        else if(httpRequest.getParameter("classification") != null)
        	queryBuilder = new DESearchQueryBuilder( httpRequest, "CLASSIFICATION", httpRequest.getParameter("classificationIdSeq") , httpRequest.getParameter("classificationIdSeq"), desb);
        else        	
			queryBuilder = new DESearchQueryBuilder( httpRequest, null, null , null, desb);
        
        
			String queryStmt = queryBuilder.getSQLWithoutOrderBy();
			String orderBy = queryBuilder.getOrderBy();
			System.out.println("- Query stmt is " + queryStmt);

			String resultXML = cdeSearchBo.getDataElements(queryStmt+ " order by  " + orderBy,httpRequest);
			if(resultXML == null)
				 return Response.status(404).build();
			
			String format = (String) httpRequest.getParameter("format");
			if ( null != format && format.equals("XML")) {
				return Response.ok(resultXML).header("Content-Disposition", "application/xml").build();
			}
			else if (null != format &&  format.equals("CSV")) {
				String csvFile = FilesTransformation.transformFormToCSV(resultXML);
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
		 pablicID, name, classification; 
		}

}