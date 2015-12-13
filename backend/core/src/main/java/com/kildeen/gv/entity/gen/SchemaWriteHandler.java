package com.kildeen.gv.entity.gen;

import java.util.List;

import com.kildeen.gv.entity.gen.process.EntityToLiquibaseProcessor;
import com.kildeen.gv.entity.gen.process.ProcessChain;

public class SchemaWriteHandler implements EntityToLiquibaseProcessor {


	public void writeDiff(List<CurrentModelData> diffResult) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(ProcessChain chain) {

		writeDiff(chain.getDiffResult());
	}



}
