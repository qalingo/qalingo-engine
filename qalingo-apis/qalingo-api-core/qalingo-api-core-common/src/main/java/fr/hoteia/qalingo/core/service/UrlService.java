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

import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.domain.enumtype.FoUrls;
import fr.hoteia.qalingo.core.service.pojo.RequestData;

public interface UrlService {

	String buildRetailerDetailsUrl(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization,
	        Retailer retailer, String retailerName, String retailerCode) throws Exception;
	
	String buildRetailerCreateUrl(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization,
	        Retailer retailer) throws Exception;

	String buildRetailerContactUrl(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization,
	        Retailer retailer, String retailerName, String retailerCode) throws Exception;

	String buildRetailerVoteUrl(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization,
	        Retailer retailer, String retailerName, String retailerCode) throws Exception;
	
	String buildRetailerCommentUrl(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization,
	        Retailer retailer, String retailerName, String retailerCode) throws Exception;
	
	String buildConditionOfUseUrl(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildSearchUrl(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildChangeLanguageUrl(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildProductBrandLineUrlAsProductAxeUrl(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer,
	        String brandName, String brandCode) throws Exception;

	String buildProductBrandDetailsUrlAsProductAxeUrl(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer,
	        String brandName, String brandCode) throws Exception;

	String buildProductCategoryUrlAsProductAxeUrl(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer,
	        String categoryName, String categoryCode) throws Exception;

	String buildProductCategoryUrlAsProductLineUrl(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer,
	        String categoryName, String categoryCode) throws Exception;

	String buildProductUrl(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String categoryName,
	        String categoryCode, String productName, String productCode) throws Exception;

	String buildProductAddToCartUrl(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String categoryName,
	        String categoryCode, String productName, String productCode, String productSkuName, String productSkuCode) throws Exception;

	String buildProductRemoveFromCartUrl(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String skuCode) throws Exception;

	String buildProductAddToWishlistUrl(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String categoryName,
	        String categoryCode, String productName, String productCode, String productSkuName, String productSkuCode) throws Exception;

	String buildProductRemoveFromWishlistUrl(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String skuCode) throws Exception;

	String buildCartDetailsUrl(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildCartDetailsUpdateUrl(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildCartAuthUrl(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildCartDeliveryAndOrderDetailsUrl(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildCartOrderPaymentUrl(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildCartOrderConfirmationUrl(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildCustomerCreateAccountUrl(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer) throws Exception;

	String buildCustomerDetailsUrl(MarketArea marketArea, String permalink) throws Exception;
	
	String buildCustomerDetailsUrl(MarketArea marketArea) throws Exception;

	String buildCustomerEditUrl(MarketArea marketArea) throws Exception;

	String buildCustomerOrderListUrl(MarketArea marketArea) throws Exception;

	String buildCustomerOrderDetailsUrl(MarketArea marketArea, String orderId) throws Exception;

	String buildCustomerWishlistUrl(MarketArea marketArea) throws Exception;

	String buildCustomerProductCommentUrl(MarketArea marketAreat) throws Exception;

	String buildCustomerAddressListUrl(MarketArea marketArea) throws Exception;

	String buildCustomerAddAddressUrl(MarketArea marketArea) throws Exception;

	String buildCustomerEditAddressUrl(MarketArea marketArea, String customerAddressId) throws Exception;

	String buildCustomerDeleteAddressUrl(MarketArea marketArea, String customerAddressId) throws Exception;

	// TODO REWRITE
	String buildOAuthConnectUrl(MarketArea marketArea, String socialNetworkCode) throws Exception;
	
	String buildOAuthCallBackUrl(MarketArea marketArea, String socialNetworkCode) throws Exception;

	String buildOpenIdConnectUrl(MarketArea marketArea, String socialNetworkCode) throws Exception;
	
	String buildOpenIdCallBackUrl(MarketArea marketArea) throws Exception;

	String buildXrdsUrl(MarketArea marketArea) throws Exception;
	 
	String buildAbsoluteUrl(MarketArea marketArea, String contextNameValue, String relativeUrl) throws Exception;

	String buildDomainePathUrl(MarketArea marketArea, String contextNameValue) throws Exception;
	
	// TODO  KEEP
	
	String generateUrl(FoUrls url, RequestData requestData);

	String generateUrl(FoUrls url, RequestData requestData, Object... params);
	
	String buildRootUrl() throws Exception;
	
	String buildAddThisUrl(String shareCode, String absoluteUrl) throws Exception;

	String buildSpringSecurityCheckUrl(RequestData requestData) throws Exception;

}