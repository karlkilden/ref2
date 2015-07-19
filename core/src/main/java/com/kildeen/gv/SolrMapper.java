package com.kildeen.gv;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;

import com.kildeen.gv.poll.PollDTO;
import com.kildeen.gv.poll.VoteDTO;
import com.kildeen.gv.vote.Poll;
import com.kildeen.gv.vote.Vote;

@ApplicationScoped
public class SolrMapper {

	@Inject
	SolrPostQueue solrPostQueue;

	private Map<Class<?>, Function<Object, Object>> dtoMapper = new HashMap<>();

	@PostConstruct
	private void init() {
		dtoMapper.put(Poll.class, PollDTO::get);
		dtoMapper.put(Vote.class, VoteDTO::get);
	}

	public void queue(Object methodResult) {
		if (Collection.class.isAssignableFrom(methodResult.getClass())) {
			@SuppressWarnings("rawtypes")
			Collection col = (Collection) methodResult;
			if (CollectionUtils.isNotEmpty(col)) {
				Function<Object, Object> mapper = dtoMapper.get(col.stream().findAny().get().getClass());
				for (Object entity : col) {
					queue(entity, mapper);
				}
			}
		} else {
			queue(methodResult, dtoMapper.get(methodResult.getClass()));
		}
	}

	private void queue(Object entity, Function<Object, Object> mapper) {
		Object o = mapper.apply(entity);
		solrPostQueue.add(o);
	}

}
