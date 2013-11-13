package fr.hoteia.qalingo.core.domain.enumtype;

public enum BoUrls {

    HOME(BoUrls.HOME_URL, BoUrls.HOME_KEY, BoUrls.HOME_VELOCITY_PAGE),
    INDEX(BoUrls.INDEX_URL, BoUrls.HOME_KEY, BoUrls.HOME_VELOCITY_PAGE),
    FAQ(BoUrls.FAQ_URL, BoUrls.FAQ_KEY, BoUrls.FAQ_VELOCITY_PAGE),
    LEGAL_TERMS(BoUrls.LEGAL_TERMS_URL, BoUrls.LEGAL_TERMS_KEY, BoUrls.LEGAL_TERMS_VELOCITY_PAGE),
    
    VELOCITY_CACHE(BoUrls.VELOCITY_CACHE_URL, BoUrls.VELOCITY_CACHE_KEY, BoUrls.VELOCITY_CACHE_PAGE),

    LOGIN(BoUrls.LOGIN_URL, BoUrls.LOGIN_KEY, BoUrls.LOGIN_VELOCITY_PAGE),
    LOGIN_CHECK(BoUrls.LOGIN_CHECK_URL, BoUrls.LOGIN_KEY, BoUrls.LOGIN_VELOCITY_PAGE),
    LOGOUT(BoUrls.LOGOUT_URL, BoUrls.LOGOUT_KEY, BoUrls.LOGOUT_VELOCITY_PAGE),
    FORBIDDEN(BoUrls.FORBIDDEN_URL, BoUrls.FORBIDDEN_KEY, BoUrls.FORBIDDEN_VELOCITY_PAGE),
    TIMEOUT(BoUrls.TIMEOUT_URL, BoUrls.TIMEOUT_KEY, BoUrls.TIMEOUT_VELOCITY_PAGE),
    FORGOTTEN_PASSWORD(BoUrls.FORGOTTEN_PASSWORD_URL, BoUrls.FORGOTTEN_PASSWORD_KEY, BoUrls.FORGOTTEN_PASSWORD_VELOCITY_PAGE),
    RESET_PASSWORD(BoUrls.RESET_PASSWORD_URL, BoUrls.RESET_PASSWORD_KEY, BoUrls.RESET_PASSWORD_VELOCITY_PAGE),
    
    SEARCH(BoUrls.SEARCH_URL, BoUrls.SEARCH_KEY, BoUrls.SEARCH_VELOCITY_PAGE),
    SEARCH_CONFIG(BoUrls.SEARCH_CONFIG_URL, BoUrls.SEARCH_CONFIG_KEY, BoUrls.SEARCH_CONFIG_VELOCITY_PAGE),
    GLOBAL_SEARCH(BoUrls.GLOBAL_SEARCH_URL, null, null),
    CHANGE_LANGUAGE(BoUrls.CHANGE_LANGUAGE_URL, null, null),

    MONITORING(BoUrls.MONITORING_URL, BoUrls.MONITORING_KEY, BoUrls.MONITORING_VELOCITY_PAGE),
    REFERENCE_DATAS(BoUrls.REFERENCE_DATAS_URL, BoUrls.REFERENCE_DATAS_KEY, BoUrls.REFERENCE_DATAS_VELOCITY_PAGE),
    REPORTING(BoUrls.REPORTING_URL, BoUrls.REPORTING_KEY, BoUrls.REPORTING_VELOCITY_PAGE),
    
    WAREHOUSE_LIST(BoUrls.WAREHOUSE_LIST_URL, BoUrls.WAREHOUSE_LIST_KEY, BoUrls.WAREHOUSE_LIST_VELOCITY_PAGE),
    WAREHOUSE_DETAILS(BoUrls.WAREHOUSE_DETAILS_URL, BoUrls.WAREHOUSE_DETAILS_KEY, BoUrls.WAREHOUSE_DETAILS_VELOCITY_PAGE),
    WAREHOUSE_EDIT(BoUrls.WAREHOUSE_EDIT_URL, BoUrls.WAREHOUSE_EDIT_KEY, BoUrls.WAREHOUSE_EDIT_VELOCITY_PAGE),

