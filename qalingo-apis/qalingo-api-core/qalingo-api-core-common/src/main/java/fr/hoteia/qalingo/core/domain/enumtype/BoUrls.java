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

    MONITORING(BoUrls.MONITORING_URL, BoUrls.MONITORING_KEY, BoUrls.MONITORING_VELOCITY_PAGE),
    REFERENCE_DATAS(BoUrls.REFERENCE_DATAS_URL, BoUrls.REFERENCE_DATAS_KEY, BoUrls.REFERENCE_DATAS_VELOCITY_PAGE),
    REPORTING(BoUrls.REPORTING_URL, BoUrls.REPORTING_KEY, BoUrls.REPORTING_VELOCITY_PAGE),
    
    RULE(BoUrls.RULE_URL, BoUrls.RULE_KEY, BoUrls.RULE_VELOCITY_PAGE),
    RULE_DETAILS(BoUrls.RULE_DETAILS_URL, BoUrls.RULE_DETAILS_KEY, BoUrls.RULE_DETAILS_VELOCITY_PAGE),
    RULE_EDIT(BoUrls.RULE_EDIT_URL, BoUrls.RULE_EDIT_KEY, BoUrls.RULE_EDIT_VELOCITY_PAGE),

    ORDER(BoUrls.ORDER_URL, BoUrls.ORDER_KEY, BoUrls.ORDER_VELOCITY_PAGE),
    ORDER_DETAILS(BoUrls.ORDER_DETAILS_URL, BoUrls.ORDER_DETAILS_KEY, BoUrls.ORDER_DETAILS_VELOCITY_PAGE),
    
    SHIPPING(BoUrls.SHIPPING_URL, BoUrls.SHIPPING_KEY, BoUrls.SHIPPING_VELOCITY_PAGE),
    SHIPPING_DETAILS(BoUrls.SHIPPING_DETAILS_URL, BoUrls.SHIPPING_DETAILS_KEY, BoUrls.SHIPPING_DETAILS_VELOCITY_PAGE),
    SHIPPING_EDIT(BoUrls.SHIPPING_EDIT_URL, BoUrls.SHIPPING_EDIT_KEY, BoUrls.SHIPPING_EDIT_VELOCITY_PAGE),
    
    CATALOG(BoUrls.CATALOG_URL, BoUrls.CATALOG_KEY, BoUrls.CATALOG_VELOCITY_PAGE),
    CUSTOMER(BoUrls.CUSTOMER_URL, BoUrls.CUSTOMER_KEY, BoUrls.CUSTOMER_VELOCITY_PAGE),
    CACHE(BoUrls.CACHE_URL, BoUrls.CACHE_KEY, BoUrls.CACHE_VELOCITY_PAGE),

    ENGINE_SETTING(BoUrls.ENGINE_SETTING_URL, BoUrls.ENGINE_SETTING_KEY, BoUrls.ENGINE_SETTING_VELOCITY_PAGE),
    ENGINE_SETTING_DETAILS(BoUrls.ENGINE_SETTING_DETAILS_URL, BoUrls.ENGINE_SETTING_DETAILS_KEY, BoUrls.ENGINE_SETTING_DETAILS_VELOCITY_PAGE),
    ENGINE_SETTING_EDIT(BoUrls.ENGINE_SETTING_EDIT_URL, BoUrls.ENGINE_SETTING_EDIT_KEY, BoUrls.ENGINE_SETTING_EDIT_VELOCITY_PAGE),

    BATCH(BoUrls.BATCH_URL, BoUrls.BATCH_KEY, BoUrls.BATCH_VELOCITY_PAGE),
    BATCH_CUSTOMER(BoUrls.BATCH_CUSTOMER_URL, BoUrls.BATCH_CUSTOMER_KEY, BoUrls.BATCH_VELOCITY_PAGE),
    BATCH_ORDER(BoUrls.BATCH_ORDER_URL, BoUrls.BATCH_ORDER_KEY, BoUrls.BATCH_VELOCITY_PAGE),
    BATCH_EMAIL(BoUrls.BATCH_EMAIL_URL, BoUrls.BATCH_EMAIL_KEY, BoUrls.BATCH_VELOCITY_PAGE),
    BATCH_CMS(BoUrls.BATCH_CMS_URL, BoUrls.BATCH_CMS_KEY, BoUrls.BATCH_VELOCITY_PAGE),
    BATCH_STOCK(BoUrls.BATCH_STOCK_URL, BoUrls.BATCH_STOCK_KEY, BoUrls.BATCH_VELOCITY_PAGE),

    MASTER_CATALOG(BoUrls.MASTER_CATALOG_URL, BoUrls.MASTER_CATALOG_KEY, BoUrls.MASTER_CATALOG_VELOCITY_PAGE),
    VIRTUAL_CATALOG(BoUrls.VIRTUAL_CATALOG_URL, BoUrls.VIRTUAL_CATALOG_KEY, BoUrls.VIRTUAL_CATALOG_VELOCITY_PAGE),
    VIRTUAL_CATEGORY_DETAILS(BoUrls.VIRTUAL_CATEGORY_DETAILS_URL, BoUrls.VIRTUAL_CATEGORY_DETAILS_KEY, BoUrls.VIRTUAL_CATEGORY_DETAILS_VELOCITY_PAGE),

    USER_SEARCH(BoUrls.USER_SEARCH_URL, BoUrls.USER_SEARCH_KEY, BoUrls.USER_SEARCH_VELOCITY_PAGE),
    USER_LIST(BoUrls.USER_LIST_URL, BoUrls.USER_LIST_KEY, BoUrls.USER_LIST_VELOCITY_PAGE),
    USER_DETAILS(BoUrls.USER_DETAILS_URL, BoUrls.USER_DETAILS_KEY, BoUrls.USER_DETAILS_VELOCITY_PAGE),
    USER_EDIT(BoUrls.USER_EDIT_URL, BoUrls.USER_EDIT_KEY, BoUrls.USER_EDIT_VELOCITY_PAGE),

    GLOBAL_SEARCH(BoUrls.GLOBAL_SEARCH_URL, null, null),
    
    CHANGE_LANGUAGE(BoUrls.CHANGE_LANGUAGE_URL, null, null);
    
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
	
	public static final String FORGOTTEN_PASSWORD_KEY				= "forgotten-password";
	public static final String FORGOTTEN_PASSWORD_URL				= "/forgotten-password.html";
	public static final String FORGOTTEN_PASSWORD_VELOCITY_PAGE		= "security/forgotten-password-form";

	public static final String RESET_PASSWORD_KEY				= "reset-password";
	public static final String RESET_PASSWORD_URL				= "/reset-password.html";
	public static final String RESET_PASSWORD_VELOCITY_PAGE		= "security/reset-password-form";
	
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
	public static final String REFERENCE_DATAS_KEY					= "reference-datas";
	public static final String REFERENCE_DATAS_URL					= "/reference-datas.html";
	public static final String REFERENCE_DATAS_VELOCITY_PAGE		= "reference-data/reference-data";

	// REPORTING
	public static final String REPORTING_KEY				= "reporting";
	public static final String REPORTING_URL				= "/reporting.html";
	public static final String REPORTING_VELOCITY_PAGE		= "reporting/reporting";

	// RULE
	public static final String RULE_KEY				= "rule";
	public static final String RULE_URL				= "/rule.html";
	public static final String RULE_VELOCITY_PAGE	= "rule/rule-list";

	public static final String RULE_LIST_URL				= "/rule_list.html";

	public static final String RULE_DETAILS_KEY				= "rule-details";
	public static final String RULE_DETAILS_URL				= "/rule-details.html";
	public static final String RULE_DETAILS_VELOCITY_PAGE	= "rule/rule-details";

	public static final String RULE_EDIT_KEY				= "rule-edit";
	public static final String RULE_EDIT_URL				= "/rule-edit.html";
	public static final String RULE_EDIT_VELOCITY_PAGE		= "rule/rule-form";

	// ORDER
	public static final String ORDER_KEY				= "order";
	public static final String ORDER_URL				= "/order.html";
	public static final String ORDER_VELOCITY_PAGE		= "order/order-list";

	public static final String ORDER_LIST_URL				= "/order-list.html";

	public static final String ORDER_DETAILS_KEY				= "order-details";
	public static final String ORDER_DETAILS_URL				= "/order-details.html";
	public static final String ORDER_DETAILS_VELOCITY_PAGE		= "order/order-details";
	
	// SHIPPING
	public static final String SHIPPING_KEY				= "shipping";
	public static final String SHIPPING_URL				= "/shipping.html";
	public static final String SHIPPING_VELOCITY_PAGE	= "shipping/shipping";

	public static final String SHIPPING_LIST_URL				= "/shipping-list.html";

	public static final String SHIPPING_DETAILS_KEY				= "shipping-details";
	public static final String SHIPPING_DETAILS_URL				= "/shipping-details.html";
	public static final String SHIPPING_DETAILS_VELOCITY_PAGE	= "shipping/shipping-details";

	public static final String SHIPPING_EDIT_KEY				= "shipping-edit";
	public static final String SHIPPING_EDIT_URL				= "/shipping-edit.html";
	public static final String SHIPPING_EDIT_VELOCITY_PAGE		= "shipping/shipping-form";

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
	public static final String ENGINE_SETTING_KEY			= "engine-setting";
	public static final String ENGINE_SETTING_URL			= "/engine-setting.html";
	public static final String ENGINE_SETTING_VELOCITY_PAGE	= "engine-setting/engine-setting-list";

	public static final String ENGINE_SETTING_DETAILS_KEY			= "engine-setting-details";
	public static final String ENGINE_SETTING_DETAILS_URL			= "/engine-setting-details.html";
	public static final String ENGINE_SETTING_DETAILS_VELOCITY_PAGE	= "engine-setting/engine-setting-details";

	public static final String ENGINE_SETTING_EDIT_KEY				= "engine-setting-edit";
	public static final String ENGINE_SETTING_EDIT_URL				= "/engine-setting-edit.html";
	public static final String ENGINE_SETTING_EDIT_VELOCITY_PAGE	= "engine-setting/engine-setting-value-edit";

	// CATALOG
	public static final String CATALOG_KEY				= "catalog";
	public static final String CATALOG_URL				= "/catalog.html";
	public static final String CATALOG_VELOCITY_PAGE	= "catalog/catalog";
	
	// CUSTOMER
	public static final String CUSTOMER_KEY				= "customer";
	public static final String CUSTOMER_URL				= "/customer.html";
	public static final String CUSTOMER_VELOCITY_PAGE	= "customer/customer";
	
	// SEARCH
	public static final String SEARCH_KEY				= "search";
	public static final String SEARCH_URL				= "/search.html";
	public static final String SEARCH_VELOCITY_PAGE		= "search/search";

	public static final String SEARCH_CONFIG_KEY			= "search-config";
	public static final String SEARCH_CONFIG_URL			= "/search-config.html";
	public static final String SEARCH_CONFIG_VELOCITY_PAGE	= "search/search-config";

	// USER
	public static final String USER_SEARCH_KEY				= "search-user";
	public static final String USER_SEARCH_URL				= "/search-user.html";
	public static final String USER_SEARCH_VELOCITY_PAGE	= "user/user-list";

	public static final String USER_LIST_KEY			= "users";
	public static final String USER_LIST_URL			= "/users.html";
	public static final String USER_LIST_VELOCITY_PAGE	= "user/user-list";

	public static final String USER_DETAILS_KEY				= "user-details";
	public static final String USER_DETAILS_URL				= "/user-details.html";
	public static final String USER_DETAILS_VELOCITY_PAGE	= "user/user-details";

	public static final String USER_EDIT_KEY				= "user-edit";
	public static final String USER_EDIT_URL				= "/user-edit.html";
	public static final String USER_EDIT_VELOCITY_PAGE		= "user/user-edit";
	
	// CATALOG
	public static final String MASTER_CATALOG_KEY				= "master-catalog";
	public static final String MASTER_CATALOG_URL				= "/master-catalog.html";
	public static final String MASTER_CATALOG_VELOCITY_PAGE		= "catalog/catalog";

	public static final String VIRTUAL_CATALOG_KEY				= "virtual-catalog";
	public static final String VIRTUAL_CATALOG_URL				= "/virtual-catalog.html";
	public static final String VIRTUAL_CATALOG_VELOCITY_PAGE	= "catalog/catalog";

	public static final String VIRTUAL_CATEGORY_DETAILS_KEY				= "virtual-category-details";
	public static final String VIRTUAL_CATEGORY_DETAILS_URL				= "/catalog-virtual-category-details.html";
	public static final String VIRTUAL_CATEGORY_DETAILS_VELOCITY_PAGE	= "catalog/catalog-category-details";

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