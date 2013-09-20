package gov.nih.nci.ncicb.cadsr.common;

import gov.nih.nci.ncicb.cadsr.common.servicelocator.spring.SpringObjectLocatorImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

	public class AppContextInitServlet implements ServletContextListener {

	  public void contextInitialized(ServletContextEvent servletContextEvent)  {
	      System.out.println("contextInitialized(ServletContextEvent e)");
	        try {
	          SpringObjectLocatorImpl.applicationContext=WebApplicationContextUtils.getRequiredWebApplicationContext(servletContextEvent.getServletContext());

	        } catch (Exception e) {
					e.printStackTrace();
	        }	      
	  }

	  public void contextDestroyed(ServletContextEvent e) 
	  {
	    System.out.println("contextDestroyed(ServletContextEvent e)");
	  }
}
