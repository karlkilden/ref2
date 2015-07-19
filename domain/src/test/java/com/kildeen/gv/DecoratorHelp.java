package com.kildeen.gv;

import java.util.function.Function;

import com.kildeen.gv.vote.Poll;

public class DecoratorHelp {

	public Function<Poll, Poll> pollDecorator = (t -> t);

}
