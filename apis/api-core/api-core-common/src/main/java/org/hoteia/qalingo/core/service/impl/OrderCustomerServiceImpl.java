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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.dao.OrderCustomerDao;
import org.hoteia.qalingo.core.domain.OrderCustomer;
import org.hoteia.qalingo.core.service.OrderCustomerService;

@Service("orderCustomerService")
@Transactional
public class OrderCustomerServiceImpl implements OrderCustomerService {

    @Autowired
    private OrderCustomerDao orderDao;

    public OrderCustomer getOrderById(final Long orderCustomerId, Object... params) {
        return orderDao.getOrderById(orderCustomerId, params);
    }
    
    public OrderCustomer getOrderById(final String rawOrderCustomerId, Object... params) {
        long orderId = -1;
        try {
            orderId = Long.parseLong(rawOrderCustomerId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getOrderById(orderId, params);
    }

    public OrderCustomer getOrderByOrderNum(final String orderNum, Object... params) {
        return orderDao.getOrderByOrderNum(orderNum, params);
    }

    public List<OrderCustomer> findOrdersByCustomerId(final String customerId, Object... params) {
        return orderDao.findOrdersByCustomerId(Long.parseLong(customerId), params);
    }

    public List<OrderCustomer> findOrders(Object... params) {
        return orderDao.findOrders(params);
    }

    public OrderCustomer createNewOrder(final OrderCustomer orderCustomer) {
        return orderDao.createNewOrder(orderCustomer);
    }

    public void saveOrUpdateOrder(final OrderCustomer orderCustomer) {
        orderDao.saveOrUpdateOrder(orderCustomer);
    }

    public void deleteOrder(final OrderCustomer orderCustomer) {
        orderDao.deleteOrder(orderCustomer);
    }

}