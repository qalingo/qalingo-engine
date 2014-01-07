/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.impl;

import java.util.List;

import org.hoteia.qalingo.core.dao.DeliveryMethodDao;
import org.hoteia.qalingo.core.domain.DeliveryMethod;
import org.hoteia.qalingo.core.service.DeliveryMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("deliveryMethodService")
@Transactional
public class DeliveryMethodServiceImpl implements DeliveryMethodService {

    @Autowired
    private DeliveryMethodDao deliveryMethodDao;

    public DeliveryMethod getDeliveryMethodById(final Long deliveryMethodId) {
        return deliveryMethodDao.getDeliveryMethodById(deliveryMethodId);
    }

    public DeliveryMethod getDeliveryMethodById(final String rawDeliveryMethodId) {
        long deliveryMethodId = -1;
        try {
            deliveryMethodId = Long.parseLong(rawDeliveryMethodId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getDeliveryMethodById(deliveryMethodId);
    }

    public DeliveryMethod getDeliveryMethodByCode(String code) {
        return deliveryMethodDao.getDeliveryMethodByCode(code);
    }

    public List<DeliveryMethod> findDeliveryMethods() {
        return deliveryMethodDao.findDeliveryMethods();
    }

    public void saveOrUpdateDeliveryMethod(DeliveryMethod deliveryMethod) {
        deliveryMethodDao.saveOrUpdateDeliveryMethod(deliveryMethod);
    }

    public void deleteDeliveryMethod(DeliveryMethod deliveryMethod) {
        deliveryMethodDao.deleteDeliveryMethod(deliveryMethod);
    }

}