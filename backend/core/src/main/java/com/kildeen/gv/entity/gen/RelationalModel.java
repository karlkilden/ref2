package com.kildeen.gv.entity.gen;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.kildeen.gv.entity.EntityConfiguration;
import com.kildeen.gv.entity.EntityConfigurationHandler;
import com.kildeen.gv.poll.ReflectionUtils;

import liquibase.change.core.AddForeignKeyConstraintChange;

public class RelationalModel {
	private List<Field> relationFields = new ArrayList<>();
	private List<Field> singleGenericTypeFields = new ArrayList<>();
	private List<Field> mapGenericFields = new ArrayList<>();
	private Map<Class<?>, CurrentEntityModel> allEntityModels;
	private EntityModelReader entityModelReader = new EntityModelReader();
	private EntityConfiguration<?> configuration;
	private EntityConfigurationHandler handler = EntityConfigurationHandler.getInstance();
	private CurrentModelData data;

	public void build(CurrentModelData data, EntityConfiguration<?> configuration, Map<Class<?>, CurrentEntityModel> allEntityModels) {
		this.data = data;
		this.configuration = configuration;
		this.allEntityModels = allEntityModels;

		addForeignKeys();
		handleSigleGenericType();
	}

	private void handleSigleGenericType() {

		// TODO: implement

		for (Field field : singleGenericTypeFields) {
			Class<?> genericType = ReflectionUtils.getGenericTypeFromCollection(field);

			if (field.isAnnotationPresent(OneToMany.class)) {
				OneToMany oneToMany = field.getAnnotation(OneToMany.class);

				if (isNotEmpty(oneToMany.mappedBy())) {
					continue;
				}

			}

		}

	}

	private void addForeignKeys() {

		for (Field field : relationFields) {
			CurrentEntityModel fieldModel = allEntityModels.get(field.getType());
			AddForeignKeyConstraintChange fkChange = new AddForeignKeyConstraintChange();
			Column columnAnno = field.getAnnotation(Column.class);
			String colName = entityModelReader.getColumnName(field, columnAnno);
			fkChange.setBaseColumnNames(colName);
			fkChange.setBaseTableName(configuration.getTableName());
			fkChange.setReferencedColumnNames(fieldModel.getData().getPk().getColumnNames());
			fkChange.setReferencedTableName(fieldModel.getData().getPk().getTableName());
			data.getFks().add(fkChange);
		}

	}

	public boolean handleIfRelational(Field field) {
		boolean handled = false;

		if (handler.findByClass(field.getType()) != null) {
			relationFields.add(field);
			handled = true;
		}

		if (field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(ManyToMany.class)) {
			handled = true;
		}

		if (field.getType().isAssignableFrom(Collection.class)) {

			if (field.getType() == Map.class) {
				mapGenericFields.add(field);
			} else {
				singleGenericTypeFields.add(field);
			}
		}

		return handled;
	}
}
