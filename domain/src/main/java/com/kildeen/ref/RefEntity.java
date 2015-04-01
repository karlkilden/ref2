package com.kildeen.ref;

import javax.persistence.Entity;

@Entity
public class RefEntity extends BaseEntity {

	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
