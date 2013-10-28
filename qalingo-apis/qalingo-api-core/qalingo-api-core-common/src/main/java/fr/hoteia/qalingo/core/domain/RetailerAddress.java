/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="TECO_RETAILER_ADDRESS")
public class RetailerAddress extends AbstractAddress implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 2501911341288490523L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;

	@Version
	@Column(name="VERSION", nullable=false, columnDefinition="int(11) default 1")
	private int version;
	
	@Column(name="ADDRESS1")
    private String address1;
	
	@Column(name="ADDRESS2")
    private String address2;
	
	@Column(name="ADDITIONAL_INFORMATION")
    private String addressAdditionalInformation;
	
	@Column(name="POSTAL_CODE")
    private String postalCode;
	
	@Column(name="CITY")
    private String city;

	@Column(name="STATE_CODE")
    private String stateCode;

	@Column(name="AREA_CODE")
    private String areaCode;

	@Column(name="COUNTRY_CODE")
    private String countryCode;
	
	@Column(name="PHONE")
    private String phone;
	
	@Column(name="MOBILE")
    private String mobile;
	
	@Column(name="FAX")
    private String fax;
	
	@Column(name="EMAIL")
    private String email;
	
	@Column(name="WEBSITE")
    private String website;
	
	@Column(name="IS_DEFAULT", nullable=false, columnDefinition="tinyint(1) default 0")
	private boolean isDefault;
	
	@Column(name="LONGITUDE")
	private String longitude;
	
	@Column(name="LATITUDE")
	private String latitude;
	
	@Column(name="RETAILER_ID")
	private Long retailerId;
	
	public RetailerAddress() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public int getVersion() {
    	return version;
    }

	public void setVersion(int version) {
    	this.version = version;
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

	public boolean isDefault() {
    	return isDefault;
    }

	public void setDefault(boolean isDefault) {
    	this.isDefault = isDefault;
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
	
	public Long getRetailerId() {
	    return retailerId;
    }
	
	public void setRetailerId(Long retailerId) {
	    this.retailerId = retailerId;
    }
	
}