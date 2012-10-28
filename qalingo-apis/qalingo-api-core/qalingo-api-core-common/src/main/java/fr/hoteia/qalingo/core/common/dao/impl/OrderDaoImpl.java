/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.common.dao.OrderDao;
import fr.hoteia.qalingo.core.common.domain.Order;

@Transactional
@Repository("orderDao")
public class OrderDaoImpl extends AbstractGenericDaoImpl implements OrderDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public Order getOrderById(Long orderId) {
		return em.find(Order.class, orderId);
	}

	public List<Order> findOrdersByCustomerId(Long customerId) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM Order WHERE customerId = :customerId";
		Query query = session.createQuery(sql);
		query.setLong("customerId", customerId);
		List<Order> orders = (List<Order>) query.list();
		return orders;
	}

	public void saveOrUpdateOrder(Order order) {
		if(order.getDateCreate() == null){
			order.setDateCreate(new Date());
		}
		order.setDateUpdate(new Date());
		if(order.getId() == null){
			em.persist(order);
		} else {
			em.merge(order);
		}
	}

	public void deleteOrder(Order order) {
		em.remove(order);
	}

}
