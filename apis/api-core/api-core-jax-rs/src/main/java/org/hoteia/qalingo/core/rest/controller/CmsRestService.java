package org.hoteia.qalingo.core.rest.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.hoteia.qalingo.core.pojo.LocalizationPojo;
import org.hoteia.qalingo.core.pojo.catalog.CatalogCategoryPojo;
import org.hoteia.qalingo.core.pojo.catalog.CatalogPojo;
import org.hoteia.qalingo.core.pojo.cms.CmsCategories;
import org.hoteia.qalingo.core.pojo.cms.CmsContext;
import org.hoteia.qalingo.core.pojo.cms.CmsProducts;
import org.hoteia.qalingo.core.pojo.market.MarketAreaPojo;
import org.hoteia.qalingo.core.pojo.market.MarketPlacePojo;
import org.hoteia.qalingo.core.pojo.market.MarketPojo;
import org.hoteia.qalingo.core.pojo.product.ProductMarketingPojo;
import org.hoteia.qalingo.core.pojo.retailer.RetailerPojo;
import org.hoteia.qalingo.core.service.pojo.LocalizationPojoService;
import org.hoteia.qalingo.core.service.pojo.MarketPojoService;
import org.hoteia.qalingo.core.service.pojo.RetailerPojoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Path("/cms/")
@CrossOriginResourceSharing(allowAllOrigins = true)
@Component("cmsRestService")
public class CmsRestService {

    @Autowired
    private MarketPojoService marketPojoService;

    @Autowired
    private RetailerPojoService retailerPojoService;

    @Autowired
    private LocalizationPojoService localizationPojoService;
    
    @GET
    @Path("marketplaces")
    @Produces(MediaType.APPLICATION_JSON)
    public CmsContext getMarketPlaces() {
        // DEFAULT CALLBACK WITH ALL THE MARKET PLACES AND DEFAULT MASTER VALUE FOR MARKET / MARKET AREA / RETAILER / LOCALIZATION
        CmsContext cmsContext = new CmsContext();

        // MARKET PLACE LIST
        buildMarketPlace(cmsContext, null);

        // MARKET LIST
        buildMarket(cmsContext, null, null);

        // MARKET AREA LIST
        buildMarketArea(cmsContext, null, null);

        // RETAILER LIST
        buildRetailer(cmsContext, null, null);

        // LOCALIZATION LIST
        buildLocalization(cmsContext, null, null);

        return cmsContext;
    }

    @GET
    @Path("marketplace/set/{marketPlaceCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public CmsContext selectMarketPlace(@PathParam("marketPlaceCode") final String marketPlaceCode) {
        CmsContext cmsContext = new CmsContext();
        
        MarketPlacePojo selectedMarketPlace = marketPojoService.getMarketPlaceByCode(marketPlaceCode);
        
        // MARKET PLACE LIST
        buildMarketPlace(cmsContext, selectedMarketPlace);
        
        // MARKET LIST
        buildMarket(cmsContext, selectedMarketPlace, null);

        // MARKET AREA LIST
        buildMarketArea(cmsContext, null, null);

        // RETAILER LIST
        buildRetailer(cmsContext, null, null);

        // LOCALIZATION LIST
        buildLocalization(cmsContext, null, null);

        return cmsContext;
    }
    
    @GET
    @Path("market/set/{marketCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public CmsContext selectMarket(@PathParam("marketCode") final String marketCode) {
        CmsContext cmsContext = new CmsContext();

        MarketPojo selectedMarket = marketPojoService.getMarketByCode(marketCode);

        MarketPlacePojo selectedMarketPlace = marketPojoService.getMarketPlaceByCode(selectedMarket.getMarketPlace().getCode());

        // MARKET PLACE LIST
        buildMarketPlace(cmsContext, selectedMarketPlace);
        
        // MARKET LIST
        buildMarket(cmsContext, selectedMarketPlace, selectedMarket);
        
        // MARKET AREA LIST
        buildMarketArea(cmsContext, selectedMarket, null);

        // RETAILER LIST
        buildRetailer(cmsContext, null, null);

        // LOCALIZATION LIST
        buildLocalization(cmsContext, null, null);

        return cmsContext;
    }
    