    PRICE_LIST(BoUrls.PRICE_LIST_URL, BoUrls.PRICE_LIST_KEY, BoUrls.PRICE_LIST_VELOCITY_PAGE),
    PRICE_DETAILS(BoUrls.PRICE_DETAILS_URL, BoUrls.PRICE_DETAILS_KEY, BoUrls.PRICE_DETAILS_VELOCITY_PAGE),
    PRICE_EDIT(BoUrls.PRICE_EDIT_URL, BoUrls.PRICE_EDIT_KEY, BoUrls.PRICE_EDIT_VELOCITY_PAGE),

    RULE_LIST(BoUrls.RULE_LIST_URL, BoUrls.RULE_LIST_KEY, BoUrls.RULE_LIST_VELOCITY_PAGE),
    RULE_DETAILS(BoUrls.RULE_DETAILS_URL, BoUrls.RULE_DETAILS_KEY, BoUrls.RULE_DETAILS_VELOCITY_PAGE),
    RULE_EDIT(BoUrls.RULE_EDIT_URL, BoUrls.RULE_EDIT_KEY, BoUrls.RULE_EDIT_VELOCITY_PAGE),

    ORDER_LIST(BoUrls.ORDER_LIST_URL, BoUrls.ORDER_LIST_KEY, BoUrls.ORDER_LIST_VELOCITY_PAGE),
    ORDER_DETAILS(BoUrls.ORDER_DETAILS_URL, BoUrls.ORDER_DETAILS_KEY, BoUrls.ORDER_DETAILS_VELOCITY_PAGE),
    
    SHIPPING_LIST(BoUrls.SHIPPING_LIST_URL, BoUrls.SHIPPING_LIST_KEY, BoUrls.SHIPPING_LIST_VELOCITY_PAGE),
    SHIPPING_DETAILS(BoUrls.SHIPPING_DETAILS_URL, BoUrls.SHIPPING_DETAILS_KEY, BoUrls.SHIPPING_DETAILS_VELOCITY_PAGE),
    SHIPPING_EDIT(BoUrls.SHIPPING_EDIT_URL, BoUrls.SHIPPING_EDIT_KEY, BoUrls.SHIPPING_EDIT_VELOCITY_PAGE),

    CUSTOMER_LIST(BoUrls.CUSTOMER_LIST_URL, BoUrls.CUSTOMER_LIST_KEY, BoUrls.CUSTOMER_LIST_VELOCITY_PAGE),
    CUSTOMER_DETAILS(BoUrls.CUSTOMER_DETAILS_URL, BoUrls.CUSTOMER_DETAILS_KEY, BoUrls.CUSTOMER_DETAILS_VELOCITY_PAGE),
    CUSTOMER_EDIT(BoUrls.CUSTOMER_EDIT_URL, BoUrls.CUSTOMER_EDIT_KEY, BoUrls.CUSTOMER_EDIT_VELOCITY_PAGE),

    CATALOG(BoUrls.CATALOG_URL, BoUrls.CATALOG_KEY, BoUrls.CATALOG_VELOCITY_PAGE),

    RETAILER_LIST(BoUrls.RETAILER_LIST_URL, BoUrls.RETAILER_LIST_KEY, BoUrls.RETAILER_LIST_VELOCITY_PAGE),
    RETAILER_DETAILS(BoUrls.RETAILER_DETAILS_URL, BoUrls.RETAILER_DETAILS_KEY, BoUrls.RETAILER_DETAILS_VELOCITY_PAGE),
    RETAILER_EDIT(BoUrls.RETAILER_EDIT_URL, BoUrls.RETAILER_EDIT_KEY, BoUrls.RETAILER_EDIT_VELOCITY_PAGE),

    CACHE(BoUrls.CACHE_URL, BoUrls.CACHE_KEY, BoUrls.CACHE_VELOCITY_PAGE),

