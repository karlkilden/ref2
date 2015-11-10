<<<<<<< HEAD:core/src/main/java/com/kildeen/gv/entity/gen/CurrentTableModelBuilder.java
package com.kildeen.gv.entity.gen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import liquibase.change.Change;
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
import liquibase.change.core.DropTableChange;
import liquibase.change.core.DropUniqueConstraintChange;
import liquibase.change.core.RenameColumnChange;
import liquibase.change.core.RenameTableChange;

public class CurrentTableModelBuilder {
	CurrentTableModelData data = new CurrentTableModelData();
	CurrentModel currentTableModel;
	Map<Class<? extends Change>, Function<Change, Boolean>> handlerMap = new HashMap<>();
	private StringBuilder definitionProblems = new StringBuilder();

	public CurrentTableModelBuilder(CurrentModel currentTableModel) {
		this.currentTableModel = currentTableModel;

		setupSpecialCases();

	}

	private void setupSpecialCases() {
		handlerMap.put(CreateTableChange.class, this::createTableChange);
		handlerMap.put(DropTableChange.class, this::dropTableChange);
		handlerMap.put(CreateIndexChange.class, this::handleCreateIndex);
		handlerMap.put(DropIndexChange.class, this::handleDropIndex);
		handlerMap.put(AddPrimaryKeyChange.class, this::handleAddPrimaryKeyChange);
		handlerMap.put(DropPrimaryKeyChange.class, this::handleDropPrimaryKeyChange);
		handlerMap.put(AddForeignKeyConstraintChange.class, this::handleForeignKeyConstraintChange);
		handlerMap.put(DropForeignKeyConstraintChange.class, this::handleDropForeignKeyConstraintChange);
		handlerMap.put(AddUniqueConstraintChange.class, this::handleUniqueConstraintChange);
		handlerMap.put(DropUniqueConstraintChange.class, this::handleDropUniqueConstraintChange);
		handlerMap.put(AddNotNullConstraintChange.class, this::handleNotNullConstraintChange);
		handlerMap.put(DropNotNullConstraintChange.class, this::handleDropNotNullConstraintChange);
		handlerMap.put(CreateSequenceChange.class, this::handleCreateSequenceChange);

		handlerMap.put(RenameColumnChange.class, this::handleRenameColumnChange);
		handlerMap.put(RenameTableChange.class, this::handleRenameTableChange);

		handlerMap.put(DropColumnChange.class, this::handleDropColumnChange);

	}

	public CurrentTableModelBuilder build() {

		buildColumns();
		buildFks();
		buildPk();
		buildIndexes();
		buildTableName();
		
		
		if (definitionProblems.length() > 1) {
			throw new IllegalStateException(
					"Problem with Entity: " + currentTableModel.toString() + System.lineSeparator() + definitionProblems.toString());
		}
		return this;
	}

	private void buildTableName() {
		if (isUsed(data.getRenameTables())) {
			
			data.getCreateTables().get(0).setTableName(data.getRenameTables().get(data.getRenameTables().size()-1).getNewTableName());
		}
	}

	private void buildIndexes() {
		if (isUsed(data.getIndexes()) && isUsed(data.getDropIndexes())) {
			List<CreateIndexChange> current = new ArrayList<>();
			Set<String> constraintsToDrop = data.getDropIndexes().stream().map(d -> d.getIndexName()).collect(Collectors.toSet());
			for (CreateIndexChange k : data.getIndexes()) {
				if (!constraintsToDrop.contains(k.getIndexName())) {
					current.add(k);
				}
			}
			data.setIndexes(current);
		}
	}

	private void buildPk() {
		if (isUsed(data.getDropPks())) {
			List<AddPrimaryKeyChange> current = new ArrayList<>();
			Set<String> constraintsToDrop = data.getDropPks().stream().map(d -> d.getConstraintName()).collect(Collectors.toSet());
			for (AddPrimaryKeyChange k : data.getPks()) {
				if (!constraintsToDrop.contains(k.getConstraintName())) {
					current.add(k);
				}
			}
			data.setPks(current);
		}
		if (data.getPks().size() > 1) {
			addProblem("Cannot have multiple primary keys. Has " + data.getPks().size() + " keys defined.");
		}
	}

	private void addProblem(String problem) {
		definitionProblems.append(problem).append(System.lineSeparator());
	}

	private void buildFks() {
		if (isUsed(data.getDropFks())) {
			List<AddForeignKeyConstraintChange> currentFks = new ArrayList<>();
			Set<String> constraintsToDrop = data.getDropFks().stream().map(d -> d.getConstraintName()).collect(Collectors.toSet());
			for (AddForeignKeyConstraintChange fk : data.getFks()) {
				if (!constraintsToDrop.contains(fk.getConstraintName())) {
					currentFks.add(fk);
				}
			}
			data.setFks(currentFks);
		}
	}

