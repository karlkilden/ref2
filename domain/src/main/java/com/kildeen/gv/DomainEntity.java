package com.kildeen.gv;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.apache.bval.constraints.NotEmpty;

/**
 * @author Karl Kilden
 *
 */
@MappedSuperclass
public class DomainEntity extends LongEntity implements Serializable {

	@NotNull
	@NotEmpty
	protected String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