    ENGINE_SETTING_LIST(BoUrls.ENGINE_SETTING_LIST_URL, BoUrls.ENGINE_SETTING_LIST_KEY, BoUrls.ENGINE_SETTING_LIST_VELOCITY_PAGE),
    ENGINE_SETTING_DETAILS(BoUrls.ENGINE_SETTING_DETAILS_URL, BoUrls.ENGINE_SETTING_DETAILS_KEY, BoUrls.ENGINE_SETTING_DETAILS_VELOCITY_PAGE),
    ENGINE_SETTING_VALUE_EDIT(BoUrls.ENGINE_SETTING_VALUE_EDIT_URL, BoUrls.ENGINE_SETTING_VALUE_EDIT_KEY, BoUrls.ENGINE_SETTING_VALUE_EDIT_VELOCITY_PAGE),
    ENGINE_SETTING_VALUE_ADD(BoUrls.ENGINE_SETTING_VALUE_ADD_URL, BoUrls.ENGINE_SETTING_VALUE_ADD_KEY, BoUrls.ENGINE_SETTING_VALUE_ADD_VELOCITY_PAGE),

    BATCH(BoUrls.BATCH_URL, BoUrls.BATCH_KEY, BoUrls.BATCH_VELOCITY_PAGE),
    BATCH_CUSTOMER(BoUrls.BATCH_CUSTOMER_URL, BoUrls.BATCH_CUSTOMER_KEY, BoUrls.BATCH_VELOCITY_PAGE),
    BATCH_ORDER(BoUrls.BATCH_ORDER_URL, BoUrls.BATCH_ORDER_KEY, BoUrls.BATCH_VELOCITY_PAGE),
    BATCH_EMAIL(BoUrls.BATCH_EMAIL_URL, BoUrls.BATCH_EMAIL_KEY, BoUrls.BATCH_VELOCITY_PAGE),
    BATCH_CMS(BoUrls.BATCH_CMS_URL, BoUrls.BATCH_CMS_KEY, BoUrls.BATCH_VELOCITY_PAGE),
    BATCH_STOCK(BoUrls.BATCH_STOCK_URL, BoUrls.BATCH_STOCK_KEY, BoUrls.BATCH_VELOCITY_PAGE),

    MASTER_CATALOG(BoUrls.MASTER_CATALOG_URL, BoUrls.MASTER_CATALOG_KEY, BoUrls.MASTER_CATALOG_VELOCITY_PAGE),
    MASTER_CATEGORY_DETAILS(BoUrls.MASTER_CATEGORY_DETAILS_URL, BoUrls.MASTER_CATEGORY_DETAILS_KEY, BoUrls.MASTER_CATEGORY_DETAILS_VELOCITY_PAGE),
    MASTER_CATEGORY_EDIT(BoUrls.MASTER_CATEGORY_EDIT_URL, BoUrls.MASTER_CATEGORY_EDIT_KEY, BoUrls.MASTER_CATEGORY_EDIT_VELOCITY_PAGE),
    MASTER_CATEGORY_ADD(BoUrls.MASTER_CATEGORY_ADD_URL, BoUrls.MASTER_CATEGORY_ADD_KEY, BoUrls.MASTER_CATEGORY_ADD_VELOCITY_PAGE),

    VIRTUAL_CATALOG(BoUrls.VIRTUAL_CATALOG_URL, BoUrls.VIRTUAL_CATALOG_KEY, BoUrls.VIRTUAL_CATALOG_VELOCITY_PAGE),
    VIRTUAL_CATEGORY_DETAILS(BoUrls.VIRTUAL_CATEGORY_DETAILS_URL, BoUrls.VIRTUAL_CATEGORY_DETAILS_KEY, BoUrls.VIRTUAL_CATEGORY_DETAILS_VELOCITY_PAGE),
    VIRTUAL_CATEGORY_EDIT(BoUrls.VIRTUAL_CATEGORY_EDIT_URL, BoUrls.VIRTUAL_CATEGORY_EDIT_KEY, BoUrls.VIRTUAL_CATEGORY_EDIT_VELOCITY_PAGE),
    VIRTUAL_CATEGORY_ADD(BoUrls.VIRTUAL_CATEGORY_ADD_URL, BoUrls.VIRTUAL_CATEGORY_ADD_KEY, BoUrls.VIRTUAL_CATEGORY_ADD_VELOCITY_PAGE),

