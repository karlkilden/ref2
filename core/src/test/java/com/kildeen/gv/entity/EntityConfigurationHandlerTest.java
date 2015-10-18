package com.kildeen.gv.entity;

import static org.junit.Assert.*;

import java.util.function.Function;

import org.junit.Test;

import com.kildeen.gv.TheKnowledge;
import com.kildeen.gv.TheKnowledgeBuilder;
import com.kildeen.gv.poll.VoteDTO;
import com.kildeen.gv.vote.Vote;

public class EntityConfigurationHandlerTest {

	private TheKnowledge tk = TheKnowledgeBuilder.getInstance().with(Vote.class).generateIds().build();

	@Test
	public void get_dto_funtion_should_return_correct_dto_function() throws Exception {
		EntityConfigurationHandler handler = new EntityConfigurationHandler();

		handler.mapHowToCreateDtos();

		Function<Object, Object> f = handler.getDTOMapper(Vote.class);

		VoteDTO dto = (VoteDTO) f.apply(tk.beerYes);
		assertNotNull(dto);
	}
	
	@Test
	public void converting_a_vote() throws Exception {
		EntityConfigurationHandler handler = new EntityConfigurationHandler();

		handler.mapHowToCreateDtos();

		Function<Object, Object> f = handler.getDTOMapper(Vote.class);

		VoteDTO dto = (VoteDTO) f.apply(tk.beerYes);
		VoteDTO dto2 = (VoteDTO) handler.getDTO(tk.beerYes);
		
		assertEquals(dto.getUserId(),dto2.getUserId());
	}
}
