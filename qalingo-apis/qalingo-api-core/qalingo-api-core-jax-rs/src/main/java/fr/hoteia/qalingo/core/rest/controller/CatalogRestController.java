package fr.hoteia.qalingo.core.rest.controller;

import fr.hoteia.qalingo.core.pojo.catalog.CatalogMasterPojo;
import fr.hoteia.qalingo.core.service.pojo.CatalogPojoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/catalog/")
@Component("catalogRestController")
public class CatalogRestController {

    @Autowired private CatalogPojoService catalogService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CatalogMasterPojo> getAllCatalogMasters() {
        return catalogService.getAllCatalogMasters();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CatalogMasterPojo getCustomerById(@PathParam("id") final String id) {
        return catalogService.getProductCatalogById(id);
    }

}
