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
import fr.hoteia.qalingo.core.rest.api.bean.CustomerJsonBean;
import fr.hoteia.qalingo.core.rest.service.CustomerRestService;

@Service("customerRestService")
@Path("/customer/")
public class CustomerRestServiceImpl implements CustomerRestService {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected CustomerService customerService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CustomerJsonBean> getCustomers() {
		List<Customer> customers = customerService.findCustomers();
		List<CustomerJsonBean> customerCustomerJsonBeans = new ArrayList<CustomerJsonBean>();
		for (Iterator<Customer> iterator = customers.iterator(); iterator.hasNext();) {
			Customer customer = (Customer) iterator.next();
			CustomerJsonBean customerJsonBean = buildCustomerJsonBean(customer);
			customerCustomerJsonBeans.add(customerJsonBean);
		}
		return customerCustomerJsonBeans;
	}
 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public CustomerJsonBean getCustomer(@PathParam("id") String id) {
		Customer customer = customerService.getCustomerById(id);
		CustomerJsonBean customerJsonBean = buildCustomerJsonBean(customer);
		return customerJsonBean;
	}
 
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void saveSomeBean(CustomerJsonBean customerJsonBean) {
		Customer customer = buildCustomer(customerJsonBean);
		customerService.saveOrUpdateCustomer(customer);
	}

	protected CustomerJsonBean buildCustomerJsonBean(Customer customer){
			CustomerJsonBean customerJsonBean = new CustomerJsonBean();
		
		// TODO : ...
		
		customerJsonBean.setLastname(customer.getLastname());
		return customerJsonBean;
	}
	
	protected Customer buildCustomer(CustomerJsonBean customerJsonBean){
		Customer customer = new Customer();
		customer.setLastname(customerJsonBean.getLastname());
	
		// TODO : ...
	
		return customer;
	}
	
}
