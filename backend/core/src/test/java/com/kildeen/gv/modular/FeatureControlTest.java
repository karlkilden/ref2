package com.kildeen.gv.modular;

import static org.junit.Assert.*;

import org.assertj.core.util.Lists;
import org.hjson.JsonValue;
import org.hjson.Stringify;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kildeen.gv.modular.FeatureControl;


public class FeatureControlTest {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void getInstance_returns_notNull() throws Exception {
		FeatureControl fc = FeatureControlHolder.getInstance();
	}
	ObjectMapper mapper = new ObjectMapper();

	@Test
	public void testName() throws Exception {
		FeatureControl conf = new FeatureControl();
		String hjsonString = JsonValue.readHjson(mapper.writeValueAsString(conf)).toString(Stringify.HJSON);
		System.out.println(hjsonString);

	}

}
