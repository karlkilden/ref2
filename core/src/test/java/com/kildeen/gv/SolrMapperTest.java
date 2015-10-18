package com.kildeen.gv;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.kildeen.gv.entity.EntityConfigurationHandler;
import com.kildeen.gv.entity.EntityConfigurationHandlerMock;
import com.kildeen.gv.vote.Poll;

@RunWith(MockitoJUnitRunner.class)
public class SolrMapperTest {
	
	TheKnowledge tk = TheKnowledgeBuilder.getInstance().with(Poll.class).build();
	
	@Spy
	private EntityConfigurationHandler entityConfigurationHandler = new EntityConfigurationHandlerMock();

	@Mock
	private SolrPostQueue solrPostQueue;
	@InjectMocks
	private SolrMapper solrMapper;

	@Test(expected = NullPointerException.class)
	public void not_an_entity_configured_for_solr_should_throw_exception() throws Exception {
		solrMapper.queue(new Object());
	}
	

	@Test
	public void poll_should_be_added_as_dtos_to_queue() throws Exception {
		solrMapper.queue(tk.beer);
		Mockito.verify(solrPostQueue).add(Mockito.any()); 
	}
	
	@Test
	public void polls_should_be_added_as_dtos_to_queue() throws Exception {
		solrMapper.queue(tk.beverages);
		Mockito.verify(solrPostQueue, Mockito.times(2)).add(Mockito.any()); 
	}

}
