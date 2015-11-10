package com.kildeen.gv.entity;

import java.util.HashMap;
import java.util.Map;

public class TableNameOwnerHistory {
	private EntityConfiguration<?> firstOwner;
	private EntityConfiguration<?> currentOwner;

	private Map<String, EntityConfiguration<?>> ownerChangedTriggers = new HashMap<>();
	private String tableName;

	public TableNameOwnerHistory(String tableName, EntityConfiguration<?> firstOwner) {
		this.tableName = tableName;
		this.firstOwner = firstOwner;

	}

	public TableNameOwnerHistory addOwner(String fromId, EntityConfiguration<?> owner) {
		ownerChangedTriggers.put(fromId, owner);
		return this;
	}

	public EntityConfiguration<?> getOwner(String id) {
		EntityConfiguration<?> newOwner = ownerChangedTriggers.get(id);
		if (newOwner != null) {
			this.currentOwner = newOwner;
		}
		return currentOwner;
	}

	public String getTableName() {
		return tableName;
	}

}
