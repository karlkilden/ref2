package com.kildeen.ref.fact;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.inject.Inject;

import com.kildeen.gv.Service;

@Service
public class CategoryService {

	@Inject
	private CategoryRepo repo;

	public Category save(Category category) {
		return repo.save(category);
	}

	public List<Category> fetchAll() {
		return repo.findAll();
	}

	public List<Category> fetchLatestCategories(int page, int max) {
		return repo.fetchLatestCategories(page, max);
	}

	public long countPages(int pageSize) {
		 long numberOfPages = new BigDecimal(repo.count()).divide(new BigDecimal(pageSize), RoundingMode.UP).longValue();
		 return numberOfPages;
	}

}
