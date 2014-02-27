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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.dao.DeliveryMethodDao;
import org.hoteia.qalingo.core.domain.DeliveryMethod;
import org.hoteia.qalingo.core.fetchplan.common.FetchPlanGraphCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("deliveryMethodDao")
public class DeliveryMethodDaoImpl extends AbstractGenericDaoImpl implements DeliveryMethodDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public DeliveryMethod getDeliveryMethodById(final Long deliveryMethodId, Object... params) {
        Criteria criteria = createDefaultCriteria(DeliveryMethod.class);
        
        handleSpecificFetchMode(criteria, params);

        criteria.add(Restrictions.eq("id", deliveryMethodId));
        DeliveryMethod deliveryMethod = (DeliveryMethod) criteria.uniqueResult();
        return deliveryMethod;
	}

	public DeliveryMethod getDeliveryMethodByCode(final String code, Object... params) {
        Criteria criteria = createDefaultCriteria(DeliveryMethod.class);
        
        handleSpecificFetchMode(criteria, params);

        criteria.add(Restrictions.eq("code", code));
        DeliveryMethod deliveryMethod = (DeliveryMethod) criteria.uniqueResult();
        return deliveryMethod;
	}
	
	public List<DeliveryMethod> findDeliveryMethods(Object... params) {
        Criteria criteria = createDefaultCriteria(DeliveryMethod.class);
        
        handleSpecificFetchMode(criteria, params);
        
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

    @Override
    protected void handleSpecificFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            super.handleSpecificFetchMode(criteria, params);
        } else {
            super.handleSpecificFetchMode(criteria, FetchPlanGraphCommon.getDefaultDeliveryMethodFetchPlan());
        }
    }
	
}