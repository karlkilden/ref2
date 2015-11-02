package com.kildeen.gv.entity;

import java.util.function.Function;

import javax.enterprise.context.ApplicationScoped;

import org.apache.solr.client.solrj.SolrQuery;

import com.kildeen.gv.DomainEntity;

@ApplicationScoped
public class EntityConfigurationContext {
	
	private EntityConfigurationHandler entityConfigurationHandler = EntityConfigurationHandler.getInstance();

	public SolrQuery getDefaultQuery(Class<?> dtoType) {
		return entityConfigurationHandler.getDefaultQuery(dtoType);
	}

	public Object getDTO(DomainEntity entity) {
		return entityConfigurationHandler.getDTO(entity);
	}

	public Function<Object, Object> getDTOMapper(Class<? extends Object> entityClazz) {
		return entityConfigurationHandler.getDTOMapper(entityClazz);
	}



}
