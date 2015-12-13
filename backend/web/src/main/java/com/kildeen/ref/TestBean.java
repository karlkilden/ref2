package com.kildeen.ref;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

import org.omnifaces.util.Faces;

@ViewScoped

public class TestBean implements Serializable {

	private String test;

	public String getTest() {
		
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}
	
	public void testSave() {
	System.out.println("keso");	
	}
	
	public void download() throws IOException {
		Faces.sendFile("tjenare".getBytes(), "hi", true);
	}
	
	@PostConstruct
	private void iwoke() {
		System.out.println("asdf");
	}
	
}
