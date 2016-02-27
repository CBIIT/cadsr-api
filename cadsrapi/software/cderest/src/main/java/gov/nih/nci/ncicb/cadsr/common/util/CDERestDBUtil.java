package gov.nih.nci.ncicb.cadsr.common.util;


import gov.nih.nci.ncicb.cadsr.common.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.common.util.logging.LogFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.sql.DataSource;
//import oracle.jdbc.OracleConnection;
//import org.jboss.resource.adapter.jdbc.WrappedConnection;
//import org.jboss.resource.adapter.jdbc.WrapperDataSource;



public class CDERestDBUtil  {
	
@Resource(lookup="jdbc/CDEBrowserDS")  	
DataSource dataSource;

public void setDataSource(DataSource dataSource) {
	this.dataSource = dataSource;
}

private static Log log = LogFactory.getLog(CDERestDBUtil.class.getName());

  private Connection conn;
  private boolean isConnected = false;

  public CDERestDBUtil() {
    conn = null;
  }
  
  /**
 *  This method returns a Connection obtained from the container using the
 *  datasource name specified as a parameter
*/
  public boolean getConnectionFromContainer()
                    throws Exception {
    if (!isConnected) {
      try {
    	 // OracleConnection connection;
    	 // System.out.println("DATASOURCE CLASS::" + dataSource.getClass().getName());
//    	  WrapperDataSource wrapperDataSource = (WrapperDataSource) dataSource;
    	  Connection connection =  dataSource.getConnection();
      
//        
//        if(connection instanceof org.jboss.resource.adapter.jdbc.WrappedConnection){ 
//        	WrappedConnection wc=(WrappedConnection)connection; 
//        	//with getUnderlying connection method , cast it to Oracle Connection 
//        	conn=(OracleConnection) wc.getUnderlyingConnection(); 
//        	}
//        else
//        {
        	conn=connection;
//        }
        
        //conn = dataSource.getConnection();
        isConnected = true;
        log.info
        ("Connected to the database successfully using datasource "+dataSource.toString());
      }
      catch (Exception e) {
        log.error("Exception occurred in getConnectionFromContainer", e);
        throw new Exception("Exception in getConnectionFromContainer() ");
      }
    }
    return isConnected;
  }
  

  
  public void returnConnection() throws SQLException {
    try {
      if (conn != null) {
        conn.close();
        isConnected = false;
      }
    } 
    catch (SQLException sqle) {
      //log.error("Error occured in returing DB connection to the container", sqle);
      //throw sqle;
    } 
  }

  public Connection getConnection(){
    return conn;
  }

  public ResultSet executeQuery(String sqlStmt) throws SQLException {
    Statement stmt = null;
    ResultSet	rs = null;
    try {
      stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                  ResultSet.CONCUR_READ_ONLY);
      rs = stmt.executeQuery(sqlStmt);
    } 
    catch (SQLException ex) {
      log.error("Exception occurred in executeQuery " + sqlStmt, ex);
      throw ex;
    } 
    return rs;
  }
}