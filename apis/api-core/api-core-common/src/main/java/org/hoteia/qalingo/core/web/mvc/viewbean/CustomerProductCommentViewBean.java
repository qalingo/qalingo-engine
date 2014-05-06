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

public class CustomerProductCommentViewBean extends AbstractViewBean implements Serializable {
	
	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 5818762483555029902L;

	private ProductSkuViewBean productSku;
	private String comment;
	
	public ProductSkuViewBean getProductSku() {
		return productSku;
	}
	
	public void setProductSku(ProductSkuViewBean productSku) {
		this.productSku = productSku;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}

}
