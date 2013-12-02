package org.hoteia.qalingo.core.rest.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hoteia.qalingo.core.pojo.RequestCms;
import org.hoteia.qalingo.core.service.pojo.MarketPojoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Path("/cms/")
@Component("cmsRestService")
public class CmsRestService {

    @Autowired
    private MarketPojoService marketPojoService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RequestCms getMarketPlaces() {
        RequestCms requestCms = new RequestCms();
        requestCms.setMarketPlaces(marketPojoService.getMarketPlaces());
        return requestCms;
    }

    @GET
    @Path("{marketplace/set}")
    @Produces(MediaType.APPLICATION_JSON)
    public RequestCms selectMarketPlace(@PathParam("marketPlaceCode") final String marketPlaceCode) {
        RequestCms requestCms = new RequestCms();
        requestCms.setMarketPlaces(marketPojoService.getMarketPlaces());
        return requestCms;
    }
    
    @GET
    @Path("{market/set}")
    @Produces(MediaType.APPLICATION_JSON)
    public RequestCms selectMarket(@PathParam("marketCode") final String marketCode) {
        RequestCms requestCms = new RequestCms();
        requestCms.setMarketPlaces(marketPojoService.getMarketPlaces());
        return requestCms;
    }
    
    @GET
    @Path("{marketarea/set}")
    @Produces(MediaType.APPLICATION_JSON)
    public RequestCms selectMarketArea(@PathParam("marketAreaCode") final String marketAreaCode) {
        RequestCms requestCms = new RequestCms();
        requestCms.setMarketPlaces(marketPojoService.getMarketPlaces());
        return requestCms;
    }
    
    @GET
    @Path("{retailer/set}")
    @Produces(MediaType.APPLICATION_JSON)
    public RequestCms selectRetailer(@PathParam("marketAreaCode") final String marketAreaCode, @PathParam("retailerCode") final String retailerCode) {
        RequestCms requestCms = new RequestCms();
        requestCms.setMarketPlaces(marketPojoService.getMarketPlaces());
        return requestCms;
    }
    
    @GET
    @Path("{localization/set}")
    @Produces(MediaType.APPLICATION_JSON)
    public RequestCms selectLocalization(@PathParam("marketAreaCode") final String marketAreaCode, @PathParam("localizationCode") final String localizationCode) {
        RequestCms requestCms = new RequestCms();
        requestCms.setMarketPlaces(marketPojoService.getMarketPlaces());
        return requestCms;
    }

}