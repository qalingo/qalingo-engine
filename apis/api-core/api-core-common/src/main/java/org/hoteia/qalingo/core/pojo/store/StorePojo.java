/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.store;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StorePojo extends LightStorePojo {

    protected String address1;
    protected String address2;
    protected String addressAdditionalInformation;
    protected String postalCode;
    protected String city;
    protected String stateCode;
    protected String areaCode;
    protected String countryCode;
    protected String countryLabel;

    protected Date dateCreate;
    protected Date dateUpdate;

    protected List<StoreAttributePojo> attributes = new ArrayList<StoreAttributePojo>();
    protected List<StoreBusinessHourPojo> businessHours = new ArrayList<StoreBusinessHourPojo>();
    protected List<StoreServicePojo> services = new ArrayList<StoreServicePojo>();

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
    
    public String getCountryLabel() {
        return countryLabel;
    }
    
    public void setCountryLabel(String countryLabel) {
        this.countryLabel = countryLabel;
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

    public List<StoreAttributePojo> getAttributes() {
        return attributes;
    }
    
    public void setAttributes(List<StoreAttributePojo> attributes) {
        this.attributes = attributes;
    }
    
    public List<StoreBusinessHourPojo> getBusinessHours() {
        return businessHours;
    }
    
    public void setBusinessHours(List<StoreBusinessHourPojo> businessHours) {
        this.businessHours = businessHours;
    }
    
    public List<StoreServicePojo> getServices() {
        return services;
    }
    
    public void setServices(List<StoreServicePojo> services) {
        this.services = services;
    }
    
}