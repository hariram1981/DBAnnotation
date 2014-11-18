package com.hariram.annotation.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.hariram.annotation.util.AnnotationUtil;

/**
 * Base DAO class with singleton pattern and common functionalities
 * 
 * @author hariram
 * date 14-Nov-2014
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
	 * Returns the single instance of this class
	 * 
	 * @return DAO instance of the class
	 */
	public static DAO getInstance() {
		return DAO;
	}
	
	/**
	 * Sets properties for DB connection
	 * 
	 * @param driverName full name of sql driver class
	 * @param connUrl connection string
	 * @param userName username of connection
	 * @param password password of connection
	 */
	protected void setProperties(String driverName, String connUrl, String userName, String password) {
		this.driverName = driverName;
		this.connUrl = connUrl;
		this.userName = userName;
		this.password = password;
	}
	
	/**
	 * Setup the sql driver in java (using Class.forName)
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
	 * Connect to the db using properties set
	 * 
	 * @return connection to the database
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
	 * Disconnect the connection to the db
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
	 * Processes the sql provided to the particular db using reflection
	 * 
	 * @return Object that is returned from the sql
	 */
	public Object process(String driverName, String connUrl, String userName, String password, String dbMethodName, Object[] dbMethodArgs) {
		Object returnObj = null;
		processBefore(driverName, connUrl, userName, password);
		returnObj = AnnotationUtil.callMethod(this, dbMethodName, dbMethodArgs);
		processAfter();
		return returnObj;
	}
	
	/**
	 * Sets the properties, sql driver setup and connects to the db before processing of sql
	 */
	public void processBefore(String driverName, String connUrl, String userName, String password) {
		setProperties(driverName, connUrl, userName, password);
		setup();
		connect();
	}
	
	/**
	 * Disconnects from the db after processing of sql is done
	 */
	public void processAfter() {
		disconnect();
	}
}
