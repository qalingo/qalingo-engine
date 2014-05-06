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

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.dao.CartDao;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.common.FetchPlanGraphCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("cartDao")
public class CartDaoImpl extends AbstractGenericDaoImpl implements CartDao {

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
	    if(em.contains(cart)){
	        cart.deleteAllCartItem();
	        em.remove(cart);
	    } else {
            cart.deleteAllCartItem();
	        em.remove(em.merge(cart));
	    }
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