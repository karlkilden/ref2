package com.kildeen.gv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.kildeen.gv.auth.User;
import com.kildeen.gv.vote.AnswerType;
import com.kildeen.gv.vote.Poll;
import com.kildeen.gv.vote.Vote;

public class TheKnowledgeBuilder {

	EntityDecorator entityDecorator = new EntityDecorator();

	Map<Class<?>, List<Class<?>>> prerequisites = new HashMap<>();
	Map<Class<?>, Supplier<Boolean>> entityInitMethods = new HashMap<>();

	private TheKnowledge theKnowledge = new TheKnowledge();

	private List<Class<?>> activatedEntities;

	private List<Class<?>> classesToInit;

	/**
	 * This is a key part. This constructor directly tracks the entity
	 * dependency graph and also maps entity types to init methods
	 */
	private TheKnowledgeBuilder() {

		setupEntity(Poll.class, this::polls);
		setupEntity(Vote.class, this::votes, Poll.class, User.class);
		setupEntity(User.class, this::users, Poll.class);
	}

	@SafeVarargs
	private final void setupEntity(Class<?> key, Supplier<Boolean> setupMethod, Class<?>... currenPrerequisites) {
		List<Class<?>> fullPrerequisites = new ArrayList<>();
		for (Class<?> clazz : currenPrerequisites) {
			fullPrerequisites.add(clazz);
		}
		prerequisites.put(key, fullPrerequisites);
		fullPrerequisites.add(key);
		entityInitMethods.put(key, setupMethod);
	}

	private boolean polls() {
		theKnowledge.beer = (Poll) decorate(PollBuilder.getInstance().name("beer").build());
		theKnowledge.milk = (Poll) decorate(PollBuilder.getInstance().name("food").build());
		return false;
	}

	private DomainEntity decorate(DomainEntity build) {
		entityDecorator.decorate(build);
		return build;
	}

	private boolean users() {
		theKnowledge.kk = (User) decorate(UserBuilder.getInstance().name("kalle").build());
		return false;
	}

	private boolean votes() {
		theKnowledge.beerYes = (Vote) decorate(
				VoteBuilder.getInstance().poll(theKnowledge.beer).answer(AnswerType.YES).addPoints().user(theKnowledge.kk).build());

		return false;
	}

	public static TheKnowledgeBuilder getInstance() {
		return new TheKnowledgeBuilder();
	}

	public TheKnowledgeBuilder decorator(EntityDecoratorExtension decorator) {
		decorator(decorator, classesToInit.toArray(new Class[0]));
		return this;
	}
	
	public TheKnowledgeBuilder decorator(EntityDecoratorExtension decorator, Class<?>... forEntites) {
		this.entityDecorator.addDecorator(decorator, forEntites);
		return this;
	}

	/**
	 * This method will setup all supplied types for testing and all others will
	 * be skipped.
	 * 
	 * @param entities
	 *            that the test requires
	 * 
	 */
	public TheKnowledgeBuilder with(List<Class<?>> entities) {
		this.activatedEntities = entities;
		buildClassToInit();
		return this;
	}

	private void setup() {
		classesToInit.forEach(c -> entityInitMethods.get(c).get());
	}

	/**
	 * This method will setup all supplied types for testing and all others will
	 * be skipped.
	 * 
	 * @param entities
	 *            that the test requires
	 * 
	 */
	@SafeVarargs
	public final TheKnowledgeBuilder with(Class<?>... entities) {

		List<Class<?>> all = new ArrayList<>();

		for (Class<?> clazz : entities) {
			all.add(clazz);
		}
		return with(all);

	}

	public TheKnowledgeBuilder generateIds(List<Class<?>> entities) {
		generateIds(entities.toArray(new Class[0]));
		return this;
	}

	public TheKnowledgeBuilder generateIds() {
		generateIds(activatedEntities);
		return this;
	}

	@SafeVarargs
	public final TheKnowledgeBuilder generateIds(Class<?>... entities) {
		decorator(new GenerateIdsDecorator(entities));
		return this;
	}

	public TheKnowledge build() {
		entityDecorator.runBeforeDecorates(entityDecorator);
		setup();
		entityDecorator.runAfterDecorates(theKnowledge);
		return theKnowledge;
	}

	private void buildClassToInit() {
		classesToInit = new ArrayList<>();
		for (Class<?> s : activatedEntities) {
			List<Class<?>> pre = prerequisites.get(s);
			for (Class<?> entityClazz : pre) {
				if (!classesToInit.contains(entityClazz)) {
					classesToInit.add(entityClazz);
				}
			}
		}
	}

	public TheKnowledgeBuilder collect() {
		decorator(new EntityCollectorDecorator());
		return this;
	}

}