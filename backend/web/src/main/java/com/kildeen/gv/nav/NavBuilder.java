package com.kildeen.gv.nav;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.core.api.config.view.metadata.ViewConfigResolver;

import com.kildeen.ref.JsfRequestContext;

@ApplicationScoped
public class NavBuilder {

	private static final String SETUP_PAGE = "setup";

	private final List<Class<? extends ViewConfig>> pages = new ArrayList<>();

	@Inject
	JsfRequestContext JsfRequestContext;

	@Inject
	private ViewConfigResolver viewConfigResolver;

	private Map<Class<? extends ViewConfig>, List<Class<? extends ViewConfig>>> tabs = new ConcurrentHashMap<>();

	public List<Class<? extends ViewConfig>> getPages() {
		return pages;
	}

	@PostConstruct
	private void extracted() {
		viewConfigResolver.getViewConfigDescriptors().forEach(conf -> pages.add(conf.getConfigClass()));
	}

	public List<Class<? extends ViewConfig>> getTabs() {
		Class<? extends ViewConfig> currentClazz = JsfRequestContext.getViewConfigClass();

		return tabs.computeIfAbsent(currentClazz, (clazz -> calcTabs(clazz)));
	}

	public List<Class<? extends ViewConfig>> calcTabs(Class<? extends ViewConfig> currentClazz) {
		Class<?> parent = currentClazz.getEnclosingClass();
		List<Class<? extends ViewConfig>> siblingsAndSelf = viewConfigResolver.getViewConfigDescriptors().stream()
				.filter(conf -> parent == conf.getConfigClass().getEnclosingClass()).map(conf -> conf.getConfigClass()).collect(toList());
		;
		Collections.reverse(siblingsAndSelf);
		return siblingsAndSelf;
	}


}
