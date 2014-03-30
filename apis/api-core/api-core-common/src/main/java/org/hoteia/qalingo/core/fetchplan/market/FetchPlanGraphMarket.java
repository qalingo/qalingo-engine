package org.hoteia.qalingo.core.fetchplan.market;

import java.util.ArrayList;
import java.util.List;

import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificAlias;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;

public class FetchPlanGraphMarket {

    public static FetchPlan defaultMarketPlaceFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
        fetchplans.add(new SpecificFetchMode("masterCatalog"));
        fetchplans.add(new SpecificFetchMode("markets"));
        fetchplans.add(new SpecificFetchMode("marketPlaceAttributes"));

        fetchplans.add(new SpecificFetchMode("marketAreas", new SpecificAlias("markets.marketAreas")));

        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultMarketFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
        fetchplans.add(new SpecificFetchMode("marketPlace"));
        fetchplans.add(new SpecificFetchMode("marketAreas"));
        fetchplans.add(new SpecificFetchMode("marketAttributes"));
        
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan specificMarketFetch(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
        fetchplans.add(new SpecificFetchMode("marketPlace"));
        fetchplans.add(new SpecificFetchMode("marketAreas"));
        fetchplans.add(new SpecificFetchMode("marketAttributes"));
        
        fetchplans.add(new SpecificFetchMode("defaultLocalization", new SpecificAlias("marketAreas.defaultLocalization")));

        fetchplans.add(new SpecificFetchMode("localizations", new SpecificAlias("marketAreas.localizations")));

        fetchplans.add(new SpecificFetchMode("retailers", new SpecificAlias("marketAreas.retailers")));

        fetchplans.add(new SpecificFetchMode("marketAreaAttributes", new SpecificAlias("marketAreas.marketAreaAttributes")));
        
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultMarketAreaFetchPlan(){
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

        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan specificMarketAreaFetchPlanWithCheckoutData(){
        return fullMarketAreaFetchPlan();
    }
    
    public static FetchPlan fullMarketAreaFetchPlan(){
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

        fetchplans.add(new SpecificFetchMode("deliveryMethods", new SpecificAlias("warehouses.deliveryMethods")));

        fetchplans.add(new SpecificFetchMode("deliveryMethodCountries", new SpecificAlias("deliveryMethods.countries")));

        fetchplans.add(new SpecificFetchMode("deliveryMethodPrices", new SpecificAlias("deliveryMethods.prices")));

        fetchplans.add(new SpecificFetchMode("paymentGateways"));
        
        return new FetchPlan(fetchplans);
    }
    
}
