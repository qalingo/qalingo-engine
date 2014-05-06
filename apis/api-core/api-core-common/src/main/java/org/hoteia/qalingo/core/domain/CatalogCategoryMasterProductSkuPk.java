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

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class CatalogCategoryMasterProductSkuPk extends AbstractEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 8745893320688286668L;

    @ManyToOne(fetch = FetchType.LAZY,  targetEntity = org.hoteia.qalingo.core.domain.CatalogCategoryMaster.class)
    @JoinColumn(name = "MASTER_CATEGORY_ID")
    private CatalogCategoryMaster catalogCategoryMaster;
    
    @ManyToOne(fetch = FetchType.LAZY,  targetEntity = org.hoteia.qalingo.core.domain.ProductSku.class)
    @JoinColumn(name = "PRODUCT_SKU_ID")
    private ProductSku productSku;
    
    public CatalogCategoryMasterProductSkuPk() {
    }
    
    public CatalogCategoryMasterProductSkuPk(final CatalogCategoryMaster catalogCategoryMaster, final ProductSku productSku) {
        this.catalogCategoryMaster = catalogCategoryMaster;
        this.productSku = productSku;
    }

    public CatalogCategoryMaster getCatalogCategoryMaster() {
        return catalogCategoryMaster;
    }
    
    public void setCatalogCategoryMaster(CatalogCategoryMaster catalogCategoryMaster) {
        this.catalogCategoryMaster = catalogCategoryMaster;
    }

    public ProductSku getProductSku() {
        return productSku;
    }

    public void setProductSku(ProductSku productSku) {
        this.productSku = productSku;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((catalogCategoryMaster == null) ? 0 : catalogCategoryMaster.hashCode());
        result = prime * result + ((productSku == null) ? 0 : productSku.hashCode());
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
        CatalogCategoryMasterProductSkuPk other = (CatalogCategoryMasterProductSkuPk) obj;
        if (catalogCategoryMaster == null) {
            if (other.catalogCategoryMaster != null)
                return false;
        } else if (!catalogCategoryMaster.equals(other.catalogCategoryMaster))
            return false;
        if (productSku == null) {
            if (other.productSku != null)
                return false;
        } else if (!productSku.equals(other.productSku))
            return false;
        return true;
    }

}