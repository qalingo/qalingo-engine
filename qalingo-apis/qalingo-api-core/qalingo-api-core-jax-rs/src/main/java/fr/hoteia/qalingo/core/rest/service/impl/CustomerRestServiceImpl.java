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

import java.util.ArrayList;
import java.util.Iterator;
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

import fr.hoteia.qalingo.core.common.domain.Customer;
import fr.hoteia.qalingo.core.common.service.CustomerService;
import fr.hoteia.qalingo.core.rest.pojo.CustomerJsonPojo;
import fr.hoteia.qalingo.core.rest.service.CustomerRestService;

@Service("customerRestService")
@Path("/customer/")
public class CustomerRestServiceImpl implements CustomerRestService {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected CustomerService customerService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CustomerJsonPojo> getCustomers() {
		List<Customer> customers = customerService.findCustomers();
		List<CustomerJsonPojo> customerCustomerJsonBeans = new ArrayList<CustomerJsonPojo>();
		for (Iterator<Customer> iterator = customers.iterator(); iterator.hasNext();) {
			Customer customer = (Customer) iterator.next();
			CustomerJsonPojo customerJsonBean = buildCustomerJsonBean(customer);
			customerCustomerJsonBeans.add(customerJsonBean);
		}
		return customerCustomerJsonBeans;
	}
 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public CustomerJsonPojo getCustomer(@PathParam("id") String id) {
		Customer customer = customerService.getCustomerById(id);
		CustomerJsonPojo customerJsonBean = buildCustomerJsonBean(customer);
		return customerJsonBean;
	}
 
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void saveSomeBean(CustomerJsonPojo customerJsonBean) {
		Customer customer = buildCustomer(customerJsonBean);
		customerService.saveOrUpdateCustomer(customer);
	}

	protected CustomerJsonPojo buildCustomerJsonBean(Customer customer){
		CustomerJsonPojo customerJsonBean = new CustomerJsonPojo();
		customerJsonBean.setLastname(customer.getLastname());
		
		// TODO : ...
		
		return customerJsonBean;
	}
	
	protected Customer buildCustomer(CustomerJsonPojo customerJsonBean){
		Customer customer = new Customer();
		customer.setLastname(customerJsonBean.getLastname());
	
		// TODO : ...
	
		return customer;
	}
	
}
