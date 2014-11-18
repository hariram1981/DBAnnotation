package com.hariram.annotation.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
/**
 * Annotation for DB connection
 * 
 * @author hariram
 * date 14-Nov-2014
 */
@Target({ElementType.TYPE})
public @interface DB {
	String connUrl();
	String userName();
	String password();
	String driverName();
}
