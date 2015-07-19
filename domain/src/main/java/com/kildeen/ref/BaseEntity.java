package com.kildeen.ref;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.apache.bval.constraints.NotEmpty;

/**
 * @author Karl Kilden
 *
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

	public static final String GVSEQ = "gvseq";

	private static final long serialVersionUID = 1L;

	@Version
	private long version;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated")
	private Date updated;

	@NotNull
	@NotEmpty
	protected String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public long getId() {
		return id;
	}

	public String baseToString() {
		return " id: " + id;
	}

	public boolean isNew() {
		return id == 0;
	}

	public int baseHashCode() {
		if (isNew()) {
			// Can't hashcode yet
			return super.hashCode();
		}
		return Objects.hashCode(getId());
	}

	/**
	 * @param other
	 *            entity
	 * @return True if the id is equal. Null if both entites are new.
	 */
	public boolean baseEquals(final BaseEntity other) {

		long otherId = other.getId();
		long thisId = this.getId();
		boolean equal = (thisId == otherId) && thisId != 0;

		if (!equal) {
			if (isNew() && other.isNew()) {
				return super.equals(other);
			}
		}
		return equal;
	}

}
