package com.kildeen.gv.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.solr.client.solrj.SolrQuery;

import com.kildeen.gv.DomainEntity;
import com.kildeen.gv.poll.PollDTO;
import com.kildeen.gv.poll.VoteDTO;

public class SolrSearchDefaults {

	private static final Map<Class<?>, SolrQuery> BASE_QUERIES = setupDefaults();

	SolrSearchDefaults(List<EntityConfiguration<? extends DomainEntity>> configs) {
		verifySolrConfig(configs);
	}

	private void verifySolrConfig(List<EntityConfiguration<? extends DomainEntity>> configs) {
		configs.stream().filter(ec -> ec.hasSolr()).forEach(ec -> requireBaseQuery(ec));

	}

	private void requireBaseQuery(EntityConfiguration<? extends DomainEntity> ec) {
		Objects.requireNonNull(BASE_QUERIES.get((Object) ec.getDTOClass()), "Missing solr search default for: " + ec.getClazz().getSimpleName());
	}

	private static Map<Class<?>, SolrQuery> setupDefaults() {
		Map<Class<?>, SolrQuery> baseQueries = new HashMap<>();
		polls(baseQueries);
		votes(baseQueries);

		return baseQueries;
	}

	private static void polls(Map<Class<?>, SolrQuery> baseQueries) {
		SolrQuery polls = new SolrQuery();
		polls.setRequestHandler("/browse");
		baseQueries.put(PollDTO.class, polls);
	}

	private static void votes(Map<Class<?>, SolrQuery> baseQueries) {
		SolrQuery votes = new SolrQuery();
		votes.setRequestHandler("/browse");
		baseQueries.put(VoteDTO.class, votes);
	}

	public final SolrQuery getDefault(Class<?> dtoType) {
		return BASE_QUERIES.get(dtoType);
	}

}
