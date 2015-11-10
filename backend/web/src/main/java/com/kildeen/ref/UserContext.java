package com.kildeen.ref;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import com.kildeen.gv.auth.User;
import com.kildeen.gv.poll.UserService;

@SessionScoped
public class UserContext implements Serializable {

	@Inject
	private UserService userService;
	
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@PostConstruct
	void init() {
		user = userService.fetch("kalle");
	}
	
}
