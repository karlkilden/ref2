package com.kildeen.gv;

import java.util.Objects;

import com.kildeen.gv.auth.User;
import com.kildeen.gv.vote.AnswerType;
import com.kildeen.gv.vote.Poll;

public class UserBuilder {

	User user = new User();

	private UserBuilder() {
	}

	public User build() {
		Objects.requireNonNull(user.getName(), "name cannot be null");
		return user;
	}

	public static UserBuilder getInstance() {
		return new UserBuilder();
	}

	public UserBuilder name(String name) {
		user.setName(name);
		return this;
	}
	
	@Override
	public String toString() {
		return user.toString();
	}


}