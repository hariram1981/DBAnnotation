package com.hariram.annotation.db;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.hariram.annotation.AnnotationProcessor;
import com.hariram.annotation.db.sample.MyDAO;

/**
 * 
 */

/**
 * @author hariram
 *
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
