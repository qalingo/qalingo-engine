/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.rest.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.hoteia.qalingo.core.pojo.LocalizationPojo;
import org.hoteia.qalingo.core.pojo.catalog.CatalogCategoryPojo;
import org.hoteia.qalingo.core.pojo.catalog.CatalogPojo;
import org.hoteia.qalingo.core.pojo.cms.CmsCategoriesPojo;
import org.hoteia.qalingo.core.pojo.cms.CmsProductsPojo;
import org.hoteia.qalingo.core.pojo.context.ContextPojo;
import org.hoteia.qalingo.core.pojo.geoloc.GeolocContextResponse;
import org.hoteia.qalingo.core.pojo.geoloc.GeolocDataPojo;
import org.hoteia.qalingo.core.pojo.market.MarketAreaPojo;
import org.hoteia.qalingo.core.pojo.market.MarketPlacePojo;
import org.hoteia.qalingo.core.pojo.market.MarketPojo;
import org.hoteia.qalingo.core.pojo.product.ProductMarketingPojo;
import org.hoteia.qalingo.core.pojo.retailer.RetailerPojo;
import org.hoteia.qalingo.core.service.pojo.LocalizationPojoService;
import org.hoteia.qalingo.core.service.pojo.MarketPojoService;
import org.hoteia.qalingo.core.service.pojo.RetailerPojoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Path("/context/")
@Component("contextRestService")
public class ContextRestService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private MarketPojoService marketPojoService;

    @Autowired
    private RetailerPojoService retailerPojoService;

    @Autowired
    private LocalizationPojoService localizationPojoService;
    
    @GET
    @Path("geoloc")
    @Produces(MediaType.APPLICATION_JSON)
    public GeolocContextResponse getGeolocMarketArea(@Context HttpServletRequest request) {
        GeolocContextResponse geolocContextResponse = new GeolocContextResponse();
        
        try {
            GeolocDataPojo geolocDataPojo = marketPojoService.getGeolocDataByRemoteAddress(request.getRemoteAddr());
            MarketAreaPojo marketAreaPojo = marketPojoService.getMarketAreaByGeolocData(geolocDataPojo);
            geolocContextResponse.setMarketArea(marketAreaPojo);
            
        } catch (Exception e) {
            logger.error("Some error during the Geoloc Context.", e);
            
            // TODO SET ERROR IN THE RESPONSE
        }
        
        return geolocContextResponse;
    }
    
    @GET
    @Path("marketplaces")
    @Produces(MediaType.APPLICATION_JSON)
    public ContextPojo getMarketPlaces() {
        // DEFAULT CALLBACK WITH ALL THE MARKET PLACES AND DEFAULT MASTER VALUE FOR MARKET / MARKET AREA / RETAILER / LOCALIZATION
        ContextPojo context = new ContextPojo();

        // MARKET PLACE LIST
        buildMarketPlace(context, null);

        // MARKET LIST
        buildMarket(context, null, null);

        // MARKET AREA LIST
        buildMarketArea(context, null, null);

        // RETAILER LIST
        buildRetailer(context, null, null);

        // LOCALIZATION LIST
        buildLocalization(context, null, null);

        return context;
    }

    @GET
    @Path("marketplace/set/{marketPlaceCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public ContextPojo selectMarketPlace(@PathParam("marketPlaceCode") final String marketPlaceCode) {
        ContextPojo context = new ContextPojo();
        
        MarketPlacePojo selectedMarketPlace = marketPojoService.getMarketPlaceByCode(marketPlaceCode);
        
        // MARKET PLACE LIST
        buildMarketPlace(context, selectedMarketPlace);
        
        // MARKET LIST
        buildMarket(context, selectedMarketPlace, null);

        // MARKET AREA LIST
        buildMarketArea(context, null, null);

        // RETAILER LIST
        buildRetailer(context, null, null);

        // LOCALIZATION LIST
        buildLocalization(context, null, null);

        return context;
    }
    
    @GET
    @Path("market/set/{marketCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public ContextPojo selectMarket(@PathParam("marketCode") final String marketCode) {
        ContextPojo context = new ContextPojo();

        MarketPojo selectedMarket = marketPojoService.getMarketByCode(marketCode);

        MarketPlacePojo selectedMarketPlace = marketPojoService.getMarketPlaceByCode(selectedMarket.getMarketPlace().getCode());

        // MARKET PLACE LIST
        buildMarketPlace(context, selectedMarketPlace);
        
        // MARKET LIST
        buildMarket(context, selectedMarketPlace, selectedMarket);
        
        // MARKET AREA LIST
        buildMarketArea(context, selectedMarket, null);

        // RETAILER LIST
        buildRetailer(context, null, null);

        // LOCALIZATION LIST
        buildLocalization(context, null, null);

        return context;
    }
    
    @GET
    @Path("marketarea/set/{marketAreaCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public ContextPojo selectMarketArea(@PathParam("marketAreaCode") final String marketAreaCode) {
        ContextPojo context = new ContextPojo();

        MarketAreaPojo selectedMarketArea = marketPojoService.getMarketAreaByCode(marketAreaCode);

        MarketPojo selectedMarket = marketPojoService.getMarketByCode(selectedMarketArea.getMarket().getCode());

        MarketPlacePojo selectedMarketPlace = marketPojoService.getMarketPlaceByCode(selectedMarket.getMarketPlace().getCode());

        // MARKET PLACE LIST
        buildMarketPlace(context, selectedMarketPlace);
        
        // MARKET LIST
        buildMarket(context, selectedMarketPlace, selectedMarket);
        
        // MARKET AREA LIST
        buildMarketArea(context, selectedMarket, selectedMarketArea);

        // RETAILER LIST
        buildRetailer(context, selectedMarketArea, null);

        // LOCALIZATION LIST
        buildLocalization(context, selectedMarketArea, null);
        
        return context;
    }
    
    @GET
    @Path("retailer/set/{marketAreaCode}/{retailerCode}/{localizationCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public ContextPojo selectRetailer(@PathParam("marketAreaCode") final String marketAreaCode, @PathParam("retailerCode") final String retailerCode,
                                     @PathParam("localizationCode") final String localizationCode) {
        ContextPojo context = new ContextPojo();

        LocalizationPojo selectedLocalization = localizationPojoService.getLocalizationByCode(localizationCode);

        RetailerPojo selectedRetailer = retailerPojoService.getRetailerByCode(retailerCode);
        
        MarketAreaPojo selectedMarketArea = marketPojoService.getMarketAreaByCode(marketAreaCode);

        MarketPojo selectedMarket = marketPojoService.getMarketByCode(selectedMarketArea.getMarket().getCode());

        MarketPlacePojo selectedMarketPlace = marketPojoService.getMarketPlaceByCode(selectedMarket.getMarketPlace().getCode());

        // MARKET PLACE LIST
        buildMarketPlace(context, selectedMarketPlace);
        
        // MARKET LIST
        buildMarket(context, selectedMarketPlace, selectedMarket);
        
        // MARKET AREA LIST
        buildMarketArea(context, selectedMarket, selectedMarketArea);

        // RETAILER LIST
        buildRetailer(context, selectedMarketArea, selectedRetailer);

        // LOCALIZATION LIST
        buildLocalization(context, selectedMarketArea, selectedLocalization);
        
        return context;
    }
    
    @GET
    @Path("localization/set/{marketAreaCode}/{retailerCode}/{localizationCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public ContextPojo selectLocalization(@PathParam("marketAreaCode") final String marketAreaCode, @PathParam("retailerCode") final String retailerCode, 
                                         @PathParam("localizationCode") final String localizationCode) {
        ContextPojo context = new ContextPojo();

        LocalizationPojo selectedLocalization = localizationPojoService.getLocalizationByCode(localizationCode);

        RetailerPojo selectedRetailer = retailerPojoService.getRetailerByCode(retailerCode);

        MarketAreaPojo selectedMarketArea = marketPojoService.getMarketAreaByCode(marketAreaCode);

        MarketPojo selectedMarket = marketPojoService.getMarketByCode(selectedMarketArea.getMarket().getCode());

        MarketPlacePojo selectedMarketPlace = marketPojoService.getMarketPlaceByCode(selectedMarket.getMarketPlace().getCode());

        // MARKET PLACE LIST
        buildMarketPlace(context, selectedMarketPlace);
        
        // MARKET LIST
        buildMarket(context, selectedMarketPlace, selectedMarket);
        
        // MARKET AREA LIST
        buildMarketArea(context, selectedMarket, selectedMarketArea);

        // RETAILER LIST
        buildRetailer(context, selectedMarketArea, selectedRetailer);
        
        // LOCALIZATION LIST
        buildLocalization(context, selectedMarketArea, selectedLocalization);
        
        return context;
    }
    
    @GET
    @Path("catalog/categories/{marketAreaCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public CmsCategoriesPojo categoriesByMarketArea(@PathParam("marketAreaCode") final String marketAreaCode) {
        CmsCategoriesPojo cmsCategories = new CmsCategoriesPojo();

        MarketAreaPojo selectedMarketArea = marketPojoService.getMarketAreaByCode(marketAreaCode);
        if(selectedMarketArea != null){
            selectedMarketArea.setMarket(null);
            selectedMarketArea.setRetailers(null);
            selectedMarketArea.setLocalizations(null);
            
            cmsCategories.setMarketArea(selectedMarketArea);
            List<CatalogCategoryPojo> categories = selectedMarketArea.getCatalog().getSortedRootCatalogCategories();
            for (Iterator<CatalogCategoryPojo> iterator = categories.iterator(); iterator.hasNext();) {
                CatalogCategoryPojo catalogCategoryPojo = (CatalogCategoryPojo) iterator.next();
                catalogCategoryPojo.setCatalogCategoryGlobalAttributes(null);
                catalogCategoryPojo.setCatalogCategoryMarketAreaAttributes(null);
                catalogCategoryPojo.setProductMarketings(null);
            }
            cmsCategories.setCatalogCategories(categories);
            
            CatalogPojo catalog = selectedMarketArea.getCatalog();
            catalog.setSortedRootCatalogCategories(null);
            catalog.setSortedAllCatalogCategories(null);
            cmsCategories.setCatalog(catalog);
        }

        return cmsCategories;
    }
    
    @GET
    @Path("catalog/products/{marketAreaCode}/{categoryCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public CmsProductsPojo productsByMarketArea(@PathParam("marketAreaCode") final String marketAreaCode, @PathParam("categoryCode") final String categoryCode) {
        CmsProductsPojo cmsProducts = new CmsProductsPojo();

        MarketAreaPojo selectedMarketArea = marketPojoService.getMarketAreaByCode(marketAreaCode);
        if(selectedMarketArea != null){
            selectedMarketArea.setMarket(null);
            selectedMarketArea.setRetailers(null);
            selectedMarketArea.setLocalizations(null);
            
            cmsProducts.setMarketArea(selectedMarketArea);
            
            List<CatalogCategoryPojo> categories = selectedMarketArea.getCatalog().getSortedRootCatalogCategories();
            for (Iterator<CatalogCategoryPojo> iterator = categories.iterator(); iterator.hasNext();) {
                CatalogCategoryPojo catalogCategoryPojo = (CatalogCategoryPojo) iterator.next();
                if(catalogCategoryPojo.getCode().equals(categoryCode)){
                    List<ProductMarketingPojo> products = catalogCategoryPojo.getProductMarketings();
                    for (Iterator<ProductMarketingPojo> iteratorProductMarketingPojo = products.iterator(); iteratorProductMarketingPojo.hasNext();) {
                        ProductMarketingPojo productMarketingPojo = (ProductMarketingPojo) iteratorProductMarketingPojo.next();
                        productMarketingPojo.setProductBrand(null);
                        productMarketingPojo.setProductMarketingMarketAreaAttributes(null);
                        productMarketingPojo.setProductMarketingGlobalAttributes(null);
                        productMarketingPojo.setProductSkus(null);
                        productMarketingPojo.setProductAssociationLinks(null);
                    }
                    cmsProducts.setProductMarketings(products);
                }
            }

            CatalogPojo catalog = selectedMarketArea.getCatalog();
            catalog.setSortedRootCatalogCategories(null);
            cmsProducts.setCatalog(catalog);

        }

        return cmsProducts;
    }
    
    private void buildMarketPlace(ContextPojo context, MarketPlacePojo selectedMarketPlace){
        List<MarketPlacePojo> marketPlaces = new ArrayList<MarketPlacePojo>();
        
        // HACK TEMPORARY BEFORE GOOD FETCH STATEGY
        List<MarketPlacePojo> allMarketPlaces = marketPojoService.getMarketPlaces();
        for (Iterator<MarketPlacePojo> iterator = allMarketPlaces.iterator(); iterator.hasNext();) {
            MarketPlacePojo marketPlacePojo = (MarketPlacePojo) iterator.next();
            if(selectedMarketPlace != null
                    && marketPlacePojo.getCode().equals(selectedMarketPlace.getCode())){
                marketPlacePojo.setSelected(true);
                selectedMarketPlace = marketPlacePojo;
            }
            marketPlacePojo.setMarkets(null);
        }
        marketPlaces.addAll(allMarketPlaces);
        
        context.setMarketPlaces(marketPlaces);
    }

    private void buildMarket(ContextPojo context, MarketPlacePojo selectedMarketPlace, MarketPojo selectedMarket){
        List<MarketPojo> markets = new ArrayList<MarketPojo>();
        
        if(selectedMarketPlace != null){
            for (Iterator<MarketPojo> iterator = selectedMarketPlace.getMarkets().iterator(); iterator.hasNext();) {
                MarketPojo marketPojo = (MarketPojo) iterator.next();
                MarketPojo market = marketPojoService.getMarketByCode(marketPojo.getCode());
                if(selectedMarket != null
                        && market.getCode().equals(selectedMarket.getCode())){
                    market.setSelected(true);
                }
                market.setMarketPlace(null);
                market.setMarketAreas(null);
                markets.add(market);
            }
        }
        
        context.setMarkets(markets);
    }
    
    private void buildMarketArea(ContextPojo context, MarketPojo selectedMarket, MarketAreaPojo selectedMarketArea){
        List<MarketAreaPojo> marketAreas = new ArrayList<MarketAreaPojo>();
        
        if(selectedMarket != null){
            for (Iterator<MarketAreaPojo> iterator = selectedMarket.getMarketAreas().iterator(); iterator.hasNext();) {
                MarketAreaPojo marketAreaPojo = (MarketAreaPojo) iterator.next();
                MarketAreaPojo marketArea = marketPojoService.getMarketAreaByCode(marketAreaPojo.getCode());
                if(selectedMarketArea != null
                        && marketArea.getCode().equals(selectedMarketArea.getCode())){
                    marketArea.setSelected(true);
                }
                marketArea.setMarket(null);
                marketArea.setRetailers(null);
                marketArea.setLocalizations(null);
                marketAreas.add(marketArea);
            }
        }
        
        context.setMarketAreas(marketAreas);
    }
    
    private void buildRetailer(ContextPojo context, MarketAreaPojo selectedMarketArea, RetailerPojo selectedRetailer){
        List<RetailerPojo> retailers = new ArrayList<RetailerPojo>();
        if(selectedMarketArea != null){
            List<RetailerPojo> retailersByMarketAreaCode = retailerPojoService.findRetailersByMarketAreaCode(selectedMarketArea.getCode());
            for (Iterator<RetailerPojo> iterator = retailersByMarketAreaCode.iterator(); iterator.hasNext();) {
                RetailerPojo retailerPojo = (RetailerPojo) iterator.next();
                if(selectedRetailer != null
                        && retailerPojo.getCode().equals(selectedRetailer.getCode())){
                    retailerPojo.setSelected(true);
                }
                retailerPojo.setCustomerComments(null);
                retailerPojo.setCustomerRates(null);
                retailerPojo.setStores(null);
                retailerPojo.setAddresses(null);
                retailers.add(retailerPojo);
            }
        }
        
        context.setRetailers(retailers);
    }
    
    private void buildLocalization(ContextPojo context, MarketAreaPojo selectedMarketArea, LocalizationPojo selectedLocalization){
        List<LocalizationPojo> localizations = new ArrayList<LocalizationPojo>();
        
        if(selectedMarketArea != null){
            List<LocalizationPojo> localizationsByMarketAreaCode = localizationPojoService.findLocalizationsByMarketAreaCode(selectedMarketArea.getCode());
            for (Iterator<LocalizationPojo> iterator = localizationsByMarketAreaCode.iterator(); iterator.hasNext();) {
                LocalizationPojo localizationPojo = (LocalizationPojo) iterator.next();
                if(selectedLocalization != null
                        && localizationPojo.getCode().equals(selectedLocalization.getCode())){
                    localizationPojo.setSelected(true);
                }
                localizations.add(localizationPojo);
            }
        }
        
        context.setLocalizations(localizations);
    }

}