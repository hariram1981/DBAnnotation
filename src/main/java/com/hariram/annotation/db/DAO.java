/**
 * 
 */
package com.hariram.annotation.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.hariram.annotation.util.AnnotationUtil;

/**
 * @author hariram
 * @date 14-Nov-2014
 */
public class DAO {
	private String driverName = null;
	private String connUrl = null;
	private String userName = null;
	private String password = null;
	
	protected static Connection connection = null;
	
	public static final DAO DAO = new DAO();
	
	/**
	 * Private constructor - singleton pattern
	 */
	protected DAO() {
	}
	
	/**
	 * 
	 * @return
	 */
	public static DAO getInstance() {
		return DAO;
	}
	
	/**
	 * 
	 * @param driverName
	 * @param connUrl
	 * @param userName
	 * @param password
	 */
	protected void setProperties(String driverName, String connUrl, String userName, String password) {
		this.driverName = driverName;
		this.connUrl = connUrl;
		this.userName = userName;
		this.password = password;
	}
	
	/**
	 * 
	 */
	protected void setup() {
		if(driverName == null || connUrl == null || userName == null || password == null) {
		}
		
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	protected Connection connect() {
		try {
			connection = DriverManager.getConnection(connUrl, userName, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	/**
	 * 
	 */
	protected void disconnect() {
		try {
			if(connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @return 
	 * 
	 */
	public Object process(String driverName, String connUrl, String userName, String password, String dbMethodName, Object[] dbMethodArgs) {
		/*setProperties(driverName, connUrl, userName, password);
		setup();
		connect();*/
		/*processBefore(driverName, connUrl, userName, password);
		processAfter();
		return null;*/
		Object returnObj = null;
		processBefore(driverName, connUrl, userName, password);
		returnObj = AnnotationUtil.callMethod(this, dbMethodName, dbMethodArgs);
		processAfter();
		return returnObj;
	}
	
	/**
	 * 
	 */
	public void processBefore(String driverName, String connUrl, String userName, String password) {
		setProperties(driverName, connUrl, userName, password);
		setup();
		connect();
	}
	
	/**
	 * 
	 */
	public void processAfter() {
		disconnect();
	}
}
