package com.kildeen.ref;

import javax.enterprise.context.RequestScoped;

import org.apache.deltaspike.core.api.provider.BeanProvider;

@RequestScoped
public class ConstructedTracker {
	
	private boolean constructedThisRequest;
	

	public boolean isConstructedThisRequest() {
		return constructedThisRequest;
	}

	public void setConstructedThisRequest(boolean constructedThisRequest) {
		this.constructedThisRequest = constructedThisRequest;
	}

	public void restoreState() {
		BeanProvider.getContextualReference(StateBuilder.class).restoreState();	
	}

}
