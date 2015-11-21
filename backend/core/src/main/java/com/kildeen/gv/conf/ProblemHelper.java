package com.kildeen.gv.conf;

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
