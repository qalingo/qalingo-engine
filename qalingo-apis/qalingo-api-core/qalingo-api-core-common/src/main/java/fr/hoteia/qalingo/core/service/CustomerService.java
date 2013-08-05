/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.service;

import java.util.List;

import fr.hoteia.qalingo.core.domain.Customer;

public interface CustomerService {

	Customer getCustomerById(String customerId);

	Customer getCustomerByCode(String code);

	Customer getCustomerByLoginOrEmail(String loginOrEmail);

	List<Customer> findCustomers();
	
	List<Customer> findCustomer(Customer criteria);
	
	void saveOrUpdateCustomer(Customer customer) throws Exception;
	
	void deleteCustomer(Customer customer);
	
}