    PRODUCT_MARKETING_DETAILS(BoUrls.PRODUCT_MARKETING_DETAILS_URL, BoUrls.PRODUCT_MARKETING_DETAILS_KEY, BoUrls.PRODUCT_MARKETING_DETAILS_VELOCITY_PAGE),
    PRODUCT_MARKETING_EDIT(BoUrls.PRODUCT_MARKETING_EDIT_URL, BoUrls.PRODUCT_MARKETING_EDIT_KEY, BoUrls.PRODUCT_MARKETING_EDIT_VELOCITY_PAGE),

    PRODUCT_SKU_DETAILS(BoUrls.PRODUCT_SKU_DETAILS_URL, BoUrls.PRODUCT_SKU_DETAILS_KEY, BoUrls.PRODUCT_SKU_DETAILS_VELOCITY_PAGE),
    PRODUCT_SKU_EDIT(BoUrls.PRODUCT_SKU_EDIT_URL, BoUrls.PRODUCT_SKU_EDIT_KEY, BoUrls.PRODUCT_SKU_EDIT_VELOCITY_PAGE),

    ASSET_DETAILS(BoUrls.ASSET_DETAILS_URL, BoUrls.ASSET_DETAILS_KEY, BoUrls.ASSET_DETAILS_VELOCITY_PAGE),
    ASSET_EDIT(BoUrls.ASSET_EDIT_URL, BoUrls.ASSET_EDIT_KEY, BoUrls.ASSET_EDIT_VELOCITY_PAGE),

    USER_SEARCH(BoUrls.USER_SEARCH_URL, BoUrls.USER_SEARCH_KEY, BoUrls.USER_SEARCH_VELOCITY_PAGE),
    USER_LIST(BoUrls.USER_LIST_URL, BoUrls.USER_LIST_KEY, BoUrls.USER_LIST_VELOCITY_PAGE),
    USER_DETAILS(BoUrls.USER_DETAILS_URL, BoUrls.USER_DETAILS_KEY, BoUrls.USER_DETAILS_VELOCITY_PAGE),
    USER_EDIT(BoUrls.USER_EDIT_URL, BoUrls.USER_EDIT_KEY, BoUrls.USER_EDIT_VELOCITY_PAGE);

	public static final String HOME_KEY				= "home";
	public static final String HOME_URL				= "/home.html";
	public static final String INDEX_URL			= "/index.html";
	public static final String HOME_VELOCITY_PAGE	= "home";

	public static final String FAQ_KEY				= "faq";
	public static final String FAQ_URL				= "/faq.html";
	public static final String FAQ_VELOCITY_PAGE	= "faq/faq";

	public static final String LEGAL_TERMS_KEY				= "legal-terms";
	public static final String LEGAL_TERMS_URL				= "/legal-terms.html";
	public static final String LEGAL_TERMS_VELOCITY_PAGE	= "legal-terms/legal-terms";

	public static final String LOGIN_KEY				= "login";
	public static final String LOGIN_URL				= "/login.html";
	public static final String LOGIN_VELOCITY_PAGE		= "security/login";

	public static final String LOGIN_CHECK_URL				= "/login-check.html";

	public static final String LOGOUT_KEY				= "logout";
	public static final String LOGOUT_URL				= "/logout.html";
	public static final String LOGOUT_VELOCITY_PAGE		= "security/logout";

	public static final String FORBIDDEN_KEY				= "forbidden";
	public static final String FORBIDDEN_URL				= "/forbidden.html";
	public static final String FORBIDDEN_VELOCITY_PAGE		= "security/forbidden";

	public static final String TIMEOUT_KEY				= "timeout";
	public static final String TIMEOUT_URL				= "/timeout.html";
	public static final String TIMEOUT_VELOCITY_PAGE	= "security/timeout";
	
