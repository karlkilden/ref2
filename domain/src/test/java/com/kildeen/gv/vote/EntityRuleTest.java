package com.kildeen.gv.vote;

import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.junit.Assert.assertEquals;

import javax.persistence.SequenceGenerator;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.Description;
import org.mockito.Mockito;

import com.kildeen.ref.BaseEntity;

@Ignore
@SuppressWarnings({"unchecked", "rawtypes"})
public class EntityRuleTest {

	EntityRule rule = new EntityRule();

	@Test
	public void test_that_missing_generator_causes_exception() throws Throwable {

		Description desc = Mockito.mock(Description.class);
		Mockito.when(desc.getTestClass()).thenReturn((Class) MissingGeneratorTest.class);
		try {
			rule.apply(Mockito.mock(org.junit.runners.model.Statement.class), desc).evaluate();
		} catch (Exception e) {
			assertThat(e.getMessage()).contains(SequenceGenerator.class.getSimpleName());
		}
	}

	@Test
	public void test_that_no_field_with_type_is_like_testClassName_causes_exception() throws Throwable {

		Description desc = Mockito.mock(Description.class);
		Mockito.when(desc.getTestClass()).thenReturn((Class) MissingEntityTest.class);
		try {
			rule.apply(Mockito.mock(org.junit.runners.model.Statement.class), desc).evaluate();
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Field with name missingEntity is required");
		}
	}

	@Test
	public void test_that_wrong_sequence_gen_name_causes_exception() throws Throwable {

		Description desc = Mockito.mock(Description.class);
		Mockito.when(desc.getTestClass()).thenReturn((Class) WrongGeneratorNameTest.class);
		try {
			rule.apply(Mockito.mock(org.junit.runners.model.Statement.class), desc).evaluate();
		} catch (Exception e) {
			assertEquals(e.getMessage(), "You must specify a proper sequenceName. It should be: wrongGeneratorName_seq");
		}
	}

	@Test
	public void test_that_all_ok_no_exception() throws Throwable {

		Description desc = Mockito.mock(Description.class);
		Mockito.when(desc.getTestClass()).thenReturn((Class) AllOkTest.class);

		rule.apply(Mockito.mock(org.junit.runners.model.Statement.class), desc).evaluate();

	}

	private class MissingGeneratorTest {

		public class MissingGenerator {

		}

		private MissingGenerator missingGenerator;

	}

	private class WrongGeneratorNameTest {
		@SequenceGenerator(name = BaseEntity.GVSEQ, sequenceName = "poll_seq")
		public class WrongGeneratorName {

		}

		private WrongGeneratorName wrongGeneratorName;

	}

	private class MissingEntityTest {

	}
	private class AllOkTest {
		@SequenceGenerator(name = BaseEntity.GVSEQ, sequenceName = "allOk_seq")
		public class AllOk {

		}

		private AllOk allOk;

	}
}
