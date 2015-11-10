package com.kildeen.gv.entity.gen;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.kildeen.gv.entity.EntityConfiguration;
import com.kildeen.gv.entity.EntityConfigurationHandler;
import com.kildeen.gv.entity.gen.TestEntities.TestEntity1;
import com.kildeen.gv.vote.Vote;

import liquibase.change.ColumnConfig;
public class CurrentEntityModelTest {

	private static final int ONE_EXTRA_DUE_TO_INNER_CLASS = 1;

	EntityConfigurationHandler handler = EntityConfigurationHandler.getInstance();

	private RelationalModel relationalModel;
	
	

	@Before
	public void setUp() throws Exception {
		relationalModel = new RelationalModel();
	}

	@Test
	public void current_primary_key_should_be_set_as_pk() throws Exception {
		EntityConfiguration<?> e = handler.getByClass(Vote.class);

		CurrentEntityModel model = newModel(e, relationalModel);
		assertThat(model.getData().getPks()).hasSize(1);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void no_primary_key_should_be_a_problem() throws Exception {
		EntityConfiguration<?> e = Mockito.mock(EntityConfiguration.class);
		Mockito.when(e.getDefiningClass()).thenReturn((Class) String.class);
		CurrentEntityModel model = newModel(e, relationalModel);
		assertTrue(model.hasProblem());
	}

	@Test
	public void should_have_one_col_for_a_field() throws Exception {
		EntityConfiguration<?> e = EntityConfiguration.create(TestEntity1.class);

		CurrentEntityModel model = newModel(e, relationalModel);
		assertThat(model.getData().getColumnConfigs()).hasSize(TestEntity1.TABLE_COLS_EXCEPT_ID_COUNT + ONE_EXTRA_DUE_TO_INNER_CLASS);
	}

	@Test
	public void should_have_one_col_for_a_field_ignoring_primaryKey() throws Exception {
		EntityConfiguration<?> e = EntityConfiguration.create(TestEntity1.class);

		CurrentEntityModel model = newModel(e, relationalModel);
		assertThat(model.getData().getColumnConfigs()).extracting("name", String.class).doesNotContain("id");
	}
	
	@Test
	public void name_should_be_taken_from_field_if_not_defined_on_column() throws Exception {
		EntityConfiguration<?> e = EntityConfiguration.create(TestEntity1.class);
		CurrentEntityModel model = newModel(e, relationalModel);
		ColumnConfig conf = findConfig("col2", model);
		assertNotNull(conf);

	}
	
	@Test
	public void name_should_be_taken_from_field_if__no_column() throws Exception {
		EntityConfiguration<?> e = EntityConfiguration.create(TestEntity1.class);
		CurrentEntityModel model = newModel(e, relationalModel);
		ColumnConfig conf = findConfig("col1", model);
		assertNotNull(conf);
	}
	
	@Test
	public void name_should_be_taken_from_col_if_defined_in_col() throws Exception {
		EntityConfiguration<?> e = EntityConfiguration.create(TestEntity1.class);
		CurrentEntityModel model = newModel(e, relationalModel);
		ColumnConfig conf = findConfig("col3", model);
		assertNotNull(conf);
	}

	@Test
	public void should_not_have_unique_for_a_col_with_no_unique() throws Exception {
		EntityConfiguration<?> e = EntityConfiguration.create(TestEntity1.class);

		CurrentEntityModel model = newModel(e, relationalModel);
		ColumnConfig conf = findConfig("col1", model);
		assertThat(conf.getConstraints().isUnique()).isNull();// liquibase uses Boolean
	}
	@Test
	public void should__have_unique_for_a_col_with_unique() throws Exception {
		EntityConfiguration<?> e = EntityConfiguration.create(TestEntity1.class);
		
		CurrentEntityModel model = newModel(e, relationalModel);
		ColumnConfig conf = findConfig("col2", model);
		assertThat(conf.getConstraints().isUnique()).isTrue();
	}
	
	@Test
	public void test_that_indexes_gets_found() throws Exception {
		EntityConfiguration<?> e = EntityConfiguration.create(TestEntity1.class);
		
		CurrentEntityModel model = newModel(e, relationalModel);
		assertThat(model.getData().getIndexes()).hasSize(1);
	}
	
	@Test
	public void addRelationalData_delegates_to_relationalModel() throws Exception {
		EntityConfiguration<?> e = EntityConfiguration.create(TestEntity1.class);
		relationalModel = Mockito.mock(RelationalModel.class);
		CurrentEntityModel model = newModel(e, relationalModel);
		model.addRelationalData(new HashMap<>());
		Mockito.verify(relationalModel).build(Mockito.any(), Mockito.any(), Mockito.any());
	}
	
	@Test
	public void table_name_should_be_added_as_createTable() throws Exception {
		EntityConfiguration<?> e = EntityConfiguration.create(TestEntity1.class);
		CurrentEntityModel model = newModel(e, relationalModel);
		assertThat(model.getData().getCreateTables().get(0).getTableName()).isEqualTo(e.getTableName());

	}
	
	protected CurrentEntityModel newModel(EntityConfiguration<?> e, RelationalModel relationalModel2) {
		return new CurrentEntityModel(e, relationalModel2);
	}

	private ColumnConfig findConfig(String col, CurrentEntityModel model) {
		Optional<ColumnConfig> res= model.getData().getColumnConfigs().stream().filter(c -> c.getName().equals(col)).findFirst();
		return res.orElse(null);
	}

}
