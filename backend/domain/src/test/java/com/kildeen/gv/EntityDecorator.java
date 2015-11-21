package com.kildeen.gv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A helper class that makes the {@link TheKnowledge} more flexible. This class
 * allows any test case to run decorate each entities before the test by
 * assigning the functions in this class to more useful decorators. The
 * functions declared as default does nothing.
 * 
 * 
 * 
 * @author Kalle
 *
 */
public class EntityDecorator {

	protected Map<Class<?>, List<DecoratorFunction<?, ?>>> decorators = new HashMap<>();
	private List<EntityDecoratorExtension> decoratorList = new ArrayList<>();

	EntityDecorator() {

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void decorate(Object entity) {

		List<DecoratorFunction<?, ?>> currentDecorators = get(entity.getClass());

		for (DecoratorFunction fnc : currentDecorators) {
			fnc.decorate((Object) entity);
		}

	}

	private List<DecoratorFunction<?, ?>> get(Class<?> entity) {
		return decorators.computeIfAbsent(entity, k -> new ArrayList<>());
	}

	public void add(DecoratorFunction<?, ?> fnc, Class<?>... entityClazzez) {
		for (Class<?> c : entityClazzez) {
			get(c).add(fnc);
		}
	}

	public void add(DecoratorFunction<?, ?> fnc) {
		decorators.values().forEach(l -> l.add(fnc));
	}

	public void addDecorator(EntityDecoratorExtension decorator, Class<?>[] forEntites) {

		for (Class<?> clazzToDecorate : forEntites) {
			get(clazzToDecorate).addAll(decorator.registerDecoratorFunctions());
		}
		this.decoratorList.add(decorator);
		decorator.registerDecoratorFunctions();
	}

	public void runAfterDecorates(TheKnowledge tk) {
		decoratorList.forEach(d -> d.afterDecorate(tk));
	}

	public void runBeforeDecorates(EntityDecorator entityDecorator) {
		decoratorList.forEach(d -> d.beforeDecorate(entityDecorator));

	}

}
