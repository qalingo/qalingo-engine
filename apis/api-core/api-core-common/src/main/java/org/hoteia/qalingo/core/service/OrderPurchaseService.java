/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 */
package org.hoteia.qalingo.core.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.dao.OrderPurchaseDao;
import org.hoteia.qalingo.core.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("orderPurchaseService")
@Transactional
public class OrderPurchaseService {

    @Autowired
    protected OrderPurchaseDao orderDao;

    public OrderPurchase getOrderById(final Long orderPurchaseId, Object... params) {
        return orderDao.getOrderById(orderPurchaseId, params);
    }

    public OrderPurchase getOrderById(final String rawOrderPurchaseId, Object... params) {
        long orderId = -1;
        try {
            orderId = Long.parseLong(rawOrderPurchaseId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getOrderById(orderId, params);
    }

    public OrderPurchase getOrderByOrderNum(final String orderNum, Object... params) {
        return orderDao.getOrderByOrderNum(orderNum, params);
    }

    public List<OrderPurchase> findOrdersByCustomerId(final String customerId, Object... params) {
        return orderDao.findOrdersByCustomerId(Long.parseLong(customerId), params);
    }

    public List<OrderPurchase> findOrders(Object... params) {
        return orderDao.findOrders(params);
    }

    public List<OrderItem> findOrderItemsByStoreId(final Long storeId, Object... params) {
        return orderDao.findOrderItemsByStoreId(storeId, params);
    }

    public List<OrderItem> findOrderItemsByStoreId(final String storeId, Object... params) {
        try {
            return orderDao.findOrderItemsByStoreId(Long.parseLong(storeId), params);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public OrderItem findOrderItemById(final Long id, Object... params) {
        return orderDao.findOrderItemById(id, params);
    }

    public List<OrderPurchase> findOrdersByStatus(final String status, Object... params) {
        return orderDao.findOrdersByStatus(status, params);
    }

    public OrderPurchase createNewOrder(final OrderPurchase orderPurchase) {
        return orderDao.createNewOrder(orderPurchase);
    }

    public OrderPurchase saveOrUpdateOrder(final OrderPurchase orderPurchase) {
        return orderDao.saveOrUpdateOrder(orderPurchase);
    }

    public void deleteOrder(final OrderPurchase orderPurchase) {
        orderDao.deleteOrder(orderPurchase);
    }

    public String getOrderTotalWithStandardCurrencySign(final OrderPurchase orderPurchase) {
        return orderPurchase.getCurrency().formatPriceWithStandardCurrencySign(getOrderTotal(orderPurchase));
    }

    public BigDecimal getOrderTotal(final OrderPurchase orderPurchase) {
        BigDecimal total = new BigDecimal(0);
        Set<OrderItem> orderItems = orderPurchase.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            total = total.add(getOrderItemTotalPriceWithTaxes(orderItem));
        }
        return total;
    }

    public String getDeliveryMethodTotalWithStandardCurrencySign(final OrderPurchase orderPurchase) {
        return orderPurchase.getCurrency().formatPriceWithStandardCurrencySign(getDeliveryMethodTotal(orderPurchase));
    }

    public BigDecimal getDeliveryMethodTotal(final OrderPurchase orderPurchase) {
        Set<OrderShipment> shipments = orderPurchase.getShipments();
        BigDecimal shippingTotal = new BigDecimal("0");
        if (shipments != null && Hibernate.isInitialized(shipments)) {
            for (final OrderShipment orderShipment : shipments) {
                BigDecimal price = orderShipment.getPrice();
                if (price != null) {
                    shippingTotal = shippingTotal.add(price);
                }
            }
        }
        return shippingTotal;
    }

    public String getTaxTotalWithStandardCurrencySign(final OrderPurchase orderPurchase) {
        return orderPurchase.getCurrency().formatPriceWithStandardCurrencySign(getTaxTotal(orderPurchase));
    }

    public String getOrderItemTotalWithStandardCurrencySign(final OrderPurchase orderPurchase) {
        return orderPurchase.getCurrency().formatPriceWithStandardCurrencySign(getOrderItemTotal(orderPurchase));
    }

    public BigDecimal getOrderItemTotal(final OrderPurchase orderPurchase) {
        BigDecimal cartItemsTotal = new BigDecimal(0);
        Set<OrderItem> orderItems = orderPurchase.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            cartItemsTotal = cartItemsTotal.add(getOrderItemTotalPrice(orderItem));
        }
        return cartItemsTotal;
    }

    public String getOrderItemTotalWithTaxesWithStandardCurrencySign(final OrderPurchase orderPurchase) {
        return orderPurchase.getCurrency().formatPriceWithStandardCurrencySign(getOrderItemTotalWithTaxes(orderPurchase));
    }

    public BigDecimal getOrderItemTotalWithTaxes(final OrderPurchase orderPurchase) {
        BigDecimal cartItemsTotal = new BigDecimal(0);
        Set<OrderItem> orderItems = orderPurchase.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            cartItemsTotal = cartItemsTotal.add(getOrderItemTotalPriceWithTaxes(orderItem));
        }
        return cartItemsTotal;
    }

    public String getOrderItemPriceWithStandardCurrencySign(final OrderItem orderItem) {
        return orderItem.getCurrency().formatPriceWithStandardCurrencySign(getOrderItemPrice(orderItem));
    }

    public BigDecimal getOrderItemPrice(final OrderItem orderItem) {
        BigDecimal totalAmount = orderItem.getPrice();
        Set<OrderTax> taxes = orderItem.getTaxes();
        if (orderItem.isVATIncluded()) {
            if (taxes != null && taxes.size() > 0) {
                for (OrderTax tax : taxes) {
                    BigDecimal taxAmount = tax.getPercent().divide(new BigDecimal(100), 5, BigDecimal.ROUND_HALF_EVEN).add(new BigDecimal(1)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                    totalAmount = totalAmount.divide(taxAmount, 5, BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                }
            }
        }
        return totalAmount;
    }

    public String getOrderItemPriceWithTaxesWithStandardCurrencySign(final OrderItem orderItem) {
        return orderItem.getCurrency().formatPriceWithStandardCurrencySign(getOrderItemPriceWithTaxes(orderItem));
    }

    public BigDecimal getOrderItemPriceWithTaxes(final OrderItem orderItem) {
        BigDecimal totalAmount = orderItem.getPrice();
        Set<OrderTax> taxes = orderItem.getTaxes();
        if (!orderItem.isVATIncluded()) {
            if (taxes != null && taxes.size() > 0) {
                for (OrderTax tax : taxes) {
                    BigDecimal taxAmount = totalAmount.multiply(tax.getPercent());
                    totalAmount = totalAmount.add(taxAmount.divide(new BigDecimal(100), 5, BigDecimal.ROUND_HALF_EVEN)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                }
            }
        }
        return totalAmount;
    }


    public String getOrderItemTotalPriceWithStandardCurrencySign(final OrderItem orderItem) {
        BigDecimal result = getOrderItemPrice(orderItem).multiply(new BigDecimal(orderItem.getQuantity()));
        return orderItem.getCurrency().formatPriceWithStandardCurrencySign(result);
    }

    public BigDecimal getOrderItemTotalPrice(final OrderItem orderItem) {
        return getOrderItemPrice(orderItem).multiply(new BigDecimal(orderItem.getQuantity()));
    }

    public String getOrderItemTotalPriceWithTaxesWithStandardCurrencySign(final OrderItem orderItem) {
        BigDecimal result = getOrderItemPriceWithTaxes(orderItem).multiply(new BigDecimal(orderItem.getQuantity()));
        return orderItem.getCurrency().formatPriceWithStandardCurrencySign(result);
    }

    public BigDecimal getOrderItemTotalPriceWithTaxes(final OrderItem orderItem) {
        return getOrderItemPriceWithTaxes(orderItem).multiply(new BigDecimal(orderItem.getQuantity()));
    }

    public BigDecimal getTaxTotal(final OrderPurchase orderPurchase) {
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderPurchase.getOrderItems()) {
            BigDecimal orderItemTaxesAmount = getOrderItemTaxesAmount(orderItem);
            totalAmount = totalAmount.add(orderItemTaxesAmount);
        }
        return totalAmount;
    }

    public static BigDecimal getOrderItemTaxesAmount(OrderItem orderItem) {
        BigDecimal salePrice = orderItem.getPrice();
        Set<OrderTax> taxes = orderItem.getTaxes();
        int quantity = orderItem.getQuantity();

        BigDecimal totalAmount = new BigDecimal(0);
        if (taxes == null || taxes.size() == 0) {
            return totalAmount;
        }
        boolean vatIncluded = orderItem.isVATIncluded();
        for (OrderTax tax : taxes) {
            if (vatIncluded) {
                BigDecimal taxAmount = tax.getPercent().divide(new BigDecimal(100), 5, BigDecimal.ROUND_HALF_EVEN).add(new BigDecimal(1)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                taxAmount = salePrice.subtract(salePrice.divide(taxAmount, 5, BigDecimal.ROUND_CEILING)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                totalAmount = totalAmount.add(taxAmount.multiply(new BigDecimal(quantity)));
            } else {
                BigDecimal taxAmount = salePrice.multiply(tax.getPercent());
                taxAmount = salePrice.add(taxAmount.divide(new BigDecimal(100), 5, BigDecimal.ROUND_HALF_EVEN)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                totalAmount = totalAmount.add(taxAmount.multiply(new BigDecimal(quantity)));
            }
        }
        return totalAmount;
    }

}