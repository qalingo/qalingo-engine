package org.hoteia.qalingo.core.domain.enumtype;

import org.apache.commons.lang3.StringUtils;

public enum BoUrls {

    HOME(BoUrls.HOME_URL, BoUrls.HOME_KEY, BoUrls.HOME_VELOCITY_PAGE, true),
    INDEX(BoUrls.INDEX_URL, BoUrls.HOME_KEY, BoUrls.HOME_VELOCITY_PAGE, true),
    FAQ(BoUrls.FAQ_URL, BoUrls.FAQ_KEY, BoUrls.FAQ_VELOCITY_PAGE, false),
    LEGAL_TERMS(BoUrls.LEGAL_TERMS_URL, BoUrls.LEGAL_TERMS_KEY, BoUrls.LEGAL_TERMS_VELOCITY_PAGE, false),
    
    PERSONAL_DETAILS(BoUrls.PERSONAL_DETAILS_URL, BoUrls.PERSONAL_DETAILS_KEY, BoUrls.PERSONAL_DETAILS_VELOCITY_PAGE, false),
    PERSONAL_EDIT(BoUrls.PERSONAL_EDIT_URL, BoUrls.PERSONAL_EDIT_KEY, BoUrls.PERSONAL_EDIT_VELOCITY_PAGE, false),

    SEARCH(BoUrls.SEARCH_URL, BoUrls.SEARCH_KEY, BoUrls.SEARCH_VELOCITY_PAGE, true),
    SEARCH_CONFIG(BoUrls.SEARCH_CONFIG_URL, BoUrls.SEARCH_CONFIG_KEY, BoUrls.SEARCH_CONFIG_VELOCITY_PAGE, true),
    GLOBAL_SEARCH(BoUrls.GLOBAL_SEARCH_URL, null, null, true),
    
    CHANGE_LANGUAGE(BoUrls.CHANGE_LANGUAGE_URL, null, null, true),
    CHANGE_CONTEXT(BoUrls.CHANGE_CONTEXT_URL, null, null, true),

    LOGIN(BoUrls.LOGIN_URL, BoUrls.LOGIN_KEY, BoUrls.LOGIN_VELOCITY_PAGE, false),
    LOGIN_CHECK(BoUrls.LOGIN_CHECK_URL, BoUrls.LOGIN_KEY, BoUrls.LOGIN_VELOCITY_PAGE, false),
    LOGOUT(BoUrls.LOGOUT_URL, BoUrls.LOGOUT_KEY, BoUrls.LOGOUT_VELOCITY_PAGE, false),
    FORBIDDEN(BoUrls.FORBIDDEN_URL, BoUrls.FORBIDDEN_KEY, BoUrls.FORBIDDEN_VELOCITY_PAGE, false),
    TIMEOUT(BoUrls.TIMEOUT_URL, BoUrls.TIMEOUT_KEY, BoUrls.TIMEOUT_VELOCITY_PAGE, false),
    FORGOTTEN_PASSWORD(BoUrls.FORGOTTEN_PASSWORD_URL, BoUrls.FORGOTTEN_PASSWORD_KEY, BoUrls.FORGOTTEN_PASSWORD_VELOCITY_PAGE, false),
    RESET_PASSWORD(BoUrls.RESET_PASSWORD_URL, BoUrls.RESET_PASSWORD_KEY, BoUrls.RESET_PASSWORD_VELOCITY_PAGE, false),
    
    MONITORING(BoUrls.MONITORING_URL, BoUrls.MONITORING_KEY, BoUrls.MONITORING_VELOCITY_PAGE, true),
    REPORTING(BoUrls.REPORTING_URL, BoUrls.REPORTING_KEY, BoUrls.REPORTING_VELOCITY_PAGE, true),

    REFERENCE_DATAS(BoUrls.REFERENCE_DATAS_URL, BoUrls.REFERENCE_DATAS_KEY, BoUrls.REFERENCE_DATAS_VELOCITY_PAGE, true),
    
    PAYMENT_GATEWAY_LIST(BoUrls.PAYMENT_GATEWAY_LIST_URL, BoUrls.PAYMENT_GATEWAY_LIST_KEY, BoUrls.PAYMENT_GATEWAY_LIST_VELOCITY_PAGE, true),
    PAYMENT_GATEWAY_DETAILS(BoUrls.PAYMENT_GATEWAY_DETAILS_URL, BoUrls.PAYMENT_GATEWAY_DETAILS_KEY, BoUrls.PAYMENT_GATEWAY_DETAILS_VELOCITY_PAGE, true),
    PAYMENT_GATEWAY_EDIT(BoUrls.PAYMENT_GATEWAY_EDIT_URL, BoUrls.PAYMENT_GATEWAY_EDIT_KEY, BoUrls.PAYMENT_GATEWAY_EDIT_VELOCITY_PAGE, true),
    
