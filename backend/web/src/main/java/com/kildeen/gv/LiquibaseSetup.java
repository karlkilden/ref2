package com.kildeen.gv;

import javax.annotation.Resource;
import javax.sql.DataSource;

public class LiquibaseSetup {

	private static final String FILE_LOCATION = "liquibase/" + "liqui" + ".xml";
	private static final String DATABASECHANGELOG_TABLE_NAME = "ref_databaseChangeLog";
	private static final String DATABASECHANGELOGLOCK_TABLE_NAME = "ref_databaseChangeLogLock";

	private static final String[] FILES = new String[] { "liquibase_2015v", "liquibase_2015h", "liquibase_2016" };

	@Resource(name = "wis")
	private DataSource dataSource;

	public void executeChanges() throws Exception {
//		ChangeLogParserFactory.getInstance().register(new MyChangeLogParser());
//
//		Database database = null;
//		try (Connection connection = dataSource.getConnection();) {
//
//			Thread currentThread = Thread.currentThread();
//			ClassLoader contextClassLoader = currentThread.getContextClassLoader();
//			ResourceAccessor threadClFO = new ClassLoaderResourceAccessor(contextClassLoader);
//
//			GlobalConfiguration conf = LiquibaseConfiguration.getInstance().getConfiguration(GlobalConfiguration.class);
//			conf.setDatabaseChangeLogTableName(DATABASECHANGELOG_TABLE_NAME);
//			conf.setDatabaseChangeLogLockTableName(DATABASECHANGELOGLOCK_TABLE_NAME);
//
//			ResourceAccessor clFO = new ClassLoaderResourceAccessor();
//			ResourceAccessor fsFO = new FileSystemResourceAccessor();
//			database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
//			CompositeResourceAccessor compositeResourceAccessor = new CompositeResourceAccessor(clFO, fsFO, threadClFO);
//
//			ChangeLogParameters changeLogParameters = new ChangeLogParameters(database);
//
//			ChangeLogParser parser = ChangeLogParserFactory.getInstance().getParser(FILE_LOCATION, compositeResourceAccessor);
//			DatabaseChangeLog databaseChangeLog = parser.parse(FILE_LOCATION, null, compositeResourceAccessor);
//
//			XMLChangeLogSerializer serializer = new XMLChangeLogSerializer();
//
//			serializer.append(databaseChangeLog.getChangeSets().get(0), new File("C:/kalle/wis/domain/src/main/resources/liquibase/liqui.xml"));
//			databaseChangeLog.getChangeSets().get(0).getChanges().get(0).getClass().isAssignableFrom(CreateTableChange.class);
//			database.close();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
