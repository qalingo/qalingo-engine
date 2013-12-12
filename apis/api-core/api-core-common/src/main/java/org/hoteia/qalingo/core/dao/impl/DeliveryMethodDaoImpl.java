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
import org.hoteia.qalingo.core.dao.DeliveryMethodDao;
import org.hoteia.qalingo.core.domain.DeliveryMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("deliveryMethodDao")
public class DeliveryMethodDaoImpl extends AbstractGenericDaoImpl implements DeliveryMethodDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public DeliveryMethod getDeliveryMethodById(final Long deliveryMethodId) {
        Criteria criteria = getSession().createCriteria(DeliveryMethod.class);
        
        addDefaultFetch(criteria);

        criteria.add(Restrictions.eq("id", deliveryMethodId));
        DeliveryMethod deliveryMethod = (DeliveryMethod) criteria.uniqueResult();
        return deliveryMethod;
	}

	public DeliveryMethod getDeliveryMethodByCode(final String code) {
        Criteria criteria = getSession().createCriteria(DeliveryMethod.class);
        
        addDefaultFetch(criteria);

        criteria.add(Restrictions.eq("code", code));
        DeliveryMethod deliveryMethod = (DeliveryMethod) criteria.uniqueResult();
        return deliveryMethod;
	}
	
	public List<DeliveryMethod> findDeliveryMethods() {
        Criteria criteria = getSession().createCriteria(DeliveryMethod.class);
        
        addDefaultFetch(criteria);
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<DeliveryMethod> deliveryMethods = criteria.list();
		return deliveryMethods;
	}

	public void saveOrUpdateDeliveryMethod(DeliveryMethod deliveryMethod) {
		if(deliveryMethod.getId() == null){
			em.persist(deliveryMethod);
		} else {
			em.merge(deliveryMethod);
		}
	}

	public void deleteDeliveryMethod(DeliveryMethod deliveryMethod) {
		em.remove(deliveryMethod);
	}

    private void addDefaultFetch(Criteria criteria) {
        criteria.setFetchMode("deliveryMethodCountries", FetchMode.JOIN); 
    }
	
}