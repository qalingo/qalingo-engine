package org.hoteia.qalingo.core.fetchplan.catalog;

import java.util.ArrayList;
import java.util.List;

import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificAlias;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;

public class FetchPlanGraphCategory {

    public static FetchPlan getAllCategoriesWithoutProductsAndAssetsFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
        fetchplans.add(new SpecificFetchMode("categoryMaster"));
        fetchplans.add(new SpecificFetchMode("catalogCategoryType", new SpecificAlias("categoryMaster.catalogCategoryType")));

        fetchplans.add(new SpecificFetchMode("defaultParentCatalogCategory"));
        fetchplans.add(new SpecificFetchMode("catalogCategoryAttributes"));
        fetchplans.add(new SpecificFetchMode("catalogCategories"));

        fetchplans.add(new SpecificFetchMode("subCatalogCategories", new SpecificAlias("catalogCategories.catalogCategories")));

//        fetchplans.add(new SpecificFetchMode("catalogSubCategoryAttributes", new SpecificAlias("catalogCategories.catalogCategoryAttributes")));
        
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan getDefaultCatalogCategoryFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
        fetchplans.add(new SpecificFetchMode("categoryMaster"));
        fetchplans.add(new SpecificFetchMode("catalogCategoryType", new SpecificAlias("categoryMaster.catalogCategoryType")));

        fetchplans.add(new SpecificFetchMode("defaultParentCatalogCategory"));
        fetchplans.add(new SpecificFetchMode("catalogCategoryAttributes"));
        fetchplans.add(new SpecificFetchMode("catalogCategories"));
        
        fetchplans.add(new SpecificFetchMode("catalogSubCategoryAttributes", new SpecificAlias("catalogCategories.catalogCategoryAttributes")));

        fetchplans.add(new SpecificFetchMode("productMarketings"));

        fetchplans.add(new SpecificFetchMode("productMarketingAttributes", new SpecificAlias("productMarketings.productMarketingAttributes")));

        fetchplans.add(new SpecificFetchMode("assets"));

        return new FetchPlan(fetchplans);
    }
    
}