package com.kildeen.gv;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Id;

import org.apache.commons.lang3.RandomUtils;
import org.apache.deltaspike.core.util.ExceptionUtils;
import org.apache.deltaspike.core.util.ReflectionUtils;

public class GenerateIdsDecorator extends EntityDecoratorExtension {

	private Class<?>[] forEntites;

	@Override
	public List<DecoratorFunction<?, ?>> registerDecoratorFunctions() {
		List<DecoratorFunction<?, ?>> fncs = new ArrayList<>();
		fncs.add(this::logEntity);
		return fncs;
		
	}

	public GenerateIdsDecorator(Class<?>... forEntites) {
		this.forEntites = forEntites;

	}

	public Object logEntity(Object entity) {
		Optional<Field> result = ReflectionUtils.getAllDeclaredFields(entity.getClass()).stream().filter(f -> f.isAnnotationPresent(Id.class))
				.findFirst();

		if (result.isPresent()) {
			Field f = result.get();
			if (f.getType() == long.class) {
				f.setAccessible(true);
				try {
					f.set(entity, RandomUtils.nextLong(0, 99999));
				} catch (Exception e) {
					ExceptionUtils.throwAsRuntimeException(e);
				}
			}
		}

		return entity;
	}

}
