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