	public static final String FORGOTTEN_PASSWORD_KEY			= "forgotten-password";
	public static final String FORGOTTEN_PASSWORD_URL			= "/forgotten-password.html";
	public static final String FORGOTTEN_PASSWORD_VELOCITY_PAGE	= "security/forgotten-password-edit";

	public static final String RESET_PASSWORD_KEY				= "reset-password";
	public static final String RESET_PASSWORD_URL				= "/reset-password.html";
	public static final String RESET_PASSWORD_VELOCITY_PAGE		= "security/reset-password-edit";
	
	public static final String LOGOUT_SESSION_URL		= "/logout-session.html";
	public static final String SPRING_SECURITY_URL		= "/j_spring_security_check";
	public static final String GLOBAL_SEARCH_URL		= "/global-search.html";
	
	public static final String CHANGE_LANGUAGE_URL		= "/change-language.html";
	public static final String CHANGE_CONTEXT_URL		= "/change-context.html";
	
	public static final String VELOCITY_CACHE_KEY			= "flush-cache-ihm";
	public static final String VELOCITY_CACHE_URL			= "/flush-cache-ihm.html";
	public static final String VELOCITY_CACHE_PAGE			= "tools/flush-cache-ihm";

	// MONITORING
	public static final String MONITORING_KEY				= "monitoring";
	public static final String MONITORING_URL				= "/monitoring.html";
	public static final String MONITORING_VELOCITY_PAGE		= "monitoring/monitoring";

	// REFERENCE DATAS
	public static final String REFERENCE_DATAS_KEY				= "reference-datas";
	public static final String REFERENCE_DATAS_URL				= "/reference-datas-list.html";
	public static final String REFERENCE_DATAS_VELOCITY_PAGE	= "reference-data/reference-datas-list";
	// REPORTING
	public static final String REPORTING_KEY				= "reporting";
	public static final String REPORTING_URL				= "/reporting-list.html";
	public static final String REPORTING_VELOCITY_PAGE		= "reporting/reporting-list";

	// RULE
	public static final String RULE_LIST_KEY				= "rule";
	public static final String RULE_LIST_URL				= "/rule-list.html";
	public static final String RULE_LIST_VELOCITY_PAGE		= "rule/rule-list";

	public static final String RULE_DETAILS_KEY				= "rule-details";
	public static final String RULE_DETAILS_URL				= "/rule-details.html";
	public static final String RULE_DETAILS_VELOCITY_PAGE	= "rule/rule-details";

	public static final String RULE_EDIT_KEY				= "rule-edit";
	public static final String RULE_EDIT_URL				= "/rule-edit.html";
	public static final String RULE_EDIT_VELOCITY_PAGE		= "rule/rule-edit";

	// ORDER
	public static final String ORDER_LIST_KEY					= "order";
	public static final String ORDER_LIST_URL					= "/order-list.html";
	public static final String ORDER_LIST_VELOCITY_PAGE			= "order/order-list";

	public static final String ORDER_DETAILS_KEY				= "order-details";
	public static final String ORDER_DETAILS_URL				= "/order-details.html";
	public static final String ORDER_DETAILS_VELOCITY_PAGE		= "order/order-details";
	
	// SHIPPING
	public static final String SHIPPING_LIST_KEY				= "shipping";
	public static final String SHIPPING_LIST_URL				= "/shipping-list.html";
	public static final String SHIPPING_LIST_VELOCITY_PAGE		= "shipping/shipping-list";

	public static final String SHIPPING_DETAILS_KEY				= "shipping-details";
	public static final String SHIPPING_DETAILS_URL				= "/shipping-details.html";
	public static final String SHIPPING_DETAILS_VELOCITY_PAGE	= "shipping/shipping-details";

	public static final String SHIPPING_EDIT_KEY				= "shipping-edit";
	public static final String SHIPPING_EDIT_URL				= "/shipping-edit.html";
	public static final String SHIPPING_EDIT_VELOCITY_PAGE		= "shipping/shipping-edit";