    @GET
    @Path("marketarea/set/{marketAreaCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public CmsContext selectMarketArea(@PathParam("marketAreaCode") final String marketAreaCode) {
        CmsContext cmsContext = new CmsContext();

        MarketAreaPojo selectedMarketArea = marketPojoService.getMarketAreaByCode(marketAreaCode);

        MarketPojo selectedMarket = marketPojoService.getMarketByCode(selectedMarketArea.getMarket().getCode());

        MarketPlacePojo selectedMarketPlace = marketPojoService.getMarketPlaceByCode(selectedMarket.getMarketPlace().getCode());

        // MARKET PLACE LIST
        buildMarketPlace(cmsContext, selectedMarketPlace);
        
        // MARKET LIST
        buildMarket(cmsContext, selectedMarketPlace, selectedMarket);
        
        // MARKET AREA LIST
        buildMarketArea(cmsContext, selectedMarket, selectedMarketArea);

        // RETAILER LIST
        buildRetailer(cmsContext, selectedMarketArea, null);

        // LOCALIZATION LIST
        buildLocalization(cmsContext, selectedMarketArea, null);
        
        return cmsContext;
    }
    
    @GET
    @Path("retailer/set/{marketAreaCode}/{retailerCode}/{localizationCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public CmsContext selectRetailer(@PathParam("marketAreaCode") final String marketAreaCode, @PathParam("retailerCode") final String retailerCode,
                                     @PathParam("localizationCode") final String localizationCode) {
        CmsContext cmsContext = new CmsContext();

        LocalizationPojo selectedLocalization = localizationPojoService.getLocalizationByCode(localizationCode);

        RetailerPojo selectedRetailer = retailerPojoService.getRetailerByCode(retailerCode);
        
        MarketAreaPojo selectedMarketArea = marketPojoService.getMarketAreaByCode(marketAreaCode);

        MarketPojo selectedMarket = marketPojoService.getMarketByCode(selectedMarketArea.getMarket().getCode());

        MarketPlacePojo selectedMarketPlace = marketPojoService.getMarketPlaceByCode(selectedMarket.getMarketPlace().getCode());

        // MARKET PLACE LIST
        buildMarketPlace(cmsContext, selectedMarketPlace);
        
        // MARKET LIST
        buildMarket(cmsContext, selectedMarketPlace, selectedMarket);
        
        // MARKET AREA LIST
        buildMarketArea(cmsContext, selectedMarket, selectedMarketArea);

        // RETAILER LIST
        buildRetailer(cmsContext, selectedMarketArea, selectedRetailer);

        // LOCALIZATION LIST
        buildLocalization(cmsContext, selectedMarketArea, selectedLocalization);
        
        return cmsContext;
    }
    
    @GET
    @Path("localization/set/{marketAreaCode}/{retailerCode}/{localizationCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public CmsContext selectLocalization(@PathParam("marketAreaCode") final String marketAreaCode, @PathParam("retailerCode") final String retailerCode, 
                                         @PathParam("localizationCode") final String localizationCode) {
        CmsContext cmsContext = new CmsContext();

        LocalizationPojo selectedLocalization = localizationPojoService.getLocalizationByCode(localizationCode);

        RetailerPojo selectedRetailer = retailerPojoService.getRetailerByCode(retailerCode);

        MarketAreaPojo selectedMarketArea = marketPojoService.getMarketAreaByCode(marketAreaCode);

        MarketPojo selectedMarket = marketPojoService.getMarketByCode(selectedMarketArea.getMarket().getCode());

        MarketPlacePojo selectedMarketPlace = marketPojoService.getMarketPlaceByCode(selectedMarket.getMarketPlace().getCode());

        // MARKET PLACE LIST
        buildMarketPlace(cmsContext, selectedMarketPlace);
        
        // MARKET LIST
        buildMarket(cmsContext, selectedMarketPlace, selectedMarket);
        
        // MARKET AREA LIST
        buildMarketArea(cmsContext, selectedMarket, selectedMarketArea);

        // RETAILER LIST
        buildRetailer(cmsContext, selectedMarketArea, selectedRetailer);
        
        // LOCALIZATION LIST
        buildLocalization(cmsContext, selectedMarketArea, selectedLocalization);
        
        return cmsContext;
    }
    
