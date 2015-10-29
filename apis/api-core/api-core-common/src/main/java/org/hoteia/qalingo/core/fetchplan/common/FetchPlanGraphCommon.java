/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.fetchplan.common;

import java.util.ArrayList;
import java.util.List;

import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificAlias;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;

import org.hoteia.qalingo.core.domain.Cart_;
import org.hoteia.qalingo.core.domain.Tag_;
import org.hoteia.qalingo.core.domain.Tax_;
import org.hoteia.qalingo.core.domain.AbstractPaymentGateway_;
import org.hoteia.qalingo.core.domain.CartItem_;
import org.hoteia.qalingo.core.domain.ProductMarketing_;
import org.hoteia.qalingo.core.domain.ProductSku_;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual_;

public class FetchPlanGraphCommon {

    public static FetchPlan defaultCartFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
        fetchplans.add(new SpecificFetchMode(Cart_.currency.getName()));
        fetchplans.add(new SpecificFetchMode(Cart_.cartItems.getName()));

        fetchplans.add(new SpecificFetchMode(Cart_.cartItems.getName() + "." + CartItem_.catalogCategory.getName()));
        fetchplans.add(new SpecificFetchMode(Cart_.cartItems.getName() + "." + CartItem_.catalogCategory.getName() + "." + CatalogCategoryVirtual_.attributes.getName()));

        fetchplans.add(new SpecificFetchMode(Cart_.cartItems.getName() + "." + CartItem_.productMarketing.getName()));
        fetchplans.add(new SpecificFetchMode(Cart_.cartItems.getName() + "." + CartItem_.productMarketing.getName() + "." + ProductMarketing_.attributes.getName()));
        fetchplans.add(new SpecificFetchMode(Cart_.cartItems.getName() + "." + CartItem_.productMarketing.getName() + "." + ProductMarketing_.assets.getName()));

        fetchplans.add(new SpecificFetchMode(Cart_.cartItems.getName() + "." + CartItem_.productSku.getName()));
        fetchplans.add(new SpecificFetchMode(Cart_.cartItems.getName() + "." + CartItem_.productSku.getName() + "." + ProductSku_.attributes.getName()));
        fetchplans.add(new SpecificFetchMode(Cart_.cartItems.getName() + "." + CartItem_.productSku.getName() + "." + ProductSku_.assets.getName()));
        fetchplans.add(new SpecificFetchMode(Cart_.cartItems.getName() + "." + CartItem_.productSku.getName() + "." + ProductSku_.prices.getName()));

        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultCatalogFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode("catalogCategories"));
        fetchplans.add(new SpecificFetchMode("catalogCategories.attributes"));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultEngineSettingFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode("engineSettingValues"));
        return new FetchPlan(fetchplans);
    }

    public static FetchPlan defaultPaymentGatewayFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(AbstractPaymentGateway_.attributes.getName()));
        fetchplans.add(new SpecificFetchMode(AbstractPaymentGateway_.options.getName()));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan fullPaymentGatewayFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(AbstractPaymentGateway_.attributes.getName()));
        fetchplans.add(new SpecificFetchMode(AbstractPaymentGateway_.options.getName()));
        fetchplans.add(new SpecificFetchMode(AbstractPaymentGateway_.marketAreas.getName()));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultTaxFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(Tax_.taxCountries.getName()));
        fetchplans.add(new SpecificFetchMode(Tax_.attributes.getName()));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultTagFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(Tag_.attributes.getName()));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultWarehouseFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        return new FetchPlan(fetchplans);
    }
    
}