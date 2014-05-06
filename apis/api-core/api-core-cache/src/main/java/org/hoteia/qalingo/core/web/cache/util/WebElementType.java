/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.cache.util;

/**
 * 
 */
public enum WebElementType {
	ENGINE_SETTING("engine_setting"),
	
	MARKET_PLACE_NAVIGATION_VIEW_BEAN_LIST("market_place_navigation_view_bean_list"),
	MARKET_NAVIGATION_VIEW_BEAN_LIST("market_navigation_view_bean_list"),
	MARKET_AREA_VIEW_BEAN_LIST("market_area_view_bean_list"),
	LOCALIZATION_VIEW_BEAN_LIST("localization_view_bean_list"),
	RETAILER_VIEW_BEAN_LIST("retailer_view_bean_list"),
	STORE_VIEW_BEAN_LIST("store_view_bean_list"),
	CUSTOMER_MENU_VIEW_BEAN_LIST("retailer_view_bean_list"),
	TOP_MENU_VIEW_BEAN_LIST("top_menu_view_bean_list"),
	FOOTER_MENU_VIEW_BEAN_LIST("footer_menu_view_bean_list");
	
	private String key;
	
	WebElementType(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return this.key;
	}
}
