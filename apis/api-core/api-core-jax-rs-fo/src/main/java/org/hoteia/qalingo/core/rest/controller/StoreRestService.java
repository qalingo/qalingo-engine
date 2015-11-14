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

import org.hoteia.qalingo.core.pojo.retailer.StoreListPojoResponse;
import org.hoteia.qalingo.core.pojo.retailer.StorePojoResponse;
import org.hoteia.qalingo.core.pojo.store.StorePojo;
import org.hoteia.qalingo.core.service.pojo.RetailerPojoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Path("/store/")
@Component("storeRestService")
public class StoreRestService {

    @Autowired
    protected RetailerPojoService retailerPojoService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public StoreListPojoResponse getAllStores() {
        StoreListPojoResponse storeListPojoResponse = new StoreListPojoResponse();
        List<StorePojo> stores = retailerPojoService.getAllStores();
        storeListPojoResponse.setStores(stores);
        return storeListPojoResponse;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{code}")
    public StorePojoResponse getStoreByCode(@PathParam("code") final String code) {
        StorePojoResponse StorePojoResponse = new StorePojoResponse();
        StorePojo store = retailerPojoService.getStoreByCode(code);
        StorePojoResponse.setStore(store);
        return StorePojoResponse;
    }

}