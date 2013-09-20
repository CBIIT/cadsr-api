package gov.nih.nci.ncicb.cadsr.cdeservice;

import javax.servlet.http.HttpServletRequest;
 
public interface CDESearchBo{
 
	String getDataElements(String query, HttpServletRequest httpRequest);	
 
}