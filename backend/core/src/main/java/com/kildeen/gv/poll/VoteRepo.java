package com.kildeen.gv.poll;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

import com.kildeen.gv.vote.Vote;

@Repository
public abstract class VoteRepo implements CriteriaSupport<Vote>, EntityRepository<Vote, Long> {

	
	public abstract Vote findOptionalByPoll_idEqualAndUser_nameEqual(long pollId, String username);
	

}
