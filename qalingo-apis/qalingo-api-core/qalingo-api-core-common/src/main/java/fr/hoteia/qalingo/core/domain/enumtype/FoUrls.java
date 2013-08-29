package fr.hoteia.qalingo.core.domain.enumtype;

import fr.hoteia.qalingo.core.RequestConstants;
import fr.hoteia.qalingo.core.service.pojo.RequestData;

public enum FoUrls {

    HOME(FoUrls.HOME_URL, FoUrls.HOME_KEY, FoUrls.HOME_VELOCITY_PAGE),
    INDEX(FoUrls.INDEX_URL, FoUrls.HOME_KEY, FoUrls.HOME_VELOCITY_PAGE),
    OUR_COMPANY(FoUrls.OUR_COMPANY_URL, FoUrls.OUR_COMPANY_KEY, FoUrls.OUR_COMPANY_VELOCITY_PAGE),
    CONDITIONS_OF_USE(FoUrls.CONDITIONS_OF_USE_URL, FoUrls.CONDITIONS_OF_USE_KEY, FoUrls.CONDITIONS_OF_USE_VELOCITY_PAGE),
    FAQ(FoUrls.FAQ_URL, FoUrls.FAQ_KEY, FoUrls.FAQ_VELOCITY_PAGE),
    LEGAL_TERMS(FoUrls.LEGAL_TERMS_URL, FoUrls.LEGAL_TERMS_KEY, FoUrls.LEGAL_TERMS_VELOCITY_PAGE),
    FOLLOW_US(FoUrls.FOLLOW_US_URL, FoUrls.FOLLOW_US_KEY, FoUrls.FOLLOW_US_VELOCITY_PAGE),
    CONTACT(FoUrls.CONTACT_URL, FoUrls.CONTACT_KEY, FoUrls.CONTACT_VELOCITY_PAGE),
    STORE_LOCATION(FoUrls.STORE_LOCATION_URL, FoUrls.STORE_LOCATION_KEY, FoUrls.STORE_LOCATION_VELOCITY_PAGE),
    NEWSLETTER_REGISTER(FoUrls.NEWSLETTER_REGISTER_URL, FoUrls.NEWSLETTER_REGISTER_KEY, FoUrls.NEWSLETTER_REGISTER_VELOCITY_PAGE),
    NEWSLETTER_UNREGISTER(FoUrls.NEWSLETTER_UNREGISTER_URL, FoUrls.NEWSLETTER_UNREGISTER_KEY, FoUrls.NEWSLETTER_UNREGISTER_VELOCITY_PAGE),
    
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
    PERSONAL_ADDRESS_LIST(FoUrls.PERSONAL_ADDRESS_LIST_URL, FoUrls.PERSONAL_ADDRESS_LIST_KEY, FoUrls.PERSONAL_ADDRESS_LIST_VELOCITY_PAGE),
    PERSONAL_ADD_ADDRESS(FoUrls.PERSONAL_ADD_ADDRESS_URL, FoUrls.PERSONAL_ADD_ADDRESS_KEY, FoUrls.PERSONAL_ADD_ADDRESS_VELOCITY_PAGE),
    PERSONAL_EDIT_ADDRESS(FoUrls.PERSONAL_EDIT_ADDRESS_URL, FoUrls.PERSONAL_EDIT_ADDRESS_KEY, FoUrls.PERSONAL_EDIT_ADDRESS_VELOCITY_PAGE),
    PERSONAL_DELETE_ADDRESS(FoUrls.PERSONAL_DELETE_ADDRESS_URL, FoUrls.PERSONAL_DELETE_ADDRESS_KEY, FoUrls.PERSONAL_DELETE_ADDRESS_VELOCITY_PAGE),
    
    
    FORBIDDEN(FoUrls.FORBIDDEN_URL, FoUrls.FORBIDDEN_KEY, FoUrls.FORBIDDEN_VELOCITY_PAGE),
    FORGOTTEN_PASSWORD(FoUrls.FORGOTTEN_PASSWORD_URL, FoUrls.FORGOTTEN_PASSWORD_KEY, FoUrls.FORGOTTEN_PASSWORD_VELOCITY_PAGE),
    RESET_PASSWORD(FoUrls.RESET_PASSWORD_URL, FoUrls.RESET_PASSWORD_KEY, FoUrls.RESET_PASSWORD_VELOCITY_PAGE);
    
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

