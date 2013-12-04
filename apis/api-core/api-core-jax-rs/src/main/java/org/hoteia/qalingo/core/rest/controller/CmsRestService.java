package org.hoteia.qalingo.core.rest.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hoteia.qalingo.core.pojo.LocalizationPojo;
import org.hoteia.qalingo.core.pojo.RequestCms;
import org.hoteia.qalingo.core.pojo.market.MarketAreaPojo;
import org.hoteia.qalingo.core.pojo.market.MarketPlacePojo;
import org.hoteia.qalingo.core.pojo.market.MarketPojo;
import org.hoteia.qalingo.core.pojo.retailer.RetailerPojo;
import org.hoteia.qalingo.core.service.pojo.LocalizationPojoService;
import org.hoteia.qalingo.core.service.pojo.MarketPojoService;
import org.hoteia.qalingo.core.service.pojo.RetailerPojoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Path("/cms/")
@Component("cmsRestService")
public class CmsRestService {

    @Autowired
    private MarketPojoService marketPojoService;

    @Autowired
    private RetailerPojoService retailerPojoService;

    @Autowired
    private LocalizationPojoService localizationPojoService;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RequestCms getMarketPlaces() {
        // DEFAULT CALLBACK WITH ALL THE MARKET PLACES AND DEFAULT MASTER VALUE FOR MARKET / MARKET AREA / RETAILER / LOCALIZATION
        RequestCms requestCms = new RequestCms();

        // MARKET PLACE LIST
        buildMarketPlace(requestCms, null);

        // MARKET LIST
        buildMarket(requestCms, null, null);

        // MARKET AREA LIST
        buildMarketArea(requestCms, null, null);

        // RETAILER LIST
        buildRetailer(requestCms, null);

        // LOCALIZATION LIST
        buildLocalization(requestCms, null);

        return requestCms;
    }

    @GET
    @Path("marketplace/set/{marketPlaceCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public RequestCms selectMarketPlace(@PathParam("marketPlaceCode") final String marketPlaceCode) {
        RequestCms requestCms = new RequestCms();
        
        MarketPlacePojo selectedMarketPlace = marketPojoService.getMarketPlaceByCode(marketPlaceCode);
        
        // MARKET PLACE LIST
        buildMarketPlace(requestCms, selectedMarketPlace);
        
        // MARKET LIST
        buildMarket(requestCms, selectedMarketPlace, null);

        // MARKET AREA LIST
        buildMarketArea(requestCms, null, null);

        // RETAILER LIST
        buildRetailer(requestCms, null);

        // LOCALIZATION LIST
        buildLocalization(requestCms, null);

        return requestCms;
    }
    
    @GET
    @Path("market/set/{marketCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public RequestCms selectMarket(@PathParam("marketCode") final String marketCode) {
        RequestCms requestCms = new RequestCms();

        MarketPojo selectedMarket = marketPojoService.getMarketByCode(marketCode);

        MarketPlacePojo selectedMarketPlace = marketPojoService.getMarketPlaceByCode(selectedMarket.getMarketPlace().getCode());

        // MARKET PLACE LIST
        buildMarketPlace(requestCms, selectedMarketPlace);
        
        // MARKET LIST
        buildMarket(requestCms, selectedMarketPlace, selectedMarket);
        
        // MARKET AREA LIST
        buildMarketArea(requestCms, selectedMarket, null);

        // RETAILER LIST
        buildRetailer(requestCms, null);

        // LOCALIZATION LIST
        buildLocalization(requestCms, null);

        return requestCms;
    }
    
    @GET
    @Path("marketarea/set/{marketAreaCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public RequestCms selectMarketArea(@PathParam("marketAreaCode") final String marketAreaCode) {
        RequestCms requestCms = new RequestCms();

        MarketAreaPojo selectedMarketArea = marketPojoService.getMarketAreaByCode(marketAreaCode);

        MarketPojo selectedMarket = marketPojoService.getMarketByCode(selectedMarketArea.getMarket().getCode());

        MarketPlacePojo selectedMarketPlace = marketPojoService.getMarketPlaceByCode(selectedMarket.getMarketPlace().getCode());

        // MARKET PLACE LIST
        buildMarketPlace(requestCms, selectedMarketPlace);
        
        // MARKET LIST
        buildMarket(requestCms, selectedMarketPlace, selectedMarket);
        
        // MARKET AREA LIST
        buildMarketArea(requestCms, selectedMarket, selectedMarketArea);

        // RETAILER LIST
        buildRetailer(requestCms, null);

        // LOCALIZATION LIST
        buildLocalization(requestCms, null);
        
        return requestCms;
    }
    