    @GET
    @Path("catalog/categories/{marketAreaCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public CmsCategories categoriesByMarketArea(@PathParam("marketAreaCode") final String marketAreaCode) {
        CmsCategories cmsCategories = new CmsCategories();

        MarketAreaPojo selectedMarketArea = marketPojoService.getMarketAreaByCode(marketAreaCode);
        if(selectedMarketArea != null){
            selectedMarketArea.setMarket(null);
            selectedMarketArea.setRetailers(null);
            selectedMarketArea.setLocalizations(null);
            
            cmsCategories.setMarketArea(selectedMarketArea);
            List<CatalogCategoryPojo> categories = selectedMarketArea.getCatalog().getCatalogCategories();
            for (Iterator<CatalogCategoryPojo> iterator = categories.iterator(); iterator.hasNext();) {
                CatalogCategoryPojo catalogCategoryPojo = (CatalogCategoryPojo) iterator.next();
                catalogCategoryPojo.setCatalogCategoryGlobalAttributes(null);
                catalogCategoryPojo.setCatalogCategoryMarketAreaAttributes(null);
                catalogCategoryPojo.setProductMarketings(null);
            }
            cmsCategories.setCatalogCategories(categories);
            
            CatalogPojo catalog = selectedMarketArea.getCatalog();
            catalog.setCatalogCategories(null);
            cmsCategories.setCatalog(catalog);

        }

        return cmsCategories;
    }
    
    @GET
    @Path("catalog/products/{marketAreaCode}/{categoryCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public CmsProducts productsByMarketArea(@PathParam("marketAreaCode") final String marketAreaCode, @PathParam("categoryCode") final String categoryCode) {
        CmsProducts cmsProducts = new CmsProducts();

        MarketAreaPojo selectedMarketArea = marketPojoService.getMarketAreaByCode(marketAreaCode);
        if(selectedMarketArea != null){
            selectedMarketArea.setMarket(null);
            selectedMarketArea.setRetailers(null);
            selectedMarketArea.setLocalizations(null);
            
            cmsProducts.setMarketArea(selectedMarketArea);
            
            List<CatalogCategoryPojo> categories = selectedMarketArea.getCatalog().getCatalogCategories();
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
            catalog.setCatalogCategories(null);
            cmsProducts.setCatalog(catalog);

        }

        return cmsProducts;
    }
    
    private void buildMarketPlace(CmsContext cmsContext, MarketPlacePojo selectedMarketPlace){
        List<MarketPlacePojo> marketPlaces = new ArrayList<MarketPlacePojo>();
        MarketPlacePojo masterMarketPlace = new MarketPlacePojo();
        masterMarketPlace.setCode("MASTER_MARKETPLACE");
        masterMarketPlace.setName("Master MarketPlace");
        if(selectedMarketPlace == null){
            masterMarketPlace.setSelected(true);
        }
        marketPlaces.add(masterMarketPlace);
        
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
        
        cmsContext.setMarketPlaces(marketPlaces);
    }

    private void buildMarket(CmsContext cmsContext, MarketPlacePojo selectedMarketPlace, MarketPojo selectedMarket){
        List<MarketPojo> markets = new ArrayList<MarketPojo>();
        MarketPojo masterMarket = new MarketPojo();
        masterMarket.setCode("MASTER_MARKET");
        masterMarket.setName("Master Market");
        if(selectedMarket == null){
            masterMarket.setSelected(true);
        }
        markets.add(masterMarket);
        
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
        
        cmsContext.setMarkets(markets);
    }
    
    private void buildMarketArea(CmsContext cmsContext, MarketPojo selectedMarket, MarketAreaPojo selectedMarketArea){
        List<MarketAreaPojo> marketAreas = new ArrayList<MarketAreaPojo>();
        MarketAreaPojo masterArea = new MarketAreaPojo();
        masterArea.setCode("MASTER_MARKET_AREA");
        masterArea.setName("Master Market Area");
        if(selectedMarket == null){
            masterArea.setSelected(true);
        }
        marketAreas.add(masterArea);
        
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
        
        cmsContext.setMarketAreas(marketAreas);
    }
    
    private void buildRetailer(CmsContext cmsContext, MarketAreaPojo selectedMarketArea, RetailerPojo selectedRetailer){
        List<RetailerPojo> retailers = new ArrayList<RetailerPojo>();
        RetailerPojo retailer = new RetailerPojo();
        retailer.setCode("MASTER_RETAILER");
        retailer.setName("Master Retailer");
        if(selectedRetailer == null){
            retailer.setSelected(true);
        }
        retailers.add(retailer);
        
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
        
        cmsContext.setRetailers(retailers);
    }
    
    private void buildLocalization(CmsContext cmsContext, MarketAreaPojo selectedMarketArea, LocalizationPojo selectedLocalization){
        List<LocalizationPojo> localizations = new ArrayList<LocalizationPojo>();
        LocalizationPojo localization = new LocalizationPojo();
        localization.setCode("MASTER_LOCALIZATION");
        localization.setName("Master Localization");
        if(selectedLocalization == null){
            localization.setSelected(true);
        }
        localizations.add(localization);
        
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
        
        cmsContext.setLocalizations(localizations);
    }
    
}