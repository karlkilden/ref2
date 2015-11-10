package com.kildeen.gv;

import com.kildeen.gv.poll.TestHelp;

public class PersistenceDecorator extends EntityDecoratorExtension {
	

	@Override
	public void beforeAbsorb() {
		add(TestHelp::persistEntity);
	}

}