    @GET
    @Path("retailer/set/{marketAreaCode}/{retailerCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public RequestCms selectRetailer(@PathParam("marketAreaCode") final String marketAreaCode, @PathParam("retailerCode") final String retailerCode) {
        RequestCms requestCms = new RequestCms();

        RetailerPojo selectedRetailer = retailerPojoService.getRetailerByCode(retailerCode);
        
        MarketAreaPojo selectedMarketArea = marketPojoService.getMarketAreaByCode(marketAreaCode);

        MarketPojo selectedMarket = marketPojoService.getMarketByCode(selectedMarketArea.getMarket().getCode());

        MarketPlacePojo selectedMarketPlace = marketPojoService.getMarketPlaceByCode(selectedMarket.getMarketPlace().getCode());

        // MARKET PLACE LIST
        buildMarketPlace(requestCms, selectedMarketPlace);
        
        // MARKET LIST
        buildMarket(requestCms, selectedMarketPlace, selectedMarket);
        
        // MARKET AREA LIST
        buildMarketArea(requestCms, selectedMarket, selectedMarketArea);

        // RETAILER LIST
        buildRetailer(requestCms, selectedRetailer);

        // LOCALIZATION LIST
        buildLocalization(requestCms, null);
        
        return requestCms;
    }
    
    @GET
    @Path("localization/set/{marketAreaCode}/{retailerCode}/{localizationCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public RequestCms selectLocalization(@PathParam("marketAreaCode") final String marketAreaCode, @PathParam("retailerCode") final String retailerCode, 
                                         @PathParam("localizationCode") final String localizationCode) {
        RequestCms requestCms = new RequestCms();

        LocalizationPojo selectedLocalization = localizationPojoService.getLocalizationByCode(retailerCode);

        RetailerPojo selectedRetailer = retailerPojoService.getRetailerByCode(retailerCode);

        MarketAreaPojo selectedMarketArea = marketPojoService.getMarketAreaByCode(marketAreaCode);

        MarketPojo selectedMarket = marketPojoService.getMarketByCode(selectedMarketArea.getMarket().getCode());

        MarketPlacePojo selectedMarketPlace = marketPojoService.getMarketPlaceByCode(selectedMarket.getMarketPlace().getCode());

        // MARKET PLACE LIST
        buildMarketPlace(requestCms, selectedMarketPlace);
        
        // MARKET LIST
        buildMarket(requestCms, selectedMarketPlace, selectedMarket);
        
        // MARKET AREA LIST
        buildMarketArea(requestCms, selectedMarket, selectedMarketArea);

        // RETAILER LIST
        buildRetailer(requestCms, selectedRetailer);
        
        // LOCALIZATION LIST
        buildLocalization(requestCms, selectedLocalization);
        
        return requestCms;
    }
    
    private void buildMarketPlace(RequestCms requestCms, MarketPlacePojo selectedMarketPlace){
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
        
        requestCms.setMarketPlaces(marketPlaces);
    }

    private void buildMarket(RequestCms requestCms, MarketPlacePojo selectedMarketPlace, MarketPojo selectedMarket){
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
        
        requestCms.setMarkets(markets);
    }
    
    private void buildMarketArea(RequestCms requestCms, MarketPojo selectedMarket, MarketAreaPojo selectedMarketArea){
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
        
        requestCms.setMarketAreas(marketAreas);
    }
    
    private void buildRetailer(RequestCms requestCms, RetailerPojo selectedRetailer){
        List<RetailerPojo> retailers = new ArrayList<RetailerPojo>();
        RetailerPojo retailer = new RetailerPojo();
        retailer.setCode("MASTER_RETAILER");
        retailer.setName("Master Retailer");
        if(selectedRetailer == null){
            retailer.setSelected(true);
        }
        retailers.add(retailer);
        
        List<RetailerPojo> allRetailers = retailerPojoService.findAllRetailers();
        if(selectedRetailer != null){
            for (Iterator<RetailerPojo> iterator = allRetailers.iterator(); iterator.hasNext();) {
                RetailerPojo retailerPojo = (RetailerPojo) iterator.next();
                if(selectedRetailer != null
                        && retailerPojo.getCode().equals(selectedRetailer.getCode())){
                    retailerPojo.setSelected(true);
                }
                retailerPojo.setCustomerComments(null);
                retailerPojo.setCustomerRates(null);
                retailerPojo.setStores(null);
                retailers.add(retailerPojo);
            }
        }
        
        requestCms.setRetailers(retailers);
    }
    
    private void buildLocalization(RequestCms requestCms, LocalizationPojo selectedLocalization){
        List<LocalizationPojo> localizations = new ArrayList<LocalizationPojo>();
        LocalizationPojo localization = new LocalizationPojo();
        localization.setCode("MASTER_LOCALIZATION");
        localization.setName("Master Localization");
        if(selectedLocalization == null){
            localization.setSelected(true);
        }
        localizations.add(localization);
        
        List<LocalizationPojo> allLocalization = localizationPojoService.findAllLocalizations();
        if(selectedLocalization != null){
            for (Iterator<LocalizationPojo> iterator = allLocalization.iterator(); iterator.hasNext();) {
                LocalizationPojo localizationPojo = (LocalizationPojo) iterator.next();
                if(selectedLocalization != null
                        && localizationPojo.getCode().equals(selectedLocalization.getCode())){
                    localizationPojo.setSelected(true);
                }
                localizations.add(localizationPojo);
            }
        }
        
        requestCms.setLocalizations(localizations);
    }
    
}