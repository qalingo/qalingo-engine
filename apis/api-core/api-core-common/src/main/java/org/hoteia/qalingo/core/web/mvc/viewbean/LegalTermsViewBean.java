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

public class LegalTermsViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 4770655450241546076L;
	
	protected String warning;
	protected String copyright;

	protected String companyName;
	protected String companyAddress;
	protected String companyAddressAdditionalInfo;
	protected String companyZipOrPostalCode;
	protected String companyCity;
	protected String companyState;
	protected String companyCountry;
	protected String companyPhone;
	protected String companyFax;
	protected String companyEmail;
	protected String companyWebsiteUrl;
	protected String companyWebsiteName;

	public String getWarning() {
		return warning;
	}
	
	public void setWarning(String warning) {
		this.warning = warning;
	}
	
	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getCompanyName() {
    	return companyName;
    }

	public void setCompanyName(String companyName) {
    	this.companyName = companyName;
    }

	public String getCompanyAddress() {
    	return companyAddress;
    }

	public void setCompanyAddress(String companyAddress) {
    	this.companyAddress = companyAddress;
    }

	public String getCompanyAddressAdditionalInfo() {
    	return companyAddressAdditionalInfo;
    }

	public void setCompanyAddressAdditionalInfo(String companyAddressAdditionalInfo) {
    	this.companyAddressAdditionalInfo = companyAddressAdditionalInfo;
    }

	public String getCompanyZipOrPostalCode() {
    	return companyZipOrPostalCode;
    }

	public void setCompanyZipOrPostalCode(String companyZipOrPostalCode) {
    	this.companyZipOrPostalCode = companyZipOrPostalCode;
    }

	public String getCompanyCity() {
    	return companyCity;
    }

	public void setCompanyCity(String companyCity) {
    	this.companyCity = companyCity;
    }

	public String getCompanyState() {
    	return companyState;
    }

	public void setCompanyState(String companyState) {
    	this.companyState = companyState;
    }

	public String getCompanyCountry() {
    	return companyCountry;
    }

	public void setCompanyCountry(String companyCountry) {
    	this.companyCountry = companyCountry;
    }

	public String getCompanyPhone() {
    	return companyPhone;
    }

	public void setCompanyPhone(String companyPhone) {
    	this.companyPhone = companyPhone;
    }

	public String getCompanyFax() {
    	return companyFax;
    }

	public void setCompanyFax(String companyFax) {
    	this.companyFax = companyFax;
    }

	public String getCompanyEmail() {
    	return companyEmail;
    }

	public void setCompanyEmail(String companyEmail) {
    	this.companyEmail = companyEmail;
    }
	
	public String getCompanyWebsiteUrl() {
	    return companyWebsiteUrl;
    }
	
	public void setCompanyWebsiteUrl(String companyWebsiteUrl) {
	    this.companyWebsiteUrl = companyWebsiteUrl;
    }
	
	public String getCompanyWebsiteName() {
	    return companyWebsiteName;
    }
	
	public void setCompanyWebsiteName(String companyWebsiteName) {
	    this.companyWebsiteName = companyWebsiteName;
    }
	
}