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

public class CustomerAddressFormViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 886368251190109572L;
	
	private String addressNameLabel;
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
    
    private String createLabel;
    private String updateLabel;
    private String cancelLabel;
    
    private String backUrl;
    
    private String successMessage;
    private String failMessage;
    
    public String getAddressNameLabel() {
		return addressNameLabel;
	}
    
    public void setAddressNameLabel(String addressNameLabel) {
		this.addressNameLabel = addressNameLabel;
	}
    
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

	public String getCreateLabel() {
		return createLabel;
	}
	
	public void setCreateLabel(String createLabel) {
		this.createLabel = createLabel;
	}
	
	public String getUpdateLabel() {
		return updateLabel;
	}
	
	public void setUpdateLabel(String updateLabel) {
		this.updateLabel = updateLabel;
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