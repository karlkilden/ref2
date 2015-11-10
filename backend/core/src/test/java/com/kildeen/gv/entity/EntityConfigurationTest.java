package com.kildeen.gv.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.kildeen.gv.entity.gen.TestEntities.TestEntity1;
import com.kildeen.gv.entity.gen.TestEntities.TestEntity2;
import com.kildeen.gv.entity.gen.TestEntities.TestEntity3;

public class EntityConfigurationTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void a_table_annotation_with_no_name_should_be_class_as_name() throws Exception {

		EntityConfiguration<TestEntity1> testConf = EntityConfiguration.create(TestEntity1.class);

		assertEquals(TestEntity1.class.getSimpleName(), testConf.getTableName());
	}
	@Test
	public void a_table_annotation_with_a_name_should_be_that_name() throws Exception {
		
		EntityConfiguration<TestEntity2> testConf = EntityConfiguration.create(TestEntity2.class);
		
		assertEquals("TestEntityTWO", testConf.getTableName());
	}
	
	@Test
	public void no_table_annotation_should_be_className() throws Exception {
		
		EntityConfiguration<TestEntity3> testConf = EntityConfiguration.create(TestEntity3.class);
		
		assertEquals(TestEntity3.class.getSimpleName(), testConf.getTableName());
	}
}
