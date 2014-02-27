/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.dao.CustomerDao;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.CustomerCredential;
import org.hoteia.qalingo.core.service.CustomerService;

@Transactional
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired private CustomerDao customerDao;

    public Customer getCustomerById(final Long customerId, Object... params) {
        return customerDao.getCustomerById(customerId, params);
    }

    public Customer getCustomerById(final String rawCustomerId, Object... params) {
        long customerId = -1;
        try {
            customerId = Long.parseLong(rawCustomerId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getCustomerById(customerId, params);
    }

    public Customer getCustomerByCode(final String code, Object... params) {
        return customerDao.getCustomerByCode(code, params);
    }

    public Customer getCustomerByPermalink(final String permalink, Object... params) {
        return customerDao.getCustomerByPermalink(permalink, params);
    }

    public Customer getCustomerByLoginOrEmail(final String usernameOrEmail, Object... params) {
        return customerDao.getCustomerByLoginOrEmail(usernameOrEmail, params);
    }

    public List<Customer> findCustomers(Object... params) {
        return customerDao.findCustomers(params);
    }

    public void saveOrUpdateCustomer(final Customer customer) throws Exception {
        customerDao.saveOrUpdateCustomer(customer);
    }

    public void deleteCustomer(final Customer customer) {
        customerDao.deleteCustomer(customer);
    }

    // CREDENTIAL

    public void saveOrUpdateCustomerCredential(final CustomerCredential customerCredential) throws Exception {
        customerDao.saveOrUpdateCustomerCredential(customerCredential);
    }

}