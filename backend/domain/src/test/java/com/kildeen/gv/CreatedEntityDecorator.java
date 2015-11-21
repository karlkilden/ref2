package com.kildeen.gv;

import java.util.ArrayList;
import java.util.List;

public class CreatedEntityDecorator extends EntityDecoratorExtension {

	@Override
	public List<DecoratorFunction<?, ?>> registerDecoratorFunctions() {
		
		List<DecoratorFunction<?, ?>> fncs = new ArrayList<>();
		fncs.add(this::logEntity);
		return fncs;
	}

	public Object logEntity(Object entity) {
		System.out.println(entity);
		return entity;
	}

}
