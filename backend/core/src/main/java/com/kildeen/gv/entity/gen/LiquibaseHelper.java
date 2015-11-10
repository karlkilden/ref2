package com.kildeen.gv.entity.gen;

import liquibase.changelog.DatabaseChangeLog;
import liquibase.exception.LiquibaseException;
import liquibase.parser.ChangeLogParser;
import liquibase.parser.ChangeLogParserFactory;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.CompositeResourceAccessor;
import liquibase.resource.FileSystemResourceAccessor;
import liquibase.resource.ResourceAccessor;

public class LiquibaseHelper {
	private static final String FILE_LOCATION = "C:/kalle/ref2/domain/src/main/resources/liquibase/liqui.xml";
	DatabaseChangeLog databaseChangeLog;

	public DatabaseChangeLog get() throws Exception {
		return databaseChangeLog;
		// XMLChangeLogSerializer serializer = new XMLChangeLogSerializer();
		// serializer.append(databaseChangeLog.getChangeSets().get(0), new
		// File(FILE_LOCATION));
		// databaseChangeLog.getChangeSets().get(0).getChanges().get(0).getClass().isAssignableFrom(CreateTableChange.class);
	}

	private LiquibaseHelper() throws LiquibaseException {
		Thread currentThread = Thread.currentThread();
		ClassLoader contextClassLoader = currentThread.getContextClassLoader();
		ResourceAccessor threadClFO = new ClassLoaderResourceAccessor(contextClassLoader);

		ResourceAccessor clFO = new ClassLoaderResourceAccessor();
		ResourceAccessor fsFO = new FileSystemResourceAccessor();
		CompositeResourceAccessor compositeResourceAccessor = new CompositeResourceAccessor(clFO, fsFO, threadClFO);

		ChangeLogParser parser = ChangeLogParserFactory.getInstance().getParser(FILE_LOCATION, compositeResourceAccessor);
		DatabaseChangeLog databaseChangeLog = parser.parse(FILE_LOCATION, null, compositeResourceAccessor);
	}

}
