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

public class CustomerAddressViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 2347791718108024528L;
	
    private Long id;
    private String addressName;
    private String title;
    private String lastname;
    private String firstname;
    private String address1;
    private String address2;
    private String addressAdditionalInformation;
    private String postalCode;
    private String city;
    private String stateCode;
    private String areaCode;
    private String countryCode;
	private Long customerId;
	private boolean isDefaultBilling;
	private boolean isDefaultShipping;

    private String editUrl;
    private String deleteUrl;
    
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getAddressName() {
		return addressName;
	}
	
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddressAdditionalInformation() {
		return addressAdditionalInformation;
	}

	public void setAddressAdditionalInformation(String addressAdditionalInformation) {
		this.addressAdditionalInformation = addressAdditionalInformation;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getStateCode() {
	    return stateCode;
    }
	
	public void setStateCode(String stateCode) {
	    this.stateCode = stateCode;
    }

	public String getAreaCode() {
	    return areaCode;
    }
	
	public void setAreaCode(String areaCode) {
	    this.areaCode = areaCode;
    }
	
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
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