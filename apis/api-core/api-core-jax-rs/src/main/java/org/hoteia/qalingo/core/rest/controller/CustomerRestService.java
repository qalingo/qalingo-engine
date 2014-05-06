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

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hoteia.qalingo.core.pojo.customer.CustomerPojo;
import org.hoteia.qalingo.core.service.pojo.CustomerPojoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Path("/customer/")
@Component("customerRestService")
public class CustomerRestService {

    @Autowired
    private CustomerPojoFactory customerService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CustomerPojo> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerPojo getCustomerById(@PathParam("id") final String id) {
        return customerService.getCustomerById(id);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveOrUpdate(final CustomerPojo customerJsonPojo) throws Exception {
        customerService.saveOrUpdate(customerJsonPojo);
    }

}
