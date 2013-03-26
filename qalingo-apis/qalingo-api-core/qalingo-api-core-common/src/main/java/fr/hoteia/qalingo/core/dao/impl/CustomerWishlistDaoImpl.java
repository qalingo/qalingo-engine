/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.CustomerWishlistDao;
import fr.hoteia.qalingo.core.domain.CustomerWishlist;

@Transactional
@Repository("customerWishlistDao")
public class CustomerWishlistDaoImpl extends AbstractGenericDaoImpl implements CustomerWishlistDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public CustomerWishlist getCustomerWishlistById(Long customerWishlistId) {
		return em.find(CustomerWishlist.class, customerWishlistId);
	}

	public List<CustomerWishlist> findByExample(CustomerWishlist customerWishlistExample) {
		return super.findByExample(customerWishlistExample);
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
