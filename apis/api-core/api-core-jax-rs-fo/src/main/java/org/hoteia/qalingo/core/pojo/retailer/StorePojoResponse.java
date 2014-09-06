package org.hoteia.qalingo.core.pojo.retailer;

import org.hoteia.qalingo.core.pojo.AbstractPojoResponse;
import org.hoteia.qalingo.core.pojo.store.StorePojo;

public class StorePojoResponse extends AbstractPojoResponse {

    private StorePojo store;
    
    public StorePojo getStore() {
        return store;
    }
    
    public void setStore(StorePojo store) {
        this.store = store;
    }
    
}
