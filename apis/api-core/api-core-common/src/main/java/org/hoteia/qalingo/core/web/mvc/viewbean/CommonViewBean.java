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
    protected String checkoutCreateAccountUrl;
	protected String customerDetailsUrl;
    protected String personalDetailsUrl;
	protected String contactUrl;
    protected String contextJsonUrl;

	protected MarketPlaceViewBean currentMarketPlace;
	protected MarketViewBean currentMarket;
	protected MarketAreaViewBean currentMarketArea;
	protected LocalizationViewBean currentMarketAreaLocalization;
    protected RetailerViewBean currentMarketAreaRetailer;
    protected CurrencyReferentialViewBean currentMarketAreaCurrency;

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
	
	public String getCheckoutCreateAccountUrl() {
        return checkoutCreateAccountUrl;
    }
	
	public void setCheckoutCreateAccountUrl(String checkoutCreateAccountUrl) {
        this.checkoutCreateAccountUrl = checkoutCreateAccountUrl;
    }

	public String getCustomerDetailsUrl() {
		return customerDetailsUrl;
	}

	public void setCustomerDetailsUrl(String customerDetailsUrl) {
		this.customerDetailsUrl = customerDetailsUrl;
	}
	
	public String getPersonalDetailsUrl() {
        return personalDetailsUrl;
    }
	
	public void setPersonalDetailsUrl(String personalDetailsUrl) {
        this.personalDetailsUrl = personalDetailsUrl;
    }

	public String getContactUrl() {
		return contactUrl;
	}
	
	public void setContactUrl(String contactUrl) {
		this.contactUrl = contactUrl;
	}
	
	public String getContextJsonUrl() {
        return contextJsonUrl;
    }
	
	public void setContextJsonUrl(String contextJsonUrl) {
        this.contextJsonUrl = contextJsonUrl;
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

	public LocalizationViewBean getCurrentMarketAreaLocalization() {
		return currentMarketAreaLocalization;
	}

	public void setCurrentMarketAreaLocalization(LocalizationViewBean currentLocalization) {
		this.currentMarketAreaLocalization = currentLocalization;
	}
	
	public RetailerViewBean getCurrentMarketAreaRetailer() {
        return currentMarketAreaRetailer;
    }
	
	public void setCurrentMarketAreaRetailer(RetailerViewBean currentMarketAreaRetailer) {
        this.currentMarketAreaRetailer = currentMarketAreaRetailer;
    }
	
	public CurrencyReferentialViewBean getCurrentMarketAreaCurrency() {
        return currentMarketAreaCurrency;
    }
	
	public void setCurrentMarketAreaCurrency(CurrencyReferentialViewBean currentMarketAreaCurrency) {
        this.currentMarketAreaCurrency = currentMarketAreaCurrency;
    }
	
}