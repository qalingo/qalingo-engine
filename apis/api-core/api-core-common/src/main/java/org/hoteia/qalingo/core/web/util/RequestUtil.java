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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.Company;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.EngineBoSession;
import org.hoteia.qalingo.core.domain.EngineEcoSession;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.OrderCustomer;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.web.bean.clickstream.ClickstreamSession;

/**
 * 
 */
public interface RequestUtil {

    /**
     * 
     */
    void handleFrontofficeUrlParameters(HttpServletRequest request) throws Exception;

    /**
     * 
     */
    void handleBackofficeUrlParameters(HttpServletRequest request) throws Exception;
    
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
    String getRemoteAddr(HttpServletRequest request);
    
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
    String getLastRequestForEmptyCartUrl(HttpServletRequest request, String fallbackUrl) throws Exception;
    
    /**
     * 
     */
    String getRequestUrl(HttpServletRequest request, List<String> excludedPatterns, int position) throws Exception;

    /**
     * 
     */
    List<String> getCommonExcludedPatterns() throws Exception;
    
    /**
     * 
     */
    EngineEcoSession getCurrentEcoSession(HttpServletRequest request) throws Exception;

    /**
     * 
     */
    EngineEcoSession updateCurrentEcoSession(HttpServletRequest request, EngineEcoSession engineEcoSession) throws Exception;
    
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
    void cleanCurrentUser(HttpServletRequest request) throws Exception;
    
    /**
     * 
     */
    Company getCurrentCompany(HttpServletRequest request) throws Exception;

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
    void resetCurrentCart(HttpServletRequest request) throws Exception;
    
    /**
     * 
     */
    void deleteCurrentCartAndSaveEngineSession(HttpServletRequest request) throws Exception;
    
    /**
     * 
     */
    void updateCurrentCart(HttpServletRequest request, Cart cart) throws Exception;

    /**
     * 
     */
    OrderCustomer getLastOrder(RequestData requestData) throws Exception;

    /**
     * 
     */
    void keepLastOrderInSession(RequestData requestData, OrderCustomer order) throws Exception;

    /**
     * 
     */
    void updateCurrentLocalization(RequestData requestData, Localization localization) throws Exception;

    /**
     * 
     */
    String getCustomerAvatar(HttpServletRequest request, Customer customer) throws Exception;

    /**
     * 
     */
    boolean hasKnownCustomerLogged(HttpServletRequest request) throws Exception;

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
    void cleanCurrentCustomer(HttpServletRequest request) throws Exception;

    /**
     * 
     */
    String getCurrentCustomerLogin(HttpServletRequest request) throws Exception;

    /**
     * 
     */
    String getCurrentTheme(RequestData requestData) throws Exception;

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
    String getRootAssetFilePath() throws Exception;

    /**
     * 
     */
    String getRootAssetWebPath() throws Exception;

    /**
     * 
     */
    String getCatalogImageWebPath(Asset asset) throws Exception;

    /**
     * 
     */
    String getProductMarketingImageWebPath(Asset asset) throws Exception;

    /**
     * 
     */
    String getProductSkuImageWebPath(Asset asset) throws Exception;

    /**
     * 
     */
    String getRetailerOrStoreImageWebPath(Asset asset) throws Exception;
    
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

    /**
     * Find the recent viewed products from cookie 
     * @param request
     * @return
     * @throws Exception
     */
    List<String> getRecentProductSkuCodesFromCookie(HttpServletRequest request) throws Exception;
    
    /**
     * Add/update recent viewed product to cookie
     * @param productId id want to store
     * @param request
     * @param response
     * @throws Exception
     */
    void addOrUpdateRecentProductSkuToCookie(String productSkuCode, HttpServletRequest request, HttpServletResponse response) throws Exception;
    
}