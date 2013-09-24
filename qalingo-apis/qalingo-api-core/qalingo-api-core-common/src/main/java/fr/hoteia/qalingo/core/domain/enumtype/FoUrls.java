package fr.hoteia.qalingo.core.domain.enumtype;

import fr.hoteia.qalingo.core.RequestConstants;

public enum FoUrls {

    HOME(FoUrls.HOME_URL, FoUrls.HOME_KEY, FoUrls.HOME_VELOCITY_PAGE),
    INDEX(FoUrls.INDEX_URL, FoUrls.HOME_KEY, FoUrls.HOME_VELOCITY_PAGE),
    OUR_COMPANY(FoUrls.OUR_COMPANY_URL, FoUrls.OUR_COMPANY_KEY, FoUrls.OUR_COMPANY_VELOCITY_PAGE),
    CONDITIONS_OF_USE(FoUrls.CONDITIONS_OF_USE_URL, FoUrls.CONDITIONS_OF_USE_KEY, FoUrls.CONDITIONS_OF_USE_VELOCITY_PAGE),
    FAQ(FoUrls.FAQ_URL, FoUrls.FAQ_KEY, FoUrls.FAQ_VELOCITY_PAGE),
    CLP(FoUrls.CLP_URL, FoUrls.CLP_KEY, FoUrls.CLP_VELOCITY_PAGE),
    LEGAL_TERMS(FoUrls.LEGAL_TERMS_URL, FoUrls.LEGAL_TERMS_KEY, FoUrls.LEGAL_TERMS_VELOCITY_PAGE),
    FOLLOW_US(FoUrls.FOLLOW_US_URL, FoUrls.FOLLOW_US_KEY, FoUrls.FOLLOW_US_VELOCITY_PAGE),
    CONTACT(FoUrls.CONTACT_URL, FoUrls.CONTACT_KEY, FoUrls.CONTACT_VELOCITY_PAGE),
    STORE_LOCATION(FoUrls.STORE_LOCATION_URL, FoUrls.STORE_LOCATION_KEY, FoUrls.STORE_LOCATION_VELOCITY_PAGE),
    NEWSLETTER_REGISTER(FoUrls.NEWSLETTER_REGISTER_URL, FoUrls.NEWSLETTER_REGISTER_KEY, FoUrls.NEWSLETTER_REGISTER_VELOCITY_PAGE),
    NEWSLETTER_UNREGISTER(FoUrls.NEWSLETTER_UNREGISTER_URL, FoUrls.NEWSLETTER_UNREGISTER_KEY, FoUrls.NEWSLETTER_UNREGISTER_VELOCITY_PAGE),

    SEARCH(FoUrls.SEARCH_URL, FoUrls.SEARCH_KEY, FoUrls.SEARCH_VELOCITY_PAGE),
    CHANGE_LANGUAGE(FoUrls.HOME_URL, FoUrls.HOME_KEY, FoUrls.HOME_VELOCITY_PAGE),

    VELOCITY_CACHE(FoUrls.VELOCITY_CACHE_URL, FoUrls.VELOCITY_CACHE_KEY, FoUrls.VELOCITY_CACHE_VELOCITY_PAGE),

    LOGIN(FoUrls.LOGIN_URL, FoUrls.LOGIN_KEY, FoUrls.LOGIN_VELOCITY_PAGE),
    LOGIN_CHECK(FoUrls.LOGIN_CHECK_URL, FoUrls.LOGIN_KEY, FoUrls.LOGIN_VELOCITY_PAGE),
    LOGOUT(FoUrls.LOGOUT_URL, FoUrls.LOGOUT_KEY, FoUrls.LOGOUT_VELOCITY_PAGE),
    
