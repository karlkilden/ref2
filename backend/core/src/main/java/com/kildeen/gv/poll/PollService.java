package com.kildeen.gv.poll;

import java.util.List;

import javax.inject.Inject;

import org.apache.deltaspike.data.api.QueryResult;
import org.apache.solr.client.solrj.SolrQuery;

import com.kildeen.gv.SolrDAO;
import com.kildeen.gv.SolrResult;
import com.kildeen.gv.ee.Service;
import com.kildeen.gv.vote.Poll;

@Service
public class PollService {

	@Inject
	private PollRepo repo;

	@Inject
	private SolrDAO solrDAO;

	@SolrResult
	public Poll save(Poll poll) {
		return repo.save(poll);
	}
	
	@SolrResult
	public Poll create(String name, String question, boolean meta) {
		Poll p = new Poll();
		p.setName(name);
		p.setQuestion(question);
		p.setMeta(meta);
		return repo.save(p);
		
	}

	public Poll fetch(Poll poll) {
		return repo.findBy(poll.getId());
	}

	public List<Poll> fetchAll() {
		return repo.fetchAll();
	}
	
	public List<Poll> fetchMeta() {
		return repo.fetchAllMeta();
	}

	public List<PollDTO> solrAll() {

		SolrQuery solrQuery = new SolrQuery();

		return solrDAO.query(solrQuery, PollDTO.class).getBeans(PollDTO.class);
	}

	public Poll fetch(long id) {
		return repo.findBy(id);
	}

	public List<PollDTO> solr(SolrQuery q, String userName) {
		return solrDAO.query(q, PollDTO.class).getBeans(PollDTO.class);

	}

	public QueryResult<Poll> fetchPollResult() {
		return repo.fetchPollResult();
	}
	


}
