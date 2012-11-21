/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.common.dao.PaymentGatewayDao;
import fr.hoteia.qalingo.core.common.domain.AbstractPaymentGateway;

@Transactional
@Repository("paymentGatewayDao")
public class PaymentGatewayDaoImpl extends AbstractGenericDaoImpl implements PaymentGatewayDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public AbstractPaymentGateway getPaymentGatewayById(Long paymentGatewayId) {
		return em.find(AbstractPaymentGateway.class, paymentGatewayId);
	}

	public AbstractPaymentGateway getPaymentGatewayByLoginOrEmail(String paymentGatewayCode) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM PaymentGateway WHERE code = :paymentGatewayCode)";
		Query query = session.createQuery(sql);
		query.setString("paymentGatewayCode", paymentGatewayCode);
		AbstractPaymentGateway paymentGateway = (AbstractPaymentGateway) query.uniqueResult();
		return paymentGateway;
	}
	
	public List<AbstractPaymentGateway> findPaymentGateways() {
		Session session = (Session) em.getDelegate();
		String sql = "FROM AbstractPaymentGateway ORDER BY name";
		Query query = session.createQuery(sql);
		List<AbstractPaymentGateway> paymentGateways = (List<AbstractPaymentGateway>) query.list();
		return paymentGateways;
	}

	public void saveOrUpdatePaymentGateway(AbstractPaymentGateway paymentGateway) {
		if(paymentGateway.getDateCreate() == null){
			paymentGateway.setDateCreate(new Date());
		}
		paymentGateway.setDateUpdate(new Date());
		if(paymentGateway.getId() == null){
			em.persist(paymentGateway);
		} else {
			em.merge(paymentGateway);
		}
	}

	public void deletePaymentGateway(AbstractPaymentGateway paymentGateway) {
		em.remove(paymentGateway);
	}

}
