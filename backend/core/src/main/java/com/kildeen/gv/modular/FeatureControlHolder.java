package com.kildeen.gv.modular;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kildeen.gv.conf.HjsonReader;

public class FeatureControlHolder {

	private static final Logger LOG = LogManager.getLogger();
	private static final FeatureControl INSTANCE = buildSingleton();

	public static FeatureControl getInstance() {
		return INSTANCE;
	}

	static FeatureControl buildSingleton() {
		try {
			Path currentRelativePath = Paths.get("");
			String path = System.getProperty("com.kildeen.featureControl");
			if (path == null) {
				path = "src/main/resources/FeatureControl.hjson";
			}
			currentRelativePath = Paths.get(path);
			FeatureControl fromFile = HjsonReader.getObject(currentRelativePath, FeatureControl.class);
			return fromFile;
		} catch (Exception e) {
			LOG.warn("Problem detecting features. Will use defaults", e);
			return new FeatureControl();
		}
	}

}