    PERSONAL_DETAILS(FoUrls.PERSONAL_DETAILS_URL, FoUrls.PERSONAL_DETAILS_KEY, FoUrls.PERSONAL_DETAILS_VELOCITY_PAGE),
    PERSONAL_EDIT(FoUrls.PERSONAL_EDIT_URL, FoUrls.PERSONAL_EDIT_KEY, FoUrls.PERSONAL_EDIT_VELOCITY_PAGE),
    PERSONAL_ORDER_LIST(FoUrls.PERSONAL_ORDER_LIST_URL, FoUrls.PERSONAL_ORDER_LIST_KEY, FoUrls.PERSONAL_ORDER_LIST_VELOCITY_PAGE),
    PERSONAL_ORDER_DETAILS(FoUrls.PERSONAL_ORDER_DETAILS_URL, FoUrls.PERSONAL_ORDER_DETAILS_KEY, FoUrls.PERSONAL_ORDER_DETAILS_VELOCITY_PAGE),
    PERSONAL_WISHLIST(FoUrls.PERSONAL_WISHLIST_URL, FoUrls.PERSONAL_WISHLIST_KEY, FoUrls.PERSONAL_WISHLIST_VELOCITY_PAGE),
    PERSONAL_PRODUCT_COMMENT_LIST(FoUrls.PERSONAL_PRODUCT_COMMENT_LIST_URL, FoUrls.PERSONAL_PRODUCT_COMMENT_LIST_KEY, FoUrls.PERSONAL_PRODUCT_COMMENT_LIST_VELOCITY_PAGE),
    PERSONAL_ADD_PRODUCT_COMMENT_LIST(FoUrls.PERSONAL_ADD_PRODUCT_COMMENT_LIST_URL, FoUrls.PERSONAL_ADD_PRODUCT_COMMENT_LIST_KEY, FoUrls.PERSONAL_ADD_PRODUCT_COMMENT_LIST_VELOCITY_PAGE),
    PERSONAL_REMOVE_PRODUCT_COMMENT_LIST(FoUrls.PERSONAL_REMOVE_PRODUCT_COMMENT_LIST_URL, FoUrls.PERSONAL_REMOVE_PRODUCT_COMMENT_LIST_KEY, FoUrls.PERSONAL_REMOVE_PRODUCT_COMMENT_LIST_VELOCITY_PAGE),
    PERSONAL_ADDRESS_LIST(FoUrls.PERSONAL_ADDRESS_LIST_URL, FoUrls.PERSONAL_ADDRESS_LIST_KEY, FoUrls.PERSONAL_ADDRESS_LIST_VELOCITY_PAGE),
    PERSONAL_ADD_ADDRESS(FoUrls.PERSONAL_ADD_ADDRESS_URL, FoUrls.PERSONAL_ADD_ADDRESS_KEY, FoUrls.PERSONAL_ADD_ADDRESS_VELOCITY_PAGE),
    PERSONAL_EDIT_ADDRESS(FoUrls.PERSONAL_EDIT_ADDRESS_URL, FoUrls.PERSONAL_EDIT_ADDRESS_KEY, FoUrls.PERSONAL_EDIT_ADDRESS_VELOCITY_PAGE),
    PERSONAL_DELETE_ADDRESS(FoUrls.PERSONAL_DELETE_ADDRESS_URL, FoUrls.PERSONAL_DELETE_ADDRESS_KEY, FoUrls.PERSONAL_DELETE_ADDRESS_VELOCITY_PAGE),

    CUSTOMER_DETAILS(FoUrls.CUSTOMER_DETAILS_URL, FoUrls.CUSTOMER_DETAILS_KEY, FoUrls.CUSTOMER_DETAILS_VELOCITY_PAGE),
    CUSTOMER_CREATE_ACCOUNT(FoUrls.CUSTOMER_CREATE_ACCOUNT_URL, FoUrls.CUSTOMER_CREATE_ACCOUNT_KEY, FoUrls.CUSTOMER_CREATE_ACCOUNT_VELOCITY_PAGE),
    CUSTOMER_NEW_ACCOUNT_VALIDATION(FoUrls.CUSTOMER_NEW_ACCOUNT_VALIDATION_URL, FoUrls.CUSTOMER_NEW_ACCOUNT_VALIDATION_KEY, FoUrls.CUSTOMER_NEW_ACCOUNT_VALIDATION_VELOCITY_PAGE),

