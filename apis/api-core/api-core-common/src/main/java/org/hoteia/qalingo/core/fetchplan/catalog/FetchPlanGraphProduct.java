/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.fetchplan.catalog;

import java.util.ArrayList;
import java.util.List;

import org.hoteia.qalingo.core.domain.ProductMarketing_;
import org.hoteia.qalingo.core.domain.ProductSku_;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;

public class FetchPlanGraphProduct {

    public static FetchPlan productMarketingDefaultFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(ProductMarketing_.productMarketingType.getName()));
        fetchplans.add(new SpecificFetchMode(ProductMarketing_.attributes.getName()));
        fetchplans.add(new SpecificFetchMode(ProductMarketing_.productSkus.getName()));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan productSkuDefaultFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(ProductSku_.attributes.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSku_.productMarketing.getName()));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultProductBrandFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        return new FetchPlan(fetchplans);
    }

}