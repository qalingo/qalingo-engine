/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.common.dao.PaymentGatewayDao;
import fr.hoteia.qalingo.core.common.domain.AbstractPaymentGateway;
import fr.hoteia.qalingo.core.common.service.PaymentGatewayService;

@Service("paymentGatewayService")
@Transactional
public class PaymentGatewayServiceImpl implements PaymentGatewayService {

	@Autowired
	private PaymentGatewayDao paymentGatewayDao;

	public AbstractPaymentGateway getPaymentGatewayById(String rawPaymentGatewayId) {
		long paymentGatewayId = -1;
		try {
			paymentGatewayId = Long.parseLong(rawPaymentGatewayId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return paymentGatewayDao.getPaymentGatewayById(paymentGatewayId);
	}
	
	public AbstractPaymentGateway getPaymentGatewayByLoginOrEmail(String usernameOrEmail) {
		return paymentGatewayDao.getPaymentGatewayByLoginOrEmail(usernameOrEmail);
	}

	public List<AbstractPaymentGateway> findPaymentGateway(AbstractPaymentGateway criteria) {
		return paymentGatewayDao.findByExample(criteria);
	}

	public void saveOrUpdatePaymentGateway(AbstractPaymentGateway paymentGateway) {
		paymentGatewayDao.saveOrUpdatePaymentGateway(paymentGateway);
	}

	public void deletePaymentGateway(AbstractPaymentGateway paymentGateway) {
		paymentGatewayDao.deletePaymentGateway(paymentGateway);
	}

}
