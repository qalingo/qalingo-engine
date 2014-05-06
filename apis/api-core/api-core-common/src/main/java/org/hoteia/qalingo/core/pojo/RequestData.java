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

import java.io.Serializable;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.CatalogMaster;
import org.hoteia.qalingo.core.domain.Company;
import org.hoteia.qalingo.core.domain.CurrencyReferential;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.MarketPlace;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.web.bean.geoloc.GeolocData;

public class RequestData implements Serializable {

	/**
	 * Generated UID
	 */
    private static final long serialVersionUID = 6012861562514088614L;

	private String contextNameValue;
	private String contextPath;
	private String VelocityEmailPrefix;

	private HttpServletRequest request;
	
	private MarketPlace marketPlace;
	private Market market;
	private MarketArea marketArea;
	private Localization marketAreaLocalization;
	private Retailer marketAreaRetailer;
    private CurrencyReferential marketAreaCurrency;
    private Cart cart;

    private Localization backoffcieLocalization;

	private Customer customer;
    private User user;
    private Company company;
    
    private GeolocData geolocData;
	
	public RequestData() {
    }
	
    public RequestData(String contextPath) {
        this.contextPath = contextPath;
    }

	public RequestData(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) {
		this.marketPlace = marketPlace;
		this.market = market;
		this.marketArea = marketArea;
		this.marketAreaLocalization = localization;
		this.marketAreaRetailer = retailer;
    }
	
    public String getContextNameValue() {
        return contextNameValue;
    }

    public void setContextNameValue(String contextNameValue) {
        this.contextNameValue = contextNameValue;
    }

    public String getContextPath() {
        if (StringUtils.isNotEmpty(contextPath) 
                && contextPath.endsWith("/")) {
            return contextPath.substring(0, contextPath.length() - 1);
        }
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public void switchContextPathByMarketAreaDomainName() {
        this.contextPath = "http://" + marketArea.getDomainName(contextNameValue) + "/";
    }

    public String getVelocityEmailPrefix() {
        return VelocityEmailPrefix;
    }

    public void setVelocityEmailPrefix(String velocityEmailPrefix) {
        VelocityEmailPrefix = velocityEmailPrefix;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public MarketPlace getMarketPlace() {
        return marketPlace;
    }

    public void setMarketPlace(MarketPlace marketPlace) {
        this.marketPlace = marketPlace;
    }

    public CatalogMaster getMasterCatalog() {
        if(marketPlace != null
                && marketPlace.getMasterCatalog() != null){
            return marketPlace.getMasterCatalog();
        }
        return null;
    }
    
    public String getMasterCatalogCode() {
        if(getMasterCatalog() != null){
            return getMasterCatalog().getCode();
        }
        return null;
    }
    
    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public MarketArea getMarketArea() {
        return marketArea;
    }
    
    public String getVirtualCatalogCode() {
        if(marketArea != null
                && marketArea.getCatalog() != null){
            return marketArea.getCatalog().getCode();
        }
        return null;
    }

    public void setMarketArea(MarketArea marketArea) {
        this.marketArea = marketArea;
    }

    public Localization getMarketAreaLocalization() {
        return marketAreaLocalization;
    }

    public void setMarketAreaLocalization(Localization localization) {
        this.marketAreaLocalization = localization;
    }

    public Retailer getMarketAreaRetailer() {
        return marketAreaRetailer;
    }

    public void setMarketAreaRetailer(Retailer retailer) {
        this.marketAreaRetailer = retailer;
    }

    public CurrencyReferential getMarketAreaCurrency() {
        return marketAreaCurrency;
    }

    public void setMarketAreaCurrency(CurrencyReferential marketAreaCurrency) {
        this.marketAreaCurrency = marketAreaCurrency;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Localization getBackoffcieLocalization() {
        return backoffcieLocalization;
    }

    public void setBackoffcieLocalization(Localization backoffcieLocalization) {
        this.backoffcieLocalization = backoffcieLocalization;
    }

    public Locale getLocale(){
        Locale locale = new Locale ("en");
        try {
            if(isBackoffice()){
                if(backoffcieLocalization != null){
                    locale = backoffcieLocalization.getLocale();
                } 
            } else {
                locale = marketAreaLocalization.getLocale();
            }
        } catch (Exception e) {
            // WE DON'T NEED THE LOG
        }
        return locale;
    }
    
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public Company getCompany() {
        return company;
    }
    
    public void setCompany(Company company) {
        this.company = company;
    }
    
    public GeolocData getGeolocData() {
        return geolocData;
    }
    
    public void setGeolocData(GeolocData geolocData) {
        this.geolocData = geolocData;
    }
    
    public boolean isBackoffice() throws Exception {
        if (getContextNameValue().contains("BO_")) {
            return true;
        }
        return false;
    }

}