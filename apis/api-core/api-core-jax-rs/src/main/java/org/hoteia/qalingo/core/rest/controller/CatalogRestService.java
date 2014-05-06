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

import org.hoteia.qalingo.core.pojo.catalog.CatalogPojo;
import org.hoteia.qalingo.core.service.pojo.CatalogPojoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Path("/catalog/")
@Component("catalogRestService")
public class CatalogRestService {

    @Autowired
    private CatalogPojoFactory catalogService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CatalogPojo> getAllCatalogMasters() {
        return catalogService.getAllCatalogMasters();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CatalogPojo getCustomerById(@PathParam("id") final String id) {
        return catalogService.getMasterCatalogById(id);
    }

}