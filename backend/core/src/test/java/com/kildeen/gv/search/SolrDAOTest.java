package com.kildeen.gv.search;

import static org.mockito.Mockito.verify;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.kildeen.gv.poll.PollDTO;
import com.kildeen.gv.search.SolrDAO;

@RunWith(MockitoJUnitRunner.class)
public class SolrDAOTest {
	@Mock
	private SolrClient client;
	@InjectMocks
	private SolrDAO solrDAO;

	@Test
	public void testName() throws Exception {
		SolrQuery query = new SolrQuery();
		solrDAO.query(query, PollDTO.class);
		verify(client).query(query);
	}

}
