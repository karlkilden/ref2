package com.kildeen.gv.auth;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.kildeen.ref.BaseEntity;

@Entity
@Table(name="usr", uniqueConstraints = {@UniqueConstraint(columnNames = { "name" })})
public class User extends BaseEntity {

	public User(String name) {
		this.name = name;
	}
	
	public User() {
	}

}
