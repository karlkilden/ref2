package com.kildeen.gv;

import java.util.List;

public abstract class EntityDecoratorExtension {

	public abstract List<DecoratorFunction<?, ?>> registerDecoratorFunctions();

	public void afterDecorate(TheKnowledge tk) {
	};

	public void beforeDecorate(EntityDecorator decorator) {
	};
}
