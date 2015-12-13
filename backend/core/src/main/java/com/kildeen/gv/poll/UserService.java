package com.kildeen.gv.poll;

import javax.inject.Inject;

import com.kildeen.gv.auth.User;
import com.kildeen.gv.ee.Service;

@Service
public class UserService {

	@Inject
	private UserRepo repo;

	public User save(User user) {
		return repo.save(user);
	}

	public User fetch(User user) {
		return repo.findOptionalByNameEqual(user.getName());
	}

	public User fetch(String username) {
		return repo.findOptionalByNameEqual(username);
	}

	public User fetchOrCreate(String username) {
		User user = fetch(username);
		return user == null ? save(new User(username)) : user;
	}

}
