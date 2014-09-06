package org.hoteia.qalingo.core.pojo.retailer;

import java.util.ArrayList;
import java.util.List;

import org.hoteia.qalingo.core.pojo.AbstractPojoResponse;

public class RetailerListPojoResponse extends AbstractPojoResponse {

    private List<RetailerPojo> retailers = new ArrayList<RetailerPojo>();
    
    public List<RetailerPojo> getRetailers() {
        return retailers;
    }
    
    public void setRetailers(List<RetailerPojo> retailers) {
        this.retailers = retailers;
    }
    
}
