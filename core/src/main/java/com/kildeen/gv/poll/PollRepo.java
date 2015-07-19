package com.kildeen.gv.poll;

import java.util.List;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

import com.kildeen.gv.vote.Poll;
import com.kildeen.gv.vote.Poll_;

@Repository
public abstract class PollRepo implements CriteriaSupport<Poll>, EntityRepository<Poll, Long> {

	public List<Poll> fetchAll() {
		return findAll();
	}
	
	public List<Poll> fetchAllMeta() {
		return criteria().eq(Poll_.meta, true).getResultList();
	}
}
