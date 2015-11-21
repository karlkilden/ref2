package com.kildeen.gv;

import java.util.List;
import java.util.Map;

import com.kildeen.gv.auth.User;
import com.kildeen.gv.vote.Poll;
import com.kildeen.gv.vote.Vote;

/**
 * @author Karl Kilden
 * 
 *         Test helper that knows all about the domain model
 *
 */
public class TheKnowledge {
	public Poll beer;
	public Poll milk;
	public Vote beerYes;
	public User kk;
	
	
	/**
	 *  Use {@link EntityCollectorDecorator} to setup this map
	 */
	public Map<Class<?>, List<Object>> perType;

	
	TheKnowledge() {

	}

}
