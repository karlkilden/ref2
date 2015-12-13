package com.kildeen.gv.search;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.kildeen.gv.jobs.GlobalJobState;
import com.kildeen.gv.jobs.JobStarter;
import com.kildeen.gv.jobs.JobState;

@Singleton
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class SolrPostJob implements JobStarter {
	@Resource
	private TimerService timerService;

	@Inject
	SolrPostQueue solrPostQeue;

	@Inject
	private GlobalJobState jobState;

	@Timeout
	@Override
	@Lock(LockType.READ)
	public void start(final Timer timer) {
		solrPostQeue.post();
		schedule();
	}

	@Override
	public void schedule() {

		if (jobState.getState(this.getId()) == JobState.ACTIVE) {
			stop();
			timerService.createSingleActionTimer(TimeUnit.SECONDS.toMillis(2), new TimerConfig());
		}
	}

	@Lock(LockType.READ)
	public void stop() {
		Collection<Timer> timers = timerService.getTimers();
		if (timers != null) {

			for (Timer timer : timers) {
				timer.cancel();
			}
		}
	}

	@Override
	public Class<? extends JobStarter> getId() {
		return SolrPostJob.class;
	}
}
