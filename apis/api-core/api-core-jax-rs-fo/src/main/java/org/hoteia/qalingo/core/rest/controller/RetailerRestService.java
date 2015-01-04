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

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hoteia.qalingo.core.pojo.retailer.RetailerListPojoResponse;
import org.hoteia.qalingo.core.pojo.retailer.RetailerPojo;
import org.hoteia.qalingo.core.pojo.retailer.RetailerPojoResponse;
import org.hoteia.qalingo.core.service.pojo.RetailerPojoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Path("/retailer/")
@Component("retailerRestService")
public class RetailerRestService {

    @Autowired
    protected RetailerPojoService retailerService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RetailerListPojoResponse getAllRetailers() {
        RetailerListPojoResponse retailerListPojoResponse = new RetailerListPojoResponse();
        List<RetailerPojo> retailers = retailerService.findAllRetailers();
        retailerListPojoResponse.setRetailers(retailers);
        return retailerListPojoResponse;
    }

    @GET
    @Path("{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public RetailerPojoResponse getRetailerByCode(@PathParam("code") final String code) {
        RetailerPojoResponse retailerPojoResponse = new RetailerPojoResponse();
        RetailerPojo retailerPojo = retailerService.getRetailerByCode(code);
        retailerPojoResponse.setRetailer(retailerPojo);
        return retailerPojoResponse;
    }

}