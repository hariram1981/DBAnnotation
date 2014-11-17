/**
 * 
 */
package com.hariram.annotation.db;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.hariram.annotation.AnnotationProcessor;


/**
 * @author hariram
 * @date 14-Nov-2014
 */
public class DBAnnotationProcessor implements AnnotationProcessor {
	/**
	 * 
	 */
	public DBAnnotationProcessor() {
	}
	
	/**
	 * 
	 * @param obj
	 */
	public Object process(Object obj, String dbMethodName, Object[] dbMethodArgs) {
		Object returnObj = null;
		Class objClass = obj.getClass();
		if(objClass.isAnnotationPresent(DB.class)) {
			DB dbObj = (DB) objClass.getAnnotation(DB.class);
			//DAO dao = DAO.getInstance();
			//dao.setProperties(dbObj.driverName(), dbObj.connUrl(), dbObj.userName(), dbObj.password());
			//dao.setup();
			//dao.connect();
			//dao.disconnect();
			try {
				Method method = objClass.getMethod("process",String.class, String.class, String.class, String.class, String.class, Object[].class);
				returnObj = method.invoke(obj, dbObj.driverName(), dbObj.connUrl(), dbObj.userName(), dbObj.password(), dbMethodName, dbMethodArgs);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return returnObj;
	}
	
}
