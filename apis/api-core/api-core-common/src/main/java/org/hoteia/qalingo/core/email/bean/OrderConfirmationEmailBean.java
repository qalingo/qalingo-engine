/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.email.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class OrderConfirmationEmailBean extends AbstractEmailBean implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 8775711859740312774L;
    
    private String orderNumber;
    private String expectedDeliveryDate;
    
    private String companyName;
    private String titleCode;
    private String titleLabel;
    private String lastname;
    private String firstname;
    protected String address1;
    protected String address2;
    protected String addressAdditionalInformation;
    protected String postalCode;
    protected String city;
    protected String i18nCity;
    protected String stateCode;
    protected String stateLabel;
    protected String areaCode;
    protected String areaLabel;
    protected String countryCode;
    protected String country;
    
    protected List<OrderItemEmailBean> orderItems = new ArrayList<OrderItemEmailBean>();
    
    private String orderItemsTotalWithCurrencySign;
    private String orderShippingTotalWithCurrencySign;
    private String orderTaxesTotalWithCurrencySign;
    private String orderTotalWithCurrencySign;
    
    private String customerDetailsUrl;

    public String getOrderNumber() {
        return orderNumber;
    }
    
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTitleCode() {
        return titleCode;
    }

    public void setTitleCode(String titleCode) {
        this.titleCode = titleCode;
    }

    public String getTitleLabel() {
        return titleLabel;
    }

    public void setTitleLabel(String titleLabel) {
        this.titleLabel = titleLabel;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
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

    public String getI18nCity() {
        return i18nCity;
    }

    public void setI18nCity(String i18nCity) {
        this.i18nCity = i18nCity;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddressOnLine() {
        StringBuffer address = new StringBuffer();
        if(StringUtils.isNotEmpty(address1)){
            address.append(address1);
        }
        if(postalCode != null || city != null){
            address.append(" - ");
        }
        if(StringUtils.isNotEmpty(postalCode)){
            address.append(postalCode + " ");
        }
        if(StringUtils.isNotEmpty(i18nCity)){
            address.append(i18nCity);
        } else {
            if(StringUtils.isNotEmpty(city)){
                address.append(city);
            }
        }
        if(StringUtils.isNotEmpty(country)){
            address.append(" - " + country);
        } else {
            if(countryCode != null){
                address.append(" - " + countryCode);
            }  
        }
        return address.toString();
    }
    
    public String getAddressHtmlBlock() {
        StringBuffer address = new StringBuffer();
        if(StringUtils.isNotEmpty(address1)){
            address.append(address1);
        }
        if(StringUtils.isNotEmpty(address2)){
            address.append("<br/>" + address2);
        }
        if(StringUtils.isNotEmpty(addressAdditionalInformation)){
            address.append("<br/>" + addressAdditionalInformation);
        }
        if(postalCode != null || city != null){
            address.append("<br/>");
        }
        if(StringUtils.isNotEmpty(postalCode)){
            address.append(postalCode + " ");
        }
        if(StringUtils.isNotEmpty(i18nCity)){
            address.append(i18nCity);
        } else {
            if(StringUtils.isNotEmpty(city)){
                address.append(city);
            }
        }
        if(StringUtils.isNotEmpty(country)){
            address.append("<br/>" + country);
        } else {
            if(countryCode != null){
                address.append("<br/>" + countryCode);
            }  
        }
        return address.toString();
    }
    
    public List<OrderItemEmailBean> getOrderItems() {
        return orderItems;
    }
    
    public void setOrderItems(List<OrderItemEmailBean> orderItems) {
        this.orderItems = orderItems;
    }
    
    public String getOrderItemsTotalWithCurrencySign() {
        return orderItemsTotalWithCurrencySign;
    }

    public void setOrderItemsTotalWithCurrencySign(String orderItemsTotalWithCurrencySign) {
        this.orderItemsTotalWithCurrencySign = orderItemsTotalWithCurrencySign;
    }

    public String getOrderShippingTotalWithCurrencySign() {
        return orderShippingTotalWithCurrencySign;
    }

    public void setOrderShippingTotalWithCurrencySign(String orderShippingTotalWithCurrencySign) {
        this.orderShippingTotalWithCurrencySign = orderShippingTotalWithCurrencySign;
    }

    public String getOrderTaxesTotalWithCurrencySign() {
        return orderTaxesTotalWithCurrencySign;
    }

    public void setOrderTaxesTotalWithCurrencySign(String orderTaxesTotalWithCurrencySign) {
        this.orderTaxesTotalWithCurrencySign = orderTaxesTotalWithCurrencySign;
    }

    public String getOrderTotalWithCurrencySign() {
        return orderTotalWithCurrencySign;
    }

    public void setOrderTotalWithCurrencySign(String orderTotalWithCurrencySign) {
        this.orderTotalWithCurrencySign = orderTotalWithCurrencySign;
    }

    public String getCustomerDetailsUrl() {
        return customerDetailsUrl;
    }

    public void setCustomerDetailsUrl(String customerDetailsUrl) {
        this.customerDetailsUrl = customerDetailsUrl;
    }
    
}