package com.kildeen.gv.entity;

import static org.junit.Assert.assertNotNull;

import java.util.function.Function;

import org.junit.Test;

import com.kildeen.gv.TheKnowledge;
import com.kildeen.gv.TheKnowledgeBuilder;
import com.kildeen.gv.poll.VoteDTO;
import com.kildeen.gv.vote.Vote;

public class EntityConfigurationHandlerTest {

	private TheKnowledge tk = TheKnowledgeBuilder.getInstance().with(Vote.class).build();

	@Test
	public void get_dto_funtion_should_return_correct_dto_function() throws Exception {
		EntityConfigurationHandler handler = new EntityConfigurationHandler();

		handler.mapHowToCreateDtos();

		Function<Object, Object> f = handler.getDTOMapper(Vote.class);

		VoteDTO dto = (VoteDTO) f.apply(tk.beerYes);
		assertNotNull(dto);
	}
}
