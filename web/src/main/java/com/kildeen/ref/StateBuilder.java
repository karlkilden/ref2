package com.kildeen.ref;

import static liquibase.util.StringUtils.lowerCaseFirst;

import java.io.Serializable;
import java.lang.reflect.Field;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.apache.deltaspike.core.util.ReflectionUtils;
import org.omnifaces.util.Faces;

@ViewScoped
public class StateBuilder implements Serializable {

	@Inject
	private StateKeeper stateKeeper;

	public void restoreState() {

	}
	
	@PostConstruct
	private void doRestoreState() {
		for (Class<?> bean : stateKeeper.getBeans()) {
			ReflectionUtils.getAllDeclaredFields(bean).stream().forEach(v -> setIfPresent(bean, v));
		}
	}

	private void setIfPresent(Class<?> bean, Field v) {
		String property = v.getName();
		String value = Faces.getRequestParameterMap().get(property);
		if (value != null) {
			String el = String.format("#{%s.%s}", className(bean), property);
			Faces.evaluateExpressionSet(el, value);
		}
	}

	private String className(Class<?> bean) {
		return lowerCaseFirst(bean.getSimpleName());
	}



}
