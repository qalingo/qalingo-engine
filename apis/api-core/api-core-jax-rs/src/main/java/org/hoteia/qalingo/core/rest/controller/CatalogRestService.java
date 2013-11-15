package org.hoteia.qalingo.core.rest.controller;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.hoteia.qalingo.core.pojo.catalog.CatalogPojo;
import org.hoteia.qalingo.core.service.pojo.CatalogPojoService;

@Path("/catalog/")
@Component("catalogRestService")
public class CatalogRestService {

    @Autowired
    private CatalogPojoService catalogService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CatalogPojo> getAllCatalogMasters() {
        return catalogService.getAllCatalogMasters();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CatalogPojo getCustomerById(@PathParam("id") final String id) {
        return catalogService.getCatalogById(id);
    }

}