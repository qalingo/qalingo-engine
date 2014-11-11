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

import java.util.List;

import javax.persistence.Column;

import org.apache.commons.lang.StringUtils;

public class StoreViewBean extends AbstractViewBean {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 2538607600492625532L;

	private String code;
	private String name;
	private String i18nName;

	private String address1;
	private String address2;
	private String addressAdditionalInformation;
	private String postalCode;
	private String city;
	private String stateCode;
	private String areaCode;
	private String countryCode;
	private String country;
	
    private String email;
    private String phone;
    private String fax;
    private String website;
    
	private String longitude;
	private String latitude;
	private String defaultImage;
	private String iconImage;
	
	private String detailsUrl;
	private String editUrl;

	private List<String> sliders;

	private StoreBusinessHourViewBean businessHour;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getI18nName() {
		if (StringUtils.isNotEmpty(i18nName)) {
			return i18nName;
		}
		return name;
	}

	public void setI18nName(String i18nName) {
		this.i18nName = i18nName;
	}

    public String getAddressOnLine() {
        StringBuffer address = new StringBuffer();
        if(address1 != null){
            address.append(address1);
        }
        if(postalCode != null || city != null){
            address.append(" - ");
        }
        if(postalCode != null){
            address.append(postalCode + " ");
        }
        if(city != null){
            address.append(city);
        }
        if(countryCode != null){
            address.append(" - " + countryCode);
        }
        return address.toString();
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

	public String getDefaultImage() {
		return defaultImage;
	}

	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
	}

	public String getIconImage() {
		return iconImage;
	}

	public void setIconImage(String iconImage) {
		this.iconImage = iconImage;
	}

	public String getDetailsUrl() {
		return detailsUrl;
	}

	public void setDetailsUrl(String detailsUrl) {
		this.detailsUrl = detailsUrl;
	}

	public List<String> getSliders() {
		return sliders;
	}

	public void setSliders(List<String> sliders) {
		this.sliders = sliders;
	}

	public String getEditUrl() {
		return editUrl;
	}

	public void setEditUrl(String editUrl) {
		this.editUrl = editUrl;
	}

	public StoreBusinessHourViewBean getBusinessHour() {
        return businessHour;
    }
	
	public void setBusinessHour(StoreBusinessHourViewBean businessHour) {
        this.businessHour = businessHour;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
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
        StoreViewBean other = (StoreViewBean) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "StoreViewBean [code=" + code + ", name=" + name + ", i18nName=" + i18nName + ", address1=" + address1 + ", address2=" + address2 + ", addressAdditionalInformation="
                + addressAdditionalInformation + ", postalCode=" + postalCode + ", city=" + city + ", stateCode=" + stateCode + ", areaCode=" + areaCode + ", countryCode=" + countryCode
                + ", country=" + country + ", email=" + email + ", phone=" + phone + ", fax=" + fax + ", website=" + website + ", longitude=" + longitude + ", latitude=" + latitude
                + ", defaultImage=" + defaultImage + ", iconImage=" + iconImage + ", detailsUrl=" + detailsUrl + ", editUrl=" + editUrl + ", sliders=" + sliders + ", businessHour=" + businessHour
                + "]";
    }
	
}