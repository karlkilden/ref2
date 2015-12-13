package com.kildeen.gv.entity;

import static com.google.common.base.MoreObjects.firstNonNull;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import javax.persistence.Table;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;

/**
 * Each entity needs to have a configuration listed in
 * {@link EntityConfigurationContext}. This is a single place of setup for a
 * single entity
 * 
 * @author Kalle
 * @param <T>
 *            The entity to configure
 * 
 */
public class EntityConfiguration<T> {

	private Class<?> clazz;
	private boolean solr;
	private boolean restIn;
	private boolean restOut;
	private Class<?> dtoClazz;
	private Function<Object, Object> dtoMappingFunction;
	private String tableName;
	private List<PreviousTableName> tableNames;
	private String currentTableNameSince;

	private EntityConfiguration() {
	};

	public static <T> EntityConfiguration<T> create(Class<T> clazz) {
		EntityConfiguration<T> conf = new EntityConfiguration<>();
		conf.clazz = clazz;
		Table table = clazz.getAnnotation(Table.class);
		String nameFromTable = table == null ? null : table.name();
		conf.tableName = firstNonNull(Strings.emptyToNull(nameFromTable), clazz.getSimpleName());
		return conf;
	}

	public static <T> EntityConfiguration<T> create(Class<T> clazz, Class<?> dtoClazz) {
		EntityConfiguration<T> ec = create(clazz);
		ec.dtoClazz = dtoClazz;
		return ec;
	}

	EntityConfiguration<T> solr() {
		solr = true;
		return this;
	}

	EntityConfiguration<T> restIn() {
		restIn = true;
		return this;
	}

	EntityConfiguration<T> restOut() {
		restOut = true;
		return this;
	}

	EntityConfiguration<T> previousTableName(PreviousTableName previousTableName) {
		tableNames.add(previousTableName);
		return this;
	}

	EntityConfiguration<T> hadCurrentTableNameSince(String id) {
		this.currentTableNameSince = id;
		return this;
	}

	public EntityConfiguration<T> mappingMethod(Function<Object, Object> dtoMappingFunction) {
		this.dtoMappingFunction = dtoMappingFunction;
		return this;
	}

	public boolean hasDTO() {
		return dtoClazz != null;
	}

	public Class<?> getDefiningClass() {
		return clazz;
	}

	public Class<?> getDTOClass() {
		return dtoClazz;
	}

	public Function<Object, Object> getMappingMethod() {
		return dtoMappingFunction;
	}

	public EntityConfiguration<T> build() {

		if (dtoClazz != null || dtoMappingFunction != null || solr || restIn || restOut) {
			Objects.requireNonNull(dtoClazz, "dtoClazz must not be null when using rest or solr, make sure correct create method is used");
			Objects.requireNonNull(dtoMappingFunction,
					"dtoMappingFunction must not be null when using rest or solr, make sure object was build correctly");
		}

		String tableSince = MoreObjects.firstNonNull(currentTableNameSince, "1");
		if (currentTableNameSince != null) {
			tableNames.add(new PreviousTableName(tableSince, getTableName()));
		}

		return this;
	}

	public boolean hasSolr() {
		return solr;
	}

	public String getTableName() {
		return tableName;
	}

	public String getClassName() {
		return clazz.getSimpleName();
	}

}
