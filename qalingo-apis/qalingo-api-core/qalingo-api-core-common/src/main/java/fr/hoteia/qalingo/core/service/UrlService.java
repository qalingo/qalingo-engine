/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.service;

import javax.servlet.http.HttpServletRequest;

import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Retailer;

public interface UrlService {

	// COMMON

	String buildRootUrl(HttpServletRequest request) throws Exception;

	String buildHomeUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildHomeUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName) throws Exception;

	String buildOurCompanyUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildOurCompanyUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName) throws Exception;

	String buildClpUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildClpUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName) throws Exception;

	String buildFollowUsUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildFollowUsUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName) throws Exception;

	String buildNewsletterRegisterUrl(HttpServletRequest request, MarketArea marketArea) throws Exception;
	
	String buildNewsletterUnRegisterUrl(HttpServletRequest request, MarketArea marketArea) throws Exception;
	
	String buildContactUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildContactUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName) throws Exception;

	String buildLegalTermsUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildLegalTermsUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName) throws Exception;

	String buildFaqUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildFaqUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName) throws Exception;

	String buildStoreLocationUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildStoreLocationUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName) throws Exception;

	String buildRetailerDetailsUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization,
	        Retailer retailer, String retailerName, String retailerCode) throws Exception;
	
	String buildRetailerDetailsUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization,
	        Retailer retailer, String retailerName, String retailerCode, boolean keepCurrentDomainName) throws Exception;
	
	String buildRetailerCreateUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization,
	        Retailer retailer) throws Exception;
	
	String buildRetailerCreateUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization,
	        Retailer retailer, boolean keepCurrentDomainName) throws Exception;

	String buildRetailerContactUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization,
	        Retailer retailer, String retailerName, String retailerCode) throws Exception;
	
	String buildRetailerContactUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization,
	        Retailer retailer, String retailerName, String retailerCode, boolean keepCurrentDomainName) throws Exception;

	String buildRetailerVoteUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization,
	        Retailer retailer, String retailerName, String retailerCode) throws Exception;
	
	String buildRetailerVoteUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization,
	        Retailer retailer, String retailerName, String retailerCode, boolean keepCurrentDomainName) throws Exception;
	
	String buildRetailerCommentUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization,
	        Retailer retailer, String retailerName, String retailerCode) throws Exception;
	
	String buildRetailerCommentUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization,
	        Retailer retailer, String retailerName, String retailerCode, boolean keepCurrentDomainName) throws Exception;
	
	String buildConditionOfUseUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildConditionOfUseUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName) throws Exception;

	String buildSearchUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildSearchUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName) throws Exception;

	String buildChangeLanguageUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildChangeLanguageUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName) throws Exception;

	String buildAddThisUrl(String shareCode, String absoluteUrl) throws Exception;

	// CATALOG

	String buildProductBrandLineUrlAsProductAxeUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer,
	        String brandName, String brandCode) throws Exception;

	String buildProductBrandLineUrlAsProductAxeUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer,
	        String brandName, String brandCode, boolean keepCurrentDomainName) throws Exception;

	String buildProductBrandDetailsUrlAsProductAxeUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer,
	        String brandName, String brandCode) throws Exception;

	String buildProductBrandDetailsUrlAsProductAxeUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer,
	        String brandName, String brandCode, boolean keepCurrentDomainName) throws Exception;

	String buildProductCategoryUrlAsProductAxeUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer,
	        String categoryName, String categoryCode) throws Exception;

	String buildProductCategoryUrlAsProductAxeUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer,
	        String categoryName, String categoryCode, boolean keepCurrentDomainName) throws Exception;

	String buildProductCategoryUrlAsProductLineUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer,
	        String categoryName, String categoryCode) throws Exception;

	String buildProductCategoryUrlAsProductLineUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer,
	        String categoryName, String categoryCode, boolean keepCurrentDomainName) throws Exception;

	String buildProductUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String categoryName,
	        String categoryCode, String productName, String productCode) throws Exception;

	String buildProductUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String categoryName,
	        String categoryCode, String productName, String productCode, boolean keepCurrentDomainName) throws Exception;

	String buildProductAddToCartUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String categoryName,
	        String categoryCode, String productName, String productCode, String productSkuName, String productSkuCode) throws Exception;

	String buildProductAddToCartUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String categoryName,
	        String categoryCode, String productName, String productCode, String productSkuName, String productSkuCode, boolean keepCurrentDomainName) throws Exception;

	String buildProductRemoveFromCartUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String skuCode)
	        throws Exception;

	String buildProductRemoveFromCartUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String skuCode,
	        boolean keepCurrentDomainName) throws Exception;

	String buildProductAddToWishlistUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String categoryName,
	        String categoryCode, String productName, String productCode, String productSkuName, String productSkuCode) throws Exception;

	String buildProductAddToWishlistUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String categoryName,
	        String categoryCode, String productName, String productCode, String productSkuName, String productSkuCode, boolean keepCurrentDomainName) throws Exception;

	String buildProductRemoveFromWishlistUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String skuCode)
	        throws Exception;

	String buildProductRemoveFromWishlistUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String skuCode,
	        boolean keepCurrentDomainName) throws Exception;

	// CART

	String buildCartDetailsUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildCartDetailsUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName) throws Exception;

	String buildCartDetailsUpdateUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildCartDetailsUpdateUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer,
	        boolean keepCurrentDomainName) throws Exception;

	String buildCartAuthUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildCartAuthUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName) throws Exception;

	String buildCartDeliveryAndOrderDetailsUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildCartDeliveryAndOrderDetailsUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer,
	        boolean keepCurrentDomainName) throws Exception;

	String buildCartOrderPaymentUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildCartOrderPaymentUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer,
	        boolean keepCurrentDomainName) throws Exception;

	String buildCartOrderConfirmationUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildCartOrderConfirmationUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer,
	        boolean keepCurrentDomainName) throws Exception;

	// CUSTOMER

	String buildCustomerCreateAccountUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildCustomerCreateAccountUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer,
	        boolean keepCurrentDomainName) throws Exception;

	String buildCustomerDetailsUrl(HttpServletRequest request, MarketArea marketArea, String permalink) throws Exception;
	
	String buildCustomerDetailsUrl(HttpServletRequest request, MarketArea marketArea) throws Exception;

	String buildCustomerEditUrl(HttpServletRequest request, MarketArea marketArea) throws Exception;

	String buildCustomerOrderListUrl(HttpServletRequest request, MarketArea marketArea) throws Exception;

	String buildCustomerOrderDetailsUrl(HttpServletRequest request, MarketArea marketArea, String orderId) throws Exception;

	String buildCustomerWishlistUrl(HttpServletRequest request, MarketArea marketArea) throws Exception;

	String buildCustomerProductCommentUrl(HttpServletRequest reques, MarketArea marketAreat) throws Exception;

	String buildCustomerAddressListUrl(HttpServletRequest request, MarketArea marketArea) throws Exception;

	String buildCustomerAddAddressUrl(HttpServletRequest request, MarketArea marketArea) throws Exception;

	String buildCustomerEditAddressUrl(HttpServletRequest request, MarketArea marketArea, String customerAddressId) throws Exception;

	String buildCustomerDeleteAddressUrl(HttpServletRequest request, MarketArea marketArea, String customerAddressId) throws Exception;

	// SECURITY

	String buildLoginUrl(HttpServletRequest request, MarketArea marketArea) throws Exception;

	String buildLoginSuccesUrl(HttpServletRequest request, MarketArea marketArea) throws Exception;
	
	String buildLogoutUrl(HttpServletRequest request, MarketArea marketArea) throws Exception;

	String buildForbiddenUrl(HttpServletRequest request, MarketArea marketArea) throws Exception;

	String buildTimeoutUrl(HttpServletRequest request, MarketArea marketArea) throws Exception;

	String buildForgottenPasswordUrl(HttpServletRequest request, MarketArea marketArea) throws Exception;

	String buildResetPasswordUrl(HttpServletRequest request, MarketArea marketArea, String email, String token) throws Exception;

	String buildSpringSecurityCheckUrl(HttpServletRequest request, MarketArea marketArea) throws Exception;

	String buildOAuthConnectUrl(HttpServletRequest request, MarketArea marketArea, String socialNetworkCode) throws Exception;
	
	String buildOAuthCallBackUrl(HttpServletRequest request, MarketArea marketArea, String socialNetworkCode) throws Exception;

	String buildOpenIdConnectUrl(final HttpServletRequest request, final MarketArea marketArea, final String socialNetworkCode) throws Exception;
	
	String buildOpenIdCallBackUrl(HttpServletRequest request, MarketArea marketArea) throws Exception;

	String buildXrdsUrl(HttpServletRequest request, MarketArea marketArea) throws Exception;
	 
	String buildAbsoluteUrl(HttpServletRequest request, MarketArea marketArea, String contextNameValue, String relativeUrl) throws Exception;

	String buildDomainePathUrl(HttpServletRequest request, MarketArea marketArea, String contextNameValue) throws Exception;

}