    RETAILER_DETAILS(FoUrls.RETAILER_DETAILS_URL, FoUrls.RETAILER_DETAILS_KEY, FoUrls.RETAILER_DETAILS_VELOCITY_PAGE),
    RETAILER_CREATE(FoUrls.RETAILER_CREATE_URL, FoUrls.RETAILER_CREATE_KEY, FoUrls.RETAILER_CREATE_VELOCITY_PAGE),
    RETAILER_CONTACT(FoUrls.RETAILER_CONTACT_URL, FoUrls.RETAILER_CONTACT_KEY, FoUrls.RETAILER_CONTACT_FORM_VELOCITY_PAGE),
    RETAILER_COMMENT(FoUrls.RETAILER_COMMENT_URL, FoUrls.RETAILER_COMMENT_KEY, FoUrls.RETAILER_COMMENT_FORM_VELOCITY_PAGE),
    RETAILER_VOTE(FoUrls.RETAILER_VOTE_URL, FoUrls.RETAILER_COMMENT_KEY, FoUrls.RETAILER_COMMENT_FORM_VELOCITY_PAGE),

    BRAND_DETAILS(FoUrls.BRAND_DETAILS_URL, FoUrls.BRAND_DETAILS_KEY, FoUrls.BRAND_DETAILS_VELOCITY_PAGE),
    BRAND_LINE(FoUrls.BRAND_LINE_URL, FoUrls.BRAND_LINE_KEY, FoUrls.BRAND_LINE_VELOCITY_PAGE),

    CATEGORY_AS_AXE(FoUrls.CATEGORY_AS_AXE_URL, FoUrls.CATEGORY_AS_AXE_KEY, FoUrls.CATEGORY_AS_AXE_VELOCITY_PAGE),
    CATEGORY_AS_LINE(FoUrls.CATEGORY_AS_LINE_URL, FoUrls.CATEGORY_AS_LINE_KEY, FoUrls.CATEGORY_AS_LINE_VELOCITY_PAGE),
    PRODUCT_DETAILS(FoUrls.PRODUCT_DETAILS_URL, FoUrls.PRODUCT_DETAILS_KEY, FoUrls.PRODUCT_DETAILS_VELOCITY_PAGE),
    WISHLIST_ADD_PRODUCT(FoUrls.WISHLIST_ADD_PRODUCT_URL, FoUrls.WISHLIST_ADD_PRODUCT_KEY, FoUrls.WISHLIST_ADD_PRODUCT_VELOCITY_PAGE),
    WISHLIST_REMOVE_ITEM(FoUrls.WISHLIST_REMOVE_ITEM_URL, FoUrls.WISHLIST_REMOVE_ITEM_KEY, FoUrls.WISHLIST_REMOVE_ITEM_VELOCITY_PAGE),

    CART_DETAILS(FoUrls.CART_DETAILS_URL, FoUrls.CART_DETAILS_KEY, FoUrls.CART_DETAILS_VELOCITY_PAGE),
    CART_ADD_ITEM(FoUrls.CART_ADD_PRODUCT_URL, FoUrls.CART_ADD_PRODUCT_KEY, FoUrls.CART_ADD_PRODUCT_VELOCITY_PAGE),
    CART_REMOVE_ITEM(FoUrls.CART_REMOVE_ITEM_URL, FoUrls.CART_REMOVE_ITEM_KEY, FoUrls.CART_REMOVE_ITEM_VELOCITY_PAGE),
    CART_AUTH(FoUrls.CART_AUTH_URL, FoUrls.CART_AUTH_KEY, FoUrls.CART_AUTH_VELOCITY_PAGE),
    CART_DELIVERY(FoUrls.CART_DELIVERY_URL, FoUrls.CART_DELIVERY_KEY, FoUrls.CART_DELIVERY_VELOCITY_PAGE),
    CART_ORDER_PAYMENT(FoUrls.CART_ORDER_PAYMENT_URL, FoUrls.CART_ORDER_PAYMENT_KEY, FoUrls.CART_ORDER_PAYMENT_VELOCITY_PAGE),
    CART_ORDER_CONFIRMATION(FoUrls.CART_ORDER_CONFIRMATION_URL, FoUrls.CART_ORDER_CONFIRMATION_KEY, FoUrls.CART_ORDER_CONFIRMATION_VELOCITY_PAGE),

