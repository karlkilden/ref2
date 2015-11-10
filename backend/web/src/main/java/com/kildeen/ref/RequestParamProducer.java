package com.kildeen.ref;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.google.common.base.Strings;

public class RequestParamProducer implements Serializable {

	private static final long serialVersionUID = -4260202951977249652L;
	@Inject
	FacesContext facesContext;

	@Produces
	@RequestParam
	String getRequestParameter(InjectionPoint ip) {
		String name = ip.getAnnotated().getAnnotation(RequestParam.class).value();

		if ("".equals(name))
			name = ip.getMember().getName();

		return facesContext.getExternalContext().getRequestParameterMap().get(name);
	}

	@Produces
	@BooleanParam
	Boolean getBooleanRequestParameter(InjectionPoint ip) {
		String name = ip.getAnnotated().getAnnotation(BooleanParam.class).value();

		if ("".equals(name))
			name = ip.getMember().getName();

		String value = facesContext.getExternalContext().getRequestParameterMap().get(name);
		if (Strings.isNullOrEmpty(value)) {
			return false;
		}
		return Boolean.parseBoolean(value);
	}

}