    // WAREHOUSE
    public static final String WAREHOUSE_LIST_KEY                = "warehouse";
    public static final String WAREHOUSE_LIST_URL                = "/warehouse-list.html";
    public static final String WAREHOUSE_LIST_VELOCITY_PAGE      = "warehouse/warehouse-list";

    public static final String WAREHOUSE_DETAILS_KEY             = "warehouse-details";
    public static final String WAREHOUSE_DETAILS_URL             = "/warehouse-details.html";
    public static final String WAREHOUSE_DETAILS_VELOCITY_PAGE   = "warehouse/warehouse-details";

    public static final String WAREHOUSE_EDIT_KEY                = "warehouse-edit";
    public static final String WAREHOUSE_EDIT_URL                = "/warehouse-edit.html";
    public static final String WAREHOUSE_EDIT_VELOCITY_PAGE      = "warehouse/warehouse-edit";

    // PRICE
    public static final String PRICE_LIST_KEY                = "price";
    public static final String PRICE_LIST_URL                = "/price-list.html";
    public static final String PRICE_LIST_VELOCITY_PAGE      = "price/price-list";

    public static final String PRICE_DETAILS_KEY             = "price-details";
    public static final String PRICE_DETAILS_URL             = "/price-details.html";
    public static final String PRICE_DETAILS_VELOCITY_PAGE   = "price/price-details";

    public static final String PRICE_EDIT_KEY                = "price-edit";
    public static final String PRICE_EDIT_URL                = "/price-edit.html";
    public static final String PRICE_EDIT_VELOCITY_PAGE      = "price/price-edit";
    
	// BATCH
	public static final String BATCH_KEY				= "batch";
	public static final String BATCH_URL				= "/batch.html";
	public static final String BATCH_VELOCITY_PAGE		= "batch/batch-list";

	public static final String BATCH_CUSTOMER_KEY		= "batch-customer";
	public static final String BATCH_CUSTOMER_URL		= "/batch-customer.html";

	public static final String BATCH_ORDER_KEY			= "batch-order";
	public static final String BATCH_ORDER_URL			= "/batch-order.html";

	public static final String BATCH_EMAIL_KEY			= "batch-email";
	public static final String BATCH_EMAIL_URL			= "/batch-email.html";

	public static final String BATCH_CMS_KEY			= "batch-cms";
	public static final String BATCH_CMS_URL			= "/batch-cms.html";

	public static final String BATCH_STOCK_KEY			= "batch-stock";
	public static final String BATCH_STOCK_URL			= "/batch-stock.html";

	// CACHE
	public static final String CACHE_KEY			= "cache";
	public static final String CACHE_URL			= "/cache.html";
	public static final String CACHE_VELOCITY_PAGE	= "cache/cache";
	
	// ENGINE SETTING
	public static final String ENGINE_SETTING_LIST_KEY             = "engine-setting";
	public static final String ENGINE_SETTING_LIST_URL             = "/engine-setting.html";
	public static final String ENGINE_SETTING_LIST_VELOCITY_PAGE   = "engine-setting/engine-setting-list";

	public static final String ENGINE_SETTING_DETAILS_KEY			= "engine-setting-details";
	public static final String ENGINE_SETTING_DETAILS_URL			= "/engine-setting-details.html";
	public static final String ENGINE_SETTING_DETAILS_VELOCITY_PAGE	= "engine-setting/engine-setting-details";

    public static final String ENGINE_SETTING_VALUE_EDIT_KEY = "engine-setting-value-edit";
    public static final String ENGINE_SETTING_VALUE_EDIT_URL = "/engine-setting-value-edit.html";
    public static final String ENGINE_SETTING_VALUE_EDIT_VELOCITY_PAGE = "engine-setting/engine-setting-value-edit";
    
    public static final String ENGINE_SETTING_VALUE_ADD_KEY = "engine-setting-value-add";
    public static final String ENGINE_SETTING_VALUE_ADD_URL = "/engine-setting-value-add.html";
    public static final String ENGINE_SETTING_VALUE_ADD_VELOCITY_PAGE = "engine-setting/engine-setting-value-add";	  
	// CATALOG
	public static final String CATALOG_KEY				= "catalog";
	public static final String CATALOG_URL				= "/catalog.html";
	public static final String CATALOG_VELOCITY_PAGE	= "catalog/catalog";
	
