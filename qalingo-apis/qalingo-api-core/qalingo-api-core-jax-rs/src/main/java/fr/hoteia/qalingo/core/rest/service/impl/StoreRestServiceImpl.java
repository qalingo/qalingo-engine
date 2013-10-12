/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.hoteia.qalingo.core.domain.Store;
import fr.hoteia.qalingo.core.rest.pojo.StoreJsonPojo;
import fr.hoteia.qalingo.core.rest.util.PojoMapper;
import fr.hoteia.qalingo.core.service.RetailerService;

@Service("storeRestService")
@Path("/store/")
public class StoreRestServiceImpl {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired private RetailerService retailerService;

    @Autowired @Qualifier("storeMapper") private PojoMapper<Store, StoreJsonPojo> pojoMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<StoreJsonPojo> getAllStores() {
        List<Store> stores = retailerService.findStores();
        return new ArrayList<StoreJsonPojo>(pojoMapper.toPojo(stores));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public StoreJsonPojo getStoreById(@PathParam("id") final String id) {
        Store store = retailerService.getStoreById(id);
        return pojoMapper.toPojo(store);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveOrUpdate(final StoreJsonPojo storeJsonBean) {
        Store store = pojoMapper.fromPojo(storeJsonBean);
        retailerService.saveOrUpdateStore(store);
    }

}
