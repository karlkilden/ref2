package com.kildeen.gv.ee;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.kildeen.gv.conf.App;


public class EntityManagerProducer {

	@PersistenceContext(unitName = App.APP_NAME)
	private EntityManager entityManager;

	@Produces
	protected EntityManager createEntityManager() {
		return this.entityManager;
	}
}