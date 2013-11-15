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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.dao.OrderDao;
import org.hoteia.qalingo.core.domain.Order;
import org.hoteia.qalingo.core.service.OrderService;

@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

	public Order getOrderById(String rawOrderId) {
		long orderId = -1;
		try {
			orderId = Long.parseLong(rawOrderId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return orderDao.getOrderById(orderId);
	}

	public Order getOrderByCode(String code) {
		return orderDao.getOrderByCode(code);
	}
	
	public List<Order> findOrdersByCustomerId(String customerId) {
		return orderDao.findOrdersByCustomerId(Long.parseLong(customerId));
	}
	
	public List<Order> findOrders() {
		return orderDao.findOrders();
	}

	public Order createNewOrder(Order order) {
		return orderDao.createNewOrder(order);
	}

	public void updateOrder(Order order) {
		orderDao.updateOrder(order);
	}

	public void deleteOrder(Order order) {
		orderDao.deleteOrder(order);
	}

}
