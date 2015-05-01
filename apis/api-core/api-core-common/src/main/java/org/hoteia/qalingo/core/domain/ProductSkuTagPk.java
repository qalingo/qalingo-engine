package org.hoteia.qalingo.core.domain;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class ProductSkuTagPk extends AbstractEntity<ProductSkuTagPk> {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 8745892210682286668L;

    @ManyToOne(fetch = FetchType.LAZY,  targetEntity = org.hoteia.qalingo.core.domain.ProductSku.class)
    @JoinColumn(name = "PRODUCT_SKU_ID")
    private ProductSku productSku;
    
    @ManyToOne(fetch = FetchType.LAZY,  targetEntity = org.hoteia.qalingo.core.domain.ProductSkuTag.class)
    @JoinColumn(name = "PRODUCT_SKU_TAG_ID")
    private ProductSkuTag productSkuTag;
    
    public ProductSkuTagPk() {
    }
    
    public ProductSkuTagPk(final ProductSku productSku, final ProductSkuTag productSkuTag) {
        this.productSku = productSku;
        this.productSkuTag = productSkuTag;
    }
    
    public ProductSku getProductSku() {
        return productSku;
    }

    public void setProductSku(ProductSku productSku) {
        this.productSku = productSku;
    }
    
    public ProductSkuTag getProductSkuTag() {
        return productSkuTag;
    }
    
    public void setProductSkuTag(ProductSkuTag productSkuTag) {
        this.productSkuTag = productSkuTag;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((productSku == null) ? 0 : productSku.hashCode());
        result = prime * result + ((productSkuTag == null) ? 0 : productSkuTag.hashCode());
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
        ProductSkuTagPk other = (ProductSkuTagPk) obj;
        if (productSkuTag == null) {
            if (other.productSkuTag != null)
                return false;
        } else if (!productSkuTag.equals(other.productSkuTag))
            return false;
        if (productSku == null) {
            if (other.productSku != null)
                return false;
        } else if (!productSku.equals(other.productSku))
            return false;
        return true;
    }

}