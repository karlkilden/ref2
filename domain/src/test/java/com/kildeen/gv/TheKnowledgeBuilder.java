package com.kildeen.gv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;

import com.google.common.collect.Lists;
import com.kildeen.gv.auth.User;
import com.kildeen.gv.vote.AnswerType;
import com.kildeen.gv.vote.Poll;
import com.kildeen.gv.vote.Vote;

public class TheKnowledgeBuilder {

	EntityDecorator entityDecorator = new EntityDecorator();

	Map<Class<? extends DomainEntity>, List<Class<? extends DomainEntity>>> prerequisites = new HashMap<>();
	Map<Class<? extends DomainEntity>, Supplier<Boolean>> entityInitMethods = new HashMap<>();

	private TheKnowledge theKnowledge = new TheKnowledge();

	private List<Class<? extends DomainEntity>> generateIdsFor;

	private List<Class<? extends DomainEntity>> activatedEntities;

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
	private final void setupEntity(Class<? extends DomainEntity> key, Supplier<Boolean> setupMethod, Class<? extends DomainEntity>... currenPrerequisites) {
		List<Class<? extends DomainEntity>> fullPrerequisites = new ArrayList<>();
		for (Class<? extends DomainEntity> clazz : currenPrerequisites) {
			fullPrerequisites.add(clazz);
		}
		prerequisites.put(key, fullPrerequisites);
		fullPrerequisites.add(key);
		entityInitMethods.put(key, setupMethod);
	}

	private boolean polls() {
		theKnowledge.beer = (Poll) decorate(PollBuilder.getInstance().name("beer").build());
		theKnowledge.milk = (Poll) decorate(PollBuilder.getInstance().name("food").build());
		theKnowledge.beverages = Lists.newArrayList(theKnowledge.beer, theKnowledge.milk);
		theKnowledge.polls = new ArrayList<>();
		theKnowledge.polls.addAll(theKnowledge.beverages);
		return false;
	}

	private DomainEntity decorate(DomainEntity build) {
		 entityDecorator.decorate(build);
		 return build;
	}

	private boolean users() {
		theKnowledge.kk = (User) decorate(UserBuilder.getInstance().name("kalle").build());
		theKnowledge.users = Lists.newArrayList(theKnowledge.kk);
		return false;
	}

	private boolean votes() {
		theKnowledge.beerYes = (Vote) decorate(VoteBuilder.getInstance().poll(theKnowledge.beer).answer(AnswerType.YES)
				.addPoints().user(theKnowledge.kk).build());
		theKnowledge.beverageVotes = Lists.newArrayList(theKnowledge.beerYes);
		theKnowledge.votes = new ArrayList<>();
		theKnowledge.votes.addAll(theKnowledge.beverageVotes);

		return false;
	}

	public static TheKnowledgeBuilder getInstance() {
		return new TheKnowledgeBuilder();
	}

	public TheKnowledgeBuilder decorator(EntityDecoratorExtension decorator) {
		this.entityDecorator.absorb(decorator);
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
	public TheKnowledgeBuilder with(List<Class<? extends DomainEntity>> entities) {
		this.activatedEntities = entities;
		return this;
	}

	private void setup() {
		Set<Class<? extends DomainEntity>> alreadyInitiated = new HashSet<>();
		for (Class<? extends DomainEntity> s : activatedEntities) {
			List<Class<? extends DomainEntity>> pre = prerequisites.get(s);
			pre.stream().filter(c -> !alreadyInitiated.contains(c)).forEach(c -> entityInitMethods.get(c).get());
		}
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
	public final TheKnowledgeBuilder with(Class<? extends DomainEntity>... entities) {

		List<Class<? extends DomainEntity>> all = new ArrayList<>();

		for (Class<? extends DomainEntity> clazz : entities) {
			all.add(clazz);
		}
		return with(all);

	}

	public TheKnowledgeBuilder generateIds(List<Class<? extends DomainEntity>> entities) {
		this.generateIdsFor = entities;
		return this;
	}

	@SafeVarargs
	public final TheKnowledgeBuilder generateIds(Class<? extends DomainEntity>... entities) {
		return generateIds(Arrays.asList(entities));
	}

	public TheKnowledge build() {
		setup();
		theKnowledge.entities = new ArrayList<>();
		addEntities(theKnowledge.polls);
		addEntities(theKnowledge.votes);
		addEntities(theKnowledge.users);

		if (activated(generateIdsFor)) {
			doGenerateIds();
		}
		return theKnowledge;
	}

	private boolean activated(List<Class<? extends DomainEntity>> entites) {
		return entites != null;
	}

	public TheKnowledgeBuilder generateIds() {
		generateIdsFor = Lists.newArrayList(activatedEntities);
		return this;
	}

	private void doGenerateIds() {
		for (DomainEntity e : theKnowledge.entities) {
			e.setId(RandomUtils.nextLong(1, 99999999));
		}
	}

	private void addEntities(List<? extends DomainEntity> entities) {
		if (entities != null) {
			theKnowledge.entities.addAll(entities);
		}
	}

}