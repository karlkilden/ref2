package com.kildeen.gv;

import java.util.List;

import com.google.common.collect.Lists;
import com.kildeen.gv.auth.User;
import com.kildeen.gv.vote.Poll;
import com.kildeen.gv.vote.Vote;

public class EntityRegister {
	protected static final List<Class<?>> SUPPORTED_ENTITIES = Lists.newArrayList(Vote.class, Poll.class, User.class);

}
