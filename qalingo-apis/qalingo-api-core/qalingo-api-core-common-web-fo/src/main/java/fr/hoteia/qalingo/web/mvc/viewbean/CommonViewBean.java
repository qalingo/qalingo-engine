/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.viewbean;

import java.io.Serializable;

public class CommonViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -6094174679811232111L;
	
	protected String themeResourcePrefixPath;
	
	protected String homeUrl;
	protected String loginUrl;
	protected String forgottenPasswordUrl;
	protected String logoutUrl;
	protected String createAccountUrl;
	protected String customerDetailsUrl;
	protected String contactUrl;

	protected MarketPlaceViewBean currentMarketPlace;
	protected MarketViewBean currentMarket;
	protected MarketAreaViewBean currentMarketArea;
	protected LocalizationViewBean currentLocalization;

	public String getThemeResourcePrefixPath() {
		return themeResourcePrefixPath;
	}
	
	public void setThemeResourcePrefixPath(String themeResourcePrefixPath) {
		this.themeResourcePrefixPath = themeResourcePrefixPath;
	}
	
	public String getHomeUrl() {
		return homeUrl;
	}
	
	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getForgottenPasswordUrl() {
		return forgottenPasswordUrl;
	}

	public void setForgottenPasswordUrl(String forgottenPasswordUrl) {
		this.forgottenPasswordUrl = forgottenPasswordUrl;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	public String getCreateAccountUrl() {
		return createAccountUrl;
	}

	public void setCreateAccountUrl(String createAccountUrl) {
		this.createAccountUrl = createAccountUrl;
	}

	public String getCustomerDetailsUrl() {
		return customerDetailsUrl;
	}

	public void setCustomerDetailsUrl(String customerDetailsUrl) {
		this.customerDetailsUrl = customerDetailsUrl;
	}

	public String getContactUrl() {
		return contactUrl;
	}
	
	public void setContactUrl(String contactUrl) {
		this.contactUrl = contactUrl;
	}

	public MarketPlaceViewBean getCurrentMarketPlace() {
		return currentMarketPlace;
	}

	public void setCurrentMarketPlace(MarketPlaceViewBean currentMarketPlace) {
		this.currentMarketPlace = currentMarketPlace;
	}

	public MarketViewBean getCurrentMarket() {
		return currentMarket;
	}

	public void setCurrentMarket(MarketViewBean currentMarket) {
		this.currentMarket = currentMarket;
	}

	public MarketAreaViewBean getCurrentMarketArea() {
		return currentMarketArea;
	}

	public void setCurrentMarketArea(MarketAreaViewBean currentMarketArea) {
		this.currentMarketArea = currentMarketArea;
	}

	public LocalizationViewBean getCurrentLocalization() {
		return currentLocalization;
	}

	public void setCurrentLocalization(LocalizationViewBean currentLocalization) {
		this.currentLocalization = currentLocalization;
	}
	
}