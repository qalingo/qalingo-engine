package fr.hoteia.qalingo.core.rest.controller;

import fr.hoteia.qalingo.core.pojo.customer.CustomerPojo;
import fr.hoteia.qalingo.core.pojo.service.CustomerPojoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/customer/")
@Component("customerRestController")
public class CustomerRestController {

    @Autowired private CustomerPojoService customerService;

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
