package com.kildeen.gv.search;

import static org.junit.Assert.*;

import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Test;

import com.kildeen.gv.poll.VoteDTO;
import com.kildeen.gv.search.QueryBuilder;


public class QueryBuilderTest {

	
	@Test
	public void test_build_dynamic_join() throws Exception {
		SolrQuery query = QueryBuilder
		.advancedQuery()
		.negate()
		.paranthesis()
		.join(VoteDTO.POLL_ID, VoteDTO.DATABASE_ID)
		.condition(VoteDTO.ANSWER, "*")
		.and()
		.negate()
		.condition(VoteDTO.USER_ID, String.valueOf(3))
		.endParanthesis()
		.buildAdvanced();
		
		String fq = query.getFilterQueries()[0];
		assertEquals("-({!join from=pollId to=databaseId}answer:* AND -userId:3)", fq);
	}
}
