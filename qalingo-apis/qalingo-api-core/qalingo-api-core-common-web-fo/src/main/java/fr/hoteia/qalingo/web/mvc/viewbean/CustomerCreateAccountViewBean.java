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

public class CustomerCreateAccountViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 1775936363742788633L;
	
	private String titleLabel;
	private String lastnameLabel;
    private String firstnameLabel;

    private String address1Label;
    private String address2Label;
    private String addressAdditionalInformationLabel;
    private String postalCodeLabel;
    private String cityLabel;
    private String countyCodeLabel;
    private String countryCodeLabel;
    
    private String emailLabel;
    private String passwordLabel;
    
    private String phoneLabel;
    private String faxLabel;
    private String mobileLabel;
    private String optinLabel;

    private String submitLabel;
    private String cancelLabel;
    
    private String backUrl;
    
    private String successMessage;
    private String failMessage;
    
    public String getTitleLabel() {
		return titleLabel;
	}
    
    public void setTitleLabel(String titleLabel) {
		this.titleLabel = titleLabel;
	}
    
	public String getLastnameLabel() {
		return lastnameLabel;
	}
	
	public void setLastnameLabel(String lastnameLabel) {
		this.lastnameLabel = lastnameLabel;
	}
	
	public String getFirstnameLabel() {
		return firstnameLabel;
	}
	
	public void setFirstnameLabel(String firstnameLabel) {
		this.firstnameLabel = firstnameLabel;
	}
	
	public String getAddress1Label() {
		return address1Label;
	}

	public void setAddress1Label(String address1Label) {
		this.address1Label = address1Label;
	}

	public String getAddress2Label() {
		return address2Label;
	}

	public void setAddress2Label(String address2Label) {
		this.address2Label = address2Label;
	}

	public String getAddressAdditionalInformationLabel() {
		return addressAdditionalInformationLabel;
	}

	public void setAddressAdditionalInformationLabel(
			String addressAdditionalInformationLabel) {
		this.addressAdditionalInformationLabel = addressAdditionalInformationLabel;
	}

	public String getPostalCodeLabel() {
		return postalCodeLabel;
	}

	public void setPostalCodeLabel(String postalCodeLabel) {
		this.postalCodeLabel = postalCodeLabel;
	}

	public String getCityLabel() {
		return cityLabel;
	}
	
	public void setCityLabel(String cityLabel) {
		this.cityLabel = cityLabel;
	}
	
	public String getCountyCodeLabel() {
		return countyCodeLabel;
	}

	public void setCountyCodeLabel(String countyCodeLabel) {
		this.countyCodeLabel = countyCodeLabel;
	}

	public String getCountryCodeLabel() {
		return countryCodeLabel;
	}

	public void setCountryCodeLabel(String countryCodeLabel) {
		this.countryCodeLabel = countryCodeLabel;
	}

	public String getEmailLabel() {
		return emailLabel;
	}
	
	public void setEmailLabel(String emailLabel) {
		this.emailLabel = emailLabel;
	}
	
	public String getPasswordLabel() {
		return passwordLabel;
	}
	
	public void setPasswordLabel(String passwordLabel) {
		this.passwordLabel = passwordLabel;
	}
	
	public String getPhoneLabel() {
		return phoneLabel;
	}
	
	public void setPhoneLabel(String phoneLabel) {
		this.phoneLabel = phoneLabel;
	}
	
	public String getFaxLabel() {
		return faxLabel;
	}
	
	public void setFaxLabel(String faxLabel) {
		this.faxLabel = faxLabel;
	}
	
	public String getMobileLabel() {
		return mobileLabel;
	}
	
	public void setMobileLabel(String mobileLabel) {
		this.mobileLabel = mobileLabel;
	}
	
	public String getOptinLabel() {
		return optinLabel;
	}
	
	public void setOptinLabel(String optinLabel) {
		this.optinLabel = optinLabel;
	}
	
	public String getSubmitLabel() {
		return submitLabel;
	}

	public void setSubmitLabel(String submitLabel) {
		this.submitLabel = submitLabel;
	}

	public String getCancelLabel() {
		return cancelLabel;
	}

	public void setCancelLabel(String cancelLabel) {
		this.cancelLabel = cancelLabel;
	}
	
	public String getBackUrl() {
		return backUrl;
	}
	
	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}
	
	public String getSuccessMessage() {
		return successMessage;
	}
	
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}
	
	public String getFailMessage() {
		return failMessage;
	}
	
	public void setFailMessage(String failMessage) {
		this.failMessage = failMessage;
	}
	
}