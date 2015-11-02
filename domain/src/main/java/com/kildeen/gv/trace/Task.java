package com.kildeen.gv.trace;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.kildeen.gv.auth.User;

@Entity
public class Task extends InfraEntity {

	private String name;
	@ManyToOne
	private User startedBy;

	@OneToMany(mappedBy="task")
	private List<Trace> traces;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