    WAREHOUSE_LIST(BoUrls.WAREHOUSE_LIST_URL, BoUrls.WAREHOUSE_LIST_KEY, BoUrls.WAREHOUSE_LIST_VELOCITY_PAGE, true),
    WAREHOUSE_DETAILS(BoUrls.WAREHOUSE_DETAILS_URL, BoUrls.WAREHOUSE_DETAILS_KEY, BoUrls.WAREHOUSE_DETAILS_VELOCITY_PAGE, true),
    WAREHOUSE_EDIT(BoUrls.WAREHOUSE_EDIT_URL, BoUrls.WAREHOUSE_EDIT_KEY, BoUrls.WAREHOUSE_EDIT_VELOCITY_PAGE, true),
    WAREHOUSE_ADD(BoUrls.WAREHOUSE_EDIT_URL, BoUrls.WAREHOUSE_ADD_KEY, BoUrls.WAREHOUSE_EDIT_VELOCITY_PAGE, true),

    TAX_LIST(BoUrls.TAX_LIST_URL, BoUrls.TAX_LIST_KEY, BoUrls.TAX_LIST_VELOCITY_PAGE, true),
    TAX_DETAILS(BoUrls.TAX_DETAILS_URL, BoUrls.TAX_DETAILS_KEY, BoUrls.TAX_DETAILS_VELOCITY_PAGE, true),
    TAX_EDIT(BoUrls.TAX_EDIT_URL, BoUrls.TAX_EDIT_KEY, BoUrls.TAX_EDIT_VELOCITY_PAGE, true),
    TAX_ADD(BoUrls.TAX_EDIT_URL, BoUrls.TAX_ADD_KEY, BoUrls.TAX_EDIT_VELOCITY_PAGE, true),

    DELIVERY_METHOD_LIST(BoUrls.DELIVERY_METHOD_LIST_URL, BoUrls.DELIVERY_METHOD_LIST_KEY, BoUrls.DELIVERY_METHOD_LIST_VELOCITY_PAGE, true),
    DELIVERY_METHOD_DETAILS(BoUrls.DELIVERY_METHOD_DETAILS_URL, BoUrls.DELIVERY_METHOD_DETAILS_KEY, BoUrls.DELIVERY_METHOD_DETAILS_VELOCITY_PAGE, true),
    DELIVERY_METHOD_EDIT(BoUrls.DELIVERY_METHOD_EDIT_URL, BoUrls.DELIVERY_METHOD_EDIT_KEY, BoUrls.DELIVERY_METHOD_EDIT_VELOCITY_PAGE, true),
    DELIVERY_METHOD_ADD(BoUrls.DELIVERY_METHOD_EDIT_URL, BoUrls.DELIVERY_METHOD_ADD_KEY, BoUrls.DELIVERY_METHOD_EDIT_VELOCITY_PAGE, true),

    RULE_LIST(BoUrls.RULE_LIST_URL, BoUrls.RULE_LIST_KEY, BoUrls.RULE_LIST_VELOCITY_PAGE, true),
    RULE_DETAILS(BoUrls.RULE_DETAILS_URL, BoUrls.RULE_DETAILS_KEY, BoUrls.RULE_DETAILS_VELOCITY_PAGE, true),
    RULE_EDIT(BoUrls.RULE_EDIT_URL, BoUrls.RULE_EDIT_KEY, BoUrls.RULE_EDIT_VELOCITY_PAGE, true),

    ORDER_LIST(BoUrls.ORDER_LIST_URL, BoUrls.ORDER_LIST_KEY, BoUrls.ORDER_LIST_VELOCITY_PAGE, true),
    ORDER_DETAILS(BoUrls.ORDER_DETAILS_URL, BoUrls.ORDER_DETAILS_KEY, BoUrls.ORDER_DETAILS_VELOCITY_PAGE, true),
    
    CUSTOMER_LIST(BoUrls.CUSTOMER_LIST_URL, BoUrls.CUSTOMER_LIST_KEY, BoUrls.CUSTOMER_LIST_VELOCITY_PAGE, true),
    CUSTOMER_DETAILS(BoUrls.CUSTOMER_DETAILS_URL, BoUrls.CUSTOMER_DETAILS_KEY, BoUrls.CUSTOMER_DETAILS_VELOCITY_PAGE, true),
    CUSTOMER_EDIT(BoUrls.CUSTOMER_EDIT_URL, BoUrls.CUSTOMER_EDIT_KEY, BoUrls.CUSTOMER_EDIT_VELOCITY_PAGE, true),
    CUSTOMER_ADD(BoUrls.CUSTOMER_EDIT_URL, BoUrls.CUSTOMER_ADD_KEY, BoUrls.CUSTOMER_EDIT_VELOCITY_PAGE, true),

    CATALOG(BoUrls.CATALOG_URL, BoUrls.CATALOG_KEY, BoUrls.CATALOG_VELOCITY_PAGE, true),

    GET_CATALOG_AJAX(BoUrls.GET_CATALOG_AJAX_URL, BoUrls.GET_CATALOG_AJAX_KEY, null, false),
    GET_PRODUCT_LIST_AJAX(BoUrls.GET_PRODUCT_LIST_AJAX_URL, BoUrls.GET_PRODUCT_LIST_AJAX_KEY, null, false),

