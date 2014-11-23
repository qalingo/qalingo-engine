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

import org.apache.commons.lang.StringUtils;

public class RetailerAddressViewBean extends AbstractAddressViewBean {

	/**
	 * Generated UID
	 */
    protected static final long serialVersionUID = -359258454675030384L;

    protected String phone;
    protected String mobile;
    protected String fax;
    protected String email;
    protected String website;

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

    public String getWebsiteHttpUrl() {
        if (StringUtils.isNotEmpty(website)
                && !website.contains("http")) {
            return "http://" + website;
        }
        return website;
    }
	
    
}