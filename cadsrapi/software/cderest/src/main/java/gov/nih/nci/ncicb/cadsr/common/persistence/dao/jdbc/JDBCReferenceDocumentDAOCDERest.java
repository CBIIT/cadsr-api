package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;
import gov.nih.nci.ncicb.cadsr.common.dto.ReferenceDocumentTransferObject;
import gov.nih.nci.ncicb.cadsr.common.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.ReferenceDocumentDAO;
import gov.nih.nci.ncicb.cadsr.common.resource.ReferenceDocument;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.common.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.object.StoredProcedure;


public class JDBCReferenceDocumentDAOCDERest extends JDBCReferenceDocumentDAO
  {
  
	public JDBCReferenceDocumentDAOCDERest(ServiceLocator locator) 
	{
		super(locator);
	}
	 
	  public List getAllReferenceDocuments(int publicId, float version) {
		     List col = new ArrayList();
		     ReferenceDocumentsQuery query = new ReferenceDocumentsQuery();
		     query.setDataSource(getDataSource());
		     query.setSql(publicId, version);
		     col = query.execute();

		     Iterator iter = col.iterator();
		     while (iter.hasNext())
		     {
		       ReferenceDocument ref = (ReferenceDocument) iter.next();
		       
		       //TODO: watch
		       //ref.setAttachments(this.getAllReferenceDocumentAttachments(ref.getDocIDSeq()));
		     }
		     return col;
		  }
	
	
	  /**
	   * Inner class to get all ReferenceDocuments that belong to
	   * the specified Admin Component
	   */
	  class ReferenceDocumentsQuery extends MappingSqlQuery {
	    ReferenceDocumentsQuery() {
	      super();
	    }
	    
	    public void setSql(int publicId, float version) {
	    	super.setSql(
	    			"SELECT ref.name, ref.dctl_name, ref.ac_idseq, " +
	    	    	        "       ref.rd_idseq, ref.url, ref.doc_text, " +
	    	    	        " ref.conte_idseq, con.name, ref.display_order,DE.VERSION" +
	    			" FROM sbr.DATA_ELEMENTS_VIEW de, sbr.reference_documents_view ref, sbr.contexts_view con" +
	    			" WHERE de.cde_id=" + publicId + " and DE.VERSION=" + version +
	    			" and DE.DE_IDSEQ = REF.AC_IDSEQ" +
	    			" AND ref.conte_idseq = con.conte_idseq " +
	    			" And (ref.dctl_name='Alternate Question Text' or ref.dctl_name='Preferred Question Text')" +
	    			" order by ref.dctl_name DESC NULLS LAST"
	    	        );
	    }

	    protected Object mapRow(
	      ResultSet rs,
	      int rownum) throws SQLException {
	      ReferenceDocument refDoc = new ReferenceDocumentTransferObject();
//
//	      refDoc.setDocName(rs.getString(1));
//	      refDoc.setDocType(rs.getString(2));
//	      refDoc.setDocIDSeq(rs.getString(4));
//	      
//	      String url = getURLWithProtocol(rs.getString(5));
//	      
//	      refDoc.setUrl(getURLWithProtocol(url));
//	      refDoc.setDocText(rs.getString(6));
//	      refDoc.setDisplayOrder(rs.getInt(9));
//
//	      ContextTransferObject contextTransferObject = new ContextTransferObject();
//	      contextTransferObject.setConteIdseq(rs.getString(7)); //CONTE_IDSEQ
//	      contextTransferObject.setName(rs.getString(8)); // CONTEXT_NAME
//	      refDoc.setContext(contextTransferObject);
	      return refDoc;
	    }
	  }


}