	// CUSTOMER
	public static final String CUSTOMER_LIST_KEY           = "customer-list";
	public static final String CUSTOMER_LIST_URL           = "/customer-list.html";
	public static final String CUSTOMER_LIST_VELOCITY_PAGE = "customer/customer-list";

    public static final String CUSTOMER_DETAILS_KEY            = "customer-details";
    public static final String CUSTOMER_DETAILS_URL            = "/customer-details.html";
    public static final String CUSTOMER_DETAILS_VELOCITY_PAGE  = "customer/customer-details";

    public static final String CUSTOMER_EDIT_KEY            = "customer-edit";
    public static final String CUSTOMER_EDIT_URL            = "/customer-edit.html";
    public static final String CUSTOMER_EDIT_VELOCITY_PAGE  = "customer/customer-edit";

	// RETAILER
	public static final String RETAILER_LIST_KEY			= "retailer";
	public static final String RETAILER_LIST_URL			= "/retailer-list.html";
	public static final String RETAILER_LIST_VELOCITY_PAGE	= "retailer/retailer-list";

	public static final String RETAILER_DETAILS_KEY				= "retailer-details";
	public static final String RETAILER_DETAILS_URL				= "/retailer-details.html";
	public static final String RETAILER_DETAILS_VELOCITY_PAGE	= "retailer/retailer-details";

	public static final String RETAILER_EDIT_KEY			= "retailer-edit";
	public static final String RETAILER_EDIT_URL			= "/retailer-edit.html";
	public static final String RETAILER_EDIT_VELOCITY_PAGE	= "retailer/retailer-edit";

	// SEARCH
	public static final String SEARCH_KEY				= "search";
	public static final String SEARCH_URL				= "/search.html";
	public static final String SEARCH_VELOCITY_PAGE		= "search/search";

	public static final String SEARCH_CONFIG_KEY			= "search-config";
	public static final String SEARCH_CONFIG_URL			= "/search-config.html";
	public static final String SEARCH_CONFIG_VELOCITY_PAGE	= "search/search-config";

	// CATALOG
	public static final String MASTER_CATALOG_KEY				= "master-catalog";
	public static final String MASTER_CATALOG_URL				= "/master-catalog.html";
	public static final String MASTER_CATALOG_VELOCITY_PAGE		= "catalog/catalog";

    public static final String MASTER_CATEGORY_DETAILS_KEY              = "master-category-details";
    public static final String MASTER_CATEGORY_DETAILS_URL              = "/catalog-master-category-details.html";
    public static final String MASTER_CATEGORY_DETAILS_VELOCITY_PAGE    = "catalog/catalog-category-details";

    public static final String MASTER_CATEGORY_EDIT_KEY              = "master-category-edit";
    public static final String MASTER_CATEGORY_EDIT_URL              = "/catalog-master-category-edit.html";
    public static final String MASTER_CATEGORY_EDIT_VELOCITY_PAGE    = "catalog/catalog-category-edit";

    public static final String MASTER_CATEGORY_ADD_KEY              = "master-category-add";
    public static final String MASTER_CATEGORY_ADD_URL              = "/add-master-catalog-category.html*";
    public static final String MASTER_CATEGORY_ADD_VELOCITY_PAGE    = "catalog/catalog-category-edit";

	public static final String VIRTUAL_CATALOG_KEY				= "virtual-catalog";
	public static final String VIRTUAL_CATALOG_URL				= "/virtual-catalog.html";
	public static final String VIRTUAL_CATALOG_VELOCITY_PAGE	= "catalog/catalog";

	public static final String VIRTUAL_CATEGORY_DETAILS_KEY				= "virtual-category-details";
	public static final String VIRTUAL_CATEGORY_DETAILS_URL				= "/catalog-virtual-category-details.html";
	public static final String VIRTUAL_CATEGORY_DETAILS_VELOCITY_PAGE	= "catalog/catalog-category-details";