    RETAILER_LIST(BoUrls.RETAILER_LIST_URL, BoUrls.RETAILER_LIST_KEY, BoUrls.RETAILER_LIST_VELOCITY_PAGE, true),
    RETAILER_DETAILS(BoUrls.RETAILER_DETAILS_URL, BoUrls.RETAILER_DETAILS_KEY, BoUrls.RETAILER_DETAILS_VELOCITY_PAGE, true),
    RETAILER_EDIT(BoUrls.RETAILER_EDIT_URL, BoUrls.RETAILER_EDIT_KEY, BoUrls.RETAILER_EDIT_VELOCITY_PAGE, true),
    RETAILER_ADD(BoUrls.RETAILER_EDIT_URL, BoUrls.RETAILER_ADD_KEY, BoUrls.RETAILER_EDIT_VELOCITY_PAGE, true),

    CACHE(BoUrls.CACHE_URL, BoUrls.CACHE_KEY, BoUrls.CACHE_VELOCITY_PAGE, true),

    ENGINE_SETTING_LIST(BoUrls.ENGINE_SETTING_LIST_URL, BoUrls.ENGINE_SETTING_LIST_KEY, BoUrls.ENGINE_SETTING_LIST_VELOCITY_PAGE, true),
    ENGINE_SETTING_DETAILS(BoUrls.ENGINE_SETTING_DETAILS_URL, BoUrls.ENGINE_SETTING_DETAILS_KEY, BoUrls.ENGINE_SETTING_DETAILS_VELOCITY_PAGE, true),
    ENGINE_SETTING_EDIT(BoUrls.ENGINE_SETTING_EDIT_URL, BoUrls.ENGINE_SETTING_EDIT_KEY, BoUrls.ENGINE_SETTING_EDIT_VELOCITY_PAGE, true),
    ENGINE_SETTING_VALUE_EDIT(BoUrls.ENGINE_SETTING_VALUE_EDIT_URL, BoUrls.ENGINE_SETTING_VALUE_EDIT_KEY, BoUrls.ENGINE_SETTING_VALUE_EDIT_VELOCITY_PAGE, true),
    ENGINE_SETTING_VALUE_ADD(BoUrls.ENGINE_SETTING_VALUE_EDIT_URL, BoUrls.ENGINE_SETTING_VALUE_ADD_KEY, BoUrls.ENGINE_SETTING_VALUE_EDIT_VELOCITY_PAGE, true),

    BATCH(BoUrls.BATCH_URL, BoUrls.BATCH_KEY, BoUrls.BATCH_VELOCITY_PAGE, true),
    BATCH_CUSTOMER(BoUrls.BATCH_CUSTOMER_URL, BoUrls.BATCH_CUSTOMER_KEY, BoUrls.BATCH_VELOCITY_PAGE, true),
    BATCH_ORDER(BoUrls.BATCH_ORDER_URL, BoUrls.BATCH_ORDER_KEY, BoUrls.BATCH_VELOCITY_PAGE, true),
    BATCH_EMAIL(BoUrls.BATCH_EMAIL_URL, BoUrls.BATCH_EMAIL_KEY, BoUrls.BATCH_VELOCITY_PAGE, true),
    BATCH_CMS(BoUrls.BATCH_CMS_URL, BoUrls.BATCH_CMS_KEY, BoUrls.BATCH_VELOCITY_PAGE, true),
    BATCH_STOCK(BoUrls.BATCH_STOCK_URL, BoUrls.BATCH_STOCK_KEY, BoUrls.BATCH_VELOCITY_PAGE, true),

    MASTER_CATALOG(BoUrls.MASTER_CATALOG_URL, BoUrls.MASTER_CATALOG_KEY, BoUrls.MASTER_CATALOG_VELOCITY_PAGE, true),
    MASTER_CATEGORY_DETAILS(BoUrls.MASTER_CATEGORY_DETAILS_URL, BoUrls.MASTER_CATEGORY_DETAILS_KEY, BoUrls.MASTER_CATEGORY_DETAILS_VELOCITY_PAGE, true),
    MASTER_CATEGORY_EDIT(BoUrls.MASTER_CATEGORY_EDIT_URL, BoUrls.MASTER_CATEGORY_EDIT_KEY, BoUrls.MASTER_CATEGORY_EDIT_VELOCITY_PAGE, true),
    MASTER_CATEGORY_ADD(BoUrls.MASTER_CATEGORY_ADD_URL, BoUrls.MASTER_CATEGORY_ADD_KEY, BoUrls.MASTER_CATEGORY_ADD_VELOCITY_PAGE, true),

    VIRTUAL_CATALOG(BoUrls.VIRTUAL_CATALOG_URL, BoUrls.VIRTUAL_CATALOG_KEY, BoUrls.VIRTUAL_CATALOG_VELOCITY_PAGE, true),
    VIRTUAL_CATEGORY_DETAILS(BoUrls.VIRTUAL_CATEGORY_DETAILS_URL, BoUrls.VIRTUAL_CATEGORY_DETAILS_KEY, BoUrls.VIRTUAL_CATEGORY_DETAILS_VELOCITY_PAGE, true),
    VIRTUAL_CATEGORY_EDIT(BoUrls.VIRTUAL_CATEGORY_EDIT_URL, BoUrls.VIRTUAL_CATEGORY_EDIT_KEY, BoUrls.VIRTUAL_CATEGORY_EDIT_VELOCITY_PAGE, true),
    VIRTUAL_CATEGORY_ADD(BoUrls.VIRTUAL_CATEGORY_ADD_URL, BoUrls.VIRTUAL_CATEGORY_ADD_KEY, BoUrls.VIRTUAL_CATEGORY_ADD_VELOCITY_PAGE, true),

