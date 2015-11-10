package com.kildeen.gv.entity.gen;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.deltaspike.core.util.ReflectionUtils;

import com.kildeen.gv.conf.ProblemHelper;
import com.kildeen.gv.entity.EntityConfiguration;

import liquibase.change.AddColumnConfig;
import liquibase.change.ColumnConfig;
import liquibase.change.ConstraintsConfig;
import liquibase.change.core.AddPrimaryKeyChange;
import liquibase.change.core.CreateIndexChange;
import liquibase.change.core.CreateTableChange;
import liquibase.change.core.RenameColumnChange;

public class CurrentEntityModel implements CurrentModel {

	private CurrentModelData data = new CurrentEntityModelData();
	private ProblemHelper problemHelper = new ProblemHelper();
	private EntityConfiguration<?> configuration;
	private RelationalModel relationalModel;
	private EntityModelReader entityModelReader = new EntityModelReader();

	@Override
	public CurrentModelData getData() {
		return data;
	}

	public CurrentEntityModel(EntityConfiguration<?> configuration, RelationalModel relationalModel) {

		this.configuration = configuration;
		this.relationalModel = relationalModel;
		addTableName();
		addIndexes();
		addCols();
		validate();
	}

	private void addTableName() {
		CreateTableChange createTable = new CreateTableChange();
		createTable.setTableName(getTableName());
		data.getCreateTables().add(createTable);
	}

	public void addRelationalData(Map<Class<?>, CurrentEntityModel> allEntityModels) {
		relationalModel.build(data, configuration, allEntityModels);
	}

	private void addIndexes() {
		Table tableAnno = configuration.getDefiningClass().getAnnotation(Table.class);
		if (tableAnno != null) {
			Index[] indexes = tableAnno.indexes();
			if (indexes.length > 0) {
				for (int i = 0; i < indexes.length; i++) {
					Index current = indexes[i];
					CreateIndexChange indexChange = new CreateIndexChange();
					indexChange.setIndexName(current.name());
					String[] colNames = current.columnList().split(",");
					for (int j = 0; j < colNames.length; j++) {
						AddColumnConfig config = new AddColumnConfig();
						config.setName(colNames[j]);
						indexChange.addColumn(config);
					}
					data.getIndexes().add(indexChange);
				}
			}
		}
	}

	private void validate() {

		if (data.getPks().isEmpty()) {
			problemHelper.addProblem("Entity %s is missing the @Id annotation", configuration.getClassName());
		}
	}

	private void addCols() {
		List<ColumnConfig> columns = new ArrayList<>();
		for (Field field : ReflectionUtils.getAllDeclaredFields(configuration.getDefiningClass())) {
			boolean isTransient = Modifier.isTransient(field.getModifiers());
			boolean isStatic = Modifier.isStatic(field.getModifiers());
			if (isStatic || isTransient || field.isAnnotationPresent(Transient.class)) {
				continue;
			}
			Column col = entityModelReader.getColumn(field);

			
			if (field.isAnnotationPresent(Id.class)) {
				mapId(field);
			}

			else {

				boolean relationalField = relationalModel.handleIfRelational(field);
				if (!relationalField) {
					mapColumn(columns, field, col);
				}
			}

		}
		data.addColumnConfigs(columns);
	}

	private void mapColumn(List<ColumnConfig> columns, Field field, Column col) {
		ColumnConfig colConfig = new ColumnConfig();
		ConstraintsConfig constraints = new ConstraintsConfig();
		colConfig.setConstraints(constraints);

		constraints.setNullable(!field.isAnnotationPresent(NotNull.class));

		if (col != null) {
			if (col.unique()) {
				constraints.setUnique(true);
			}
		}
		


		String columnName = entityModelReader.getColumnName(field, col);
		colConfig.setName(columnName);

		
		columns.add(colConfig);

	}

	private void mapId(Field field) {
		String idColumnName = entityModelReader.getColumnName(field);
		AddPrimaryKeyChange pk = new AddPrimaryKeyChange();
		pk.setTableName(configuration.getTableName());
		pk.setColumnNames(idColumnName);
		data.getPks().add(pk);
	}

	public boolean hasProblem() {
		return problemHelper.hasProblem();
	}

	public String getTableName() {
		return configuration.getTableName();
	}

}
