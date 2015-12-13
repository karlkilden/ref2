package com.kildeen.gv.modular;

public class FeatureControl {
	
	private boolean solr = true;
	private boolean liquibase = true;
	private boolean jobs = true;	
	
	public boolean isSolr() {
		return solr;
	}

	public void setSolr(boolean solr) {
		this.solr = solr;
	}

	public boolean isLiquibase() {
		return liquibase;
	}

	public void setLiquibase(boolean liquibase) {
		this.liquibase = liquibase;
	}

	FeatureControl() {

	}

	public boolean isJobs() {
		return jobs;
	}

	public void setJobs(boolean jobs) {
		this.jobs = jobs;
	}

}
