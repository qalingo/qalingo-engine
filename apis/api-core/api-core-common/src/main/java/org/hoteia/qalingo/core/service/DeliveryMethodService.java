/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import java.util.List;

import org.hoteia.qalingo.core.domain.DeliveryMethod;

public interface DeliveryMethodService {

    DeliveryMethod getDeliveryMethodById(Long deliveryMethodId, Object... params);

    DeliveryMethod getDeliveryMethodById(String deliveryMethodId, Object... params);

    DeliveryMethod getDeliveryMethodByCode(String code, Object... params);

    List<DeliveryMethod> findDeliveryMethods(Object... params);

    List<DeliveryMethod> findDeliveryMethodsByWarehouseId(Long warehouseId, Object... params);
    
    List<DeliveryMethod> findDeliveryMethodsByMarketAreaId(Long marketAreaId, Object... params);

    DeliveryMethod saveOrUpdateDeliveryMethod(DeliveryMethod deliveryMethod);

    void deleteDeliveryMethod(DeliveryMethod deliveryMethod);

}