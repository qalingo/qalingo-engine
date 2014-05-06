/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.Hibernate;

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

    @Column(name = "LOCALIZATION_ID")
    private Long localizationId;

    @Column(name = "RETAILER_ID")
    private Long retailerId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRENCY_ID", insertable = true, updatable = true)
    private CurrencyReferential currency;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "BILLING_ADDRESS_ID")
    private Long billingAddressId;

    @Column(name = "SHIPPING_ADDRESS_ID")
    private Long shippingAddressId;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = {CascadeType.ALL})
    @JoinColumn(name="CART_ID", referencedColumnName="ID")
    private Set<CartItem> cartItems = new HashSet<CartItem>();

    @Transient
    private Set<DeliveryMethod> deliveryMethods = new HashSet<DeliveryMethod>();

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
    
    public Long getLocalizationId() {
        return localizationId;
    }
    
    public void setLocalizationId(Long localizationId) {
        this.localizationId = localizationId;
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

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public void deleteAllCartItem() {
        if (cartItems != null) {
            cartItems.clear();
        }
    }

    public void deleteCartItem(CartItem cartItemToDelete) {
        if (cartItems != null
                && Hibernate.isInitialized(cartItems)) {
            Set<CartItem> checkedCartItems = new HashSet<CartItem>(cartItems);
            for (Iterator<CartItem> iterator = checkedCartItems.iterator(); iterator.hasNext();) {
                CartItem cartItem = (CartItem) iterator.next();
                if (cartItem != null && cartItem.getProductSkuCode().equals(cartItemToDelete.getProductSkuCode())) {
                    cartItems.remove(cartItem);
                }
            }
        }
    }

    public int getTotalCartItems() {
        if (cartItems != null
                && Hibernate.isInitialized(cartItems)) {
            return cartItems.size();
        }
        return 0;
    }

    public Set<DeliveryMethod> getDeliveryMethods() {
        return deliveryMethods;
    }

    public void setDeliveryMethods(Set<DeliveryMethod> deliveryMethods) {
        this.deliveryMethods = deliveryMethods;
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
        final Set<DeliveryMethod> deliveryMethods = getDeliveryMethods();
        BigDecimal cartDeliveryMethodTotal = new BigDecimal("0");
        if (deliveryMethods != null
                && Hibernate.isInitialized(deliveryMethods)) {
            for (Iterator<DeliveryMethod> iterator = deliveryMethods.iterator(); iterator.hasNext();) {
                final DeliveryMethod deliveryMethod = (DeliveryMethod) iterator.next();
                BigDecimal price = deliveryMethod.getPrice(getMarketAreaId(), getRetailerId());
                if (price != null) {
                    cartDeliveryMethodTotal = cartDeliveryMethodTotal.add(price);
                }
            }
        }
        return cartDeliveryMethodTotal;
    }

    public String getDeliveryMethodTotalWithStandardCurrencySign() {
        return getCurrency().formatPriceWithStandardCurrencySign(getDeliveryMethodTotal());
    }

    public BigDecimal getCartItemTotal() {
        BigDecimal cartItemsTotal = new BigDecimal("0");
        if (cartItems != null
                && Hibernate.isInitialized(cartItems)) {
            for (Iterator<CartItem> iterator = cartItems.iterator(); iterator.hasNext();) {
                final CartItem cartItem = (CartItem) iterator.next();
                cartItemsTotal = cartItemsTotal.add(cartItem.getTotalAmountCartItem(getMarketAreaId(), getRetailerId()));
            }
        }
        return cartItemsTotal;
    }

    public String getCartItemTotalWithStandardCurrencySign() {
        if(getCurrency() != null){
            return getCurrency().formatPriceWithStandardCurrencySign(getCartItemTotal());
        }
        return null;
    }

    public BigDecimal getTaxTotal() {
        BigDecimal cartFeesTotal = new BigDecimal("0");
        final Set<Tax> taxes = getTaxes();
        if (taxes != null
                && Hibernate.isInitialized(taxes)) {
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
        carTotal = carTotal.add(getCartItemTotal());
        carTotal = carTotal.add(getDeliveryMethodTotal());
        carTotal = carTotal.add(getTaxTotal());
        return carTotal;
    }

    public String getCartTotalWithStandardCurrencySign() {
        return getCurrency().formatPriceWithStandardCurrencySign(getCartTotal());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((billingAddressId == null) ? 0 : billingAddressId.hashCode());
        result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
        result = prime * result + ((dateCreate == null) ? 0 : dateCreate.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((marketAreaId == null) ? 0 : marketAreaId.hashCode());
        result = prime * result + ((retailerId == null) ? 0 : retailerId.hashCode());
        result = prime * result + ((shippingAddressId == null) ? 0 : shippingAddressId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cart other = (Cart) obj;
        if (billingAddressId == null) {
            if (other.billingAddressId != null)
                return false;
        } else if (!billingAddressId.equals(other.billingAddressId))
            return false;
        if (customerId == null) {
            if (other.customerId != null)
                return false;
        } else if (!customerId.equals(other.customerId))
            return false;
        if (dateCreate == null) {
            if (other.dateCreate != null)
                return false;
        } else if (!dateCreate.equals(other.dateCreate))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (marketAreaId == null) {
            if (other.marketAreaId != null)
                return false;
        } else if (!marketAreaId.equals(other.marketAreaId))
            return false;
        if (retailerId == null) {
            if (other.retailerId != null)
                return false;
        } else if (!retailerId.equals(other.retailerId))
            return false;
        if (shippingAddressId == null) {
            if (other.shippingAddressId != null)
                return false;
        } else if (!shippingAddressId.equals(other.shippingAddressId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Cart [id=" + id + ", version=" + version + ", status=" + status + ", marketAreaId=" + marketAreaId + ", retailerId=" + retailerId + ", customerId=" + customerId
                + ", billingAddressId=" + billingAddressId + ", shippingAddressId=" + shippingAddressId + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }

}