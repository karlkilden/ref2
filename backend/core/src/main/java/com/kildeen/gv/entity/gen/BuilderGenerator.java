package com.kildeen.gv.entity.gen;

import java.lang.reflect.Field;
import java.util.Set;

import org.apache.deltaspike.core.util.ReflectionUtils;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Sets;

public class BuilderGenerator {

	private static final String METHOD_NAME = "#METHOD_NAME";
	private static final String TEMPLATE = "public class #CLASS_NAMEBuilder {\r\n\r\nprivate #CLASS_NAMEBuilder () {\r\n}\r\n\r\npublic static #CLASS_NAMEBuilder getInstance() {\r\nreturn new #CLASS_NAMEBuilder();\r\n}\r\n\r\nprivate #CLASS_NAME #CLASS_VAR = new #CLASS_NAME();\r\n\r\n#METHODS\r\n\r\n\r\n\r\npublic #CLASS_NAME build() {\r\nreturn this.#CLASS_VAR;\r\n}\r\n\r\n}";
	private static final String METHOD_TEMPLATE = "public #CLASS_NAMEBuilder #METHOD_NAME {\r\nthis.#CLASS_VAR.#FIELD_NAME = #FIELD_NAME;\r\nreturn this;\r\n}\r\n";
	private static final String CLASS_NAME = "#CLASS_NAME";
	private static final String CLASS_VAR = "#CLASS_VAR";
	private static final String METHODS = "#METHODS";
	private static final Set<String> GENERICS = Sets.newHashSet("List");

	public static void main(String[] args) {
		Class<?> clazz = ColumnDefinition.class;

		String varName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, clazz.getSimpleName());

		StringBuilder sb = new StringBuilder();
		for (Field f : ReflectionUtils.getAllDeclaredFields(clazz)) {
			f.setAccessible(true);
			String methodRes = METHOD_TEMPLATE.replace(METHOD_NAME, f.getName() + "(" + f.getType().getSimpleName() + " " + f.getName() + ")");
			methodRes = methodRes.replace("#FIELD_NAME", f.getName());
			if (GENERICS.contains(f.getType().getSimpleName())) {
				methodRes = methodRes.replace(f.getType().getSimpleName()+" ", f.getType().getSimpleName()+"<" + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, f.getName().substring(0, f.getName().length()-1)) + "> ");
			}
			sb.append(methodRes).append(System.lineSeparator());

		}
		String res = TEMPLATE;
		res = res.replace(METHODS, sb.toString());
		res = res.replace(CLASS_VAR, varName);
		res = res.replace(CLASS_NAME, clazz.getSimpleName());
		if (res.contains("List<"))
		res = "import java.util.List;" + System.lineSeparator() + res;
		if (res.contains("Set<"))
			res = "import java.util.Set;" + System.lineSeparator() + res;
		if (res.contains("Map<"))
			res = "import java.util.Map;" + System.lineSeparator() + res;
		
		res = "package " + clazz.getPackage().getName() +";"+ System.lineSeparator() + res;
		System.out.println(res);
	}

}
