package com.kildeen.ref;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class EntityManagerProducer {

	@PersistenceContext(unitName = "ref2")
	private EntityManager entityManager;

	@Produces
	protected EntityManager createEntityManager() {
		return this.entityManager;
	}
}