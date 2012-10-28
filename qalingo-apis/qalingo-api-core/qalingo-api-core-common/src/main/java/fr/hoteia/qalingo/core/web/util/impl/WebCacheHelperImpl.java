/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.web.util.impl;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import fr.hoteia.qalingo.core.web.util.WebCacheHelper;

public class WebCacheHelperImpl implements WebCacheHelper {
	
	private Ehcache cache;

	/**
	 * Gets an object from the cache.
	 * 
	 * @param elementType the type of cache element.
	 * @param key The cache key
	 * @return the object or null of no item in cache.
	 */
	public Object getFromCache(final ElementType elementType, final String key) {
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
	public void addToCache(final ElementType elementType, final String key, final Object obj) {
		addToCacheInternal(elementType, key, obj, false);
	}
	
	/**
	 * Adds an object to the cache with a randomized TTL (time to live) value.
	 * 
	 * @param elementType the type of cache element.
	 * @param key the key
	 * @param obj the object to be cached.
	 */
	public void addToCacheRandomizeTtl(final ElementType elementType, final String key, final Object obj) {
		addToCacheInternal(elementType, key, obj, true);
	}
	
	private void addToCacheInternal(final ElementType elementType, final String key, final Object obj, final boolean randomizeTtl) {
		if (obj != null) {
			Element element = new Element(elementType.getKey() + key, obj);
//			int ttlSeconds = (getElementTimeToLive(elementType));
//			if (ttlSeconds > 0) {
//				if (randomizeTtl) {
//					// Add randomness to TTL so that all cache entries do not expire at the same time - the actual TTL is 100% to 175% of the configured value.
//					ttlSeconds = ttlSeconds + (int)(ttlSeconds * Math.random() * 0.75);
//				}
//				element.setTimeToLive(ttlSeconds);
//				cache.put(element);
//			}
		}
	}
	
//	/**
//	 * @return the TTL value for an element.
//	 */
//	public int getElementTimeToLive(final ElementType elementType) {
//		return settingsReader.getSettingValue("LVMH/PC/WEB/CACHE/elementTimeToLive", elementType.toString()).getIntegerValue();
//	}

	/**
	 * @param cache the cache to set.
	 */
	public void setCache(final Ehcache cache) {
		this.cache = cache;
	}

}
