package com.kildeen.gv;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RefEntityDTO {

	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
