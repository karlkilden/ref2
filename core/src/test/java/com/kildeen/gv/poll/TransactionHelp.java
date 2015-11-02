package com.kildeen.gv.poll;

import java.sql.Connection;
import java.sql.SQLException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.deltaspike.jpa.api.transaction.Transactional;

import com.kildeen.gv.DomainEntity;

@Transactional
@ApplicationScoped
public class TransactionHelp {
	

    @Inject
    private EntityManager entityManager;

    
    public Object persist(Object entity) {
    	return this.entityManager.merge(entity);
    }
    public DomainEntity find (DomainEntity entity) {
    	return entityManager.find(entity.getClass(), entity.getId());
    }
    
	public void delete() throws SQLException {
		Connection connection = entityManager.unwrap(Connection.class);
		try (java.sql.Statement stmt = connection.createStatement()) {
		    stmt.execute("TRUNCATE SCHEMA public AND COMMIT");
		}		
	}
}
