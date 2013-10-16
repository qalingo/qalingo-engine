package fr.hoteia.qalingo.core.rest.controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import fr.hoteia.qalingo.core.pojo.retailer.RetailerPojo;
import fr.hoteia.qalingo.core.pojo.store.StorePojo;
import fr.hoteia.qalingo.core.service.pojo.RetailerPojoService;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Path("/retailer/")
@Component("retailerRestController")
public class RetailerRestController {

    @Autowired private RetailerPojoService retailerService;

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
