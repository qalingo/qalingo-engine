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

public class OrderTaxViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 5892816934330014830L;

	private String orderTaxCode;
	private String orderTaxTotalLabel;
	private String orderTaxTotal;

	public OrderTaxViewBean() {
	}

	public String getOrderTaxCode() {
		return orderTaxCode;
	}
	
	public void setOrderTaxCode(String orderTaxCode) {
		this.orderTaxCode = orderTaxCode;
	}

	public String getOrderTaxTotalLabel() {
		return orderTaxTotalLabel;
	}

	public void setOrderTaxTotalLabel(String orderTaxTotalLabel) {
		this.orderTaxTotalLabel = orderTaxTotalLabel;
	}

	public String getOrderTaxTotal() {
		return orderTaxTotal;
	}

	public void setOrderTaxTotal(String orderTaxTotal) {
		this.orderTaxTotal = orderTaxTotal;
	}

}
