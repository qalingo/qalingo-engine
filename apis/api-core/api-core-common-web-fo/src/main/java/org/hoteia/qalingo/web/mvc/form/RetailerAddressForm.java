/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.form;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * 
 */
public class RetailerAddressForm implements Serializable {
	
    /**
     * 
     */
    private static final long serialVersionUID = 1618038512177555709L;
    
	private String address1;
    private String address2;
    private String addressAdditionalInformation;
    private String postalCode;
    private String city;
    private String stateCode;
    private String areaCode;
    private String countryCode;
    
    private String phone;
    private String fax;    
    
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

	@NotEmpty(message = "error.form.customer.create.account.postal.code.empty")
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@NotEmpty(message = "error.form.customer.create.account.city.empty")
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

	@NotEmpty(message = "error.form.customer.create.account.country.code.empty")
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
	
	public String getFax() {
	    return fax;
    }
	
	public void setFax(String fax) {
	    this.fax = fax;
    }

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((address1 == null) ? 0 : address1.hashCode());
	    result = prime * result + ((address2 == null) ? 0 : address2.hashCode());
	    result = prime * result + ((addressAdditionalInformation == null) ? 0 : addressAdditionalInformation.hashCode());
	    result = prime * result + ((areaCode == null) ? 0 : areaCode.hashCode());
	    result = prime * result + ((city == null) ? 0 : city.hashCode());
	    result = prime * result + ((countryCode == null) ? 0 : countryCode.hashCode());
	    result = prime * result + ((fax == null) ? 0 : fax.hashCode());
	    result = prime * result + ((phone == null) ? 0 : phone.hashCode());
	    result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
	    result = prime * result + ((stateCode == null) ? 0 : stateCode.hashCode());
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
	    RetailerAddressForm other = (RetailerAddressForm) obj;
	    if (address1 == null) {
		    if (other.address1 != null)
			    return false;
	    } else if (!address1.equals(other.address1))
		    return false;
	    if (address2 == null) {
		    if (other.address2 != null)
			    return false;
	    } else if (!address2.equals(other.address2))
		    return false;
	    if (addressAdditionalInformation == null) {
		    if (other.addressAdditionalInformation != null)
			    return false;
	    } else if (!addressAdditionalInformation.equals(other.addressAdditionalInformation))
		    return false;
	    if (areaCode == null) {
		    if (other.areaCode != null)
			    return false;
	    } else if (!areaCode.equals(other.areaCode))
		    return false;
	    if (city == null) {
		    if (other.city != null)
			    return false;
	    } else if (!city.equals(other.city))
		    return false;
	    if (countryCode == null) {
		    if (other.countryCode != null)
			    return false;
	    } else if (!countryCode.equals(other.countryCode))
		    return false;
	    if (fax == null) {
		    if (other.fax != null)
			    return false;
	    } else if (!fax.equals(other.fax))
		    return false;
	    if (phone == null) {
		    if (other.phone != null)
			    return false;
	    } else if (!phone.equals(other.phone))
		    return false;
	    if (postalCode == null) {
		    if (other.postalCode != null)
			    return false;
	    } else if (!postalCode.equals(other.postalCode))
		    return false;
	    if (stateCode == null) {
		    if (other.stateCode != null)
			    return false;
	    } else if (!stateCode.equals(other.stateCode))
		    return false;
	    return true;
    }

	@Override
    public String toString() {
	    return "RetailerAddressForm [address1=" + address1 + ", address2=" + address2 + ", addressAdditionalInformation=" + addressAdditionalInformation + ", postalCode=" + postalCode + ", city="
	            + city + ", stateCode=" + stateCode + ", areaCode=" + areaCode + ", countryCode=" + countryCode + ", phone=" + phone + ", fax=" + fax + "]";
    }

}