package com.kildeen.gv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import com.google.common.collect.Lists;
import com.kildeen.gv.auth.User;
import com.kildeen.gv.vote.Poll;
import com.kildeen.gv.vote.Vote;

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

	@SuppressWarnings("unchecked")
	protected static final List<Class<? extends DomainEntity>> SUPPORTED_ENTITIES = Lists.newArrayList(Vote.class, Poll.class, User.class);

	protected Map<Class<? extends DomainEntity>, List<Function<? extends DomainEntity, ? extends DomainEntity>>> decorators = new HashMap<>();

	EntityDecorator() {

		for (Class<? extends DomainEntity> entityClazz : SUPPORTED_ENTITIES) {
			decorators.put(entityClazz, new ArrayList<>());
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void decorate(DomainEntity entity) {

		List<Function<? extends DomainEntity, ? extends DomainEntity>> currentDecorators = decorators.get(entity.getClass());

		for (Function fnc : currentDecorators) {
			fnc.apply((Object) entity);
		}

	}

	public void add(Function<? extends DomainEntity, ? extends DomainEntity> fnc,
			@SuppressWarnings("unchecked") Class<? extends DomainEntity>... entityClazzez) {
		for (Class<? extends DomainEntity> c : entityClazzez) {
			decorators.get(c).add(fnc);
		}
	}

	public void add(Function<? extends DomainEntity, ? extends DomainEntity> fnc) {
		decorators.values().forEach(l -> l.add(fnc));
	}

	public void absorb(EntityDecoratorExtension decorator) {
		decorator.beforeAbsorb();
		for (Entry<Class<? extends DomainEntity>, List<Function<? extends DomainEntity, ? extends DomainEntity>>> entry : decorator.decorators.entrySet()) {
			this.decorators.get(entry.getKey()).addAll(entry.getValue());
		}
	}

}
