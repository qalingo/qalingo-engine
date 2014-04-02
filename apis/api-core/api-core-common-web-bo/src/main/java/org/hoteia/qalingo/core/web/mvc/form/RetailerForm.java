/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.form;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * 
 */
public class RetailerForm {

	private String id;
	private String code;
	private String name;
	private String description;

	protected boolean isOfficialRetailer;
	protected boolean isBrand;
	protected boolean isEcommerce;
	protected boolean isCorner;

	private String address1;
	private String address2;
	private String addressAdditionalInformation;
	private String postalCode;
	private String city;
	private String stateCode;
	private String areaCode;
	private String countryCode;
	private String warehouseId;

	private String longitude;
	private String latitude;

	private String phone;
	private String fax;
	private String mobile;
	private String email;
	private String website;

	MultipartFile file;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@NotEmpty(message = "bo.retailer.error_form_retailer_code_empty")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@NotEmpty(message = "bo.retailer.error_form_retailer_name_empty")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotEmpty(message = "bo.retailer.error_form_retailer_address_empty")
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

	@NotEmpty(message = "bo.retailer.error_form_retailer_postal_code_empty")
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@NotEmpty(message = "bo.retailer.error_form_retailer_city_empty")
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

	@NotEmpty(message = "bo.retailer.error_form_retailer_country_empty")
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public boolean isOfficialRetailer() {
		return isOfficialRetailer;
	}

	public void setOfficialRetailer(boolean isOfficialRetailer) {
		this.isOfficialRetailer = isOfficialRetailer;
	}

	public boolean isBrand() {
		return isBrand;
	}

	public void setBrand(boolean isBrand) {
		this.isBrand = isBrand;
	}

	public boolean isEcommerce() {
		return isEcommerce;
	}

	public void setEcommerce(boolean isEcommerce) {
		this.isEcommerce = isEcommerce;
	}

	public boolean isCorner() {
		return isCorner;
	}

	public void setCorner(boolean isCorner) {
		this.isCorner = isCorner;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
}