    FORBIDDEN(FoUrls.FORBIDDEN_URL, FoUrls.FORBIDDEN_KEY, FoUrls.FORBIDDEN_VELOCITY_PAGE),
    FORGOTTEN_PASSWORD(FoUrls.FORGOTTEN_PASSWORD_URL, FoUrls.FORGOTTEN_PASSWORD_KEY, FoUrls.FORGOTTEN_PASSWORD_VELOCITY_PAGE),
    RESET_PASSWORD(FoUrls.RESET_PASSWORD_URL, FoUrls.RESET_PASSWORD_KEY, FoUrls.RESET_PASSWORD_VELOCITY_PAGE),
    CANCEL_RESET_PASSWORD(FoUrls.CANCEL_RESET_PASSWORD_URL, FoUrls.CANCEL_RESET_PASSWORD_KEY, FoUrls.CANCEL_RESET_PASSWORD_VELOCITY_PAGE),
    TIMEOUT(FoUrls.TIMEOUT_URL, FoUrls.TIMEOUT_KEY, FoUrls.TIMEOUT_VELOCITY_PAGE),

    XRDS(FoUrls.XRDS_URL, FoUrls.XRDS_KEY, FoUrls.XRDS_VELOCITY_PAGE);

	public static final String HOME_KEY				= "home";
	public static final String HOME_URL				= "/home.html";
	public static final String HOME_VELOCITY_PAGE	= "home";
	public static final String INDEX_URL			= "/index.html";
    
	public static final String OUR_COMPANY_KEY				= "our-company";
	public static final String OUR_COMPANY_URL				= "/our-company.html";
	public static final String OUR_COMPANY_VELOCITY_PAGE	= "our-company/our-company";
	
	public static final String CONDITIONS_OF_USE_KEY = "conditionsofuse";
	public static final String CONDITIONS_OF_USE_URL = "/conditions-of-use.html";
	public static final String CONDITIONS_OF_USE_VELOCITY_PAGE = "conditions-of-use/conditions-of-use";
	
	public static final String FAQ_KEY				= "faq";
	public static final String FAQ_URL				= "/faq.html";
	public static final String FAQ_VELOCITY_PAGE	= "faq/faq";

	public static final String CLP_KEY				= "clp";
	public static final String CLP_URL				= "/clp.html";
	public static final String CLP_VELOCITY_PAGE	= "clp/clp";
	
	public static final String LEGAL_TERMS_KEY				= "legal-terms";
	public static final String LEGAL_TERMS_URL				= "/legal-terms.html";
	public static final String LEGAL_TERMS_VELOCITY_PAGE	= "legal-terms/legal-terms";

	public static final String FOLLOW_US_KEY					= "follow-us";
	public static final String FOLLOW_US_URL					= "/follow-us.html";
	public static final String FOLLOW_US_VELOCITY_PAGE			= "follow-us/follow-us-form";
	public static final String FOLLOW_US_SUCCESS_VELOCITY_PAGE	= "follow-us/follow-us-success";

	public static final String CONTACT_KEY				= "contact";
	public static final String CONTACT_URL				= "/contact.html";
	public static final String CONTACT_VELOCITY_PAGE	= "contact/contact-form";
	
