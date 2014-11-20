package com.hariram.annotation.db;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.hariram.annotation.AnnotationProcessor;


/**
 * Annotation process for @DB (that performs DB functionalities)
 * 
 * @author hariram
 * date 14-Nov-2014
 */
public class DBAnnotationProcessor implements AnnotationProcessor {
	private static final Logger LOGGER = Logger.getLogger(DBAnnotationProcessor.class);

	/**
	 * Default constructor
	 */
	public DBAnnotationProcessor() {
	}
	
	/**
	 * Process - call the db method with arguments provided, of the object passed that has the @DB
	 * 
	 * @param obj object which has @DB and the db method to be called using reflection
	 * @param dbMethodName the db method to be called
	 * @param dbMethodArgs arguments for the db method to be called
	 * @return Object that is returned by the db method
	 */
	public Object process(Object obj, String dbMethodName, Object[] dbMethodArgs) {
		LOGGER.info("DBAnnotationProcessor.process, obj: " + obj + ", dbMethodName: " + dbMethodName + ", dbMethodArgs: " + dbMethodArgs);
		Object returnObj = null;
		Class<? extends Object> objClass = obj.getClass();
		if(objClass.isAnnotationPresent(DB.class)) {
			DB dbObj = (DB) objClass.getAnnotation(DB.class);
			try {
				Method method = objClass.getMethod("process",String.class, String.class, String.class, String.class, String.class, Object[].class);
				returnObj = method.invoke(obj, dbObj.driverName(), dbObj.connUrl(), dbObj.userName(), dbObj.password(), dbMethodName, dbMethodArgs);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				LOGGER.error("DBAnnotationProcessor.process, message : " + e.getClass() + " " + e.getMessage());
			}
		}
		LOGGER.info("DBAnnotationProcessor.process, returnObj: " + returnObj);
		
		return returnObj;
	}
	
}
