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

public abstract class AbstractAddressViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
    private static final long serialVersionUID = 6696636885155166315L;
    
    private String address1;
    private String address2;
    private String addressAdditionalInformation;
    private String postalCode;
    private String city;
    private String stateCode;
    private String stateLabel;
    private String areaCode;
    private String areaLabel;
    private String countryCode;
    private String countryLabel;
    private String longitude;
    private String latitude;
    
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

	public String getStateLabel() {
    	return stateLabel;
    }

	public void setStateLabel(String stateLabel) {
    	this.stateLabel = stateLabel;
    }

	public String getAreaCode() {
    	return areaCode;
    }

	public void setAreaCode(String areaCode) {
    	this.areaCode = areaCode;
    }

	public String getAreaLabel() {
    	return areaLabel;
    }

	public void setAreaLabel(String areaLabel) {
    	this.areaLabel = areaLabel;
    }

	public String getCountryCode() {
    	return countryCode;
    }

	public void setCountryCode(String countryCode) {
    	this.countryCode = countryCode;
    }

	public String getCountryLabel() {
    	return countryLabel;
    }

	public void setCountryLabel(String countryLabel) {
    	this.countryLabel = countryLabel;
    }

	public String getLongitude() {
    	return longitude;
    }

	public void setLongitude(String longitude) {
    	this.longitude = longitude;
    }

	public String getLatitude() {
    	return latitude;
    }

	public void setLatitude(String latitude) {
    	this.latitude = latitude;
    }
	
}