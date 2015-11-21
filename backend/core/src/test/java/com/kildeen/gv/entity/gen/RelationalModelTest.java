package com.kildeen.gv.entity.gen;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import com.kildeen.gv.entity.EntityConfiguration;
import com.kildeen.gv.entity.gen.TestEntities.*;
import com.kildeen.gv.vote.Vote;

import static org.assertj.core.api.Assertions.assertThat;

public class RelationalModelTest extends CurrentEntityModelTest {

	@Before
	public void setUp2() throws Exception {
	}

	@Test
	public void a_foreign_key_should_be_added() throws Exception {
		RelationalModel relationalModel = new RelationalModel();
		EntityConfiguration<?> e = EntityConfiguration.create(TestEntity1.class);
		CurrentEntityModel model = newModel(e, relationalModel);
		relationalModel.build(model.getData(), e, getAllRelationalModels());
		assertThat(model.getData().getFks()).hasSize(1);
	}

	private Map<Class<?>, CurrentEntityModel> getAllRelationalModels() {
		Map<Class<?>, CurrentEntityModel> all = new HashMap<>();

		RelationalModel relationalModel = new RelationalModel();
		EntityConfiguration<?> e = EntityConfiguration.create(Vote.class);
		CurrentEntityModel model = newModel(e, relationalModel);
		all.put(Vote.class, model);
		return all;
	}

}
