package com.kildeen.gv;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

@Singleton
public class SolrPostQueue {

	@Inject
	private SolrDAO solrDAO;

	@SuppressWarnings("rawtypes")
	private Queue dtos = new ConcurrentLinkedDeque<>();

	@Schedule(hour = "*", minute = "*", second = "10")
	@Lock(LockType.WRITE)
	public void writeJournalMessages() {
		Queue<?> postCopy;
		postCopy = dtos;
		dtos = new ConcurrentLinkedDeque<>();
		solrDAO.post(postCopy);
	}

	@SuppressWarnings("unchecked")
	@Lock(LockType.READ)
	public void add(Object dto) {
		dtos.add(dto);
	}

}
