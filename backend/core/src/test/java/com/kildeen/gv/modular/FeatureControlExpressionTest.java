package com.kildeen.gv.modular;

import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FeatureControlExpressionTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void excluded_property_returns_false() throws Exception {
		FeatureControl fc = new FeatureControl();
		fc.setJobs(false);
		FeatureControlExpression fce = new FeatureControlExpression(fc);
		assertThat(fce.isExcluded("jobs")).isFalse();
	}
	
	@Test
	public void included_property_returns_true() throws Exception {
		FeatureControl fc = new FeatureControl();
		fc.setJobs(true);
		FeatureControlExpression fce = new FeatureControlExpression(fc);
		assertThat(fce.isExcluded("jobs")).isTrue();
	}
	
	@Test
	public void or_properties_does_not_exclude() throws Exception {
		FeatureControl fc = new FeatureControl();
		fc.setJobs(true);
		fc.setLiquibase(false);
		FeatureControlExpression fce = new FeatureControlExpression(fc);
		assertThat(fce.isExcluded("jobs||liquibase")).isFalse();
	}
	
	@Test
	public void and_properties_does_exclude_on_mixed() throws Exception {
		FeatureControl fc = new FeatureControl();
		fc.setJobs(true);
		fc.setLiquibase(false);
		FeatureControlExpression fce = new FeatureControlExpression(fc);
		assertThat(fce.isExcluded("jobs&&liquibase")).isTrue();
	}
	

}
