package fr.hoteia.qalingo.core.ws.service.impl;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.hoteia.qalingo.core.common.domain.Customer;
import fr.hoteia.qalingo.core.common.service.CustomerService;
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
