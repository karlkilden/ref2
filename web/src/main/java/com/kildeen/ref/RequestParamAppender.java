package com.kildeen.ref;

import java.net.URISyntaxException;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import org.apache.deltaspike.core.util.ExceptionUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.omnifaces.util.Ajax;

@RequestScoped
public class RequestParamAppender {

	private static final String PUSH_STATE = "History.pushState(null, null, '%s');";

	public void append (String currentPage, List<NameValuePair> params) {
    	URIBuilder builder = new URIBuilder();
    	builder.setParameters(params);
    	builder.setPath(currentPage);
//		try {
////			Ajax.oncomplete(String.format(PUSH_STATE, builder.build()));
//		} catch (URISyntaxException e) {
//			ExceptionUtils.throwAsRuntimeException(e);
//		}

	}

	public void append(String currentPage, List<NameValuePair>[] params) {
		// TODO Auto-generated method stub
		
	}
	
}
