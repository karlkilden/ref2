package com.kildeen.gv;

import java.util.List;

import com.kildeen.gv.auth.User;
import com.kildeen.gv.vote.Poll;
import com.kildeen.gv.vote.Vote;
import com.kildeen.ref.BaseEntity;

/**
 * @author Karl Kilden
 * 
 *         Test helper that knows all about the domain model
 *
 */
public class TheKnowledge {

	public List<Poll> beverages;
	public List<Poll> polls;
	public Poll beer;
	public Poll milk;
	public Vote beerYes;
	public List<Vote> beverageVotes;
	public List<Vote> votes;

	public User kk;
	public List<User> users;
	
	public List<BaseEntity> entities;


	TheKnowledge() {

	}

}
