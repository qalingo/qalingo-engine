package org.hoteia.qalingo.core.fetchplan.catalog;

import java.util.ArrayList;
import java.util.List;

import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificAlias;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;

public class FetchPlanGraphCategory {

    public static FetchPlan virtualCategoriesWithoutProductsAndAssetsFetchPlan(){
        List<SpecificFetchMode> fetchplans = categoriesWithoutProductsAndAssetsFetchPlan();
        
        fetchplans.add(new SpecificFetchMode("categoryMaster"));
        
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan virtualCategoryWithProductsFetchPlan(){
        List<SpecificFetchMode> fetchplans = categoriesWithoutProductsAndAssetsFetchPlan();
        
        fetchplans.add(new SpecificFetchMode("categoryMaster"));
        fetchplans.add(new SpecificFetchMode("productMarketings"));
        fetchplans.add(new SpecificFetchMode("productMarketingAttributes", new SpecificAlias("productMarketings.productMarketingAttributes")));

        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultVirtualCatalogCategoryFetchPlan(){
        List<SpecificFetchMode> fetchplans = defaultCatalogCategoryFetchPlan();
        
        fetchplans.add(new SpecificFetchMode("categoryMaster"));
        fetchplans.add(new SpecificFetchMode("catalogCategoryType", new SpecificAlias("categoryMaster.catalogCategoryType")));

        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan masterCategoriesWithoutProductsAndAssetsFetchPlan(){
        List<SpecificFetchMode> fetchplans = categoriesWithoutProductsAndAssetsFetchPlan();
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan masterCategoryWithProductsFetchPlan(){
        List<SpecificFetchMode> fetchplans = categoriesWithoutProductsAndAssetsFetchPlan();
        
        fetchplans.add(new SpecificFetchMode("productMarketings"));
        fetchplans.add(new SpecificFetchMode("productMarketingAttributes", new SpecificAlias("productMarketings.productMarketingAttributes")));
        
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultMasterCatalogCategoryFetchPlan(){
        List<SpecificFetchMode> fetchplans = defaultCatalogCategoryFetchPlan();
        return new FetchPlan(fetchplans);
    }
    
    protected static List<SpecificFetchMode> categoriesWithoutProductsAndAssetsFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
        fetchplans.add(new SpecificFetchMode("defaultParentCatalogCategory"));
        fetchplans.add(new SpecificFetchMode("catalogCategoryAttributes"));
        fetchplans.add(new SpecificFetchMode("catalogCategories"));

        fetchplans.add(new SpecificFetchMode("subCatalogCategories", new SpecificAlias("catalogCategories.catalogCategories")));

//        fetchplans.add(new SpecificFetchMode("catalogSubCategoryAttributes", new SpecificAlias("catalogCategories.catalogCategoryAttributes")));
        
        return fetchplans;
    }
    
    protected static List<SpecificFetchMode> defaultCatalogCategoryFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
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