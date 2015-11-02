package com.kildeen.gv.entity.gen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import liquibase.change.AbstractChange;
import liquibase.change.Change;
import liquibase.change.ColumnConfig;
import liquibase.change.core.CreateIndexChange;

import com.kildeen.gv.entity.EntityConfiguration;

public class CurrentTableModel {

	private List<ColumnConfig> columnConfigs = new ArrayList<>();
	private List<CreateIndexChange> indexes = new ArrayList<>();
	Map<Class<? extends Change>, Function<Change, Boolean>> handlerMap = new HashMap<>();

	public CurrentTableModel(EntityConfiguration<?> conf, List<Change> changes) throws Exception {

		setupSpecialCases();

		for (Change change : changes) {

			Function<Change, Boolean> handler = handlerMap.get(change.getClass());

			if (handler == null) {
				Optional<List<ColumnConfig>> cols = ChangeReadHelper.getColumnConfigs(change);
				if (cols.isPresent()) {
					columnConfigs.addAll(cols.get());
				}
			}

			else {
				handler.apply(change);
			}

		}
	}

	private void setupSpecialCases() {
		handlerMap.put(CreateIndexChange.class, this::handleCreateIndex);
	}

	public List<ColumnConfig> getColumnConfigs() {
		return columnConfigs;
	}

	private boolean handleCreateIndex(Change change) {
		return indexes.add((CreateIndexChange) change);
	}

}
