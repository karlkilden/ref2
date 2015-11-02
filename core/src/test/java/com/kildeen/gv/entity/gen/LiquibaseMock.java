package com.kildeen.gv.entity.gen;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import liquibase.change.AddColumnConfig;
import liquibase.change.Change;
import liquibase.change.ColumnConfig;
import liquibase.change.core.AddColumnChange;
import liquibase.change.core.AddForeignKeyConstraintChange;
import liquibase.change.core.CreateIndexChange;
import liquibase.change.core.CreateSequenceChange;
import liquibase.change.core.CreateTableChange;
import liquibase.change.core.DropAllForeignKeyConstraintsChange;
import liquibase.change.core.DropColumnChange;
import liquibase.change.core.DropIndexChange;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;

import org.mockito.Mockito;

import com.google.common.collect.Lists;

public class LiquibaseMock {

	private List<Change> changeList = new ArrayList<>();
	private DatabaseChangeLog log;
	private CreateTableChange createTable;
	private AddColumnChange addCol;
	private CreateIndexChange createIndexChange;
	private CreateSequenceChange createSeq;

	public DatabaseChangeLog get() {
		return log;
	}

	public LiquibaseMock() {

		log = Mockito.mock(DatabaseChangeLog.class);
		ChangeSet set1 = new ChangeSet(log);
		createTable = new CreateTableChange();

		createTable.setChangeSet(set1);
		createTable.setTableName("Vote");
		ColumnConfig col = new ColumnConfig();
		col.setName("id");
		createTable.addColumn(col);
		set1.addChange(createTable);
		addCol = new AddColumnChange();
		addCol.setChangeSet(set1);
		addCol.setTableName("Vote");
		AddColumnConfig col2 = new AddColumnConfig();
		col2.setName("name");
		col2.setAfterColumn("id");
		addCol.addColumn(col2);
		set1.addChange(addCol);
		createIndexChange = new CreateIndexChange();
		createIndexChange.setTableName("Poll");
		createIndexChange.addColumn(col2);
		List<ChangeSet> changeSets = Lists.newArrayList(set1);
		when(log.getChangeSets()).thenReturn(changeSets);
		createSeq = new CreateSequenceChange();
	}

	public LiquibaseMock newList() {
		changeList.clear();
		return this;
	}

	public LiquibaseMock createTable() {
		changeList.add(createTable);
		return this;
	}
	public LiquibaseMock addColumn() {
		changeList.add(addCol);
		return this;
	}

	public LiquibaseMock createIndex() {
		changeList.add(createIndexChange);
		return this;
	}

	public List<Change> getList() {
		return changeList;
	}

	public LiquibaseMock createSequence() {
		changeList.add(createSeq);
		return this;
	}

}