    PRODUCT_MARKETING_DETAILS(BoUrls.PRODUCT_MARKETING_DETAILS_URL, BoUrls.PRODUCT_MARKETING_DETAILS_KEY, BoUrls.PRODUCT_MARKETING_DETAILS_VELOCITY_PAGE, true),
    PRODUCT_MARKETING_EDIT(BoUrls.PRODUCT_MARKETING_EDIT_URL, BoUrls.PRODUCT_MARKETING_EDIT_KEY, BoUrls.PRODUCT_MARKETING_EDIT_VELOCITY_PAGE, true),

    PRODUCT_SKU_DETAILS(BoUrls.PRODUCT_SKU_DETAILS_URL, BoUrls.PRODUCT_SKU_DETAILS_KEY, BoUrls.PRODUCT_SKU_DETAILS_VELOCITY_PAGE, true),
    PRODUCT_SKU_EDIT(BoUrls.PRODUCT_SKU_EDIT_URL, BoUrls.PRODUCT_SKU_EDIT_KEY, BoUrls.PRODUCT_SKU_EDIT_VELOCITY_PAGE, true),

    ASSET_DETAILS(BoUrls.ASSET_DETAILS_URL, BoUrls.ASSET_DETAILS_KEY, BoUrls.ASSET_DETAILS_VELOCITY_PAGE, true),
    ASSET_EDIT(BoUrls.ASSET_EDIT_URL, BoUrls.ASSET_EDIT_KEY, BoUrls.ASSET_EDIT_VELOCITY_PAGE, true),

    USER_SEARCH(BoUrls.USER_SEARCH_URL, BoUrls.USER_SEARCH_KEY, BoUrls.USER_SEARCH_VELOCITY_PAGE, true),
    USER_LIST(BoUrls.USER_LIST_URL, BoUrls.USER_LIST_KEY, BoUrls.USER_LIST_VELOCITY_PAGE, true),
    USER_DETAILS(BoUrls.USER_DETAILS_URL, BoUrls.USER_DETAILS_KEY, BoUrls.USER_DETAILS_VELOCITY_PAGE, true),
    USER_EDIT(BoUrls.USER_EDIT_URL, BoUrls.USER_EDIT_KEY, BoUrls.USER_EDIT_VELOCITY_PAGE, true),
    
    CONTEXT(BoUrls.CONTEXT_URL, BoUrls.CONTEXT_KEY, BoUrls.CONTEXT_VELOCITY_PAGE, false),

    ERROR_500(BoUrls.ERROR_500_URL, BoUrls.ERROR_500_KEY, BoUrls.ERROR_500_VELOCITY_PAGE, false),
    ERROR_400(BoUrls.ERROR_400_URL, BoUrls.ERROR_400_KEY, BoUrls.ERROR_400_VELOCITY_PAGE, false),
    ERROR_403(BoUrls.ERROR_403_URL, BoUrls.ERROR_403_KEY, BoUrls.ERROR_403_VELOCITY_PAGE, false),
    ERROR_404(BoUrls.ERROR_404_URL, BoUrls.ERROR_404_KEY, BoUrls.ERROR_404_VELOCITY_PAGE, false),
    
    VELOCITY_CACHE(BoUrls.VELOCITY_CACHE_URL, BoUrls.VELOCITY_CACHE_KEY, BoUrls.VELOCITY_CACHE_PAGE, false);

	public static final String HOME_KEY				= "home";
	public static final String HOME_URL				= "/**/home.html";
	public static final String INDEX_URL			= "/**/index.html";
	public static final String HOME_VELOCITY_PAGE	= "home";

	public static final String FAQ_KEY				= "faq";
	public static final String FAQ_URL				= "/faq.html";
	public static final String FAQ_VELOCITY_PAGE	= "faq/faq";

	public static final String LEGAL_TERMS_KEY				= "legal-terms";
	public static final String LEGAL_TERMS_URL				= "/legal-terms.html";
	public static final String LEGAL_TERMS_VELOCITY_PAGE	= "legal-terms/legal-terms";

    public static final String PERSONAL_DETAILS_KEY                 = "personal-details";
    public static final String PERSONAL_DETAILS_URL                 = "/personal-details.html";
    public static final String PERSONAL_DETAILS_VELOCITY_PAGE       = "user/personal-details";
	    
    public static final String PERSONAL_EDIT_KEY                = "personal-edit";
    public static final String PERSONAL_EDIT_URL                = "/personal-edit.html";
    public static final String PERSONAL_EDIT_VELOCITY_PAGE      = "user/personal-edit";
	    
	public static final String CHANGE_LANGUAGE_URL		= "/**/change-language.html";
	public static final String CHANGE_CONTEXT_URL		= "/**/change-context.html";
	
