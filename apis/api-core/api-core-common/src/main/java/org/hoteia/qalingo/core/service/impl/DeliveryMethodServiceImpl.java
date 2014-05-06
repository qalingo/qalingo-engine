/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
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

    public DeliveryMethod getDeliveryMethodById(final Long deliveryMethodId, Object... params) {
        return deliveryMethodDao.getDeliveryMethodById(deliveryMethodId, params);
    }

    public DeliveryMethod getDeliveryMethodById(final String rawDeliveryMethodId, Object... params) {
        long deliveryMethodId = -1;
        try {
            deliveryMethodId = Long.parseLong(rawDeliveryMethodId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getDeliveryMethodById(deliveryMethodId, params);
    }

    public DeliveryMethod getDeliveryMethodByCode(final String code, Object... params) {
        return deliveryMethodDao.getDeliveryMethodByCode(code, params);
    }

    public List<DeliveryMethod> findDeliveryMethods(Object... params) {
        return deliveryMethodDao.findDeliveryMethods(params);
    } 

    public List<DeliveryMethod> findDeliveryMethodsByWarehouseId(Long warehouseId, Object... params) {
        return deliveryMethodDao.findDeliveryMethodsByWarehouseId(warehouseId, params);
    }

    public List<DeliveryMethod> findDeliveryMethodsByMarketAreaId(Long marketAreaId, Object... params) {
        return deliveryMethodDao.findDeliveryMethodsByMarketAreaId(marketAreaId, params);
    }

    public DeliveryMethod saveOrUpdateDeliveryMethod(DeliveryMethod deliveryMethod) {
        return deliveryMethodDao.saveOrUpdateDeliveryMethod(deliveryMethod);
    }

    public void deleteDeliveryMethod(DeliveryMethod deliveryMethod) {
        deliveryMethodDao.deleteDeliveryMethod(deliveryMethod);
    }

}