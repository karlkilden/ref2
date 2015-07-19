package com.kildeen.gv.nav;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.config.view.ViewConfig;

import com.kildeen.ref.JsfRequestContext;

@Model
public class NavBean {

	@Inject
	private JsfRequestContext jsfRequestContext;

	@Inject
	NavBuilder builder;

	public List<Class<? extends ViewConfig>> getPages() {
		return builder.getPages();
	}

	public String getClass(Class<? extends ViewConfig> clazz) {
		return jsfRequestContext.getStateClass(clazz);
	}

	public String getCurrentView() {

		return jsfRequestContext.getViewConfigClass().getSimpleName();
	}
	
	public List<Class<? extends ViewConfig>> getTabs() {
		return builder.getTabs();
	}

}
