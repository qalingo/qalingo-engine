/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.web.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.BoPageConstants;
import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.RequestConstants;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.web.service.BackofficeUrlService;

@Service("backofficeUrlService")
@Transactional
public class BackofficeUrlServiceImpl implements BackofficeUrlService {

	public String buildHomeUrl() {
		return buildPrefix() + BoPageConstants.HOME_URL;
	}
	
	// COMMON
	
	public String buildLoginUrl() {
		return buildPrefix() + BoPageConstants.LOGIN_URL;
	}
	
	public String buildLogoutUrl() {
		return buildPrefix() + BoPageConstants.LOGOUT_SESSION_URL;
	}
	
	public String buildFaqUrl() {
		return buildPrefix() + BoPageConstants.FAQ_URL;
	}
	
	public String buildChangeLanguageUrl(final Localization localization) {
		return buildPrefix() + BoPageConstants.CHANGE_LANGUAGE_URL + "?" + RequestConstants.REQUEST_PARAMETER_LOCALE_CODE + "=" + handleString(localization.getCode());
	}
	
	public String buildChangeContextUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization, final Retailer retailer) {
		String url = buildPrefix() + "/change-context.html?";
		url = url + RequestConstants.REQUEST_PARAMETER_MARKET_PLACE_CODE + "=" + marketPlace.getCode();
		url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_CODE + "=" + market.getCode();
		url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_AREA_CODE + "=" + marketArea.getCode();
		url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_LANGUAGE + "=" + localization.getCode();
		url = url + "&" + RequestConstants.REQUEST_PARAMETER_RETAILER_CODE + "=" + retailer.getCode();
		return url;
	}
	
	public String buildSpringSecurityCheckUrl() {
		return BoPageConstants.SPRING_SECURITY;
	}
	
	public String buildForbiddenUrl() {
		return buildPrefix() + BoPageConstants.FORBIDDEN_URL;
	}
	
	public String buildForgottenPasswordUrl() {
		return buildPrefix() + BoPageConstants.FORGOTTEN_PASSWORD_URL;
	}
	
	public String buildGlobalSearchUrl() {
		return buildPrefix() + BoPageConstants.GLOBAL_SEARCH_URL;
	}
	
	public String buildUserDetailsUrl() {
		return buildUserDetailsUrl(null);
	}
	
	public String buildUserDetailsUrl(String userId) {
		if(StringUtils.isNotEmpty(userId)){
			return buildPrefix() + "/user-details.html?" + RequestConstants.REQUEST_PARAM_USER_ID + "=" + handleString(userId);
		} else {
			return buildPrefix() + "/user-details.html";
		}
	}
	
	public String buildUserEditUrl() {
		return buildUserEditUrl(null);
	}
	
	public String buildUserEditUrl(String userId) {
		if(StringUtils.isNotEmpty(userId)){
			return buildPrefix() + "/user-edit.html?" + RequestConstants.REQUEST_PARAM_USER_ID + "=" + handleString(userId);
		} else {
			return buildPrefix() + "/user-edit.html";
		}
	}
	
	public String buildUserFormPostUrl() {
		return buildPrefix() + "/user-form.html";
	}
	
	public String buildUserListUrl() {
		return buildPrefix() + "/users.html";
	}
	
	// REPORTING
	
	public String buildCatalogStatsUrl() {
		return "/sc/catalog-stats.html";
	}
	
	public String buildPromotionStatsUrl() {
		return "/sc/promotion-stats.html";
	}
	
	public String buildShippingStatsUrl() {
		return "/sc/shipping-stats.html";
	}
	
	public String buildOrderStatsUrl() {
		return "/sc/order-stats.html";
	}
	
	public String buildCustomerStatsUrl() {
		return "/sc/customer-stats.html";
	}
	
	public String buildReportingUrl() {
		return "/sc/reporting.html";
	}
	
	// BUSINESS
	
	public String buildManageMasterCatalogUrl() {
		return buildPrefix() + "/manage-master-catalog.html";
	}
	
	public String buildManageVirtualCatalogUrl() {
		return buildPrefix() + "/manage-virtual-catalog.html";
	}
	
	public String buildProductMasterCategoryDetailsUrl(String productCategoryCode) {
		return buildPrefix() + "/catalog-master-category-details.html?" + RequestConstants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE + "=" + handleString(productCategoryCode);
	}
	
	public String buildProductVirtualCategoryDetailsUrl(String productCategoryCode) {
		return buildPrefix() + "/catalog-virtual-category-details.html?" + RequestConstants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE + "=" + handleString(productCategoryCode);
	}
	
	public String buildAddMasterProductCategoryUrl(String productCategoryCode) {
		if(StringUtils.isNotEmpty(productCategoryCode)){
			return buildPrefix() + "/add-master-catalog-category.html?" + RequestConstants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE + "=" + handleString(productCategoryCode);
		} else {
			return buildPrefix() + "/add-master-catalog-category.html";
		}
	}

	public String buildAddVirtualProductCategoryUrl(String productCategoryCode) {
		if(StringUtils.isNotEmpty(productCategoryCode)){
			return buildPrefix() + "/add-virtual-catalog-category.html?" + RequestConstants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE + "=" + handleString(productCategoryCode);
		} else {
			return buildPrefix() + "/add-virtual-catalog-category.html";
		}
	}
	
	public String buildMasterProductCategoryEditUrl(String productCategoryCode) {
		return buildPrefix() + "/catalog-master-category-edit.html?" + RequestConstants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE + "=" + handleString(productCategoryCode);
	}
	
	public String buildVirtualProductCategoryEditUrl(String productCategoryCode) {
		return buildPrefix() + "/catalog-virtual-category-edit.html?" + RequestConstants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE + "=" + handleString(productCategoryCode);
	}
	
	public String buildMasterProductCategoryFormPostUrl() {
		return buildPrefix() + "/catalog-master-category-form.html";
	}
	
	public String buildVirtualProductCategoryFormPostUrl() {
		return buildPrefix() + "/catalog-virtual-category-form.html";
	}
	
	public String buildProductMarketingDetailsUrl(String productMarketingCode) {
		return buildPrefix() + "/product-marketing-details.html?" + RequestConstants.REQUEST_PARAM_PRODUCT_MARKETING_CODE + "=" + handleString(productMarketingCode);
	}
	
	public String buildProductMarketingEditUrl(String productMarketingCode) {
		return buildPrefix() + "/product-marketing-edit.html?" + RequestConstants.REQUEST_PARAM_PRODUCT_MARKETING_CODE + "=" + handleString(productMarketingCode);
	}
	
	public String buildProductMarketingFormPostUrl() {
		return buildPrefix() + "/product-marketing-form.html";
	}
	
	public String buildProductSkuDetailsUrl(String productSkuCode) {
		return buildPrefix() + "/product-sku-details.html?" + RequestConstants.REQUEST_PARAM_PRODUCT_SKU_CODE + "=" + handleString(productSkuCode);
	}
	
	public String buildProductSkuEditUrl(String productSkuCode) {
		return buildPrefix() + "/product-sku-edit.html?" + RequestConstants.REQUEST_PARAM_PRODUCT_SKU_CODE + "=" + handleString(productSkuCode);
	}
	
	public String buildProductSkuFormPostUrl() {
		return buildPrefix() + "/product-sku-form.html";
	}
	
	public String buildAssetDetailsUrl(String assetCode) {
		return buildPrefix() + "/asset-details.html?" + RequestConstants.REQUEST_PARAM_ASSET_CODE + "=" + handleString(assetCode);
	}
	
	public String buildAssetEditUrl(String assetCode) {
		return buildPrefix() + "/asset-edit.html?" + RequestConstants.REQUEST_PARAM_ASSET_CODE + "=" + handleString(assetCode);
	}
	
	public String buildAssetFormPostUrl() {
		return buildPrefix() + "/asset-form.html";
	}
	
	public String buildRuleListUrl() {
		return buildPrefix() + "/rule-list.html";
	}
	
	public String buildRuleDetailsUrl(String promotionCode) {
		return buildPrefix() + "/rule-details.html?" + RequestConstants.REQUEST_PARAM_RULE_CODE + "=" + handleString(promotionCode);
	}
	
	public String buildRuleEditUrl(String promotionCode) {
		return buildPrefix() + "/rule-edit.html?" + RequestConstants.REQUEST_PARAM_RULE_CODE + "=" + handleString(promotionCode);
	}
	
	public String buildShippingListUrl() {
		return buildPrefix() + "/shipping-list.html";
	}
	
	public String buildShippingDetailsUrl(String shippingCode) {
		return buildPrefix() + "/shipping-details.html?" + RequestConstants.REQUEST_PARAM_SHIPPING_CODE + "=" + handleString(shippingCode);
	}
	
	public String buildShippingEditUrl(String shippingCode) {
		return buildPrefix() + "/shipping-edit.html?" + RequestConstants.REQUEST_PARAM_SHIPPING_CODE + "=" + handleString(shippingCode);
	}
	
	public String buildOrderListUrl() {
		return buildPrefix() + "/order-list.html";
	}
	
	public String buildOrderDetailsUrl(String orderNum) {
		return buildPrefix() + "/order-details.html?" + RequestConstants.REQUEST_PARAM_ORDER_CODE + "=" + handleString(orderNum);
	}
	
	public String buildOrderEditUrl(String orderNum) {
		return buildPrefix() + "/order-edit.html?" + RequestConstants.REQUEST_PARAM_ORDER_CODE + "=" + handleString(orderNum);
	}
	
	public String buildCustomerListUrl() {
		return buildPrefix() + "/customer-list.html";
	}
	
	public String buildCustomerDetailsUrl(String customerCode) {
		return buildPrefix() + "/customer-details.html?" + RequestConstants.REQUEST_PARAM_CUSTOMER_CODE + "=" + handleString(customerCode);
	}
	
	public String buildCustomerEditUrl(String customerCode) {
		return buildPrefix() + "/customer-edit.html?" + RequestConstants.REQUEST_PARAM_CUSTOMER_CODE + "=" + handleString(customerCode);
	}

	// TECHNICAL
	
	public String buildReferenceDataListUrl() {
		return "/sc/reference-datas.html";
	}
	
	public String buildEngineSettingListUrl() {
		return "/sc/engine-settings.html";
	}
	
	public String buildCacheUrl() {
		return "/sc/cache.html";
	}
	
	public String buildBatchUrl() {
		return "/sc/batch.html";
	}
	
	public String buildBatchCustomerUrl() {
		return "/sc/batch-customer.html";
	}
	
	public String buildBatchOrderUrl() {
		return "/sc/batch-order.html";
	}
	
	public String buildBatchEmailUrl() {
		return "/sc/batch-email.html";
	}
	
	public String buildBatchCmsUrl() {
		return "/sc/batch-cms.html";
	}
	
	public String buildBatchStockUrl() {
		return "/sc/batch-stock.html";
	}

	public String buildMonitoringUrl() {
		return "/sc/monitoring.html";
	}
	
	public String buildSearchConfigUrl() {
		return "/sc/search-config.html";
	}
	
	public String buildSearchUrl() {
		return "/sc/search.html";
	}
	
	public String buildSearchEngineSettingUrl() {
		return "/sc/search-engine-setting.html";
	}
	
	public String buildSearchUserUrl() {
		return "/sc/search-user.html";
	}
	
	public String buildSearchBatchUrl() {
		return "/sc/search-batch.html";
	}
	
	public String buildEngineSettingDetailsUrl(final String engineSettingId) {
		return "/sc/engine-setting-details.html?" + Constants.REQUEST_PARAM_ENGINE_SETTING_ID + "=" + engineSettingId;
	}
	
	public String buildEngineSettingValueEditUrl() {
		return "/sc/engine-setting-value-edit.html";
	}

	public String buildEngineSettingValueEditUrl(final String engineSettingValueId) {
		return "/sc/engine-setting-value-edit.html?" + Constants.REQUEST_PARAM_ENGINE_SETTING_VALUE_ID + "=" + engineSettingValueId;
	}
	
	// --
	
	protected String handleString(String string) {
		String stringToReturn = string;
		if(StringUtils.isNotEmpty(stringToReturn)) {
			return stringToReturn.toLowerCase().trim();
		}
		return stringToReturn;
	}
	
	private String buildPrefix(){
		return Constants.SPRING_URL_PATH;
	}
//	protected String getContextPrefixUrl(final ) {
//		String contextPrefixUrl = "";
//		if(request.getRequestURL().toString().contains("localhost")
//				|| request.getRequestURL().toString().contains("127.0.0.1")) {
//			contextPrefixUrl = contextPrefixUrl + request.getContextPath() + "/";
//		} else {
//			contextPrefixUrl = "/";
//		}
//		return contextPrefixUrl;
//	}
}
