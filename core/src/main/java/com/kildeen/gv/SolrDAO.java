package com.kildeen.gv;

import java.io.IOException;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;

import com.kildeen.gv.entity.EntityConfigurationHandler;

@ApplicationScoped
public class SolrDAO {

	protected SolrClient client;
	
	@Inject
	EntityConfigurationHandler entityConfHandler;

	public QueryResponse query(SolrQuery solrQuery, Class<?> dtoType) {
		SolrQuery defaults = entityConfHandler.getDefaultQuery(dtoType);

		for (String param : defaults.getParameterNames()) {
			solrQuery.add(param, defaults.getParams(param));
		}
		QueryResponse resp = null;
		try {
			resp = client.query(solrQuery);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	public void post(Collection<?> dtos) {
		if (CollectionUtils.isEmpty(dtos)) {
			return;
		}
		try {
			client.addBeans(dtos);
			UpdateResponse resp = client.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void post(Object dto) {
		try {
			client.addBean(dto);
			UpdateResponse resp = client.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@PostConstruct
	private void init() {
		setup();
	}

	protected void setup() {
		client = new HttpSolrClient("http://localhost:8983/solr/gv");
	}

}
