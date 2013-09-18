package gov.nih.nci.ncicb.cadsr.form;

import javax.ws.rs.core.Response;

import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.message.Message;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import gov.nih.nci.ncicb.cadsr.common.persistence.dao.UserManagerDAO;

public class AuthenticationHandler implements RequestHandler {
	public static String URL = "";
	UserManagerDAO userManagerDAO = null;
	
    public Response handleRequest(Message m, ClassResourceInfo resourceClass) {
		@SuppressWarnings("resource")
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/applicationContext-service-db.xml");
			
		userManagerDAO = (UserManagerDAO)applicationContext.getBean("userManagerDAO");
		
        AuthorizationPolicy policy = (AuthorizationPolicy)m.get(AuthorizationPolicy.class);
        String url = (String) m.get("org.apache.cxf.request.url");        
        String username = "";
        String password = "";
        
        URL = url;
        if ( url != null & url.contains(Constants.FORM_RETRIEVE_URL)) {
        	return null;
        }
        
        if( policy != null ) {
        	username = policy.getUserName();
        	password = policy.getPassword(); 
        }
        if (isAuthenticated(username, password)) {
            // let request to continue
            return null;
        } else {
            // authentication failed, request the authetication, add the realm name if needed to the value of WWW-Authenticate 
            return Response.status(401).header("WWW-Authenticate", "Basic").build();
        }
    }

	private boolean isAuthenticated(String username, String password) {
		//if (username.equals("user") && password.equals("pass"))
		if (userManagerDAO.validUser(username, password))	
			return true;
		else
			return false;
	}
 
}
