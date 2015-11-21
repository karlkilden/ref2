package com.kildeen.gv;

import java.util.ArrayList;
import java.util.List;

import com.kildeen.gv.poll.TestHelp;

public class PersistenceDecorator extends EntityDecoratorExtension {
	

	@Override
	public List<DecoratorFunction<?, ?>> registerDecoratorFunctions() {
		List<DecoratorFunction<?, ?>> fncs = new ArrayList<>();
		fncs.add(TestHelp::persistEntity);
		return fncs;
	}

}
