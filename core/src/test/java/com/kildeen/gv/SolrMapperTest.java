package com.kildeen.gv;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.kildeen.gv.poll.PollDTO;
import com.kildeen.gv.vote.Poll;

@RunWith(MockitoJUnitRunner.class)
public class SolrMapperTest {
	@Mock
	private SolrPostQueue solrPostQueue;
	@InjectMocks
	private SolrMapper solrMapper;

	@Before
	public void init() {
		solrMapper.init();
	}

	@Test
	public void single_entity_should_get_queued() throws Exception {
		Poll p = new Poll();
		solrMapper.queue(p);
		verify(solrPostQueue).add(new PollDTO(p));
	}
	
	@Test
	public void collection_of_entity_should_get_queued() throws Exception {
		Poll p = new Poll();
		solrMapper.queue(Lists.newArrayList(p));
		verify(solrPostQueue).add(new PollDTO(p));
	}

}
