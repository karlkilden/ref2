package com.kildeen.gv.entity.gen;

public class ColumnDefinitionBuilder {

	private ColumnDefinitionBuilder() {
	}

	public static ColumnDefinitionBuilder getInstance() {
		return new ColumnDefinitionBuilder();
	}

	private ColumnDefinition columnDefinition = new ColumnDefinition();

	public ColumnDefinitionBuilder nullable(boolean nullable) {
		this.columnDefinition.nullable = nullable;
		return this;
	}

	public ColumnDefinitionBuilder fk(boolean fk) {
		this.columnDefinition.fk = fk;
		return this;
	}

	public ColumnDefinitionBuilder defaultValue(String defaultValue) {
		this.columnDefinition.defaultValue = defaultValue;
		return this;
	}

	public ColumnDefinitionBuilder pk(boolean pk) {
		this.columnDefinition.pk = pk;
		return this;
	}

	public ColumnDefinitionBuilder type(ColumnType type) {
		this.columnDefinition.type = type;
		return this;
	}

	public ColumnDefinitionBuilder columnName(String columnName) {
		this.columnDefinition.columnName = columnName;
		return this;
	}

	public ColumnDefinition build() {
		return this.columnDefinition;
	}

}
