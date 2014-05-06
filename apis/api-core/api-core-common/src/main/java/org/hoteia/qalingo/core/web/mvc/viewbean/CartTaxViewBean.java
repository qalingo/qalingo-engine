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

public class CartTaxViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -280251656713999576L;

	private String cartTaxCode;
	private String cartTaxTotalLabel;
	private String cartTaxTotal;

	public CartTaxViewBean() {
	}

	public String getCartTaxCode() {
		return cartTaxCode;
	}

	public void setCartTaxCode(String cartTaxCode) {
		this.cartTaxCode = cartTaxCode;
	}

	public String getCartTaxTotalLabel() {
		return cartTaxTotalLabel;
	}

	public void setCartTaxTotalLabel(String cartTaxTotalLabel) {
		this.cartTaxTotalLabel = cartTaxTotalLabel;
	}

	public String getCartTaxTotal() {
		return cartTaxTotal;
	}

	public void setCartTaxTotal(String cartTaxTotal) {
		this.cartTaxTotal = cartTaxTotal;
	}


}
