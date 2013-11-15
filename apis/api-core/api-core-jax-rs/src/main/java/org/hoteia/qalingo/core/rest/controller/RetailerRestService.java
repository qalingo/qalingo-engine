package org.hoteia.qalingo.core.rest.controller;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.hoteia.qalingo.core.pojo.retailer.RetailerPojo;
import org.hoteia.qalingo.core.service.pojo.RetailerPojoService;

@Path("/retailer/")
@Component("retailerRestService")
public class RetailerRestService {

    @Autowired
    private RetailerPojoService retailerService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<RetailerPojo> getAllRetailers() {
        return retailerService.findAllRetailers();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public RetailerPojo getStoreById(@PathParam("id") final String id) {
        return retailerService.getRetailerById(id);
    }

}