/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo;

import java.util.ArrayList;
import java.util.List;

public class ContextPojo {

    private String marketPlaceCode;
    private String marketCode;
    private String marketAreaCode;
    private String marketAreaLocalizationCode;
    private String marketAreaRetailerCode;
    private String marketAreaCurrencyCode;

    private String backoffcieLocalizationCode;

    private String customerCode;
    private String userCode;
    private String companyCode;
    
    private int cartMaxItemQuantity;

    private List<UrlPojo> urls = new ArrayList<UrlPojo>();

    public String getMarketPlaceCode() {
        return marketPlaceCode;
    }

    public void setMarketPlaceCode(String marketPlaceCode) {
        this.marketPlaceCode = marketPlaceCode;
    }

    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    public String getMarketAreaCode() {
        return marketAreaCode;
    }

    public void setMarketAreaCode(String marketAreaCode) {
        this.marketAreaCode = marketAreaCode;
    }

    public String getMarketAreaLocalizationCode() {
        return marketAreaLocalizationCode;
    }

    public void setMarketAreaLocalizationCode(String marketAreaLocalizationCode) {
        this.marketAreaLocalizationCode = marketAreaLocalizationCode;
    }

    public String getMarketAreaRetailerCode() {
        return marketAreaRetailerCode;
    }

    public void setMarketAreaRetailerCode(String marketAreaRetailerCode) {
        this.marketAreaRetailerCode = marketAreaRetailerCode;
    }

    public String getMarketAreaCurrencyCode() {
        return marketAreaCurrencyCode;
    }

    public void setMarketAreaCurrencyCode(String marketAreaCurrencyCode) {
        this.marketAreaCurrencyCode = marketAreaCurrencyCode;
    }

    public String getBackoffcieLocalizationCode() {
        return backoffcieLocalizationCode;
    }

    public void setBackoffcieLocalizationCode(String backoffcieLocalizationCode) {
        this.backoffcieLocalizationCode = backoffcieLocalizationCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public int getCartMaxItemQuantity() {
        return cartMaxItemQuantity;
    }
    
    public void setCartMaxItemQuantity(int cartMaxItemQuantity) {
        this.cartMaxItemQuantity = cartMaxItemQuantity;
    }
    
    public List<UrlPojo> getUrls() {
        return urls;
    }
    
    public void setUrls(List<UrlPojo> urls) {
        this.urls = urls;
    }
    
}