	private void buildColumns() {
		if (data.hasDroppedColumns() || data.hasDroppedColumns()) {
			List<ColumnConfig> currentCols = new ArrayList<>();
			Set<String> colNamesToDrop = data.getDropColumns().stream().map(dc -> dc.getColumnName()).collect(Collectors.toSet());
			Set<String> colNamesToDrop2 = data.getRenameColumns().stream().map(dc -> dc.getOldColumnName()).collect(Collectors.toSet());
			colNamesToDrop.addAll(colNamesToDrop2);
			for (ColumnConfig conf : data.getColumnConfigs()) {
				if (!colNamesToDrop.contains(conf.getName())) {
					currentCols.add(conf);
				}
			}
			data.setColumnConfigs(currentCols);
		}
	}
	
	private boolean dropTableChange(Change change) {
		data = new CurrentTableModelData();
		return true;
	}

	private boolean createTableChange(Change change) {
		return data.getCreateTables().add((CreateTableChange) change);
	}
	
	private boolean handleRenameColumnChange(Change change) {
		return data.getRenameColumns().add((RenameColumnChange) change);
	}

	private boolean handleCreateIndex(Change change) {
		return data.getIndexes().add((CreateIndexChange) change);
	}

	private boolean handleDropIndex(Change change) {
		return data.getDropIndexes().add((DropIndexChange) change);
	}

	private boolean handleUniqueConstraintChange(Change change) {
		return data.getUniques().add((AddUniqueConstraintChange) change);
	}

	private boolean handleDropUniqueConstraintChange(Change change) {
		return data.getDropUniques().add((DropUniqueConstraintChange) change);
	}

	private boolean handleForeignKeyConstraintChange(Change change) {
		return data.getFks().add((AddForeignKeyConstraintChange) change);
	}

	private boolean handleNotNullConstraintChange(Change change) {
		return data.getNotNulls().add((AddNotNullConstraintChange) change);
	}

	private boolean handleDropForeignKeyConstraintChange(Change change) {
		return data.getDropFks().add((DropForeignKeyConstraintChange) change);
	}

	private boolean handleDropNotNullConstraintChange(Change change) {
		return data.getDropNotNulls().add((DropNotNullConstraintChange) change);
	}

	private boolean handleCreateSequenceChange(Change change) {
		data.getSeqs().add((CreateSequenceChange) change);
		return true;
	}

	private boolean handleAddPrimaryKeyChange(Change change) {
		data.getPks().add((AddPrimaryKeyChange) change);
		return true;
	}
	
	private boolean handleRenameTableChange(Change change) {
		data.getRenameTables().add((RenameTableChange) change);
		return true;
	}

	private boolean handleDropPrimaryKeyChange(Change change) {
		data.getDropPks().add((DropPrimaryKeyChange) change);
		return true;
	}

	private boolean handleDropColumnChange(Change change) {
		return data.getDropColumns().add((DropColumnChange) change);
	}

	public void addColumnConfigs(List<ColumnConfig> list) {

		data.addColumnConfigs(list);
	}

	public CurrentModelData getData() {
		return data;
	}

	public Function<Change, Boolean> get(Class<? extends Change> changeType) {
		return handlerMap.get(changeType);
	}

	public boolean isUsed(Collection<?> col) {
		return !col.isEmpty();
	}

}
=======
package com.kildeen.gv.entity.gen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import liquibase.change.Change;
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
import liquibase.change.core.DropUniqueConstraintChange;
import liquibase.change.core.RenameColumnChange;

public class CurrentTableModelBuilder {
	CurrentTableModelData data = new CurrentTableModelData();
	CurrentTableModel currentTableModel;
	Map<Class<? extends Change>, Function<Change, Boolean>> handlerMap = new HashMap<>();
	private StringBuilder definitionProblems = new StringBuilder();

	public CurrentTableModelBuilder(CurrentTableModel currentTableModel) {
		this.currentTableModel = currentTableModel;

		setupSpecialCases();

	}

	private void setupSpecialCases() {
		handlerMap.put(CreateIndexChange.class, this::handleCreateIndex);
		handlerMap.put(DropIndexChange.class, this::handleDropIndex);
		handlerMap.put(AddPrimaryKeyChange.class, this::handleAddPrimaryKeyChange);
		handlerMap.put(DropPrimaryKeyChange.class, this::handleDropPrimaryKeyChange);
		handlerMap.put(AddForeignKeyConstraintChange.class, this::handleForeignKeyConstraintChange);
		handlerMap.put(DropForeignKeyConstraintChange.class, this::handleDropForeignKeyConstraintChange);
		handlerMap.put(AddUniqueConstraintChange.class, this::handleUniqueConstraintChange);
		handlerMap.put(DropUniqueConstraintChange.class, this::handleDropUniqueConstraintChange);
		handlerMap.put(AddNotNullConstraintChange.class, this::handleNotNullConstraintChange);
		handlerMap.put(DropNotNullConstraintChange.class, this::handleDropNotNullConstraintChange);
		handlerMap.put(CreateSequenceChange.class, this::handleCreateSequenceChange);

		handlerMap.put(RenameColumnChange.class, this::handleRenameColumnChange);

		handlerMap.put(DropColumnChange.class, this::handleDropColumnChange);

	}

