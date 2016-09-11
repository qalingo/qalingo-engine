/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 */
package org.hoteia.qalingo.core.service;

import java.util.HashSet;
import java.util.Set;

import org.hoteia.qalingo.core.domain.*;
import org.hoteia.qalingo.core.domain.enumtype.OrderStatus;
import org.hoteia.qalingo.core.fetchplan.customer.FetchPlanGraphCustomer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("checkoutService")
@Transactional
public class CheckoutService {

    @Autowired
    protected OrderPurchaseService orderPurchaseService;

    @Autowired
    protected CartService cartService;

    @Autowired
    protected CustomerService customerService;
    
    public OrderPurchase checkout(final Customer customer, final Cart cart) throws Exception {
        OrderPurchase orderPurchase = new OrderPurchase();
        // ORDER NUMBER IS CREATE BY DAO

        orderPurchase.setStatus(OrderStatus.ORDER_STATUS_PENDING.getPropertyKey());

        orderPurchase.setCurrency(cart.getCurrency());
        Long marketAreaId = cart.getMarketAreaId();
        orderPurchase.setMarketAreaId(marketAreaId);
        orderPurchase.setRetailerId(cart.getRetailerId());
        orderPurchase.setLocalizationId(cart.getLocalizationId());
        orderPurchase.setCustomer(customer);

        Customer reloadedCustomer = customerService.getCustomerById(customer.getId(), FetchPlanGraphCustomer.fullCustomerFetchPlan());
        OrderAddress billingAddress = new OrderAddress();
        BeanUtils.copyProperties(reloadedCustomer.getAddress(cart.getBillingAddressId()), billingAddress);
        billingAddress.setId(null);
        orderPurchase.setBillingAddress(billingAddress);

        OrderAddress shippingAddress = new OrderAddress();
        BeanUtils.copyProperties(reloadedCustomer.getAddress(cart.getShippingAddressId()), shippingAddress);
        shippingAddress.setId(null);
        orderPurchase.setShippingAddress(shippingAddress);

        // SHIPMENT
        Set<OrderShipment> orderShipments = new HashSet<OrderShipment>();
        Set<DeliveryMethod> deliveryMethods = cart.getDeliveryMethods();
        if (deliveryMethods != null) {
            Set<Tax> taxes = cart.getTaxes();
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
                    ProductSku productSku = cartItem.getProductSku();
                    orderItem.setProductSkuCode(productSku.getCode());
                    orderItem.setProductSku(productSku);
                    Boolean cartItemVATIncluded = cartService.isCartItemVATIncluded(cartItem, marketAreaId);
                    if(cartItemVATIncluded) {
                        orderItem.setPrice(cartService.getCartItemPriceWithTaxes(cartItem, marketAreaId, taxes));
                    } else {
                        orderItem.setPrice(cartService.getCartItemPrice(cartItem, marketAreaId, taxes));
                    }
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setStoreId(cartItem.getStoreId());
                    orderItem.setVATIncluded(cartItemVATIncluded);
                    // TAXES
                    if (taxes != null) {
                        for (Tax tax : taxes) {
                            OrderTax orderTax = new OrderTax();
                            orderTax.setName(tax.getName());
                            orderTax.setPercent(tax.getPercent());
                            orderTax.setAmount(cartService.getCartItemTaxesAmount(cartItem, marketAreaId, taxes));
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
