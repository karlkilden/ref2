package com.kildeen.gv.entity.gen;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.kildeen.gv.vote.Vote;

// We want the test entities as inner class so it will not be found in
// scanning by integration tests. Inner classes has a reference to
// parent ie. this$0
public class TestEntities {
	@Entity
	@Table(indexes = { @Index(name = "col1col3", columnList = "col1, col3") })
	public class TestEntity1 {

		public static final int TABLE_COLS_EXCEPT_ID_COUNT = 3;

		@Id
		private String id;

		private String col1;

		@Column(name = "col3")
		private String column3;

		@Column(unique = true)
		private String col2;
		
		private Vote vote;

	}
	
	@Entity
	@Table(name= "TestEntityTWO", indexes = { @Index(name = "col1col3", columnList = "col1, col3") })
	public class TestEntity2 {

		public static final int NON_ID_COLS = 3;

		@Id
		private String id;

		private String col1;

		@Column(name = "col3")
		private String column3;

		@Column(unique = true)
		private String col2;

	}
	
	@Entity
	public class TestEntity3 {

		public static final int NON_ID_COLS = 3;

		@Id
		private String id;

		private String col1;

		@Column(name = "col3")
		private String column3;

		@Column(unique = true)
		private String col2;

	}
}
