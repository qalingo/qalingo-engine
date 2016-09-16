/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 */
package org.hoteia.qalingo.core.service;

import java.util.List;

import org.hoteia.qalingo.core.dao.OrderPurchaseDao;
import org.hoteia.qalingo.core.domain.OrderItem;
import org.hoteia.qalingo.core.domain.OrderPurchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("orderPurchaseService")
@Transactional
public class OrderPurchaseService {

    @Autowired
    protected OrderPurchaseDao orderDao;

    public OrderPurchase getOrderById(final Long orderPurchaseId, Object... params) {
        return orderDao.getOrderById(orderPurchaseId, params);
    }

    public OrderPurchase getOrderById(final String rawOrderPurchaseId, Object... params) {
        long orderId = -1;
        try {
            orderId = Long.parseLong(rawOrderPurchaseId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getOrderById(orderId, params);
    }

    public OrderPurchase getOrderByOrderNum(final String orderNum, Object... params) {
        return orderDao.getOrderByOrderNum(orderNum, params);
    }

    public List<OrderPurchase> findOrdersByCustomerId(final String customerId, Object... params) {
        return orderDao.findOrdersByCustomerId(Long.parseLong(customerId), params);
    }

    public List<OrderPurchase> findOrders(Object... params) {
        return orderDao.findOrders(params);
    }

    public List<OrderItem> findOrderItemsByStoreId(final Long storeId, Object... params) {
        return orderDao.findOrderItemsByStoreId(storeId, params);
    }

    public List<OrderItem> findOrderItemsByStoreId(final String storeId, Object... params) {
        try {
            return orderDao.findOrderItemsByStoreId(Long.parseLong(storeId), params);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public OrderItem findOrderItemById(final Long id, Object... params) {
        return orderDao.findOrderItemById(id, params);
    }

    public List<OrderPurchase> findOrdersByStatus(final String status, Object... params) {
        return orderDao.findOrdersByStatus(status, params);
    }

    public OrderPurchase createNewOrder(final OrderPurchase orderPurchase) {
        return orderDao.createNewOrder(orderPurchase);
    }

    public OrderPurchase saveOrUpdateOrder(final OrderPurchase orderPurchase) {
        return orderDao.saveOrUpdateOrder(orderPurchase);
    }

    public void deleteOrder(final OrderPurchase orderPurchase) {
        orderDao.deleteOrder(orderPurchase);
    }

}