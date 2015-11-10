<<<<<<< HEAD:core/src/main/java/com/kildeen/gv/entity/gen/CurrentTableModelData.java
package com.kildeen.gv.entity.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import org.apache.openejb.jee.jba.cmp.CreateTable;

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

public class CurrentTableModelData implements CurrentModelData {
	List<CreateTableChange> createTables = new ArrayList<>();

	List<ColumnConfig> columnConfigs = new ArrayList<>();
	List<CreateIndexChange> indexes = new ArrayList<>();
	List<DropIndexChange> dropIndexes = new ArrayList<>();
	List<AddForeignKeyConstraintChange> fks = new ArrayList<>();
	List<DropForeignKeyConstraintChange> dropFks = new ArrayList<>();
	List<AddUniqueConstraintChange> uniques = new ArrayList<>();
	List<DropUniqueConstraintChange> dropUniques = new ArrayList<>();
	List<AddNotNullConstraintChange> notNulls = new ArrayList<>();
	List<DropNotNullConstraintChange> dropNotNulls = new ArrayList<>();
	List<DropColumnChange> dropColumns = new ArrayList<>();
	List<RenameColumnChange> renameColumns = new ArrayList<>();
	List<AddPrimaryKeyChange> pks = new ArrayList<>();
	List<DropPrimaryKeyChange> dropPks = new ArrayList<>();
	List<CreateSequenceChange> seqs = new ArrayList<>();
	List<DropSequenceChange> dropSeqs = new ArrayList<>();
	List<RenameTableChange> renameTableChanges = new ArrayList<>();

	@Override
	public List<AddPrimaryKeyChange> getPks() {
		return pks;
	}

	@Override
	public void setPks(List<AddPrimaryKeyChange> pks) {
		this.pks = pks;
	}

	@Override
	public List<DropPrimaryKeyChange> getDropPks() {
		return dropPks;
	}

	@Override
	public void setDropPks(List<DropPrimaryKeyChange> dropPks) {
		this.dropPks = dropPks;
	}

	@Override
	public List<CreateSequenceChange> getSeqs() {
		return seqs;
	}

	@Override
	public void setSeqs(List<CreateSequenceChange> seqs) {
		this.seqs = seqs;
	}

	@Override
	public List<DropSequenceChange> getDropSeqs() {
		return dropSeqs;
	}

	@Override
	public void setDropSeqs(List<DropSequenceChange> dropSeqs) {
		this.dropSeqs = dropSeqs;
	}

	@Override
	public void setIndexes(List<CreateIndexChange> indexes) {
		this.indexes = indexes;
	}

	@Override
	public void setRenameColumns(List<RenameColumnChange> renameColumns) {
		this.renameColumns = renameColumns;
	}

	@Override
	public List<ColumnConfig> getColumnConfigs() {
		return columnConfigs;
	}

	@Override
	public void setColumnConfigs(List<ColumnConfig> columnConfigs) {
		this.columnConfigs = columnConfigs;
	}

	@Override
	public List<CreateIndexChange> getIndexes() {
		return indexes;
	}

	@Override
	public List<DropIndexChange> getDropIndexes() {
		return dropIndexes;
	}

	@Override
	public void setDropIndexes(List<DropIndexChange> dropIndexes) {
		this.dropIndexes = dropIndexes;
	}

	@Override
	public List<AddForeignKeyConstraintChange> getFks() {
		return fks;
	}

	@Override
	public void setFks(List<AddForeignKeyConstraintChange> fks) {
		this.fks = fks;
	}

	@Override
	public List<DropForeignKeyConstraintChange> getDropFks() {
		return dropFks;
	}

	@Override
	public void setDropFks(List<DropForeignKeyConstraintChange> dropFks) {
		this.dropFks = dropFks;
	}

	@Override
	public List<AddUniqueConstraintChange> getUniques() {
		return uniques;
	}

	@Override
	public void setUniques(List<AddUniqueConstraintChange> uniques) {
		this.uniques = uniques;
	}

	@Override
	public List<DropUniqueConstraintChange> getDropUniques() {
		return dropUniques;
	}

	@Override
	public void setDropUniques(List<DropUniqueConstraintChange> dropUniques) {
		this.dropUniques = dropUniques;
	}

	@Override
	public List<AddNotNullConstraintChange> getNotNulls() {
		return notNulls;
	}

	@Override
	public void setNotNulls(List<AddNotNullConstraintChange> notNulls) {
		this.notNulls = notNulls;
	}

	@Override
	public List<DropNotNullConstraintChange> getDropNotNulls() {
		return dropNotNulls;
	}

	@Override
	public void setDropNotNulls(List<DropNotNullConstraintChange> dropNotNulls) {
		this.dropNotNulls = dropNotNulls;
	}

	@Override
	public List<DropColumnChange> getDropColumns() {
		return dropColumns;
	}

	@Override
	public void setDropColumns(List<DropColumnChange> dropColumns) {
		this.dropColumns = dropColumns;
	}

	@Override
	public void addColumnConfigs(List<ColumnConfig> list) {
		columnConfigs.addAll(list);
	}

	public boolean hasDroppedColumns() {
		return !renameColumns.isEmpty();
	}

	@Override
	public List<RenameColumnChange> getRenameColumns() {
		return renameColumns;
	}

	@Override
	public AddPrimaryKeyChange getPk() {
		return pks.get(0);
	}

	@Override
	public List<RenameTableChange> getRenameTables() {
		return renameTableChanges;
	}

