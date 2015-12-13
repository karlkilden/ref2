package com.kildeen.gv;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;

import org.apache.deltaspike.core.api.exclude.Exclude;

import com.kildeen.gv.modular.FeatureControlExpression;


@ApplicationScoped
@Exclude(onExpression = "solr", interpretedBy = FeatureControlExpression.class)
@Specializes
public class SolrMapperShutdown extends SolrMapper {

	@Override
	public void queue(Object methodResult) {

	}

}
