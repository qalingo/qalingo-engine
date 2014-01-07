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
import org.hoteia.qalingo.core.dao.PaymentGatewayDao;
import org.hoteia.qalingo.core.domain.AbstractPaymentGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("paymentGatewayDao")
public class PaymentGatewayDaoImpl extends AbstractGenericDaoImpl implements PaymentGatewayDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public AbstractPaymentGateway getPaymentGatewayById(final Long paymentGatewayId) {
        Criteria criteria = createDefaultCriteria(AbstractPaymentGateway.class);
        
        addDefaultFetch(criteria);

        criteria.add(Restrictions.eq("id", paymentGatewayId));
        AbstractPaymentGateway paymentGateway = (AbstractPaymentGateway) criteria.uniqueResult();
        return paymentGateway;
	}

	public AbstractPaymentGateway getPaymentGatewayByLoginOrEmail(final String paymentGatewayCode) {
        Criteria criteria = createDefaultCriteria(AbstractPaymentGateway.class);
        
        addDefaultFetch(criteria);

        criteria.add(Restrictions.eq("code", paymentGatewayCode));
        AbstractPaymentGateway paymentGateway = (AbstractPaymentGateway) criteria.uniqueResult();
		return paymentGateway;
	}
	
	public List<AbstractPaymentGateway> findPaymentGateways() {
        Criteria criteria = createDefaultCriteria(AbstractPaymentGateway.class);

        addDefaultFetch(criteria);
        
        criteria.addOrder(Order.asc("name"));

        @SuppressWarnings("unchecked")
        List<AbstractPaymentGateway> paymentGateways = criteria.list();
		return paymentGateways;
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
	
    private void addDefaultFetch(Criteria criteria) {
        criteria.setFetchMode("paymentGatewayAttributes", FetchMode.JOIN); 
    }

}