    public static final String LOGIN_KEY                = "login";
    public static final String LOGIN_URL                = "/login.html";
    public static final String LOGIN_VELOCITY_PAGE      = "security/login";

    public static final String LOGIN_CHECK_URL          = "/login-check.html";

    public static final String LOGOUT_KEY               = "logout";
    public static final String LOGOUT_URL               = "/**/logout-session.html";
    public static final String LOGOUT_VELOCITY_PAGE     = "security/logout";

    public static final String FORBIDDEN_KEY            = "forbidden";
    public static final String FORBIDDEN_URL            = "/forbidden.html";
    public static final String FORBIDDEN_VELOCITY_PAGE  = "security/forbidden";

    public static final String TIMEOUT_KEY              = "timeout";
    public static final String TIMEOUT_URL              = "/timeout.html";
    public static final String TIMEOUT_VELOCITY_PAGE    = "security/timeout";
	    
    public static final String FORGOTTEN_PASSWORD_KEY           = "forgotten-password";
    public static final String FORGOTTEN_PASSWORD_URL           = "/forgotten-password.html";
    public static final String FORGOTTEN_PASSWORD_VELOCITY_PAGE = "security/forgotten-password-edit";

    public static final String RESET_PASSWORD_KEY               = "reset-password";
    public static final String RESET_PASSWORD_URL               = "/reset-password.html";
    public static final String RESET_PASSWORD_VELOCITY_PAGE     = "security/reset-password-edit";
	    
    public static final String SPRING_SECURITY_URL      = "/j_spring_security_check";
    public static final String GLOBAL_SEARCH_URL        = "/**/global-search.html";
	    
	// MONITORING
	public static final String MONITORING_KEY				= "monitoring";
	public static final String MONITORING_URL				= "/**/monitoring.html";
	public static final String MONITORING_VELOCITY_PAGE		= "monitoring/monitoring";

	// REFERENCE DATAS
	public static final String REFERENCE_DATAS_KEY				= "reference-datas";
	public static final String REFERENCE_DATAS_URL				= "/**/reference-datas-list.html";
	public static final String REFERENCE_DATAS_VELOCITY_PAGE	= "reference-data/reference-datas-list";

    public static final String PAYMENT_GATEWAY_LIST_KEY           = "payment-gateway-list";
    public static final String PAYMENT_GATEWAY_LIST_URL           = "/**/payment-gateway-list.html";
    public static final String PAYMENT_GATEWAY_LIST_VELOCITY_PAGE = "payment-gateway/payment-gateway-list";

    public static final String PAYMENT_GATEWAY_DETAILS_KEY           = "payment-gateway-details";
    public static final String PAYMENT_GATEWAY_DETAILS_URL           = "/**/payment-gateway-details.html";
    public static final String PAYMENT_GATEWAY_DETAILS_VELOCITY_PAGE = "payment-gateway/payment-gateway-details";

    public static final String PAYMENT_GATEWAY_EDIT_KEY           = "payment-gateway-edit";
    public static final String PAYMENT_GATEWAY_EDIT_URL           = "/**/payment-gateway-edit.html";
    public static final String PAYMENT_GATEWAY_EDIT_VELOCITY_PAGE = "payment-gateway/payment-gateway-edit";

	// REPORTING
	public static final String REPORTING_KEY				= "reporting";
	public static final String REPORTING_URL				= "/**/reporting-list.html";
	public static final String REPORTING_VELOCITY_PAGE		= "reporting/reporting-list";

	// RULE
	public static final String RULE_LIST_KEY				= "rule-list";
	public static final String RULE_LIST_URL				= "/**/rule-list.html";
	public static final String RULE_LIST_VELOCITY_PAGE		= "rule/rule-list";

	public static final String RULE_DETAILS_KEY				= "rule-details";
	public static final String RULE_DETAILS_URL				= "/**/rule-details.html";
	public static final String RULE_DETAILS_VELOCITY_PAGE	= "rule/rule-details";

	public static final String RULE_EDIT_KEY				= "rule-edit";
	public static final String RULE_EDIT_URL				= "/**/rule-edit.html";
	public static final String RULE_EDIT_VELOCITY_PAGE		= "rule/rule-edit";

	// ORDER
	public static final String ORDER_LIST_KEY					= "order-list";
	public static final String ORDER_LIST_URL					= "/**/order-list.html";
	public static final String ORDER_LIST_VELOCITY_PAGE			= "order/order-list";

	public static final String ORDER_DETAILS_KEY				= "order-details";
	public static final String ORDER_DETAILS_URL				= "/**/order-details.html";
	public static final String ORDER_DETAILS_VELOCITY_PAGE		= "order/order-details";
	
	// DELIVERY_METHOD
	public static final String DELIVERY_METHOD_LIST_KEY                = "delivery-method-list";
	public static final String DELIVERY_METHOD_LIST_URL                = "/**/delivery-method-list.html";
	public static final String DELIVERY_METHOD_LIST_VELOCITY_PAGE      = "delivery-method/delivery-method-list";

