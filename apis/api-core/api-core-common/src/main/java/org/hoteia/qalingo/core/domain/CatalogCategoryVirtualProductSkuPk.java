package org.hoteia.qalingo.core.domain;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class CatalogCategoryVirtualProductSkuPk extends AbstractEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 8745893320688286668L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VIRTUAL_CATEGORY_ID")
    private CatalogCategoryVirtual catalogCategoryVirtual;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_SKU_ID")
    private ProductSku productSku;
    
    public CatalogCategoryVirtualProductSkuPk() {
    }

    public CatalogCategoryVirtual getCatalogCategoryVirtual() {
        return catalogCategoryVirtual;
    }
    
    public void setCatalogCategoryVirtual(CatalogCategoryVirtual catalogCategoryVirtual) {
        this.catalogCategoryVirtual = catalogCategoryVirtual;
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
        result = prime * result + ((catalogCategoryVirtual == null) ? 0 : catalogCategoryVirtual.hashCode());
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
        CatalogCategoryVirtualProductSkuPk other = (CatalogCategoryVirtualProductSkuPk) obj;
        if (catalogCategoryVirtual == null) {
            if (other.catalogCategoryVirtual != null)
                return false;
        } else if (!catalogCategoryVirtual.equals(other.catalogCategoryVirtual))
            return false;
        if (productSku == null) {
            if (other.productSku != null)
                return false;
        } else if (!productSku.equals(other.productSku))
            return false;
        return true;
    }

}