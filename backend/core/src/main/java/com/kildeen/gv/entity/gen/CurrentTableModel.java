package com.kildeen.gv.entity.gen;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.kildeen.gv.entity.EntityConfiguration;

import liquibase.change.Change;
import liquibase.change.ColumnConfig;

public class CurrentTableModel implements CurrentModel {
	CurrentTableModelBuilder builder;
	private CurrentModelData data;
	private EntityConfiguration<?> conf;

	public CurrentTableModel(EntityConfiguration<?> conf, List<Change> changes) {
		this.conf = conf;
		builder = new CurrentTableModelBuilder(this);

		for (Change change : changes) {
			
			Function<Change, Boolean> handler = builder.get(change.getClass());

			if (handler == null) {
				Optional<List<ColumnConfig>> cols = ChangeReadHelper.getColumnConfigs(change);
				if (cols.isPresent()) {
					builder.addColumnConfigs(cols.get());
				}
			}

			else {
				handler.apply(change);
			}
		}
		data = builder.build().getData();

	}

	@Override
	public CurrentModelData getData() {
		return data;
	}

	@Override
	public String toString() {
		return conf.getTableName();
	}

	public String getTableName() {
		return data.getCreateTables().get(0).getTableName();
	}

}
