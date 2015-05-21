/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.form;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * 
 */
public class CustomerAddressForm implements Serializable {
	
    /**
     * 
     */
    private static final long serialVersionUID = 8718140220836343060L;
    
	private String id;
    private String guid;
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
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getAddressName() {
		return addressName;
	}
    
    public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
    
	@NotEmpty(message = "fo.customer.error_form_address_title_empty")
    public String getTitle() {
		return title;
	}
    
    public void setTitle(String title) {
		this.title = title;
	}
    
	@NotEmpty(message = "fo.customer.error_form_address_lastname_empty")
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@NotEmpty(message = "fo.customer.error_form_address_firstname_empty")
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@NotEmpty(message = "fo.customer.error_form_address_address1_empty")
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

	@NotEmpty(message = "fo.customer.error_form_address_zip_or_postal_code_empty")
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@NotEmpty(message = "fo.customer.error_form_address_city_empty")
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

	@NotEmpty(message = "fo.customer.error_form_address_country_code_empty")
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((addressName == null) ? 0 : addressName.hashCode());
        result = prime * result + ((countryCode == null) ? 0 : countryCode.hashCode());
        result = prime * result + ((guid == null) ? 0 : guid.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CustomerAddressForm other = (CustomerAddressForm) obj;
        if (addressName == null) {
            if (other.addressName != null)
                return false;
        } else if (!addressName.equals(other.addressName))
            return false;
        if (countryCode == null) {
            if (other.countryCode != null)
                return false;
        } else if (!countryCode.equals(other.countryCode))
            return false;
        if (guid == null) {
            if (other.guid != null)
                return false;
        } else if (!guid.equals(other.guid))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
	    return "CustomerAddressForm [addressName=" + addressName + ", title=" + title + ", lastname=" + lastname + ", firstname=" + firstname + ", address1=" + address1 + ", address2=" + address2
	            + ", addressAdditionalInformation=" + addressAdditionalInformation + ", postalCode=" + postalCode + ", city=" + city + ", stateCode=" + stateCode + ", areaCode=" + areaCode
	            + ", countryCode=" + countryCode + "]";
    }
	
}