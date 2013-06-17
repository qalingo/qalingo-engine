/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
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
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import fr.hoteia.qalingo.core.domain.Asset;
import fr.hoteia.qalingo.core.domain.Company;
import fr.hoteia.qalingo.core.domain.EngineBoSession;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.domain.User;

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
	Localization getCurrentMarketLocalization(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	Retailer getCurrentRetailer(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	Localization getCurrentLocalization(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	Locale getCurrentLocale(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	void updateCurrentLocalization(HttpServletRequest request, Localization localization) throws Exception;
	
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
	String getCurrentTheme(HttpServletRequest request) throws Exception;
	
	/**
     * 
     */
	void updateCurrentTheme(HttpServletRequest request, String theme) throws Exception;
	
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
	String getCurrentThemeResourcePrefixPath(HttpServletRequest request, String context) throws Exception;
	
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