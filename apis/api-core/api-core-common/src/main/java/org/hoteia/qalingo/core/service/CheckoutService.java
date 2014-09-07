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

import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.CartItem;
import org.hoteia.qalingo.core.domain.CartItemTax;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.DeliveryMethod;
import org.hoteia.qalingo.core.domain.OrderAddress;
import org.hoteia.qalingo.core.domain.OrderCustomer;
import org.hoteia.qalingo.core.domain.OrderItem;
import org.hoteia.qalingo.core.domain.OrderShipment;
import org.hoteia.qalingo.core.domain.OrderTax;
import org.hoteia.qalingo.core.domain.enumtype.OrderStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("checkoutService")
@Transactional
public class CheckoutService {

    @Autowired
    protected OrderCustomerService orderCustomerService;
    
    public OrderCustomer checkout(final Customer customer, final Cart cart) throws Exception {
        OrderCustomer orderCustomer = new OrderCustomer();
        // ORDER NUMBER IS CREATE BY DAO
        
        orderCustomer.setStatus(OrderStatus.ORDER_STATUS_PENDING.getPropertyKey());

        orderCustomer.setCurrency(cart.getCurrency());
        orderCustomer.setMarketAreaId(cart.getMarketAreaId());
        orderCustomer.setRetailerId(cart.getRetailerId());
        orderCustomer.setLocalizationId(cart.getLocalizationId());
        orderCustomer.setCustomerId(customer.getId());
        
        OrderAddress billingAddress = new OrderAddress();
        BeanUtils.copyProperties(customer.getAddress(cart.getBillingAddressId()), billingAddress);
        orderCustomer.setBillingAddress(billingAddress);

        OrderAddress shippingAddress = new OrderAddress();
        BeanUtils.copyProperties(customer.getAddress(cart.getShippingAddressId()), shippingAddress);
        orderCustomer.setShippingAddress(shippingAddress);
        
        // SHIPMENT
        Set<OrderShipment> orderShipments = new HashSet<OrderShipment>();
        Set<DeliveryMethod> deliveryMethods = cart.getDeliveryMethods();
        if(deliveryMethods != null){
            for (Iterator<DeliveryMethod> iteratorDeliveryMethod = deliveryMethods.iterator(); iteratorDeliveryMethod.hasNext();) {
                DeliveryMethod deliveryMethod = (DeliveryMethod) iteratorDeliveryMethod.next();
                OrderShipment orderShipment = new OrderShipment();
                orderShipment.setName(deliveryMethod.getName());
                orderShipment.setExpectedDeliveryDate(null);
                orderShipment.setDeliveryMethodId(deliveryMethod.getId());
                orderShipment.setPrice(deliveryMethod.getPrice(cart.getCurrency().getId()));
                
                Set<CartItem> cartItems = cart.getCartItems();
                Set<OrderItem> orderItems = new HashSet<OrderItem>();
                for (Iterator<CartItem> iteratorCartItem = cartItems.iterator(); iteratorCartItem.hasNext();) {
                    CartItem cartItem = (CartItem) iteratorCartItem.next();
                    OrderItem orderItem = new OrderItem();
                    orderItem.setCurrency(cart.getCurrency());
                    orderItem.setProductSkuCode(cartItem.getProductSkuCode());
                    orderItem.setProductSku(cartItem.getProductSku());
                    orderItem.setPrice(cartItem.getPrice(cart.getMarketAreaId(), cart.getRetailerId()).getSalePrice());
                    orderItem.setQuantity(cartItem.getQuantity());
                    
                    // TAXES
                    Set<CartItemTax> taxes = cartItem.getTaxes();
                    if(taxes != null){
                        for (Iterator<CartItemTax> iteratorCartItemTax = taxes.iterator(); iteratorCartItemTax.hasNext();) {
                            CartItemTax cartItemTax = (CartItemTax) iteratorCartItemTax.next();
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
        orderCustomer.setOrderShipments(orderShipments);
        
        orderCustomer = orderCustomerService.createNewOrder(orderCustomer);
        
        return orderCustomer;
    }

}