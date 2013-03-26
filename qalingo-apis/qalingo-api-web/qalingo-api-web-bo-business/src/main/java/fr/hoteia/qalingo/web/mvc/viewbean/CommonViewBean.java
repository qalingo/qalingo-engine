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

public class CommonViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -6094174679811232111L;
	
	protected String themeResourcePrefixPath;
	
	protected String homeUrl;
	protected String loginUrl;
	protected String loginLabel;
	protected String forgottenPasswordUrl;
	protected String logoutUrl;
	protected String logoutLabel;
	protected String userDetailsUrl;
	protected String userDetailsLabel;

	protected MarketPlaceViewBean currentMarketPlace;
	protected MarketViewBean currentMarket;
	protected MarketAreaViewBean currentMarketArea;
	protected LocalizationViewBean currentMarketLocalization;
	protected RetailerViewBean currentRetailer;
	
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

	public String getLoginLabel() {
		return loginLabel;
	}

	public void setLoginLabel(String loginLabel) {
		this.loginLabel = loginLabel;
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

	public String getLogoutLabel() {
		return logoutLabel;
	}

	public void setLogoutLabel(String logoutLabel) {
		this.logoutLabel = logoutLabel;
	}

	public String getUserDetailsUrl() {
		return userDetailsUrl;
	}

	public void setUserDetailsUrl(String userDetailsUrl) {
		this.userDetailsUrl = userDetailsUrl;
	}

	public String getUserDetailsLabel() {
		return userDetailsLabel;
	}

	public void setUserDetailsLabel(String userDetailsLabel) {
		this.userDetailsLabel = userDetailsLabel;
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

	public LocalizationViewBean getCurrentMarketLocalization() {
		return currentMarketLocalization;
	}
	
	public void setCurrentMarketLocalization(LocalizationViewBean currentMarketLocalization) {
		this.currentMarketLocalization = currentMarketLocalization;
	}
	
	public RetailerViewBean getCurrentRetailer() {
		return currentRetailer;
	}
	
	public void setCurrentRetailer(RetailerViewBean currentRetailer) {
		this.currentRetailer = currentRetailer;
	}

}
