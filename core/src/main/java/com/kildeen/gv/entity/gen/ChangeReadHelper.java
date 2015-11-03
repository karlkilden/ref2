package com.kildeen.gv.entity.gen;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import liquibase.change.Change;
import liquibase.change.ColumnConfig;

import org.apache.xbean.recipe.Option;
import org.apache.xbean.recipe.ReflectionUtil;
import org.eclipse.persistence.queries.ReadObjectQuery;

public class ChangeReadHelper {
	private static Method getMethod(Change change, String propertyName) {
		return ReflectionUtil.findGetter(change.getClass(), propertyName, EnumSet.of(Option.PRIVATE_PROPERTIES));
	}

	public static Optional<String> getTableName(Change change, String... propertyNames) throws Exception {
		String tableName = (String) readObject(change, propertyNames);
		return Optional.ofNullable(tableName);
	}

	private static Object readObject(Change change, String... propertyNames) throws Exception {
		for (String s : propertyNames) {
			Method m = getMethod(change, s);

			if (m != null) {
				return m.invoke(change, new Object[0]);
			}
		}
		return null;
	}

	public static Optional<List<ColumnConfig>> getColumnConfigs(Change change) throws Exception {
		@SuppressWarnings("unchecked")
		List<ColumnConfig> columns = (List<ColumnConfig>) readObject(change, "columns");
		return Optional.ofNullable(columns);
	}
}
