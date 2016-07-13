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
import org.hoteia.qalingo.core.domain.ProductSkuStorePrice_;
import org.hoteia.qalingo.core.domain.ProductSkuOptionRel_;
import org.hoteia.qalingo.core.domain.ProductSkuOptionPk_;
import org.hoteia.qalingo.core.domain.ProductSkuTagRel_;
import org.hoteia.qalingo.core.domain.ProductSkuTagPk_;
import org.hoteia.qalingo.core.domain.Tag_;
import org.hoteia.qalingo.core.domain.ProductSkuStoreRel_;
import org.hoteia.qalingo.core.domain.ProductSkuStorePk_;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtualProductSkuRel_;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtualProductSkuPk_;
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
    
    public static FetchPlan productMarketingDisplayFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(ProductMarketing_.attributes.getName()));
        fetchplans.add(new SpecificFetchMode(ProductMarketing_.assets.getName()));
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
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan fullIndexedProductMarketingFetchPlan(){
        return fullProductMarketingFetchPlan();
    }
    
    public static FetchPlan productSkuDefaultFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(ProductSku_.attributes.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSku_.productMarketing.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSku_.productMarketing.getName() + "." + ProductMarketing_.productBrand.getName()));
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
    
    public static FetchPlan fullProductSkuFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(ProductSku_.attributes.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSku_.assets.getName()));

        fetchplans.add(new SpecificFetchMode(ProductSku_.productMarketing.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSku_.productMarketing.getName() + "." + ProductMarketing_.productBrand.getName()));

        fetchplans.add(new SpecificFetchMode(ProductSku_.prices.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSku_.prices.getName() + "." + ProductSkuStorePrice_.currency.getName()));
        
        fetchplans.add(new SpecificFetchMode(ProductSku_.catalogCategoryVirtualProductSkuRels.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSku_.catalogCategoryVirtualProductSkuRels.getName() + "." + CatalogCategoryVirtualProductSkuRel_.pk.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSku_.catalogCategoryVirtualProductSkuRels.getName() + "." + CatalogCategoryVirtualProductSkuRel_.pk.getName() + "." + CatalogCategoryVirtualProductSkuPk_.catalogCategoryVirtual.getName()));
        
        fetchplans.add(new SpecificFetchMode(ProductSku_.optionRels.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSku_.optionRels.getName() + "." + ProductSkuOptionRel_.pk.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSku_.optionRels.getName() + "." + ProductSkuOptionRel_.pk.getName() + "." + ProductSkuOptionPk_.productSkuOptionDefinition.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSku_.optionRels.getName() + "." + ProductSkuOptionRel_.pk.getName() + "." + ProductSkuOptionPk_.productSkuOptionDefinition.getName()+ "." + ProductSkuOptionDefinition_.attributes.getName()));

        fetchplans.add(new SpecificFetchMode(ProductSku_.tagRels.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSku_.tagRels.getName() + "." + ProductSkuTagRel_.pk.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSku_.tagRels.getName() + "." + ProductSkuTagRel_.pk.getName() + "." + ProductSkuTagPk_.tag.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSku_.tagRels.getName() + "." + ProductSkuTagRel_.pk.getName() + "." + ProductSkuTagPk_.tag.getName() + "." + Tag_.attributes.getName()));

        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan fullIndexedProductSkuFetchPlan(){
        return fullProductSkuFetchPlan();
    }
    
    public static FetchPlan productBrandDefaultFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(ProductBrand_.attributes.getName()));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan productBrandDisplayFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(ProductBrand_.attributes.getName()));
        fetchplans.add(new SpecificFetchMode(ProductBrand_.assets.getName()));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan fullProductBrandFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(ProductBrand_.attributes.getName()));
        fetchplans.add(new SpecificFetchMode(ProductBrand_.assets.getName()));
        fetchplans.add(new SpecificFetchMode(ProductBrand_.tags.getName()));
        fetchplans.add(new SpecificFetchMode(ProductBrand_.company.getName()));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan productSkuOptionDefinitionDefaultFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(ProductSkuOptionDefinition_.attributes.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSkuOptionDefinition_.optionDefinitionType.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSkuOptionDefinition_.optionDefinitionType.getName() + "." + ProductSkuOptionDefinitionType_.attributes.getName()));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan productSkuOptionDefinitionTypeDefaultFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(ProductSkuOptionDefinitionType_.attributes.getName()));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan productSkuStoreRelDefaultFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(ProductSkuStoreRel_.pk.getName() + "." + ProductSkuStorePk_.store.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSkuStoreRel_.pk.getName() + "." + ProductSkuStorePk_.productSku.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSkuStoreRel_.attributes.getName()));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan fullProductSkuStoreRelDefaultFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(ProductSkuStoreRel_.pk.getName() + "." + ProductSkuStorePk_.store.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSkuStoreRel_.pk.getName() + "." + ProductSkuStorePk_.productSku.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSkuStoreRel_.attributes.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSkuStoreRel_.configurations.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSkuStoreRel_.prices.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSkuStoreRel_.prices.getName() + "." + ProductSkuStorePrice_.currency.getName()));
        fetchplans.add(new SpecificFetchMode(ProductSkuStoreRel_.stocks.getName()));
        return new FetchPlan(fetchplans);
    }
    
}