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

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
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
        Criteria criteria = createDefaultCriteria(DeliveryMethod.class);
        
        addDefaultFetch(criteria);

        criteria.add(Restrictions.eq("id", deliveryMethodId));
        DeliveryMethod deliveryMethod = (DeliveryMethod) criteria.uniqueResult();
        return deliveryMethod;
	}

	public DeliveryMethod getDeliveryMethodByCode(final String code) {
        Criteria criteria = createDefaultCriteria(DeliveryMethod.class);
        
        addDefaultFetch(criteria);

        criteria.add(Restrictions.eq("code", code));
        DeliveryMethod deliveryMethod = (DeliveryMethod) criteria.uniqueResult();
        return deliveryMethod;
	}
	
	public List<DeliveryMethod> findDeliveryMethods() {
        Criteria criteria = createDefaultCriteria(DeliveryMethod.class);
        
        addDefaultFetch(criteria);
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<DeliveryMethod> deliveryMethods = criteria.list();
		return deliveryMethods;
	}

	public DeliveryMethod saveOrUpdateDeliveryMethod(final DeliveryMethod deliveryMethod) {
        if (deliveryMethod.getDateCreate() == null) {
            deliveryMethod.setDateCreate(new Date());
        }
        deliveryMethod.setDateUpdate(new Date());
        if (deliveryMethod.getId() != null) {
            if(em.contains(deliveryMethod)){
                em.refresh(deliveryMethod);
            }
            DeliveryMethod mergedDeliveryMethod = em.merge(deliveryMethod);
            em.flush();
            return mergedDeliveryMethod;
        } else {
            em.persist(deliveryMethod);
            return deliveryMethod;
        }
	}

	public void deleteDeliveryMethod(final DeliveryMethod deliveryMethod) {
		em.remove(deliveryMethod);
	}

    private void addDefaultFetch(Criteria criteria) {
        criteria.setFetchMode("deliveryMethodCountries", FetchMode.JOIN); 
        criteria.setFetchMode("prices", FetchMode.JOIN); 
        
        criteria.createAlias("prices.currency", "currency", JoinType.LEFT_OUTER_JOIN);
        criteria.setFetchMode("currency", FetchMode.JOIN);

    }
	
}