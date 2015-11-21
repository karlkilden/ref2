package com.kildeen.gv.entity.gen;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EntityToLiquibaseHandlerTest {

	@Before
	public void setUp() throws Exception {
	}

	@Mock
	private TableModelHandler tableModelHandler;

	@Mock
	private EntityModelHandler entityModelHandler;

	@Mock
	private DiffHandler diffHandler;

	@Mock
	private LiquibaseChangeSetWriter liquibaseChangeSetWriter;

	@InjectMocks
	private EntityToLiquibaseHandler entityToLiquibaseHandler;

	@Test
	public void tables_should_be_modeled_then_entites_then_diffed_then_new_logs_generated() throws Exception {

		entityToLiquibaseHandler.execute();
		verify(tableModelHandler).mapTables();
		verify(entityModelHandler).mapEntities();
		verify(diffHandler.diff(any(), Mockito.any()));
		verify(liquibaseChangeSetWriter).writeDiff(any());
	}

}
