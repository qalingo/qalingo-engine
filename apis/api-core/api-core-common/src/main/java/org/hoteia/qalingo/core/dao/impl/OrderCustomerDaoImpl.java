/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.RollbackException;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.dao.OrderCustomerDao;
import org.hoteia.qalingo.core.domain.OrderCustomer;
import org.hoteia.qalingo.core.domain.OrderNumber;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.common.FetchPlanGraphCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("orderCustomerDao")
public class OrderCustomerDaoImpl extends AbstractGenericDaoImpl implements OrderCustomerDao {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public OrderCustomer getOrderById(final Long orderCustomerId, Object... params) {
        Criteria criteria = createDefaultCriteria(OrderCustomer.class);

        FetchPlan fetchPlan =  handleSpecificFetchMode(criteria, params);

        criteria.add(Restrictions.eq("id", orderCustomerId));
        OrderCustomer orderCustomer = (OrderCustomer) criteria.uniqueResult();
        if(orderCustomer != null){
            orderCustomer.setFetchPlan(fetchPlan);
        }
        return orderCustomer;
    }

    public OrderCustomer getOrderByOrderNum(final String orderNum, Object... params) {
        Criteria criteria = createDefaultCriteria(OrderCustomer.class);

        FetchPlan fetchPlan = handleSpecificFetchMode(criteria, params);

        criteria.add(Restrictions.eq("orderNum", orderNum));
        OrderCustomer orderCustomer = (OrderCustomer) criteria.uniqueResult();
        if(orderCustomer != null){
            orderCustomer.setFetchPlan(fetchPlan);
        }
        return orderCustomer;
    }

    public List<OrderCustomer> findOrders(Object... params) {
        Criteria criteria = createDefaultCriteria(OrderCustomer.class);

        handleSpecificFetchMode(criteria, params);

        criteria.addOrder(Order.asc("dateCreate"));

        @SuppressWarnings("unchecked")
        List<OrderCustomer> orderCustomers = criteria.list();

        return orderCustomers;
    }

    public List<OrderCustomer> findOrdersByCustomerId(final Long customerId, Object... params) {
        Criteria criteria = createDefaultCriteria(OrderCustomer.class);

        handleSpecificFetchMode(criteria, params);

        criteria.add(Restrictions.eq("customerId", customerId));

        criteria.addOrder(Order.asc("dateCreate"));

        @SuppressWarnings("unchecked")
        List<OrderCustomer> orderCustomers = criteria.list();

        return orderCustomers;
    }

    public OrderCustomer createNewOrder(OrderCustomer orderCustomer) {
        if (orderCustomer.getDateCreate() == null) {
            orderCustomer.setDateCreate(new Date());
        }
        orderCustomer.setDateUpdate(new Date());
        if (orderCustomer.getId() == null) {
            orderCustomer = createNewOrderWithRightOrderNumber(orderCustomer);
        }
        return orderCustomer;
    }

    private OrderCustomer createNewOrderWithRightOrderNumber(final OrderCustomer orderCustomer) {
        OrderCustomer mergedOrSavedOrderCustomer = null;
        try {
            Session session = (Session) em.getDelegate();
            String hql = "FROM OrderNumber";
            Query query = session.createQuery(hql);
            OrderNumber orderNumber = (OrderNumber) query.uniqueResult();
            Integer previousLastOrderNumber = orderNumber.getLastOrderNumber();
            Integer newLastOrderNumber = new Integer(previousLastOrderNumber.intValue() + 1);

            orderCustomer.setPrefixHashFolder(UUID.randomUUID().toString());
            orderCustomer.setOrderNum("" + newLastOrderNumber);

//            if (orderCustomer.getId() != null) {
//                if (em.contains(orderCustomer)) {
//                    em.refresh(orderCustomer);
//                }
//                mergedOrSavedOrderCustomer = em.merge(orderCustomer);
//                em.flush();
//                return orderCustomer;
//            } else {
//                em.persist(orderCustomer);
//                mergedOrSavedOrderCustomer = orderCustomer;
//            }
            mergedOrSavedOrderCustomer = em.merge(orderCustomer);

            hql = "UPDATE OrderNumber SET lastOrderNumber = :newLastOrderNumber WHERE lastOrderNumber = :previousLastOrderNumber";
            query = session.createQuery(hql);
            query.setInteger("newLastOrderNumber", newLastOrderNumber);
            query.setInteger("previousLastOrderNumber", previousLastOrderNumber);
            int rowCount = query.executeUpdate();

            if (rowCount == 0) {
                em.getTransaction().rollback();
                mergedOrSavedOrderCustomer = createNewOrderWithRightOrderNumber(orderCustomer);
            }

        } catch (RollbackException e) {
            logger.debug("Failed to create a new Order with a specific OrderNumber increment", e);
        } catch (Exception e) {
            logger.error("Failed to create a new Order with a specific OrderNumber increment", e);
        }
        return mergedOrSavedOrderCustomer;
    }

    public OrderCustomer saveOrUpdateOrder(OrderCustomer orderCustomer) {
        if (orderCustomer.getDateCreate() == null) {
            orderCustomer.setDateCreate(new Date());
        }
        orderCustomer.setDateUpdate(new Date());
//        if (orderCustomer.getId() != null) {
//            if(em.contains(orderCustomer)){
//                em.refresh(orderCustomer);
//            }
//            OrderCustomer mergedOrderCustomer = em.merge(orderCustomer);
//            em.flush();
//            return mergedOrderCustomer;
//        } else {
//            em.persist(orderCustomer);
//            return orderCustomer;
//        }
        
        if (em.contains(orderCustomer)) {
            em.refresh(orderCustomer);
        }
        OrderCustomer mergedOrderCustomer = em.merge(orderCustomer);
        em.flush();
        return mergedOrderCustomer;
    }

    public void deleteOrder(OrderCustomer orderCustomer) {
        em.remove(orderCustomer);
    }

    @Override
    protected FetchPlan handleSpecificFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphCommon.defaultOrderCustomerFetchPlan());
        }
    }
    
}