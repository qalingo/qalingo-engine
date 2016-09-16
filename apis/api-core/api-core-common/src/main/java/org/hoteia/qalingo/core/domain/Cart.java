/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 */
package org.hoteia.qalingo.core.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import org.hoteia.qalingo.core.domain.impl.DomainEntity;

@Entity
@Table(name = "TECO_CART")
public class Cart extends AbstractExtendEntity<Cart, CartAttribute> implements DomainEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -1607025617573183198L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Version
    @Column(name = "VERSION", nullable = false) // , columnDefinition = "int(11) default 1"
    private int version;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "MARKET_AREA_ID")
    private Long marketAreaId;

    @Column(name = "LOCALIZATION_ID")
    private Long localizationId;

    @Column(name = "RETAILER_ID")
    private Long retailerId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.CurrencyReferential.class)
    @JoinColumn(name = "CURRENCY_ID", insertable = true, updatable = true)
    private CurrencyReferential currency;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "BILLING_ADDRESS_ID")
    private Long billingAddressId;

    @Column(name = "SHIPPING_ADDRESS_ID")
    private Long shippingAddressId;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = {CascadeType.ALL}, targetEntity = org.hoteia.qalingo.core.domain.CartItem.class)
    @JoinColumn(name = "CART_ID", referencedColumnName = "ID")
    private Set<CartItem> cartItems = new HashSet<CartItem>();

    @Transient
    private Set<DeliveryMethod> deliveryMethods = new HashSet<DeliveryMethod>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CartAttribute.class)
    @JoinColumn(name = "CART_ID")
    private Set<CartAttribute> attributes = new HashSet<CartAttribute>();

    public Cart() {
        this.dateCreate = new Date();
        this.dateUpdate = new Date();
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    
    public CartItem getCartItem(ProductSku productSku) {
        if (cartItems != null && Hibernate.isInitialized(cartItems)) {
            for (CartItem cartItem : cartItems) {
                if (productSku != null && cartItem.getProductSku().equals(productSku)) {
                    return cartItem;
                }
            }
        }
        return null;
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
        if (cartItems != null && Hibernate.isInitialized(cartItems)) {
            Set<CartItem> checkedCartItems = new HashSet<CartItem>(cartItems);
            for (CartItem cartItem : checkedCartItems) {
                if (cartItem != null && cartItem.getProductSku().equals(cartItemToDelete.getProductSku())) {
                    cartItems.remove(cartItem);
                }
            }
        }
    }

    public int getTotalCartItems() {
        if (cartItems != null && Hibernate.isInitialized(cartItems)) {
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

    public List<Tax> getTaxes() {
        if (cartItems != null && Hibernate.isInitialized(cartItems)) {
            Map<String, Tax> mapTaxes = new HashMap<String, Tax>();
            for (CartItem cartItem : cartItems) {
                for (CartItemTax tax : cartItem.getTaxes()) {
                    mapTaxes.put(tax.getTax().getCode(), tax.getTax());
                }
            }
            if(!mapTaxes.isEmpty()){
                return new ArrayList<Tax>(mapTaxes.values());
            }
        }
        return null;
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

    public Set<CartAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<CartAttribute> attributes) {
        this.attributes = attributes;
    }

    public void copyTransient(Cart cart) {
        this.deliveryMethods = cart.getDeliveryMethods();
        if (cartItems != null && Hibernate.isInitialized(cartItems)) {
            for (CartItem cartItem : cartItems) {
                cartItem.setTaxes(cart.getCartItem(cartItem.getProductSku()).getTaxes());
            }
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dateCreate == null) ? 0 : dateCreate.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((localizationId == null) ? 0 : localizationId.hashCode());
        result = prime * result + ((marketAreaId == null) ? 0 : marketAreaId.hashCode());
        result = prime * result + ((retailerId == null) ? 0 : retailerId.hashCode());
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
        if (localizationId == null) {
            if (other.localizationId != null)
                return false;
        } else if (!localizationId.equals(other.localizationId))
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
        return true;
    }

    @Override
    public String toString() {
        return "Cart [id=" + id + ", version=" + version + ", status=" + status + ", marketAreaId=" + marketAreaId + ", retailerId=" + retailerId + ", customerId=" + customerId
                + ", billingAddressId=" + billingAddressId + ", shippingAddressId=" + shippingAddressId + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }

}