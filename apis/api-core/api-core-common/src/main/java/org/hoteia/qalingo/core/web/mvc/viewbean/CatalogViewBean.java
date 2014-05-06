/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CatalogViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 906812037579319564L;
	
	protected String name;
	protected String code;

	protected List<CatalogCategoryViewBean> categories = new ArrayList<CatalogCategoryViewBean>();

	protected String addRootCategoryUrl;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public List<CatalogCategoryViewBean> getCategories() {
		return categories;
	}
	
	public void setCategories(List<CatalogCategoryViewBean> categories) {
		this.categories = categories;
	}
	
	public String getAddRootCategoryUrl() {
		return addRootCategoryUrl;
	}
	
	public void setAddRootCategoryUrl(String addRootCategoryUrl) {
		this.addRootCategoryUrl = addRootCategoryUrl;
	}
	
}
