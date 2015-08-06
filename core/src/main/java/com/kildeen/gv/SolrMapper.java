package com.kildeen.gv;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.kildeen.gv.poll.PollDTO;
import com.kildeen.gv.poll.VoteDTO;
import com.kildeen.gv.vote.Poll;
import com.kildeen.gv.vote.Vote;

@ApplicationScoped
public class SolrMapper {

	private static final int ENTITY_MAPPER_COUNT = 2;

	@Inject
	private SolrPostQueue solrPostQueue;

	private Map<Class<?>, Function<Object, Object>> dtoMapper = new HashMap<>(ENTITY_MAPPER_COUNT);

	@PostConstruct
	void init() {
		dtoMapper.put(Poll.class, PollDTO::get);
		dtoMapper.put(Vote.class, VoteDTO::get);
	}

	public void queue(Object entityReturnValue) {

		if (entityReturnValue == null) {
			return;
		}

		if (Collection.class.isAssignableFrom(entityReturnValue.getClass())) {
			@SuppressWarnings("rawtypes")
			Collection col = (Collection) entityReturnValue;
			if (!col.isEmpty()) {
				Function<Object, Object> mapper = dtoMapper.get(col.stream().findAny().get().getClass());
				for (Object entity : col) {
					queue(entity, mapper);
				}
			}
		} else {
			queue(entityReturnValue, dtoMapper.get(entityReturnValue.getClass()));
		}
	}

	private void queue(Object entity, Function<Object, Object> mapper) {
		Object o = mapper.apply(entity);
		solrPostQueue.add(o);
	}

}
