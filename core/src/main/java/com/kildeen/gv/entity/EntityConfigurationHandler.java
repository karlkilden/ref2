package com.kildeen.gv.entity;

import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.apache.solr.client.solrj.SolrQuery;

import com.google.common.collect.Lists;
import com.kildeen.gv.DomainEntity;
import com.kildeen.gv.poll.PollDTO;
import com.kildeen.gv.poll.VoteDTO;
import com.kildeen.gv.vote.Poll;
import com.kildeen.gv.vote.Vote;

public class EntityConfigurationHandler {

	private static final EntityConfigurationHandler INSTANCE = new EntityConfigurationHandler();
	private EntityConfiguration<Poll> pollConfig = EntityConfiguration.create(Poll.class, PollDTO.class).mappingMethod(PollDTO::get).restIn()
			.restOut().solr().build();
	private EntityConfiguration<Vote> voteConfig = EntityConfiguration.create(Vote.class, VoteDTO.class).mappingMethod(VoteDTO::get).restIn()
			.restOut().solr().build();

	List<EntityConfiguration<?>> configs = Lists.newArrayList(pollConfig, voteConfig);
	private Map<Class<?>, Function<Object, Object>> dtoMapper = new HashMap<>();
	private Map<String, EntityConfiguration<?>> tableNameToEntity = new HashMap<>();
	private SolrSearchDefaults solrDefaults = new SolrSearchDefaults(configs);
	private Map<String, TableNameOwnerHistory> ownerHistory = new HashMap<>();

	private EntityConfigurationHandler() {
		mapHowToCreateDtos();
		mapTableNames();
		mapPreviousTableNameOwners();
	}

	private void mapPreviousTableNameOwners() {
		// ownerHistory.put("Vote", ne)
	}

	public static EntityConfigurationHandler getInstance() {
		return INSTANCE;
	}

	private void mapTableNames() {
		for (EntityConfiguration<?> conf : configs) {
			tableNameToEntity.put(conf.getTableName(), conf);
		}
	}

	void mapHowToCreateDtos() {
		configs.stream().filter(ec -> ec.hasDTO()).forEach(ec -> dtoMapper.put(ec.getDefiningClass(), ec.getMappingMethod()));
	}

	public Object getDTO(DomainEntity entity) {
		return dtoMapper.get(entity.getClass()).apply(entity);

	}

	/**
	 * This method should be used when multiple mappings are done in a loop. It
	 * then performs better than {@link #getDTO(DomainEntity)}
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

	public List<EntityConfiguration<?>> getAll() {
		return configs;
	}

	public EntityConfiguration<?> getByCurrentOrPreviousTableName(String id, String tableName) {

		TableNameOwnerHistory history = ownerHistory.get(tableName);

		if (history != null) {
			return history.getOwner(id);
		}

		return tableNameToEntity.get(tableName);
	}
	
	
	public EntityConfiguration<?> getByTableName(String tableName) {
		return tableNameToEntity.get(tableName);
	}

	public List<EntityConfiguration<?>> getSolrEnabled() {
		return configs.stream().filter(ec -> ec.hasSolr()).collect(toList());
	}

	public EntityConfiguration<?> getByClass(Class<?> clazz) {
		Optional<EntityConfiguration<?>> conf = doFind(clazz);
		return conf.orElseThrow(() -> new NullPointerException(String.format("%s is not configured", clazz.getName())));
	}

	private Optional<EntityConfiguration<?>> doFind(Class<?> clazz) {
		Optional<EntityConfiguration<?>> conf = configs.stream().filter(ec -> ec.getDefiningClass() == clazz).findFirst();
		return conf;
	}

	public Object findByClass(Class<?> clazz) {
		Optional<EntityConfiguration<?>> conf = doFind(clazz);
		return conf.orElse(null);

	}

}