	public static final String LEGAL_TERMS_KEY				= "legal-terms";
	public static final String LEGAL_TERMS_URL				= "/legal-terms.html";
	public static final String LEGAL_TERMS_VELOCITY_PAGE	= "legal-terms/legal-terms";

	public static final String FOLLOW_US_KEY				= "follow-us";
	public static final String FOLLOW_US_URL				= "/follow-us.html";
	public static final String FOLLOW_US_VELOCITY_PAGE		= "follow-us/follow-us-form";
	
	public static final String CONTACT_KEY				= "contact";
	public static final String CONTACT_URL				= "/contact.html";
	public static final String CONTACT_VELOCITY_PAGE	= "contact/contact-form";
	
	public static final String STORE_LOCATION_KEY				= "store-location";
	public static final String STORE_LOCATION_URL				= "/store-location.html";
	public static final String STORE_LOCATION_VELOCITY_PAGE		= "store-location/store-location";
	
	public static final String NEWSLETTER_REGISTER_KEY				= "newsletter-register";
	public static final String NEWSLETTER_REGISTER_URL				= "/newsletter-register.html";
	public static final String NEWSLETTER_REGISTER_VELOCITY_PAGE	= "newsletter/register";
	
	public static final String NEWSLETTER_UNREGISTER_KEY				= "newsletter-unregister";
	public static final String NEWSLETTER_UNREGISTER_URL				= "/newsletter-unregister.html";
	public static final String NEWSLETTER_UNREGISTER_VELOCITY_PAGE		= "newsletter/unregister";
	
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

	public static final String PERSONAL_DETAILS_KEY					= "personal-details";
	public static final String PERSONAL_DETAILS_URL					= "/personal-details.html";
	public static final String PERSONAL_DETAILS_VELOCITY_PAGE		= "customer/personal-details";
	
	public static final String PERSONAL_EDIT_KEY				= "personal-edit";
	public static final String PERSONAL_EDIT_URL				= "/personal-edit.html";
	public static final String PERSONAL_EDIT_VELOCITY_PAGE		= "customer/personal-edit";
	
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
	
	public static final String PERSONAL_ADDRESS_LIST_KEY				= "personal-address-list";
	public static final String PERSONAL_ADDRESS_LIST_URL				= "/personal-address-list.html";
	public static final String PERSONAL_ADDRESS_LIST_VELOCITY_PAGE		= "customer/personal-address-list";

	public static final String PERSONAL_ADD_ADDRESS_KEY					= "personal-add-address";
	public static final String PERSONAL_ADD_ADDRESS_URL					= "/personal-add-address.html";
	public static final String PERSONAL_ADD_ADDRESS_VELOCITY_PAGE		= "customer/personal-add-address";

	public static final String PERSONAL_EDIT_ADDRESS_KEY					= "personal-edit-address";
	public static final String PERSONAL_EDIT_ADDRESS_URL					= "/personal-edit-address.html";
	public static final String PERSONAL_EDIT_ADDRESS_VELOCITY_PAGE			= "customer/personal-edit-address";

	public static final String PERSONAL_DELETE_ADDRESS_KEY					= "personal-delete-address";
	public static final String PERSONAL_DELETE_ADDRESS_URL					= "/personal-delete-address.html";
	public static final String PERSONAL_DELETE_ADDRESS_VELOCITY_PAGE		= "customer/personal-delete-address";
	
	public static final String SPRING_SECURITY_URL		= "/j_spring_security_check";
	
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