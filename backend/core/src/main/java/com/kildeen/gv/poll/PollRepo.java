package com.kildeen.gv.poll;

import java.util.List;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

import com.kildeen.gv.vote.Poll;

@Repository
public abstract class PollRepo implements CriteriaSupport<Poll>, EntityRepository<Poll, Long> {

	public List<Poll> fetchAll() {
		return findAll();
	}

	public List<Poll> fetchAllMeta() {
		return null;
	}

	@Query(value = "select p from Poll p")
	public abstract QueryResult<Poll> fetchPollResult();
}
