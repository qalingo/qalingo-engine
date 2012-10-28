/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.web.util;

/**
 * A helper class for caching web model objects.
 */
public interface WebCacheHelper {
	
	/**
	 * Defines the types of elements that are cached. Each element type can be configured
	 * to have a separate time-to-live.
	 */
	public enum ElementType {
		URL_BUILDER("ub_"),
		TOP_MENU("tm_"),
		STORE_RESOLVER("sr_"),
		PRODUCT_LINE("pl_"),
		CATEGORY("c_"),
		PRODUCT("p_"),
		PRODUCT_ASSOCIATION("pa_");
		
		private String key;
		
		ElementType(final String key) {
			this.key = key;
		}
		
		public String getKey() {
			return this.key;
		}
	}
	
	/**
	 * Gets an object from the cache.
	 * 
	 * @param elementType the type of cache element.
	 * @param key The cache key
	 * @return the object or null of no item in cache.
	 */
	Object getFromCache(final ElementType elementType, final String key);
	
	/**
	 * Adds an object to the cache.
	 * 
	 * @param elementType the type of cache element.
	 * @param key the key
	 * @param obj the object to be cached.
	 */
	void addToCache(final ElementType elementType, final String key, final Object obj);
	
	/**
	 * Adds an object to the cache with a randomized TTL (time to live) value.
	 * 
	 * @param elementType the type of cache element.
	 * @param key the key
	 * @param obj the object to be cached.
	 */
	void addToCacheRandomizeTtl(final ElementType elementType, final String key, final Object obj);
	
}
