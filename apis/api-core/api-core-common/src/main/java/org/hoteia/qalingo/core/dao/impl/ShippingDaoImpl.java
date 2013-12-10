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

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.dao.ShippingDao;
import org.hoteia.qalingo.core.domain.Shipping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("shippingDao")
public class ShippingDaoImpl extends AbstractGenericDaoImpl implements ShippingDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public Shipping getShippingById(final Long shippingId) {
        Criteria criteria = getSession().createCriteria(Shipping.class);
        
        addDefaultFetch(criteria);

        criteria.add(Restrictions.eq("id", shippingId));
        Shipping shipping = (Shipping) criteria.uniqueResult();
        return shipping;
	}

	public Shipping getShippingByCode(final String code) {
        Criteria criteria = getSession().createCriteria(Shipping.class);
        
        addDefaultFetch(criteria);

        criteria.add(Restrictions.eq("code", code));
        Shipping shipping = (Shipping) criteria.uniqueResult();
        return shipping;
	}
	
	public List<Shipping> findShippings() {
        Criteria criteria = getSession().createCriteria(Shipping.class);
        
        addDefaultFetch(criteria);
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<Shipping> shippings = criteria.list();
		return shippings;
	}

	public void saveOrUpdateShipping(Shipping shipping) {
		if(shipping.getId() == null){
			em.persist(shipping);
		} else {
			em.merge(shipping);
		}
	}

	public void deleteShipping(Shipping shipping) {
		em.remove(shipping);
	}

    private void addDefaultFetch(Criteria criteria) {
        criteria.setFetchMode("shippingCountries", FetchMode.JOIN); 
    }
	
}