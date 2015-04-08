package org.hoteia.qalingo.core.domain;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class ProductSkuOptionPk extends AbstractEntity<ProductSkuOptionPk> {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 8745892210688286668L;

    @ManyToOne(fetch = FetchType.LAZY,  targetEntity = org.hoteia.qalingo.core.domain.ProductSku.class)
    @JoinColumn(name = "PRODUCT_SKU_ID")
    private ProductSku productSku;
    
    @ManyToOne(fetch = FetchType.LAZY,  targetEntity = org.hoteia.qalingo.core.domain.ProductSkuOptionDefinition.class)
    @JoinColumn(name = "PRODUCT_SKU_OPTION_DEFINITION_ID")
    private ProductSkuOptionDefinition productSkuOptionDefinition;
    
    public ProductSkuOptionPk() {
    }
    
    public ProductSkuOptionPk(final ProductSku productSku, final ProductSkuOptionDefinition productSkuOptionDefinition) {
        this.productSku = productSku;
        this.productSkuOptionDefinition = productSkuOptionDefinition;
    }
    
    public ProductSkuOptionDefinition getProductSkuOptionDefinition() {
		return productSkuOptionDefinition;
	}
    
    public void setProductSkuOptionDefinition(ProductSkuOptionDefinition productSkuOptionDefinition) {
		this.productSkuOptionDefinition = productSkuOptionDefinition;
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
        result = prime * result + ((productSku == null) ? 0 : productSku.hashCode());
        result = prime * result + ((productSkuOptionDefinition == null) ? 0 : productSkuOptionDefinition.hashCode());
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
        ProductSkuOptionPk other = (ProductSkuOptionPk) obj;
        if (productSkuOptionDefinition == null) {
            if (other.productSkuOptionDefinition != null)
                return false;
        } else if (!productSkuOptionDefinition.equals(other.productSkuOptionDefinition))
            return false;
        if (productSku == null) {
            if (other.productSku != null)
                return false;
        } else if (!productSku.equals(other.productSku))
            return false;
        return true;
    }

}
