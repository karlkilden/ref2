package com.kildeen.gv.entity.gen;

import java.util.List;

public class GenModelBuilder {

	private GenModelBuilder() {
	}

	public static GenModelBuilder getInstance() {
		return new GenModelBuilder();
	}

	private GenModel genModel = new GenModel();

	public GenModelBuilder columnDefinitions(List<ColumnDefinition> columnDefinitions) {
		this.genModel.columnDefinitions = columnDefinitions;
		return this;
	}

	public GenModelBuilder hasChangeSet(boolean hasChangeSet) {
		this.genModel.hasChangeSet = hasChangeSet;
		return this;
	}

	public GenModelBuilder entityClass(String entityClass) {
		this.genModel.entityClass = entityClass;
		return this;
	}

	public GenModel build() {
		return this.genModel;
	}

}
