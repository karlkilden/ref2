package com.kildeen.gv;

import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;
import org.apache.deltaspike.core.api.exclude.Exclude;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
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
