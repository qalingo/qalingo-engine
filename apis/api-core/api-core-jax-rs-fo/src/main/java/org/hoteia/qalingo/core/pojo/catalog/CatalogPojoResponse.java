package org.hoteia.qalingo.core.pojo.catalog;

import org.hoteia.qalingo.core.pojo.AbstractPojoResponse;

public class CatalogPojoResponse extends AbstractPojoResponse {

    private CatalogPojo catalog;
    
    public CatalogPojo getCatalog() {
        return catalog;
    }
    
    public void setCatalog(CatalogPojo catalog) {
        this.catalog = catalog;
    }
    
}
