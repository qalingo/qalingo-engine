/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 */
package org.hoteia.qalingo.core.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.CartItem;
import org.hoteia.qalingo.core.domain.CartItemTax;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.CustomerAddress;
import org.hoteia.qalingo.core.domain.DeliveryMethod;
import org.hoteia.qalingo.core.domain.OrderAddress;
import org.hoteia.qalingo.core.domain.OrderItem;
import org.hoteia.qalingo.core.domain.OrderPurchase;
import org.hoteia.qalingo.core.domain.OrderShipment;
import org.hoteia.qalingo.core.domain.OrderTax;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.Tax;
import org.hoteia.qalingo.core.domain.enumtype.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("checkoutService")
@Transactional
public class CheckoutService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    protected OrderPurchaseService orderPurchaseService;

    @Autowired
    protected CartService cartService;

    @Autowired
    protected CustomerService customerService;
    
    public OrderPurchase checkoutB2C(final Customer customer, final Cart cart) throws Exception {
        Long marketAreaId = cart.getMarketAreaId();
        Set<DeliveryMethod> deliveryMethods = cart.getDeliveryMethods();
        Set<CartItem> cartItems = cart.getCartItems();

        OrderPurchase orderPurchase = new OrderPurchase();
        // ORDER NUMBER IS CREATE BY DAO

        orderPurchase.setStatus(OrderStatus.ORDER_STATUS_PENDING.getPropertyKey());
        orderPurchase.setType(cart.getType());

        orderPurchase.setCurrency(cart.getCurrency());
        orderPurchase.setMarketAreaId(marketAreaId);
        orderPurchase.setRetailerId(cart.getRetailerId());
        orderPurchase.setLocalizationId(cart.getLocalizationId());
        orderPurchase.setCustomer(customer);

        CustomerAddress cartShippingAddress = customerService.getCustomerAddressById(cart.getShippingAddressId());
        OrderAddress shippingAddress = new OrderAddress();
        BeanUtils.copyProperties(cartShippingAddress, shippingAddress);
        shippingAddress.setId(null);
        orderPurchase.setShippingAddress(shippingAddress);
        
        CustomerAddress cartBillingAddress = customerService.getCustomerAddressById(cart.getBillingAddressId());
        if(cartBillingAddress != null){
            OrderAddress billingAddress = new OrderAddress();
            BeanUtils.copyProperties(cartBillingAddress, billingAddress);
            billingAddress.setId(null);
            orderPurchase.setBillingAddress(billingAddress);
        } else {
            orderPurchase.setBillingAddress(shippingAddress);
        }

        // SHIPMENT
        Set<OrderShipment> orderShipments = new HashSet<OrderShipment>();
        
        // SANITY CHECK
        if(deliveryMethods == null || deliveryMethods.isEmpty()){
            logger.error("Checkout an order with a cart who has no deliveryMethods! User id: " + customer.getId() + ", Cart id: " + cart.getId());
        }
        
        if (deliveryMethods != null) {
            for (DeliveryMethod deliveryMethod : deliveryMethods) {
                OrderShipment orderShipment = new OrderShipment();
                orderShipment.setName(deliveryMethod.getName());
                orderShipment.setExpectedDeliveryDate(null);
                orderShipment.setDeliveryMethodId(deliveryMethod.getId());
                orderShipment.setPrice(deliveryMethod.getPrice(cart.getCurrency().getId()));

                Set<OrderItem> orderItems = new HashSet<OrderItem>();
                for (CartItem cartItem : cartItems) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setCurrency(cart.getCurrency());
                    ProductSku productSku = cartItem.getProductSku();
                    orderItem.setProductSkuCode(productSku.getCode());
                    orderItem.setProductSku(productSku);
                    Boolean cartItemVATIncluded = cartService.isCartItemVATIncluded(cartItem, marketAreaId);
                    if(cartItemVATIncluded) {
                        orderItem.setPrice(cartService.getCartItemPriceWithTaxes(cartItem, marketAreaId));
                    } else {
                        orderItem.setPrice(cartService.getCartItemPrice(cartItem, marketAreaId));
                    }
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setStoreId(cartItem.getStoreId());
                    orderItem.setVATIncluded(cartItemVATIncluded);
                    
                    // TAXES
                    List<CartItemTax> cartItemTaxes = new ArrayList<CartItemTax>(); 
                    
                    // SANITY CHECK
                    if(cartItemTaxes == null || cartItemTaxes.isEmpty()){
                        logger.error("Checkout an order with an item who has no taxes! Cart id: " + cart.getId() + ", CartItem id: " + cartItem.getId());
                    }
                    
                    if (cartItemTaxes != null) {
                        for (CartItemTax cartItemTax : cartItemTaxes) {
                            Tax tax = cartItemTax.getTax();
                            OrderTax orderTax = new OrderTax();
                            orderTax.setTaxId(tax.getId());
                            orderTax.setName(tax.getName());
                            orderTax.setPercent(tax.getPercent());
                            orderTax.setAmount(cartService.getCartItemTaxesAmount(cartItem, marketAreaId));
                            orderItem.getTaxes().add(orderTax);
                        }
                    }
                    orderItem.setShipment(orderShipment);
                    orderItems.add(orderItem);
                }
                orderShipment.setOrderItems(orderItems);
                orderShipment.setOrderPurchase(orderPurchase);
                orderShipments.add(orderShipment);
            }
        }
        orderPurchase.setShipments(orderShipments);
        orderPurchase = orderPurchaseService.createNewOrder(orderPurchase);

        return orderPurchase;
    }

}
