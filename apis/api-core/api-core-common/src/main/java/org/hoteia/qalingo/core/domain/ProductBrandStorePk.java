package org.hoteia.qalingo.core.domain;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hoteia.qalingo.core.domain.impl.DomainEntity;

@Embeddable
public class ProductBrandStorePk extends AbstractEntity<ProductBrandStorePk> implements DomainEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 8745232210682286968L;

    @ManyToOne(fetch = FetchType.LAZY,  targetEntity = org.hoteia.qalingo.core.domain.ProductBrand.class)
    @JoinColumn(name = "PRODUCT_BRAND_ID")
    private ProductBrand productBrand;

    @ManyToOne(fetch = FetchType.LAZY,  targetEntity = org.hoteia.qalingo.core.domain.Store.class)
    @JoinColumn(name = "STORE_ID")
    private Store store;

    public ProductBrandStorePk() {
    }
    
    public ProductBrandStorePk(final ProductBrand productBrand, final Store store) {
        this.productBrand = productBrand;
        this.store = store;
    }
    
    public ProductBrand getProductBrand() {
		return productBrand;
	}
    
    public void setProductBrand(ProductBrand productBrand) {
		this.productBrand = productBrand;
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
        result = prime * result + ((productBrand == null) ? 0 : productBrand.hashCode());
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
        ProductBrandStorePk other = (ProductBrandStorePk) obj;
        if (store == null) {
            if (other.store != null)
                return false;
        } else if (!store.equals(other.store))
            return false;
        if (productBrand == null) {
            if (other.productBrand != null)
                return false;
        } else if (!productBrand.equals(other.productBrand))
            return false;
        return true;
    }

}