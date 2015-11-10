package com.kildeen.gv.vote;

import java.lang.reflect.Field;

import javax.persistence.SequenceGenerator;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import com.google.common.base.CaseFormat;

public class EntityRule implements TestRule {

	@Override
	public Statement apply(Statement base, Description description) {
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				
				
				
				Class<?> clazz = description.getTestClass();
				
				if (clazz != null)  {
					base.evaluate();
					return;
				}
				
				String entity = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, clazz.getSimpleName().replace("Test", ""));
				Field f;
				try {
					f = clazz.getDeclaredField(entity);
				} catch (Exception e) {
					throw new NoSuchFieldException(String.format("Field with name %s is required", entity));
				}

				SequenceGenerator gen;
				try {
					gen = f.getType().getAnnotation(SequenceGenerator.class);
					if (gen == null) {
						throw new NullPointerException();
					}
				} catch (Exception e) {
					String properName = String.format("@SequenceGenerator(name = BaseEntity.GVSEQ, sequenceName = \"%s_seq\"", entity);
					throw new NullPointerException(String.format("missing a sequenceGenerator: pls use: %s",properName));
				}

				String sequenceName = entity + "_seq";
				if (!gen.sequenceName().equals(sequenceName)) {
					throw new IllegalArgumentException(String.format("You must specify a proper sequenceName. It should be: %s", sequenceName));
				}

				base.evaluate();
			}
		};
	}

}