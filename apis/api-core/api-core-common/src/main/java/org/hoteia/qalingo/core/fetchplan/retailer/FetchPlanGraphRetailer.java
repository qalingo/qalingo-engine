/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.fetchplan.retailer;

import java.util.ArrayList;
import java.util.List;

import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;

public class FetchPlanGraphRetailer {
    
    public static FetchPlan defaultRetailerFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode("links"));
        fetchplans.add(new SpecificFetchMode("addresses"));
        fetchplans.add(new SpecificFetchMode("stores"));
        fetchplans.add(new SpecificFetchMode("assets"));
        fetchplans.add(new SpecificFetchMode("retailerAttributes"));
        fetchplans.add(new SpecificFetchMode("customerRates"));
        fetchplans.add(new SpecificFetchMode("customerComments"));
        fetchplans.add(new SpecificFetchMode("retailerTags"));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan fullRetailerFetchPlan() {
    	List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode("links"));
        fetchplans.add(new SpecificFetchMode("addresses"));
        fetchplans.add(new SpecificFetchMode("stores"));
        fetchplans.add(new SpecificFetchMode("assets"));
        fetchplans.add(new SpecificFetchMode("retailerAttributes"));
        fetchplans.add(new SpecificFetchMode("customerRates"));
        fetchplans.add(new SpecificFetchMode("customerComments"));
        fetchplans.add(new SpecificFetchMode("retailerTags"));
        fetchplans.add(new SpecificFetchMode("warehouse"));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultStoreFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode("storeAttributes"));
        fetchplans.add(new SpecificFetchMode("assets"));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan fullStoreFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode("retailer"));
        fetchplans.add(new SpecificFetchMode("storeAttributes"));
        fetchplans.add(new SpecificFetchMode("assets"));
        fetchplans.add(new SpecificFetchMode("businessHours"));
        return new FetchPlan(fetchplans);
    }
    
}