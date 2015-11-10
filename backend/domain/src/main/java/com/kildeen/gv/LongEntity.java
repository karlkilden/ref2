package com.kildeen.gv;

import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class LongEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	public boolean baseEquals(final LongEntity other) {

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
