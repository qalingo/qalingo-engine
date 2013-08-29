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

import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.enumtype.FoUrls;
import fr.hoteia.qalingo.core.service.pojo.RequestData;

public interface UrlService {

	String buildRetailerDetailsUrl(RequestData requestData, String retailerName, String retailerCode) throws Exception;
	
	String buildRetailerCreateUrl(RequestData requestData) throws Exception;

	String buildRetailerContactUrl(RequestData requestData, String retailerName, String retailerCode) throws Exception;

	String buildRetailerVoteUrl(RequestData requestData, String retailerName, String retailerCode) throws Exception;
	
	String buildRetailerCommentUrl(RequestData requestData, String retailerName, String retailerCode) throws Exception;
	
	String buildConditionOfUseUrl(RequestData requestData) throws Exception;

	String buildSearchUrl(RequestData requestData) throws Exception;

	String buildChangeLanguageUrl(RequestData requestData) throws Exception;

	String buildProductBrandLineUrlAsProductAxeUrl(RequestData requestData,
	        String brandName, String brandCode) throws Exception;

	String buildProductBrandDetailsUrlAsProductAxeUrl(RequestData requestData,
	        String brandName, String brandCode) throws Exception;

	String buildProductCategoryUrlAsProductAxeUrl(RequestData requestData,
	        String categoryName, String categoryCode) throws Exception;

	String buildProductCategoryUrlAsProductLineUrl(RequestData requestData,
	        String categoryName, String categoryCode) throws Exception;

	String buildProductUrl(RequestData requestData, String categoryName,
	        String categoryCode, String productName, String productCode) throws Exception;

	String buildProductAddToCartUrl(RequestData requestData, String categoryName,
	        String categoryCode, String productName, String productCode, String productSkuName, String productSkuCode) throws Exception;

	String buildProductRemoveFromCartUrl(RequestData requestData, String skuCode) throws Exception;

	String buildProductAddToWishlistUrl(RequestData requestData, String categoryName,
	        String categoryCode, String productName, String productCode, String productSkuName, String productSkuCode) throws Exception;

	String buildProductRemoveFromWishlistUrl(RequestData requestData, String skuCode) throws Exception;

	String buildCartDetailsUrl(RequestData requestData) throws Exception;

	String buildCartDetailsUpdateUrl(RequestData requestData) throws Exception;

	String buildCartAuthUrl(RequestData requestData) throws Exception;

	String buildCartDeliveryAndOrderDetailsUrl(RequestData requestData) throws Exception;

	String buildCartOrderPaymentUrl(RequestData requestData) throws Exception;

	String buildCartOrderConfirmationUrl(RequestData requestData) throws Exception;

	String buildCustomerCreateAccountUrl(RequestData requestData) throws Exception;

	String buildCustomerDetailsUrl(RequestData requestData, String permalink) throws Exception;
	
	// TODO REWRITE
	String buildOAuthConnectUrl(RequestData requestData, String socialNetworkCode) throws Exception;
	
	String buildOAuthCallBackUrl(RequestData requestData, String socialNetworkCode) throws Exception;

	String buildOpenIdConnectUrl(RequestData requestData, String socialNetworkCode) throws Exception;
	
	String buildOpenIdCallBackUrl(RequestData requestData) throws Exception;

	String buildXrdsUrl(RequestData requestData) throws Exception;
	 
	String buildAbsoluteUrl(MarketArea marketArea, String contextNameValue, String relativeUrl) throws Exception;

	String buildDomainePathUrl(MarketArea marketArea, String contextNameValue) throws Exception;
	
	// TODO  KEEP

	String generateUrl(FoUrls url, RequestData requestData, Object... params);
	
//	String buildRootUrl() throws Exception;
	
	String buildAddThisUrl(String shareCode, String absoluteUrl) throws Exception;

	String buildSpringSecurityCheckUrl(RequestData requestData) throws Exception;

}