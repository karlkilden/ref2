package com.kildeen.gv.search;

import org.apache.solr.client.solrj.SolrQuery;

public class QueryBuilder {
	private static final String JOIN_TEMPLATE_START = "{!join from=";
	private static final String TO = " to=";
	private static final String JOIN_TEMPLATE_END = "}";
	private SolrQuery q = new SolrQuery();
	private StringBuilder sb;

	private QueryBuilder() {

	}

	public static final QueryBuilder query() {
		return new QueryBuilder();
	}

	public static final QueryBuilder advancedQuery() {
		QueryBuilder b = new QueryBuilder();
		b.sb = new StringBuilder();
		return b;
	}

	public QueryBuilder fq(String field, String value) {
		q.addFilterQuery(field + ":" + value);
		return this;
	}

	public QueryBuilder fq(String field, boolean value) {
		return fq(field, String.valueOf(value));
	}

	public SolrQuery build() {
		return q;
	}

	public SolrQuery buildAdvanced() {
		q.addFilterQuery(sb.toString());

		return q;
	}

	public QueryBuilder paranthesis() {
		sb.append("(");
		return this;
	}
	
	public QueryBuilder endParanthesis() {
		sb.append(")");
		return this;
	}
	
	public QueryBuilder negate() {
		sb.append("-");
		return this;
	}

	public QueryBuilder join(String field, String otherField) {
		sb.append(JOIN_TEMPLATE_START).append(field).append(TO).append(otherField).append(JOIN_TEMPLATE_END);
		return this;
	}

	public QueryBuilder condition(String field, String value) {
		sb.append(field+":"+value);
		return this;
	}

	public QueryBuilder and() {
		sb.append(" AND ");
		return this;
	}

}
