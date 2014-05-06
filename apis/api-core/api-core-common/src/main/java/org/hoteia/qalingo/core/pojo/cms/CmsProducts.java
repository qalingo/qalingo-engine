/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.cms;

import java.util.List;

import org.hoteia.qalingo.core.pojo.catalog.CatalogCategoryPojo;
import org.hoteia.qalingo.core.pojo.catalog.CatalogPojo;
import org.hoteia.qalingo.core.pojo.market.MarketAreaPojo;
import org.hoteia.qalingo.core.pojo.product.ProductMarketingPojo;

public class CmsProducts {

    protected MarketAreaPojo marketArea;
    protected CatalogPojo Catalog;
    protected CatalogCategoryPojo catalogCategory;
    protected List<ProductMarketingPojo> productMarketings;

    public MarketAreaPojo getMarketArea() {
        return marketArea;
    }
    
    public void setMarketArea(MarketAreaPojo marketArea) {
        this.marketArea = marketArea;
    }
    
    public CatalogPojo getCatalog() {
        return Catalog;
    }
    
    public void setCatalog(CatalogPojo catalog) {
        Catalog = catalog;
    }
    
    public CatalogCategoryPojo getCatalogCategory() {
        return catalogCategory;
    }
    
    public void setCatalogCategory(CatalogCategoryPojo catalogCategory) {
        this.catalogCategory = catalogCategory;
    }
    
    public List<ProductMarketingPojo> getProductMarketings() {
        return productMarketings;
    }
    
    public void setProductMarketings(List<ProductMarketingPojo> productMarketings) {
        this.productMarketings = productMarketings;
    }

}