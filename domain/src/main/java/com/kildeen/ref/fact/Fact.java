package com.kildeen.ref.fact;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.apache.bval.constraints.NotEmpty;

import com.kildeen.gv.DomainEntity;

@Entity
public class Fact extends DomainEntity {

	@NotEmpty
	@NotNull
	@Column(unique = true)
	private String fact;
	
	
	private Category category;

	public String getFact() {
		return fact;
	}

	public void setFact(String fact) {
		this.fact = fact;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
