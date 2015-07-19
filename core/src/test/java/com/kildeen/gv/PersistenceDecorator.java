package com.kildeen.gv;

import com.kildeen.gv.poll.TestHelp;
import com.kildeen.gv.vote.Poll;

public class PersistenceDecorator {
	static TestHelp<Poll> poll = new TestHelp<>();

	public static DecoratorHelp getInstance() {
		DecoratorHelp help = new DecoratorHelp();
		help.pollDecorator = poll::persist;
		return help;
	}

	private PersistenceDecorator() {
	}

}
