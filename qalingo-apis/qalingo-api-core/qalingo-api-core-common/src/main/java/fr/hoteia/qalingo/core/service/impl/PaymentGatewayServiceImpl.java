/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.PaymentGatewayDao;
import fr.hoteia.qalingo.core.domain.AbstractPaymentGateway;
import fr.hoteia.qalingo.core.service.PaymentGatewayService;

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
	
	public List<AbstractPaymentGateway> findPaymentGateways() {
		return paymentGatewayDao.findPaymentGateways();
	}


	public void saveOrUpdatePaymentGateway(AbstractPaymentGateway paymentGateway) {
		paymentGatewayDao.saveOrUpdatePaymentGateway(paymentGateway);
	}

	public void deletePaymentGateway(AbstractPaymentGateway paymentGateway) {
		paymentGatewayDao.deletePaymentGateway(paymentGateway);
	}

}
