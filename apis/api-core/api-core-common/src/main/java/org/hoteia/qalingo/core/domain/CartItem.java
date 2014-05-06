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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.Hibernate;

@Entity
@Table(name = "TECO_CART_ITEM")
public class CartItem extends AbstractEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 6636336983669678530L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Cart.class)
    @JoinColumn(name = "CART_ID", insertable = true, updatable = true)
    private Cart cart;
    
    @Column(name = "QUANTITY", nullable = false, columnDefinition = "int(11) default 0")
    private int quantity;

    @Transient
    private CartItemPrice cartItemPrice;
    
    @Column(name = "PRODUCT_SKU_CODE")
    private String productSkuCode;

    @Transient
    private ProductSku productSku;
    
    @Column(name = "PRODUCT_MARKETING_CODE")
    private String productMarketingCode;

    @Transient
    private ProductMarketing productMarketing;
    
    @Column(name = "VIRTUAL_CATEGORY_CODE")
    private String catalogCategoryCode;

    @Transient
    private CatalogCategoryVirtual catalogCategory;
    
    @Transient
    private DeliveryMethod deliveryMethod;

    @Transient
    private Set<CartItemTax> taxes = new HashSet<CartItemTax>();

    public CartItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Cart getCart() {
        return cart;
    }
    
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public int getQuantity() {
        return quantity;
    }

    public int addQuantity(int newQuantity) {
        this.quantity = quantity + newQuantity;
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CartItemPrice getCartItemPrice() {
        return cartItemPrice;
    }

    public void setCartItemPrice(CartItemPrice cartItemPrice) {
        this.cartItemPrice = cartItemPrice;
    }

    public String getProductSkuCode() {
        return productSkuCode;
    }

    public void setProductSkuCode(String productSkuCode) {
        this.productSkuCode = productSkuCode;
    }
    
    public ProductSku getProductSku() {
        return productSku;
    }
    
    public void setProductSku(ProductSku productSku) {
        this.productSku = productSku;
    }

    public String getProductMarketingCode() {
        return productMarketingCode;
    }

    public void setProductMarketingCode(String productMarketingCode) {
        this.productMarketingCode = productMarketingCode;
    }
    
    public ProductMarketing getProductMarketing() {
        return productMarketing;
    }
    
    public void setProductMarketing(ProductMarketing productMarketing) {
        this.productMarketing = productMarketing;
    }

    public String getCatalogCategoryCode() {
        return catalogCategoryCode;
    }

    public void setCatalogCategoryCode(String catalogCategoryCode) {
        this.catalogCategoryCode = catalogCategoryCode;
    }

    public CatalogCategoryVirtual getCatalogCategory() {
        return catalogCategory;
    }
    
    public void setCatalogCategory(CatalogCategoryVirtual catalogCategory) {
        this.catalogCategory = catalogCategory;
    }
    
    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public Set<CartItemTax> getTaxes() {
        return taxes;
    }

    public void setTaxes(Set<CartItemTax> taxes) {
        this.taxes = taxes;
    }

    public ProductSkuPrice getPrice(final Long marketAreaId, final Long retailerId) {
        if (productSku != null 
                && Hibernate.isInitialized(productSku)
                && productSku.getPrices() != null 
                && Hibernate.isInitialized(productSku.getPrices())) {
            for (Iterator<ProductSkuPrice> iterator = productSku.getPrices().iterator(); iterator.hasNext();) {
                final ProductSkuPrice productSkuPrice = (ProductSkuPrice) iterator.next();
                if (productSkuPrice.getMarketAreaId() != null && productSkuPrice.getMarketAreaId().equals(marketAreaId) && productSkuPrice.getRetailerId() != null
                        && productSkuPrice.getRetailerId().equals(retailerId)) {
                    return productSkuPrice;
                }
            }
        }
        return null;
    }

    public String getPriceWithStandardCurrencySign(final Long marketAreaId, final Long retailerId) {
        final ProductSkuPrice productSkuPrice = getPrice(marketAreaId, retailerId);
        if (productSkuPrice != null) {
            return productSkuPrice.getPriceWithStandardCurrencySign();
        }
        return null;
    }

    public BigDecimal getTotalAmountCartItem(final Long marketAreaId, final Long retailerId) {
        BigDecimal totalAmount = new BigDecimal("0");
        final ProductSkuPrice productSkuPrice = getPrice(marketAreaId, retailerId);
        if (productSkuPrice != null) {
            totalAmount = totalAmount.add(productSkuPrice.getSalePrice());
        }
        totalAmount = totalAmount.multiply(new BigDecimal(quantity));
        return totalAmount;
    }

    public String getTotalAmountWithStandardCurrencySign(final Long marketAreaId, final Long retailerId) {
        BigDecimal totalAmount = new BigDecimal("0");
        final ProductSkuPrice productSkuPrice = getPrice(marketAreaId, retailerId);
        if (productSkuPrice != null) {
            totalAmount = totalAmount.add(productSkuPrice.getSalePrice());
        }
        totalAmount = totalAmount.multiply(new BigDecimal(quantity));
        return productSkuPrice.getCurrency().formatPriceWithStandardCurrencySign(totalAmount);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((catalogCategoryCode == null) ? 0 : catalogCategoryCode.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((productMarketingCode == null) ? 0 : productMarketingCode.hashCode());
        result = prime * result + ((productSkuCode == null) ? 0 : productSkuCode.hashCode());
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
        CartItem other = (CartItem) obj;
        if (catalogCategoryCode == null) {
            if (other.catalogCategoryCode != null)
                return false;
        } else if (!catalogCategoryCode.equals(other.catalogCategoryCode))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (productMarketingCode == null) {
            if (other.productMarketingCode != null)
                return false;
        } else if (!productMarketingCode.equals(other.productMarketingCode))
            return false;
        if (productSkuCode == null) {
            if (other.productSkuCode != null)
                return false;
        } else if (!productSkuCode.equals(other.productSkuCode))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CartItem [id=" + id + ", quantity=" + quantity + ", productSkuCode=" + productSkuCode + ", productMarketingCode=" + productMarketingCode + ", catalogCategoryCode="
                + catalogCategoryCode + "]";
    }

}