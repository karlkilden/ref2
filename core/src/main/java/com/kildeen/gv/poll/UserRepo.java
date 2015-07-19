package com.kildeen.gv.poll;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import com.kildeen.gv.auth.User;

@Repository

public interface UserRepo extends EntityRepository<User, Long> {

	User findOptionalByNameEqual(String username);

}
