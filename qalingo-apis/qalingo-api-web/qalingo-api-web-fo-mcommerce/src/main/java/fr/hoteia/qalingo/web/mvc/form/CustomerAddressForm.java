/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.form;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * 
 */
public class CustomerAddressForm {
	
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
    
    public String getAddressName() {
		return addressName;
	}
    
    public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
    
	@NotEmpty(message = "error.form.create.account.title.empty")
    public String getTitle() {
		return title;
	}
    
    public void setTitle(String title) {
		this.title = title;
	}
    
	@NotEmpty(message = "error.form.create.account.lastname.empty")
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@NotEmpty(message = "error.form.create.account.firstname.empty")
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@NotEmpty(message = "error.form.create.account.address1.empty")
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

	@NotEmpty(message = "error.form.create.account.postal.code.empty")
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	@NotEmpty(message = "error.form.create.account.city.empty")
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

	@NotEmpty(message = "error.form.create.account.country.code.empty")
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

}