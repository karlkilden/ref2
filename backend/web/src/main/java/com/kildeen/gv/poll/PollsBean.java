package com.kildeen.gv.poll;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.apache.deltaspike.data.api.QueryResult;

import com.kildeen.gv.QueryResultBean;
import com.kildeen.gv.vote.Poll;

@Model
public class PollsBean {

	@Inject
	private PollService pollService;
	
	@Inject
	private QueryResultBean queryResultBean;
	private List<Poll> polls;
	
	private QueryResult<Poll> pollRes;
	
	public List<Poll> getPolls() {
		return polls;
	}

	public void init() {
		pollRes = pollService.fetchPollResult();
		pollRes.maxResults(10);
		queryResultBean.setup(pollRes, 2);
	}

	
}
