package com.kildeen.gv.entity.gen;

import java.util.List;
import java.util.OptionalDouble;

import liquibase.change.ColumnConfig;
import liquibase.change.core.AddForeignKeyConstraintChange;
import liquibase.change.core.AddNotNullConstraintChange;
import liquibase.change.core.AddPrimaryKeyChange;
import liquibase.change.core.AddUniqueConstraintChange;
import liquibase.change.core.CreateIndexChange;
import liquibase.change.core.CreateSequenceChange;
import liquibase.change.core.CreateTableChange;
import liquibase.change.core.DropColumnChange;
import liquibase.change.core.DropForeignKeyConstraintChange;
import liquibase.change.core.DropIndexChange;
import liquibase.change.core.DropNotNullConstraintChange;
import liquibase.change.core.DropPrimaryKeyChange;
import liquibase.change.core.DropSequenceChange;
import liquibase.change.core.DropUniqueConstraintChange;
import liquibase.change.core.RenameColumnChange;
import liquibase.change.core.RenameTableChange;

public interface CurrentModelData {
	
	List<CreateTableChange> getCreateTables();

	List<AddPrimaryKeyChange> getPks();

	void setPks(List<AddPrimaryKeyChange> pks);

	List<DropPrimaryKeyChange> getDropPks();

	void setDropPks(List<DropPrimaryKeyChange> dropPks);

	List<CreateSequenceChange> getSeqs();

	void setSeqs(List<CreateSequenceChange> seqs);

	List<DropSequenceChange> getDropSeqs();

	void setDropSeqs(List<DropSequenceChange> dropSeqs);

	void setIndexes(List<CreateIndexChange> indexes);

	void setRenameColumns(List<RenameColumnChange> renameColumns);

	List<ColumnConfig> getColumnConfigs();

	void setColumnConfigs(List<ColumnConfig> columnConfigs);

	List<CreateIndexChange> getIndexes();

	List<DropIndexChange> getDropIndexes();

	void setDropIndexes(List<DropIndexChange> dropIndexes);

	List<AddForeignKeyConstraintChange> getFks();

	void setFks(List<AddForeignKeyConstraintChange> fks);

	List<DropForeignKeyConstraintChange> getDropFks();

	void setDropFks(List<DropForeignKeyConstraintChange> dropFks);

	List<AddUniqueConstraintChange> getUniques();

	void setUniques(List<AddUniqueConstraintChange> uniques);

	List<DropUniqueConstraintChange> getDropUniques();

	void setDropUniques(List<DropUniqueConstraintChange> dropUniques);

	List<AddNotNullConstraintChange> getNotNulls();

	void setNotNulls(List<AddNotNullConstraintChange> notNulls);

	List<DropNotNullConstraintChange> getDropNotNulls();

	void setDropNotNulls(List<DropNotNullConstraintChange> dropNotNulls);

	List<DropColumnChange> getDropColumns();

	void setDropColumns(List<DropColumnChange> dropColumns);

	void addColumnConfigs(List<ColumnConfig> list);

	List<RenameColumnChange> getRenameColumns();

	AddPrimaryKeyChange getPk();

	List<RenameTableChange> getRenameTables();

}