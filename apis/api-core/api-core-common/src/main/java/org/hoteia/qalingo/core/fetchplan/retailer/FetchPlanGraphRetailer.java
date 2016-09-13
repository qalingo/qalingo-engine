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
import org.hoteia.qalingo.core.domain.Retailer_;
import org.hoteia.qalingo.core.domain.Store_;
import org.hoteia.qalingo.core.domain.Company_;
import org.hoteia.qalingo.core.domain.CompanyStoreRel_;

public class FetchPlanGraphRetailer {
    
    public static FetchPlan defaultRetailerFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(Retailer_.links.getName()));
        fetchplans.add(new SpecificFetchMode(Retailer_.addresses.getName()));
        fetchplans.add(new SpecificFetchMode(Retailer_.stores.getName()));
        fetchplans.add(new SpecificFetchMode(Retailer_.assets.getName()));
        fetchplans.add(new SpecificFetchMode(Retailer_.attributes.getName()));
        fetchplans.add(new SpecificFetchMode(Retailer_.customerRates.getName()));
        fetchplans.add(new SpecificFetchMode(Retailer_.customerComments.getName()));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan fullRetailerFetchPlan() {
    	List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(Retailer_.links.getName()));
        fetchplans.add(new SpecificFetchMode(Retailer_.addresses.getName()));
        fetchplans.add(new SpecificFetchMode(Retailer_.stores.getName()));
        fetchplans.add(new SpecificFetchMode(Retailer_.assets.getName()));
        fetchplans.add(new SpecificFetchMode(Retailer_.attributes.getName()));
        fetchplans.add(new SpecificFetchMode(Retailer_.customerRates.getName()));
        fetchplans.add(new SpecificFetchMode(Retailer_.customerComments.getName()));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultStoreFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(Store_.attributes.getName()));
        fetchplans.add(new SpecificFetchMode(Store_.assets.getName()));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan fullStoreFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(Store_.attributes.getName()));
        fetchplans.add(new SpecificFetchMode(Store_.assets.getName()));
        fetchplans.add(new SpecificFetchMode(Store_.businessHours.getName()));
        fetchplans.add(new SpecificFetchMode(Store_.retailer.getName()));
        fetchplans.add(new SpecificFetchMode(Store_.productBrands.getName()));
        fetchplans.add(new SpecificFetchMode(Store_.companyStoreRels.getName()));
        fetchplans.add(new SpecificFetchMode(Store_.companyStoreRels.getName() + "." + CompanyStoreRel_.pk.getName()));
//        fetchplans.add(new SpecificFetchMode(Store_.retailer.getName() + "." + Retailer_.company.getName() + "." + Company_.users.getName()));
        return new FetchPlan(fetchplans);
    }
    
}