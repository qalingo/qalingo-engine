package org.hoteia.qalingo.core.fetchplan.catalog;

import java.util.ArrayList;
import java.util.List;

import org.hoteia.qalingo.core.fetchplan.SpecificAlias;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;

public class FetchPlanGraphCategory {

    public static List<SpecificFetchMode> getAllCategoriesWithouProductsAndAssetsFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
        fetchplans.add(new SpecificFetchMode("categoryMaster"));
        fetchplans.add(new SpecificFetchMode("defaultParentCatalogCategory"));
        fetchplans.add(new SpecificFetchMode("catalogCategoryAttributes"));
        fetchplans.add(new SpecificFetchMode("catalogCategories"));

        fetchplans.add(new SpecificFetchMode("subCatalogCategories", new SpecificAlias("catalogCategories.catalogCategories")));

//        fetchplans.add(new SpecificFetchMode("catalogSubCategoryAttributes", new SpecificAlias("catalogCategories.catalogCategoryAttributes")));
        
        return fetchplans;
    }
    
    public static List<SpecificFetchMode> getDefaultCatalogCategoryFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
        fetchplans.add(new SpecificFetchMode("categoryMaster"));
        fetchplans.add(new SpecificFetchMode("defaultParentCatalogCategory"));
        fetchplans.add(new SpecificFetchMode("catalogCategoryAttributes"));
        fetchplans.add(new SpecificFetchMode("catalogCategories"));
        
        fetchplans.add(new SpecificFetchMode("catalogSubCategoryAttributes", new SpecificAlias("catalogCategories.catalogCategoryAttributes")));

        fetchplans.add(new SpecificFetchMode("productMarketings"));

        fetchplans.add(new SpecificFetchMode("productMarketingAttributes", new SpecificAlias("productMarketings.productMarketingAttributes")));

        fetchplans.add(new SpecificFetchMode("assets"));

        return fetchplans;
    }
    
}