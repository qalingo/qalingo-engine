/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.cart;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hoteia.qalingo.core.pojo.CurrencyReferentialPojo;
import org.hoteia.qalingo.core.pojo.RulePojo;
import org.hoteia.qalingo.core.pojo.deliverymethod.DeliveryMethodPojo;
import org.hoteia.qalingo.core.pojo.tax.TaxPojo;

public class CartPojo {

    private Long id;
    private int version;
    private String status;
    private Long marketAreaId;
    private Long retailerId;
    private CurrencyReferentialPojo currency;
    private Long customerId;
    private Long billingAddressId;
    private Long shippingAddressId;
    
    private Set<CartItemPojo> cartItems = new HashSet<CartItemPojo>();
    private BigDecimal cartItemTotal;
    private String cartItemTotalWithStandardCurrencySign;

    private Set<DeliveryMethodPojo> deliveryMethods = new HashSet<DeliveryMethodPojo>();
    private BigDecimal deliveryMethodTotal;
    private String deliveryMethodTotalWithStandardCurrencySign;

    private Set<TaxPojo> taxes = new HashSet<TaxPojo>();
    private BigDecimal taxTotal;
    private String taxTotalWithStandardCurrencySign;

    private Set<RulePojo> rules = new HashSet<RulePojo>();

    private BigDecimal cartTotal;
    private String cartTotalWithStandardCurrencySign;

    private Date dateCreate;
    private Date dateUpdate;

    public CartPojo() {
    }

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

    public CurrencyReferentialPojo getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyReferentialPojo currency) {
        this.currency = currency;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getBillingAddressId() {
        return billingAddressId;
    }

    public void setBillingAddressId(Long billingAddressId) {
        this.billingAddressId = billingAddressId;
    }

    public Long getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(Long shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public Set<CartItemPojo> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItemPojo> cartItems) {
        this.cartItems = cartItems;
    }

    public int getTotalCartItems() {
        if (cartItems != null) {
            return cartItems.size();
        }
        return 0;
    }

    public BigDecimal getCartItemTotal() {
        return cartItemTotal;
    }

    public void setCartItemTotal(BigDecimal cartItemTotal) {
        this.cartItemTotal = cartItemTotal;
    }
    
    public String getCartItemTotalWithStandardCurrencySign() {
        return cartItemTotalWithStandardCurrencySign;
    }
    
    public void setCartItemTotalWithStandardCurrencySign(String cartItemTotalWithStandardCurrencySign) {
        this.cartItemTotalWithStandardCurrencySign = cartItemTotalWithStandardCurrencySign;
    }
    
    public Set<DeliveryMethodPojo> getDeliveryMethods() {
        return deliveryMethods;
    }
    
    public void setDeliveryMethods(Set<DeliveryMethodPojo> deliveryMethods) {
        this.deliveryMethods = deliveryMethods;
    }
    
    public BigDecimal getDeliveryMethodTotal() {
        return deliveryMethodTotal;
    }

    public void setDeliveryMethodTotal(BigDecimal deliveryMethodTotal) {
        this.deliveryMethodTotal = deliveryMethodTotal;
    }

    public String getDeliveryMethodTotalWithStandardCurrencySign() {
        return deliveryMethodTotalWithStandardCurrencySign;
    }

    public void setDeliveryMethodTotalWithStandardCurrencySign(String deliveryMethodTotalWithStandardCurrencySign) {
        this.deliveryMethodTotalWithStandardCurrencySign = deliveryMethodTotalWithStandardCurrencySign;
    }

    public Set<TaxPojo> getTaxes() {
        return taxes;
    }
    
    public void setTaxes(Set<TaxPojo> taxes) {
        this.taxes = taxes;
    }
    
    public BigDecimal getTaxTotal() {
        return taxTotal;
    }

    public void setTaxTotal(BigDecimal taxTotal) {
        this.taxTotal = taxTotal;
    }

    public String getTaxTotalWithStandardCurrencySign() {
        return taxTotalWithStandardCurrencySign;
    }

    public void setTaxTotalWithStandardCurrencySign(String taxTotalWithStandardCurrencySign) {
        this.taxTotalWithStandardCurrencySign = taxTotalWithStandardCurrencySign;
    }

    public Set<RulePojo> getRules() {
        return rules;
    }
    
    public void setRules(Set<RulePojo> rules) {
        this.rules = rules;
    }
    
    public BigDecimal getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(BigDecimal cartTotal) {
        this.cartTotal = cartTotal;
    }

    public String getCartTotalWithStandardCurrencySign() {
        return cartTotalWithStandardCurrencySign;
    }

    public void setCartTotalWithStandardCurrencySign(String cartTotalWithStandardCurrencySign) {
        this.cartTotalWithStandardCurrencySign = cartTotalWithStandardCurrencySign;
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