package com.kildeen.gv;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import com.kildeen.gv.vote.Poll;
import com.kildeen.ref.BaseEntity;

/**
 * @author Karl Kilden
 * 
 *         Test helper that knows all about the domain model
 *
 */
public class TheKnowledge {

	public Poll beverage;
	public Poll food;
	public Poll beef;

	public Poll beer;
	public Poll hamburgers;

	private TheKnowledge() {

	}

	public static class TheKnowledgeBuilder {

		DecoratorHelp d = new DecoratorHelp();

		Map<Class<? extends BaseEntity>, List<Class<? extends BaseEntity>>> prerequisites = new HashMap<>();
		Map<Class<? extends BaseEntity>, Supplier<Boolean>> map = new HashMap<>();

		private TheKnowledge theKnowledge = new TheKnowledge();

		private TheKnowledgeBuilder() {
			map.put(Poll.class, this::polls);
			prerequisites.put(Poll.class, Arrays.asList(Poll.class));
		}

		private boolean polls() {
			theKnowledge.beer = d.pollDecorator.apply(PollBuilder.getInstance().name("Beer").build());

			return false;

		}


		public static TheKnowledgeBuilder getInstance() {
			return new TheKnowledgeBuilder();
		}

		public TheKnowledgeBuilder decorator(DecoratorHelp decoratorHelp) {
			this.d = decoratorHelp;
			return this;

		}

		public TheKnowledgeBuilder with(List<Class<? extends BaseEntity>> entities) {
			Set<Class<? extends BaseEntity>> alreadyInitiated = new HashSet<>();
			for (Class<? extends BaseEntity> s : entities) {
				List<Class<? extends BaseEntity>> pre = prerequisites.get(s);
				pre.stream().filter(c -> !alreadyInitiated.contains(c)).forEach(c -> map.get(c).get());

			}
			return this;

		}

		public static class PollBuilder {

			Poll poll = new Poll();

			private PollBuilder() {

			}

			public Poll build() {
				return poll;
			}

			public static PollBuilder getInstance() {
				return new PollBuilder();
			}

			public PollBuilder name(String name) {
				poll.setName(name);
				return this;
			}

			public PollBuilder explain(String explain) {
				poll.setExplain(explain);
				return this;
			}

			public PollBuilder question(String question) {
				poll.setQuestion(question);
				return this;
			}

			public PollBuilder link(String link) {
				poll.setLink(link);
				return this;
			}
		}

		public TheKnowledge build() {
			return theKnowledge;
		}

	}

}