	public static final String STORE_LOCATION_KEY				= "store-location";
	public static final String STORE_LOCATION_URL				= "/store-location.html";
	public static final String STORE_LOCATION_VELOCITY_PAGE		= "store-location/store-location";
	
	public static final String NEWSLETTER_REGISTER_KEY				= "newsletter-register";
	public static final String NEWSLETTER_REGISTER_URL				= "/newsletter-register.html";
	public static final String NEWSLETTER_REGISTER_VELOCITY_PAGE	= "newsletter/register-newsletter-success";
	
	public static final String NEWSLETTER_UNREGISTER_KEY				= "newsletter-unregister";
	public static final String NEWSLETTER_UNREGISTER_URL				= "/newsletter-unregister.html";
	public static final String NEWSLETTER_UNREGISTER_VELOCITY_PAGE		= "follow-us/unregister-newsletter-success";

	public static final String SEARCH_KEY				= "search";
	public static final String SEARCH_URL				= "/search.html";
	public static final String SEARCH_VELOCITY_PAGE		= "search/search-result";

	public static final String VELOCITY_CACHE_KEY			= "flush-cache-ihm";
	public static final String VELOCITY_CACHE_URL			= "/flush-cache-ihm.html";
	public static final String VELOCITY_CACHE_VELOCITY_PAGE	= "tools/flush-cache-ihm";
	
	public static final String LOGIN_KEY				= "login";
	public static final String LOGIN_URL				= "/login.html";
	public static final String LOGIN_VELOCITY_PAGE		= "security/login";

	public static final String LOGIN_CHECK_URL			= "/login-check.html";

	public static final String LOGOUT_KEY				= "logout";
	public static final String LOGOUT_URL				= "/logout-session.html";
	public static final String LOGOUT_VELOCITY_PAGE		= "security/logout";

	public static final String CHANGE_LANGUAGE_URL		= "/change-language.html";
	public static final String CHANGE_CONTEXT_URL		= "/change-context.html";
	
	public static final String PERSONAL_DETAILS_KEY					= "personal-details";
	public static final String PERSONAL_DETAILS_URL					= "/personal-details.html";
	public static final String PERSONAL_DETAILS_VELOCITY_PAGE		= "customer/personal-details";
	
	public static final String PERSONAL_EDIT_KEY				= "personal-edit";
	public static final String PERSONAL_EDIT_URL				= "/personal-edit.html";
	public static final String PERSONAL_EDIT_VELOCITY_PAGE		= "customer/personal-edit-form";
	
	public static final String PERSONAL_ORDER_LIST_KEY					= "personal-order-list";
	public static final String PERSONAL_ORDER_LIST_URL					= "/personal-order-list.html";
	public static final String PERSONAL_ORDER_LIST_VELOCITY_PAGE		= "customer/personal-order-list";
	
	public static final String PERSONAL_ORDER_DETAILS_KEY					= "personal-order-details";
	public static final String PERSONAL_ORDER_DETAILS_URL					= "/personal-order-details.html";
	public static final String PERSONAL_ORDER_DETAILS_VELOCITY_PAGE		= "customer/personal-order-details";
	
	public static final String PERSONAL_WISHLIST_KEY				= "personal-wishlist";
	public static final String PERSONAL_WISHLIST_URL				= "/personal-wishlist.html";
	public static final String PERSONAL_WISHLIST_VELOCITY_PAGE		= "customer/personal-wishlist";
	
	public static final String PERSONAL_PRODUCT_COMMENT_LIST_KEY				= "personal-product-comment-list";
	public static final String PERSONAL_PRODUCT_COMMENT_LIST_URL				= "/personal-product-comment-list.html";
	public static final String PERSONAL_PRODUCT_COMMENT_LIST_VELOCITY_PAGE		= "customer/personal-product-comment-list";

