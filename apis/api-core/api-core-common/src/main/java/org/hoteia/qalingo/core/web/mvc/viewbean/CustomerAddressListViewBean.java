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

public class CustomerAddressListViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 3367453428667832074L;
	
    private String backUrl;
    
    private List<CustomerAddressViewBean> customerAddressList = new ArrayList<CustomerAddressViewBean>();
    
	public String getBackUrl() {
		return backUrl;
	}
	
	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}
	
	public List<CustomerAddressViewBean> getCustomerAddressList() {
		return customerAddressList;
	}
	
	public void setCustomerAddressList(List<CustomerAddressViewBean> customerAddressList) {
		this.customerAddressList = customerAddressList;
	}
	
}