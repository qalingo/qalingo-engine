package org.hoteia.qalingo.core.pojo.catalog;

import org.hoteia.qalingo.core.pojo.AbstractPojoResponse;
import org.hoteia.qalingo.core.pojo.product.ProductMarketingPojo;

public class ProductPojoResponse extends AbstractPojoResponse {

    private ProductMarketingPojo productMarketing;
    
    public ProductMarketingPojo getProductMarketing() {
        return productMarketing;
    }
    
    public void setProductMarketing(ProductMarketingPojo productMarketing) {
        this.productMarketing = productMarketing;
    }
    
}
