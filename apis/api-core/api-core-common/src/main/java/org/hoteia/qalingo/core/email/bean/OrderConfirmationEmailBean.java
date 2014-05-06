/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.email.bean;

import java.io.Serializable;

public class OrderConfirmationEmailBean extends AbstractEmailBean implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 8775711859740312774L;
    
    private String orderNumber;
    private String expectedDeliveryDate;
    
    private String orderItemsTotalWithCurrencySign;
    private String orderShippingTotalWithCurrencySign;
    private String orderTaxesTotalWithCurrencySign;
    private String orderTotalWithCurrencySign;
    
    private String customerDetailsUrl;

    public String getOrderNumber() {
        return orderNumber;
    }
    
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getOrderItemsTotalWithCurrencySign() {
        return orderItemsTotalWithCurrencySign;
    }

    public void setOrderItemsTotalWithCurrencySign(String orderItemsTotalWithCurrencySign) {
        this.orderItemsTotalWithCurrencySign = orderItemsTotalWithCurrencySign;
    }

    public String getOrderShippingTotalWithCurrencySign() {
        return orderShippingTotalWithCurrencySign;
    }

    public void setOrderShippingTotalWithCurrencySign(String orderShippingTotalWithCurrencySign) {
        this.orderShippingTotalWithCurrencySign = orderShippingTotalWithCurrencySign;
    }

    public String getOrderTaxesTotalWithCurrencySign() {
        return orderTaxesTotalWithCurrencySign;
    }

    public void setOrderTaxesTotalWithCurrencySign(String orderTaxesTotalWithCurrencySign) {
        this.orderTaxesTotalWithCurrencySign = orderTaxesTotalWithCurrencySign;
    }

    public String getOrderTotalWithCurrencySign() {
        return orderTotalWithCurrencySign;
    }

    public void setOrderTotalWithCurrencySign(String orderTotalWithCurrencySign) {
        this.orderTotalWithCurrencySign = orderTotalWithCurrencySign;
    }

    public String getCustomerDetailsUrl() {
        return customerDetailsUrl;
    }

    public void setCustomerDetailsUrl(String customerDetailsUrl) {
        this.customerDetailsUrl = customerDetailsUrl;
    }
    
}