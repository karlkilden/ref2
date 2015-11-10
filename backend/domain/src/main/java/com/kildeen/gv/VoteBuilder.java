package com.kildeen.gv;

import java.util.Objects;

import com.kildeen.gv.auth.User;
import com.kildeen.gv.vote.AnswerType;
import com.kildeen.gv.vote.Poll;
import com.kildeen.gv.vote.Vote;

public class VoteBuilder {

	Vote vote = new Vote();

	private VoteBuilder() {

	}

	public Vote build() {
		Objects.requireNonNull(vote.getPoll(), "poll cannot be null");
		Objects.requireNonNull(vote.getUser(), "user cannot be null");
		return vote;
	}

	public static VoteBuilder getInstance() {
		return new VoteBuilder();
	}

	public VoteBuilder poll(Poll poll) {
		vote.setPoll(poll);
		return this;
	}
	
	public VoteBuilder user(User user) {
		vote.setUser(user);
		return this;
	}

	public VoteBuilder answer(AnswerType answer) {
		vote.setAnswer(answer);
		return this;
	}

	public VoteBuilder addPoints() {
		vote.addPoints();
		return this;
	}

	@Override
	public String toString() {
		return vote.toString();
	}

}