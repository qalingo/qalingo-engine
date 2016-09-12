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

public class OrderShippingViewBean extends AbstractViewBean {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 7409858501276622760L;

	private String code;
    private String name;
    private String deliveryTime;
	private String totalLabel;
	private String total;

	public OrderShippingViewBean() {
	}

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
        return name;
    }
	
	public void setName(String name) {
        this.name = name;
    }
	
	public String getDeliveryTime() {
        return deliveryTime;
    }
	
	public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

	public String getTotalLabel() {
		return totalLabel;
	}

	public void setTotalLabel(String totalLabel) {
		this.totalLabel = totalLabel;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
	
}
