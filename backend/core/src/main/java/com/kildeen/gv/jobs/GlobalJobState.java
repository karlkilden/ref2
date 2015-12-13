package com.kildeen.gv.jobs;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GlobalJobState {
	Map<Class<? extends JobStarter>, JobState> jobStates = new ConcurrentHashMap<>();

	public JobState getState(Class<? extends JobStarter> jobStarter) {
		return jobStates.computeIfAbsent(jobStarter, j -> JobState.ACTIVE);
	}

	public void deactivate(Class<? extends JobStarter> jobStarterClazz) {
		jobStates.put(jobStarterClazz, JobState.INACTIVE);
	}

	public Object activate(Class<? extends JobStarter> jobStarterClazz) {
		jobStates.put(jobStarterClazz, JobState.ACTIVE);
		return null;
	}

}
