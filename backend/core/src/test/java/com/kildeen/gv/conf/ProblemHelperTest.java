package com.kildeen.gv.conf;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ProblemHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test_that_problem_gets_registered() throws Exception {

		ProblemHelper helper = new ProblemHelper();

		String message = "New problem!";
		helper.addProblem(message);

		assertEquals(helper.getMessage(), message);

	}

	@Test
	public void multiple_problems_are_seperated() throws Exception {

		ProblemHelper helper = new ProblemHelper();

		String message = "New problem!";
		helper.addProblem(message);
		helper.addProblem(message);

		assertEquals(2, helper.getMessage().split(System.lineSeparator()).length);
	}

	@Test
	public void parameters_should_be_formatted_in() throws Exception {
		ProblemHelper helper = new ProblemHelper();

		String message = "New problem %s%s";
		helper.addProblem(message, "withParam", "!");
		assertEquals(helper.getMessage(), "New problem withParam!");
	}

	@Test
	public void has_problem_should_return_false_when_no_problem_has_been_added() {
		ProblemHelper helper = new ProblemHelper();
		assertFalse(helper.hasProblem());
	}
	
	@Test
	public void has_problem_should_return_true_when_problem_has_been_added() {
		ProblemHelper helper = new ProblemHelper();

		String message = "New problem!";
		helper.addProblem(message);
		assertTrue(helper.hasProblem());
	}

}
