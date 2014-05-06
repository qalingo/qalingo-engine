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

public class HeaderCartViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -3703554811104386867L;
	
	protected int cartTotalItems;
	protected String checkoutShoppingCartHeaderLabel;
	protected String checkoutShoppingCartUrl;
	
	public int getCartTotalItems() {
		return cartTotalItems;
	}
	
	public void setCartTotalItems(int cartTotalItems) {
		this.cartTotalItems = cartTotalItems;
	}
	
	public String getCheckoutShoppingCartHeaderLabel() {
        return checkoutShoppingCartHeaderLabel;
    }
	
	public void setCheckoutShoppingCartHeaderLabel(String checkoutShoppingCartHeaderLabel) {
        this.checkoutShoppingCartHeaderLabel = checkoutShoppingCartHeaderLabel;
    }
	
	public String getCheckoutShoppingCartUrl() {
        return checkoutShoppingCartUrl;
    }
	
	public void setCheckoutShoppingCartUrl(String checkoutShoppingCartUrl) {
        this.checkoutShoppingCartUrl = checkoutShoppingCartUrl;
    }
	
}
