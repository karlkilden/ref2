package com.kildeen.gv.entity.gen;

import static java.nio.file.Files.readAllBytes;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.enterprise.context.ApplicationScoped;

import org.apache.deltaspike.core.util.ExceptionUtils;
import org.hjson.JsonValue;
import org.hjson.Stringify;


import com.fasterxml.jackson.databind.ObjectMapper;

@ApplicationScoped
public class CacheReader {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	private String getjson(Path path) throws IOException {
		final String hjson = new String(readAllBytes(path));
		final String jsonString = JsonValue.readHjson(hjson).toString(Stringify.PLAIN);
		return jsonString;
	}

	public <E> E getObject(Path path, Class<? extends E> clazz) {
		try {
			return MAPPER.readValue(getjson(path), clazz);
		} catch (Exception e) {
			ExceptionUtils.throwAsRuntimeException(e);
		}
		return null;
	}

	public <E> E getObject(String path, Class<? extends E> clazz) {
		return getObject(Paths.get(path), clazz);
	}
}
