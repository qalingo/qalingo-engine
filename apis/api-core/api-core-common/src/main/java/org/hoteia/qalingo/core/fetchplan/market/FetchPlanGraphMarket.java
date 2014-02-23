package org.hoteia.qalingo.core.fetchplan.market;

import java.util.ArrayList;
import java.util.List;

import org.hoteia.qalingo.core.fetchplan.SpecificAlias;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;

public class FetchPlanGraphMarket {

    public static List<SpecificFetchMode> getDefaultMarketPlaceFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
        fetchplans.add(new SpecificFetchMode("masterCatalog"));
        fetchplans.add(new SpecificFetchMode("markets"));
        fetchplans.add(new SpecificFetchMode("marketPlaceAttributes"));

        fetchplans.add(new SpecificFetchMode("marketAreas", new SpecificAlias("markets.marketAreas")));

        return fetchplans;
    }
    
    public static List<SpecificFetchMode> getDefaultMarketFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
        fetchplans.add(new SpecificFetchMode("marketPlace"));
        fetchplans.add(new SpecificFetchMode("marketAreas"));
        fetchplans.add(new SpecificFetchMode("marketAttributes"));
        
        return fetchplans;
    }
    
    public static List<SpecificFetchMode> getSpecificMarketFetch(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
        fetchplans.add(new SpecificFetchMode("marketPlace"));
        fetchplans.add(new SpecificFetchMode("marketAreas"));
        fetchplans.add(new SpecificFetchMode("marketAttributes"));
        
        fetchplans.add(new SpecificFetchMode("defaultLocalization", new SpecificAlias("marketAreas.defaultLocalization")));

        fetchplans.add(new SpecificFetchMode("localizations", new SpecificAlias("marketAreas.localizations")));

        fetchplans.add(new SpecificFetchMode("retailers", new SpecificAlias("marketAreas.retailers")));

        fetchplans.add(new SpecificFetchMode("marketAreaAttributes", new SpecificAlias("marketAreas.marketAreaAttributes")));
        
        return fetchplans;
    }
    
    public static List<SpecificFetchMode> getDefaultMarketAreaFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
        fetchplans.add(new SpecificFetchMode("catalog"));
        
        fetchplans.add(new SpecificFetchMode("catalogMaster", new SpecificAlias("catalog.catalogMaster")));

        fetchplans.add(new SpecificFetchMode("market"));

        fetchplans.add(new SpecificFetchMode("marketAreaAttributes"));

        fetchplans.add(new SpecificFetchMode("defaultLocalization"));
        fetchplans.add(new SpecificFetchMode("localizations"));

        fetchplans.add(new SpecificFetchMode("defaultRetailer"));
        fetchplans.add(new SpecificFetchMode("retailers"));
        
        fetchplans.add(new SpecificFetchMode("defaultCurrency"));
        fetchplans.add(new SpecificFetchMode("currencies"));

        return fetchplans;
    }
    
    public static List<SpecificFetchMode> getSpecificMarketAreaFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
        fetchplans.add(new SpecificFetchMode("catalog"));
        
        fetchplans.add(new SpecificFetchMode("catalogMaster", new SpecificAlias("catalog.catalogMaster")));

        fetchplans.add(new SpecificFetchMode("market"));

        fetchplans.add(new SpecificFetchMode("defaultCurrency"));
        fetchplans.add(new SpecificFetchMode("currencies"));

        fetchplans.add(new SpecificFetchMode("marketAreaAttributes"));

        fetchplans.add(new SpecificFetchMode("defaultLocalization"));
        fetchplans.add(new SpecificFetchMode("localizations"));

        fetchplans.add(new SpecificFetchMode("defaultRetailer"));
        fetchplans.add(new SpecificFetchMode("retailers"));

        fetchplans.add(new SpecificFetchMode("deliveryMethods"));

        fetchplans.add(new SpecificFetchMode("deliveryMethodCountries", new SpecificAlias("deliveryMethods.countries")));

        fetchplans.add(new SpecificFetchMode("deliveryMethodPrices", new SpecificAlias("deliveryMethods.prices")));

        fetchplans.add(new SpecificFetchMode("paymentGateways"));
        
        return fetchplans;
    }
}
