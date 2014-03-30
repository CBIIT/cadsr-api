package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.object.MappingSqlQuery;

import gov.nih.nci.ncicb.cadsr.common.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.ContextDAOCDERest;
import gov.nih.nci.ncicb.cadsr.common.resource.Context;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocator;

public class JDBCContextDAOCDERest extends JDBCContextDAO
implements ContextDAOCDERest{
	public JDBCContextDAOCDERest(ServiceLocator locator) 
	{
		    super(locator);
	}
	
	public Context getContextByName(String contextName) {
	     ContextByNameQuery_STMT query = new ContextByNameQuery_STMT();
	     query.setDataSource(getDataSource());
	     query._setSql(contextName);
	     List result = (List) query.execute();
	     Context theContext = null;
	     if (result.size() != 0) {
	       theContext = (Context) (query.execute().get(0));
	     }

	     return theContext;
	  }
	
	/**
	   * Inner class that accesses database to get all the contexts in caDSR
	   *
	   */
	  class ContextByNameQuery_STMT extends MappingSqlQuery {

	    public ContextByNameQuery_STMT(){
	      super();
	    }

	    public void _setSql(String name){
	      super.setSql("select conte_idseq, name from sbr.contexts_view where upper(name) = '" + name.toUpperCase() + "'");
	    }
	   /**
	    * 3.0 Refactoring- Removed JDBCTransferObject
	    */
	    protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
	      Context aContext = new ContextTransferObject();
	      aContext.setConteIdseq(rs.getString(1)); //CONTE_IDSEQ
	      aContext.setName(rs.getString(2));  // NAME
	      return aContext;
	    }
	  }	
   
}
