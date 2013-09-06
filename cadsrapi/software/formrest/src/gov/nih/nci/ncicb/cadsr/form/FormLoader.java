package gov.nih.nci.ncicb.cadsr.form;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
 
 
 
@Path("/formLoad/")
public interface FormLoader {
 
@PUT
@Produces ("application/xml")
@Consumes("application/xml")
public String loadForms(String xmlForm);


}