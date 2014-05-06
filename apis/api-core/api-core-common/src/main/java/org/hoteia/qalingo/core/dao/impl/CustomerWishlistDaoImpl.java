/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao.impl;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.dao.CustomerWishlistDao;
import org.hoteia.qalingo.core.domain.CustomerWishlist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("customerWishlistDao")
public class CustomerWishlistDaoImpl extends AbstractGenericDaoImpl implements CustomerWishlistDao {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public CustomerWishlist getCustomerWishlistById(final Long customerWishlistId, Object... params) {
        Criteria criteria = createDefaultCriteria(CustomerWishlist.class);
        criteria.add(Restrictions.eq("id", customerWishlistId));
        CustomerWishlist customerWishlist = (CustomerWishlist) criteria.uniqueResult();
        return customerWishlist;
    }

    public CustomerWishlist saveOrUpdateCustomerWishlist(final CustomerWishlist customerWishlist) {
        if (customerWishlist.getDateCreate() == null) {
            customerWishlist.setDateCreate(new Date());
        }
        customerWishlist.setDateUpdate(new Date());
        if (customerWishlist.getId() != null) {
            if(em.contains(customerWishlist)){
                em.refresh(customerWishlist);
            }
            CustomerWishlist mergedCustomerWishlist = em.merge(customerWishlist);
            em.flush();
            return mergedCustomerWishlist;
        } else {
            em.persist(customerWishlist);
            return customerWishlist;
        }
    }

    public void deleteCustomerWishlist(final CustomerWishlist customerWishlist) {
        em.remove(customerWishlist);
    }

}