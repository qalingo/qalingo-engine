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

import java.util.Date;
import java.util.List;

import javax.persistence.RollbackException;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hoteia.qalingo.core.domain.*;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.order.FetchPlanGraphOrder;
import org.hoteia.qalingo.core.util.CoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("orderPurchaseDao")
public class OrderPurchaseDao extends AbstractGenericDao {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public OrderPurchase getOrderById(final Long orderPurchaseId, Object... params) {
        Criteria criteria = createDefaultCriteria(OrderPurchase.class);

        FetchPlan fetchPlan =  handleSpecificOrderFetchMode(criteria, params);

        criteria.add(Restrictions.eq("id", orderPurchaseId));
        OrderPurchase orderPurchase = (OrderPurchase) criteria.uniqueResult();
        if(orderPurchase != null){
            orderPurchase.setFetchPlan(fetchPlan);
        }
        return orderPurchase;
    }

    public OrderPurchase getOrderByOrderNum(final String orderNum, Object... params) {
        Criteria criteria = createDefaultCriteria(OrderPurchase.class);

        FetchPlan fetchPlan = handleSpecificOrderFetchMode(criteria, params);

        criteria.add(Restrictions.eq("orderNum", orderNum));
        OrderPurchase orderPurchase = (OrderPurchase) criteria.uniqueResult();
        if(orderPurchase != null){
            orderPurchase.setFetchPlan(fetchPlan);
        }
        return orderPurchase;
    }

    public List<OrderPurchase> findOrders(Object... params) {
        Criteria criteria = createDefaultCriteria(OrderPurchase.class);

        handleSpecificOrderFetchMode(criteria, params);

        criteria.addOrder(Order.asc("dateCreate"));

        @SuppressWarnings("unchecked")
        List<OrderPurchase> orderPurchases = criteria.list();

        return orderPurchases;
    }
    
    public List<OrderItem> findOrderItemsByStoreId(final Long storeId, Object... params) {
        Criteria criteria = createDefaultCriteria(OrderItem.class);
        handleSpecificOrderFetchMode(criteria, params);
        criteria.add(Restrictions.eq(OrderItem_.storeId.getName(), storeId));
        @SuppressWarnings("unchecked")
        List<OrderItem> orderItems = criteria.list();
        return orderItems;
    }

    public OrderItem findOrderItemById(final Long id, Object... params) {
        Criteria criteria = createDefaultCriteria(OrderItem.class);
        handleSpecificOrderFetchMode(criteria, params);
        criteria.add(Restrictions.eq(OrderItem_.id.getName(), id));
        return (OrderItem) criteria.uniqueResult();
    }

    public List<OrderPurchase> findOrdersByStatus(final String status, Object... params) {
        Criteria criteria = createDefaultCriteria(OrderItem.class);
        handleSpecificOrderFetchMode(criteria, params);
        criteria.add(Restrictions.eq(OrderPurchase_.status.getName(), status));
        @SuppressWarnings("unchecked")
        List<OrderPurchase> orderPurchases = criteria.list();
        return orderPurchases;
    }

    public List<OrderPurchase> findOrdersByCustomerId(final Long customerId, Object... params) {
        Criteria criteria = createDefaultCriteria(OrderPurchase.class);
        handleSpecificOrderFetchMode(criteria, params);
        criteria.createAlias("customer", "customer", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("customer.id", customerId));
        criteria.addOrder(Order.asc("dateCreate"));
        @SuppressWarnings("unchecked")
        List<OrderPurchase> orderPurchases = criteria.list();
        return orderPurchases;
    }

    public OrderPurchase createNewOrder(OrderPurchase orderPurchase) {
        if (orderPurchase.getDateCreate() == null) {
            orderPurchase.setDateCreate(new Date());
        }
        orderPurchase.setDateUpdate(new Date());
        if (orderPurchase.getId() == null) {
            orderPurchase = createNewOrderWithRightOrderNumber(orderPurchase);
        }
        return orderPurchase;
    }

    protected OrderPurchase createNewOrderWithRightOrderNumber(final OrderPurchase orderPurchase) {
        OrderPurchase mergedOrSavedOrderPurchase = null;
        try {
            Session session = (Session) em.getDelegate();
            String hql = "FROM OrderNumber";
            Query query = session.createQuery(hql);
            OrderNumber orderNumber = (OrderNumber) query.uniqueResult();
            Integer previousLastOrderNumber = orderNumber.getLastOrderNumber();
            Integer newLastOrderNumber = orderNumber.getLastOrderNumber() + 1;

            orderPurchase.setPrefixHashFolder(CoreUtil.generateEntityCode());
            orderPurchase.setOrderNum("" + newLastOrderNumber);

//            if (orderPurchase.getId() != null) {
//                if (em.contains(orderPurchase)) {
//                    em.refresh(orderPurchase);
//                }
//                mergedOrSavedOrderPurchase = em.merge(orderPurchase);
//                em.flush();
//                return orderPurchase;
//            } else {
//                em.persist(orderPurchase);
//                mergedOrSavedOrderPurchase = orderPurchase;
//            }
            mergedOrSavedOrderPurchase = em.merge(orderPurchase);

            hql = "UPDATE OrderNumber SET lastOrderNumber = :newLastOrderNumber WHERE lastOrderNumber = :previousLastOrderNumber";
            query = session.createQuery(hql);
            query.setInteger("newLastOrderNumber", newLastOrderNumber);
            query.setInteger("previousLastOrderNumber", previousLastOrderNumber);
            int rowCount = query.executeUpdate();

            if (rowCount == 0) {
                em.getTransaction().rollback();
                mergedOrSavedOrderPurchase = createNewOrderWithRightOrderNumber(orderPurchase);
            }

        } catch (RollbackException e) {
            logger.debug("Failed to create a new Order with a specific OrderNumber increment", e);
        } catch (Exception e) {
            logger.error("Failed to create a new Order with a specific OrderNumber increment", e);
        }
        return mergedOrSavedOrderPurchase;
    }

    public OrderPurchase saveOrUpdateOrder(OrderPurchase orderPurchase) {
        if (orderPurchase.getDateCreate() == null) {
            orderPurchase.setDateCreate(new Date());
        }
        orderPurchase.setDateUpdate(new Date());
//        if (orderPurchase.getId() != null) {
//            if(em.contains(orderPurchase)){
//                em.refresh(orderPurchase);
//            }
//            OrderPurchase mergedOrderPurchase = em.merge(orderPurchase);
//            em.flush();
//            return mergedOrderPurchase;
//        } else {
//            em.persist(orderPurchase);
//            return orderPurchase;
//        }
        
        if (em.contains(orderPurchase)) {
            em.refresh(orderPurchase);
        }
        OrderPurchase mergedOrderPurchase = em.merge(orderPurchase);
        em.flush();
        return mergedOrderPurchase;
    }

    public void deleteOrder(OrderPurchase orderPurchase) {
        em.remove(em.contains(orderPurchase) ? orderPurchase : em.merge(orderPurchase));
    }

    protected FetchPlan handleSpecificOrderFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphOrder.defaultOrderPurchaseFetchPlan());
        }
    }
    
    protected FetchPlan handleSpecificOrderItemFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphOrder.defaultOrderItemFetchPlan());
        }
    }
    
}