package com.kildeen.gv.entity.gen;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Map;

import liquibase.changelog.DatabaseChangeLog;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.kildeen.gv.entity.EntityConfigurationHandler;
import com.kildeen.gv.entity.gen.TableModelHandler;

@RunWith(MockitoJUnitRunner.class)
public class TableModelHandlerTest {

	@InjectMocks
	private TableModelHandler changeSetBuilder;
	@Mock
	LiquibaseReadHelper liquibaseHelper;
	
	LiquibaseMock mock = new LiquibaseMock();

	private EntityConfigurationHandler entityConfigurationHandler =  EntityConfigurationHandler.getInstance();
	@Before
	public void before() throws Exception {

		changeSetBuilder.clearCache();
		mock.createIndex("Vote", "", 0).createTable("Poll", 1).record();
		when(liquibaseHelper.get()).thenReturn(mock.get());
	}

	@Test
	public void getChangeLogs_should_return_one_changelog_per_new_entity() throws Exception {
		DatabaseChangeLog log = mock.get();
		when(liquibaseHelper.get()).thenReturn(log);
		changeSetBuilder.mapTables();
		Map<Class<?>, CurrentTableModel> changeSetsMap = changeSetBuilder.mapTables();
		assertThat(changeSetsMap).hasSize(entityConfigurationHandler.getAll().size());

	}

}
