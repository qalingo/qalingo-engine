/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.rest.service.impl;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.rest.pojo.CustomerJsonPojo;
import fr.hoteia.qalingo.core.rest.service.CustomerRestService;
import fr.hoteia.qalingo.core.rest.util.JsonFactory;
import fr.hoteia.qalingo.core.service.CustomerService;

@Service("customerRestService")
@Path("/customer/")
public class CustomerRestServiceImpl implements CustomerRestService {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected CustomerService customerService;
	
	@Autowired
	protected JsonFactory jsonFactory;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CustomerJsonPojo> getCustomers() {
		List<Customer> customers = customerService.findCustomers();
		List<CustomerJsonPojo> customerCustomerJsonPojos = jsonFactory.buildJsonCustomers(customers);
		return customerCustomerJsonPojos;
	}
 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public CustomerJsonPojo getCustomer(@PathParam("id") String id) {
		Customer customer = customerService.getCustomerById(id);
		CustomerJsonPojo customerJsonPojo = jsonFactory.buildJsonCustomer(customer);
		return customerJsonPojo;
	}
 
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void saveSomeBean(CustomerJsonPojo customerJsonPojo) {
		Customer customer = jsonFactory.buildCustomer(customerJsonPojo);
		customerService.saveOrUpdateCustomer(customer);
	}
	
}
