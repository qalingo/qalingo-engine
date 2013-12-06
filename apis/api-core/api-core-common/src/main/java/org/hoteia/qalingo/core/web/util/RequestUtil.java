/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.util;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.Company;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.EngineBoSession;
import org.hoteia.qalingo.core.domain.EngineEcoSession;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.MarketPlace;
import org.hoteia.qalingo.core.domain.OrderCustomer;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.web.clickstream.ClickstreamSession;

/**
 * 
 */
public interface RequestUtil {

	/**
	 *
	 */
	boolean isLocalHostMode(HttpServletRequest request) throws Exception;
	
	/**
	 *
	 */
	String getHost(HttpServletRequest request) throws Exception;
	
	/**
	 *
	 */
	String getEnvironmentName() throws Exception;
	
	/**
	 *
	 */
	String getApplicationName() throws Exception;
	
	/**
	 *
	 */
	String getContextName() throws Exception;
	
	/**
	 *
	 */
	DateFormat getFormatDate(RequestData requestData, int dateStyle, int timeStyle) throws Exception;
	
	/**
	 *
	 */
	SimpleDateFormat getRssFormatDate(RequestData requestData) throws Exception;
	
	/**
	 *
	 */
	SimpleDateFormat getDataVocabularyFormatDate(RequestData requestData) throws Exception;
	
	/**
	 *
	 */
	SimpleDateFormat getAtomFormatDate(RequestData requestData) throws Exception;
	
	/**
	 *
	 */
	NumberFormat getCartItemPriceNumberFormat(RequestData requestData, String currencyCode) throws Exception;
	
	/**
	 *
	 */
	NumberFormat getNumberFormat(RequestData requestData, String currencyCode, RoundingMode roundingMode, int maximumFractionDigits,
			int minimumFractionDigits, int maximumIntegerDigits, int minimumIntegerDigits) throws Exception;
	
    /**
     * 
     */
	ClickstreamSession getClickstreamSession(HttpServletRequest request) throws Exception;
        
    /**
     * 
     */
    void addClickstream(HttpServletRequest request) throws Exception;

	/**
     * 
     */
	String getLastRequestUrlNotSecurity(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	String getLastRequestUrl(HttpServletRequest request, List<String> excludedPatterns) throws Exception;

	/**
     * 
     */
	String getLastRequestUrl(HttpServletRequest request, List<String> excludedPatterns, String fallbackUrl) throws Exception;
	
	/**
     * 
     */
	String getLastRequestUrl(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	String getCurrentRequestUrl(HttpServletRequest request, List<String> excludedPatterns) throws Exception;
	
	/**
     * 
     */
	String getCurrentRequestUrl(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	String getCurrentRequestUrlNotSecurity(HttpServletRequest request) throws Exception;

	/**
     * 
     */
	String getRequestUrl(HttpServletRequest request, List<String> excludedPatterns, int position) throws Exception;

	/**
     * 
     */
	void handleFrontofficeUrlParameters(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	EngineEcoSession getCurrentEcoSession(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	void setCurrentEcoSession(HttpServletRequest request, EngineEcoSession ecoSession) throws Exception;
	
	/**
     * 
     */
	User getCurrentUser(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	void updateCurrentUser(HttpServletRequest request, User user) throws Exception;
	
	/**
     * 
     */
	Company getCurrentCompany(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	void handleBackofficeUrlParameters(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	EngineBoSession getCurrentBoSession(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	void setCurrentBoSession(HttpServletRequest request, EngineBoSession ecoSession) throws Exception;
	
	/**
     * 
     */
	Cart getCurrentCart(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	void updateCurrentCart(RequestData requestData, String skuCode, int quantity) throws Exception;
	
	/**
     * 
     */
	void updateCurrentCart(HttpServletRequest request, Long billingAddressId, Long shippingAddressId) throws Exception;
	
	/**
     * 
     */
	void cleanCurrentCart(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	OrderCustomer getLastOrder(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	void saveLastOrder(HttpServletRequest request, OrderCustomer order) throws Exception;
	
	/**
     * 
     */
	void removeCartItemFromCurrentCart(HttpServletRequest request, String skuCode) throws Exception;
	
	/**
     * 
     */
	MarketPlace getCurrentMarketPlace(RequestData requestData) throws Exception;
	
	/**
     * 
     */
	Market getCurrentMarket(RequestData requestData) throws Exception;
	
	/**
     * 
     */
	MarketArea getCurrentMarketArea(RequestData requestData) throws Exception;
	
	/**
     * 
     */
	Localization getCurrentMarketLocalization(RequestData requestData) throws Exception;
	
	/**
     * 
     */
	Localization getCurrentMarketAreaLocalization(RequestData requestData) throws Exception;
	
//	/**
//     * 
//     */
//	Locale getCurrentLocale(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	void updateCurrentLocalization(RequestData requestData, Localization localization) throws Exception;
	
	/**
     * 
     */
	Retailer getCurrentRetailer(RequestData requestData) throws Exception;
	
	/**
     * 
     */
	Customer getCurrentCustomer(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	String getCustomerAvatar(HttpServletRequest request, Customer customer) throws Exception;
	
	/**
     * 
     */
	boolean hasKnownCustomerLogged(final HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	Long getCurrentCustomerId(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	void updateCurrentCustomer(HttpServletRequest request, Customer customer) throws Exception;
	
	/**
     * 
     */	
	void cleanCurrentCustomer(final HttpServletRequest request) throws Exception;
	
	/**
     * 
     */	
	String getCurrentCustomerLogin(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	String getCurrentTheme(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	void updateCurrentTheme(HttpServletRequest request, String theme) throws Exception;
	
	/**
     * 
     */
	String getCurrentThemeResourcePrefixPath(RequestData requestData) throws Exception;

	/**
     * 
     */
	String getCurrentContextNameValue(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	String getRootAssetFilePath(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	String getRootAssetWebPath(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	String getCatalogImageWebPath(HttpServletRequest request, Asset asset) throws Exception;

	/**
     * 
     */
	String getProductMarketingImageWebPath(HttpServletRequest request, Asset asset) throws Exception;

	/**
     * 
     */
	String getProductSkuImageWebPath(HttpServletRequest request, Asset asset) throws Exception;

	/**
     * 
     */
	String getCurrentDevice(RequestData requestData) throws Exception;
	 
	/**
	 * 
	 */
	void updateCurrentDevice(RequestData requestData, String device) throws Exception;
	
	/**
	 * 
	 */
	String getCurrentVelocityWebPrefix(RequestData requestData) throws Exception;
	
	/**
     * 
     */
	String getCurrentVelocityEmailPrefix(RequestData requestData) throws Exception;
	
	/**
     * 
     */
	RequestData getRequestData(HttpServletRequest request) throws Exception;
	
}