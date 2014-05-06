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

import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.MarketPlace;
import org.hoteia.qalingo.core.domain.Retailer;

/**
 * A helper class for caching web model objects.
 */
public interface CommonCacheHelper {
	
	/**
	 * 
	 */
	String buildGlobalPrefixKey();
	
	/**
	 * 
	 */
	String buildGlobalPrefixKey(Localization localization);
	
	/**
	 * 
	 */
	String buildPrefixKey(WebElementType elementType);
	
	/**
	 * 
	 */
	String buildPrefixKey(MarketPlace marketPlace, Market market, MarketArea marketArea, 
			 Localization localization, Retailer retailer, WebElementType elementType);
	
	/**
	 * Gets an object from the cache.
	 * 
	 * @param elementType the type of cache element.
	 * @param key The cache key
	 * @return the object or null of no item in cache.
	 */
	Object getFromCache(WebElementType elementType, String key);
	
	/**
	 * Adds an object to the cache.
	 * 
	 * @param elementType the type of cache element.
	 * @param key the key
	 * @param obj the object to be cached.
	 */
	void addToCache(WebElementType elementType, String key, Object obj);
	
	/**
	 * Adds an object to the cache with a randomized TTL (time to live) value.
	 * 
	 * @param elementType the type of cache element.
	 * @param key the key
	 * @param obj the object to be cached.
	 */
	void addToCacheRandomizeTtl(WebElementType elementType, String key, Object obj);
}
