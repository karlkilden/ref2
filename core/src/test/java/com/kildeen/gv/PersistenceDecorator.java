package com.kildeen.gv;

import org.eclipse.persistence.internal.jpa.rs.metadata.model.Link;

import com.kildeen.gv.poll.TestHelp;
import com.kildeen.gv.vote.Poll;
import com.kildeen.gv.vote.Vote;

/**
 * This is a simple entity decorator that reuses {@link TestHelp} to persist all entities
 * 
 * @author Kalle
 *
 */
public class PersistenceDecorator {
	static TestHelp<Poll> poll = new TestHelp<>();
	static TestHelp<Vote> vote = new TestHelp<>();

	public static EntityDecorator getInstance() {
		EntityDecorator help = new EntityDecorator();
		help.pollDecorator = poll::persist;
		help.voteDecorator = vote::persist;
		return help;
	}

	private PersistenceDecorator() {
	}

}
