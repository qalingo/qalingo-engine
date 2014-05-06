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

public class OrderShippingViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 7409858501276622760L;

	private String orderShippingCode;
	private String orderShippingTotalLabel;
	private String orderShippingTotal;

	public OrderShippingViewBean() {
	}

	public String getOrderShippingCode() {
		return orderShippingCode;
	}
	
	public void setOrderShippingCode(String orderShippingCode) {
		this.orderShippingCode = orderShippingCode;
	}

	public String getOrderShippingTotalLabel() {
		return orderShippingTotalLabel;
	}

	public void setOrderShippingTotalLabel(String orderShippingTotalLabel) {
		this.orderShippingTotalLabel = orderShippingTotalLabel;
	}

	public String getOrderShippingTotal() {
		return orderShippingTotal;
	}

	public void setOrderShippingTotal(String orderShippingTotal) {
		this.orderShippingTotal = orderShippingTotal;
	}
	
}
