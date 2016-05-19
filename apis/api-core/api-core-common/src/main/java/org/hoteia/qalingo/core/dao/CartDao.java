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

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.common.FetchPlanGraphCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("cartDao")
public class CartDao extends AbstractGenericDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public Cart getCartById(final Long cartId, Object... params) {
        Criteria criteria = createDefaultCriteria(Cart.class);
        
        FetchPlan fetchPlan = handleSpecificFetchMode(criteria);
        
        criteria.add(Restrictions.eq("id", cartId));
        Cart cart = (Cart) criteria.uniqueResult();
        if(cart != null){
            cart.setFetchPlan(fetchPlan);
        }
        return cart;
	}
	
    public Cart getCartByMarketAreaIdAndCustomerId(final Long marketAreaId, final Long customerId, Object... params) {
        Criteria criteria = createDefaultCriteria(Cart.class);
        
        FetchPlan fetchPlan = handleSpecificFetchMode(criteria);
        
        criteria.add(Restrictions.eq("marketAreaId", marketAreaId));
        criteria.add(Restrictions.eq("customerId", customerId));

        Cart cart = (Cart) criteria.uniqueResult();
        if(cart != null){
            cart.setFetchPlan(fetchPlan);
        }
        return cart;
    }

	public Cart saveOrUpdateCart(final Cart cart) {
		if(cart.getDateCreate() == null){
			cart.setDateCreate(new Date());
		}
		cart.setDateUpdate(new Date());
        if (cart.getId() != null) {
            if(em.contains(cart)){
                em.refresh(cart);
            }
            Cart mergedCart = em.merge(cart);
            em.flush();
            return mergedCart;
        } else {
            em.persist(cart);
            return cart;
        }
	}

	public void deleteCart(final Cart cart) {
	    cart.deleteAllCartItem();
	    em.remove(em.contains(cart) ? cart : em.merge(cart));
	}
	
    public int deleteCart(final Timestamp before) {
        Session session = (Session) em.getDelegate();
        String sql = "FROM Cart WHERE dateCreate <= :before";
        Query query = session.createQuery(sql);
        query.setTimestamp("before", before);
        List<Cart> sessions = (List<Cart>) query.list();
        if (sessions != null) {
            for (Iterator<Cart> iterator = sessions.iterator(); iterator.hasNext();) {
                Cart cart = (Cart) iterator.next();
                deleteCart(cart);
            }
            return sessions.size();
        }
        return 0;
    }
	
    @Override
    protected FetchPlan handleSpecificFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphCommon.defaultCartFetchPlan());
        }
    }
	   
}