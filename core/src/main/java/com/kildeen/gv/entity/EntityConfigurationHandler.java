package com.kildeen.gv.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.apache.solr.client.solrj.SolrQuery;

import com.google.common.collect.Lists;
import com.kildeen.gv.poll.PollDTO;
import com.kildeen.gv.poll.VoteDTO;
import com.kildeen.gv.vote.Poll;
import com.kildeen.gv.vote.Vote;
import com.kildeen.ref.BaseEntity;

@ApplicationScoped
public class EntityConfigurationHandler {

	private EntityConfiguration<Poll> pollConfig = EntityConfiguration.create(Poll.class, PollDTO.class).mappingMethod(PollDTO::get).restIn()
			.restOut().solr().build();
	private EntityConfiguration<Vote> voteConfig = EntityConfiguration.create(Vote.class, VoteDTO.class).mappingMethod(VoteDTO::get).restIn()
			.restOut().solr().build();

	@SuppressWarnings("unchecked")
	List<EntityConfiguration<? extends BaseEntity>> configs = Lists.newArrayList(pollConfig, voteConfig);
	private Map<Class<?>, Function<Object, Object>> dtoMapper = new HashMap<>();

	private SolrSearchDefaults solrDefaults = new SolrSearchDefaults(configs);

	@PostConstruct
	private void init() {
		mapHowToCreateDtos();
	}

	void mapHowToCreateDtos() {
		configs.stream().filter(ec -> ec.hasDTO()).forEach(ec -> dtoMapper.put(ec.getClazz(), ec.getMappingMethod()));
	}

	public Object getDTO(BaseEntity entity) {
		return dtoMapper.get(entity.getClass()).apply(entity);

	}

	/**
	 * This method should be used when multiple mappings are done in a loop. It
	 * then performs better than {@link #getDTO(BaseEntity)}
	 * 
	 * @param entity
	 *            class
	 * @return the function that can create a new dto
	 */
	public Function<Object, Object> getDTOMapper(Class<?> entity) {
		return dtoMapper.get(entity);
	}

	public SolrQuery getDefaultQuery(Class<?> dtoType) {
		return solrDefaults.getDefault(dtoType);
	}

}
