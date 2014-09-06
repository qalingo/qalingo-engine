package org.hoteia.qalingo.core.pojo.catalog;

import java.util.ArrayList;
import java.util.List;

import org.hoteia.qalingo.core.pojo.AbstractPojoResponse;

public class CatalogListPojoResponse extends AbstractPojoResponse {

    private List<CatalogPojo> catalogs = new ArrayList<CatalogPojo>();
    
    public List<CatalogPojo> getCatalogs() {
        return catalogs;
    }
    
    public void setCatalogs(List<CatalogPojo> catalogs) {
        this.catalogs = catalogs;
    }
    
}
