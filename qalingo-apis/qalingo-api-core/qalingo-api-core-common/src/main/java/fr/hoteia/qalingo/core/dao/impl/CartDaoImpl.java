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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.CartDao;
import fr.hoteia.qalingo.core.domain.Cart;

@Transactional
@Repository("cartDao")
public class CartDaoImpl extends AbstractGenericDaoImpl implements CartDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public Cart getCartById(Long cartId) {
		return em.find(Cart.class, cartId);
	}

	public void saveOrUpdateCart(Cart cart) {
		if(cart.getDateCreate() == null){
			cart.setDateCreate(new Date());
		}
		cart.setDateUpdate(new Date());
		if(cart.getId() == null){
			em.persist(cart);
		} else {
			em.merge(cart);
		}
	}

	public void deleteCart(Cart cart) {
		em.remove(cart);
	}

}
