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

    @Override
    public Customer getCustomerById(final String rawCustomerId) {
        long customerId = -1;
        try {
            customerId = Long.parseLong(rawCustomerId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return customerDao.getCustomerById(customerId);
    }

    @Override
    public Customer getCustomerByCode(final String code) {
        return customerDao.getCustomerByCode(code);
    }

    @Override
    public Customer getCustomerByPermalink(final String permalink) {
        return customerDao.getCustomerByPermalink(permalink);
    }

    @Override
    public Customer getCustomerByLoginOrEmail(final String usernameOrEmail) {
        return customerDao.getCustomerByLoginOrEmail(usernameOrEmail);
    }

    @Override
    public List<Customer> findCustomers() {
        return customerDao.findCustomers();
    }

    @Override
    public void saveOrUpdateCustomer(final Customer customer) throws Exception {
        customerDao.saveOrUpdateCustomer(customer);
    }

    @Override
    public void deleteCustomer(final Customer customer) {
        customerDao.deleteCustomer(customer);
    }

    // CREDENTIAL

    @Override
    public void saveOrUpdateCustomerCredential(final CustomerCredential customerCredential) throws Exception {
        customerDao.saveOrUpdateCustomerCredential(customerCredential);
    }

}
