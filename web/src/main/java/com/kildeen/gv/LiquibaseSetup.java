package com.kildeen.gv;

import java.io.File;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.transaction.Status;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import liquibase.Liquibase;
import liquibase.change.Change;
import liquibase.change.core.CreateTableChange;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.changelog.visitor.ChangeLogSyncListener;
import liquibase.configuration.GlobalConfiguration;
import liquibase.configuration.LiquibaseConfiguration;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.parser.ChangeLogParser;
import liquibase.parser.ChangeLogParserFactory;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.CompositeResourceAccessor;
import liquibase.resource.FileSystemResourceAccessor;
import liquibase.resource.ResourceAccessor;
import liquibase.serializer.core.xml.XMLChangeLogSerializer;

import org.apache.activemq.store.kahadb.disk.journal.Journal;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.slf4j.Logger;
import org.w3c.dom.Document;

import com.google.common.util.concurrent.Uninterruptibles;



public class LiquibaseSetup  {

	private static final String FILE_LOCATION = "liquibase/" + "liqui" + ".xml";
	private static final String DATABASECHANGELOG_TABLE_NAME ="ref_databaseChangeLog";
	private static final String DATABASECHANGELOGLOCK_TABLE_NAME ="ref_databaseChangeLogLock";

	private static final String[] FILES = new String[] { "liquibase_2015v", "liquibase_2015h", "liquibase_2016" };

	@Resource(name = "ref2")
	private DataSource dataSource;





	public void executeChanges() throws Exception {
		ChangeLogParserFactory.getInstance().register(new MyChangeLogParser());



		
		Database database = null;
		try (Connection connection = dataSource.getConnection();) {

			Thread currentThread = Thread.currentThread();
			ClassLoader contextClassLoader = currentThread.getContextClassLoader();
			ResourceAccessor threadClFO = new ClassLoaderResourceAccessor(contextClassLoader);

			GlobalConfiguration conf = LiquibaseConfiguration.getInstance().getConfiguration(GlobalConfiguration.class);
			conf.setDatabaseChangeLogTableName(DATABASECHANGELOG_TABLE_NAME);
			conf.setDatabaseChangeLogLockTableName(DATABASECHANGELOGLOCK_TABLE_NAME);

			ResourceAccessor clFO = new ClassLoaderResourceAccessor();
			ResourceAccessor fsFO = new FileSystemResourceAccessor();
			database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
			CompositeResourceAccessor compositeResourceAccessor = new CompositeResourceAccessor(clFO, fsFO, threadClFO);

	        ChangeLogParameters changeLogParameters = new ChangeLogParameters(database);

	        ChangeLogParser parser = ChangeLogParserFactory.getInstance().getParser(FILE_LOCATION, compositeResourceAccessor);
	        DatabaseChangeLog databaseChangeLog = parser.parse(FILE_LOCATION, null, compositeResourceAccessor);

	        XMLChangeLogSerializer serializer = new XMLChangeLogSerializer();
	        
	        serializer.append(databaseChangeLog.getChangeSets().get(0), new File("C:/kalle/ref2/domain/src/main/resources/liquibase/liqui.xml"));
	        databaseChangeLog.getChangeSets().get(0).getChanges().get(0).getClass().isAssignableFrom(CreateTableChange.class);
	        database.close();

		} catch (Exception e) {
e.printStackTrace();
		}
	}

}
