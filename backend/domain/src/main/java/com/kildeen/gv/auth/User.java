package com.kildeen.gv.auth;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.kildeen.gv.DomainEntity;
import com.kildeen.gv.trace.Task;

@Entity
@Table(name="usr", uniqueConstraints = {@UniqueConstraint(columnNames = { "name" })})
public class User extends DomainEntity {

	private List<Task> tasks;
	
	public User(String name) {
		this.name = name;
	}
	
	public User() {
	}

}
