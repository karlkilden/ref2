package com.kildeen.gv;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.kildeen.gv.SolrMapper;
import com.kildeen.gv.poll.PollService;
import com.kildeen.gv.poll.VoteService;

@Stateless
@Asynchronous
public class AsyncStartup {

	@Inject
	private PollService pollService;
	@Inject
	private VoteService voteService;

	@Inject
	SolrMapper solrMapper;

	public void postAll() {
		solrMapper.queue(pollService.fetchAll());
		solrMapper.queue(voteService.fetchAll());
	}
}