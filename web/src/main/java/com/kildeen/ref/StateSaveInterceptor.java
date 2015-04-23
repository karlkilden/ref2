package com.kildeen.ref;

import static liquibase.util.StringUtils.lowerCaseFirst;

import java.io.Serializable;
import java.lang.reflect.Method;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.deltaspike.core.api.provider.BeanProvider;

import liquibase.util.StringUtils;

@Interceptor
@State
public class StateSaveInterceptor implements Serializable {

	private static final Object[] NO_ARGS = new Object[] {};
	private static final Class<?>[] NO_PARAMS = new Class<?>[] {};

	@Inject
	private StateKeeper stateKeeper;

	@Inject
	private ConstructedTracker constructedTracker;

	private enum MethodType {
		GET, SET, ACTION, OTHER
	}

	@AroundInvoke
	public Object saveState(InvocationContext invocation) throws Exception {
		MethodType methodType = resolveMethodType(invocation);

		if (methodType == MethodType.GET && constructedTracker.isConstructedThisRequest()) {
			constructedTracker.restoreState();
		}

		Object setResult = invocation.proceed();
		if (methodType == MethodType.ACTION) {
			stateKeeper.addState();
		} else if (methodType == MethodType.SET) {

			String property = getPropertyNameIfSetter(invocation);
			if (property != null) {
				Method getter = invocation.getMethod().getDeclaringClass().getMethod("get" + StringUtils.upperCaseFirst(property), NO_PARAMS);
				Object value = getter.invoke(invocation.getTarget(), NO_ARGS);
				stateKeeper.addValue(property, value);
			}
		}
		return setResult;

	}

	private MethodType resolveMethodType(InvocationContext invocation) {
		Method method = invocation.getMethod();
		String methodName = method.getName();
		if (methodName.startsWith("set")) {
			return MethodType.SET;
		} else if (methodName.startsWith("get")) {
			return MethodType.GET;
		} else if (method.isAnnotationPresent(State.class)) {
			return MethodType.ACTION;
		} else {
			return MethodType.OTHER;
		}
	}

	private String getPropertyNameIfSetter(InvocationContext invocation) {
		return lowerCaseFirst(invocation.getMethod().getName().replace("set", ""));
	}

	@PostConstruct
	public Object restoreState(InvocationContext invocation) throws Exception {
		constructedTracker.setConstructedThisRequest(true);
		stateKeeper.addBean(invocation.getTarget().getClass());
		return invocation.proceed();

	}

}
