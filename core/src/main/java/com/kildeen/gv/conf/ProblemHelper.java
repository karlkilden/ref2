package com.kildeen.gv.conf;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class ProblemHelper {

	private StringBuilder definitionProblems = new StringBuilder();

	boolean appendSeperator = false;

	public void addProblem(String message) {
		if (appendSeperator) {
			definitionProblems.append(System.lineSeparator());
		}
		definitionProblems.append(message);
		appendSeperator = !appendSeperator;
	}

	public String getMessage() {
		return definitionProblems.toString();
	}

	public void addProblem(String message, Object... parameters) {
		definitionProblems.append(String.format(message, parameters));
	}

	public boolean hasProblem() {
		return definitionProblems.length() > 0;
	}

}
