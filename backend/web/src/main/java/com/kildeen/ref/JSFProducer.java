package com.kildeen.ref;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

/**

 * @author: Karl Kilden
 */
@ApplicationScoped
public class JSFProducer implements Serializable {

	private static final long serialVersionUID = 1L;


	@Produces
	@RequestScoped
	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
}
