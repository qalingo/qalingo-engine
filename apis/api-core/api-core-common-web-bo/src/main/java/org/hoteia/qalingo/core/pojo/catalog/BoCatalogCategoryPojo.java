/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.catalog;

import org.hoteia.qalingo.core.pojo.catalog.CatalogCategoryPojo;

/**
 *
 * <p>
 * <a href="BoProductCategoryJsonPojo.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
public class BoCatalogCategoryPojo extends CatalogCategoryPojo {
	
	private String addChildCategoryUrl;
	private String detailsUrl;
	private String editUrl;

	public BoCatalogCategoryPojo() {
	}
	
	public String getAddChildCategoryUrl() {
		return addChildCategoryUrl;
	}
	
	public void setAddChildCategoryUrl(String addChildCategoryUrl) {
		this.addChildCategoryUrl = addChildCategoryUrl;
	}
	
	public String getDetailsUrl() {
		return detailsUrl;
	}
	
	public void setDetailsUrl(String detailsUrl) {
		this.detailsUrl = detailsUrl;
	}
	
	public String getEditUrl() {
		return editUrl;
	}
	
	public void setEditUrl(String editUrl) {
		this.editUrl = editUrl;
	}
	
}