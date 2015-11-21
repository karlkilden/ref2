package com.kildeen.gv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityCollectorDecorator extends EntityDecoratorExtension {

	private Map<Class<?>, List<Object>> perType = new HashMap<>();
	
	
	@Override
	public List<DecoratorFunction<?, ?>> registerDecoratorFunctions() {
		List<DecoratorFunction<?, ?>> fncs = new ArrayList<>();
		fncs.add(this::collectEntity);
		return fncs;
	}
	
	@Override
	public void afterDecorate(TheKnowledge tk) {
		tk.perType = perType;
	}

	public Object collectEntity(Object entity) {
		perType.computeIfAbsent(entity.getClass(), key -> new ArrayList<>()).add(entity);
		return entity;
	}
	
	
}
