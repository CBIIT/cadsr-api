package gov.nih.nci.ncicb.cadsr.cdeservice.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.CDEXMLConverter;
import gov.nih.nci.ncicb.cadsr.common.persistence.bc4j.ContextsViewRowImpl;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.ConceptDAO;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.DerivedDataElementDAO;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.ValueDomainDAO;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCValueDomainDAO;
import gov.nih.nci.ncicb.cadsr.common.resource.Classification;
import gov.nih.nci.ncicb.cadsr.common.resource.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.common.resource.Context;
import gov.nih.nci.ncicb.cadsr.common.resource.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.common.resource.DerivedDataElement;
import gov.nih.nci.ncicb.cadsr.common.resource.ObjectClass;
import gov.nih.nci.ncicb.cadsr.common.resource.Representation;
import gov.nih.nci.ncicb.cadsr.common.resource.ValidValue;
import gov.nih.nci.ncicb.cadsr.common.resource.ValueDomain;
import gov.nih.nci.ncicb.cadsr.common.cdebrowser.DESearchQueryBuilder;
import gov.nih.nci.ncicb.cadsr.common.cdebrowser.DataElementSearchBean;
import gov.nih.nci.ncicb.cadsr.common.dto.BC4JContextTransferObjectCDERest;
import gov.nih.nci.ncicb.cadsr.common.dto.BC4JDataElementConceptTransferObjectCDERest;
import gov.nih.nci.ncicb.cadsr.common.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.DataElementConceptTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ObjectClassTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.PropertyTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.RepresentationTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ValueDomainTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.bc4j.BC4JClassificationsTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.bc4j.BC4JContextTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.bc4j.BC4JDataElementConceptTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.bc4j.BC4JDataElementTransferObject;
import gov.nih.nci.ncicb.cadsr.common.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.common.resource.Property;
import gov.nih.nci.ncicb.cadsr.common.resource.handler.ClassificationHandler;
import gov.nih.nci.ncicb.cadsr.common.resource.handler.DataElementHandler;
import gov.nih.nci.ncicb.cadsr.common.resource.handler.ValidValueHandler;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocatorFactory;
import gov.nih.nci.ncicb.cadsr.common.util.CDERestDBUtil;
import gov.nih.nci.ncicb.cadsr.common.util.DBUtil;

import gov.nih.nci.ncicb.cadsr.cdeservice.CDESearchBo;

public class CDESearchBoImpl implements CDESearchBo {

	CDERestDBUtil dBUtil;
	  
	public void setdBUtil(CDERestDBUtil dBUtil) {
		this.dBUtil = dBUtil;
	}
	
