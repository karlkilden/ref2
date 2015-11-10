package com.kildeen.gv.entity.gen;

public class ColumnDefinition {
	ColumnType type;
	String columnName;
	String defaultValue;
	boolean nullable;
	boolean fk;
	boolean pk;

	public ColumnType getType() {
		return type;
	}

	public String getColumnName() {
		return columnName;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public boolean isNullable() {
		return nullable;
	}

	public boolean isFk() {
		return fk;
	}

	public boolean isPk() {
		return pk;
	}

}
