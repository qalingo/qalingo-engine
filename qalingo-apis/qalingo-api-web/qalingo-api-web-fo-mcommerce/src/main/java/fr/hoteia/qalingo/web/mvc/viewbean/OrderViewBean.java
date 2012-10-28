/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
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

public class OrderViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -7481342045685333815L;
	
	private String orderNum;
	private String status;
	private String totalAmount;
	private String dateCreate;
	private String dateUpdate;
	
	private String billingAddressLabel;
	private String shippingAddressLabel;
	
	private String confirmationMessage;

	private List<OrderItemViewBean> orderItems = new ArrayList<OrderItemViewBean>();

	private String orderDetailsUrl;
	
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

	public String getTotalAmount() {
		return totalAmount;
	}
	
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
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
	
	public String getOrderDetailsUrl() {
		return orderDetailsUrl;
	}
	
	public void setOrderDetailsUrl(String orderDetailsUrl) {
		this.orderDetailsUrl = orderDetailsUrl;
	}
	
}
