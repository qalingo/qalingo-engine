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

public class RetailerAddressViewBean extends AbstractAddressViewBean implements Serializable {

	/**
	 * Generated UID
	 */
    private static final long serialVersionUID = -359258454675030384L;

    private String phone;
    private String mobile;
    private String fax;
    private String email;
    private String website;

    public String getPhone() {
	    return phone;
    }
    
    public void setPhone(String phone) {
	    this.phone = phone;
    }

	public String getMobile() {
    	return mobile;
    }

	public void setMobile(String mobile) {
    	this.mobile = mobile;
    }

	public String getFax() {
    	return fax;
    }

	public void setFax(String fax) {
    	this.fax = fax;
    }

	public String getEmail() {
    	return email;
    }

	public void setEmail(String email) {
    	this.email = email;
    }
	
	public String getWebsite() {
	    return website;
    }
	
	public void setWebsite(String website) {
	    this.website = website;
    }
    
}