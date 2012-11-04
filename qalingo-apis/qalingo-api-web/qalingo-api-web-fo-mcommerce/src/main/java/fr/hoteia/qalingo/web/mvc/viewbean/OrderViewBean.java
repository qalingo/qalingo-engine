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

public class OrderViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -7481342045685333815L;
	
	private String orderNum;
	private String status;
	private String dateCreate;
	private String dateUpdate;
	
	private String orderItemNameLabel;
	private String orderItemQuantityLabel;
	private String orderItemDeleteActionLabel;
	private String orderItemPriceLabel;
	private String orderItemSubTotalLabel;

	private String billingAddressLabel;
	private String shippingAddressLabel;
	
	private String orderItemsTotalLabel;
	private String orderShippingTotalLabel;
	private String orderTaxesTotalLabel;
	private String orderTotalLabel;
	
	private String orderItemsTotal;
	private String orderShippingTotal;
	private String orderFeesTotal;
	private String orderTotal;
	
	private String cardHolderLabel;
	private String cardNumberLabel;
	private String cardCryptoLabel;
	private String cardExpirationDateLabel;
	private String cardExpirationMonthLabel;
	private String cardExpirationYearLabel;
	
	private String cardHolder;
	private String cardNumber;
	private String cardCrypto;
	private String cardExpirationDate;
	private String cardExpirationMonth;
	private String cardExpirationYear;
	
	private String orderDetailsUrl;

	private String confirmationMessage;

	private List<OrderItemViewBean> orderItems = new ArrayList<OrderItemViewBean>();
	private List<OrderShippingViewBean> orderShippings = new ArrayList<OrderShippingViewBean>();
	private List<OrderTaxViewBean> orderTaxes = new ArrayList<OrderTaxViewBean>();
	
	public OrderViewBean() {
	}
	
	public String getOrderNum() {
		return orderNum;
	}
	
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getDateCreate() {
		return dateCreate;
	}
	
	public void setDateCreate(String dateCreate) {
		this.dateCreate = dateCreate;
	}
	
	public String getDateUpdate() {
		return dateUpdate;
	}
	
	public void setDateUpdate(String dateUpdate) {
		this.dateUpdate = dateUpdate;
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

	public String getOrderItemsTotalLabel() {
		return orderItemsTotalLabel;
	}

	public void setOrderItemsTotalLabel(String orderItemsTotalLabel) {
		this.orderItemsTotalLabel = orderItemsTotalLabel;
	}

	public String getOrderShippingTotalLabel() {
		return orderShippingTotalLabel;
	}

	public void setOrderShippingTotalLabel(String orderShippingTotalLabel) {
		this.orderShippingTotalLabel = orderShippingTotalLabel;
	}

	public String getOrderTaxesTotalLabel() {
		return orderTaxesTotalLabel;
	}

	public void setOrderTaxesTotalLabel(String orderTaxesTotalLabel) {
		this.orderTaxesTotalLabel = orderTaxesTotalLabel;
	}

	public String getOrderTotalLabel() {
		return orderTotalLabel;
	}

	public void setOrderTotalLabel(String orderTotalLabel) {
		this.orderTotalLabel = orderTotalLabel;
	}

	public String getOrderItemsTotal() {
		return orderItemsTotal;
	}

	public void setOrderItemsTotal(String orderItemsTotal) {
		this.orderItemsTotal = orderItemsTotal;
	}

	public String getOrderShippingTotal() {
		return orderShippingTotal;
	}

	public void setOrderShippingTotal(String orderShippingTotal) {
		this.orderShippingTotal = orderShippingTotal;
	}

	public String getOrderFeesTotal() {
		return orderFeesTotal;
	}

	public void setOrderFeesTotal(String orderFeesTotal) {
		this.orderFeesTotal = orderFeesTotal;
	}

	public String getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(String orderTotal) {
		this.orderTotal = orderTotal;
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

	public String getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardCrypto() {
		return cardCrypto;
	}

	public void setCardCrypto(String cardCrypto) {
		this.cardCrypto = cardCrypto;
	}

	public String getCardExpirationDate() {
		return cardExpirationDate;
	}

	public void setCardExpirationDate(String cardExpirationDate) {
		this.cardExpirationDate = cardExpirationDate;
	}

	public String getCardExpirationMonth() {
		return cardExpirationMonth;
	}

	public void setCardExpirationMonth(String cardExpirationMonth) {
		this.cardExpirationMonth = cardExpirationMonth;
	}

	public String getCardExpirationYear() {
		return cardExpirationYear;
	}

	public void setCardExpirationYear(String cardExpirationYear) {
		this.cardExpirationYear = cardExpirationYear;
	}

	public String getOrderDetailsUrl() {
		return orderDetailsUrl;
	}

	public void setOrderDetailsUrl(String orderDetailsUrl) {
		this.orderDetailsUrl = orderDetailsUrl;
	}

	public String getConfirmationMessage() {
		return confirmationMessage;
	}

	public void setConfirmationMessage(String confirmationMessage) {
		this.confirmationMessage = confirmationMessage;
	}

	public List<OrderItemViewBean> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemViewBean> orderItems) {
		this.orderItems = orderItems;
	}

	public List<OrderShippingViewBean> getOrderShippings() {
		return orderShippings;
	}

	public void setOrderShippings(List<OrderShippingViewBean> orderShippings) {
		this.orderShippings = orderShippings;
	}

	public List<OrderTaxViewBean> getOrderTaxes() {
		return orderTaxes;
	}
	
	public void setOrderTaxes(List<OrderTaxViewBean> orderTaxes) {
		this.orderTaxes = orderTaxes;
	}

	
}
