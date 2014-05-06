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

import org.hoteia.qalingo.core.domain.OrderCustomer;

public interface OrderCustomerDao {

	OrderCustomer getOrderById(Long orderCustomerId, Object... params);

	OrderCustomer getOrderByOrderNum(String orderNum, Object... params);
	
	List<OrderCustomer> findOrders(Object... params);
	
	List<OrderCustomer> findOrdersByCustomerId(Long customerId, Object... params);
	
	OrderCustomer createNewOrder(OrderCustomer orderCustomer);
	
	OrderCustomer saveOrUpdateOrder(OrderCustomer orderCustomer);

	void deleteOrder(OrderCustomer orderCustomer);

}
