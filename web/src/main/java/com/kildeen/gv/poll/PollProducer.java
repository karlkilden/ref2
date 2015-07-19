package com.kildeen.gv.poll;

import static com.kildeen.gv.QueryBuilder.query;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.solr.client.solrj.SolrQuery;

import com.kildeen.gv.QueryBuilder;
import com.kildeen.gv.auth.User;
import com.kildeen.gv.vote.Poll_;

@Path("/poll")
@Produces({ "text/xml", "application/json" })
@Consumes({ "text/plain", "text/xml", "application/json" })
public class PollProducer {
	@Inject
	private PollService pollService;
	
	@Inject
	private UserService userService;

	@Path("/create")
	@PUT
	public void create(@QueryParam("name") String name, @QueryParam("question") String question, @QueryParam("meta") boolean meta) {
		pollService.create(name, question, meta);
	}

	@Path("/list/")
	@GET
	public List<PollDTO> list(@QueryParam("username") String username) {
		User u = userService.fetch(username);
		
		SolrQuery query = QueryBuilder
		.advancedQuery()
		.paranthesis()
		.negate()
		.join(VoteDTO.POLL_ID, VoteDTO.DATABASE_ID)
		.condition(VoteDTO.ANSWER, "*")
		.and()
		.negate()
		.condition(VoteDTO.USER_ID, String.valueOf(u.getId()))
		.endParanthesis()
		.buildAdvanced();
				
		return pollService.solr(query, username);
	}

	@Path("/list/meta")
	@GET
	public List<PollDTO> listMeta(@QueryParam("userName") String userName) {

		return pollService.solr(query().fq(PollDTO.META, true).build(), userName);
	}
	
	@Path("/list/meta2")
	@GET
	public List<PollDTO> listMeta2() {
		
		return pollService.fetchMeta().stream().map(p -> new PollDTO(p)).collect(Collectors.toList());
	}

}
