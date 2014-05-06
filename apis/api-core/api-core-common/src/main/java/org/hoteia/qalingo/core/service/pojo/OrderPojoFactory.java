/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.pojo;

import org.dozer.Mapper;
import org.hoteia.qalingo.core.domain.OrderCustomer;
import org.hoteia.qalingo.core.pojo.OrderCustomerPojo;
import org.hoteia.qalingo.core.service.pojo.OrderPojoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("orderPojoService")
@Transactional(readOnly = true)
public class OrderPojoFactory {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired 
    private Mapper dozerBeanMapper;
    
    public OrderCustomerPojo handleOrderMapping(final OrderCustomer order) {
        return order == null ? null : dozerBeanMapper.map(order, OrderCustomerPojo.class);
    }
    
}