	@Override
	public List<CreateTableChange> getCreateTables() {
		return createTables;
	}

}
=======
package com.kildeen.gv.entity.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import liquibase.change.ColumnConfig;
import liquibase.change.core.AddForeignKeyConstraintChange;
import liquibase.change.core.AddNotNullConstraintChange;
import liquibase.change.core.AddPrimaryKeyChange;
import liquibase.change.core.AddUniqueConstraintChange;
import liquibase.change.core.CreateIndexChange;
import liquibase.change.core.CreateSequenceChange;
import liquibase.change.core.DropColumnChange;
import liquibase.change.core.DropForeignKeyConstraintChange;
import liquibase.change.core.DropIndexChange;
import liquibase.change.core.DropNotNullConstraintChange;
import liquibase.change.core.DropPrimaryKeyChange;
import liquibase.change.core.DropSequenceChange;
import liquibase.change.core.DropUniqueConstraintChange;
import liquibase.change.core.RenameColumnChange;

public class CurrentTableModelData {
	List<ColumnConfig> columnConfigs = new ArrayList<>();
	List<CreateIndexChange> indexes = new ArrayList<>();
	List<DropIndexChange> dropIndexes = new ArrayList<>();
	List<AddForeignKeyConstraintChange> fks = new ArrayList<>();
	List<DropForeignKeyConstraintChange> dropFks = new ArrayList<>();
	List<AddUniqueConstraintChange> uniques = new ArrayList<>();
	List<DropUniqueConstraintChange> dropUniques = new ArrayList<>();
	List<AddNotNullConstraintChange> notNulls = new ArrayList<>();
	List<DropNotNullConstraintChange> dropNotNulls = new ArrayList<>();
	List<DropColumnChange> dropColumns = new ArrayList<>();
	List<RenameColumnChange> renameColumns = new ArrayList<>();
	List<AddPrimaryKeyChange> pks = new ArrayList<>();
	List<DropPrimaryKeyChange> dropPks = new ArrayList<>();
	List<CreateSequenceChange> seqs = new ArrayList<>();
	List<DropSequenceChange> dropSeqs = new ArrayList<>();

	public List<AddPrimaryKeyChange> getPks() {
		return pks;
	}

	public void setPks(List<AddPrimaryKeyChange> pks) {
		this.pks = pks;
	}

	public List<DropPrimaryKeyChange> getDropPks() {
		return dropPks;
	}

	public void setDropPks(List<DropPrimaryKeyChange> dropPks) {
		this.dropPks = dropPks;
	}

	public List<CreateSequenceChange> getSeqs() {
		return seqs;
	}

	public void setSeqs(List<CreateSequenceChange> seqs) {
		this.seqs = seqs;
	}

	public List<DropSequenceChange> getDropSeqs() {
		return dropSeqs;
	}

	public void setDropSeqs(List<DropSequenceChange> dropSeqs) {
		this.dropSeqs = dropSeqs;
	}

	public void setIndexes(List<CreateIndexChange> indexes) {
		this.indexes = indexes;
	}

	public void setRenameColumns(List<RenameColumnChange> renameColumns) {
		this.renameColumns = renameColumns;
	}

	public List<ColumnConfig> getColumnConfigs() {
		return columnConfigs;
	}

	public void setColumnConfigs(List<ColumnConfig> columnConfigs) {
		this.columnConfigs = columnConfigs;
	}

	public List<CreateIndexChange> getIndexes() {
		return indexes;
	}

	public List<DropIndexChange> getDropIndexes() {
		return dropIndexes;
	}

	public void setDropIndexes(List<DropIndexChange> dropIndexes) {
		this.dropIndexes = dropIndexes;
	}

	public List<AddForeignKeyConstraintChange> getFks() {
		return fks;
	}

	public void setFks(List<AddForeignKeyConstraintChange> fks) {
		this.fks = fks;
	}

	public List<DropForeignKeyConstraintChange> getDropFks() {
		return dropFks;
	}

	public void setDropFks(List<DropForeignKeyConstraintChange> dropFks) {
		this.dropFks = dropFks;
	}

	public List<AddUniqueConstraintChange> getUniques() {
		return uniques;
	}

	public void setUniques(List<AddUniqueConstraintChange> uniques) {
		this.uniques = uniques;
	}

	public List<DropUniqueConstraintChange> getDropUniques() {
		return dropUniques;
	}

	public void setDropUniques(List<DropUniqueConstraintChange> dropUniques) {
		this.dropUniques = dropUniques;
	}

	public List<AddNotNullConstraintChange> getNotNulls() {
		return notNulls;
	}

	public void setNotNulls(List<AddNotNullConstraintChange> notNulls) {
		this.notNulls = notNulls;
	}

	public List<DropNotNullConstraintChange> getDropNotNulls() {
		return dropNotNulls;
	}

	public void setDropNotNulls(List<DropNotNullConstraintChange> dropNotNulls) {
		this.dropNotNulls = dropNotNulls;
	}

	public List<DropColumnChange> getDropColumns() {
		return dropColumns;
	}

	public void setDropColumns(List<DropColumnChange> dropColumns) {
		this.dropColumns = dropColumns;
	}

	public void addColumnConfigs(List<ColumnConfig> list) {
		columnConfigs.addAll(list);
	}

	public boolean hasDroppedColumns() {
		return !renameColumns.isEmpty();
	}

	public List<RenameColumnChange> getRenameColumns() {
		return renameColumns;
	}

}
>>>>>>> 1fec527c6be4e00509c51d1e46306d612ebd12ec:backend/core/src/main/java/com/kildeen/gv/entity/gen/CurrentTableModelData.java