	public static final String DELIVERY_METHOD_DETAILS_KEY				= "delivery-method-details";
	public static final String DELIVERY_METHOD_DETAILS_URL				= "/**/delivery-method-details.html";
	public static final String DELIVERY_METHOD_DETAILS_VELOCITY_PAGE	= "delivery-method/delivery-method-details";

    public static final String DELIVERY_METHOD_EDIT_KEY             = "delivery-method-edit";
    public static final String DELIVERY_METHOD_EDIT_URL             = "/**/delivery-method-edit.html";
    public static final String DELIVERY_METHOD_EDIT_VELOCITY_PAGE   = "delivery-method/delivery-method-edit";

    public static final String DELIVERY_METHOD_ADD_KEY              = "delivery-method-add";

    // WAREHOUSE
    public static final String WAREHOUSE_LIST_KEY               = "warehouse-list";
    public static final String WAREHOUSE_LIST_URL               = "/**/warehouse-list.html";
    public static final String WAREHOUSE_LIST_VELOCITY_PAGE     = "warehouse/warehouse-list";

    public static final String WAREHOUSE_DETAILS_KEY            = "warehouse-details";
    public static final String WAREHOUSE_DETAILS_URL            = "/**/warehouse-details.html";
    public static final String WAREHOUSE_DETAILS_VELOCITY_PAGE  = "warehouse/warehouse-details";

    public static final String WAREHOUSE_EDIT_KEY               = "warehouse-edit";
    public static final String WAREHOUSE_EDIT_URL               = "/**/warehouse-edit.html";
    public static final String WAREHOUSE_EDIT_VELOCITY_PAGE     = "warehouse/warehouse-edit";

    public static final String WAREHOUSE_ADD_KEY                = "warehouse-add";

    // PRICE
    public static final String TAX_LIST_KEY                = "tax-list";
    public static final String TAX_LIST_URL                = "/**/tax-list.html";
    public static final String TAX_LIST_VELOCITY_PAGE      = "tax/tax-list";

    public static final String TAX_DETAILS_KEY             = "tax-details";
    public static final String TAX_DETAILS_URL             = "/**/tax-details.html";
    public static final String TAX_DETAILS_VELOCITY_PAGE   = "tax/tax-details";

    public static final String TAX_EDIT_KEY                = "tax-edit";
    public static final String TAX_EDIT_URL                = "/**/tax-edit.html";
    public static final String TAX_EDIT_VELOCITY_PAGE      = "tax/tax-edit";

    public static final String TAX_ADD_KEY                  = "tax-add";

	// BATCH
	public static final String BATCH_KEY				= "batch";
	public static final String BATCH_URL				= "/**/batch.html";
	public static final String BATCH_VELOCITY_PAGE		= "batch/batch-list";

	public static final String BATCH_CUSTOMER_KEY		= "batch-customer";
	public static final String BATCH_CUSTOMER_URL		= "/**/batch-customer.html";

	public static final String BATCH_ORDER_KEY			= "batch-order";
	public static final String BATCH_ORDER_URL			= "/**/batch-order.html";

	public static final String BATCH_EMAIL_KEY			= "batch-email";
	public static final String BATCH_EMAIL_URL			= "/**/batch-email.html";

	public static final String BATCH_CMS_KEY			= "batch-cms";
	public static final String BATCH_CMS_URL			= "/**/batch-cms.html";

	public static final String BATCH_STOCK_KEY			= "batch-stock";
	public static final String BATCH_STOCK_URL			= "/**/batch-stock.html";

	// CACHE
	public static final String CACHE_KEY			= "cache";
	public static final String CACHE_URL			= "/**/cache.html";
	public static final String CACHE_VELOCITY_PAGE	= "cache/cache";
	
	// ENGINE SETTING
	public static final String ENGINE_SETTING_LIST_KEY             = "engine-setting-list";
	public static final String ENGINE_SETTING_LIST_URL             = "/**/engine-setting-list.html";
	public static final String ENGINE_SETTING_LIST_VELOCITY_PAGE   = "engine-setting/engine-setting-list";

	public static final String ENGINE_SETTING_DETAILS_KEY			= "engine-setting-details";
	public static final String ENGINE_SETTING_DETAILS_URL			= "/**/engine-setting-details.html";
	public static final String ENGINE_SETTING_DETAILS_VELOCITY_PAGE	= "engine-setting/engine-setting-details";

    public static final String ENGINE_SETTING_EDIT_KEY           = "engine-setting-edit";
    public static final String ENGINE_SETTING_EDIT_URL           = "/**/engine-setting-edit.html";
    public static final String ENGINE_SETTING_EDIT_VELOCITY_PAGE = "engine-setting/engine-setting-edit";

    public static final String ENGINE_SETTING_VALUE_EDIT_KEY            = "engine-setting-value-edit";
    public static final String ENGINE_SETTING_VALUE_EDIT_URL            = "/**/engine-setting-value-edit.html";
    public static final String ENGINE_SETTING_VALUE_EDIT_VELOCITY_PAGE  = "engine-setting/engine-setting-value-edit";

    public static final String ENGINE_SETTING_VALUE_ADD_KEY             = "engine-setting-value-add";

	// CATALOG
	public static final String CATALOG_KEY				= "catalog";
	public static final String CATALOG_URL				= "/**/catalog.html";
	public static final String CATALOG_VELOCITY_PAGE	= "catalog/catalog";
	
