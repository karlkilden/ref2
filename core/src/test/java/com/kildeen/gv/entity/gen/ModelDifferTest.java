package com.kildeen.gv.entity.gen;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import liquibase.change.ColumnConfig;
import liquibase.change.core.AddPrimaryKeyChange;
import liquibase.change.core.CreateTableChange;

public class ModelDifferTest {

	private CurrentTableModel tableModel;
	private CurrentEntityModel entityModel;
	private ModelDiffer differ;
	private CurrentModelData entityData;
	private CurrentModelData tableData;

	@Before
	public void setUp() throws Exception {

		tableModel = mock(CurrentTableModel.class);
		entityModel = mock(CurrentEntityModel.class);

		tableData = new CurrentTableModelData();
		Mockito.when(tableModel.getData()).thenReturn(tableData);
		entityData = new CurrentEntityModelData();
		when(entityModel.getData()).thenReturn(entityData);
		differ = new ModelDiffer();
		setupSamePk();
		setupDifferentTableNames();
	}

	@Test
	public void different_table_names_should_generate_rename() throws Exception {
		CurrentModelData diffResult = differ.diff(tableModel, entityModel);
		assertThat(diffResult.getRenameTables()).hasSize(1);

	}

	private void setupDifferentTableNames() {
		CreateTableChange table = new CreateTableChange();
		table.setTableName("OldName");
		tableData.getCreateTables().add(table);

		CreateTableChange newTable = new CreateTableChange();
		newTable.setTableName("newTable");
		entityData.getCreateTables().add(newTable);
	}

	@Test
	public void same_primary_key_should_not_generate_drop() throws Exception {

		CurrentModelData diffResult = differ.diff(tableModel, entityModel);
		assertThat(diffResult.getDropPks()).isEmpty();

	}

	@Test
	public void same_primary_key_should_not_generate_addPk() throws Exception {
		CurrentModelData diffResult = differ.diff(tableModel, entityModel);
		assertThat(diffResult.getPks()).isEmpty();
	}

	@Test
	public void different_primary_key_should_generate_addPk() throws Exception {
		setupDifferentPks();
		CurrentModelData diffResult = differ.diff(tableModel, entityModel);
		assertThat(diffResult.getPks()).hasSize(1);
	}

	@Test
	public void different_primary_key_should_generate_dropPk() throws Exception {
		setupDifferentPks();
		CurrentModelData diffResult = differ.diff(tableModel, entityModel);
		assertThat(diffResult.getDropPks()).hasSize(1);
	}

	@Test
	public void dropPK_should_be_previous_constraintName() throws Exception {
		setupDifferentPks();
		CurrentModelData diffResult = differ.diff(tableModel, entityModel);
		assertThat(diffResult.getDropPks().get(0).getConstraintName()).isEqualTo(tableData.getPk().getConstraintName());
	}

	@Test
	public void addPK_should_be_new_constraintName() throws Exception {
		setupDifferentPks();
		CurrentModelData diffResult = differ.diff(tableModel, entityModel);
		assertThat(diffResult.getPks().get(0).getConstraintName()).isEqualTo(entityData.getPk().getConstraintName());
	}
	
	@Test
	public void same_columns_should_not_cause_drop_columns() throws Exception {
		setupSameColumns();
		CurrentModelData diffResult = differ.diff(tableModel, entityModel);
		assertThat(diffResult.getColumnConfigs()).isEmpty();
	}
	
	@Test
	public void extra_table_columns_should_cause_cause_drop_column() throws Exception {
		setupDifferentColumns();
		CurrentModelData diffResult = differ.diff(tableModel, entityModel);
		assertThat(diffResult.getDropColumns()).hasSize(1);
	}
	
	@Test
	public void extra_table_column_should_cause_cause_rename_column() throws Exception {
		setupDifferentColumns();
		CurrentModelData diffResult = differ.diff(tableModel, entityModel);
		assertThat(diffResult.getRenameColumns()).hasSize(2);
	}
	
	@Test
	public void extra_entity_columns_should_cause_cause_add_column() throws Exception {
		setupDifferentColumns();
		CurrentModelData diffResult = differ.diff(tableModel, entityModel);
		assertThat(diffResult.getColumnConfigs()).hasSize(1);
	}
	
	@Test
	public void extra_entiti_column_should_cause_cause_rename_column() throws Exception {
		setupDifferentColumns();
		CurrentModelData diffResult = differ.diff(tableModel, entityModel);
		assertThat(diffResult.getRenameColumns()).hasSize(2);
	}

	private void setupDifferentColumns() {
		List<ColumnConfig> cols = new ArrayList<>();
		ColumnConfig col1 = new ColumnConfig();
		col1.setName("col1");
		cols.add(col1);
		
		List<ColumnConfig> cols2 = new ArrayList<>();
		ColumnConfig col2 = new ColumnConfig();
		cols2.add(col2);
		col2.setName("col2");
		tableData.getColumnConfigs().addAll(cols);		
		entityData.getColumnConfigs().addAll(cols2);
	}

	private void setupSameColumns() {
		List<ColumnConfig> cols = new ArrayList<>();
		ColumnConfig col1 = new ColumnConfig();
		col1.setName("col1");
		entityData.getColumnConfigs().addAll(cols);
		tableData.getColumnConfigs().addAll(cols);
	}

	private void setupDifferentPks() {
		AddPrimaryKeyChange tablePk = new AddPrimaryKeyChange();
		tablePk.setConstraintName("test");
		tablePk.setColumnNames("test3, test4");
		entityData.getPks().clear();
		entityData.getPks().add(tablePk);
	}

	private void setupSamePk() {
		AddPrimaryKeyChange tablePk = new AddPrimaryKeyChange();
		tablePk.setConstraintName("test");
		tablePk.setColumnNames("test, test2");
		tableData.getPks().add(tablePk);
		entityData.getPks().add(tablePk);
	}

}
