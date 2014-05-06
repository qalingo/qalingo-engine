/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import org.hoteia.qalingo.core.domain.Cart;

public interface CartService {

    Cart addToCart(Cart cart);

    Cart updateToCart(Cart cart);

    Cart deleteToCart(Cart cart);
    
    Cart getCartById(Long cartId, Object... params);

	Cart getCartById(String cartId, Object... params);
	
	void saveOrUpdateCart(Cart cart);
	
	void deleteCart(Cart cart);

}