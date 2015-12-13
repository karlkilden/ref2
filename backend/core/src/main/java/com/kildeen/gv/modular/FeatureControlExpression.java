package com.kildeen.gv.modular;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.deltaspike.core.api.interpreter.ExpressionInterpreter;
import org.apache.deltaspike.core.util.ExceptionUtils;

public class FeatureControlExpression implements ExpressionInterpreter<String, Boolean> {

	private Map<String, String> properties;
	private Set<String> deactivatedFeatures;

	@Override
	public Boolean evaluate(String expression) {
		return isExcluded(expression);
	}

	public Boolean isExcluded(String expression) {

		boolean exclude = true;

		if (expression.contains("||")) {
			exclude = checkOrCondition(expression);
		} else if (expression.contains("&&")) {
			exclude = checkAndCondition(expression);
		} else {
			exclude = !deactivatedFeatures.contains(expression);
		}
		return exclude;
	}

	private boolean checkAndCondition(String expression) {
		String[] expressions = expression.split("\\&\\&");

		for (String ex : expressions) {
			Boolean realInstancesIsDeactivated = deactivatedFeatures.contains(ex);
			if (!realInstancesIsDeactivated) {
				return true;
			}

		}
		return false;
	}

	private boolean checkOrCondition(String expression) {
		String[] expressions = expression.split("\\|\\|");

		for (String ex : expressions) {
			Boolean realInstancesIsDeactivated = deactivatedFeatures.contains(ex);
			if (realInstancesIsDeactivated) {
				return false; // Do not exclude the mock if real instance is
								// deactivated
			}
		}
		return true;
	}

	public FeatureControlExpression(FeatureControl fc) {
		try {
			properties = BeanUtils.describe(fc);
			deactivatedFeatures = new HashSet<>(properties.size());

			for (Entry<String, String> entry : properties.entrySet()) {
				if (isBool(entry.getValue()) && !Boolean.parseBoolean(entry.getValue())) {
					deactivatedFeatures.add(entry.getKey());
				}
			}

		} catch (Exception e) {
			ExceptionUtils.throwAsRuntimeException(e);
		}
	}

	public FeatureControlExpression() {
		this(new FeatureControlHolder().getInstance());
	}

	private boolean isBool(String value) {
		return "true".equals(value) || "false".equals(value);
	}

}
