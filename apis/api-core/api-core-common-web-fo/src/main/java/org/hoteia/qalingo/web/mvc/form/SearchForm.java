/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.form;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hoteia.qalingo.core.Constants;

/**
 * 
 * 
 */
public class SearchForm implements Serializable {
	private static final long serialVersionUID = -1243393136279082685L;

	private String text;
	private int pageSize;
	private int page;
	private String sortBy;
	private String order;
	private PriceRange price;
	private String categoriesFilter;
	private String cities;

	public SearchForm() {
		page = 0;
		pageSize = Constants.PAGINATION_DEFAULT_PAGE_SIZE;
		sortBy = "";
		order = "";

	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public PriceRange getPrice() {
		return price;
	}

	public void setPrice(PriceRange price) {
		this.price = price;
	}

	public String getCategoriesFilter() {
		return categoriesFilter;
	}

	public void setCategoriesFilter(String categoriesFilter) {
		this.categoriesFilter = categoriesFilter;
	}
	

	public String getCities() {
		return cities;
	}

	public void setCities(String cities) {
		this.cities = cities;
	}

	public List<String> getCatalogCategoryList() {
		if (StringUtils.isEmpty(categoriesFilter)) {
			return null;
		}

		String[] arr = categoriesFilter.split(",");
		return Arrays.asList(arr);
	}
	
	public List<String> getCityList() {
		if (StringUtils.isEmpty(cities)) {
			return null;
		}

		String[] arr = cities.split(",");
		return Arrays.asList(arr);
	}

	@Override
	public String toString() {
		return "SearchForm [text=" + text + ", pageSize=" + pageSize
				+ ", page=" + page + ", sortBy=" + sortBy + ", order=" + order
				+ ", price=" + price + ", catalogCategories="
				+ categoriesFilter + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((categoriesFilter == null) ? 0 : categoriesFilter.hashCode());
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result + page;
		result = prime * result + pageSize;
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((sortBy == null) ? 0 : sortBy.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchForm other = (SearchForm) obj;
		if (categoriesFilter == null) {
			if (other.categoriesFilter != null)
				return false;
		} else if (!categoriesFilter.equals(other.categoriesFilter))
			return false;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (page != other.page)
			return false;
		if (pageSize != other.pageSize)
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (sortBy == null) {
			if (other.sortBy != null)
				return false;
		} else if (!sortBy.equals(other.sortBy))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

}