	public CurrentTableModelBuilder build() {

		buildColumns();
		buildFks();
		buildPk();
		if (definitionProblems.length() > 1) {
			throw new IllegalStateException("Problem with Entity: " + currentTableModel.toString() + System.lineSeparator()+definitionProblems.toString());
		}
		return this;
	}

	private void buildPk() {
		if (isUsed(data.getDropPks())) {
			List<AddPrimaryKeyChange> current = new ArrayList<>();
			Set<String> constraintsToDrop = data.getDropPks().stream().map(d -> d.getConstraintName()).collect(Collectors.toSet());
			for (AddPrimaryKeyChange k : data.getPks()) {
				if (!constraintsToDrop.contains(k.getConstraintName())) {
					current.add(k);
				}
			}
			data.setPks(current);
		}
		if (data.getPks().size() > 1) {
			addProblem("Cannot have multiple primary keys. Has " + data.getPks().size() +" keys defined.");
		}
	}

	private void addProblem(String problem) {
		definitionProblems.append(problem).append(System.lineSeparator());		
	}

	private void buildFks() {
		if (isUsed(data.getDropFks())) {
			List<AddForeignKeyConstraintChange> currentFks = new ArrayList<>();
			Set<String> constraintsToDrop = data.getDropFks().stream().map(d -> d.getConstraintName()).collect(Collectors.toSet());
			for (AddForeignKeyConstraintChange fk : data.getFks()) {
				if (!constraintsToDrop.contains(fk.getConstraintName())) {
					currentFks.add(fk);
				}
			}
			data.setFks(currentFks);
		}
	}

	private void buildColumns() {
		if (data.hasDroppedColumns() || data.hasDroppedColumns()) {
			List<ColumnConfig> currentCols = new ArrayList<>();
			Set<String> colNamesToDrop = data.getDropColumns().stream().map(dc -> dc.getColumnName()).collect(Collectors.toSet());
			Set<String> colNamesToDrop2 = data.getRenameColumns().stream().map(dc -> dc.getOldColumnName()).collect(Collectors.toSet());
			colNamesToDrop.addAll(colNamesToDrop2);
			for (ColumnConfig conf : data.getColumnConfigs()) {
				if (!colNamesToDrop.contains(conf.getName())) {
					currentCols.add(conf);
				}
			}
			data.setColumnConfigs(currentCols);
		}
	}

	private boolean handleRenameColumnChange(Change change) {
		return data.getRenameColumns().add((RenameColumnChange) change);
	}

	private boolean handleCreateIndex(Change change) {
		return data.getIndexes().add((CreateIndexChange) change);
	}

	private boolean handleDropIndex(Change change) {
		return data.getDropIndexes().add((DropIndexChange) change);
	}

	private boolean handleDropUniqueConstraintChange(Change change) {
		return data.getDropUniques().add((DropUniqueConstraintChange) change);
	}

	private boolean handleUniqueConstraintChange(Change change) {
		return data.getUniques().add((AddUniqueConstraintChange) change);
	}

	private boolean handleForeignKeyConstraintChange(Change change) {
		return data.getFks().add((AddForeignKeyConstraintChange) change);
	}

	private boolean handleNotNullConstraintChange(Change change) {
		return data.getNotNulls().add((AddNotNullConstraintChange) change);
	}

	private boolean handleDropForeignKeyConstraintChange(Change change) {
		return data.getDropFks().add((DropForeignKeyConstraintChange) change);
	}

	private boolean handleDropNotNullConstraintChange(Change change) {
		return data.getDropNotNulls().add((DropNotNullConstraintChange) change);
	}

	private boolean handleCreateSequenceChange(Change change) {
		data.getSeqs().add((CreateSequenceChange) change);
		return true;
	}

	private boolean handleAddPrimaryKeyChange(Change change) {
		data.getPks().add((AddPrimaryKeyChange) change);
		return true;
	}

	private boolean handleDropPrimaryKeyChange(Change change) {
		data.getDropPks().add((DropPrimaryKeyChange) change);
		return true;
	}

	private boolean handleDropColumnChange(Change change) {
		return data.getDropColumns().add((DropColumnChange) change);
	}

	public void addColumnConfigs(List<ColumnConfig> list) {

		data.addColumnConfigs(list);
	}

	public CurrentTableModelData getData() {
		return data;
	}

	public Function<Change, Boolean> get(Class<? extends Change> changeType) {
		return handlerMap.get(changeType);
	}

	public boolean isUsed(Collection<?> col) {
		return !col.isEmpty();
	}

}
>>>>>>> 1fec527c6be4e00509c51d1e46306d612ebd12ec:backend/core/src/main/java/com/kildeen/gv/entity/gen/CurrentTableModelBuilder.java
