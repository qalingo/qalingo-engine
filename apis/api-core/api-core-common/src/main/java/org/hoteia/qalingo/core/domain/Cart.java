/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
@Table(name = "TECO_CART")
public class Cart extends AbstractEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -1607025617573183198L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Version
    @Column(name = "VERSION", nullable = false, columnDefinition = "int(11) default 1")
    private int version;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "MARKET_AREA_ID")
    private Long marketAreaId;

    @Column(name = "RETAILER_ID")
    private Long retailerId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CURRENCY_ID", insertable = false, updatable = false)
    private CurrencyReferential currency;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "BILLING_ADDRESS_ID")
    private Long billingAddressId;

    @Column(name = "SHIPPING_ADDRESS_ID")
    private Long shippingAddressId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "SESSION_ID")
    private EngineEcoSession session;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CART_ID")
    private Set<CartItem> cartItems = new HashSet<CartItem>();

    @Transient
    private Set<DeliveryMethod> shippings = new HashSet<DeliveryMethod>();

    @Transient
    private Set<Tax> taxes = new HashSet<Tax>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;

    public Cart() {
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

    public CurrencyReferential getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyReferential currency) {
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

    public EngineEcoSession getSession() {
        return session;
    }

    public void setSession(EngineEcoSession session) {
        this.session = session;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public int getTotalCartItems() {
        if (cartItems != null) {
            return cartItems.size();
        }
        return 0;
    }

    public Set<DeliveryMethod> getShippings() {
        return shippings;
    }

    public void setShippings(Set<DeliveryMethod> shippings) {
        this.shippings = shippings;
    }

    public Set<Tax> getTaxes() {
        return taxes;
    }

    public void setTaxes(Set<Tax> taxes) {
        this.taxes = taxes;
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

    public BigDecimal getDeliveryMethodTotal() {
        final Set<DeliveryMethod> deliveryMethods = getShippings();
        BigDecimal cartDeliveryMethodTotal = new BigDecimal("0");
        if (deliveryMethods != null) {
            for (Iterator<DeliveryMethod> iterator = deliveryMethods.iterator(); iterator.hasNext();) {
                final DeliveryMethod deliveryMethod = (DeliveryMethod) iterator.next();
                cartDeliveryMethodTotal = cartDeliveryMethodTotal.add(deliveryMethod.getPrice(getMarketAreaId(), getRetailerId()));
            }
        }
        return cartDeliveryMethodTotal;
    }

    public String getDeliveryMethodTotalWithStandardCurrencySign() {
        return getCurrency().formatPriceWithStandardCurrencySign(getDeliveryMethodTotal());
    }

    public BigDecimal getCartItemTotal() {
        BigDecimal cartItemsTotal = new BigDecimal("0");
        for (Iterator<CartItem> iterator = cartItems.iterator(); iterator.hasNext();) {
            final CartItem cartItem = (CartItem) iterator.next();
            if (cartItem.getPrice() != null) {
                cartItemsTotal = cartItemsTotal.add(cartItem.getPrice().getSalePrice());
            }
        }
        return cartItemsTotal;
    }

    public String getCartItemTotalWithStandardCurrencySign() {
        return getCurrency().formatPriceWithStandardCurrencySign(getCartItemTotal());
    }

    public BigDecimal getTaxTotal() {
        BigDecimal cartFeesTotal = new BigDecimal("0");
        final Set<Tax> taxes = getTaxes();
        if (taxes != null) {
            for (Iterator<Tax> iterator = taxes.iterator(); iterator.hasNext();) {
                final Tax tax = (Tax) iterator.next();

                // TODO TAX can be only on product or deliveyMethod or both

                BigDecimal taxesCalc = getDeliveryMethodTotal();
                taxesCalc = taxesCalc.multiply(tax.getPercent());
                taxesCalc = taxesCalc.divide(new BigDecimal("100"));
                cartFeesTotal = cartFeesTotal.add(taxesCalc);
            }
        }
        return cartFeesTotal;
    }

    public String getTaxTotalWithStandardCurrencySign() {
        return getCurrency().formatPriceWithStandardCurrencySign(getTaxTotal());
    }

    public BigDecimal getCartTotal() {
        BigDecimal carTotal = new BigDecimal("0");
        carTotal.add(getCartItemTotal());
        carTotal.add(getDeliveryMethodTotal());
        carTotal.add(getTaxTotal());
        return carTotal;
    }

    public String getCartTotalWithStandardCurrencySign() {
        return getCurrency().formatPriceWithStandardCurrencySign(getCartTotal());
    }
}