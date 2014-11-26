package com.hariram.annotation.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.hariram.annotation.AnnotationException;
import com.hariram.annotation.AnnotationProcessor;
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

	public static final Logger LOGGER = Logger.getLogger(DAO.class);
	
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
		LOGGER.info("DAO.setProperties, driverName : " + driverName + ", connUrl: " + connUrl  + ", userName: " + userName  + ", password: " + password);
		this.driverName = driverName;
		this.connUrl = connUrl;
		this.userName = userName;
		this.password = password;
		LOGGER.info("DAO.setProperties, done");
	}
	
	/**
	 * Setup the sql driver in java (using Class.forName)
	 */
	protected void setup() {
		LOGGER.info("DAO.setup, start");
		if(driverName == null || connUrl == null || userName == null || password == null) {
		}
		
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			LOGGER.error("DAO.setup, message : " + e.getClass() + " " + e.getMessage());
		}
		LOGGER.info("DAO.setup, done");
	}
	
	/**
	 * Connect to the db using properties set
	 * 
	 * @return connection to the database
	 */
	protected Connection connect() {
		LOGGER.info("DAO.connect, start");
		try {
			connection = DriverManager.getConnection(connUrl, userName, password);
		} catch (SQLException e) {
			LOGGER.error("DAO.setup, message : " + e.getClass() + " " + e.getMessage());
		}
		LOGGER.info("DAO.connect, connection: " + connection);
		return connection;
	}
	
	/**
	 * Disconnect the connection to the db
	 */
	protected void disconnect() {
		LOGGER.info("DAO.disconnect, start");
		try {
			if(connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			LOGGER.error("DAO.setup, message : " + e.getClass() + " " + e.getMessage());
		}
		LOGGER.info("DAO.disconnect, done");
	}
	/**
	 * Processes the sql provided to the particular db using reflection
	 * 
	 * @param driverName full name of sql driver class
	 * @param connUrl connection string
	 * @param userName username of connection
	 * @param password password of connection
	 * @param dbMethodName the db method to be called
	 * @param dbMethodArgs arguments for the db method to be called
	 * @return Object that is returned from the sql
	 */
	public Object process(String driverName, String connUrl, String userName, String password, String dbMethodName, Object[] dbMethodArgs) {
		LOGGER.info("DAO.process, driverName: " + driverName + ", connUrl: " + connUrl + ", userName: " + userName + ", password: " + password + ", dbMethodName: " + dbMethodName + ", dbMethodArgs: " + dbMethodArgs);
		Object returnObj = null;
		processBefore(driverName, connUrl, userName, password);
		returnObj = AnnotationUtil.callMethod(this, dbMethodName, dbMethodArgs);
		processAfter();
		LOGGER.info("DAO.process, returnObj: " + returnObj);
		return returnObj;
	}
	
	/**
	 * Sets the properties, sql driver setup and connects to the db before processing of sql
	 * @param driverName full name of sql driver class
	 * @param connUrl connection string
	 * @param userName username of connection
	 * @param password password of connection
	 */
	public void processBefore(String driverName, String connUrl, String userName, String password) {
		LOGGER.info("DAO.processBefore, driverName: " + driverName + ", connUrl: " + connUrl + ", userName: " + userName + ", password: " + password);
		setProperties(driverName, connUrl, userName, password);
		setup();
		connect();
		LOGGER.info("DAO.processBefore, done");
	}
	
	/**
	 * Disconnects from the db after processing of sql is done
	 */
	public void processAfter() {
		LOGGER.info("DAO.processAfter, start");
		disconnect();
		LOGGER.info("DAO.processAfter, done");
	}
	
	/**
	 * Method called by all sub classes in order to process annotation and
	 *  invoke callback method
	 * 
	 * @param methodName db method name of the object, that is to be invoked
	 * @param methodArgs arguments for the db method name
	 * @return Object return of the db method invocation
	 */
	public Object processAndCallback(String methodName, Object[] methodArgs) throws AnnotationException {
		LOGGER.info("DAO.processAndCallback, methodName: " + methodName + ", methodArgs: " + methodArgs);
		AnnotationProcessor processor = new DBAnnotationProcessor();
		Object returnObj = processor.process(this, methodName, methodArgs);
		LOGGER.info("DAO.processAfter, returnObj: " + returnObj);
		return returnObj;
	}
}
