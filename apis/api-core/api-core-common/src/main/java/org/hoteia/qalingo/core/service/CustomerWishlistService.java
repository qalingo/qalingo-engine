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

import org.hoteia.qalingo.core.domain.CustomerWishlist;

public interface CustomerWishlistService {

    CustomerWishlist getCustomerWishlistById(Long customerWishlistId, Object... params);

	CustomerWishlist getCustomerWishlistById(String customerWishlistId, Object... params);
	
	void saveOrUpdateCustomerWishlist(CustomerWishlist customerWishlist);
	
	void deleteCustomerWishlist(CustomerWishlist customerWishlist);

}
