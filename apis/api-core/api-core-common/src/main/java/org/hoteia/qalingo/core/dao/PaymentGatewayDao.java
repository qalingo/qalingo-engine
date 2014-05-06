/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao;

import java.util.List;

import org.hoteia.qalingo.core.domain.AbstractPaymentGateway;
import org.hoteia.qalingo.core.domain.PaymentGatewayOption;

public interface PaymentGatewayDao {

    AbstractPaymentGateway getPaymentGatewayById(Long paymentGatewayId, Object... params);

    AbstractPaymentGateway getPaymentGatewayByCode(String paymentGatewayCode, Object... params);

    List<AbstractPaymentGateway> findPaymentGateways(Object... params);

    List<PaymentGatewayOption> findPaymentGatewayOptions();
    
    AbstractPaymentGateway saveOrUpdatePaymentGateway(AbstractPaymentGateway paymentGateway);

    void deletePaymentGateway(AbstractPaymentGateway paymentGateway);

}