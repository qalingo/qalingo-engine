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

import org.hoteia.qalingo.core.domain.ProductBrand_;
import org.hoteia.qalingo.core.domain.ProductMarketing_;
import org.hoteia.qalingo.core.domain.ProductSku_;
import org.hoteia.qalingo.core.domain.ProductSkuOptionDefinition_;
import org.hoteia.qalingo.core.domain.ProductSkuOptionDefinitionType_;
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
    
    public static FetchPlan fullProductMarketingFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(ProductMarketing_.productMarketingType.getName()));
        fetchplans.add(new SpecificFetchMode(ProductMarketing_.attributes.getName()));
        fetchplans.add(new SpecificFetchMode(ProductMarketing_.productSkus.getName()));
        fetchplans.add(new SpecificFetchMode(ProductMarketing_.assets.getName()));
        fetchplans.add(new SpecificFetchMode(ProductMarketing_.productBrand.getName()));
        fetchplans.add(new SpecificFetchMode(ProductMarketing_.productMarketingType.getName()));
        fetchplans.add(new SpecificFetchMode(ProductMarketing_.productAssociationLinks.getName()));
        fetchplans.add(new SpecificFetchMode(ProductMarketing_.customerRates.getName()));
        fetchplans.add(new SpecificFetchMode(ProductMarketing_.customerComments.getName()));
        fetchplans.add(new SpecificFetchMode(ProductMarketing_.tags.getName()));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan productSkuDefaultFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(ProductSku_.attributes.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSku_.productMarketing.getName()));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan productSkuDisplayFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(ProductSku_.attributes.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSku_.assets.getName()));
        
        fetchplans.add(new SpecificFetchMode(ProductSku_.productMarketing.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSku_.productMarketing.getName() + "." + ProductMarketing_.productBrand.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSku_.productMarketing.getName() + "." + ProductMarketing_.attributes.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSku_.productMarketing.getName() + "." + ProductMarketing_.assets.getName()));
        
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan productBrandDefaultFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(ProductBrand_.attributes.getName()));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan productSkuOptionDefinitionDefaultFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(ProductSkuOptionDefinition_.attributes.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSkuOptionDefinition_.productSkuOptionDefinitionType.getName()));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan productSkuOptionDefinitionTypeDefaultFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(ProductSkuOptionDefinitionType_.attributes.getName()));
        return new FetchPlan(fetchplans);
    }
    
}