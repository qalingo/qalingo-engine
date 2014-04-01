package org.hoteia.qalingo.core.fetchplan.common;

import java.util.ArrayList;
import java.util.List;

import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificAlias;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;

public class FetchPlanGraphCommon {

    public static FetchPlan defaultCartFetchPlan(){
        
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
        fetchplans.add(new SpecificFetchMode("session"));
        fetchplans.add(new SpecificFetchMode("cartItems"));

        fetchplans.add(new SpecificFetchMode("productSkuAttributes", new SpecificAlias("cartItems.productSku.productSkuAttributes")));

        fetchplans.add(new SpecificFetchMode("productSkuAssets", new SpecificAlias("cartItems.productSku.assets")));

        fetchplans.add(new SpecificFetchMode("productSkuPrices", new SpecificAlias("cartItems.productSku.prices")));

        fetchplans.add(new SpecificFetchMode("productSkuStocks", new SpecificAlias("cartItems.productSku.stocks")));

        fetchplans.add(new SpecificFetchMode("productMarketing", new SpecificAlias("cartItems.productMarketing")));

        fetchplans.add(new SpecificFetchMode("productMarketingAttributes", new SpecificAlias("cartItems.productMarketing.productMarketingAttributes")));

        fetchplans.add(new SpecificFetchMode("productMarketingAssets", new SpecificAlias("cartItems.productMarketing.assets")));

        fetchplans.add(new SpecificFetchMode("catalogCategoryAttributes", new SpecificAlias("cartItems.catalogCategory.catalogCategoryAttributes")));

        fetchplans.add(new SpecificFetchMode("catalogCategoryAssets", new SpecificAlias("cartItems.catalogCategory.assets")));

        fetchplans.add(new SpecificFetchMode("shippings"));

        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultCatalogFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();

        fetchplans.add(new SpecificFetchMode("catalogCategories"));
        fetchplans.add(new SpecificFetchMode("catalogCategoryAttributes", new SpecificAlias("catalogCategories.catalogCategoryAttributes")));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultEngineSettingFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode("engineSettingValues"));
        return new FetchPlan(fetchplans);
    }

    public static FetchPlan defaultOrderCustomerFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode("billingAddress"));
        fetchplans.add(new SpecificFetchMode("shippingAddress"));
        fetchplans.add(new SpecificFetchMode("orderPayments"));
        fetchplans.add(new SpecificFetchMode("orderShipments"));
        fetchplans.add(new SpecificFetchMode("orderItems", new SpecificAlias("orderShipments.orderItems")));
        
        fetchplans.add(new SpecificFetchMode("productSku", new SpecificAlias("orderShipments.orderItems.productSku")));
        fetchplans.add(new SpecificFetchMode("productSkuAttributes", new SpecificAlias("orderShipments.orderItems.productSku.productSkuAttributes")));

        fetchplans.add(new SpecificFetchMode("assets", new SpecificAlias("orderShipments.orderItems.productSku.assets")));
        fetchplans.add(new SpecificFetchMode("orderTaxes", new SpecificAlias("orderShipments.orderItems.orderTaxes")));
        fetchplans.add(new SpecificFetchMode("currency", new SpecificAlias("orderShipments.orderItems.currency")));
        
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultPaymentGatewayFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode("attributes"));
        fetchplans.add(new SpecificFetchMode("options"));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan fullPaymentGatewayFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode("attributes"));
        fetchplans.add(new SpecificFetchMode("options"));
        fetchplans.add(new SpecificFetchMode("marketAreas"));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultTaxFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode("taxCountries"));
        fetchplans.add(new SpecificFetchMode("attributes"));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultCompanyFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode("localizations"));
        return new FetchPlan(fetchplans);
    }
    
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
    
    public static FetchPlan defaultStoreFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode("storeAttributes"));
        fetchplans.add(new SpecificFetchMode("assets"));
        fetchplans.add(new SpecificFetchMode("businessHours"));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultWarehouseFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultUserFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode("defaultLocalization"));
        fetchplans.add(new SpecificFetchMode("company"));
        fetchplans.add(new SpecificFetchMode("userGroups"));
        fetchplans.add(new SpecificFetchMode("roles", new SpecificAlias("userGroups.groupRoles")));
        fetchplans.add(new SpecificFetchMode("rolePermissions", new SpecificAlias("userGroups.groupRoles.rolePermissions")));
        fetchplans.add(new SpecificFetchMode("connectionLogs"));
        return new FetchPlan(fetchplans);
    }
    
}