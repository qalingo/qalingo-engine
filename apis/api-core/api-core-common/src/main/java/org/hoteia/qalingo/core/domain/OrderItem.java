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

import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.domain.impl.DomainEntity;

@Entity
@Table(name = "TECO_ORDER_ITEM")
public class OrderItem extends AbstractEntity<OrderItem> implements DomainEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 6982641911557993534L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ORDER_ID")
    private Long orderId;
//
//    @Column(name = "RETAILER_ID")
//    private Long retailerId;
//

    @Column(name = "STORE_ID")
    private Long storeId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.CurrencyReferential.class)
    @JoinColumn(name = "CURRENCY_ID", insertable = true, updatable = true)
    private CurrencyReferential currency;
    
    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "QUANTITY", nullable = false) // , columnDefinition = "int(11) default 0"
    private int quantity;

    @Column(name = "PRODUCT_SKU_CODE")
    private String productSkuCode;

    @ManyToOne(fetch = FetchType.LAZY,  targetEntity = org.hoteia.qalingo.core.domain.ProductSku.class)
    @JoinColumn(name = "PRODUCT_SKU_ID", insertable = true, updatable = true)
    private ProductSku productSku;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.OrderShipment.class)
    @JoinColumn(name = "ORDER_SHIPMENT_ID", insertable = true, updatable = true)
    private OrderShipment shipment;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.OrderTax.class)
    @JoinColumn(name = "ORDER_ITEM_ID")
    private Set<OrderTax> taxes = new HashSet<OrderTax>();

    @Column(name = "IS_VAT_INCLUDED", nullable = false) // , columnDefinition = "tinyint(1) default 0"
    private boolean isVATIncluded = false;

    public OrderItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getOrderId() {
        return orderId;
    }


    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public CurrencyReferential getCurrency() {
        return currency;
    }
    
    public void setCurrency(CurrencyReferential currency) {
        this.currency = currency;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
    
    public OrderShipment getShipment() {
        return shipment;
    }
    
    public void setShipment(OrderShipment shipment) {
        this.shipment = shipment;
    }
    
    public OrderPurchase getOrderPurchase() {
        if(shipment != null
                && Hibernate.isInitialized(shipment)
                && shipment.getOrderPurchase() != null
                && Hibernate.isInitialized(shipment.getOrderPurchase())){
                return shipment.getOrderPurchase();
        }
        return null;
    }

    public Set<OrderTax> getTaxes() {
        return taxes;
    }
    
    public void setTaxes(Set<OrderTax> taxes) {
        this.taxes = taxes;
    }

    public boolean isVATIncluded() {
        return isVATIncluded;
    }

    public void setVATIncluded(boolean VATIncluded) {
        isVATIncluded = VATIncluded;
    }

    public String getOrderItemPriceWithStandardCurrencySign() {
        return getCurrency().formatPriceWithStandardCurrencySign(getOrderItemPrice());
    }

    public BigDecimal getOrderItemPrice() {
        BigDecimal totalAmount = getPrice();
        Set<OrderTax> taxes = getTaxes();
        if (isVATIncluded()) {
            if (taxes != null && taxes.size() > 0) {
                for (OrderTax tax : taxes) {
                    BigDecimal taxAmount = tax.getPercent().divide(new BigDecimal(100), 5, BigDecimal.ROUND_HALF_EVEN).add(new BigDecimal(1)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                    totalAmount = totalAmount.divide(taxAmount, 5, BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                }
            }
        }
        return totalAmount;
    }

    public String getOrderItemPriceWithTaxesWithStandardCurrencySign() {
        return getCurrency().formatPriceWithStandardCurrencySign(getOrderItemPriceWithTaxes());
    }

    public String getOrderItemTotalPriceWithStandardCurrencySign() {
        BigDecimal orderItemTotalPrice = getOrderItemPrice().multiply(new BigDecimal(getQuantity()));
        return getCurrency().formatPriceWithStandardCurrencySign(orderItemTotalPrice);
    }

    public BigDecimal getOrderItemTotalPrice() {
        return getOrderItemPrice().multiply(new BigDecimal(getQuantity()));
    }

    public String getOrderItemTotalPriceWithTaxesWithStandardCurrencySign() {
        BigDecimal orderItemTotalPrice = getOrderItemPriceWithTaxes().multiply(new BigDecimal(getQuantity()));
        return getCurrency().formatPriceWithStandardCurrencySign(orderItemTotalPrice);
    }

    public BigDecimal getOrderItemTotalPriceWithTaxes() {
        return getOrderItemPriceWithTaxes().multiply(new BigDecimal(getQuantity()));
    }
    
    public BigDecimal getOrderItemPriceWithTaxes() {
        BigDecimal totalAmount = getPrice();
        Set<OrderTax> taxes = getTaxes();
        if (!isVATIncluded()) {
            if (taxes != null && taxes.size() > 0) {
                for (OrderTax tax : taxes) {
                    BigDecimal taxAmount = totalAmount.multiply(tax.getPercent());
                    totalAmount = totalAmount.add(taxAmount.divide(new BigDecimal(100), 5, BigDecimal.ROUND_HALF_EVEN)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                }
            }
        }
        return totalAmount;
    }
    
    public BigDecimal getOrderItemTaxesAmount() {
        BigDecimal salePrice = getPrice();
        Set<OrderTax> taxes = getTaxes();
        int quantity = getQuantity();

        BigDecimal totalAmount = new BigDecimal(0);
        if (taxes == null || taxes.size() == 0) {
            return totalAmount;
        }
        boolean vatIncluded = isVATIncluded();
        for (OrderTax tax : taxes) {
            if (vatIncluded) {
                BigDecimal taxAmount = tax.getPercent().divide(new BigDecimal(100), 5, BigDecimal.ROUND_HALF_EVEN).add(new BigDecimal(1)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                taxAmount = salePrice.subtract(salePrice.divide(taxAmount, 5, BigDecimal.ROUND_CEILING)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                totalAmount = totalAmount.add(taxAmount.multiply(new BigDecimal(quantity)));
            } else {
                BigDecimal taxAmount = salePrice.multiply(tax.getPercent());
                taxAmount = taxAmount.divide(new BigDecimal(100), 5, BigDecimal.ROUND_HALF_EVEN);
                totalAmount = totalAmount.add(taxAmount.multiply(new BigDecimal(quantity)));
            }
        }
        return totalAmount;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((productSkuCode == null) ? 0 : productSkuCode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object sourceObj) {
        Object obj = deproxy(sourceObj);
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderItem other = (OrderItem) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
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
        return "OrderItem [id=" + id + ", price=" + price + ", quantity=" + quantity + ", productSkuCode=" + productSkuCode + "]";
    }
}