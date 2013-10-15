package fr.hoteia.qalingo.core.rest.controller;

import fr.hoteia.qalingo.core.pojo.service.StorePojoService;
import fr.hoteia.qalingo.core.pojo.store.StorePojo;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/store/")
@Component("storeRestController")
public class StoreRestController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired private DozerBeanMapper mapper;
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