	public static final String PERSONAL_ADD_PRODUCT_COMMENT_LIST_KEY				= "add-personal-product-comment";
	public static final String PERSONAL_ADD_PRODUCT_COMMENT_LIST_URL				= "/add-personal-product-comment.html";
	public static final String PERSONAL_ADD_PRODUCT_COMMENT_LIST_VELOCITY_PAGE		= "";

	public static final String PERSONAL_REMOVE_PRODUCT_COMMENT_LIST_KEY				= "remove-personal-product-comment";
	public static final String PERSONAL_REMOVE_PRODUCT_COMMENT_LIST_URL				= "/remove-personal-product-comment.html";
	public static final String PERSONAL_REMOVE_PRODUCT_COMMENT_LIST_VELOCITY_PAGE	= "";

	public static final String PERSONAL_ADDRESS_LIST_KEY				= "personal-address-list";
	public static final String PERSONAL_ADDRESS_LIST_URL				= "/personal-address-list.html";
	public static final String PERSONAL_ADDRESS_LIST_VELOCITY_PAGE		= "customer/personal-address-list";

	public static final String PERSONAL_ADD_ADDRESS_KEY					= "personal-add-address";
	public static final String PERSONAL_ADD_ADDRESS_URL					= "/personal-add-address.html";
	public static final String PERSONAL_ADD_ADDRESS_VELOCITY_PAGE		= "customer/personal-add-address-form";

	public static final String PERSONAL_EDIT_ADDRESS_KEY					= "personal-edit-address";
	public static final String PERSONAL_EDIT_ADDRESS_URL					= "/personal-edit-address.html";
	public static final String PERSONAL_EDIT_ADDRESS_VELOCITY_PAGE			= "customer/personal-edit-address-form";

	public static final String PERSONAL_DELETE_ADDRESS_KEY					= "personal-delete-address";
	public static final String PERSONAL_DELETE_ADDRESS_URL					= "/personal-delete-address.html";
	public static final String PERSONAL_DELETE_ADDRESS_VELOCITY_PAGE		= "customer/personal-delete-address";

	public static final String CUSTOMER_DETAILS_KEY							= "customer-details";
	public static final String CUSTOMER_DETAILS_URL							= "/customer-details.html";
	public static final String CUSTOMER_DETAILS_VELOCITY_PAGE				= "customer/customer-details";
	
	public static final String CUSTOMER_CREATE_ACCOUNT_KEY					= "customer-create-account";
	public static final String CUSTOMER_CREATE_ACCOUNT_URL					= "/customer-create-account.html";
	public static final String CUSTOMER_CREATE_ACCOUNT_VELOCITY_PAGE		= "customer/customer-create-account-form";

	public static final String CUSTOMER_NEW_ACCOUNT_VALIDATION_KEY				= "customer-new-account-validation";
	public static final String CUSTOMER_NEW_ACCOUNT_VALIDATION_URL				= "/customer-new-account-validation.html";
	public static final String CUSTOMER_NEW_ACCOUNT_VALIDATION_VELOCITY_PAGE	= "customer/customer-new-account-validation";

	public static final String RETAILER_DETAILS_KEY					= "retailer-details";
	public static final String RETAILER_DETAILS_URL					= "/retailer-details-{" + RequestConstants.URL_PATTERN_RETAILER_CODE + ":[a-zA-Z0-9\\-]+}.html";
	public static final String RETAILER_DETAILS_VELOCITY_PAGE		= "retailer/retailer-details";

	public static final String RETAILER_CREATE_KEY					= "retailer-create";
	public static final String RETAILER_CREATE_URL					= "/retailer-create.html";
	public static final String RETAILER_CREATE_VELOCITY_PAGE		= "retailer/retailer-create-form";

	public static final String RETAILER_CONTACT_KEY						= "retailer-contact";
	public static final String RETAILER_CONTACT_URL						= "/retailer-contact-{" + RequestConstants.URL_PATTERN_RETAILER_CODE + ":[a-zA-Z0-9\\-]+}.html";
	public static final String RETAILER_CONTACT_FORM_VELOCITY_PAGE		= "retailer/retailer-contact-form";
	public static final String RETAILER_CONTACT_SUCCESS_VELOCITY_PAGE	= "retailer/retailer-contact-succes";

