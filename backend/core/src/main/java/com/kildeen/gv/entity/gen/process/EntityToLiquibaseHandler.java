package com.kildeen.gv.entity.gen.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kildeen.gv.entity.gen.CurrentEntityModel;
import com.kildeen.gv.entity.gen.CurrentModelData;
import com.kildeen.gv.entity.gen.CurrentTableModel;
import com.kildeen.gv.entity.gen.DiffHandler;
import com.kildeen.gv.entity.gen.EntityModelHandler;
import com.kildeen.gv.entity.gen.SchemaWriteHandler;
import com.kildeen.gv.entity.gen.LiquibaseReadHelper;
import com.kildeen.gv.entity.gen.TableModelHandler;

public class EntityToLiquibaseHandler {

	private TableModelHandler tableModelHandler;

	private EntityModelHandler entityModelHandler;

	private DiffHandler diffHandler;

	private SchemaWriteHandler schemaWriteHandler;

	private List<EntityToLiquibaseProcessor> processors = new ArrayList<>();

	public void execute() {
		setupProcessors();
		executeChain();

	}

	private void executeChain() {
		ProcessChain chain = new ProcessChain();
		processors.forEach(p -> p.process(chain));
	}

	private void setupProcessors() {
		LiquibaseReadHelper readHelper = new LiquibaseReadHelper("liquibase/liquibase.xml");

		tableModelHandler = new TableModelHandler(readHelper);
		processors.add(tableModelHandler);

		entityModelHandler = new EntityModelHandler();
		processors.add(entityModelHandler);

		diffHandler = new DiffHandler();
		processors.add(diffHandler);

		schemaWriteHandler = new SchemaWriteHandler();
		processors.add(schemaWriteHandler);
	}

}
