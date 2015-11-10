package com.kildeen.gv.entity.gen;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import liquibase.change.AddColumnConfig;
import liquibase.change.Change;
import liquibase.change.ChangeWithColumns;
import liquibase.change.ColumnConfig;
import liquibase.change.core.AddColumnChange;
import liquibase.change.core.AddForeignKeyConstraintChange;
import liquibase.change.core.AddPrimaryKeyChange;
import liquibase.change.core.CreateIndexChange;
import liquibase.change.core.CreateSequenceChange;
import liquibase.change.core.CreateTableChange;
import liquibase.change.core.DropAllForeignKeyConstraintsChange;
import liquibase.change.core.DropColumnChange;
import liquibase.change.core.DropForeignKeyConstraintChange;
import liquibase.change.core.DropIndexChange;
import liquibase.change.core.DropPrimaryKeyChange;
import liquibase.change.core.DropTableChange;
import liquibase.change.core.RenameColumnChange;
import liquibase.change.core.RenameTableChange;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;

import org.mockito.Mockito;

import com.google.common.collect.Lists;

public class LiquibaseMock {

	List<Change> changeList = new ArrayList<>();
	DatabaseChangeLog log;
	ChangeSet set1;
	ChangeSet set2;

	CreateTableChange createTable;
	CreateIndexChange createIndexChange;
	CreateSequenceChange createSeq;
	AddPrimaryKeyChange pkChange;
	List<ChangeSet> changeSets;
	private ChangeSet[] sets = new ChangeSet[2];
	private AddPrimaryKeyChange createPK;
	private AddForeignKeyConstraintChange addFk;
	private RenameColumnChange renameColumnChange;
	private DropForeignKeyConstraintChange dropFk;
	private DropPrimaryKeyChange dropPK;
	private AddColumnConfig col2;
	private DropIndexChange dropIndexChange;
	private AddColumnChange addColumnChange;
	private DropColumnChange dropColumnChange;
	private RenameTableChange renameTableChange;
	private DropTableChange dropTableChange;

	public DatabaseChangeLog get() {
		return log;
	}

	public LiquibaseMock() {

		log = Mockito.mock(DatabaseChangeLog.class);
		set1 = new ChangeSet(log);
		set2 = new ChangeSet(log);
		sets[0] = set1;
		sets[1] = set2;
		changeSets = Lists.newArrayList(sets);

		createTable = new CreateTableChange();
		col2 = new AddColumnConfig();
		col2.setName("name");
		createTable.addColumn(col2);

		createSeq = new CreateSequenceChange();
		dropFk = new DropForeignKeyConstraintChange();
		pkChange = new AddPrimaryKeyChange();
		pkChange.setTableName("Vote");
		renameColumnChange = new RenameColumnChange();

	}

	public LiquibaseMock newList() {
		changeList.clear();
		return this;
	}

	public LiquibaseMock createTable(String tableName, int set) {
		createTable.setTableName(tableName);
		add(set, createTable);
		return this;
	}
	
	public LiquibaseMock renameTable(String oldTableName, String tableName,  int set) {
		renameTableChange = new RenameTableChange();
		renameTableChange.setOldTableName(oldTableName);
		renameTableChange.setNewTableName(tableName);
		add(set, renameTableChange);
		return this;
	}

	public LiquibaseMock createPK(String tableName, String constraintName, int set) {
		createPK = new AddPrimaryKeyChange();
		createPK.setTableName(tableName);
		createPK.setConstraintName(constraintName);
		add(set, createPK);
		return this;
	}

	public LiquibaseMock dropPK(String tableName, String constraintName, int set) {
		dropPK = new DropPrimaryKeyChange();
		dropPK.setTableName(tableName);
		dropPK.setConstraintName(constraintName);
		add(set, dropPK);
		return this;
	}

	public LiquibaseMock renameColumn(String tableName, String oldName, String name, int set) {
		createTable.setTableName(tableName);
		ColumnConfig column = new ColumnConfig();
		column.setName(oldName);
		createTable.addColumn(column);
		add(set, createTable);

		renameColumnChange.setTableName(tableName);
		renameColumnChange.setOldColumnName(oldName);
		renameColumnChange.setNewColumnName(name);
		add(set, renameColumnChange);
		return this;
	}

	private void add(int set, Change change) {
		changeList.add(change);
		sets[set].addChange(change);
	}

	public LiquibaseMock addColumn(String tableName, String columnName, int set) {
		addColumnChange = new AddColumnChange();
		col2 = new AddColumnConfig();
		col2.setName("columnName");
		addColumnChange.addColumn(col2);
		addColumnChange.setTableName(tableName);
		add(set, addColumnChange);
		return this;
	}
	
	public LiquibaseMock dropColumn(String tableName, String columnName, int set) {
		dropColumnChange = new DropColumnChange();
		col2 = new AddColumnConfig();
		col2.setName("columnName");
		dropColumnChange.addColumn(col2);
		dropColumnChange.setTableName(tableName);
		add(set, dropColumnChange);
		return this;
	}

	public LiquibaseMock createIndex(String tableName, String indexName, int set) {
		createIndexChange = new CreateIndexChange();
		createIndexChange.setIndexName(indexName);
		createIndexChange.addColumn(col2);
		createIndexChange.setTableName(tableName);
		add(set, createIndexChange);
		return this;
	}

	public LiquibaseMock dropIndex(String tableName, String indexName, int set) {
		dropIndexChange = new DropIndexChange();
		dropIndexChange.setIndexName(indexName);
		dropIndexChange.setTableName(tableName);
		add(set, dropIndexChange);
		return this;
	}
	
	public LiquibaseMock dropTable(String tableName, int set) {
		dropTableChange = new DropTableChange();
		dropTableChange.setTableName(tableName);
		add(set, dropTableChange);
		return this;	}

	public LiquibaseMock createFk(String tableName, String cols, int set) {
		addFk = new AddForeignKeyConstraintChange();
		addFk.setBaseTableName(tableName);
		addFk.setBaseColumnNames(cols);
		addFk.setConstraintName(cols);
		add(set, addFk);
		return this;
	}

	public LiquibaseMock dropFk(String tableName, String constraintName, int set) {
		dropFk.setBaseTableName(tableName);
		dropFk.setConstraintName(constraintName);
		add(set, dropFk);
		return this;
	}

	public LiquibaseMock createPrimaryKey() {
		changeList.add(pkChange);
		return this;
	}

	public List<Change> getList() {
		return changeList;
	}

	public LiquibaseMock addToSet(Change change, ChangeSet set) {
		change.setChangeSet(set);
		changeSets.add(set);
		set.addChange(change);
		return this;
	}

	public LiquibaseMock createSequence() {
		changeList.add(createSeq);
		return this;
	}

	public LiquibaseMock updateTableName(String tableName) {
		if (changeList.contains(createTable) && createTable.getTableName() == null) {
			createTable.setTableName(tableName);
		}
		if (changeList.contains(createIndexChange) && createIndexChange.getTableName() == null) {
			createIndexChange.setTableName(tableName);
		}
		return this;
	}

	public void record() {
		when(log.getChangeSets()).thenReturn(changeSets);
	}



}
