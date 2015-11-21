package com.kildeen.gv.poll;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

public class ReflectionUtils {

	private ReflectionUtils() {
	}

	public static Class<?> getGenericTypeFromCollection(final Field field) {
		final ParameterizedType genericType = (ParameterizedType) field.getGenericType();
		return (Class<?>) genericType.getActualTypeArguments()[0];
	}
	
	public static Field findField(String name, Class<?> clazz) throws IllegalAccessException {
		for (Field f : org.apache.deltaspike.core.util.ReflectionUtils.getAllDeclaredFields(clazz)) {
			if (f.getName().equals(name)) {
				f.setAccessible(true);
				return f;
			}
		}
		return null;
	}
}