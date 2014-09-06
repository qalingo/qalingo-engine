package org.hoteia.qalingo.core.pojo.catalog;

import java.util.ArrayList;
import java.util.List;

import org.hoteia.qalingo.core.pojo.AbstractPojoResponse;
import org.hoteia.qalingo.core.pojo.product.ProductMarketingPojo;

public class ProductListPojoResponse extends AbstractPojoResponse {

    private List<ProductMarketingPojo> productMarketings = new ArrayList<ProductMarketingPojo>();
    
    public List<ProductMarketingPojo> getProductMarketings() {
        return productMarketings;
    }
    
    public void setProductMarketings(List<ProductMarketingPojo> productMarketings) {
        this.productMarketings = productMarketings;
    }
    
}
