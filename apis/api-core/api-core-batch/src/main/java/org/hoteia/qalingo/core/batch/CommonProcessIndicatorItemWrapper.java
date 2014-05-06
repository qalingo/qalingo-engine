/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.batch;

/**
 * 
 */
public class CommonProcessIndicatorItemWrapper<K,T> {

	private K id;

	private T item;

	public CommonProcessIndicatorItemWrapper(K id, T item) {
		this.id = id;
		this.item = item;
	}
	
	/**
	 * @return id identifying the input data (typically row in database)
	 */
	public K getId() {
		return id;
	}

	/**
	 * @return item (domain object for business processing)
	 */
	public T getItem() {
		return item;
	}
	
}
