package gov.nih.nci.ncicb.cadsr.form;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.cxf.message.Message;
 
 
 
@Path("/formRetrieve")
public interface FormRetriever {
 
@GET
@Produces ("text/plain")
@Path("{formPublicId}")
public String getForm(@PathParam ("formPublicId") String formPublicId);

@GET
@Produces ({"application/xml","application/octet-stream","application/json"})
//@Path("GET")
public Response getForm(@DefaultValue("") @QueryParam("formPublicId") String formPublicId, 
		@DefaultValue("") @QueryParam("formLongName") String formLongName,
		@DefaultValue("") @QueryParam("context") String context,
		@DefaultValue("") @QueryParam("classification") String classification,
		@DefaultValue("") @QueryParam("protocol") String protocol,
		@DefaultValue("") @QueryParam("createdBy") String createdBy,
		@DefaultValue("") @QueryParam("workFlowStatus") String workFlowStatus,
		@DefaultValue("") @QueryParam("registrationStatus") String registrationStatus,
		@DefaultValue("") @QueryParam("start") String start,
		@DefaultValue("") @QueryParam("size") String size,
		@DefaultValue("") @QueryParam("total") String total,
	    @DefaultValue("XML") @QueryParam("format") String format);


}