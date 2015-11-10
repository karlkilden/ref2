package com.kildeen.ref;

import java.io.IOException;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.core.api.config.view.metadata.ConfigDescriptor;
import org.apache.deltaspike.core.api.config.view.metadata.ViewConfigResolver;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.omnifaces.util.Ajax;
import org.omnifaces.util.FacesLocal;

@RequestScoped
public class JsfRequestContext {

	@Inject
	private RequestParamAppender requestParamAppender;

	@Inject
	private FacesContext facesContext;

	@Inject
	private ViewConfigResolver viewConfigResolver;

	public String getParam(String key) {
		return FacesLocal.getRequestParameter(facesContext, key);
	}

	public boolean getBooleanParam(String key) {
		return Boolean.parseBoolean(FacesLocal.getRequestParameter(facesContext, key));
	}

	public Long getLongParam(String key) {
		return Long.parseLong(FacesLocal.getRequestParameter(facesContext, key));
	}

	private int getIntParam(String key) {
		return Integer.parseInt(FacesLocal.getRequestParameter(facesContext, key));

	}

	public void onComplete(String script) {
		Ajax.oncomplete(script);
	}

	public void updateColumn(UIData data, int index) {
		Ajax.updateColumn(data, index);
	}

	public void destroySession() {
		FacesLocal.getSession(facesContext).invalidate();
	}

	public Flash getFlashScope() {
		return FacesLocal.getFlash(facesContext);
	}

	public void addParams(List<NameValuePair> params) {
		String viewId = getViewId().substring(0, getViewId().indexOf("."));
		requestParamAppender.append(FacesLocal.getRequest(facesContext).getContextPath() + viewId, params);
	}

	public String getViewId() {
		return facesContext.getViewRoot().getViewId();
	}

	public void redirect(String url) throws IOException {
		FacesLocal.redirect(facesContext, url);
	}

	@SuppressWarnings("unchecked")
	public Class<? extends ViewConfig> getViewConfigClass() {
		return (Class<? extends ViewConfig>) viewConfigResolver.getConfigDescriptor(getViewId()).getConfigClass();
	}

	public String getStateClass(Class<? extends ViewConfig> clazz) {
		ConfigDescriptor<?> descriptor = viewConfigResolver.getConfigDescriptor(clazz);
		if (descriptor.getPath().equals(getViewId())) {
			return "active";
		} else {
			return "";
		}
	}


	private BasicNameValuePair getPageParam(int page) {
		return new BasicNameValuePair("page", String.valueOf(page));
	}

	public int getPage() {
		int page = 0;
		String pageParam = getParam("page");
		if (pageParam != null) {
			page = Integer.parseInt(pageParam);
		}
		return page;
	}

	public BasicNameValuePair incrementAndGetPage() {
		int page = getPage()+1;
		return getPageParam(page+1);
	}

	public void addParams(BasicNameValuePair basicNameValuePair, BasicNameValuePair basicNameValuePair2, BasicNameValuePair pageParam) {
		// TODO Auto-generated method stub
		
	}

}