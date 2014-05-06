/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hoteia.qalingo.core.domain.CurrencyReferential;
import org.hoteia.qalingo.core.domain.OrderAddress;
import org.hoteia.qalingo.core.domain.OrderPayment;
import org.hoteia.qalingo.core.domain.OrderShipment;

public class OrderCustomerPojo {

    private Long id;
    private int version;
    private String status;
    private String orderNum;
    private Long marketAreaId;
    private Long retailerId;
    private Long customerId;
    private OrderAddress billingAddress;
    private OrderAddress shippingAddress;
    private CurrencyReferential currency;
    private Set<OrderShipment> orderShipments = new HashSet<OrderShipment>();
    private Set<OrderPayment> orderPayments = new HashSet<OrderPayment>(); 
    private Date dateCreate;
    private Date dateUpdate;

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Long getMarketAreaId() {
        return marketAreaId;
    }

    public void setMarketAreaId(Long marketAreaId) {
        this.marketAreaId = marketAreaId;
    }

    public Long getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(Long retailerId) {
        this.retailerId = retailerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public OrderAddress getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(OrderAddress billingAddress) {
        this.billingAddress = billingAddress;
    }

    public OrderAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(OrderAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public CurrencyReferential getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyReferential currency) {
        this.currency = currency;
    }

    public Set<OrderShipment> getOrderShipments() {
        return orderShipments;
    }

    public void setOrderShipments(Set<OrderShipment> orderShipments) {
        this.orderShipments = orderShipments;
    }

    public Set<OrderPayment> getOrderPayments() {
        return orderPayments;
    }

    public void setOrderPayments(Set<OrderPayment> orderPayments) {
        this.orderPayments = orderPayments;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
    
}