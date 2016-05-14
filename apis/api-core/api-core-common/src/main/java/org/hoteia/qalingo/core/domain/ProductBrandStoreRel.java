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
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "TECO_PRODUCT_BRAND_STORE_REL")
@AssociationOverrides({
    @AssociationOverride(name = "pk.productBrand", joinColumns = @JoinColumn(name = "PRODUCT_BRAND_ID")),
    @AssociationOverride(name = "pk.store", joinColumns = @JoinColumn(name = "STORE_ID"))})
public class ProductBrandStoreRel extends AbstractEntity<ProductBrandStoreRel> {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 9179501918678304872L;

    @EmbeddedId
    private ProductBrandStorePk pk;
    
    @Column(name = "RANKING")
    private Integer ranking;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductBrandStoreAttribute.class)
    @JoinColumns({
        @JoinColumn(name = "PRODUCT_BRAND_ID"),
        @JoinColumn(name = "STORE_ID")
    })
    private Set<ProductBrandStoreAttribute> attributes = new HashSet<ProductBrandStoreAttribute>();
    
    public ProductBrandStoreRel() {
        this.pk = new ProductBrandStorePk();
    }
    
    public ProductBrandStoreRel(final ProductBrand productBrand, final Store store) {
        this.pk = new ProductBrandStorePk(productBrand, store);
    }

    public ProductBrandStorePk getPk() {
        return pk;
    }

    public void setPk(ProductBrandStorePk pk) {
        this.pk = pk;
    }

    @Transient
    public ProductBrand getProductBrand() {
        return getPk().getProductBrand();
    }

    public void setProductBrand(final ProductBrand productBrand) {
        pk.setProductBrand(productBrand);
    }
    
    @Transient
    public Store getgetStore() {
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
    
    public Set<ProductBrandStoreAttribute> getAttributes() {
		return attributes;
	}
    
    public void setAttributes(Set<ProductBrandStoreAttribute> attributes) {
		this.attributes = attributes;
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
        ProductBrandStoreRel other = (ProductBrandStoreRel) obj;
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