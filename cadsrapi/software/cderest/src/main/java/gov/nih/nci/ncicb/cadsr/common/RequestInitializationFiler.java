package gov.nih.nci.ncicb.cadsr.common;

import gov.nih.nci.ncicb.cadsr.common.dto.CSITransferObject;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.ClassificationSchemeDAO;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.ClassificationSchemeDAOCDERest;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.ContextDAO;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCClassificationSchemeDAO;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCClassificationSchemeDAOCDERest;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocatorFactory;
import gov.nih.nci.ncicb.cadsr.common.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.common.resource.Classification;
import gov.nih.nci.ncicb.cadsr.common.resource.Context;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


public class RequestInitializationFiler implements Filter {


    public void init(FilterConfig filterConfig) throws ServletException {
    }

   
    public void doFilter(ServletRequest servletRequest,
            ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest wrappedRequest = new InitializationHttpServletRequest(
                (HttpServletRequest) servletRequest);
        filterChain.doFilter(wrappedRequest, servletResponse);
    }

   
    public void destroy() {
  
    }


    private static class InitializationHttpServletRequest extends
            HttpServletRequestWrapper {
    	
        private final Map<String, String[]> modifiableParameters;
        private Map<String, String[]> allParameters = null;
    	
    	public InitializationHttpServletRequest(HttpServletRequest request) {
            super(request);
            modifiableParameters = new TreeMap<String, String[]>();
            modifiableParameters.put("SEARCH",new String[]{"1","1"});
            modifiableParameters.put("txtValueDomain", null);
            modifiableParameters.put("txtDataElementConcept", null);
            modifiableParameters.put("txtClassSchemeItem", null);
            //modifiableParameters.put("jspKeyword", "");
           
            modifiableParameters.put("altName", null);
            modifiableParameters.put("jspValueDomain", null);
            modifiableParameters.put("jspDataElementConcept", null);
            modifiableParameters.put("jspClassification", null);
           
            modifiableParameters.put("jspLatestVersion", null);
            modifiableParameters.put("contextUse", new String[]{"both"});
            modifiableParameters.put("jspValidValue", null);
            modifiableParameters.put("jspAltName", null);
            
            modifiableParameters.put("jspConceptName", null);
            modifiableParameters.put("jspConceptCode", null);
            modifiableParameters.put("jspObjectClass", null);
            modifiableParameters.put("jspProperty", null);

            modifiableParameters.put("jspPVSearchMode", null);
            modifiableParameters.put("jspVDType", null);
            modifiableParameters.put("jspSearchIn", new String[]{"ALL"});
            if(request.getParameter("publicId") != null)
            {
            	String input = request.getParameter("publicId");
            	String modPID = null;
            	String searchMode = "Exact phrase";
            	if (input.contains(","))
            	{
            		modPID = input.replace(",", " ");
            		searchMode = "At least one of the words";
            	}else{
            		modPID = input;
            	}
        
            	 modifiableParameters.put("jspSimpleKeyword", new String[]{modPID});
            	 modifiableParameters.put("jspCdeId", new String[]{modPID});
            	 modifiableParameters.put("jspBasicSearchType", new String[]{"publicId"});
            	 modifiableParameters.put("jspNameSearchMode", new String[]{searchMode});
            }
            if(request.getParameter("name") != null)
            {
            	 modifiableParameters.put("jspSimpleKeyword", new String[]{request.getParameter("name")});
            	 modifiableParameters.put("jspBasicSearchType", new String[]{"name"});
            	 modifiableParameters.put("jspKeyword", new String[]{request.getParameter("name")});
                 modifiableParameters.put("jspNameSearchMode", new String[]{"Exact phrase"});
            }
            if(request.getParameter("registrationStatus") != null)
            	modifiableParameters.put("regStatus", request.getParameter("registrationStatus").split(","));
            else
            	modifiableParameters.put("regStatus", new String[]{"ALL"});

            if(request.getParameter("workflowStatus") != null)
            	modifiableParameters.put("jspStatus", request.getParameter("workflowStatus").split(","));              
            else
            	modifiableParameters.put("jspStatus", new String[]{"ALL"});
            
            if(request.getParameter("classification") != null)
            {
  			  	ServiceLocator locator = ServiceLocatorFactory.getLocator(CaDSRConstants.CDEBROWSER_SERVICE_LOCATOR_CLASSNAME);
  			  
				String classification = request.getParameter("classification");
				ClassificationSchemeDAOCDERest  classificationDAO = new JDBCClassificationSchemeDAOCDERest(locator);
				String classificationIdSeq = null;
				String searchMode = "Exact phrase";
  			  
        			//ClassificationSchemeDAOCDERest classificationDAO =  (ClassificationSchemeDAOCDERest) daoFactory.getClassificationSchemeDAO();
    			StringTokenizer classtokenizer = new StringTokenizer(classification, ",");

    			StringBuffer classBuffer = new StringBuffer();
    			while (classtokenizer.hasMoreElements()) {
    				Classification classific = null;
    				String idSeq = null;
    				if( classBuffer.length() > 0 )
    				{
    					classBuffer.append(" ");
    					searchMode = "At least one of the words";
    				}	
    				classific = ((Classification) classificationDAO.getClassificationByName((String)classtokenizer.nextElement()));
    				if(null != classific)
    					idSeq = classific.getCsIdseq();    				

    				if ( idSeq != null )
    					classBuffer.append(idSeq);
    			}
    			classificationIdSeq = classBuffer.toString();
    			modifiableParameters.put("classificationIdSeq",new String[]{classificationIdSeq});
//    			 modifiableParameters.put("jspClassification", new String[]{classificationIdSeq} );
//            	 modifiableParameters.put("jspBasicSearchType", new String[]{"classification"});
//            	 modifiableParameters.put("jspNameSearchMode", new String[]{searchMode});
            }
            if(request.getParameter("context") != null)
            {
  			  	ServiceLocator locator = ServiceLocatorFactory.getLocator(CaDSRConstants.CDEBROWSER_SERVICE_LOCATOR_CLASSNAME);
  			    AbstractDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory(locator);
  			  	ContextDAO contextDao = daoFactory.getContextDAO();
  			  
				String context = request.getParameter("context");
				String contextIdSeq = null;
				String searchMode = "Exact phrase";
  			  
        			//ClassificationSchemeDAOCDERest classificationDAO =  (ClassificationSchemeDAOCDERest) daoFactory.getClassificationSchemeDAO();
    			StringTokenizer classtokenizer = new StringTokenizer(context, ",");

    			StringBuffer classBuffer = new StringBuffer();
    			while (classtokenizer.hasMoreElements()) {
    				if( classBuffer.length() > 0 )
    				{
    					classBuffer.append(" ");
    					searchMode = "At least one of the words";
    				}	
    				String idSeq = (String) contextDao.getContextByName((String)classtokenizer.nextElement()).getConteIdseq();

    				if ( idSeq != null )
    					classBuffer.append(idSeq);
    			}
    			contextIdSeq = classBuffer.toString();
    			modifiableParameters.put("contextIdSeq",new String[]{contextIdSeq});
//    			 modifiableParameters.put("jspClassification", new String[]{classificationIdSeq} );
//            	 modifiableParameters.put("jspBasicSearchType", new String[]{"classification"});
//            	 modifiableParameters.put("jspNameSearchMode", new String[]{searchMode});
            }
            
            if(request.getParameter("classificationItem") != null)
            {
  			  	ServiceLocator locator = ServiceLocatorFactory.getLocator(CaDSRConstants.CDEBROWSER_SERVICE_LOCATOR_CLASSNAME);
  			  
				String classificationItem = request.getParameter("classificationItem");
				ClassificationSchemeDAOCDERest  classificationDAO = new JDBCClassificationSchemeDAOCDERest(locator);
				String classificationItemIdSeq = null;
				String searchMode = "Exact phrase";
				
  			  
        			//ClassificationSchemeDAOCDERest classificationDAO =  (ClassificationSchemeDAOCDERest) daoFactory.getClassificationSchemeDAO();
    			StringTokenizer classtokenizer = new StringTokenizer(classificationItem, ",");

    			StringBuffer classBuffer = new StringBuffer();
    			while (classtokenizer.hasMoreElements()) {
    				ClassSchemeItem classSchemeItem = null;
    				String idSeq = null;
    				if( classBuffer.length() > 0 )
    				{
    					classBuffer.append(" ");
    					searchMode = "At least one of the words";
    				}	
    				classSchemeItem = ((ClassSchemeItem) classificationDAO.getClassificationItemByName((String)classtokenizer.nextElement()));
    				if(null != classSchemeItem)
    					idSeq = classSchemeItem.getCsiIdseq();
    					

    				if ( idSeq != null )
    					classBuffer.append(idSeq);
    			}
    			classificationItemIdSeq = classBuffer.toString();
    			//modifiableParameters.put("classificationIdSeq",new String[]{classificationIdSeq});
    			 modifiableParameters.put("jspClassification", new String[]{classificationItemIdSeq} );
            	 modifiableParameters.put("jspBasicSearchType", new String[]{"classification"});
            	 modifiableParameters.put("jspNameSearchMode", new String[]{searchMode});
            }            
            	
        }
    	
    	
        @Override
        public String getParameter(final String name)
        {
            String[] strings = getParameterMap().get(name);
            if (strings != null)
            {
                return strings[0];
            }
            return super.getParameter(name);
        }

        @Override
        public Map<String, String[]> getParameterMap()
        {
            if (allParameters == null)
            {
                allParameters = new TreeMap<String, String[]>();
                allParameters.putAll(super.getParameterMap());
                allParameters.putAll(modifiableParameters);
            }
            //Return an unmodifiable collection because we need to uphold the interface contract.
            return Collections.unmodifiableMap(allParameters);
        }

        @Override
        public Enumeration<String> getParameterNames()
        {
            return Collections.enumeration(getParameterMap().keySet());
        }

        @Override
        public String[] getParameterValues(final String name)
        {
            return getParameterMap().get(name);
        }
    }
}