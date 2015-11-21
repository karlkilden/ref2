package com.kildeen.gv.entity.gen;

import java.util.List;

import org.hjson.JsonValue;
import org.hjson.Stringify;
import org.junit.Before;
import org.junit.Test;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

public class CacheReaderTest {

	CacheReader reader;
	ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setUp() throws Exception {
		reader = new CacheReader();

	}

	@Test
	public void get() throws Exception {

		List<ColumnDefinition> columnDefinitions = Lists.newArrayList(ColumnDefinitionBuilder.getInstance().columnName("test")
				.defaultValue("defaultVal").build());
		GenModel model = GenModelBuilder.getInstance().columnDefinitions(columnDefinitions).entityClass("String").build();
		String hjsonString = JsonValue.readHjson(mapper.writeValueAsString(model)).toString(Stringify.HJSON);
		System.out.println(hjsonString);

	}

}
