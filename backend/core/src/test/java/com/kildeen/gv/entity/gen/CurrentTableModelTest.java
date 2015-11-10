package com.kildeen.gv.entity.gen;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.kildeen.gv.entity.EntityConfiguration;
import com.kildeen.gv.entity.EntityConfigurationHandler;

import liquibase.change.Change;

public class CurrentTableModelTest {
	EntityConfigurationHandler handler = EntityConfigurationHandler.getInstance();
	private CurrentModel model;
	LiquibaseMock mock = new LiquibaseMock();

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void create_table_adds_table() throws Exception {
		List<Change> changes = mock.newList().createTable("Vote", 1).getList();
		EntityConfiguration<?> vote = handler.getByTableName("Vote");
		model = new CurrentTableModel(vote, changes);
		assertThat(model.getData().getCreateTables()).hasSize(1);
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
		List<Change> changes = mock.newList().createTable("Vote", 1).addColumn("Vote", "name", 1).getList();
		model = new CurrentTableModel(vote, changes);
		assertThat(model.getData().getColumnConfigs()).hasSize(2);
	}

	@Test
	public void pk_gets_registered() throws Exception {
		List<Change> changes = mock.newList().createPK("Vote", "const", 1).getList();
		EntityConfiguration<?> vote = handler.getByTableName("Vote");
		model = new CurrentTableModel(vote, changes);
		assertThat(model.getData().getPks()).hasSize(1);
	}
	
	@Test
	public void pk_gets_dropped() throws Exception {
		List<Change> changes = mock.newList().createPK("Vote", "const", 1).dropPK("Vote", "const", 1).createPK("Vote", "newConst", 1).getList();
		EntityConfiguration<?> vote = handler.getByTableName("Vote");
		model = new CurrentTableModel(vote, changes);
		assertThat(model.getData().getPks()).hasSize(1);
	}
	
	@Test(expected = IllegalStateException.class)
	public void multiple_pk_cause_illegalStateException() throws Exception {
		List<Change> changes = mock.newList().createPK("Vote", "const", 1).createPK("Vote", "newConst", 1).getList();
		EntityConfiguration<?> vote = handler.getByTableName("Vote");
		model = new CurrentTableModel(vote, changes);
		assertThat(model.getData().getPks()).hasSize(1);
	}

	@Test
	public void fk_gets_registered() throws Exception {
		List<Change> changes = mock.newList().createFk("Vote", "poll_id", 1).getList();
		EntityConfiguration<?> vote = handler.getByTableName("Vote");
		model = new CurrentTableModel(vote, changes);
		assertThat(model.getData().getFks()).isNotEmpty();
	}
	
	@Test
	public void fk_gets_dropped() throws Exception {
		List<Change> changes = mock.newList().createFk("Vote", "poll_id", 1).createFk("Vote", "new_poll_id", 1).dropFk("Vote", "poll_id", 1).getList();
		EntityConfiguration<?> vote = handler.getByTableName("Vote");
		model = new CurrentTableModel(vote, changes);
		assertThat(model.getData().getFks()).hasSize(1);
	}

	@Test
	public void index_gets_registered() throws Exception {
		List<Change> changes = mock.newList().createIndex("Vote", "index", 1).getList();
		EntityConfiguration<?> vote = handler.getByTableName("Vote");
		model = new CurrentTableModel(vote, changes);
		assertThat(model.getData().getIndexes()).isNotEmpty();
	}
	
	@Test
	public void index_gets_dropped_if_name_matches() throws Exception {
		List<Change> changes = mock.newList().createIndex("Vote", "index", 1).dropIndex("Vote", "index", 1).getList();
		EntityConfiguration<?> vote = handler.getByTableName("Vote");
		model = new CurrentTableModel(vote, changes);
		assertThat(model.getData().getIndexes()).isEmpty();
	}
	
	@Test
	public void index_is_kept_if_no_drop_matches_name() throws Exception {
		List<Change> changes = mock.newList().createIndex("Vote", "index", 1).dropIndex("Vote", "index2", 1).getList();
		EntityConfiguration<?> vote = handler.getByTableName("Vote");
		model = new CurrentTableModel(vote, changes);
		assertThat(model.getData().getIndexes()).isNotEmpty();
	}
	
	@Test
	public void _renameCols_removes_old_column() throws Exception {
		List<Change> changes = mock.newList().renameColumn("Vote", "oldName", "name", 1).getList();
		EntityConfiguration<?> vote = handler.getByTableName("Vote");
		model = new CurrentTableModel(vote, changes);
		assertThat(model.getData().getColumnConfigs().stream().map(c -> c.getName()).collect(Collectors.toList())).asList().contains("name");
		assertThat(model.getData().getColumnConfigs().stream().map(c -> c.getName()).collect(Collectors.toList())).asList().doesNotContain("oldName");
	}
	
	@Test
	public void rename_table_should_add_a_rename_table() throws Exception {
		List<Change> changes = mock.newList().renameTable("OldVote", "newVote", 1).getList();
		EntityConfiguration<?> vote = handler.getByTableName("Vote");
		model = new CurrentTableModel(vote, changes);
		assertThat(model.getData().getRenameTables()).hasSize(1);
	}
	
	@Test
	public void drop_table_should_clean_all_data() throws Exception {
		List<Change> changes = mock.newList().addColumn("OldVote", "col", 1).dropTable("Vote", 1).getList();
		EntityConfiguration<?> vote = handler.getByTableName("Vote");
		model = new CurrentTableModel(vote, changes);
		assertThat(model.getData().getColumnConfigs()).hasSize(0);
	}
	
	@Test
	public void rename_table_should_update_the_existing_createTable() throws Exception {
		String newName = "newVote";
		List<Change> changes = mock.newList().createTable("OldVote", 1).renameTable("OldVote", newName, 1).getList();
		EntityConfiguration<?> vote = handler.getByTableName("Vote");
		model = new CurrentTableModel(vote, changes);
		assertThat(model.getData().getCreateTables().get(0).getTableName()).isEqualTo(newName);
	
	}
	
	@Test
	public void dropsColumn_matching_name() throws Exception {
		List<Change> changes = mock.newList().addColumn("Vote", "name", 1).dropColumn("Vote", "name", 1).getList();
		EntityConfiguration<?> vote = handler.getByTableName("Vote");
		model = new CurrentTableModel(vote, changes);
	
	}

}
