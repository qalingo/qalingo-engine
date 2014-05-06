/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core;

public final class RequestConstants {

	public static final String NAMESPACE_URI = "http://ws.qalingo.com/schemas/ws/eco";

	public static final String REFERER	= "Referer";
	public static final String HOST		= "Host";
	public static final String HTTP		= "Http";
	public static final String HTTP_COLON_SLASH_SLASH = "http://";

	// SESSION
	public static final String ENGINE_ECO_SESSION_OBJECT	= "ENGINE_ECO_SESSION_OBJECT";
	public static final String ENGINE_BO_SESSION_OBJECT		= "ENGINE_BO_SESSION_OBJECT";

    // REQUEST PARAMETER
    public static final String REQUEST_PARAMETER_MARKET_PLACE_CODE          = "market-place-code";
    public static final String REQUEST_PARAMETER_MARKET_CODE                = "market-code";
    public static final String REQUEST_PARAMETER_MARKET_AREA_CODE           = "market-area-code";
    public static final String REQUEST_PARAMETER_MARKET_AREA_LANGUAGE       = "market-area-language-code";
    public static final String REQUEST_PARAMETER_MARKET_AREA_RETAILER_CODE  = "market-area-retailer-code";
    public static final String REQUEST_PARAMETER_MARKET_AREA_CURRENCY_CODE  = "market-area-currency-code";
	
    public static final String REQUEST_PARAMETER_CATALOG_CODE                   = "catalog-code";
    public static final String REQUEST_PARAMETER_CATALOG_TYPE                   = "catalog-type";
    public static final String REQUEST_PARAMETER_PARENT_CATALOG_CATEGORY_CODE   = "parent-category-code";
    public static final String REQUEST_PARAMETER_CATALOG_CATEGORY_CODE          = "category-code";
    public static final String REQUEST_PARAMETER_PRODUCT_MARKETING_CODE         = "product-code";
    public static final String REQUEST_PARAMETER_PRODUCT_SKU_CODE               = "product-sku-code";
    public static final String REQUEST_PARAMETER_ASSET_CODE                     = "asset-code";
    public static final String REQUEST_PARAMETER_ASSET_ID                       = "asset-id";
    public static final String REQUEST_PARAMETER_BRAND_CODE                     = "brand-code";
    public static final String REQUEST_PARAMETER_LOCALE_CODE                    = "locale-code";
    public static final String REQUEST_PARAMETER_CUSTOMER_CODE                  = "customer-code";
    public static final String REQUEST_PARAMETER_CUSTOMER_PERMALINK             = "customer-permalink";
    public static final String REQUEST_PARAMETER_ORDER_NUM                      = "order-num";
    public static final String REQUEST_PARAMETER_RULE_CODE                      = "rule-code";
    public static final String REQUEST_PARAMETER_WAREHOUSE_CODE                 = "warehouse-code";
    public static final String REQUEST_PARAMETER_DELIVERY_METHOD_CODE           = "delivery-method-code";
    public static final String REQUEST_PARAMETER_TAX_CODE                       = "tax-code";
    public static final String REQUEST_PARAMETER_ENGINE_SETTING_CODE            = "engine-setting-code";
    public static final String REQUEST_PARAMETER_PAYMENT_GATEWAY_CODE           = "payment-gateway-code";
    public static final String REQUEST_PARAMETER_RETAILER_CODE                  = "retailer-code";
    public static final String REQUEST_PARAMETER_STORE_CODE                     = "store-code";

    public static final String REQUEST_PARAMETER_SKU_CODE_LIST                  = "sku-codes";

    public static final String REQUEST_PARAMETER_ENGINE_SETTING_VALUE_CONTEXT   = "engine-setting-value-context";

	public static final String REQUEST_PARAMETER_GLOBAL_SEARCH_TXT			= "global-search-txt";
	public static final String REQUEST_PARAMETER_SEARCH_TXT					= "search-txt";

    public static final String REQUEST_PARAMETER_CART_ITEM_SKU_CODE         = "cart-item-sku-code";
    public static final String REQUEST_PARAMETER_CART_ITEM_SKU_QUANTITY     = "cart-item-sku-quantity";
    public static final String REQUEST_PARAMETER_CART_SHIPPING_ADDRESS_GUID = "cart-shipping-address-guid";
    public static final String REQUEST_PARAMETER_CART_BILLING_ADDRESS_GUID  = "cart-billing-address-guid";
    public static final String REQUEST_PARAMETER_CART_DELIVERY_METHOD_CODE  = "cart-delivery-method-code";

    public static final String REQUEST_PARAMETER_MULTIPLE_ADD_TO_CART_SKU_CODES     = "sku-codes";
    public static final String REQUEST_PARAMETER_MULTIPLE_ADD_TO_CART_QUANTITIES    = "qties";
    public static final String REQUEST_PARAMETER_MULTIPLE_ADD_TO_CART_QUANTITY    = "quantity";

    public static final String REQUEST_PARAMETER_CUSTOMER_ADDRESS_GUID      = "customer-address-guid";
    public static final String REQUEST_PARAMETER_CUSTOMER_ORDER_GUID        = "customer-order-guid";
    
    public static final String REQUEST_PARAMETER_USER_ID                    = "user-id";
    public static final String REQUEST_PARAMETER_USER_CODE                  = "user-code";

	public static final String REQUEST_PARAMETER_PAGE_ID					= "page-id";

	public static final String REQUEST_PARAMETER_NEW_CUSTOMER_VALIDATION_EMAIL	= "customer-email";
	public static final String REQUEST_PARAMETER_NEW_CUSTOMER_VALIDATION_TOKEN	= "validation-token";

	public static final String REQUEST_PARAMETER_PASSWORD_RESET_TOKEN			= "reset-token";
	public static final String REQUEST_PARAMETER_PASSWORD_RESET_EMAIL			= "reset-email";
	public static final String REQUEST_PARAMETER_AUTH_ERROR						= "auth-error";

	public static final String REQUEST_PARAMETER_NEWSLETTER_EMAIL			= "newsletter-email";

    public static final String REQUEST_PARAMETER_FLAG                       = "flag";
    public static final String REQUEST_PARAMETER_SERVERNAME                 = "serverName";
    public static final String REQUEST_PARAMETER_CACHE_NAME                 = "cacheName";

	public static final String REQUEST_PARAMETER_TAB						= "tab"; 
	
	// SPRING URL REWRITE
	public static final String URL_PATTERN_CART_ITEM_CODE					= "cartItemCode";
	public static final String URL_PATTERN_RETAILER_CODE					= "retailerCode";
    public static final String URL_PATTERN_STORE_CODE                       = "storeCode";
	public static final String URL_PATTERN_BRAND_CODE						= "brandCode";
	public static final String URL_PATTERN_CATEGORY_CODE					= "categoryCode";
	public static final String URL_PATTERN_PRODUCT_MARKETING_CODE			= "productMarketingCode";
	public static final String URL_PATTERN_PRODUCT_SKU_CODE					= "productSkuCode";

	// SEARCH PAGINATION
	public static final String SEARCH_FACET_FIELD_LIST = "facetFieldList";
	
}