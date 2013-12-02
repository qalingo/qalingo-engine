package org.hoteia.qalingo.core.rest.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hoteia.qalingo.core.pojo.store.StorePojo;
import org.hoteia.qalingo.core.service.pojo.StorePojoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Path("/store/")
@Component("storeRestService")
public class StoreRestService {

    @Autowired
    private StorePojoService storeService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<StorePojo> getAllStores() {
        return storeService.getAllStores();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public StorePojo getStoreById(@PathParam("id") final String id) {
        return storeService.getStoreById(id);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveOrUpdate(final StorePojo storeJsonBean) {
        storeService.saveOrUpdate(storeJsonBean);
    }
}
