/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.viewbean;

import java.io.Serializable;

public class HeaderCartViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -3703554811104386867L;
	
	protected int cartTotalItems;
	protected String cartTotalSummaryLabel;
	protected String cartUrl;
	
	public int getCartTotalItems() {
		return cartTotalItems;
	}
	
	public void setCartTotalItems(int cartTotalItems) {
		this.cartTotalItems = cartTotalItems;
	}
	
	public String getCartTotalSummaryLabel() {
		return cartTotalSummaryLabel;
	}
	
	public void setCartTotalSummaryLabel(String cartTotalSummaryLabel) {
		this.cartTotalSummaryLabel = cartTotalSummaryLabel;
	}
	
	public String getCartUrl() {
		return cartUrl;
	}
	
	public void setCartUrl(String cartUrl) {
		this.cartUrl = cartUrl;
	}
	
}
