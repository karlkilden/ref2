package com.kildeen.ref.fact;
import static java.lang.String.valueOf;
import static java.util.stream.IntStream.iterate;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import org.apache.http.message.BasicNameValuePair;
import com.kildeen.ref.JsfRequestContext;

@Model
public class CategoryBean {

	private static final int MAX_PAGES = 3;

	@Inject
	private CategoryService categoryService;

	@Inject
	private JsfRequestContext jsfRequestContext;

	private List<Category> latestCategories;

	private int page;
	private List<Category> categories;
	private Category category = new Category();
	private List<Integer> pages = new ArrayList<>();

	public void save() {
		categoryService.save(category);
		page = jsfRequestContext.getPage();
		setup();
	}

	public void morePages() {
		BasicNameValuePair pageParam = jsfRequestContext.incrementAndGetPage();
		setup();
		long pageCount = categoryService.countPages(MAX_PAGES);

		jsfRequestContext.addParams(new BasicNameValuePair("morePages", String.valueOf("")), new BasicNameValuePair("pageCount",
				valueOf(pageCount)), pageParam);

		setPages(new ArrayList<>());
		int limit = (int) (pageCount < 5 ? pageCount : 5);
		iterate(1, i -> i + 1).limit(limit).forEach(v -> getPages().add(v));
	}

	public void setup() {
		latestCategories = categoryService.fetchLatestCategories(page, MAX_PAGES);
	}

	public List<Category> getLatestCategories() {
		return latestCategories;
	}

	public void setLatestCategories(List<Category> latestCategories) {
		this.latestCategories = latestCategories;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public boolean isMorePages() {
		return true;
	}

	public List<Integer> getPages() {
		return pages;
	}

	public void setPages(List<Integer> pages) {
		this.pages = pages;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

}
