package com.kildeen.ref.fact;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.apache.http.message.BasicNameValuePair;

import com.kildeen.ref.JsfRequestContext;

@Model
public class FactBean {

	@Inject
	private FactService factService;
	
	@Inject
	private JsfRequestContext jsfRequestContext;
	
	@Inject
	private CategoryService categoryService;
	
	private String state;
	
	private List<Category> categories;

	private Fact fact = new Fact();

	public void save() {
//		jsfRequestContext.addParams(new BasicNameValuePair("state", "4"));
		
	}

	public Fact getFact() {
		return fact;
	}

	public void setFact(Fact fact) {
		this.fact = fact;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<Category> getCategories() {
		return categories;
	}
	
	@PostConstruct
	private void setup() {
		categories = categoryService.fetchAll();
	}
}
