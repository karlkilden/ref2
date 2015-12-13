package com.kildeen.gv.entity.gen.process;

import java.util.List;
import java.util.Map;

import com.kildeen.gv.entity.gen.CurrentEntityModel;
import com.kildeen.gv.entity.gen.CurrentModelData;
import com.kildeen.gv.entity.gen.CurrentTableModel;

public class ProcessChain {
	private Map<Class<?>, CurrentTableModel> mappedTables;
	private Map<Class<?>, CurrentEntityModel> mappedEntities;
	private List<CurrentModelData> diffResult;
	public Map<Class<?>, CurrentTableModel> getMappedTables() {
		return mappedTables;
	}

	public void setMappedTables(Map<Class<?>, CurrentTableModel> mappedTables) {
		this.mappedTables = mappedTables;
	}

	public Map<Class<?>, CurrentEntityModel> getMappedEntities() {
		return mappedEntities;
	}

	public void setMappedEntities(Map<Class<?>, CurrentEntityModel> mappedEntities) {
		this.mappedEntities = mappedEntities;
	}

	public List<CurrentModelData> getDiffResult() {
		return diffResult;
	}

	public void setDiffResult(List<CurrentModelData> diffResult) {
		this.diffResult = diffResult;
	}


}
