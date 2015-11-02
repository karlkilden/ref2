package com.kildeen.gv;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@MappedSuperclass
public class BaseEntity {
	public static final String GVSEQ = "gvseq";

	private static final long serialVersionUID = 1L;

	@Version
	private long version;


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated")
	private Date updated;

	private transient Date loadTime;

	@PreUpdate
	@PrePersist
	protected void onUpdate() {
		updated = new Date();
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(final long version) {
		this.version = version;
	}


}
