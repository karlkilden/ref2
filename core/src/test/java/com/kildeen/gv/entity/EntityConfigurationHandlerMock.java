package com.kildeen.gv.entity;

import javax.annotation.PostConstruct;

import com.kildeen.gv.MethodRunner;

public class EntityConfigurationHandlerMock extends EntityConfigurationContext {

	public EntityConfigurationHandlerMock() {
		MethodRunner.runMethod(PostConstruct.class, this);
	}
	
}
