/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.io.Serializable;

public class CartShippingViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 251889000239105026L;

	private String cartShippingCode;
	private String cartShippingTotalLabel;
	private String cartShippingTotal;

	public CartShippingViewBean() {
	}

	public String getCartShippingCode() {
		return cartShippingCode;
	}
	
	public void setCartShippingCode(String cartShippingCode) {
		this.cartShippingCode = cartShippingCode;
	}
	
	public String getCartShippingTotalLabel() {
		return cartShippingTotalLabel;
	}

	public void setCartShippingTotalLabel(String cartShippingTotalLabel) {
		this.cartShippingTotalLabel = cartShippingTotalLabel;
	}

	public String getCartShippingTotal() {
		return cartShippingTotal;
	}

	public void setCartShippingTotal(String cartShippingTotal) {
		this.cartShippingTotal = cartShippingTotal;
	}

}
