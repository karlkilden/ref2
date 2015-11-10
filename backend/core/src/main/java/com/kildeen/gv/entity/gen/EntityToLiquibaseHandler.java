package com.kildeen.gv.entity.gen;

import java.util.List;
import java.util.Map;

import org.mockito.Mock;

public class EntityToLiquibaseHandler {

	private TableModelHandler tableModelHandler;

	private EntityModelHandler entityModelHandler;

	private DiffHandler diffHandler;

	private LiquibaseChangeSetWriter liquibaseChangeSetWriter;

	public void execute() {
		LiquibaseReadHelper readHelper = new LiquibaseReadHelper("liquibase/liquibase.xml");
		tableModelHandler = new TableModelHandler(readHelper);
		Map<Class<?>, CurrentTableModel> mappedTables = tableModelHandler.mapTables();
		entityModelHandler = new EntityModelHandler();
		Map<Class<?>, CurrentEntityModel> mappedEntities = entityModelHandler.mapEntities();
		List<CurrentModelData> diffResult = diffHandler.diff(mappedTables, mappedEntities);
		liquibaseChangeSetWriter.writeDiff(diffResult);
	}

}
