package com.kildeen.gv.poll;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ReflectionUtilsTest {

	List<String> collectionTest;
	
	@Before
	public void setUp() throws Exception {
		
		
	}
	
	@Test
	public void getRealClass() throws Exception {
		
		Field f = ReflectionUtils.findField("collectionTest", getClass());
		System.out.println(ReflectionUtils.getGenericTypeFromCollection(f));
	}

}
