package com.kildeen.gv;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.kildeen.gv.poll.PollService;

@Stateless
@Asynchronous
public class AsyncPollService {

	@Inject
	private PollService pollService;

	@Inject
	SolrMapper solrMapper;

	public void postAll() {
		solrMapper.queue(pollService.fetchAll());
	}
}
