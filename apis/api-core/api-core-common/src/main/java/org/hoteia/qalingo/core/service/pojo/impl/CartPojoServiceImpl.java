/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.pojo.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.dozer.Mapper;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.CartItem;
import org.hoteia.qalingo.core.pojo.cart.CartPojo;
import org.hoteia.qalingo.core.service.CartService;
import org.hoteia.qalingo.core.service.pojo.CartPojoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("cartPojoService")
@Transactional(readOnly = true)
public class CartPojoServiceImpl implements CartPojoService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired 
    private Mapper dozerBeanMapper;
    
    @Autowired 
    private CartService cartService;

    public CartPojo handleCartMapping(final Cart cart) {
        
        // CLEAN GRAPH : EVICT LAZY / SESSION EXCEPTION WITH UNUSED INFORMATIONS
        Set<CartItem> cartItems = cart.getCartItems();
        for (Iterator<CartItem> iterator = cartItems.iterator(); iterator.hasNext();) {
            CartItem cartItem = (CartItem) iterator.next();

            cartItem.getProductSku().setProductMarketing(null);
            cartItem.getProductSku().setRetailers(null);

            cartItem.getProductMarketing().setProductBrand(null);
            cartItem.getProductMarketing().setProductMarketingType(null);
            cartItem.getProductMarketing().setProductSkus(null);
            cartItem.getProductMarketing().setProductAssociationLinks(null);
            cartItem.getProductMarketing().setDefaultCatalogCategory(null);

            cartItem.getCatalogCategory().setDefaultParentCatalogCategory(null);
            cartItem.getCatalogCategory().setCatalogCategories(null);
            cartItem.getCatalogCategory().setProductMarketings(null);
        }
        
        return cart == null ? null : dozerBeanMapper.map(cart, CartPojo.class);
    }

}