	public String getDataElements(String query, HttpServletRequest httpRequest) { 
		String  xmlString = null;
		Connection conn = null;
		ResultSet deRows = null;
		PreparedStatement ps = null;
		try {
    		dBUtil.getConnectionFromContainer();   
			conn = dBUtil.getConnection();
			ps = conn.prepareStatement(query);
			deRows = ps.executeQuery();
			
//	       ServiceLocator locator = 
//	    	        ServiceLocatorFactory.getLocator(CaDSRConstants.CDEBROWSER_SERVICE_LOCATOR_CLASSNAME);
//	    	       AbstractDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory(locator);
	    	     
			
//			 String stmt = " SELECT PublicId " +
//                     ", LongName "+
//                     ",  PreferredName  "+
//                     ",  PreferredDefinition  "+
//                     ",  Version  "+
//                     ",  WorkflowStatus  "+
//                     ",  ContextName  "+
//                     ",  ContextVersion  "+
//                     ",  Origin  "+
//                     ",  RegistrationStatus  "+
//                     ",  DataElementConcept  "+
//                     ",  ValueDomain  " +
//                     ",  ReferenceDocumentsList  " +
//                     ",  ClassificationsList  " +
//                     ",  AlternateNameList  " +                    
//                     ",  DataElementDerivation  " +
//                " FROM sbrext.DE_XML_GENERATOR_VIEW  where DE_IDSEQ IN ("+query+")";

//			OracleXMLDataSetExtJdbc dset =
//					 new OracleXMLDataSetExtJdbc(con,stmt);			 
//			 
//			OracleXMLQuery xmlQuery = new OracleXMLQuery(dset);
//		    xmlQuery.setEncoding("UTF-8");
//		    xmlQuery.useNullAttributeIndicator(false);
//
//		    
//		   xmlString = xmlQuery.getXMLString();
	    	       
	   		List deList = new ArrayList<DataElement>();		    			
//			ResultSet deRows =  dBUtil.executeQuery(query);    	       
			DataElement de = null;

		      while(deRows.next()) {
		          de = new BC4JDataElementTransferObject();
		          de.setDeIdseq(deRows.getString("DE_IDSEQ"));
		          de.setPreferredName(deRows.getString("DE_PREFERRED_NAME"));
		          de.setLongName(deRows.getString("LONG_NAME"));

		          de.setLongCDEName(checkForNull(deRows.getString("DOC_TEXT")));
		          de.setContextName(deRows.getString("NAME"));
		          de.setAslName(deRows.getString("ASL_NAME"));
		          de.setCDEId(checkForNull(deRows.getString("DE_CDEID")));
		          de.setVersion(new Float(deRows.getInt("DE_VERSION")));
		          de.setUsingContexts(checkForNull(deRows.getString("DE_USEDBY")));
		          de.setPreferredDefinition(deRows.getString("PREFERRED_DEFINITION"));
		          de.setRegistrationStatus((String)checkForNull(deRows.getString("REGISTRATION_STATUS")));	          
		          de.setVdIdseq((deRows.getString("VD_IDSEQ")).trim());

		          DataElementConcept dec = new DataElementConceptTransferObject();
		          dec.setDecIdseq((checkForNull(deRows.getString("DEC_IDSEQ"))).trim());
		          if( null != dec.getDecIdseq() && dec.getDecIdseq() !="")
		          {
		        	  dec = getDataElementConcept(dec.getDecIdseq());
		        	  de.setDataElementConcept(dec);
		          }
		          Context conte = new BC4JContextTransferObjectCDERest();
		          conte.setConteIdseq((checkForNull(deRows.getString("CONTE_IDSEQ"))).trim());
		          if( null != conte.getConteIdseq() && conte.getConteIdseq() !="")
		          {
		        	  conte = getCotexts(conte.getConteIdseq());
		        	  de.setContext(conte);
		          }

		          
		          de.setClassifications(getClassifications(de.getDeIdseq().trim()));
		          getOtherDataElementDetails(de);
		          deList.add(de);
		        }
		      
		      	//de = getDataElementDetails(de, httpRequest.getSession().getId())
//		        getOtherDataElementDetails(de);
				if(!deList.isEmpty())
					xmlString = CDEXMLConverter.instance().convertFormToXML(deList);
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		catch (Exception e) {
			// TODO Auto-generated catch block
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
					dBUtil.returnConnection();
				}	
	  		 }				
			 catch (Exception ex) 
			 {
				System.out.println("Exception Caught in getCotexts() during cleaning up :" + ex.getMessage());
			 }
		}
		return xmlString;

	}
	/*
	 private DataElement getDataElementDetails(DataElement de, String sessionId) throws Exception {
		  DataElementHandler dh = (DataElementHandler)HandlerFactory.getHandler(DataElement.class);

		  de = (DataElement)dh.findObject(de.getDeIdseq(), sessionId);

		  ServiceLocator locator = ServiceLocatorFactory.getLocator(CaDSRConstants.CDEBROWSER_SERVICE_LOCATOR_CLASSNAME);
		  AbstractDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory(locator);
		  ConceptDAO conDAO = daoFactory.getConceptDAO();

		  if (de != null) {
		   Property prop = de.getDataElementConcept().getProperty();

		   if (prop != null) {
		    ConceptDerivationRule
		       propRule = conDAO.getPropertyConceptDerivationRuleForDEC(de.getDataElementConcept().getDecIdseq());

		    de.getDataElementConcept().getProperty().setConceptDerivationRule(propRule);
		   }

		   ObjectClass objClass = de.getDataElementConcept().getObjectClass();

		   if (objClass != null) {
		    ConceptDerivationRule
		       classRule = conDAO.getObjectClassConceptDerivationRuleForDEC(de.getDataElementConcept().getDecIdseq());

		    de.getDataElementConcept().getObjectClass().setConceptDerivationRule(classRule);
		   }

		   ValidValueHandler validValueHandler = (ValidValueHandler)HandlerFactory.getHandler(ValidValue.class);
		   List vvList = validValueHandler.getValidValues(de.getVdIdseq(), sessionId);

		  if (vvList != null && !vvList.isEmpty()) {
		    vvList = conDAO.populateConceptsForValidValues(vvList);
		   }

		   de.getValueDomain().setValidValues(vvList);
		  }
		  
		  ClassificationHandler classificationHandler = (ClassificationHandler)HandlerFactory.getHandler(Classification.class);
		  List classificationSchemes = classificationHandler.getClassificationSchemes(de.getDeIdseq(), sessionId);
		  de.setClassifications(classificationSchemes);

		  DerivedDataElementDAO ddeDAO = daoFactory.getDerivedDataElementDAO();
		  DerivedDataElement dde = ddeDAO.findDerivedDataElement(de.getDeIdseq());
		  de.setDerivedDataElement(dde);
		  return de;
		 }
*/
	
	 /**
	   * Check for null value for the given Object. If the input Object is null, an
	   * empty String is returned, else toString() of the given object is
	   * returned.
	   *
	   * @param inputObj an <code>Object</code>.
	   *
	   * @return the <code>String</code> value for the given Object.
	   */
	  private String checkForNull(Object inputObj) {
	    if (inputObj == null) {
	      return new String("");
	    }
	    else {
	      return inputObj.toString();
	    }
	  }	
	  
	  private Context getCotexts(String conteIdseq) {
			Connection conn = null;
			ResultSet rows = null;
			PreparedStatement ps = null;			
		  	String sqlQuery = "select CONTE_IDSEQ,NAME,LL_NAME,PAL_NAME,DESCRIPTION,LANGUAGE,VERSION,CREATED_BY,DATE_CREATED,MODIFIED_BY,DATE_MODIFIED from contexts_view where CONTE_IDSEQ	= ?";
		  	BC4JContextTransferObjectCDERest conte = null;
		  	try
		  	{
	    		dBUtil.getConnectionFromContainer();   
				conn = dBUtil.getConnection();
				ps = conn.prepareStatement(sqlQuery);
				ps.setString(1, conteIdseq);
				rows = ps.executeQuery();
			    while(rows.next()) {
			    	conte = new BC4JContextTransferObjectCDERest();
//			    	conte.setConteIdseq(rows.getString("CONTE_IDSEQ"));
			    	conte.setName(rows.getString("NAME"));
			    	conte.setVersion(rows.getFloat("VERSION"));
//			    	conte.setLlName(rows.getString("LL_NAME"));
//			    	conte.setPalName(rows.getString("PAL_NAME"));
			    	conte.setDescription(rows.getString("DESCRIPTION"));
//			    	conte.setLanguage(rows.getString("LANGUAGE"));
//			    	conte.setCreatedBy(rows.getString("CREATED_BY"));
//			    	conte.setDateCreated(rows.getTimestamp("DATE_CREATED"));
//			    	conte.setModifiedBy(rows.getString("MODIFIED_BY"));
//			    	conte.setDateModified(rows.getTimestamp("DATE_MODIFIED"));
			    }
		  	}
		    catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					if(ps!=null)
						ps.close();
			
					if(rows!=null)
						rows.close();
					if (conn != null)
						conn=null;
				}				
			 catch (Exception ex) {
				System.out.println("Exception Caught in getCotexts() during cleaning up :" + ex.getMessage());
			}
		}
		    
		    return (Context) conte;
	  }

	  
	  private DataElementConcept getDataElementConcept(String deconceptIdseq) 
	  {		  
			Connection conn = null;
			ResultSet rows = null;
			PreparedStatement ps = null;
			BC4JDataElementConceptTransferObjectCDERest deConcept = null;

		  	String sqlQuery = "select dec.DEC_IDSEQ DEC_IDSEQ,dec.VERSION DEC_VERSION,dec.PREFERRED_NAME DEC_PREFERRED_NAME,dec.CONTE_IDSEQ DEC_CONTE_IDSEQ, "
		  			+ " dec.PROPL_NAME DEC_PROPL_NAME,dec.OCL_NAME DEC_OCL_NAME,dec.PREFERRED_DEFINITION DEC_PREFERRED_DEFINITION,dec.ASL_NAME DEC_ASL_NAME,dec.LONG_NAME DEC_LONG_NAME,dec.LATEST_VERSION_IND DEC_LATEST_VERSION_IND, "
		  			+ " dec.DELETED_IND DEC_DELETED_IND,dec.DATE_CREATED DEC_DATE_CREATED,dec.CREATED_BY DEC_CREATED_BY,dec.DATE_MODIFIED DEC_DATE_MODIFIED,dec.MODIFIED_BY DEC_MODIFIED_BY, "
		  			+ " dec.OBJ_CLASS_QUALIFIER DEC_OBJ_CLASS_QUALIFIER,dec.PROPERTY_QUALIFIER DEC_PROPERTY_QUALIFIER, dec.CHANGE_NOTE DEC_CHANGE_NOTE, dec.OC_IDSEQ DEC_OC_IDSEQ, dec.PROP_IDSEQ DEC_PROP_IDSEQ, dec.ORIGIN DEC_ORIGIN, dec.DEC_ID DEC_ID, "
		  			+ " cd.PREFERRED_NAME CD_PREFERRED_NAME, cd.VERSION CD_VERSION,cd.LONG_NAME CD_LONG_NAME, cd.CD_ID CD_ID, cd.CONTE_IDSEQ CD_CONTE_IDSEQ "
		  			+ " from data_element_concepts_view dec,CONCEPTUAL_DOMAINS cd where dec.CD_IDSEQ=cd.CD_IDSEQ and DEC_IDSEQ = ?"; ;
		  
		  	try
		  	{
	    		dBUtil.getConnectionFromContainer();   
				conn = dBUtil.getConnection();
				ps = conn.prepareStatement(sqlQuery);
				ps.setString(1, deconceptIdseq);
				rows = ps.executeQuery();	    		 
			    while(rows.next()) {
			    	deConcept = new BC4JDataElementConceptTransferObjectCDERest();
			    	deConcept.setDecIdseq(rows.getString("DEC_IDSEQ"));
			    	deConcept.setVersion(rows.getFloat("DEC_VERSION"));
			    	deConcept.setPreferredName(rows.getString("DEC_PREFERRED_NAME"));
			    	deConcept.setConteIdseq(rows.getString("DEC_CONTE_IDSEQ"));
//			    	deConcept.setCdIdseq(rows.getString("DEC_CD_IDSEQ"));
			    	
			    	deConcept.setCdPrefName(rows.getString("CD_PREFERRED_NAME"));	
			        Context cdcontext = new BC4JContextTransferObjectCDERest();
			        cdcontext = getCotexts(rows.getString("CD_CONTE_IDSEQ"));		    	
			    	deConcept.setCdContextName(cdcontext.getName());
			    	deConcept.setCdVersion(rows.getFloat("CD_VERSION"));
			    	deConcept.setCdPublicId(rows.getInt("CD_ID"));
			    	deConcept.setCdLongName(rows.getString("CD_LONG_NAME"));
			   // 	deConcept.setCdVersion(cdcontext.getVersion());
			    				    	
			        Context context = new BC4JContextTransferObjectCDERest();
			        context = getCotexts(deConcept.getConteIdseq());			        
			    	deConcept.setContext(context);
			    	deConcept.setProplName(rows.getString("DEC_PROPL_NAME"));
			    	deConcept.setOclName(rows.getString("DEC_OCL_NAME"));			    	
			    	deConcept.setPreferredDefinition(rows.getString("DEC_PREFERRED_DEFINITION"));
			    	deConcept.setAslName(rows.getString("DEC_ASL_NAME"));
			    	deConcept.setLongName(rows.getString("DEC_LONG_NAME"));
			    	deConcept.setLatestVersionInd(rows.getString("DEC_LATEST_VERSION_IND"));
			    	deConcept.setDeletedInd(rows.getString("DEC_DELETED_IND"));
			    	deConcept.setDateCreated(rows.getTimestamp("DEC_DATE_CREATED"));
			    	deConcept.setCreatedBy(rows.getString("DEC_CREATED_BY"));
			    	deConcept.setDateModified(rows.getTimestamp("DEC_DATE_MODIFIED"));
			    	deConcept.setModifiedBy(rows.getString("DEC_MODIFIED_BY"));			    	
			    	deConcept.setObjClassQualifier(rows.getString("DEC_OBJ_CLASS_QUALIFIER"));
			    	deConcept.setPropertyQualifier(rows.getString("DEC_PROPERTY_QUALIFIER"));
			    	deConcept.setChangeNote(rows.getString("DEC_CHANGE_NOTE"));
//			    	deConcept.set(rows.getString("OC_IDSEQ"));
			    	ObjectClass oc = new ObjectClassTransferObject();
			    	oc.setIdseq(rows.getString("DEC_OC_IDSEQ"));
			    	deConcept.setObjectClass(oc);
			    	Property property = new PropertyTransferObject();
			    	property.setIdseq(rows.getString("DEC_PROP_IDSEQ"));
			    	deConcept.setProperty(property);
			    	deConcept.setOrigin(rows.getString("DEC_ORIGIN"));
			    	deConcept.setPublicId(rows.getInt("DEC_ID"));
			    	//deConcept.set(rows.getString("CDR_NAME"));

			    }
		  	}
		    catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
						if(ps!=null)
							ps.close();
				
						if(rows!=null)
							rows.close();
						if (conn != null)
							conn=null;
					}				
				 catch (Exception ex) {
					System.out.println("Exception Caught in getDataElementConcept() during cleaning up :" + ex.getMessage());
				}
			}
		    
		    return (DataElementConcept) deConcept;
	  }
	  
	  
	  private List<Classification> getClassifications(String deClassificationdseq) {
			Connection conn = null;
			ResultSet rows = null;
			PreparedStatement ps = null;
			BC4JDataElementConceptTransferObject deConcept = null;
			List classficationList = new ArrayList<Classification>();
		    String sqlQuery = "SELECT AdministeredComponents.AC_IDSEQ AC_IDSEQ,       AdministeredComponents.ACTL_NAME ACTL_NAME,       AdministeredComponents.VERSION AC_VERSION,     "
		     		+ "  AdministeredComponents.CONTE_IDSEQ AC_CONTE_IDSEQ,       ClassSchemeItems.CSI_IDSEQ CSI_IDSEQ, ClassSchemeItems.CSITL_NAME CSITL_NAME,       ClassSchemeItems.LONG_NAME CSI_LONG_NAME,   "
		     		+ "    ClassSchemeItems.CSI_ID CSI_ID,       ClassSchemeItems.VERSION CSI_VERSION,        ClassificationSchemes.CS_IDSEQ CS_IDSEQ,       ClassificationSchemes.PREFERRED_NAME  CS_PREFERRED_NAME,    "
		     		+ "    ClassificationSchemes.PREFERRED_DEFINITION CS_PREFERRED_DEFINITION,	   ClassificationSchemes.CS_ID CS_ID,	   ClassificationSchemes.VERSION CS_VERSION,	   ClassificationSchemes.LONG_NAME CS_LONG_NAME "
		     		+ "  FROM AC_CSI AcCsi, ADMINISTERED_COMPONENTS AdministeredComponents, CLASSIFICATION_SCHEMES ClassificationSchemes, CS_ITEMS_VIEW ClassSchemeItems, CS_CSI CsCsi "
		     		+ "  WHERE (((AcCsi.AC_IDSEQ = AdministeredComponents.AC_IDSEQ) AND (CsCsi.CS_IDSEQ = ClassificationSchemes.CS_IDSEQ)) AND (CsCsi.CSI_IDSEQ = ClassSchemeItems.CSI_IDSEQ)) "
		     		+ "  AND (AcCsi.CS_CSI_IDSEQ = CsCsi.CS_CSI_IDSEQ) "
		     		+ "  AND AdministeredComponents.AC_IDSEQ = ?"; 
		  	
		  	try
		  	{
	    		dBUtil.getConnectionFromContainer();   
				conn = dBUtil.getConnection();
				ps = conn.prepareStatement(sqlQuery);
				ps.setString(1, deClassificationdseq);
				rows = ps.executeQuery();	
			    while(rows.next()) {
			    	BC4JClassificationsTransferObject deClassifications = new BC4JClassificationsTransferObject();

			    	deClassifications.setDeIdseq(rows.getString("AC_IDSEQ"));
			    	deClassifications.setClassSchemeName(rows.getString("CS_PREFERRED_NAME"));
			    	deClassifications.setClassSchemeDefinition(rows.getString("CS_PREFERRED_DEFINITION"));
			    	deClassifications.setClassSchemeLongName(rows.getString("CS_LONG_NAME"));
			    	deClassifications.setClassSchemeItemName(rows.getString("CSI_LONG_NAME"));
			    	deClassifications.setClassSchemeItemType(rows.getString("CSITL_NAME"));
			    	deClassifications.setClassSchemeItemId(rows.getInt("CSI_ID"));
			    	deClassifications.setClassSchemeItemVersion(rows.getFloat("CSI_VERSION"));
			    	deClassifications.setCsIdseq(rows.getString("CS_IDSEQ").trim());
			    	deClassifications.setCsiIdseq(rows.getString("CSI_IDSEQ").trim());
			    	deClassifications.setCsVersion(rows.getFloat("CS_VERSION"));
			    	classficationList.add((Classification) deClassifications);
			    }
		  	}
		    catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					if(ps!=null)
						ps.close();
			
					if(rows!=null)
						rows.close();
					if (conn != null)
						conn=null;
				}				
			 catch (Exception ex) {
				System.out.println("Exception Caught in getClassifications() during cleaning up :" + ex.getMessage());
			}
		}
		    
		    return classficationList;
	  }	  
	  
	  
		 private DataElement getOtherDataElementDetails(DataElement de) throws Exception 
		 {
			  ServiceLocator locator = ServiceLocatorFactory.getLocator(CaDSRConstants.CDEBROWSER_SERVICE_LOCATOR_CLASSNAME);
			  AbstractDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory(locator);
			  ValueDomainDAO valueDomainDAO = daoFactory.getValueDomainDAO();			  
			  
			  ConceptDAO conDAO = daoFactory.getConceptDAO();

			  if (de != null) {
				  de.setReferenceDocs(conDAO.getAllReferenceDocuments(de.getDeIdseq(), null));
				  de.setDesignations(conDAO.getDesignations(de.getDeIdseq(), null));
			  if( null != de.getVdIdseq())
			  { 
				ValueDomain vd = valueDomainDAO.getValueDomainById(de.getVdIdseq());
				
				Collection vdIdseqList = new ArrayList();
				vdIdseqList.add(de.getVdIdseq());
				Map vdMap = valueDomainDAO.getPermissibleValues(vdIdseqList);
				vd.setValidValues((List) vdMap.get(de.getVdIdseq()));
				de.setValueDomain(vd);
			  }
			  
			  
			  if (de.getValueDomain().getConceptDerivationRule() != null) {
			        ConceptDerivationRule rule =
			          conDAO.findConceptDerivationRule(
			            de.getValueDomain().getConceptDerivationRule().getIdseq());
			        de.getValueDomain().setConceptDerivationRule(rule);
			      }

		      if (de.getValueDomain().getRefereceDocs() == null) {
		        ValueDomainDAO vdDAO = daoFactory.getValueDomainDAO();
		        de.getValueDomain().setReferenceDocs(vdDAO.getAllReferenceDocuments(de.getValueDomain().getVdIdseq(), null));
		      }
		      
		      if (de.getValueDomain().getContext() == null) {
		          ValueDomainDAO vdDAO = daoFactory.getValueDomainDAO();
		          de.getValueDomain().setContext(vdDAO.getContext(de.getVdIdseq()));
		      }			  

		     Representation rep = getRepresentation(de.getValueDomain().getVdIdseq());
			 if(rep!=null)
			 {
			   ConceptDerivationRule repRule = conDAO.getRepresentationDerivationRuleForVD(de.getValueDomain().getVdIdseq());
			   rep.setConceptDerivationRule(repRule);
			   de.getValueDomain().setRepresentation(rep);
			 }
		      
			  Property prop = de.getDataElementConcept().getProperty();

			  if (prop != null) {
			    ConceptDerivationRule
			       propRule = conDAO.getPropertyConceptDerivationRuleForDEC(de.getDataElementConcept().getDecIdseq());

			    de.getDataElementConcept().getProperty().setConceptDerivationRule(propRule);
			   }

			   ObjectClass objClass = de.getDataElementConcept().getObjectClass();

			   if (objClass != null) {
			    ConceptDerivationRule
			       classRule = conDAO.getObjectClassConceptDerivationRuleForDEC(de.getDataElementConcept().getDecIdseq());

			    de.getDataElementConcept().getObjectClass().setConceptDerivationRule(classRule);
			   }

				  DerivedDataElementDAO ddeDAO = daoFactory.getDerivedDataElementDAO();
				  if(null != de.getDeIdseq())
				  {
				  DerivedDataElement dde = ddeDAO.findDerivedDataElement(de.getDeIdseq());
				  de.setDerivedDataElement(dde);
				  }
			  }
			  

			  return de;
			 }

		 		  
		  private Representation getRepresentation(String vdIdseq) {
				Connection conn = null;
				ResultSet rows = null;
				PreparedStatement ps = null;			
			  	String sqlQuery = "select rep.PREFERRED_NAME REP_PREFER_NAME,rep.LONG_NAME REP_LONG_NAME,rep.VERSION REP_VERSION, rep.REP_ID REP_ID, conte.VERSION CONTE_VERSION,conte.NAME CONTE_NAME from sbr.value_domains_view vd, REPRESENTATIONS_EXT rep,sbr.contexts_view conte  where vd.REP_IDSEQ = rep.REP_IDSEQ and conte.CONTE_IDSEQ=rep.CONTE_IDSEQ and vd_IDSEQ = ?";
			  	RepresentationTransferObject representation = null;
			  	try
			  	{
		    		dBUtil.getConnectionFromContainer();   
					conn = dBUtil.getConnection();
					ps = conn.prepareStatement(sqlQuery);
					ps.setString(1, vdIdseq);
					rows = ps.executeQuery();
				    while(rows.next()) {
				    	representation = new RepresentationTransferObject();
				    	representation.setPreferredName(rows.getString("REP_PREFER_NAME"));
				    	representation.setLongName(rows.getString("REP_LONG_NAME"));
				    	representation.setVersion(rows.getFloat("REP_VERSION"));
				    	representation.setPublicId(rows.getInt("REP_ID"));
				    	BC4JContextTransferObjectCDERest conte = new BC4JContextTransferObjectCDERest();
				    	conte.setName(rows.getString("CONTE_NAME"));
				    	conte.setVersion(rows.getFloat("CONTE_VERSION"));
				    	
				    	representation.setContext(conte);
				    }
			  	}
			    catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					try {
						if(ps!=null)
							ps.close();
				
						if(rows!=null)
							rows.close();
						if (conn != null)
							conn=null;
					}				
				 catch (Exception ex) {
					System.out.println("Exception Caught in getCotexts() during cleaning up :" + ex.getMessage());
				}
			}		 
			  	return (Representation) representation;		 
		  }
		  		  
}
