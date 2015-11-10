package com.kildeen.gv;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections4.map.LRUMap;
import org.apache.deltaspike.data.api.QueryResult;

@ViewScoped
@Named
public class QueryResultBean implements Serializable {
	
	@Inject
	private QueryResultPreLoader queryResultPreLoader;
	
	private int pageCount;
	private int page = 0;
	private QueryResult<?> queryResult;
	private List<?> entities;
	private ArrayList<Integer> pages;
	private int pagesToCache;
	private LRUMap<Integer, List<?>> pageCache;
	private boolean preload = true;

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public void setup(QueryResult<?> queryResult, int pagesToCacheVal) {
		this.pagesToCache = pagesToCacheVal;
		this.queryResult = queryResult;
		this.pageCount = queryResult.countPages();
		this.pages = new ArrayList<>(pageCount);

		for (int i = 0; i < pageCount; i++) {
			pages.add(i + 1);
		}

		this.entities = queryResult.getResultList();
		this.pageCache = new LRUMap<Integer, List<?>>(pagesToCache);
		if (preload) {
			this.queryResultPreLoader.preLoad(queryResult, pageCache, page+1);
		}
	}

	public void switchPage(int newPage) {
		this.page = newPage;
		queryResult.toPage(this.page);
		
		if (pagesToCache > 0) {
			entities = pageCache.get(page);
			if (entities == null) {
				entities = queryResult.getResultList();
				pageCache.put(page, entities);
			}
		}
		else {
			entities = queryResult.getResultList();			
		}
		
		if (preload) {
			this.queryResultPreLoader.preLoad(queryResult, pageCache, page+1);
		}
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<?> getEntities() {
		return entities;
	}

	public void setEntities(List<?> entities) {
		this.entities = entities;
	}

	public ArrayList<Integer> getPages() {
		return pages;
	}

	public void setPages(ArrayList<Integer> pages) {
		this.pages = pages;
	}

	public String getStyleClass(int iteratedPageIndex) {
		if (iteratedPageIndex == page) {
			return "active";
		}
		return "";
	}

	public int getPagesToCache() {
		return pagesToCache;
	}

	public void setPagesToCache(int pagesToCache) {
		this.pagesToCache = pagesToCache;
	}

}
