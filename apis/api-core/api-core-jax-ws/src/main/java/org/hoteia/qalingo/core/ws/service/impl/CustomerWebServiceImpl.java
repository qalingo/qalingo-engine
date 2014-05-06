/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.ws.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.hoteia.qalingo.core.pojo.customer.CustomerPojo;
import org.hoteia.qalingo.core.service.pojo.CustomerPojoFactory;
import org.hoteia.qalingo.core.ws.service.CustomerWebService;

@Service("customerWebService")
@WebService(endpointInterface = "org.hoteia.qalingo.core.ws.service.CustomerWebService")
public class CustomerWebServiceImpl implements CustomerWebService {

    @Autowired private CustomerPojoFactory customerService;

    @Override
    public CustomerPojo getCustomerById(final String customerId) {
        return customerService.getCustomerById(customerId);
    }

    @Override
    public List<CustomerPojo> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @Override
    public void saveOrUpdateCustomer(final CustomerPojo customer) throws Exception {
        customerService.saveOrUpdate(customer);
    }

}
