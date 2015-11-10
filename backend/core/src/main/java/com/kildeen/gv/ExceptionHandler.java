package com.kildeen.gv;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ApplicationScoped
public class ExceptionHandler {
	private static final Logger LOG = LogManager.getLogger();

	public void log(@Observes ExceptionEvent e) {
		LOG.error(e);
	}

}
