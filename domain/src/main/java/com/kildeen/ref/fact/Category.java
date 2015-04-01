package com.kildeen.ref.fact;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.apache.bval.constraints.NotEmpty;

import com.kildeen.ref.BaseEntity;

@Entity
public class Category extends BaseEntity {
	@NotNull
	@NotEmpty
	private String name;
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
