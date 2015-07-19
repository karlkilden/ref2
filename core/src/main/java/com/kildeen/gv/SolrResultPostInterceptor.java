package com.kildeen.gv;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.commons.collections.CollectionUtils;

import com.kildeen.gv.poll.PollDTO;
import com.kildeen.gv.vote.Poll;

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
