package com.kildeen.gv.trace;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Trace extends InfraEntity {

	@ManyToOne
	private Task task;

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
	
}
