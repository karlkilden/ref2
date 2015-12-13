package com.kildeen.gv.entity.gen;

import java.util.List;

import com.kildeen.gv.entity.gen.process.EntityToLiquibaseProcessor;
import com.kildeen.gv.entity.gen.process.ProcessChain;

public class DiffHandler implements EntityToLiquibaseProcessor {

	public List<CurrentModelData> diff(Object any, Object any2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void process(ProcessChain chain) {

		chain.setDiffResult(diff(null,null));
		
	}

}
