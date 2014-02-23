package org.hoteia.qalingo.core.fetchplan.catalog;

import java.util.ArrayList;
import java.util.List;

import org.hoteia.qalingo.core.fetchplan.SpecificAlias;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;

public class FetchPlanGraphProduct {

    public static List<SpecificFetchMode> getDefaultProductMarketingFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode("productBrand"));
        fetchplans.add(new SpecificFetchMode("productMarketingType"));
        fetchplans.add(new SpecificFetchMode("productMarketingAttributes"));
        fetchplans.add(new SpecificFetchMode("productSkus"));
        fetchplans.add(new SpecificFetchMode("prices", new SpecificAlias("productSkus.prices")));
        fetchplans.add(new SpecificFetchMode("currency", new SpecificAlias("productSkus.prices.currency")));
        fetchplans.add(new SpecificFetchMode("productAssociationLinks"));
        fetchplans.add(new SpecificFetchMode("assets"));
        fetchplans.add(new SpecificFetchMode("defaultCatalogCategory"));
        return fetchplans;
    }
    
    public static List<SpecificFetchMode> getDefaultProductSkuFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode("productSkuAttributes"));
        fetchplans.add(new SpecificFetchMode("productMarketing"));
        fetchplans.add(new SpecificFetchMode("assets"));
        fetchplans.add(new SpecificFetchMode("prices"));
        fetchplans.add(new SpecificFetchMode("currency", new SpecificAlias("prices.currency")));
        fetchplans.add(new SpecificFetchMode("stocks"));
        fetchplans.add(new SpecificFetchMode("retailers"));
        return fetchplans;
    }
    
    public static List<SpecificFetchMode> getDefaultProductBrandFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        return fetchplans;
    }

}