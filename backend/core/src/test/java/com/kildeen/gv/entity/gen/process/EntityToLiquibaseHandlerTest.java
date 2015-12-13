package com.kildeen.gv.entity.gen.process;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.kildeen.gv.entity.gen.DiffHandler;
import com.kildeen.gv.entity.gen.EntityModelHandler;
import com.kildeen.gv.entity.gen.SchemaWriteHandler;
import com.kildeen.gv.entity.gen.TableModelHandler;

import com.kildeen.gv.entity.gen.process.EntityToLiquibaseHandler;

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
	private SchemaWriteHandler liquibaseChangeSetWriter;

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
