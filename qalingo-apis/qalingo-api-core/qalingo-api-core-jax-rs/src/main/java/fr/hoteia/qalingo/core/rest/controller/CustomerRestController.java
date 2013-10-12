package fr.hoteia.qalingo.core.rest.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.hoteia.qalingo.core.rest.pojo.CustomerJsonPojo;
import fr.hoteia.qalingo.core.rest.service.CustomerRestService;

@Path("/customer/")
@Component("customerRestController")
public class CustomerRestController {

    @Autowired private CustomerRestService customerService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CustomerJsonPojo> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public CustomerJsonPojo getCustomerById(@PathParam("id") final String id) {
        return customerService.getCustomerById(id);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveOrUpdate(final CustomerJsonPojo customerJsonPojo) throws Exception {
        customerService.saveOrUpdate(customerJsonPojo);
    }

}
