/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.user;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hoteia.qalingo.core.pojo.LocalizationPojo;
import org.hoteia.qalingo.core.pojo.product.ProductBrandPojo;
import org.hoteia.qalingo.core.pojo.retailer.RetailerPojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyPojo {

    private Long id;
    private int version;
    private String code;
    private String name;
    private String description;
    private String theme;
    private boolean active;
    
    private String address1;
    private String address2;
    private String addressAdditionalInformation;
    private String postalCode;
    private String city;
    private String stateCode;
    private String areaCode;
    private String countryCode;
    private String legalGuid;
    
    private String email;
    private String phone;
    private String fax;
    private String website;
    
    private Long createdByUserId;
    
    private Set<UserPojo> users = new HashSet<UserPojo>();
    private LocalizationPojo defaultLocalization;

    private Set<LocalizationPojo> localizations = new HashSet<LocalizationPojo>();

    private Set<ProductBrandPojo> productBrands = new HashSet<ProductBrandPojo>();

    private Set<RetailerPojo> retailers = new HashSet<RetailerPojo>();

    private Date dateCreate;
    private Date dateUpdate;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public String getLegalGuid() {
        return legalGuid;
    }

    public void setLegalGuid(String legalGuid) {
        this.legalGuid = legalGuid;
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

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Set<UserPojo> getUsers() {
        return users;
    }

    public void setUsers(Set<UserPojo> users) {
        this.users = users;
    }

    public LocalizationPojo getDefaultLocalization() {
        return defaultLocalization;
    }

    public void setDefaultLocalization(LocalizationPojo defaultLocalization) {
        this.defaultLocalization = defaultLocalization;
    }

    public Set<LocalizationPojo> getLocalizations() {
        return localizations;
    }

    public void setLocalizations(Set<LocalizationPojo> localizations) {
        this.localizations = localizations;
    }

    public Set<ProductBrandPojo> getProductBrands() {
        return productBrands;
    }

    public void setProductBrands(Set<ProductBrandPojo> productBrands) {
        this.productBrands = productBrands;
    }

    public Set<RetailerPojo> getRetailers() {
        return retailers;
    }

    public void setRetailers(Set<RetailerPojo> retailers) {
        this.retailers = retailers;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

}