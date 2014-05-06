/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.product;

import org.hoteia.qalingo.core.pojo.product.ProductMarketingPojo;

/**
 *
 * <p>
 * <a href="BoProductMarketingPojo.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
public class BoProductMarketingPojo extends ProductMarketingPojo {
	
    private String addUrl;
	private String editUrl;

	public BoProductMarketingPojo() {
	}
	
	public String getAddUrl() {
        return addUrl;
    }
	
	public void setAddUrl(String addUrl) {
        this.addUrl = addUrl;
    }
	
	public String getEditUrl() {
		return editUrl;
	}
	
	public void setEditUrl(String editUrl) {
		this.editUrl = editUrl;
	}
	
}