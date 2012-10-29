/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.web.cache.util;

import fr.hoteia.qalingo.core.common.domain.Localization;
import fr.hoteia.qalingo.core.common.domain.Market;
import fr.hoteia.qalingo.core.common.domain.MarketArea;
import fr.hoteia.qalingo.core.common.domain.MarketPlace;
import fr.hoteia.qalingo.core.common.domain.Retailer;

/**
 * A helper class for caching web model objects.
 */
public interface WebCacheHelper {
	
	/**
	 * Defines the types of elements that are cached. Each element type can be configured
	 * to have a separate time-to-live.
	 */
	public enum ElementType {
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
		
		ElementType(String key) {
			this.key = key;
		}
		
		public String getKey() {
			return this.key;
		}
	}
	
	/**
	 * 
	 */
	String buildGlobalPrefixKey(Localization localization);
	
	/**
	 * 
	 */
	String buildPrefixKey(MarketPlace marketPlace, Market market, MarketArea marketArea, 
			 Localization localization, Retailer retailer, ElementType elementType);
	
	/**
	 * Gets an object from the cache.
	 * 
	 * @param elementType the type of cache element.
	 * @param key The cache key
	 * @return the object or null of no item in cache.
	 */
	Object getFromCache(ElementType elementType, String key);
	
	/**
	 * Adds an object to the cache.
	 * 
	 * @param elementType the type of cache element.
	 * @param key the key
	 * @param obj the object to be cached.
	 */
	void addToCache(ElementType elementType, String key, Object obj);
	
	/**
	 * Adds an object to the cache with a randomized TTL (time to live) value.
	 * 
	 * @param elementType the type of cache element.
	 * @param key the key
	 * @param obj the object to be cached.
	 */
	void addToCacheRandomizeTtl(ElementType elementType, String key, Object obj);
}
