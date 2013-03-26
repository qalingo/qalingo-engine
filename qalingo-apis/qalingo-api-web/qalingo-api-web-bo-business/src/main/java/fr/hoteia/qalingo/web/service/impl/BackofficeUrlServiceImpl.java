/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.web.service.BackofficeUrlService;

@Service("backofficeUrlService")
@Transactional
public class BackofficeUrlServiceImpl implements BackofficeUrlService {

	public String buildHomeUrl() {
		return "/sc/home.html";
	}
	
	public String buildLoginUrl() {
		return "/sc/login.html";
	}
	
	public String buildLogoutUrl() {
		return "/sc/logout-session.html";
	}
	
	public String buildUserDetailsUrl() {
		return "/sc/user-details.html";
	}
	
	public String buildUserEditUrl() {
		return "/sc/user-edit.html";
	}
	
	public String buildManageMasterCatalogUrl() {
		return "/sc/manage-master-catalog.html";
	}
	
	public String buildManageVirtualCatalogUrl() {
		return "/sc/manage-virtual-catalog.html";
	}
	
	public String buildProductMasterCategoryDetailsUrl(String productCategoryCode) {
		return "/sc/catalog-master-category-details.html?" + Constants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE + "=" + handleString(productCategoryCode);
	}
	
	public String buildProductVirtualCategoryDetailsUrl(String productCategoryCode) {
		return "/sc/catalog-virtual-category-details.html?" + Constants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE + "=" + handleString(productCategoryCode);
	}
	
	public String buildAddMasterProductCategoryUrl(String productCategoryCode) {
		if(StringUtils.isNotEmpty(productCategoryCode)){
			return "/sc/add-master-product-category.html?" + Constants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE + "=" + handleString(productCategoryCode);
		} else {
			return "/sc/add-master-product-category.html";
		}
	}

	public String buildAddVirtualProductCategoryUrl(String productCategoryCode) {
		if(StringUtils.isNotEmpty(productCategoryCode)){
			return "/sc/add-virtual-product-category.html?" + Constants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE + "=" + handleString(productCategoryCode);
		} else {
			return "/sc/add-virtual-product-category.html";
		}
	}
	
	public String buildMasterProductCategoryEditUrl(String productCategoryCode) {
		return "/sc/catalog-master-category-edit.html?" + Constants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE + "=" + handleString(productCategoryCode);
	}
	
	public String buildVirtualProductCategoryEditUrl(String productCategoryCode) {
		return "/sc/catalog-virtual-category-edit.html?" + Constants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE + "=" + handleString(productCategoryCode);
	}
	
	public String buildMasterProductCategoryFormPostUrl() {
		return "/sc/catalog-master-category-form.html";
	}
	
	public String buildVirtualProductCategoryFormPostUrl() {
		return "/sc/catalog-virtual-category-form.html";
	}
	
	public String buildProductMarketingDetailsUrl(String productMarketingCode) {
		return "/sc/catalog-product-details.html?" + Constants.REQUEST_PARAM_PRODUCT_MARKETING_CODE + "=" + handleString(productMarketingCode);
	}
	
	public String buildProductMarketingEditUrl(String productMarketingCode) {
		return "/sc/catalog-product-edit.html?" + Constants.REQUEST_PARAM_PRODUCT_MARKETING_CODE + "=" + handleString(productMarketingCode);
	}
	
	public String buildProductMarketingFormPostUrl() {
		return "/sc/catalog-product-form.html";
	}
	
	public String buildProductSkuDetailsUrl(String productSkuCode) {
		return "/sc/catalog-product-sku-details.html?" + Constants.REQUEST_PARAM_PRODUCT_SKU_CODE + "=" + handleString(productSkuCode);
	}
	
	public String buildProductSkuEditUrl(String productSkuCode) {
		return "/sc/catalog-product-sku-edit.html?" + Constants.REQUEST_PARAM_PRODUCT_SKU_CODE + "=" + handleString(productSkuCode);
	}
	
	public String buildProductSkuFormPostUrl() {
		return "/sc/catalog-product-sku-form.html";
	}
	
	public String buildPromotionUrl() {
		return "/sc/promotion.html";
	}
	
	public String buildShippingUrl() {
		return "/sc/shipping.html";
	}
	
	public String buildOrderListUrl() {
		return "/sc/order-list.html";
	}
	
	public String buildCustomerListUrl() {
		return "/sc/customer-list.html";
	}
	
	public String buildFaqUrl() {
		return "/sc/faq.html";
	}
	
	public String buildChangeLanguageUrl(final Localization localization) {
		return "/sc/change-language.html?" + Constants.REQUEST_PARAMETER_LOCALE_CODE + "=" + handleString(localization.getCode());
	}
	
	public String buildChangeContextUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization, final Retailer retailer) {
		String url = "/sc/change-context.html?";
		url = url + Constants.REQUEST_PARAMETER_MARKET_PLACE_CODE + "=" + marketPlace.getCode();
		url = url + "&" + Constants.REQUEST_PARAMETER_MARKET_CODE + "=" + market.getCode();
		url = url + "&" + Constants.REQUEST_PARAMETER_MARKET_AREA_CODE + "=" + marketArea.getCode();
		url = url + "&" + Constants.REQUEST_PARAMETER_MARKET_LANGUAGE + "=" + localization.getCode();
		url = url + "&" + Constants.REQUEST_PARAMETER_RETAILER_CODE + "=" + retailer.getCode();
		return url;
	}
	
	public String buildSpringSecurityCheckUrl() {
		return "/j_spring_security_check";
	}
	
	public String buildForbiddenUrl() {
		return "/sc/forbidden.html";
	}
	
	public String buildForgottenPasswordUrl() {
		return "/sc/forgotten-password.html";
	}
	
	public String buildSearchUrl() {
		return "/sc/search.html";
	}

	protected String handleString(String string) {
		String stringToReturn = string;
		if(StringUtils.isNotEmpty(stringToReturn)) {
			return stringToReturn.toLowerCase().trim();
		}
		return stringToReturn;
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
