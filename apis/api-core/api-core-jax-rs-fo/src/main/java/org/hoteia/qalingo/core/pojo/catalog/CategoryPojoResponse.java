package org.hoteia.qalingo.core.pojo.catalog;

import org.hoteia.qalingo.core.pojo.AbstractPojoResponse;

public class CategoryPojoResponse extends AbstractPojoResponse {

    private CatalogCategoryPojo category;
    
    public CatalogCategoryPojo getCategory() {
        return category;
    }
    
    public void setCategory(CatalogCategoryPojo category) {
        this.category = category;
    }
    
}
