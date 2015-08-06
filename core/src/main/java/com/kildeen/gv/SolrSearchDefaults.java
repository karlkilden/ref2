package com.kildeen.gv;

import java.util.HashMap;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;

import com.kildeen.gv.poll.PollDTO;

public class SolrSearchDefaults {

	private static final Map<Class<?>, SolrQuery> BASE_QUERIES = setupDefaults();

	private SolrSearchDefaults() {

	}

	private static Map<Class<?>, SolrQuery> setupDefaults() {
		SolrQuery polls = new SolrQuery();
		Map<Class<?>, SolrQuery> baseQueries = new HashMap<>();
		polls.setRequestHandler("/browse");
		baseQueries.put(PollDTO.class, polls);

		return baseQueries;
	}

	public static final SolrQuery getDefault(Class<?> dtoType) {
		return BASE_QUERIES.get(dtoType);
	}
}
