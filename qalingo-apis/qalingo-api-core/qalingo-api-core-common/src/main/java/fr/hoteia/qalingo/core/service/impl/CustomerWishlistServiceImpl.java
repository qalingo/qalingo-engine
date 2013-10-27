/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.CustomerWishlistDao;
import fr.hoteia.qalingo.core.domain.CustomerWishlist;
import fr.hoteia.qalingo.core.service.CustomerWishlistService;

@Service("customerWishlistService")
@Transactional
public class CustomerWishlistServiceImpl implements CustomerWishlistService {

	@Autowired
	private CustomerWishlistDao customerWishlistDao;

	public CustomerWishlist getCustomerWishlistById(String rawCustomerWishlistId) {
		long customerWishlistId = -1;
		try {
			customerWishlistId = Long.parseLong(rawCustomerWishlistId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return customerWishlistDao.getCustomerWishlistById(customerWishlistId);
	}

	public void saveOrUpdateCustomerWishlist(CustomerWishlist customerWishlist) {
		customerWishlistDao.saveOrUpdateCustomerWishlist(customerWishlist);
	}

	public void deleteCustomerWishlist(CustomerWishlist customerWishlist) {
		customerWishlistDao.deleteCustomerWishlist(customerWishlist);
	}

}
