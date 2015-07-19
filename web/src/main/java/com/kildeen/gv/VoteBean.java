package com.kildeen.gv;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import com.kildeen.gv.vote.Vote;
import com.kildeen.ref.UserContext;

@Model
public class VoteBean {
	
	@Inject
	private UserContext userContext;

	private Vote vote;

	public Vote getVote() {
		return vote;
	}

	public void setVote(Vote vote) {
		this.vote = vote;
	}
	
	@PostConstruct
	private void setup() {
		this.vote.setUser(userContext.getUser());
	}
	
}
