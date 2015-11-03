package com.kildeen.gv.entity.gen;

import java.util.List;

public class GenModel {
	String entityClass;
	boolean hasChangeSet;
	List<ColumnDefinition> columnDefinitions;

	public String getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}

	public boolean isHasChangeSet() {
		return hasChangeSet;
	}

	public void setHasChangeSet(boolean hasChangeSet) {
		this.hasChangeSet = hasChangeSet;
	}

	public List<ColumnDefinition> getColumnDefinitions() {
		return columnDefinitions;
	}

	public void setColumnDefinitions(List<ColumnDefinition> columnDefinitions) {
		this.columnDefinitions = columnDefinitions;
	}

}
