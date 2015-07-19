package com.kildeen.gv;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.kildeen.gv.auth.App;


public class EntityManagerProducer {

	@PersistenceContext(unitName = App.APP_NAME)
	private EntityManager entityManager;

	@Produces
	protected EntityManager createEntityManager() {
		return this.entityManager;
	}
}