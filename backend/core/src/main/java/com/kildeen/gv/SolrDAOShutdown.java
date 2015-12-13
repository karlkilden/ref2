package com.kildeen.gv;

import java.io.IOException;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.deltaspike.core.api.exclude.Exclude;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;

import com.kildeen.gv.entity.EntityConfigurationContext;
import com.kildeen.gv.modular.FeatureControlExpression;

@ApplicationScoped
@Exclude(onExpression = "jobs||solr", interpretedBy = FeatureControlExpression.class)
@Specializes
public class SolrDAOShutdown extends SolrDAO {

	@Override
	public QueryResponse query(SolrQuery solrQuery, Class<?> dtoType) {
		return null;
	}

	@Override
	public void post(Collection<?> dtos) {

	}

	@Override
	public void post(Object dto) {

	}

}
