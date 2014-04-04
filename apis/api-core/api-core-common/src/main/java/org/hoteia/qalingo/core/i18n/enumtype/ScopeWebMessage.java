/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.i18n.enumtype;

/**
 * 
 */
public enum ScopeWebMessage {

	/**
	 * 
	 */
	COMMON("common"),
	
	/**
	 * 
	 */
	SEO("seo"),
	
	/**
	 * 
	 */
	HEADER_TITLE("header_title"),

	   /**
     * 
     */
    HEADER_MENU("header_menu"),

	/**
	 * 
	 */
	FOOTER_MENU("footer_menu"),
	
	/**
	 * 
	 */
	BREADCRUMB_AND_HEADER("breadcrumb_and_header"),
	
	/**
	 * 
	 */
	PRE_HOME("pre_home"),
	
	/**
	 * 
	 */
	HOME("home"),
	
	/**
	 * 
	 */
	BRAND("brand"),
	
	/**
	 * 
	 */
	AXE("axe"),
	
	/**
	 * 
	 */
	PRODUCT_LINE("product_line"),
	
	/**
	 * 
	 */
	CATALOG_CATEGORY("catalog_category"),
	
	/**
	 * 
	 */
	PRODUCT_MARKETING("product_marketing"),
	
	/**
	 * 
	 */
	PRODUCT_SKU("product_sku"),
	
	/**
	 * 
	 */
	CHECKOUT_SHOPPING_CART("checkout_shopping_cart"),
	
	/**
	 * 
	 */
	ORDER("order"),
	
	/**
	 * 
	 */
	RULE("rule"),
	
	/**
	 * 
	 */
	DELIVERY_METHOD("delivery_method"),
	
    /**
     * 
     */
    TAX("tax"),
    
	/**
	 * 
	 */
	REPORTING("reporting"),
	
	/**
	 * 
	 */
	SOCIAL("social"),
	
	/**
	 * 
	 */
	ENGINE_SETTING("engine_setting"),
	
    /**
     * 
     */
    ERROR("error"),
    
	/**
	 * 
	 */
	MONITORING("monitoring"),
	
	/**
	 * 
	 */
	CUSTOMER("customer"),
	
	/**
	 * 
	 */
	AUTH("auth"),
	
	/**
	 * 
	 */
	USER("user"),
	
	/**
	 * 
	 */
	WISHLIST("wishlist"),
	
	/**
	 * 
	 */
	CUSTOMER_PRODUCT("customer_product"),
	
	/**
	 * 
	 */
	CLP("clp"),
	
	/**
	 * 
	 */
	CONDITIONS_OF_USE("conditions_of_use"),
	
	/**
	 * 
	 */
	LEGAL_TERMS("legal_terms"),

	/**
	 * 
	 */
	FAQ("faq"),

	/**
	 * 
	 */
	RETAILER("retailer"),
	
    /**
     * 
     */
    WAREHOUSE("warehouse"),
    
    /**
     * 
     */
    PAYMENT_GATEWAY("payment_gateway"),
    
	/**
	 * 
	 */
	STORE_LOCATOR("store_locator"),

	/**
	 * 
	 */
	CONTACT("contact"),
	
	/**
	 * 
	 */
	SEARCH("search"),
	
	/**
	 * 
	 */
	FOLLOW_US("follow_us"),
	
	/**
	 * 
	 */
	SHARE_OPTION("share_option"),
	
	/**
	 * 
	 */
	OUR_COMPANY("our_company"),
	
	/**
	 * 
	 */
	CHARTE("charte"),
	
	/**
	 * 
	 */
	STORE("store");
	
	private String propertyKey = "";

	/**
	 * Constructor.
	 * 
	 * @param propertyKey the property key.
	 */
	ScopeWebMessage(final String propertyKey) {
		this.propertyKey = propertyKey;
	}

	/**
	 * Get the localization property key.
	 * 
	 * @return the localized property key
	 */
	public String getPropertyKey() {
		return this.propertyKey;
	}
	
}