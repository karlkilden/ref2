package com.kildeen.gv.entity.gen;

import java.util.Map;

import com.kildeen.gv.entity.gen.process.EntityToLiquibaseProcessor;
import com.kildeen.gv.entity.gen.process.ProcessChain;

public class EntityModelHandler implements EntityToLiquibaseProcessor {

	public Map<Class<?>, CurrentEntityModel> mapEntities() {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(ProcessChain chain) {
		chain.setMappedEntities(mapEntities());		
	}

}
