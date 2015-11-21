package com.kildeen.gv.entity.gen;

import org.junit.Before;
import org.junit.Test;

public class LiquibaseParseIntegrationTest {

	@Test
	public void test_that_a_changelog_xml_gets_parsed_correctly() {
		helper = new LiquibaseReadHelper("liquibasetest/liqui1.xml");
		
		TableModelHandler builder = new TableModelHandler(helper);
		
		
//		for (Entry<EntityConfiguration<?>, List<Change>> entry : builder.getChangeSets().entrySet()) {
//			try {
//				CurrentModel currentTableModel = new CurrentTableModel(entry.getKey(), entry.getValue());
//				assertThat(currentTableModel.getData().getColumnConfigs()).isNotEmpty();
//				
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		

	}

	private LiquibaseReadHelper helper;

	@Before
	public void setUp() throws Exception {

	}

}
