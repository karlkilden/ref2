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

public class ChangeSetBuilder {

	private EntityConfigurationHandler handler = EntityConfigurationHandler.getInstance();
	private DatabaseChangeLog lbChangeLog;
	LiquibaseHelper liquibaseHelper;
	private Map<EntityConfiguration<?>, List<Change>> changesPerEntity = new HashMap<>();

	public ChangeSetBuilder() {

	}

	public void mapChange() throws Exception {
		try {
			lbChangeLog = liquibaseHelper.get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (ChangeSet set : lbChangeLog.getChangeSets()) {
			for (Change change : set.getChanges()) {
				Optional<String> tableName = getTableName(change, "tableName", "baseTableName");
				if (tableName.isPresent()) {
					EntityConfiguration<?> conf = handler.getByTableName(tableName.get());
					changesPerEntity.computeIfAbsent(conf, c -> new ArrayList<>()).add(change);
				}
			}
		}
		for (Entry<EntityConfiguration<?>, List<Change>> entry : changesPerEntity.entrySet()) {
			CurrentTableModel m = new CurrentTableModel(entry.getKey(), entry.getValue());
		}

	}

	public void clearCache() {

	}

	public Map<EntityConfiguration<?>, List<Change>> getChangeSets() {

		return changesPerEntity;

	}

}