	public static final String RETAILER_COMMENT_KEY					= "retailer-comment";
	public static final String RETAILER_COMMENT_URL					= "/retailer-comment-{" + RequestConstants.URL_PATTERN_RETAILER_CODE + ":[a-zA-Z0-9\\-]+}.html";
	public static final String RETAILER_COMMENT_FORM_VELOCITY_PAGE	= "retailer/retailer-comment-form";
	public static final String RETAILER_VOTE_URL					= "/retailer-vote-{" + RequestConstants.URL_PATTERN_RETAILER_CODE + ":[a-zA-Z0-9\\-]+}.html";

	public static final String BRAND_DETAILS_KEY					= "brand-details";
	public static final String BRAND_DETAILS_URL					= "/brand-details-{" + RequestConstants.URL_PATTERN_BRAND_CODE + ":[a-zA-Z0-9\\-]+}.html";
	public static final String BRAND_DETAILS_VELOCITY_PAGE			= "catalog/brand-details";
	
	public static final String BRAND_LINE_KEY					= "brand-line";
	public static final String BRAND_LINE_URL					= "/brand-line-{" + RequestConstants.URL_PATTERN_BRAND_CODE + ":[a-zA-Z0-9\\-]+}.html";
	public static final String BRAND_LINE_VELOCITY_PAGE			= "catalog/brand-line";

	public static final String CATEGORY_AS_AXE_KEY					= "product-axe";
	public static final String CATEGORY_AS_AXE_URL					= "/product-axe-{" + RequestConstants.URL_PATTERN_CATEGORY_CODE + ":[a-zA-Z0-9\\-]+}.html";
	public static final String CATEGORY_AS_AXE_VELOCITY_PAGE			= "catalog/product-axe";

	public static final String CATEGORY_AS_LINE_KEY					= "product-line";
	public static final String CATEGORY_AS_LINE_URL					= "/product-line-{" + RequestConstants.URL_PATTERN_CATEGORY_CODE + ":[a-zA-Z0-9\\-]+}.html";
	public static final String CATEGORY_AS_LINE_VELOCITY_PAGE		= "catalog/product-line";

	public static final String PRODUCT_DETAILS_KEY					= "product-details";
	public static final String PRODUCT_DETAILS_URL					= "/product-details-{" + RequestConstants.URL_PATTERN_CATEGORY_CODE + ":[a-zA-Z0-9\\-]+}-pm-{" + RequestConstants.URL_PATTERN_PRODUCT_MARKETING_CODE + ":[a-zA-Z0-9\\-]+}-sku-{" + RequestConstants.URL_PATTERN_PRODUCT_SKU_CODE + ":[a-zA-Z0-9\\-]+}.html";
	public static final String PRODUCT_DETAILS_VELOCITY_PAGE		= "catalog/product-details";
	
	public static final String WISHLIST_ADD_PRODUCT_URL				= "add-to-wishlist";
	public static final String WISHLIST_ADD_PRODUCT_KEY				= "/add-to-wishlist.html";
	public static final String WISHLIST_ADD_PRODUCT_VELOCITY_PAGE	= "";

	public static final String WISHLIST_REMOVE_ITEM_URL				= "remove-from-wishlist";
	public static final String WISHLIST_REMOVE_ITEM_KEY				= "/remove-from-wishlist.html";
	public static final String WISHLIST_REMOVE_ITEM_VELOCITY_PAGE	= "";
	
	public static final String CART_DETAILS_URL					= "cart-details";
	public static final String CART_DETAILS_KEY					= "/cart-details.html";
	public static final String CART_DETAILS_VELOCITY_PAGE		= "cart/cart-details";

