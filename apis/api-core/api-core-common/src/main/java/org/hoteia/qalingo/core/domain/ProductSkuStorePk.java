package org.hoteia.qalingo.core.domain;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hoteia.qalingo.core.domain.impl.DomainEntity;

@Embeddable
public class ProductSkuStorePk extends AbstractEntity<ProductSkuStorePk> implements DomainEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 8745892515688286668L;

    @ManyToOne(fetch = FetchType.EAGER,  targetEntity = org.hoteia.qalingo.core.domain.ProductSku.class)
    @JoinColumn(name = "PRODUCT_SKU_ID")
    private ProductSku productSku;
    
    @ManyToOne(fetch = FetchType.EAGER,  targetEntity = org.hoteia.qalingo.core.domain.Store.class)
    @JoinColumn(name = "STORE_ID")
    private Store store;
    
    public ProductSkuStorePk() {
    }
    
    public ProductSkuStorePk(final ProductSku productSku, final Store store) {
        this.productSku = productSku;
        this.store = store;
    }
    
    public ProductSku getProductSku() {
        return productSku;
    }

    public void setProductSku(ProductSku productSku) {
        this.productSku = productSku;
    }
    
    public Store getStore() {
        return store;
    }
    
    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((productSku == null) ? 0 : productSku.hashCode());
        result = prime * result + ((store == null) ? 0 : store.hashCode());
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
        ProductSkuStorePk other = (ProductSkuStorePk) obj;
        if (store == null) {
            if (other.store != null)
                return false;
        } else if (!store.equals(other.store))
            return false;
        if (productSku == null) {
            if (other.productSku != null)
                return false;
        } else if (!productSku.equals(other.productSku))
            return false;
        return true;
    }

}