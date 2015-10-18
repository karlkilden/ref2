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
import com.kildeen.ref.BaseEntity;

public class TheKnowledgeBuilder {

	EntityDecorator entityDecorator = new EntityDecorator();

	Map<Class<? extends BaseEntity>, List<Class<? extends BaseEntity>>> prerequisites = new HashMap<>();
	Map<Class<? extends BaseEntity>, Supplier<Boolean>> entityInitMethods = new HashMap<>();

	private TheKnowledge theKnowledge = new TheKnowledge();

	private List<Class<? extends BaseEntity>> generateIdsFor;

	private List<Class<? extends BaseEntity>> activatedEntities;

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
	private final void setupEntity(Class<? extends BaseEntity> key, Supplier<Boolean> setupMethod, Class<? extends BaseEntity>... currenPrerequisites) {
		List<Class<? extends BaseEntity>> fullPrerequisites = new ArrayList<>();
		for (Class<? extends BaseEntity> clazz : currenPrerequisites) {
			fullPrerequisites.add(clazz);
		}
		prerequisites.put(key, fullPrerequisites);
		fullPrerequisites.add(key);
		entityInitMethods.put(key, setupMethod);
	}

	private boolean polls() {
		theKnowledge.beer = entityDecorator.pollDecorator.apply(PollBuilder.getInstance().name("beer").build());
		theKnowledge.milk = entityDecorator.pollDecorator.apply(PollBuilder.getInstance().name("food").build());
		theKnowledge.beverages = Lists.newArrayList(theKnowledge.beer, theKnowledge.milk);
		theKnowledge.polls = new ArrayList<>();
		theKnowledge.polls.addAll(theKnowledge.beverages);
		return false;
	}

	private boolean users() {
		theKnowledge.kk = entityDecorator.userDecorator.apply(UserBuilder.getInstance().name("kalle").build());
		theKnowledge.users = Lists.newArrayList(theKnowledge.kk);
		return false;
	}

	private boolean votes() {
		theKnowledge.beerYes = entityDecorator.voteDecorator.apply(VoteBuilder.getInstance().poll(theKnowledge.beer).answer(AnswerType.YES)
				.addPoints().user(theKnowledge.kk).build());
		theKnowledge.beverageVotes = Lists.newArrayList(theKnowledge.beerYes);
		theKnowledge.votes = new ArrayList<>();
		theKnowledge.votes.addAll(theKnowledge.beverageVotes);

		return false;
	}

	public static TheKnowledgeBuilder getInstance() {
		return new TheKnowledgeBuilder();
	}

	public TheKnowledgeBuilder decorator(EntityDecorator decoratorHelp) {
		this.entityDecorator = decoratorHelp;
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
	public TheKnowledgeBuilder with(List<Class<? extends BaseEntity>> entities) {
		this.activatedEntities = entities;
		Set<Class<? extends BaseEntity>> alreadyInitiated = new HashSet<>();
		for (Class<? extends BaseEntity> s : entities) {
			List<Class<? extends BaseEntity>> pre = prerequisites.get(s);
			pre.stream().filter(c -> !alreadyInitiated.contains(c)).forEach(c -> entityInitMethods.get(c).get());
		}
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
	@SafeVarargs
	public final TheKnowledgeBuilder with(Class<? extends BaseEntity>... entities) {

		List<Class<? extends BaseEntity>> all = new ArrayList<>();

		for (Class<? extends BaseEntity> clazz : entities) {
			all.add(clazz);
		}
		return with(all);

	}

	public TheKnowledgeBuilder generateIds(List<Class<? extends BaseEntity>> entities) {
		this.generateIdsFor = entities;
		return this;
	}

	@SafeVarargs
	public final TheKnowledgeBuilder generateIds(Class<? extends BaseEntity>... entities) {
		return generateIds(Arrays.asList(entities));
	}

	public TheKnowledge build() {

		theKnowledge.entities = new ArrayList<>();
		addEntities(theKnowledge.polls);
		addEntities(theKnowledge.votes);
		addEntities(theKnowledge.users);

		if (activated(generateIdsFor)) {
			doGenerateIds();
		}
		return theKnowledge;
	}

	private boolean activated(List<Class<? extends BaseEntity>> entites) {
		return entites != null;
	}

	public TheKnowledgeBuilder generateIds() {
		generateIdsFor = Lists.newArrayList(activatedEntities);
		return this;
	}

	private void doGenerateIds() {
		for (BaseEntity e : theKnowledge.entities) {
			e.setId(RandomUtils.nextLong(1, 99999999));
		}
	}

	private void addEntities(List<? extends BaseEntity> entities) {
		if (entities != null) {
			theKnowledge.entities.addAll(entities);
		}
	}

}