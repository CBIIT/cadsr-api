package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.common.dto.bc4j.BC4JClassificationsTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.jdbc.ClassSchemeValueObject;
import gov.nih.nci.ncicb.cadsr.common.dto.CSITransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.ClassificationSchemeDAO;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.ClassificationSchemeDAOCDERest;
import gov.nih.nci.ncicb.cadsr.common.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.common.resource.Classification;
import gov.nih.nci.ncicb.cadsr.common.resource.ClassificationScheme;
import gov.nih.nci.ncicb.cadsr.common.resource.Context;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;


public class JDBCClassificationSchemeDAOCDERest extends JDBCClassificationSchemeDAO
  implements ClassificationSchemeDAOCDERest {
	
	public JDBCClassificationSchemeDAOCDERest(ServiceLocator locator) 
	{
		    super(locator);
	}
   
    /**
     * Gets the Classification
     *
     * @return <b>Collection</b> Collection of ContextTransferObjects
     */
    public Classification getClassificationByName(String longName) {
    	ClassificationItemByNameQuery query = new ClassificationItemByNameQuery();
       query.setDataSource(getDataSource());
       query.setSql(longName);
       List result = query.execute();
       Classification classification = null;
       if (result.size() != 0) {
    	   classification = (Classification) (query.execute().get(0));
       }

       return classification;
    }
    
    /**
     * Inner class that accesses database to get all the contexts in caDSR
     *
     */
    class ClassificationItemByNameQuery extends MappingSqlQuery {

      public ClassificationItemByNameQuery(){
        super();
      }

      public void setSql(String longName){
        super.setSql("select cs_idseq from sbr.classification_Schemes_view where long_name  = '" + longName + "'");
    	 // super.setSql("select csv.CS_CSI_IDSEQ from sbr.cs_csi_view csv, sbr.cs_items_view csi where csv.CSI_IDSEQ= csi.CSI_IDSEQ and csi.LONG_NAME = '" + longName + "'"); 
       }
     /**
      * 
      */
      protected Object mapRow(ResultSet rs, int rownum) throws SQLException {

    	  Classification classification = new BC4JClassificationsTransferObject();
    	  //Adding CS_CSI_IDSEQ
    	  classification.setCsIdseq(rs.getString(1));
    	  return classification;
      }

    }

}
