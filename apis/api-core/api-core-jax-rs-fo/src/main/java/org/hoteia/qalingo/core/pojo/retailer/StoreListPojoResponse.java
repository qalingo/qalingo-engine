package org.hoteia.qalingo.core.pojo.retailer;

import java.util.ArrayList;
import java.util.List;

import org.hoteia.qalingo.core.pojo.AbstractPojoResponse;
import org.hoteia.qalingo.core.pojo.store.StorePojo;

public class StoreListPojoResponse extends AbstractPojoResponse {

    private List<StorePojo> stores = new ArrayList<StorePojo>();
    
    public List<StorePojo> getStores() {
        return stores;
    }
    
    public void setStores(List<StorePojo> stores) {
        this.stores = stores;
    }
    
}
