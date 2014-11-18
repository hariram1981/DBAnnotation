package com.hariram.annotation.db;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.hariram.annotation.AnnotationProcessor;
import com.hariram.annotation.db.sample.MyDAO;

/**
 * JUnit Test for the sample MyDAO class
 * 
 * @author hariram
 * date 14-Nov-2014
 */
public class DBAnnotationProcessorTest {

	@Test
	public void test() {
		AnnotationProcessor processor = new DBAnnotationProcessor();
		Object obj = processor.process(MyDAO.getInstance(), "getData", null);
		System.out.println(obj);
		assertTrue(obj != null);
	}

}
