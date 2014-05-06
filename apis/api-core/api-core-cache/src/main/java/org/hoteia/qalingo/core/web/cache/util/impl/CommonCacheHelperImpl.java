/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.cache.util.impl;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.MarketPlace;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.web.cache.util.CommonCacheHelper;
import org.hoteia.qalingo.core.web.cache.util.WebElementType;

/**
 * 
 */
public class CommonCacheHelperImpl implements CommonCacheHelper {
	
	private Ehcache cache;
	
	/**
	 * @return the prefix key value for a global element.
	 */
	public String buildGlobalPrefixKey() {
		return "GLOBAL";
	}
	
	/**
	 * @return the prefix key value for a global element.
	 */
	public String buildGlobalPrefixKey(final Localization localization) {
		return buildGlobalPrefixKey() + "_" + localization.getCode();
	}
	
	/**
	 * @return the prefix key value for a specific element.
	 */
	public String buildPrefixKey(final WebElementType elementType) {
		String cacheKey = elementType.getKey();
		return cacheKey;
	}
	
	/**
	 * @return the prefix key value for a specific element.
	 */
	public String buildPrefixKey(final MarketPlace marketPlace, final Market market, final MarketArea marketArea, 
			 final Localization localization, final Retailer retailer, final WebElementType elementType) {
		String cacheKey = marketPlace.getCode() + "_" + market.getCode() + "_" + marketArea.getCode() + "_" + localization.getCode() + "_" + retailer.getCode() + "_" + elementType.getKey();
		return cacheKey;
	}
	
	/**
	 * Gets an object from the cache.
	 * 
	 * @param elementType the type of cache element.
	 * @param key The cache key
	 * @return the object or null of no item in cache.
	 */
	public Object getFromCache(final WebElementType elementType, final String key) {
		Element element = cache.get(elementType.getKey() + key);
		if (element != null && !element.isExpired()) {
			return element.getObjectValue();
		}
		return null;
	}
	
	/**
	 * Adds an object to the cache.
	 * 
	 * @param elementType the type of cache element.
	 * @param key the key
	 * @param obj the object to be cached.
	 */
	public void addToCache(final WebElementType elementType, final String key, final Object obj) {
		addToCacheInternal(elementType, key, obj, false);
	}
	
	/**
	 * Adds an object to the cache with a randomized TTL (time to live) value.
	 * 
	 * @param elementType the type of cache element.
	 * @param key the key
	 * @param obj the object to be cached.
	 */
	public void addToCacheRandomizeTtl(final WebElementType elementType, final String key, final Object obj) {
		addToCacheInternal(elementType, key, obj, true);
	}
	
	private void addToCacheInternal(final WebElementType elementType, final String key, final Object obj, final boolean randomizeTtl) {
		if (obj != null) {
			Element element = new Element(elementType.getKey() + key, obj);
			int ttlSeconds = (getElementTimeToLive(elementType));
			if (ttlSeconds > 0) {
				if (randomizeTtl) {
					// Add randomness to TTL so that all cache entries do not expire at the same time - the actual TTL is 100% to 175% of the configured value.
					ttlSeconds = ttlSeconds + (int)(ttlSeconds * Math.random() * 0.75);
				}
				element.setTimeToLive(ttlSeconds);
				cache.put(element);
			}
		}
	}
	
	/**
	 * @return the TTL value for an element.
	 */
	public int getElementTimeToLive(final WebElementType elementType) {
		return 3600;
	}

	/**
	 * @param cache the cache to set.
	 */
	public void setCache(final Ehcache cache) {
		this.cache = cache;
	}
	
}
