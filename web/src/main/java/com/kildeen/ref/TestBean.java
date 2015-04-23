package com.kildeen.ref;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.view.ViewScoped;

import org.apache.deltaspike.core.api.config.view.ViewRef;
import org.apache.deltaspike.core.api.config.view.controller.PreRenderView;

import com.kildeen.ref.nav.F;

@ViewRef(config = { F.Cat.Category.class })
@ViewScoped
@State
public class TestBean implements Serializable {

	private String test;

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}
	
	@State
	public void testSave() {
	System.out.println("keso");	
	}
	
	@PostConstruct
	private void iwoke() {
		System.out.println("asdf");
	}
	
}
