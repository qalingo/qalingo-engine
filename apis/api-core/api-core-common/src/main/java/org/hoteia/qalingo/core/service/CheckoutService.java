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
import java.util.Set;

import org.hoteia.qalingo.core.domain.*;
import org.hoteia.qalingo.core.domain.enumtype.OrderStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("checkoutService")
@Transactional
public class CheckoutService {

    @Autowired
    protected OrderPurchaseService orderPurchaseService;
    
    public OrderPurchase checkout(final Customer customer, final Cart cart, String transactionId) throws Exception {
        OrderPurchase orderPurchase = new OrderPurchase();
        // ORDER NUMBER IS CREATE BY DAO
        
        orderPurchase.setStatus(OrderStatus.ORDER_STATUS_PENDING.getPropertyKey());

        orderPurchase.setCurrency(cart.getCurrency());
        orderPurchase.setMarketAreaId(cart.getMarketAreaId());
        orderPurchase.setRetailerId(cart.getRetailerId());
        orderPurchase.setLocalizationId(cart.getLocalizationId());
        orderPurchase.setCustomerId(customer.getId());
        
        OrderAddress billingAddress = new OrderAddress();
        BeanUtils.copyProperties(customer.getAddress(cart.getBillingAddressId()), billingAddress);
        orderPurchase.setBillingAddress(billingAddress);

        OrderAddress shippingAddress = new OrderAddress();
        BeanUtils.copyProperties(customer.getAddress(cart.getShippingAddressId()), shippingAddress);
        orderPurchase.setShippingAddress(shippingAddress);
        
        // SHIPMENT
        Set<OrderShipment> orderShipments = new HashSet<OrderShipment>();
        Set<DeliveryMethod> deliveryMethods = cart.getDeliveryMethods();
        if(deliveryMethods != null){
            for (DeliveryMethod deliveryMethod : deliveryMethods) {
                OrderShipment orderShipment = new OrderShipment();
                orderShipment.setName(deliveryMethod.getName());
                orderShipment.setExpectedDeliveryDate(null);
                orderShipment.setDeliveryMethodId(deliveryMethod.getId());
                orderShipment.setPrice(deliveryMethod.getPrice(cart.getCurrency().getId()));

                Set<CartItem> cartItems = cart.getCartItems();
                Set<OrderItem> orderItems = new HashSet<OrderItem>();
                for (CartItem cartItem : cartItems) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setCurrency(cart.getCurrency());
                    orderItem.setProductSkuCode(cartItem.getProductSku().getCode());
                    orderItem.setProductSku(cartItem.getProductSku());
                    orderItem.setPrice(cartItem.getPrice(cart.getMarketAreaId(), cart.getRetailerId()).getSalePrice());
                    orderItem.setQuantity(cartItem.getQuantity());

                    // TAXES
                    Set<CartItemTax> taxes = cartItem.getTaxes();
                    if (taxes != null) {
                        for (CartItemTax cartItemTax : taxes) {
                            OrderTax orderTax = new OrderTax();
                            orderTax.setName(cartItemTax.getTax().getName());
                            orderTax.setPercent(cartItemTax.getTax().getPercent());
                            orderTax.setAmount(cartItemTax.getTaxAmount());
                            orderItem.getOrderTaxes().add(orderTax);
                        }
                    }

                    orderItems.add(orderItem);
                }
                orderShipment.setOrderItems(orderItems);
                orderShipments.add(orderShipment);
            }
        }
        orderPurchase.setOrderShipments(orderShipments);

        if(transactionId != null) {
            OrderPayment orderPayment = new OrderPayment();
            orderPayment.setIpAddress(transactionId);
            orderPurchase.getOrderPayments().add(orderPayment);
        }

        orderPurchase = orderPurchaseService.createNewOrder(orderPurchase);
        
        return orderPurchase;
    }

}