	public static final String CART_ADD_PRODUCT_URL				= "add-to-cart";
	public static final String CART_ADD_PRODUCT_KEY				= "/add-to-cart.html";
	public static final String CART_ADD_PRODUCT_VELOCITY_PAGE	= "";

	public static final String CART_REMOVE_ITEM_URL				= "remove-from-cart";
	public static final String CART_REMOVE_ITEM_KEY				= "/remove-from-cart.html";
	public static final String CART_REMOVE_ITEM_VELOCITY_PAGE	= "";

	public static final String CART_AUTH_KEY					= "cart-auth";
	public static final String CART_AUTH_URL					= "/cart-auth.html";
	public static final String CART_AUTH_VELOCITY_PAGE			= "cart/cart-auth";
	
	public static final String CART_DELIVERY_KEY					= "cart-delivery-order-information";
	public static final String CART_DELIVERY_URL					= "/cart-delivery-order-information.html";
	public static final String CART_DELIVERY_VELOCITY_PAGE			= "cart/cart-delivery-order-information";
	
	public static final String CART_ORDER_PAYMENT_KEY					= "cart-order-payment";
	public static final String CART_ORDER_PAYMENT_URL					= "/cart-order-payment.html";
	public static final String CART_ORDER_PAYMENT_VELOCITY_PAGE			= "cart/cart-order-payment";
	
	public static final String CART_ORDER_CONFIRMATION_KEY				= "cart-order-confirmation";
	public static final String CART_ORDER_CONFIRMATION_URL				= "/cart/cart-order-confirmation.html";
	public static final String CART_ORDER_CONFIRMATION_VELOCITY_PAGE	= "cart/cart/cart-order-confirmation";
	
	public static final String FORBIDDEN_KEY				= "forbidden";
	public static final String FORBIDDEN_URL				= "/forbidden.html";
	public static final String FORBIDDEN_VELOCITY_PAGE		= "security/forbidden";

	public static final String TIMEOUT_KEY				= "timeout";
	public static final String TIMEOUT_URL				= "/timeout.html";
	public static final String TIMEOUT_VELOCITY_PAGE	= "security/timeout";
	
	public static final String FORGOTTEN_PASSWORD_KEY					= "forgotten-password";
	public static final String FORGOTTEN_PASSWORD_URL					= "/forgotten-password.html";
	public static final String FORGOTTEN_PASSWORD_VELOCITY_PAGE			= "security/forgotten-password-form";
	public static final String FORGOTTEN_PASSWORD_SUCCESS_VELOCITY_PAGE	= "security/forgotten-password-success";

	public static final String RESET_PASSWORD_KEY					= "reset-password";
	public static final String RESET_PASSWORD_URL					= "/reset-password.html";
	public static final String RESET_PASSWORD_VELOCITY_PAGE			= "security/reset-password-form";
	public static final String RESET_PASSWORD_SUCCESS_VELOCITY_PAGE	= "security/reset-password-success";

	public static final String CANCEL_RESET_PASSWORD_KEY					= "cancel-reset-password";
	public static final String CANCEL_RESET_PASSWORD_URL					= "/cancel-reset-password.html";
	public static final String CANCEL_RESET_PASSWORD_VELOCITY_PAGE			= "";

	public static final String SPRING_SECURITY_URL		= "/j_spring_security_check";

	public static final String XRDS_KEY				= "xrds";
	public static final String XRDS_URL				= "/xrds.html";
	public static final String XRDS_VELOCITY_PAGE	= "";
	
	// TOOLS
//	public static final String XRDS_CACHE_VELOCITY_PAGE = "openid/xrds";
	
	
	
    private final String url;
    private final String key; // CODE IS USE TO GET THE SEO VALUE
    private final String velocityPage;
    
    FoUrls(String url, String key, String velocityPage) {
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
	
    public static FoUrls fromKey(String key) {
        for (FoUrls url : FoUrls.values()) {
            if (url.getKey() == key) return url;
        }
        return null;
    }
}