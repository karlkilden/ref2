package com.kildeen.gv.entity.gen;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class LiquibaseReadHelperTest {

	private LiquibaseReadHelper helper;

	@Before
	public void setUp() throws Exception {
		
		
	}
	
	@Test
	public void changelog_should_contain_one_changeset_when_file_has_one() throws Exception {
		helper = new LiquibaseReadHelper("liquibasetest/liqui1.xml");
		assertThat(helper.get().getChangeSets()).hasSize(1);
	}

}
