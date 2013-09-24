/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-api/LICENSE.txt for details.
 */

package gov.nih.nci.objectCart.migration;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class MigrationUtility {

	private Connection connection = null;

	private String objCartDbURL;
	private String caDSRDbURL;
	private String objCartDriver;
	private String caDSRDriver;
	private String objCartUser;
	private String caDSRUser;
	private String objCartPasswd;
	private String caDSRPasswd;

	public static void main(String[] args) {
		try
		{
			if (args.length == 0 )
			{
				System.out.println("Usage: java MigrationUtility <properties file>");
				System.exit(-1);
			}

			Properties properties = new Properties();
			properties.load(new FileInputStream(args[0]));
			MigrationUtility utility = new MigrationUtility(properties);
			int cart = utility.checkCartTable();
			if(cart == -1)
			{
				System.out.println("CART table does not exist in target database. Please create CART table before running this migration.");
			}
			else if(cart > 0)
			{
				System.out.println("CART table has already been populated with data. Aborting migration.");
			}
			else
			{
				utility.migrateCart();
			}

			int cartObj = utility.checkCartObjectTable();
			if(cartObj == -1)
			{
				System.out.println("CART_OBJECT table does not exist in target database. Please create CART_OBJECT table before running this migration.");
			}
			else if(cartObj > 0)
			{
				System.out.println("CART_OBJECT table has already been populated with data. Aborting migration.");
			}
			else
			{
				utility.migrateCartObject();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			System.out.println("Failed to read properties file: "+args[0]);
			e.printStackTrace();
		}
	}

	public MigrationUtility(Properties props)
	{
		objCartDbURL = props.getProperty("ojbectcart.db.connection.url");
		objCartDriver = props.getProperty("objectcart.db.driver.class");
		objCartUser = props.getProperty("objectcart.db.user.name");
		objCartPasswd = props.getProperty("objectcart.db.user.password");

		caDSRDbURL = props.getProperty("cadsr.db.connection.url");
		caDSRDriver = props.getProperty("cadsr.db.driver.class");
		caDSRUser = props.getProperty("cadsr.db.user.name");
		caDSRPasswd = props.getProperty("cadsr.db.user.password");

		if(objCartDbURL == null || objCartDbURL.trim().length() == 0)
			throw new RuntimeException("Property ojbectcart.db.connection.url value is missing");
		if(objCartDriver == null || objCartDriver.trim().length() == 0)
			throw new RuntimeException("Property objectcart.db.driver.class value is missing");
		if(objCartUser == null || objCartUser.trim().length() == 0)
			throw new RuntimeException("Property objectcart.db.user.name value is missing");
		if(objCartPasswd == null || objCartPasswd.trim().length() == 0)
			throw new RuntimeException("Property objectcart.db.user.password value is missing");

		if(caDSRDbURL == null || caDSRDbURL.trim().length() == 0)
			throw new RuntimeException("Property cadsr.db.connection.url value is missing");
		if(caDSRDriver == null || caDSRDriver.trim().length() == 0)
			throw new RuntimeException("Property cadsr.db.driver.class value is missing");
		if(caDSRUser == null || caDSRUser.trim().length() == 0)
			throw new RuntimeException("Property cadsr.db.user.name value is missing");
		if(caDSRPasswd == null || caDSRPasswd.trim().length() == 0)
			throw new RuntimeException("Property cadsr.db.user.password value is missing");

	}


	private int checkCartTable() throws SQLException
	{
		Connection connection = null;
		Statement stmt = null;
		try
		{
		connection = getConnection(caDSRDbURL, caDSRDriver, caDSRUser, caDSRPasswd);
		stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet resultSet = stmt.executeQuery("SELECT count(*) count FROM CART");
		while(resultSet.next())
		{
			int count = resultSet.getInt("count");
			return count;
		}
		resultSet.close();
		}
		catch(SQLException e)
		{
			return -1;
		}
		finally
		{
			if(stmt != null)
				stmt.close();
			if(connection != null)
				connection.close();
		}
		return -1;
	}

	private int checkCartObjectTable() throws SQLException
	{
		Connection connection = null;
		Statement stmt = null;
		try
		{
		connection = getConnection(caDSRDbURL, caDSRDriver, caDSRUser, caDSRPasswd);
		stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet resultSet = stmt.executeQuery("SELECT count(*) count FROM CART_OBJECT");
		while(resultSet.next())
		{
			int count = resultSet.getInt("count");
			return count;
		}
		resultSet.close();
		}
		catch(SQLException e)
		{
			return -1;
		}
		finally
		{
			if(stmt != null)
				stmt.close();
			if(connection != null)
				connection.close();
		}
		return -1;
	}


	private void migrateCart() throws SQLException
	{
		Connection connection = null;
		Statement stmt = null;
		Connection connection2 = null;
		PreparedStatement stmt2 = null;
		try
		{
		connection = getConnection(objCartDbURL, objCartDriver, objCartUser, objCartPasswd);
		stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet resultSet = stmt.executeQuery("SELECT * FROM CART");

		connection2 = getConnection(caDSRDbURL, caDSRDriver, caDSRUser, caDSRPasswd);
		stmt2 = connection2.prepareStatement("INSERT INTO CART (ID, USER_ID, CREATION_DATE,LAST_WRITE_DATE, NAME, EXPIRATION_DATE,LAST_READ_DATE) VALUES (?, ?, ?, ?, ?, ?, ?)");
		int counter = 0;
		while(resultSet.next())
		{
			stmt2.setInt(1, resultSet.getInt("ID"));
			System.out.println("Migrating CART ID: "+resultSet.getInt("ID"));
			stmt2.setString(2, resultSet.getString("USER_ID"));
			stmt2.setDate(3, resultSet.getDate("CREATION_DATE"));
			stmt2.setDate(4, resultSet.getDate("LAST_WRITE_DATE"));
			stmt2.setString(5, resultSet.getString("NAME"));
			stmt2.setDate(6, resultSet.getDate("EXPIRATION_DATE"));
			stmt2.setDate(7, resultSet.getDate("LAST_READ_DATE"));
			stmt2.executeUpdate();
			counter++;
		}
		System.out.println("CART table migration has successfully migrated: "+counter + " rows");
		resultSet.close();
		}
		finally
		{
			if(stmt != null)
				stmt.close();
			if(connection != null)
				connection.close();
			if(stmt2 != null)
				stmt2.close();
			if(connection2 != null)
				connection2.close();
		}
	}


	private void migrateCartObject() throws SQLException
	{
		Connection connection = null;
		Statement stmt = null;
		Connection connection2 = null;
		PreparedStatement stmt2 = null;
		try
		{
		connection = getConnection(objCartDbURL, objCartDriver, objCartUser, objCartPasswd);
		stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet resultSet = stmt.executeQuery("SELECT * FROM CART_OBJECT");

		connection2 = getConnection(caDSRDbURL, caDSRDriver, caDSRUser, caDSRPasswd);
		stmt2 = connection2.prepareStatement("INSERT INTO CART_OBJECT (ID, DATA, CART_ID,DATE_ADDED, NATIVE_ID, DISPLAY_TEXT,TYPE, RELATED_ID) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)");
		int counter = 0;
		while(resultSet.next())
		{
			stmt2.setInt(1, resultSet.getInt("ID"));
			java.sql.Clob clob = resultSet.getClob("DATA");
			stmt2.setString(2,  resultSet.getString("DATA"));
			stmt2.setInt(3, resultSet.getInt("CART_ID"));
			stmt2.setDate(4, resultSet.getDate("DATE_ADDED"));
			stmt2.setString(5, resultSet.getString("NATIVE_ID"));
			stmt2.setString(6, resultSet.getString("DISPLAY_TEXT"));
			stmt2.setString(7, resultSet.getString("TYPE"));
			stmt2.setInt(8, resultSet.getInt("RELATED_ID"));
			stmt2.addBatch();
			counter++;
			if(counter % 100 == 0)
			{
				stmt2.executeBatch();
				System.out.println("CART_OBJECT table migration has successfully migrated: "+counter + " rows");
			}
		}
		stmt2.executeBatch();
		System.out.println("CART_OBJECT table migration has successfully migrated: "+counter + " rows");
		resultSet.close();
		}
		finally
		{
			if(stmt != null)
				stmt.close();
			if(connection != null)
				connection.close();
			if(stmt2 != null)
				stmt2.close();
			if(connection2 != null)
				connection2.close();
		}
	}

	private Connection getConnection(String url, String driverClass, String userName, String passwd) {
		 Connection connection = null;
		    try {
		        // Load the JDBC driver
		        Class.forName(driverClass);
		        connection = DriverManager.getConnection(url, userName, passwd);
		    } catch (ClassNotFoundException e) {
		    	e.printStackTrace();
		        // Could not find the database driver
		    } catch (SQLException e) {
		    	e.printStackTrace();
		        // Could not connect to the database
		    }
		return connection;
	}

}
