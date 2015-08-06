package com.kildeen.gv;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;

@ApplicationScoped
public class SolrDAO {

	protected SolrClient client;

	@Inject
	private Event<ExceptionEvent> exceptionEvent;

	public QueryResponse query(SolrQuery solrQuery, Class<?> dtoType) {
		SolrQuery defaults = SolrSearchDefaults.getDefault(dtoType);

		for (String param : defaults.getParameterNames()) {
			solrQuery.add(param, defaults.getParams(param));
		}
		QueryResponse resp = null;
		try {
			resp = client.query(solrQuery);
		} catch (Exception e) {
			exceptionEvent.fire(new ExceptionEvent(e));
		}
		return resp;
	}

	public void post(Collection<?> dtos) {
		if (CollectionUtils.isEmpty(dtos)) {
			return;
		}
		try {
			client.addBeans(dtos);
			client.commit();
		} catch (Exception e) {
			exceptionEvent.fire(new ExceptionEvent(e));
		}
	}

	@PostConstruct
	void init() {
		client = new HttpSolrClient("http://localhost:8983/solr/gv");
	}

}