    public static final String GET_CATALOG_AJAX_KEY        = "get-catalog-ajax";
    public static final String GET_CATALOG_AJAX_URL        = "/**/get-catalog.ajax";

    public static final String GET_PRODUCT_LIST_AJAX_KEY        = "get-product-list-ajax";
    public static final String GET_PRODUCT_LIST_AJAX_URL        = "/**/get-product-list.ajax";
    
	// CUSTOMER
	public static final String CUSTOMER_LIST_KEY           = "customer-list";
	public static final String CUSTOMER_LIST_URL           = "/**/customer-list.html";
	public static final String CUSTOMER_LIST_VELOCITY_PAGE = "customer/customer-list";

    public static final String CUSTOMER_DETAILS_KEY            = "customer-details";
    public static final String CUSTOMER_DETAILS_URL            = "/**/customer-details.html";
    public static final String CUSTOMER_DETAILS_VELOCITY_PAGE  = "customer/customer-details";

    public static final String CUSTOMER_EDIT_KEY            = "customer-edit";
    public static final String CUSTOMER_EDIT_URL            = "/**/customer-edit.html";
    public static final String CUSTOMER_EDIT_VELOCITY_PAGE  = "customer/customer-edit";

    public static final String CUSTOMER_ADD_KEY             = "customer-add";

	// RETAILER
	public static final String RETAILER_LIST_KEY			= "retailer-list";
	public static final String RETAILER_LIST_URL			= "/**/retailer-list.html";
	public static final String RETAILER_LIST_VELOCITY_PAGE	= "retailer/retailer-list";

	public static final String RETAILER_DETAILS_KEY				= "retailer-details";
	public static final String RETAILER_DETAILS_URL				= "/**/retailer-details.html";
	public static final String RETAILER_DETAILS_VELOCITY_PAGE	= "retailer/retailer-details";

	public static final String RETAILER_EDIT_KEY			= "retailer-edit";
	public static final String RETAILER_EDIT_URL			= "/**/retailer-edit.html";
	public static final String RETAILER_EDIT_VELOCITY_PAGE	= "retailer/retailer-edit";

    public static final String RETAILER_ADD_KEY             = "retailer-add";
	
	// SEARCH
	public static final String SEARCH_KEY				= "search";
	public static final String SEARCH_URL				= "/**/search.html";
	public static final String SEARCH_VELOCITY_PAGE		= "search/search";

	public static final String SEARCH_CONFIG_KEY			= "search-config";
	public static final String SEARCH_CONFIG_URL			= "/**/search-config.html";
	public static final String SEARCH_CONFIG_VELOCITY_PAGE	= "search/search-config";

	// CATALOG
	public static final String MASTER_CATALOG_KEY				= "master-catalog";
	public static final String MASTER_CATALOG_URL				= "/**/master-catalog.html";
	public static final String MASTER_CATALOG_VELOCITY_PAGE		= "catalog/catalog-manage";

    public static final String MASTER_CATEGORY_DETAILS_KEY              = "master-category-details";
    public static final String MASTER_CATEGORY_DETAILS_URL              = "/**/catalog-master-category-details.html";
    public static final String MASTER_CATEGORY_DETAILS_VELOCITY_PAGE    = "catalog/catalog-category-details";

    public static final String MASTER_CATEGORY_EDIT_KEY              = "master-category-edit";
    public static final String MASTER_CATEGORY_EDIT_URL              = "/**/catalog-master-category-edit.html";
    public static final String MASTER_CATEGORY_EDIT_VELOCITY_PAGE    = "catalog/catalog-category-edit";

    public static final String MASTER_CATEGORY_ADD_KEY              = "master-category-add";
    public static final String MASTER_CATEGORY_ADD_URL              = "/**/add-master-catalog-category.html";
    public static final String MASTER_CATEGORY_ADD_VELOCITY_PAGE    = "catalog/catalog-category-edit";

	public static final String VIRTUAL_CATALOG_KEY				= "virtual-catalog";
	public static final String VIRTUAL_CATALOG_URL				= "/**/virtual-catalog.html";
	public static final String VIRTUAL_CATALOG_VELOCITY_PAGE	= "catalog/catalog-manage";

	public static final String VIRTUAL_CATEGORY_DETAILS_KEY				= "virtual-category-details";
	public static final String VIRTUAL_CATEGORY_DETAILS_URL				= "/**/catalog-virtual-category-details.html";
	public static final String VIRTUAL_CATEGORY_DETAILS_VELOCITY_PAGE	= "catalog/catalog-category-details";

    public static final String VIRTUAL_CATEGORY_EDIT_KEY            = "virtual-category-edit";
    public static final String VIRTUAL_CATEGORY_EDIT_URL            = "/**/catalog-virtual-category-edit.html";
    public static final String VIRTUAL_CATEGORY_EDIT_VELOCITY_PAGE  = "catalog/catalog-category-edit";

