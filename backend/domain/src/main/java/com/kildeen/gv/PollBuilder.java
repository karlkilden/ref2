package com.kildeen.gv;

import com.kildeen.gv.vote.Poll;

public class PollBuilder {

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