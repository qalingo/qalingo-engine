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

public class CartViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 8804558925159158979L;

    boolean withItemQuantityActions = false;
    boolean withPromoCode = false;

	private String cartDetailsUrl;
	private String cartAuthUrl;
	private String cartDeliveryAndOrderDetailsUrl;
	private String cartOrderPaymentUrl;
	private String cartOrderConfirmationUrl;
	
	private String addNewAddressUrl;

	private String cartItemsTotalWithCurrencySign;
	private String cartShippingTotalWithCurrencySign;
	private String cartFeesTotalWithCurrencySign;
	private String cartTotalWithCurrencySign;
	
	private List<CartItemViewBean> cartItems = new ArrayList<CartItemViewBean>();
	private List<CartDeliveryMethodViewBean> cartDeliveryMethods = new ArrayList<CartDeliveryMethodViewBean>();
	private List<CartTaxViewBean> cartTaxes = new ArrayList<CartTaxViewBean>();

	public CartViewBean() {
	}

	public boolean isWithItemQuantityActions() {
        return withItemQuantityActions;
    }

    public void setWithItemQuantityActions(boolean withItemQuantityActions) {
        this.withItemQuantityActions = withItemQuantityActions;
    }

    public boolean isWithPromoCode() {
        return withPromoCode;
    }

    public void setWithPromoCode(boolean withPromoCode) {
        this.withPromoCode = withPromoCode;
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
	
	public String getCartItemsTotalWithCurrencySign() {
        return cartItemsTotalWithCurrencySign;
    }

    public void setCartItemsTotalWithCurrencySign(String cartItemsTotalWithCurrencySign) {
        this.cartItemsTotalWithCurrencySign = cartItemsTotalWithCurrencySign;
    }

    public String getCartShippingTotalWithCurrencySign() {
        return cartShippingTotalWithCurrencySign;
    }

    public void setCartShippingTotalWithCurrencySign(String cartShippingTotalWithCurrencySign) {
        this.cartShippingTotalWithCurrencySign = cartShippingTotalWithCurrencySign;
    }

    public String getCartFeesTotalWithCurrencySign() {
        return cartFeesTotalWithCurrencySign;
    }

    public void setCartFeesTotalWithCurrencySign(String cartFeesTotalWithCurrencySign) {
        this.cartFeesTotalWithCurrencySign = cartFeesTotalWithCurrencySign;
    }

    public String getCartTotalWithCurrencySign() {
        return cartTotalWithCurrencySign;
    }

    public void setCartTotalWithCurrencySign(String cartTotalWithCurrencySign) {
        this.cartTotalWithCurrencySign = cartTotalWithCurrencySign;
    }

    public List<CartItemViewBean> getCartItems() {
		return cartItems;
	}
	
	public void setCartItems(List<CartItemViewBean> cartItems) {
		this.cartItems = cartItems;
	}
	
    public int getTotalCartItems() {
        if (cartItems != null) {
            return cartItems.size();
        }
        return 0;
    }
	
	public List<CartDeliveryMethodViewBean> getCartDeliveryMethods() {
		return cartDeliveryMethods;
	}
	
	public void setCartDeliveryMethods(List<CartDeliveryMethodViewBean> cartDeliveryMethods) {
		this.cartDeliveryMethods = cartDeliveryMethods;
	}
	
    public int getTotalDeliveryMethods() {
        if (cartDeliveryMethods != null) {
            return cartDeliveryMethods.size();
        }
        return 0;
    }
	
	public List<CartTaxViewBean> getCartTaxes() {
		return cartTaxes;
	}
	
	public void setCartTaxes(List<CartTaxViewBean> cartTaxes) {
		this.cartTaxes = cartTaxes;
	}
	
    public int getTotalCartTaxes() {
        if (cartTaxes != null) {
            return cartTaxes.size();
        }
        return 0;
    }
	
}