    public static final String VIRTUAL_CATEGORY_EDIT_KEY            = "virtual-category-edit";
    public static final String VIRTUAL_CATEGORY_EDIT_URL            = "/catalog-virtual-category-edit.html";
    public static final String VIRTUAL_CATEGORY_EDIT_VELOCITY_PAGE  = "catalog/catalog-category-edit";

    public static final String VIRTUAL_CATEGORY_ADD_KEY            = "virtual-category-add";
    public static final String VIRTUAL_CATEGORY_ADD_URL            = "/add-virtual-catalog-category.html*";
    public static final String VIRTUAL_CATEGORY_ADD_VELOCITY_PAGE  = "catalog/catalog-category-edit";

    public static final String PRODUCT_MARKETING_DETAILS_KEY            = "product-marketing-details";
    public static final String PRODUCT_MARKETING_DETAILS_URL            = "/product-marketing-details.html";
    public static final String PRODUCT_MARKETING_DETAILS_VELOCITY_PAGE  = "catalog/product-marketing-details";

    public static final String PRODUCT_MARKETING_EDIT_KEY            = "product-marketing-edit";
    public static final String PRODUCT_MARKETING_EDIT_URL            = "/product-marketing-edit.html";
    public static final String PRODUCT_MARKETING_EDIT_VELOCITY_PAGE  = "catalog/product-marketing-edit";

    public static final String PRODUCT_SKU_DETAILS_KEY            = "product-sku-details";
    public static final String PRODUCT_SKU_DETAILS_URL            = "/product-sku-details.html";
    public static final String PRODUCT_SKU_DETAILS_VELOCITY_PAGE  = "catalog/product-sku-details";

    public static final String PRODUCT_SKU_EDIT_KEY            = "product-sku-edit";
    public static final String PRODUCT_SKU_EDIT_URL            = "/product-sku-edit.html";
    public static final String PRODUCT_SKU_EDIT_VELOCITY_PAGE  = "catalog/product-sku-edit";
    
    public static final String ASSET_DETAILS_KEY            = "asset-details";
    public static final String ASSET_DETAILS_URL            = "/asset-details.html";
    public static final String ASSET_DETAILS_VELOCITY_PAGE  = "catalog/asset-details";

    public static final String ASSET_EDIT_KEY            = "asset-edit";
    public static final String ASSET_EDIT_URL            = "/asset-edit.html";
    public static final String ASSET_EDIT_VELOCITY_PAGE  = "catalog/asset-edit";
    
    // USER
    public static final String USER_SEARCH_KEY              = "search-user";
    public static final String USER_SEARCH_URL              = "/search-user.html";
    public static final String USER_SEARCH_VELOCITY_PAGE    = "user/user-list";

    public static final String USER_LIST_KEY                = "users";
    public static final String USER_LIST_URL                = "/user-list.html";
    public static final String USER_LIST_VELOCITY_PAGE      = "user/user-list";

    public static final String USER_DETAILS_KEY             = "user-details";
    public static final String USER_DETAILS_URL             = "/user-details.html";
    public static final String USER_DETAILS_VELOCITY_PAGE   = "user/user-details";

    public static final String USER_EDIT_KEY                = "user-edit";
    public static final String USER_EDIT_URL                = "/user-edit.html";
    public static final String USER_EDIT_VELOCITY_PAGE      = "user/user-edit";
    
    private final String url;
    private final String key; // CODE IS USE TO GET THE ASSOCIATED MENU AND THE SEO VALUE
    private final String velocityPage;
    
    BoUrls(String url, String key, String velocityPage) {
        this.url = url;
        this.key = key;
        this.velocityPage = velocityPage;
    }
    
	public String getUrl() {
		return url;
	}

	public String getKey() {
		return this.key;
	}
	
	public String getVelocityPage() {
		return this.velocityPage;
	}
	
    public static BoUrls fromKey(String key) {
        for (BoUrls url : BoUrls.values()) {
            if (url.getKey() == key) return url;
        }
        return null;
    }
	
}