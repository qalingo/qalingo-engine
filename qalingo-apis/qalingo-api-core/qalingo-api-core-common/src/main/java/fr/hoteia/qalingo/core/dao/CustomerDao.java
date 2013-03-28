/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.dao;

import java.util.List;

import fr.hoteia.qalingo.core.domain.Customer;

public interface CustomerDao {

	Customer getCustomerById(Long customerId);

	Customer getCustomerByLoginOrEmail(String usernameOrEmail);
	 
	List<Customer> findCustomers();
	
	List<Customer> findByExample(Customer customerExample);

	void saveOrUpdateCustomer(Customer customer);

	void deleteCustomer(Customer customer);

}