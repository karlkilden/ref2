package com.kildeen.gv;

import java.util.List;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

import org.apache.commons.collections4.map.LRUMap;
import org.apache.deltaspike.data.api.QueryResult;

@Stateless
@Asynchronous
public class QueryResultPreLoader {

	public void preLoad(QueryResult<?> queryResult, LRUMap<Integer, List<?>> lruCache, int nextPage) {
		lruCache.put(nextPage, queryResult.toPage(nextPage).getResultList());
	}

}
