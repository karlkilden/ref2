package com.kildeen.gv.entity;

import java.util.Objects;
import java.util.function.Function;

import com.kildeen.ref.BaseEntity;

/**
 * Each entity needs to have a configuration listed in {@link EntityConfigurationHandler}. This is a single place of setup for a single entity
 * 
 * @author Kalle
 * @param <T> The entity to configure
 * 
 */
public class EntityConfiguration<T extends BaseEntity> {

	private Class<? extends BaseEntity> clazz;
	private boolean solr;
	private boolean restIn;
	private boolean restOut;
	private Class<?> dtoClazz;
	private Function<Object, Object> dtoMappingFunction;

	public static <T extends BaseEntity> EntityConfiguration<T> create(Class<T> clazz) {
		EntityConfiguration<T> conf = new EntityConfiguration<T>();
		conf.clazz = clazz;
		return conf;
	}

	public static <T extends BaseEntity> EntityConfiguration<T> create(Class<T> clazz, Class<?> dtoClazz) {
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

	public EntityConfiguration<T> mappingMethod(Function<Object, Object> dtoMappingFunction) {
		this.dtoMappingFunction = dtoMappingFunction;
		return this;
	}

	public boolean hasDTO() {
		return dtoClazz != null;
	}

	public Class<?> getClazz() {
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
		return this;
	}

	public boolean hasSolr() {
		return solr;
	}

}
