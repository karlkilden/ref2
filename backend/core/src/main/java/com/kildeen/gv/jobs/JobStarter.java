package com.kildeen.gv.jobs;

import javax.ejb.Timer;

public interface JobStarter {

	void schedule();

	void start(final Timer timer);

	void stop();

	Class<? extends JobStarter> getId();
}
