package org.hoteia.qalingo.core.domain;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hoteia.qalingo.core.domain.impl.DomainEntity;

@Embeddable
public class ProductMarketingTagPk extends AbstractEntity<ProductMarketingTagPk> implements DomainEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 8745892210682286968L;

    @ManyToOne(fetch = FetchType.LAZY,  targetEntity = org.hoteia.qalingo.core.domain.ProductMarketing.class)
    @JoinColumn(name = "PRODUCT_SKU_ID")
    private ProductMarketing productMarketing;
    
    @ManyToOne(fetch = FetchType.LAZY,  targetEntity = org.hoteia.qalingo.core.domain.Tag.class)
    @JoinColumn(name = "TAG_ID")
    private Tag tag;
    
    public ProductMarketingTagPk() {
    }
    
    public ProductMarketingTagPk(final ProductMarketing productMarketing, final Tag tag) {
        this.productMarketing = productMarketing;
        this.tag = tag;
    }
    
    public ProductMarketing getProductMarketing() {
        return productMarketing;
    }

    public void setProductMarketing(ProductMarketing productMarketing) {
        this.productMarketing = productMarketing;
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
        result = prime * result + ((productMarketing == null) ? 0 : productMarketing.hashCode());
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
        ProductMarketingTagPk other = (ProductMarketingTagPk) obj;
        if (tag == null) {
            if (other.tag != null)
                return false;
        } else if (!tag.equals(other.tag))
            return false;
        if (productMarketing == null) {
            if (other.productMarketing != null)
                return false;
        } else if (!productMarketing.equals(other.productMarketing))
            return false;
        return true;
    }

}