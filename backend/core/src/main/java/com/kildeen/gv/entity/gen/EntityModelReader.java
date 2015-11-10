package com.kildeen.gv.entity.gen;

import static com.google.common.base.MoreObjects.firstNonNull;

import java.lang.reflect.Field;

import javax.persistence.Column;

import com.google.common.base.Strings;

public class EntityModelReader {

	public String getColumnName(Field field) {
		String idColumnName;

		Column columnAnno = getColumn(field);
		idColumnName = getColumnName(field, columnAnno);

		return idColumnName;
	}

	public String getColumnName(Field field, Column columnAnno) {
		String nameFromAnoo = Strings.emptyToNull(columnAnno == null ? null : columnAnno.name());
		return firstNonNull(nameFromAnoo, field.getName());
	}

	public Column getColumn(Field field) {
		Column columnAnno = field.getAnnotation(Column.class);
		return columnAnno;
	}
}
