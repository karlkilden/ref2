package com.kildeen.ref.fact;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.apache.bval.constraints.NotEmpty;

import com.kildeen.ref.BaseEntity;

@Entity
public class Category extends BaseEntity {

	private String description;
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
