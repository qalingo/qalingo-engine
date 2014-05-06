/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.exception;

public class ProductAlreadyExistInWishlistException extends Exception {

	/**
     * 
     */
    private static final long serialVersionUID = 8119968376428885104L;

    public static String MESSAGE_KEY = "product_already_exist_in_wishlist";
    
	public ProductAlreadyExistInWishlistException() {
	}
	
}
