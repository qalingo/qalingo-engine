package org.hoteia.qalingo.core.pojo.catalog;

import java.util.ArrayList;
import java.util.List;

import org.hoteia.qalingo.core.pojo.AbstractPojoResponse;

public class CategoryListPojoResponse extends AbstractPojoResponse {

    private List<CatalogCategoryPojo> categories = new ArrayList<CatalogCategoryPojo>();
    
    public List<CatalogCategoryPojo> getCategories() {
        return categories;
    }
    
    public void setCategories(List<CatalogCategoryPojo> categories) {
        this.categories = categories;
    }
    
}
