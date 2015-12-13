package com.kildeen.gv.poll;

import static com.kildeen.gv.search.QueryBuilder.query;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/vote")
@Produces({ "text/xml", "application/json" })
@Consumes({ "text/plain", "text/xml", "application/json" })
public class VoteProducer {
	@Inject
	private VoteService voteService;

	@Path("/create")
	@PUT
	public void create(@QueryParam("pollId") long pollId, @QueryParam("answer") String answer, @QueryParam("username") String username) {
		voteService.create(pollId, answer, username);
	}

	@Path("/list/")
	@GET
	public List<VoteDTO> list(@QueryParam("userName") String userName) {
		return voteService.solr(query().build(), userName);
	}

}
