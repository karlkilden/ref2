package com.kildeen.gv;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import com.kildeen.gv.entity.EntityConfigurationHandler;
import com.kildeen.gv.poll.AsyncStartup;
import com.kildeen.gv.poll.PollService;
import com.kildeen.gv.poll.VoteService;

@RunWith(MockitoJUnitRunner.class)
public class AsyncStartupTest {
	@Mock
	private PollService pollService;

	@Mock
	private SolrMapper solrMapper;

	@Mock
	private VoteService voteService;
	@InjectMocks
	private AsyncStartup asyncStartup;
	
	EntityConfigurationHandler handler = EntityConfigurationHandler.getInstance();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void should_fetch_all_votes_when_posting_to_solr() throws Exception {

		asyncStartup.postAll();
		verify(voteService).fetchAll();		
	
	}
	@Test
	public void should_fetch_all_polls_when_posting_to_solr() throws Exception {
			asyncStartup.postAll();
		verify(pollService).fetchAll();		
		
	}
	
	@Test
	public void should_invoke_solrMapper_once_per_entity() throws Exception {
		asyncStartup.postAll();
		verify(solrMapper, times(handler.getSolrEnabled().size())).queue(any());;	
		
	}

}
