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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "TECO_PRODUCT_SKU_STORE_REL")
@AssociationOverrides({
    @AssociationOverride(name = "pk.productSku", joinColumns = @JoinColumn(name = "PRODUCT_SKU_ID")),
    @AssociationOverride(name = "pk.store", joinColumns = @JoinColumn(name = "STORE_ID"))})
public class ProductSkuStoreRel extends AbstractExtendEntity<ProductSkuStoreRel, ProductSkuStoreAttribute> {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 2179540514678304872L;

    @EmbeddedId
    private ProductSkuStorePk pk;
    
    @Column(name = "RANKING")
    private Integer ranking;

    @Column(name = "IS_DEFAULT_STORE", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isDefaultStore;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductSkuStoreAttribute.class)
    @JoinColumns({
        @JoinColumn(name = "PRODUCT_SKU_ID"),
        @JoinColumn(name = "STORE_ID") 
    })
    private Set<ProductSkuStoreAttribute> attributes = new HashSet<ProductSkuStoreAttribute>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductSkuStorePrice.class)
    @JoinColumns({
        @JoinColumn(name = "PRODUCT_SKU_ID"),
        @JoinColumn(name = "STORE_ID") 
    })
    private Set<ProductSkuStorePrice> prices = new HashSet<ProductSkuStorePrice>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductSkuStoreStock.class)
    @JoinColumns({
        @JoinColumn(name = "PRODUCT_SKU_ID"),
        @JoinColumn(name = "STORE_ID") 
    })
    private ProductSkuStoreStock stock;
    
    public ProductSkuStoreRel() {
        this.pk = new ProductSkuStorePk();
    }
    
    public ProductSkuStoreRel(final ProductSku productSku, final Store store) {
        this.pk = new ProductSkuStorePk(productSku, store);
    }

    public ProductSkuStorePk getPk() {
        return pk;
    }

    public void setPk(ProductSkuStorePk pk) {
        this.pk = pk;
    }

    @Transient
    public ProductSku getProductSku() {
        return getPk().getProductSku();
    }

    public void setProductSku(final ProductSku productSku) {
        pk.setProductSku(productSku);
    }
    
    @Transient
    public Store getStore() {
        return getPk().getStore();
    }

    public void setStore(final Store store) {
        pk.setStore(store);
    }
    
    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public boolean isDefaultStore() {
        return isDefaultStore;
    }

    public void setDefaultStore(boolean isDefaultStore) {
        this.isDefaultStore = isDefaultStore;
    }

    public Set<ProductSkuStoreAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<ProductSkuStoreAttribute> attributes) {
        this.attributes = attributes;
    }

    public Set<ProductSkuStorePrice> getPrices() {
        return prices;
    }

    public void setPrices(Set<ProductSkuStorePrice> prices) {
        this.prices = prices;
    }

    public ProductSkuStoreStock getStock() {
        return stock;
    }
    
    public void setStock(ProductSkuStoreStock stock) {
        this.stock = stock;
    }
    
    // Attributes
    
    public Object getValue(String attributeCode, Long marketAreaId, String localizationCode) {
        AbstractAttribute attribute = getAttribute(attributeCode, marketAreaId, localizationCode);
        if(attribute != null) {
            return attribute.getValue();
        }
        return null;
    }
    
    public Object getValue(String attributeCode) {
        AbstractAttribute attribute = getAttribute(attributeCode, null, null);
        if(attribute != null) {
            return attribute.getValue();
        }
        return null;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pk == null) ? 0 : pk.hashCode());
        result = prime * result + ((ranking == null) ? 0 : ranking.hashCode());
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
        ProductSkuStoreRel other = (ProductSkuStoreRel) obj;
        if (pk == null) {
            if (other.pk != null)
                return false;
        } else if (!pk.equals(other.pk))
            return false;
        if (ranking == null) {
            if (other.ranking != null)
                return false;
        } else if (!ranking.equals(other.ranking))
            return false;
        return true;
    }

}