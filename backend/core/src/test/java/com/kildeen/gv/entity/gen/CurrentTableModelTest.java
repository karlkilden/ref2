package com.kildeen.gv.entity.gen;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;

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
		List<Change> changes = mock.newList().createTable("Vote", 1).createTable("Vote", 1).getList();
		model = new CurrentTableModel(vote, changes);
		assertThat(model.getData().getColumnConfigs()).hasSize(2);
	}

	@Test
	public void create_index_should_not_add_columns() throws Exception {
		EntityConfiguration<?> vote = handler.getByTableName("Vote");
		List<Change> changes = mock.newList().createTable("Vote", 1).addColumn().getList();
		model = new CurrentTableModel(vote, changes);
		assertThat(model.getData().getColumnConfigs()).hasSize(2);
	}

	@Test
	public void test_that_pk_gets_registered() throws Exception {
		List<Change> changes = mock.newList().createPK("Vote", "const", 1).getList();
		EntityConfiguration<?> vote = handler.getByTableName("Vote");
		model = new CurrentTableModel(vote, changes);
		assertThat(model.getData().getPks()).hasSize(1);
	}
	
	@Test
	public void test_that_pk_gets_removed() throws Exception {
		List<Change> changes = mock.newList().createPK("Vote", "const", 1).dropPK("Vote", "const", 1).createPK("Vote", "newConst", 1).getList();
		EntityConfiguration<?> vote = handler.getByTableName("Vote");
		model = new CurrentTableModel(vote, changes);
		assertThat(model.getData().getPks()).hasSize(1);
	}
	
	@Test
	public void test_that_multiple_pk_cause_illegalStateException() throws Exception {
		List<Change> changes = mock.newList().createPK("Vote", "const", 1).createPK("Vote", "newConst", 1).getList();
		EntityConfiguration<?> vote = handler.getByTableName("Vote");
		model = new CurrentTableModel(vote, changes);
		assertThat(model.getData().getPks()).hasSize(1);
	}

	@Test
	public void test_that_fk_gets_registered() throws Exception {
		List<Change> changes = mock.newList().createFk("Vote", "poll_id", 1).getList();
		EntityConfiguration<?> vote = handler.getByTableName("Vote");
		model = new CurrentTableModel(vote, changes);
		assertThat(model.getData().getFks()).isNotEmpty();
	}
	
	@Test
	public void test_that_fk_gets_removed() throws Exception {
		List<Change> changes = mock.newList().createFk("Vote", "poll_id", 1).createFk("Vote", "new_poll_id", 1).dropFk("Vote", "poll_id", 1).getList();
		EntityConfiguration<?> vote = handler.getByTableName("Vote");
		model = new CurrentTableModel(vote, changes);
		assertThat(model.getData().getFks()).hasSize(1);
	}

	@Test
	public void test_that_index_gets_registered() throws Exception {
		List<Change> changes = mock.newList().createIndex("Vote", 1).getList();
		EntityConfiguration<?> vote = handler.getByTableName("Vote");
		model = new CurrentTableModel(vote, changes);
		assertThat(model.getData().getIndexes()).isNotEmpty();
	}
	
	@Test
	public void test_that_index_gets_renameCols_gets_registered() throws Exception {
		List<Change> changes = mock.newList().renameColumn("Vote", "oldName", "name", 1).getList();
		EntityConfiguration<?> vote = handler.getByTableName("Vote");
		model = new CurrentTableModel(vote, changes);
		assertThat(model.getData().getColumnConfigs().stream().map(c -> c.getName()).collect(Collectors.toList())).asList().contains("name");
		assertThat(model.getData().getColumnConfigs().stream().map(c -> c.getName()).collect(Collectors.toList())).asList().doesNotContain("oldName");
	}

}
