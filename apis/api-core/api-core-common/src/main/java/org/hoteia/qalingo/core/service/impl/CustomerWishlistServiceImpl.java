/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.dao.CustomerWishlistDao;
import org.hoteia.qalingo.core.domain.CustomerWishlist;
import org.hoteia.qalingo.core.service.CustomerWishlistService;

@Service("customerWishlistService")
@Transactional
public class CustomerWishlistServiceImpl implements CustomerWishlistService {

    @Autowired
    private CustomerWishlistDao customerWishlistDao;

    public CustomerWishlist getCustomerWishlistById(final Long customerWishlistId, Object... params) {
        return customerWishlistDao.getCustomerWishlistById(customerWishlistId, params);
    }

    public CustomerWishlist getCustomerWishlistById(final String rawCustomerWishlistId, Object... params) {
        long customerWishlistId = -1;
        try {
            customerWishlistId = Long.parseLong(rawCustomerWishlistId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getCustomerWishlistById(customerWishlistId, params);
    }

    public void saveOrUpdateCustomerWishlist(final CustomerWishlist customerWishlist) {
        customerWishlistDao.saveOrUpdateCustomerWishlist(customerWishlist);
    }

    public void deleteCustomerWishlist(final CustomerWishlist customerWishlist) {
        customerWishlistDao.deleteCustomerWishlist(customerWishlist);
    }

}