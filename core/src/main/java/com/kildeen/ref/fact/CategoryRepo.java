package com.kildeen.ref.fact;

import java.util.List;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.FirstResult;
import org.apache.deltaspike.data.api.MaxResults;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository

public interface CategoryRepo extends EntityRepository<Category, Long> {

	@Query("select c from Category c order by c.updatedAt desc")
	List<Category> fetchLatestCategories(@FirstResult int page, @MaxResults int max);

	@Query("select c from Category c")
	QueryResult<Category> getQueryResult();
}
