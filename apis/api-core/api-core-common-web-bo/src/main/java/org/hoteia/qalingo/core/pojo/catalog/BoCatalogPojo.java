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

/**
 *
 * <p>
 * <a href="BoCatalogPojo.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
public class BoCatalogPojo extends CatalogPojo {
	
	private String addRootCategoryUrl;

	public BoCatalogPojo() {
	}
	
	public String getAddRootCategoryUrl() {
        return addRootCategoryUrl;
    }
	
	public void setAddRootCategoryUrl(String addRootCategoryUrl) {
        this.addRootCategoryUrl = addRootCategoryUrl;
    }
	
}