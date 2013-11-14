/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.ws.service.impl;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.service.CustomerService;
import fr.hoteia.qalingo.core.ws.pojo.CustomerWsPojo;
import fr.hoteia.qalingo.core.ws.service.CustomerWebService;

@WebService(endpointInterface="fr.hoteia.qalingo.core.ws.service.CustomerWebService")
@Service("customerWebService")
public class CustomerWebServiceImpl implements CustomerWebService {
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected CustomerService customerService;
	
	public CustomerWsPojo getCustomerById(String customerId){
		Customer customer = customerService.getCustomerById(customerId);
		CustomerWsPojo customerBean = buildCustomerWsPojo(customer);
		return customerBean;
	}
	
	protected CustomerWsPojo buildCustomerWsPojo(Customer customer){
		CustomerWsPojo customerWsPojo = new CustomerWsPojo();
		customerWsPojo.setLastname(customer.getLastname());
	
		// TODO : ...
	
		return customerWsPojo;
	}
	
}
