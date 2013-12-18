/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.dao.CustomerWishlistDao;
import org.hoteia.qalingo.core.domain.CustomerWishlist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("customerWishlistDao")
public class CustomerWishlistDaoImpl extends AbstractGenericDaoImpl implements CustomerWishlistDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public CustomerWishlist getCustomerWishlistById(final Long customerWishlistId) {
        Criteria criteria = createDefaultCriteria(CustomerWishlist.class);
        criteria.add(Restrictions.eq("id", customerWishlistId));
        CustomerWishlist customerWishlist = (CustomerWishlist) criteria.uniqueResult();
        return customerWishlist;
	}

	public void saveOrUpdateCustomerWishlist(CustomerWishlist customerWishlist) {
		if(customerWishlist.getId() == null){
			em.persist(customerWishlist);
		} else {
			em.merge(customerWishlist);
		}
	}

	public void deleteCustomerWishlist(CustomerWishlist customerWishlist) {
		em.remove(customerWishlist);
	}

}