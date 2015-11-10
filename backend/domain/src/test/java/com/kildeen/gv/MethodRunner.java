package com.kildeen.gv;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.deltaspike.core.util.ReflectionUtils;

public class MethodRunner {

	public static void runMethod(Class<? extends Annotation> annotationClass, Object bean) {
		
		for (Method m : ReflectionUtils.getAllDeclaredMethods(bean.getClass())) {
			if (m.isAnnotationPresent(annotationClass)) {
				m.setAccessible(true);
				try {
					m.invoke(bean, new Object[]{});
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
