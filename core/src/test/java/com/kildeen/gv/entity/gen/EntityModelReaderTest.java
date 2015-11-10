package com.kildeen.gv.entity.gen;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import javax.persistence.Column;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class EntityModelReaderTest {

	@Column
	private String testField;

	@Column(name = "test")
	private String testField2;

	private String testField3;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void get_col_name_from_field_should_use_field_when_no_col() throws Exception {
		EntityModelReader reader = new EntityModelReader();
		Field f = getClass().getDeclaredField("testField3");
		assertEquals("testField3", reader.getColumnName(f));
	}

	@Test
	public void get_col_name_should_get_from_col_if_set() throws Exception {
		EntityModelReader reader = new EntityModelReader();
		Field f = getClass().getDeclaredField("testField2");
		assertEquals("test", reader.getColumnName(f));	}

	@Test
	public void should_find_col_when_present() throws Exception {
		EntityModelReader reader = new EntityModelReader();
		Field f = getClass().getDeclaredField("testField");
		reader.getColumn(f);
		
		assertNotNull(reader.getColumn(f));
	}

}
