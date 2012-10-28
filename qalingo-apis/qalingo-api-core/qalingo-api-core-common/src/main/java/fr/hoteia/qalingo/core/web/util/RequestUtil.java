/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.web.util;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.hoteia.qalingo.core.common.domain.Cart;
import fr.hoteia.qalingo.core.common.domain.Company;
import fr.hoteia.qalingo.core.common.domain.Customer;
import fr.hoteia.qalingo.core.common.domain.EngineSession;
import fr.hoteia.qalingo.core.common.domain.Localization;
import fr.hoteia.qalingo.core.common.domain.Market;
import fr.hoteia.qalingo.core.common.domain.MarketArea;
import fr.hoteia.qalingo.core.common.domain.MarketPlace;
import fr.hoteia.qalingo.core.common.domain.Order;
import fr.hoteia.qalingo.core.common.domain.Retailer;
import fr.hoteia.qalingo.core.common.domain.User;

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
	DateFormat getFormatDate(HttpServletRequest request, int dateStyle, int timeStyle) throws Exception;
	
	/**
	 *
	 */
	SimpleDateFormat getRssFormatDate(HttpServletRequest request) throws Exception;
	
	/**
	 *
	 */
	SimpleDateFormat getAtomFormatDate(HttpServletRequest request) throws Exception;
	
	/**
	 *
	 */
	NumberFormat getCartItemPriceNumberFormat(HttpServletRequest request, String currencyCode) throws Exception;
	
	/**
	 *
	 */
	NumberFormat getNumberFormat(HttpServletRequest request, String currencyCode, RoundingMode roundingMode, int maximumFractionDigits,
			int minimumFractionDigits, int maximumIntegerDigits, int minimumIntegerDigits) throws Exception;
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
	void handleUrlParameters(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	EngineSession getCurrentSession(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	void setCurrentSession(HttpServletRequest request, EngineSession ecoSession) throws Exception;
	
	/**
     * 
     */
	Cart getCurrentCart(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	void updateCurrentCart(HttpServletRequest request, String skuCode, int quantity) throws Exception;
	
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
	Order getLastOrder(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	void saveLastOrder(HttpServletRequest request, Order order) throws Exception;
	
	/**
     * 
     */
	void removeCartItemFromCurrentCart(HttpServletRequest request, String skuCode) throws Exception;
	
	/**
     * 
     */
	MarketPlace getCurrentMarketPlace(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	Market getCurrentMarket(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	MarketArea getCurrentMarketArea(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	Localization getCurrentLocalization(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	void updateCurrentLocalization(HttpServletRequest request, Localization localization) throws Exception;
	
	/**
     * 
     */
	Retailer getCurrentRetailer(HttpServletRequest request) throws Exception;
	
//	/**
//     * 
//     */
//	CatalogVirtual getCurrentProductCatalog(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	User getCurrentUser(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	Long getCurrentUserId(HttpServletRequest request) throws Exception;
	
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
	Customer getCurrentCustomer(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	String getCurrentCustomerId(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	void updateCurrentCustomer(HttpServletRequest request, Customer customer) throws Exception;
	
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
	String getCurrentThemeResourcePrefixPath(HttpServletRequest request, String context) throws Exception;
	
	/**
     * 
     */
	String getCurrentCatalogImageResourcePrefixPath(HttpServletRequest request, String context) throws Exception;
	
	/**
     * 
     */
	String getCurrentDevice(HttpServletRequest request) throws Exception;
	 
	/**
	 * 
	 */
	void updateCurrentDevice(HttpServletRequest request, String device) throws Exception;
	
	/**
	 * 
	 */
	String getCurrentVelocityPrefix(HttpServletRequest request) throws Exception;
}