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
import java.util.ArrayList;
import java.util.List;

public class CartViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 8804558925159158979L;

	private String orderItemNameLabel;
	private String orderItemQuantityLabel;
	private String orderItemDeleteActionLabel;
	private String orderItemPriceLabel;
	private String orderItemSubTotalLabel;

	private String billingAddressLabel;
	private String shippingAddressLabel;
	
	private String step1SubmitLabel;
	private String step2SubmitLabel;
	private String step3SubmitLabel;
	
	private String cartDetailsUrl;
	private String cartAuthUrl;
	private String cartDeliveryAndOrderDetailsUrl;
	private String cartOrderPaymentUrl;
	private String cartOrderConfirmationUrl;

	private String addNewAddressLabel;
	private String addNewAddressUrl;

	private String cartItemsTotalLabel;
	private String cartShippingTotalLabel;
	private String cartTaxesTotalLabel;
	private String cartTotalLabel;
	
	private String cartItemsTotal;
	private String cartShippingTotal;
	private String cartFeesTotal;
	private String cartTotal;
	
	private String cardHolderLabel;
	private String cardNumberLabel;
	private String cardCryptoLabel;
	private String cardExpirationDateLabel;
	private String cardExpirationMonthLabel;
	private String cardExpirationYearLabel;

	private List<CartItemViewBean> cartItems = new ArrayList<CartItemViewBean>();
	private List<CartShippingViewBean> cartShippings = new ArrayList<CartShippingViewBean>();
	private List<CartTaxViewBean> cartTaxes = new ArrayList<CartTaxViewBean>();

	public CartViewBean() {
	}

	public String getOrderItemNameLabel() {
		return orderItemNameLabel;
	}

	public void setOrderItemNameLabel(String orderItemNameLabel) {
		this.orderItemNameLabel = orderItemNameLabel;
	}

	public String getOrderItemQuantityLabel() {
		return orderItemQuantityLabel;
	}

	public void setOrderItemQuantityLabel(String orderItemQuantityLabel) {
		this.orderItemQuantityLabel = orderItemQuantityLabel;
	}

	public String getOrderItemDeleteActionLabel() {
		return orderItemDeleteActionLabel;
	}

	public void setOrderItemDeleteActionLabel(String orderItemDeleteActionLabel) {
		this.orderItemDeleteActionLabel = orderItemDeleteActionLabel;
	}

	public String getOrderItemPriceLabel() {
		return orderItemPriceLabel;
	}

	public void setOrderItemPriceLabel(String orderItemPriceLabel) {
		this.orderItemPriceLabel = orderItemPriceLabel;
	}

	public String getOrderItemSubTotalLabel() {
		return orderItemSubTotalLabel;
	}

	public void setOrderItemSubTotalLabel(String orderItemSubTotalLabel) {
		this.orderItemSubTotalLabel = orderItemSubTotalLabel;
	}

	public String getBillingAddressLabel() {
		return billingAddressLabel;
	}
	
	public void setBillingAddressLabel(String billingAddressLabel) {
		this.billingAddressLabel = billingAddressLabel;
	}
	
	public String getShippingAddressLabel() {
		return shippingAddressLabel;
	}
	
	public void setShippingAddressLabel(String shippingAddressLabel) {
		this.shippingAddressLabel = shippingAddressLabel;
	}
	
	public String getStep1SubmitLabel() {
		return step1SubmitLabel;
	}

	public void setStep1SubmitLabel(String step1SubmitLabel) {
		this.step1SubmitLabel = step1SubmitLabel;
	}

	public String getStep2SubmitLabel() {
		return step2SubmitLabel;
	}

	public void setStep2SubmitLabel(String step2SubmitLabel) {
		this.step2SubmitLabel = step2SubmitLabel;
	}

	public String getStep3SubmitLabel() {
		return step3SubmitLabel;
	}

	public void setStep3SubmitLabel(String step3SubmitLabel) {
		this.step3SubmitLabel = step3SubmitLabel;
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

	public String getAddNewAddressLabel() {
		return addNewAddressLabel;
	}
	
	public void setAddNewAddressLabel(String addNewAddressLabel) {
		this.addNewAddressLabel = addNewAddressLabel;
	}
	
	public String getAddNewAddressUrl() {
		return addNewAddressUrl;
	}
	
	public void setAddNewAddressUrl(String addNewAddressUrl) {
		this.addNewAddressUrl = addNewAddressUrl;
	}
	
	public String getCartItemsTotalLabel() {
		return cartItemsTotalLabel;
	}

	public void setCartItemsTotalLabel(String cartItemsTotalLabel) {
		this.cartItemsTotalLabel = cartItemsTotalLabel;
	}

	public String getCartShippingTotalLabel() {
		return cartShippingTotalLabel;
	}

	public void setCartShippingTotalLabel(String cartShippingTotalLabel) {
		this.cartShippingTotalLabel = cartShippingTotalLabel;
	}

	public String getCartTaxesTotalLabel() {
		return cartTaxesTotalLabel;
	}

	public void setCartTaxesTotalLabel(String cartTaxesTotalLabel) {
		this.cartTaxesTotalLabel = cartTaxesTotalLabel;
	}

	public String getCartTotalLabel() {
		return cartTotalLabel;
	}

	public void setCartTotalLabel(String cartTotalLabel) {
		this.cartTotalLabel = cartTotalLabel;
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

	public String getCardHolderLabel() {
		return cardHolderLabel;
	}

	public void setCardHolderLabel(String cardHolderLabel) {
		this.cardHolderLabel = cardHolderLabel;
	}

	public String getCardNumberLabel() {
		return cardNumberLabel;
	}

	public void setCardNumberLabel(String cardNumberLabel) {
		this.cardNumberLabel = cardNumberLabel;
	}

	public String getCardCryptoLabel() {
		return cardCryptoLabel;
	}

	public void setCardCryptoLabel(String cardCryptoLabel) {
		this.cardCryptoLabel = cardCryptoLabel;
	}
	
	public String getCardExpirationDateLabel() {
		return cardExpirationDateLabel;
	}
	
	public void setCardExpirationDateLabel(String cardExpirationDateLabel) {
		this.cardExpirationDateLabel = cardExpirationDateLabel;
	}

	public String getCardExpirationMonthLabel() {
		return cardExpirationMonthLabel;
	}
	
	public void setCardExpirationMonthLabel(String cardExpirationMonthLabel) {
		this.cardExpirationMonthLabel = cardExpirationMonthLabel;
	}

	public String getCardExpirationYearLabel() {
		return cardExpirationYearLabel;
	}
	
	public void setCardExpirationYearLabel(String cardExpirationYearLabel) {
		this.cardExpirationYearLabel = cardExpirationYearLabel;
	}

	public List<CartItemViewBean> getCartItems() {
		return cartItems;
	}
	
	public void setCartItems(List<CartItemViewBean> cartItems) {
		this.cartItems = cartItems;
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
