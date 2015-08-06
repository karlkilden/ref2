package com.kildeen.gv;

import java.io.Serializable;
import java.util.logging.Level;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.apache.deltaspike.core.api.lifecycle.Initialized;

import com.kildeen.gv.poll.AsyncStartup;


/**
 * 
 * @author: Karl Kilden
 */
@ApplicationScoped
public class ApplicationStart implements Serializable {

	private static final long serialVersionUID = 1L;


	@Inject
	private AsyncStartup asyncPollService;


	private java.util.logging.Logger logger;

	public void boot(@Observes @Initialized final ServletContext context) {
		turnOffNoisyLogger();
		asyncPollService.postAll();
	}

	private void turnOffNoisyLogger() {
		// Must keep a handle to logger or log level could be reset
		logger = java.util.logging.Logger.getLogger("org.apache.geronimo.connector.work.WorkerContext");
		logger.setLevel(Level.WARNING);
	}


}
