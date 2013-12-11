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
import java.util.ArrayList;
import java.util.List;

public class CartViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 8804558925159158979L;

	private String cartDetailsUrl;
	private String cartAuthUrl;
	private String cartDeliveryAndOrderDetailsUrl;
	private String cartOrderPaymentUrl;
	private String cartOrderConfirmationUrl;

	private String addNewAddressUrl;

	private String cartItemsTotal;
	private String cartShippingTotal;
	private String cartFeesTotal;
	private String cartTotal;
	
	private List<CartItemViewBean> cartItems = new ArrayList<CartItemViewBean>();
	private List<CartShippingViewBean> cartShippings = new ArrayList<CartShippingViewBean>();
	private List<CartTaxViewBean> cartTaxes = new ArrayList<CartTaxViewBean>();

	public CartViewBean() {
	}

	public String getCartDetailsUrl() {
		return cartDetailsUrl;
	}

	public void setCartDetailsUrl(String cartDetailsUrl) {
		this.cartDetailsUrl = cartDetailsUrl;
	}

	public String getCartAuthUrl() {
		return cartAuthUrl;
	}
	
	public void setCartAuthUrl(String cartAuthUrl) {
		this.cartAuthUrl = cartAuthUrl;
	}

	public String getCartDeliveryAndOrderDetailsUrl() {
		return cartDeliveryAndOrderDetailsUrl;
	}

	public void setCartDeliveryAndOrderDetailsUrl(
			String cartDeliveryAndOrderDetailsUrl) {
		this.cartDeliveryAndOrderDetailsUrl = cartDeliveryAndOrderDetailsUrl;
	}

	public String getCartOrderPaymentUrl() {
		return cartOrderPaymentUrl;
	}

	public void setCartOrderPaymentUrl(String cartOrderPaymentUrl) {
		this.cartOrderPaymentUrl = cartOrderPaymentUrl;
	}

	public String getCartOrderConfirmationUrl() {
		return cartOrderConfirmationUrl;
	}

	public void setCartOrderConfirmationUrl(String cartOrderConfirmationUrl) {
		this.cartOrderConfirmationUrl = cartOrderConfirmationUrl;
	}

	public String getAddNewAddressUrl() {
		return addNewAddressUrl;
	}
	
	public void setAddNewAddressUrl(String addNewAddressUrl) {
		this.addNewAddressUrl = addNewAddressUrl;
	}
	
	public String getCartItemsTotal() {
		return cartItemsTotal;
	}

	public void setCartItemsTotal(String cartItemsTotal) {
		this.cartItemsTotal = cartItemsTotal;
	}

	public String getCartShippingTotal() {
		return cartShippingTotal;
	}

	public void setCartShippingTotal(String cartShippingTotal) {
		this.cartShippingTotal = cartShippingTotal;
	}

	public String getCartFeesTotal() {
		return cartFeesTotal;
	}

	public void setCartFeesTotal(String cartFeesTotal) {
		this.cartFeesTotal = cartFeesTotal;
	}

	public String getCartTotal() {
		return cartTotal;
	}

	public void setCartTotal(String cartTotal) {
		this.cartTotal = cartTotal;
	}

	public List<CartItemViewBean> getCartItems() {
		return cartItems;
	}
	
	public void setCartItems(List<CartItemViewBean> cartItems) {
		this.cartItems = cartItems;
	}
	
    public int getTotalCartItems() {
        if(cartItems != null){
            return cartItems.size();
        }
        return 0;
     }
	
	public List<CartShippingViewBean> getCartShippings() {
		return cartShippings;
	}
	
	public void setCartShippings(List<CartShippingViewBean> cartShippings) {
		this.cartShippings = cartShippings;
	}
	
	public List<CartTaxViewBean> getCartTaxes() {
		return cartTaxes;
	}
	
	public void setCartTaxes(List<CartTaxViewBean> cartTaxes) {
		this.cartTaxes = cartTaxes;
	}
	
}