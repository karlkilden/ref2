package com.kildeen.gv.entity.gen;

import static com.kildeen.gv.entity.gen.ChangeReadHelper.getTableName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import liquibase.change.Change;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;

import com.kildeen.gv.entity.EntityConfiguration;
import com.kildeen.gv.entity.EntityConfigurationHandler;

public class TableModelHandler {

	private EntityConfigurationHandler handler = EntityConfigurationHandler.getInstance();
	private DatabaseChangeLog lbChangeLog;
	private Map<EntityConfiguration<?>, List<Change>> changesPerEntity = new HashMap<>();
	private LiquibaseReadHelper liquibaseHelper;

	public TableModelHandler(LiquibaseReadHelper liquibaseHelper) {
		this.liquibaseHelper = liquibaseHelper;

	}

	public Map<Class<?>, CurrentTableModel> mapTables() {
		try {
			lbChangeLog = liquibaseHelper.get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (ChangeSet set : lbChangeLog.getChangeSets()) {
			for (Change change : set.getChanges()) {
				Optional<String> tableName = null;
				try {
					tableName = getTableName(change, "tableName", "baseTableName");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (tableName.isPresent()) {
					EntityConfiguration<?> conf = handler.getByCurrentOrPreviousTableName(set.getId(), tableName.get());
					changesPerEntity.computeIfAbsent(conf, c -> new ArrayList<>()).add(change);
				}
			}
		}
		Map<Class<?>, CurrentTableModel> result = new HashMap<>();
		for (Entry<EntityConfiguration<?>, List<Change>> entry : changesPerEntity.entrySet()) {
			CurrentTableModel m = new CurrentTableModel(entry.getKey(), entry.getValue());
			result.put(entry.getKey().getDefiningClass(), m);
		}

		createEntityModels();
		return result;
	}

	private Map<Class<?>, CurrentEntityModel> createEntityModels() {
		Map<Class<?>, CurrentEntityModel> entityModels = new HashMap<>();
		for (EntityConfiguration<?> conf : handler.getAll()) {
			CurrentEntityModel em = new CurrentEntityModel(conf, new RelationalModel());
			entityModels.put(conf.getDefiningClass(), em);
		}
		entityModels.values().forEach(model -> model.addRelationalData(entityModels));

		return entityModels;
	}

	public void clearCache() {

	}

}
