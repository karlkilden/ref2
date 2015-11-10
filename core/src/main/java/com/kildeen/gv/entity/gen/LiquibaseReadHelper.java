package com.kildeen.gv.entity.gen;

import liquibase.changelog.DatabaseChangeLog;
import liquibase.exception.ChangeLogParseException;
import liquibase.exception.LiquibaseException;
import liquibase.parser.ChangeLogParser;
import liquibase.parser.ChangeLogParserFactory;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.CompositeResourceAccessor;
import liquibase.resource.FileSystemResourceAccessor;
import liquibase.resource.ResourceAccessor;

public class LiquibaseReadHelper {
	private static final String FILE_LOCATION = "C:/kalle/ref2/domain/src/main/resources/liquibase/liqui.xml";
	DatabaseChangeLog databaseChangeLog;

	public DatabaseChangeLog get() throws Exception {
		return databaseChangeLog;
		// XMLChangeLogSerializer serializer = new XMLChangeLogSerializer();
		// serializer.append(databaseChangeLog.getChangeSets().get(0), new
		// File(FILE_LOCATION));
		// databaseChangeLog.getChangeSets().get(0).getChanges().get(0).getClass().isAssignableFrom(CreateTableChange.class);
	}

	public LiquibaseReadHelper(String fileLocation) {
		Thread currentThread = Thread.currentThread();
		ClassLoader contextClassLoader = currentThread.getContextClassLoader();
		ResourceAccessor threadClFO = new ClassLoaderResourceAccessor(contextClassLoader);

		ResourceAccessor clFO = new ClassLoaderResourceAccessor();
		ResourceAccessor fsFO = new FileSystemResourceAccessor();
		CompositeResourceAccessor compositeResourceAccessor = new CompositeResourceAccessor(clFO, fsFO, threadClFO);

		try {
			ChangeLogParser parser = ChangeLogParserFactory.getInstance().getParser(fileLocation, compositeResourceAccessor);
			databaseChangeLog = parser.parse(fileLocation, null, compositeResourceAccessor);
		} catch (ChangeLogParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LiquibaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
