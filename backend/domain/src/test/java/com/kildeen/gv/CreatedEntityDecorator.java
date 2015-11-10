package com.kildeen.gv;



public class CreatedEntityDecorator extends EntityDecoratorExtension {

	@Override
	public void beforeAbsorb() {
		add(this::logEntity);
	}

	public DomainEntity logEntity(DomainEntity entity) {
		System.out.println(entity);
		return entity;
	}

}
