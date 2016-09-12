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

public class OrderAddressViewBean extends AbstractAddressViewBean {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 2347791718108024528L;
	
    private String addressName;
    private String companyName;
    private String titleCode;
    private String titleLabel;
    private String lastname;
    private String firstname;
	private Long customerId;
	private boolean isDefaultBilling;
	private boolean isDefaultShipping;

    private String editUrl;
    private String deleteUrl;

	public String getAddressName() {
		return addressName;
	}
	
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	
	public String getCompanyName() {
        return companyName;
    }
	
	public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
	
	public String getTitleCode() {
    	return titleCode;
    }

	public void setTitleCode(String titleCode) {
    	this.titleCode = titleCode;
    }

	public String getTitleLabel() {
    	return titleLabel;
    }

	public void setTitleLabel(String titleLabel) {
    	this.titleLabel = titleLabel;
    }

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public boolean isDefaultBilling() {
		return isDefaultBilling;
	}

	public void setDefaultBilling(boolean isDefaultBilling) {
		this.isDefaultBilling = isDefaultBilling;
	}

	public boolean isDefaultShipping() {
		return isDefaultShipping;
	}

	public void setDefaultShipping(boolean isDefaultShipping) {
		this.isDefaultShipping = isDefaultShipping;
	}
	
	public String getEditUrl() {
		return editUrl;
	}
	
	public void setEditUrl(String editUrl) {
		this.editUrl = editUrl;
	}
	
	public String getDeleteUrl() {
		return deleteUrl;
	}
	
	public void setDeleteUrl(String deleteUrl) {
		this.deleteUrl = deleteUrl;
	}
	
}