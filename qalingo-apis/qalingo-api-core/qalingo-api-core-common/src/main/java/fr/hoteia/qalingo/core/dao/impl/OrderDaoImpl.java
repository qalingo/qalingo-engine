/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.OrderDao;
import fr.hoteia.qalingo.core.domain.Order;
import fr.hoteia.qalingo.core.domain.OrderNumber;

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

	public Order createNewOrder(Order order) {
		if(order.getDateCreate() == null){
			order.setDateCreate(new Date());
		}
		order.setDateUpdate(new Date());
		if(order.getId() == null){
			createNewOrderWithCorrectOrderNumber(order);
		}
		return order;
	}
	
	private Order createNewOrderWithCorrectOrderNumber(Order order){
		try {
			Session session = (Session) em.getDelegate();
			String hql = "FROM OrderNumber";
			Query query = session.createQuery(hql);
			OrderNumber orderNumber = (OrderNumber) query.uniqueResult();
			Integer previousLastOrderNumber = orderNumber.getLastOrderNumber();
			Integer newLastOrderNumber = new Integer(previousLastOrderNumber.intValue() + 1);

			order.setOrderNum("" + newLastOrderNumber);
			
			em.persist(order);
			
			hql = "UPDATE OrderNumber SET lastOrderNumber = :newLastOrderNumber WHERE lastOrderNumber = :previousLastOrderNumber";
			query = session.createQuery(hql);
	        query.setInteger("newLastOrderNumber", newLastOrderNumber);
	        query.setInteger("previousLastOrderNumber", previousLastOrderNumber);
	        int rowCount = query.executeUpdate();

	        if(rowCount == 0){
	        	em.getTransaction().rollback();
	        	createNewOrderWithCorrectOrderNumber(order);
	        }
			
		} catch (Exception e) {
			LOG.error("Failed to create a new Order with a specific OrderNumber increment", e);
		}
		return order;
	}
	
	public void updateOrder(Order order) {
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
