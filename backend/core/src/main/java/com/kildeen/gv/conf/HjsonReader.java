package com.kildeen.gv.conf;

import static java.nio.file.Files.readAllBytes;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.deltaspike.core.util.ExceptionUtils;
import org.hjson.JsonValue;
import org.hjson.Stringify;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HjsonReader {
	private static final ObjectMapper MAPPER = new ObjectMapper();

	public static String getjson(Path path) throws IOException {
		final String confString = new String(readAllBytes(path));
		final String jsonString = JsonValue.readHjson(confString).toString(Stringify.PLAIN);
		return jsonString;
	}

	public static <E> E getObject(Path path, Class<? extends E> clazz) {
		try {
			return MAPPER.readValue(getjson(path), clazz);
		} catch (Exception e) {
			ExceptionUtils.throwAsRuntimeException(e);
		}
		return null;
	}
}
