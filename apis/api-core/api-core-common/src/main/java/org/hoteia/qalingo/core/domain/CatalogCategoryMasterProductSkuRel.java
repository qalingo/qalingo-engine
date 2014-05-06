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

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "TECO_CATALOG_MASTER_CATEGORY_PRODUCT_SKU_REL")
@AssociationOverrides({
    @AssociationOverride(name = "pk.catalogCategoryMaster", joinColumns = @JoinColumn(name = "MASTER_CATEGORY_ID")),
    @AssociationOverride(name = "pk.productSku", joinColumns = @JoinColumn(name = "PRODUCT_SKU_ID")) })
public class CatalogCategoryMasterProductSkuRel extends AbstractEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 2322332601183745543L;

    @EmbeddedId
    private CatalogCategoryMasterProductSkuPk pk;
    
    @Column(name = "RANKING")
    private Integer ranking;
    
    @Column(name = "IS_DEFAULT_CATEGORY", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isDefaultCategory;

    @Column(name = "IS_DEFAULT_SKU", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isDefaultSku;
    
    public CatalogCategoryMasterProductSkuRel() {
    }
    
    public CatalogCategoryMasterProductSkuRel(final CatalogCategoryMaster catalogCategoryMaster, final ProductSku productSku) {
        this.pk = new CatalogCategoryMasterProductSkuPk(catalogCategoryMaster, productSku);
    }

    public CatalogCategoryMasterProductSkuPk getPk() {
        return pk;
    }

    public void setPk(CatalogCategoryMasterProductSkuPk pk) {
        this.pk = pk;
    }

    @Transient
    public CatalogCategoryMaster getCatalogCategoryMaster() {
        return getPk().getCatalogCategoryMaster();
    }

    public void setCatalogCategoryMaster(final CatalogCategoryMaster catalogCategoryMaster) {
        pk.setCatalogCategoryMaster(catalogCategoryMaster);
    }

    @Transient
    public ProductSku getProductSku() {
        return getPk().getProductSku();
    }

    public void setProductSku(final ProductSku productSku) {
        pk.setProductSku(productSku);
    }
    
    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public boolean isDefaultCategory() {
        return isDefaultCategory;
    }

    public void setDefaultCategory(boolean isDefaultCategory) {
        this.isDefaultCategory = isDefaultCategory;
    }

    public boolean isDefaultSku() {
        return isDefaultSku;
    }

    public void setDefaultSku(boolean isDefaultSku) {
        this.isDefaultSku = isDefaultSku;
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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CatalogCategoryMasterProductSkuRel other = (CatalogCategoryMasterProductSkuRel) obj;
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