    public static final String VIRTUAL_CATEGORY_ADD_KEY            = "virtual-category-add";
    public static final String VIRTUAL_CATEGORY_ADD_URL            = "/**/add-virtual-catalog-category.html";
    public static final String VIRTUAL_CATEGORY_ADD_VELOCITY_PAGE  = "catalog/catalog-category-edit";

    public static final String PRODUCT_MARKETING_DETAILS_KEY            = "product-marketing-details";
    public static final String PRODUCT_MARKETING_DETAILS_URL            = "/**/product-marketing-details.html";
    public static final String PRODUCT_MARKETING_DETAILS_VELOCITY_PAGE  = "catalog/product-marketing-details";

    public static final String PRODUCT_MARKETING_EDIT_KEY            = "product-marketing-edit";
    public static final String PRODUCT_MARKETING_EDIT_URL            = "/**/product-marketing-edit.html";
    public static final String PRODUCT_MARKETING_EDIT_VELOCITY_PAGE  = "catalog/product-marketing-edit";

    public static final String PRODUCT_SKU_DETAILS_KEY            = "product-sku-details";
    public static final String PRODUCT_SKU_DETAILS_URL            = "/**/product-sku-details.html";
    public static final String PRODUCT_SKU_DETAILS_VELOCITY_PAGE  = "catalog/product-sku-details";

    public static final String PRODUCT_SKU_EDIT_KEY            = "product-sku-edit";
    public static final String PRODUCT_SKU_EDIT_URL            = "/**/product-sku-edit.html";
    public static final String PRODUCT_SKU_EDIT_VELOCITY_PAGE  = "catalog/product-sku-edit";
    
    public static final String ASSET_DETAILS_KEY            = "asset-details";
    public static final String ASSET_DETAILS_URL            = "/**/asset-details.html";
    public static final String ASSET_DETAILS_VELOCITY_PAGE  = "catalog/asset-details";

    public static final String ASSET_EDIT_KEY            = "asset-edit";
    public static final String ASSET_EDIT_URL            = "/**/asset-edit.html";
    public static final String ASSET_EDIT_VELOCITY_PAGE  = "catalog/asset-edit";
    
    // USER
    public static final String USER_SEARCH_KEY              = "search-user";
    public static final String USER_SEARCH_URL              = "/**/search-user.html";
    public static final String USER_SEARCH_VELOCITY_PAGE    = "user/user-list";

    public static final String USER_LIST_KEY                = "user-list";
    public static final String USER_LIST_URL                = "/**/user-list.html";
    public static final String USER_LIST_VELOCITY_PAGE      = "user/user-list";

    public static final String USER_DETAILS_KEY             = "user-details";
    public static final String USER_DETAILS_URL             = "/**/user-details.html";
    public static final String USER_DETAILS_VELOCITY_PAGE   = "user/user-details";

    public static final String USER_EDIT_KEY                = "user-edit";
    public static final String USER_EDIT_URL                = "/**/user-edit.html";
    public static final String USER_EDIT_VELOCITY_PAGE      = "user/user-edit";
    
    public static final String CONTEXT_KEY            = "context";
    public static final String CONTEXT_URL            = "/**/bo-context.config"; // WARN: *.config pattern must be without any web cache
    public static final String CONTEXT_VELOCITY_PAGE  = "context/context";

    public static final String ERROR_500_KEY            = "error-500";
    public static final String ERROR_500_URL            = "/500.html";
    public static final String ERROR_500_VELOCITY_PAGE  = "error/error-500";

    public static final String ERROR_400_KEY            = "error-400";
    public static final String ERROR_400_URL            = "/400.html";
    public static final String ERROR_400_VELOCITY_PAGE  = "error/error-400";

    public static final String ERROR_403_KEY            = "error-403";
    public static final String ERROR_403_URL            = "/403.html";
    public static final String ERROR_403_VELOCITY_PAGE  = "error/error-403";

    public static final String ERROR_404_KEY            = "error-404";
    public static final String ERROR_404_URL            = "/404.html";
    public static final String ERROR_404_VELOCITY_PAGE  = "error/error-404";
    
    public static final String VELOCITY_CACHE_KEY           = "flush-cache-ihm";
    public static final String VELOCITY_CACHE_URL           = "/flush-cache-ihm.html";
    public static final String VELOCITY_CACHE_PAGE          = "tools/flush-cache-ihm";
    
    private final String url;
    private final String key; // CODE IS USE TO GET THE ASSOCIATED MENU AND THE SEO VALUE
    private final String velocityPage;
    private final boolean withPrefixSEO;
    
    BoUrls(String url, String key, String velocityPage, boolean withPrefixSEO) {
        this.url = url;
        this.key = key;
        this.velocityPage = velocityPage;
        this.withPrefixSEO = withPrefixSEO;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlWithoutWildcard() {
        if (StringUtils.isNotEmpty(url)) {
            return url.replace("/**", "");
        }
        return url;
    }

    public String getKey() {
        return this.key;
    }

    public String getVelocityPage() {
        return this.velocityPage;
    }

    public boolean withPrefixSEO() {
        return withPrefixSEO;
    }

    public static BoUrls fromKey(String key) {
        for (BoUrls url : BoUrls.values()) {
            if (url.getKey() == key)
                return url;
        }
        return null;
    }

}