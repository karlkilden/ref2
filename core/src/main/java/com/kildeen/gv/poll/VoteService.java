package com.kildeen.gv.poll;

import java.util.List;

import javax.inject.Inject;

import org.apache.solr.client.solrj.SolrQuery;

import com.kildeen.gv.Service;
import com.kildeen.gv.SolrDAO;
import com.kildeen.gv.SolrResult;
import com.kildeen.gv.vote.AnswerType;
import com.kildeen.gv.vote.Poll;
import com.kildeen.gv.vote.Vote;

@Service
public class VoteService {

	@Inject
	private VoteRepo repo;

	@Inject
	private PollService pollService;
	@Inject
	private UserService userService;

	@Inject
	private SolrDAO solrDAO;

	public Vote save(Vote vote) {
		return repo.save(vote);
	}

	public Vote fetch(Vote vote) {
		return repo.findBy(vote.getId());
	}

	@SolrResult
	public Vote create(long pollId, String answer, String username) {
		Vote v = repo.findOptionalByPoll_idEqualAndUser_nameEqual(pollId, username);
		if (v == null) {
			Poll p = pollService.fetch(pollId);
			if (p == null) {
				throw new IllegalArgumentException("no such poll:" + pollId);
			}
			v = new Vote();
			v.setPoll(p);
			v.setAnswer(AnswerType.valueOf(answer.toUpperCase()));
			v.setUser(userService.fetchOrCreate(username));
		}
		return repo.save(v);
	}

	public List<VoteDTO> solr(SolrQuery q, String userName) {
		return solrDAO.query(q, VoteDTO.class).getBeans(VoteDTO.class);

	}

}
