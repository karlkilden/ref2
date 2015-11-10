package com.kildeen.ref.fact;

import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;
import org.junit.Test;


public class FactBeanTest {

	@Test
	public void test() throws URISyntaxException {
    	URIBuilder builder = new URIBuilder();
    	builder.setParameter("test", "   ");
    	builder.setPath("aaaaaaaaaa");
    	System.out.println(builder.build());
	}
	
}
