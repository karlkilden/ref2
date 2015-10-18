package com.kildeen.gv;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@SolrResult
@Priority(javax.interceptor.Interceptor.Priority.PLATFORM_AFTER + 20000)
public class SolrResultPostInterceptor {

@Inject
private SolrMapper mapper;

	@AroundInvoke
	public Object logMethodInvocation(InvocationContext invocation) throws Exception {
		
		Object methodResult = invocation.proceed();
		mapper.queue(methodResult);
		return methodResult;
		
	}


}
