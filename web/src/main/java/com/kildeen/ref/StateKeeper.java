package com.kildeen.ref;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
@ViewScoped
public class StateKeeper implements Serializable {
	@Inject
	private JsfRequestContext jsfRequestContext;
	
	private Set<Class<?>> beans = new HashSet<>();
	List<NameValuePair> params = new ArrayList<>();

	public void addBean(Class<?> bean) {
		this.beans.add(bean);
	}

	public void addValue(String property, Object value) {
		params.add(new BasicNameValuePair(property, value.toString()));
	}

	public Set<Class<?>> getBeans() {
		return beans;
	}

	public void addState() {
		jsfRequestContext.addParams(params);
	}
}
