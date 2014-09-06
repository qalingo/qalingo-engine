package org.hoteia.qalingo.core.pojo.retailer;

import org.hoteia.qalingo.core.pojo.AbstractPojoResponse;

public class RetailerPojoResponse extends AbstractPojoResponse {

    private RetailerPojo retailer;
    
    public RetailerPojo getRetailer() {
        return retailer;
    }
    
    public void setRetailer(RetailerPojo retailer) {
        this.retailer = retailer;
    }
    
}
