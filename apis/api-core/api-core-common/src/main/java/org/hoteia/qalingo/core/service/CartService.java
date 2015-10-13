/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.dao.CartDao;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.CartItem;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.Retailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("cartService")
@Transactional
public class CartService {

    @Autowired
    protected CartDao cartDao;

    @Autowired
    protected CatalogCategoryService catalogCategoryService;

    @Autowired
    protected ProductService productService;

    @Autowired
    protected DeliveryMethodService deliveryMethodService;
    
    public void addProductSkuToCart(Cart cart, final String virtualCatalogCode, final String catalogCategoryCode, final String productSkuCode, final int quantity) throws Exception {
        addProductSkuToCart(cart, null, virtualCatalogCode, catalogCategoryCode, productSkuCode, quantity);
    }
    
    public Cart addProductSkuToCart(Cart cart, Retailer retailer, final String virtualCatalogCode, final String catalogCategoryCode, final String productSkuCode, final int quantity) throws Exception {
        int finalQuantity = quantity;
        if (cart != null) {
            Set<CartItem> cartItems = cart.getCartItems();
            for (Iterator<CartItem> iterator = cartItems.iterator(); iterator.hasNext();) {
                CartItem cartItem = (CartItem) iterator.next();
                if (cartItem.getProductSku().getCode().equalsIgnoreCase(productSkuCode)
                        && cartItem.getRetailerId().equals(retailer)) {
                    finalQuantity = finalQuantity + cartItem.getQuantity();
                }
            }
        }
        cart = updateCartItem(cart, retailer, virtualCatalogCode, catalogCategoryCode, productSkuCode, finalQuantity);
        return cart;
    }
    
    public Cart updateCartItem(Cart cart, final String productSkuCode, final int quantity) throws Exception {
        return updateCartItem(cart, null, null, null, productSkuCode, quantity);
    }
    
    public Cart updateCartItem(Cart cart, Retailer retailer, final String virtualCatalogCode, final String catalogCategoryCode, final String productSkuCode, final int quantity) throws Exception {
        Set<CartItem> cartItems = cart.getCartItems();
        boolean productSkuIsNew = true;
        for (Iterator<CartItem> iterator = cartItems.iterator(); iterator.hasNext();) {
            CartItem cartItem = (CartItem) iterator.next();
            if (cartItem.getProductSku().getCode().equalsIgnoreCase(productSkuCode)) {
                cartItem.setQuantity(quantity);
                productSkuIsNew = false;
            }
        }
        if (productSkuIsNew) {
            final ProductSku productSku = productService.getProductSkuByCode(productSkuCode);
            if (productSku != null) {
                CartItem cartItem = new CartItem();
                cartItem.setProductSku(productSku);

                final ProductMarketing reloadedProductMarketing = productService.getProductMarketingByCode(productSku.getProductMarketing().getCode());
                cartItem.setProductMarketing(reloadedProductMarketing);
                cartItem.setQuantity(quantity);
                if(retailer != null){
                    cartItem.setRetailerId(retailer.getId());
                }

                if (StringUtils.isNotEmpty(catalogCategoryCode)) {
                    final CatalogCategoryVirtual defaultVirtualCatalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(catalogCategoryCode, virtualCatalogCode);
                    cartItem.setCatalogCategory(defaultVirtualCatalogCategory);
                } else {
                    final List<CatalogCategoryVirtual> catalogCategories = catalogCategoryService.findVirtualCategoriesByProductSkuId(productSku.getId());
                    final CatalogCategoryVirtual defaultVirtualCatalogCategory = productService.getDefaultVirtualCatalogCategory(reloadedProductMarketing, catalogCategories, true);
                    cartItem.setCatalogCategory(defaultVirtualCatalogCategory);
                }
                cart.getCartItems().add(cartItem);
            } else {
                // TODO : throw ??
            }
        }
        return saveOrUpdateCart(cart);
    }
    
    public Cart deleteCartItem(Cart cart, final String productSkuCode) throws Exception {
        return deleteCartItem(cart, null, productSkuCode);
    }
    
    public Cart deleteCartItem(Cart cart, Retailer retailer, final String productSkuCode) throws Exception {
        if(cart != null){
            Set<CartItem> cartItems = new HashSet<CartItem>(cart.getCartItems());
            for (Iterator<CartItem> iterator = cart.getCartItems().iterator(); iterator.hasNext();) {
                CartItem cartItem = (CartItem) iterator.next();
                if (cartItem.getProductSku().getCode().equalsIgnoreCase(productSkuCode)
                        && cartItem.getRetailerId().equals(retailer)) {
                    cartItems.remove(cartItem);
                }
            }
            cart.setCartItems(cartItems);
        }
        return saveOrUpdateCart(cart);
    }
      
    public Cart setShippingAddress(Cart cart, Customer customer, Long customerAddressId) throws Exception {
        if(customer.getAddress(customerAddressId) != null){
            cart.setShippingAddressId(customerAddressId);
        }
        return saveOrUpdateCart(cart);
    }

    public Cart setBillingAddress(Cart cart, Customer customer, Long customerAddressId) throws Exception {
        if(customer.getAddress(customerAddressId) != null){
            cart.setShippingAddressId(customerAddressId);
        }
        return saveOrUpdateCart(cart);
    }
    
    public Cart setDeliveryMethod(Cart cart, String deliveryMethodCode) throws Exception {
        if(cart.getDeliveryMethods().isEmpty()){
            cart.getDeliveryMethods().add(deliveryMethodService.getDeliveryMethodByCode(deliveryMethodCode));
        } else {
            cart.getDeliveryMethods().clear();
            cart.getDeliveryMethods().add(deliveryMethodService.getDeliveryMethodByCode(deliveryMethodCode));
        }
        return saveOrUpdateCart(cart);
    }
    
    public Cart newCustomerCart(final MarketArea marketArea, Customer customer) {
        Cart cart = new Cart();
        cart.setMarketAreaId(marketArea.getId());
        cart.setCustomerId(customer.getId());
        saveOrUpdateCart(cart);
        return cart;
    }
    
    public Cart newGuestCart(final MarketArea marketArea) {
        Cart cart = new Cart();
        cart.setMarketAreaId(marketArea.getId());
        saveOrUpdateCart(cart);
        return cart;
    }
    
    public Cart getCartById(final Long cartId, Object... params) {
        return cartDao.getCartById(cartId, params);
    }

    public Cart getCartByMarketAreaIdAndCustomerId(final Long marketAreaId, final Long customerId, Object... params) {
        return cartDao.getCartByMarketAreaIdAndCustomerId(marketAreaId, customerId, params);
    }
    
    public Cart getCartById(final String rawCartId, Object... params) {
        long cartId = -1;
        try {
            cartId = Long.parseLong(rawCartId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getCartById(cartId, params);
    }

    public Cart saveOrUpdateCart(Cart cart) {
        return cartDao.saveOrUpdateCart(cart);
    }

    public void deleteCart(Cart cart) {
        cartDao.deleteCart(cart);
    }

}