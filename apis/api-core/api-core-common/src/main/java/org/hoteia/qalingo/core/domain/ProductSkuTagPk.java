package org.hoteia.qalingo.core.domain;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hoteia.qalingo.core.domain.impl.DomainEntity;

@Embeddable
public class ProductSkuTagPk extends AbstractEntity<ProductSkuTagPk> implements DomainEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 8745892210682286668L;

    @ManyToOne(fetch = FetchType.LAZY,  targetEntity = org.hoteia.qalingo.core.domain.ProductSku.class)
    @JoinColumn(name = "PRODUCT_SKU_ID")
    private ProductSku productSku;
    
    @ManyToOne(fetch = FetchType.LAZY,  targetEntity = org.hoteia.qalingo.core.domain.Tag.class)
    @JoinColumn(name = "TAG_ID")
    private Tag tag;
    
    public ProductSkuTagPk() {
    }
    
    public ProductSkuTagPk(final ProductSku productSku, final Tag tag) {
        this.productSku = productSku;
        this.tag = tag;
    }
    
    public ProductSku getProductSku() {
        return productSku;
    }

    public void setProductSku(ProductSku productSku) {
        this.productSku = productSku;
    }
    
    public Tag getTag() {
        return tag;
    }
    
    public void setTag(Tag tag) {
        this.tag = tag;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((productSku == null) ? 0 : productSku.hashCode());
        result = prime * result + ((tag == null) ? 0 : tag.hashCode());
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
        if (tag == null) {
            if (other.tag != null)
                return false;
        } else if (!tag.equals(other.tag))
            return false;
        if (productSku == null) {
            if (other.productSku != null)
                return false;
        } else if (!productSku.equals(other.productSku))
            return false;
        return true;
    }

}