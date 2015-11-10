package com.kildeen.gv.entity.gen;

import liquibase.change.ColumnConfig;
import liquibase.change.core.AddPrimaryKeyChange;
import liquibase.change.core.DropColumnChange;
import liquibase.change.core.DropPrimaryKeyChange;
import liquibase.change.core.RenameColumnChange;
import liquibase.change.core.RenameTableChange;

public class ModelDiffer {

	private CurrentModelData tableModelData;
	private CurrentModelData entityModelData;
	private CurrentModelData result;
	private CurrentTableModel tableModel;
	private CurrentEntityModel entityModel;

	public CurrentModelData diff(CurrentTableModel tableModel, CurrentEntityModel entityModel) {
		this.tableModel = tableModel;
		this.entityModel = entityModel;
		tableModelData = tableModel.getData();
		entityModelData = entityModel.getData();
		result = new CurrentTableModelData();

		diffTableName();
		diffPk();
		diffCols();
		return result;

	}

	private void diffCols() {

		for (ColumnConfig tableCol : tableModelData.getColumnConfigs()) {
			ColumnConfig matchingCol = null;
			for (ColumnConfig entityCol : entityModelData.getColumnConfigs()) {

				if (tableCol.getName().equals(entityCol.getName())) {
					matchingCol = entityCol;
				}
			}
			if (matchingCol == null) {
				// Drop or rename? We don't know so lets add both
				DropColumnChange dropCol = new DropColumnChange();
				dropCol.setColumnName(tableCol.getName());
				dropCol.setTableName(tableModel.getTableName());
				result.getDropColumns().add(dropCol);
				
				RenameColumnChange rename = new RenameColumnChange();
				rename.setTableName(tableModel.getTableName());
				rename.setOldColumnName(tableCol.getName());
				rename.setNewColumnName("newName");
				result.getRenameColumns().add(rename);
			}
		}
		
		
		for (ColumnConfig entityCol : entityModelData.getColumnConfigs()) {
			ColumnConfig matchingCol = null;

			for (ColumnConfig tableCol : tableModelData.getColumnConfigs()) {
				if (tableCol.getName().equals(entityCol.getName())) {
					matchingCol = entityCol;
				}
			}
			if (matchingCol == null) {
				RenameColumnChange rename = new RenameColumnChange();
				rename.setTableName(tableModel.getTableName());
				rename.setOldColumnName("oldName");
				rename.setNewColumnName(entityCol.getName());
				result.getRenameColumns().add(rename);
				
				result.getColumnConfigs().add(entityCol);
			}
		}

	}

	private void diffTableName() {
		String tableName1 = tableModelData.getCreateTables().get(0).getTableName();
		String tableName2 = entityModelData.getCreateTables().get(0).getTableName();
		if (noMatch(tableName1, tableName2)) {
			RenameTableChange renameTable = new RenameTableChange();
			renameTable.setOldTableName(tableModel.getTableName());
			renameTable.setNewTableName(entityModel.getTableName());
			result.getRenameTables().add(renameTable);
		}
	}

	private void diffPk() {
		String tablePk = getPkValidation(tableModelData);
		String entityPk = getPkValidation(entityModelData);

		if (noMatch(tablePk, entityPk)) {
			DropPrimaryKeyChange dropPrimaryKeyChange = new DropPrimaryKeyChange();
			dropPrimaryKeyChange.setTableName(entityModel.getTableName());
			dropPrimaryKeyChange.setConstraintName(tableModelData.getPk().getConstraintName());
			result.getDropPks().add(dropPrimaryKeyChange);
			AddPrimaryKeyChange createPk = new AddPrimaryKeyChange();
			createPk.setColumnNames(entityModelData.getPk().getColumnNames());
			createPk.setTableName(entityModel.getTableName());
			createPk.setConstraintName(entityModelData.getPk().getConstraintName());
			result.getPks().add(createPk);
		}
	}

	private boolean noMatch(String first, String second) {
		return !first.equals(second);
	}

	private String getPkValidation(CurrentModelData entityModelData) {
		return entityModelData.getPk().getColumnNames() + "-" + entityModelData.getPk().getConstraintName();
	}

}
