package com.kildeen.gv.poll;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import com.kildeen.gv.vote.Poll;

@Model
public class PollsBean {

	@Inject
	private PollService pollService;
	private List<Poll> polls;
	
	public List<Poll> getPolls() {
		return polls;
	}

	@PostConstruct
	private void extracted() {
		polls = pollService.fetchAll();
	}
	
}
