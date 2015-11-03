package com.kildeen.gv.poll;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import com.kildeen.gv.DomainEntity;
import com.kildeen.gv.PersistenceDecorator;
import com.kildeen.gv.TheKnowledge;
import com.kildeen.gv.TheKnowledgeBuilder;

public class TestHelp<E extends DomainEntity> implements TestRule {
	@Inject
	private EntityManager entityManager;
	@Inject
	private TransactionHelp transactionHelp;
	private Class<E> clazz;

	public static <E extends DomainEntity> TestHelp<E> getInstance(Class<E> clazz) {
		TestHelp<E> help = new TestHelp<>();
		help.clazz = clazz;
		BeanProvider.injectFields(help);
		return help;
	}

	public int getCurrentCount() {
		String selectMethod = "SELECT e FROM %s e";
		entityManager = BeanProvider.getContextualReference(EntityManager.class);
		Query query = entityManager.createQuery(String.format(selectMethod, clazz.getSimpleName()));
		int currentCount = query.getResultList().size();
		return currentCount;
	}

	public void assertCountIncreaseWith(int i) {
		int currentCount = getCurrentCount();
		assertEquals(i, currentCount);
	}

	@SuppressWarnings("unchecked")
	public E persist(E entity) {
		if (transactionHelp == null) {
			BeanProvider.injectFields(this);
		}
		return (E) transactionHelp.persist(entity);
	}
	
	public static DomainEntity persistEntity(DomainEntity entity) {
		return (DomainEntity) BeanProvider.getContextualReference(TransactionHelp.class).persist(entity);
	}

	public void persistAll(Object... entities) {
		for (Object o : entities) {
			transactionHelp.persist(o);
		}
	}

	@Override
	public Statement apply(Statement base, Description desc) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				try {
					base.evaluate();
				} finally {
					transactionHelp.delete();
				}
			}
		};
	}

	public TheKnowledge getKnowledge(List<Class<? extends DomainEntity>> entities) {
		List<Class<? extends DomainEntity>> all = new ArrayList<>(entities);
		all.add(clazz);

		return TheKnowledgeBuilder.getInstance().decorator(new PersistenceDecorator()).with(all).build();
	}

	public TheKnowledge getKnowledge() {
		return getKnowledge(newArrayList());
	}

}
