/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.fetchplan.market;

import java.util.ArrayList;
import java.util.List;

import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificAlias;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;

import org.hoteia.qalingo.core.domain.MarketPlace_;
import org.hoteia.qalingo.core.domain.Market_;
import org.hoteia.qalingo.core.domain.MarketArea_;
import org.hoteia.qalingo.core.domain.CatalogVirtual_;
import org.hoteia.qalingo.core.domain.CatalogMaster_;

public class FetchPlanGraphMarket {

    public static FetchPlan defaultMarketPlaceFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
        fetchplans.add(new SpecificFetchMode(MarketPlace_.attributes.getName()));
        fetchplans.add(new SpecificFetchMode(MarketPlace_.masterCatalog.getName()));

        fetchplans.add(new SpecificFetchMode(MarketPlace_.markets.getName()));
        fetchplans.add(new SpecificFetchMode(MarketPlace_.markets.getName() + "." + Market_.marketAreas.getName()));

        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan requestInitMarketPlaceFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
        fetchplans.add(new SpecificFetchMode(MarketPlace_.attributes.getName()));
        fetchplans.add(new SpecificFetchMode(MarketPlace_.masterCatalog.getName()));

        fetchplans.add(new SpecificFetchMode(MarketPlace_.markets.getName()));
        fetchplans.add(new SpecificFetchMode(MarketPlace_.markets.getName() + "." + Market_.marketAreas.getName()));
        fetchplans.add(new SpecificFetchMode(MarketPlace_.markets.getName() + "." + Market_.marketAreas.getName() + "." + MarketArea_.defaultLocalization.getName()));
        fetchplans.add(new SpecificFetchMode(MarketPlace_.markets.getName() + "." + Market_.marketAreas.getName() + "." + MarketArea_.localizations.getName()));

        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultMarketFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
        fetchplans.add(new SpecificFetchMode(Market_.marketPlace.getName()));
        fetchplans.add(new SpecificFetchMode(Market_.marketAreas.getName()));
        fetchplans.add(new SpecificFetchMode(Market_.attributes.getName()));
        
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan requestInitMarketFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
        fetchplans.add(new SpecificFetchMode(Market_.attributes.getName()));

        fetchplans.add(new SpecificFetchMode(Market_.marketPlace.getName()));
        
        fetchplans.add(new SpecificFetchMode(Market_.marketAreas.getName()));
        fetchplans.add(new SpecificFetchMode(Market_.marketAreas.getName() + "." + MarketArea_.defaultLocalization.getName()));
        fetchplans.add(new SpecificFetchMode(Market_.marketAreas.getName() + "." + MarketArea_.localizations.getName()));
        
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan fullMarketFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
        fetchplans.add(new SpecificFetchMode(Market_.marketPlace.getName()));
        fetchplans.add(new SpecificFetchMode(Market_.marketAreas.getName()));
        fetchplans.add(new SpecificFetchMode(Market_.attributes.getName()));
        
        fetchplans.add(new SpecificFetchMode(Market_.marketAreas.getName() + "." + MarketArea_.defaultLocalization.getName()));
        fetchplans.add(new SpecificFetchMode(Market_.marketAreas.getName() + "." + MarketArea_.localizations.getName()));
        fetchplans.add(new SpecificFetchMode(Market_.marketAreas.getName() + "." + MarketArea_.retailers.getName()));
        fetchplans.add(new SpecificFetchMode(Market_.marketAreas.getName() + "." + MarketArea_.attributes.getName()));
        
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultMarketAreaFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
        fetchplans.add(new SpecificFetchMode(MarketArea_.catalog.getName()));
        fetchplans.add(new SpecificFetchMode(MarketArea_.catalog.getName() + "." + CatalogVirtual_.catalogMaster.getName()));
        
        fetchplans.add(new SpecificFetchMode(MarketArea_.market.getName()));
        
        fetchplans.add(new SpecificFetchMode(MarketArea_.attributes.getName()));

        fetchplans.add(new SpecificFetchMode(MarketArea_.defaultLocalization.getName()));
        fetchplans.add(new SpecificFetchMode(MarketArea_.localizations.getName()));

        fetchplans.add(new SpecificFetchMode(MarketArea_.defaultRetailer.getName()));
        fetchplans.add(new SpecificFetchMode(MarketArea_.retailers.getName()));
        
        fetchplans.add(new SpecificFetchMode(MarketArea_.defaultCurrency.getName()));
        fetchplans.add(new SpecificFetchMode(MarketArea_.currencies.getName()));

        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan specificMarketAreaFetchPlanWithCheckoutData(){
        return fullMarketAreaFetchPlan();
    }
    
    public static FetchPlan fullMarketAreaFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
        fetchplans.add(new SpecificFetchMode(MarketArea_.catalog.getName()));
        fetchplans.add(new SpecificFetchMode(MarketArea_.catalog.getName() + "." + CatalogVirtual_.catalogMaster.getName()));
        
        fetchplans.add(new SpecificFetchMode(MarketArea_.market.getName()));
        
        fetchplans.add(new SpecificFetchMode(MarketArea_.attributes.getName()));

        fetchplans.add(new SpecificFetchMode(MarketArea_.defaultLocalization.getName()));
        fetchplans.add(new SpecificFetchMode(MarketArea_.localizations.getName()));
        
        fetchplans.add(new SpecificFetchMode(MarketArea_.defaultRetailer.getName()));
        fetchplans.add(new SpecificFetchMode(MarketArea_.retailers.getName()));
        
        fetchplans.add(new SpecificFetchMode(MarketArea_.defaultCurrency.getName()));
        fetchplans.add(new SpecificFetchMode(MarketArea_.currencies.getName()));

        fetchplans.add(new SpecificFetchMode(MarketArea_.paymentGateways.getName()));
        fetchplans.add(new SpecificFetchMode(MarketArea_.taxes.getName()));

        fetchplans.add(new SpecificFetchMode(MarketArea_.warehouseMarketAreaRels.getName()));

        fetchplans.add(new SpecificFetchMode("deliveryMethods", new SpecificAlias("warehouseMarketAreaRel.pk.warehouse.deliveryMethods")));

        fetchplans.add(new SpecificFetchMode("deliveryMethodCountries", new SpecificAlias("warehouseMarketAreaRel.pk.warehouse.deliveryMethods.countries")));

        fetchplans.add(new SpecificFetchMode("deliveryMethodPrices", new SpecificAlias("warehouseMarketAreaRel.pk.warehouse.deliveryMethods.prices")));

        return new FetchPlan(fetchplans);
    }
    
}
