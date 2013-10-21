package fr.hoteia.qalingo.core.rest.controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import fr.hoteia.qalingo.core.pojo.store.StorePojo;
import fr.hoteia.qalingo.core.service.pojo.StorePojoService;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Path("/store/")
@Component("storeRestController")
public class StoreRestController {

    @Autowired private StorePojoService storeService;

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
