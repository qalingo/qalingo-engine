package org.hoteia.qalingo.core.pojo.cms;

import java.util.List;

import org.hoteia.qalingo.core.pojo.catalog.CatalogCategoryPojo;
import org.hoteia.qalingo.core.pojo.catalog.CatalogPojo;
import org.hoteia.qalingo.core.pojo.market.MarketAreaPojo;

public class CmsCategories {

    protected MarketAreaPojo marketArea;
    protected CatalogPojo Catalog;
    protected List<CatalogCategoryPojo> catalogCategories;

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

    public List<CatalogCategoryPojo> getCatalogCategories() {
        return catalogCategories;
    }

    public void setCatalogCategories(List<CatalogCategoryPojo> catalogCategories) {
        this.catalogCategories = catalogCategories;
    }

}