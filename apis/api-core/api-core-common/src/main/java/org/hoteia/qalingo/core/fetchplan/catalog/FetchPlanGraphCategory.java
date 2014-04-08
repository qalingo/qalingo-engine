package org.hoteia.qalingo.core.fetchplan.catalog;

import java.util.ArrayList;
import java.util.List;

import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificAlias;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;

public class FetchPlanGraphCategory {

    // MASTER
    
    public static FetchPlan masterCategoriesWithoutProductsAndAssetsFetchPlan(){
        List<SpecificFetchMode> fetchplans = categoriesWithoutProductsAndAssetsFetchPlan();
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan masterCategoryWithProductsFetchPlan(){
        List<SpecificFetchMode> fetchplans = defaultCatalogCategoryFetchPlan();
        
//        fetchplans.add(new SpecificFetchMode("catalogCategoryVirtualProductSkuRels"));
//        fetchplans.add(new SpecificFetchMode("productSku", new SpecificAlias("catalogCategoryVirtualProductSkuRels.pk.productSku")));
//        fetchplans.add(new SpecificFetchMode("productMarketing", new SpecificAlias("productSku.productMarketing")));
//        fetchplans.add(new SpecificFetchMode("productMarketingAttributes", new SpecificAlias("productMarketing.productMarketingAttributes")));

//        fetchplans.add(new SpecificFetchMode("assets"));

        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultMasterCatalogCategoryFetchPlan(){
        List<SpecificFetchMode> fetchplans = defaultCatalogCategoryFetchPlan();
        return new FetchPlan(fetchplans);
    }
    
    // VIRTUAL
    
    public static FetchPlan virtualCategoriesWithoutProductsAndAssetsFetchPlan(){
        List<SpecificFetchMode> fetchplans = categoriesWithoutProductsAndAssetsFetchPlan();
        
        fetchplans.add(new SpecificFetchMode("categoryMaster"));
        
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan virtualCategoryWithProductsAndAssetsFetchPlan(){
        List<SpecificFetchMode> fetchplans = catalogCategoryWithSubCategoriesFetchPlan();
        
        fetchplans.add(new SpecificFetchMode("categoryMaster"));
        
        fetchplans.add(new SpecificFetchMode("catalogCategoryVirtualProductSkuRels"));
        fetchplans.add(new SpecificFetchMode("productSku", new SpecificAlias("catalogCategoryVirtualProductSkuRels.pk.productSku")));

        fetchplans.add(new SpecificFetchMode("assets"));

        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultVirtualCatalogCategoryFetchPlan(){
        List<SpecificFetchMode> fetchplans = defaultCatalogCategoryFetchPlan();
        
        fetchplans.add(new SpecificFetchMode("categoryMaster"));
        fetchplans.add(new SpecificFetchMode("catalogCategoryType", new SpecificAlias("categoryMaster.catalogCategoryType")));

        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan menuCatalogCategoryFetchPlan(){
        List<SpecificFetchMode> fetchplans = catalogCategoryWithSubCategoriesFetchPlan();
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan footerCatalogCategoryFetchPlan(){
        List<SpecificFetchMode> fetchplans = catalogCategoryWithSubCategoriesFetchPlan();
        return new FetchPlan(fetchplans);
    }
    
    protected static List<SpecificFetchMode> categoriesWithoutProductsAndAssetsFetchPlan(){
        List<SpecificFetchMode> fetchplans = catalogCategoryWithSubCategoriesFetchPlan();
        
//        fetchplans.add(new SpecificFetchMode("subCatalogCategories", new SpecificAlias("catalogCategories.catalogCategories")));
//        fetchplans.add(new SpecificFetchMode("catalogSubCategoryAttributes", new SpecificAlias("catalogCategories.catalogCategoryAttributes")));
        
        return fetchplans;
    }

    protected static List<SpecificFetchMode> catalogCategoryWithSubCategoriesFetchPlan(){
        List<SpecificFetchMode> fetchplans = defaultCatalogCategoryFetchPlan();
        
        fetchplans.add(new SpecificFetchMode("childVirtualCategoryRels"));
        fetchplans.add(new SpecificFetchMode("childCatalogCategoryVirtual", new SpecificAlias("childVirtualCategoryRels.pk.childCatalogCategoryVirtual")));
        fetchplans.add(new SpecificFetchMode("childCatalogCategoryAttributes", new SpecificAlias("childCatalogCategoryVirtual.catalogCategoryAttributes")));

        return fetchplans;
    }
    
    protected static List<SpecificFetchMode> defaultCatalogCategoryFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
        fetchplans.add(new SpecificFetchMode("defaultParentCatalogCategory"));
        fetchplans.add(new SpecificFetchMode("catalogCategoryAttributes"));
        
//        fetchplans.add(new SpecificFetchMode("childVirtualCategoryRels"));
//        fetchplans.add(new SpecificFetchMode("childCatalogCategoryVirtual", new SpecificAlias("childVirtualCategoryRels.pk.childCatalogCategoryVirtual")));
//        fetchplans.add(new SpecificFetchMode("childCatalogCategoryAttributes", new SpecificAlias("childCatalogCategoryVirtual.catalogCategoryAttributes")));
        
//        fetchplans.add(new SpecificFetchMode("catalogSubCategoryAttributes", new SpecificAlias("catalogCategories.catalogCategoryAttributes")));

        fetchplans.add(new SpecificFetchMode("assets"));

        return fetchplans;
    }
    
}