package com.kildeen.gv.jobs;

public class JobRegistration {

	private volatile boolean valid = true;
	private JobStarter jobStarter;

	public JobRegistration(JobStarter jobStarter, boolean active) {
		this.jobStarter = jobStarter;
		this.valid = active;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public JobRegistration deactivate() {
		valid = false;
		return this;
	}

	public JobRegistration cancel() {
		jobStarter.stop();
		return this;
	}

	public void activate() {
		this.valid = true;
	}

	public void start() {
		jobStarter.schedule();
	}

}
