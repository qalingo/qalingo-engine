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
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.dao.PaymentGatewayDao;
import org.hoteia.qalingo.core.domain.AbstractPaymentGateway;
import org.hoteia.qalingo.core.domain.PaymentGatewayOption;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.common.FetchPlanGraphCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("paymentGatewayDao")
public class PaymentGatewayDaoImpl extends AbstractGenericDaoImpl implements PaymentGatewayDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public AbstractPaymentGateway getPaymentGatewayById(final Long paymentGatewayId, Object... params) {
        Criteria criteria = createDefaultCriteria(AbstractPaymentGateway.class);
        
        FetchPlan fetchPlan = handleSpecificFetchMode(criteria, params);

        criteria.add(Restrictions.eq("id", paymentGatewayId));
        AbstractPaymentGateway paymentGateway = (AbstractPaymentGateway) criteria.uniqueResult();
        if(paymentGateway != null){
            paymentGateway.setFetchPlan(fetchPlan);
        }
        return paymentGateway;
	}

    public AbstractPaymentGateway getPaymentGatewayByCode(final String paymentGatewayCode, Object... params) {
        Criteria criteria = createDefaultCriteria(AbstractPaymentGateway.class);

        FetchPlan fetchPlan = handleSpecificFetchMode(criteria, params);

        criteria.add(Restrictions.eq("code", paymentGatewayCode));
        AbstractPaymentGateway paymentGateway = (AbstractPaymentGateway) criteria.uniqueResult();
        if(paymentGateway != null){
            paymentGateway.setFetchPlan(fetchPlan);
        }
        return paymentGateway;
    }
	
	public List<AbstractPaymentGateway> findPaymentGateways(Object... params) {
        Criteria criteria = createDefaultCriteria(AbstractPaymentGateway.class);

        handleSpecificFetchMode(criteria, params);
        
        criteria.addOrder(Order.asc("code"));

        @SuppressWarnings("unchecked")
        List<AbstractPaymentGateway> paymentGateways = criteria.list();
		return paymentGateways;
	}
	
    public List<PaymentGatewayOption> findPaymentGatewayOptions() {
        Criteria criteria = createDefaultCriteria(PaymentGatewayOption.class);

        criteria.addOrder(Order.asc("code"));

        @SuppressWarnings("unchecked")
        List<PaymentGatewayOption> paymentGatewayOptions = criteria.list();
        return paymentGatewayOptions;
    }

	public AbstractPaymentGateway saveOrUpdatePaymentGateway(AbstractPaymentGateway paymentGateway) {
		if(paymentGateway.getDateCreate() == null){
			paymentGateway.setDateCreate(new Date());
		}
		paymentGateway.setDateUpdate(new Date());
        if (paymentGateway.getId() != null) {
            if(em.contains(paymentGateway)){
                em.refresh(paymentGateway);
            }
            AbstractPaymentGateway mergedPaymentGateway = em.merge(paymentGateway);
            em.flush();
            return mergedPaymentGateway;
        } else {
            em.persist(paymentGateway);
            return paymentGateway;
        }
	}

	public void deletePaymentGateway(AbstractPaymentGateway paymentGateway) {
		em.remove(paymentGateway);
	}
	
    @Override
    protected FetchPlan handleSpecificFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphCommon.defaultPaymentGatewayFetchPlan());
        }
    }

}