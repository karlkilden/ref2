package com.kildeen.gv;

import java.util.function.Function;

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

	public Function<Poll, Poll> pollDecorator = (e -> e);
	public Function<Vote, Vote> voteDecorator = (e -> e);
	public Function<User, User> userDecorator = (e -> e);

}
