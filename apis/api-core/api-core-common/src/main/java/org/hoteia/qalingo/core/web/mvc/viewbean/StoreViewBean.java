/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class StoreViewBean extends AbstractViewBean implements Serializable {

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
	private String longitude;
	private String latitude;
	private String defaultImage;
	private String iconImage;
	
	private String detailsUrl;
	private String editUrl;

	private List<String> sliders;

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

	public void setAddressAdditionalInformation(
			String addressAdditionalInformation) {
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

	@Override
	public String toString() {
		return "StoreViewBean [code=" + code + ", name=" + name + ", i18nName="
				+ i18nName + ", address1=" + address1 + ", address2="
				+ address2 + ", addressAdditionalInformation="
				+ addressAdditionalInformation + ", postalCode=" + postalCode
				+ ", city=" + city + ", stateCode=" + stateCode + ", areaCode="
				+ areaCode + ", countryCode=" + countryCode + ", country="
				+ country + ", longitude=" + longitude + ", latitude="
				+ latitude + ", defaultImage=" + defaultImage + ", iconImage="
				+ iconImage + ", detailsUrl=" + detailsUrl + ", editUrl="
				+ editUrl + ", sliders=" + sliders + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((address1 == null) ? 0 : address1.hashCode());
		result = prime * result
				+ ((address2 == null) ? 0 : address2.hashCode());
		result = prime
				* result
				+ ((addressAdditionalInformation == null) ? 0
						: addressAdditionalInformation.hashCode());
		result = prime * result
				+ ((areaCode == null) ? 0 : areaCode.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result
				+ ((countryCode == null) ? 0 : countryCode.hashCode());
		result = prime * result
				+ ((defaultImage == null) ? 0 : defaultImage.hashCode());
		result = prime * result
				+ ((detailsUrl == null) ? 0 : detailsUrl.hashCode());
		result = prime * result + ((editUrl == null) ? 0 : editUrl.hashCode());
		result = prime * result
				+ ((i18nName == null) ? 0 : i18nName.hashCode());
		result = prime * result
				+ ((iconImage == null) ? 0 : iconImage.hashCode());
		result = prime * result
				+ ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result
				+ ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((postalCode == null) ? 0 : postalCode.hashCode());
		result = prime * result + ((sliders == null) ? 0 : sliders.hashCode());
		result = prime * result
				+ ((stateCode == null) ? 0 : stateCode.hashCode());
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
		} else if (!addressAdditionalInformation
				.equals(other.addressAdditionalInformation))
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
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (countryCode == null) {
			if (other.countryCode != null)
				return false;
		} else if (!countryCode.equals(other.countryCode))
			return false;
		if (defaultImage == null) {
			if (other.defaultImage != null)
				return false;
		} else if (!defaultImage.equals(other.defaultImage))
			return false;
		if (detailsUrl == null) {
			if (other.detailsUrl != null)
				return false;
		} else if (!detailsUrl.equals(other.detailsUrl))
			return false;
		if (editUrl == null) {
			if (other.editUrl != null)
				return false;
		} else if (!editUrl.equals(other.editUrl))
			return false;
		if (i18nName == null) {
			if (other.i18nName != null)
				return false;
		} else if (!i18nName.equals(other.i18nName))
			return false;
		if (iconImage == null) {
			if (other.iconImage != null)
				return false;
		} else if (!iconImage.equals(other.iconImage))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (postalCode == null) {
			if (other.postalCode != null)
				return false;
		} else if (!postalCode.equals(other.postalCode))
			return false;
		if (sliders == null) {
			if (other.sliders != null)
				return false;
		} else if (!sliders.equals(other.sliders))
			return false;
		if (stateCode == null) {
			if (other.stateCode != null)
				return false;
		} else if (!stateCode.equals(other.stateCode))
			return false;
		return true;
	}

}