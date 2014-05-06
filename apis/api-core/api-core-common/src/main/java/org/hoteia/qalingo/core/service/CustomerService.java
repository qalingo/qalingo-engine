/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import java.util.List;

import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.CustomerCredential;

public interface CustomerService {

    Customer getCustomerById(Long customerId, Object... params);
    
	Customer getCustomerById(String customerId, Object... params);

	Customer getCustomerByCode(String code, Object... params);

	Customer getCustomerByPermalink(String permalink, Object... params);

	Customer getCustomerByLoginOrEmail(String loginOrEmail, Object... params);

	List<Customer> findCustomers(Object... params);
	
	Customer saveOrUpdateCustomer(Customer customer) throws Exception;
	
	void deleteCustomer(Customer customer);
	
	// CREDENTIAL
	
	CustomerCredential saveOrUpdateCustomerCredential(CustomerCredential customerCredential) throws Exception;
	
}