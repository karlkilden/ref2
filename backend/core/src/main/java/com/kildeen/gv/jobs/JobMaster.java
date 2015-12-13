package com.kildeen.gv.jobs;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.jmx.JmxManaged;
import org.apache.deltaspike.core.api.jmx.MBean;

@MBean(category = "com.kildeen")
@ApplicationScoped
public class JobMaster {

	private Map<Class<? extends JobStarter>, JobRegistration> tickets = new ConcurrentHashMap<>();

	@Inject
	private Instance<JobStarter> jobstarters;
	
	@Inject
	private GlobalJobState globalJobState;
	
	
	@JmxManaged(description= "deactivate all")
	public void deactivate() {
		jobstarters.forEach(j -> globalJobState.deactivate(j.getId()));
	}
	
	@JmxManaged(description= "activate all")
	public void activate() {
		jobstarters.forEach(j -> globalJobState.activate(j.getId()));
	}
	
	@JmxManaged(description= "activate all")
	public void cancel() {
		jobstarters.forEach(j -> j.stop());
	}
	
	
	@JmxManaged(description= "start all")
	public void start() {
		jobstarters.forEach(j -> j.schedule());
	}
	
	

}
