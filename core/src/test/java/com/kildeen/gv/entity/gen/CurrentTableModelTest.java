package com.kildeen.gv.entity.gen;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import liquibase.change.Change;

import org.junit.Before;
import org.junit.Test;

import com.kildeen.gv.entity.EntityConfiguration;
import com.kildeen.gv.entity.EntityConfigurationHandler;

public class CurrentTableModelTest {
	EntityConfigurationHandler handler = EntityConfigurationHandler.getInstance();
	private CurrentTableModel model;
	LiquibaseMock mock = new LiquibaseMock();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void should_only_collect_column_configs_not_other_changes() throws Exception {
		EntityConfiguration<?> vote = handler.getByTableName("Vote");
		List<Change> changes =  mock.newList().createTable().createSequence().addColumn().getList();
		model = new CurrentTableModel(vote, changes);
		assertThat(model.getColumnConfigs()).hasSize(2);
	}
	
	@Test
	public void create_index_should_not_add_columns() throws Exception {
		EntityConfiguration<?> vote = handler.getByTableName("Vote");
		List<Change> changes = mock.newList().createTable().createIndex().addColumn().getList();
		model = new CurrentTableModel(vote, changes);
		assertThat(model.getColumnConfigs()).hasSize(2);
	}

}
