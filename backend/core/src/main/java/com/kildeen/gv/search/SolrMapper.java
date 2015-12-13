package com.kildeen.gv.search;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import com.kildeen.gv.entity.EntityConfiguration;
import com.kildeen.gv.entity.EntityConfigurationContext;

/**
 * This class can take any entity listed as {@link EntityConfiguration#solr()} as an object and adds it to to {@link SolrPostQueue}
 * @author Kalle
 *
 */
@ApplicationScoped
public class SolrMapper {

	@Inject
	SolrPostQueue solrPostQueue;

	@Inject
	EntityConfigurationContext entityConfigurationHandler;

	@PostConstruct
	private void init() {

	}

	public void queue(Object methodResult) {
		if (Collection.class.isAssignableFrom(methodResult.getClass())) {
			@SuppressWarnings("rawtypes")
			Collection col = (Collection) methodResult;
			if (CollectionUtils.isNotEmpty(col)) {
				Object firstEntity = col.stream().findAny().get();
				Function<Object, Object> mapper = getMapper(firstEntity);
				for (Object entity : col) {
					queue(entity, mapper);
				}
			}
		} else {
			queue(methodResult, getMapper(methodResult));
		}
	}

	private Function<Object, Object> getMapper(Object firstEntity) {
		Class<? extends Object> entityClazz = firstEntity.getClass();
		Function<Object, Object> dtoMapper = entityConfigurationHandler.getDTOMapper(entityClazz);
		Objects.requireNonNull(dtoMapper, "this class is not configured for solr: "+ entityClazz);
		return dtoMapper;
	}

	private void queue(Object entity, Function<Object, Object> mapper) {
		Object dto = mapper.apply(entity);
		solrPostQueue.add(dto);
	}

}
