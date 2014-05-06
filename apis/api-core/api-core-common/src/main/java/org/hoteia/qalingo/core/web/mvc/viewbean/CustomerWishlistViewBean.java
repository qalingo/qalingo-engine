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

public class CustomerWishlistViewBean extends AbstractViewBean implements Serializable {
	
	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -6694161619489386027L;

	List<ProductSkuViewBean> productSkus = new ArrayList<ProductSkuViewBean>();
	
	public List<ProductSkuViewBean> getProductSkus() {
		return productSkus;
	}
	
	public void setProductSkus(List<ProductSkuViewBean> productSkus) {
		this.productSkus = productSkus;
	}
	
}
