package com.kildeen.gv;

import java.io.Serializable;
import java.util.logging.Level;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.apache.deltaspike.core.api.lifecycle.Initialized;
import org.apache.deltaspike.core.api.provider.BeanProvider;

import com.kildeen.gv.jobs.JobMaster;
import com.kildeen.gv.jobs.JobStarter;
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
	
	@Inject
	private LiquibaseSetup liquibase;
	
	@Inject
	private JobMaster jobMaster;


	private java.util.logging.Logger logger;

	public void boot(@Observes @Initialized final ServletContext context) {
		turnOffNoisyLogger();
		for (JobStarter jobStarter : BeanProvider.getContextualReferences(JobStarter.class, false)) {
			jobStarter.schedule();
		}
		
		asyncPollService.postAll();
		try {
			liquibase.executeChanges();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void turnOffNoisyLogger() {
		// Must keep a handle to logger or log level could be reset
		logger = java.util.logging.Logger.getLogger("org.apache.geronimo.connector.work.WorkerContext");
		logger.setLevel(Level.WARNING);
	}


}
