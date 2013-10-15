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

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.hoteia.qalingo.core.pojo.customer.CustomerPojo;
import fr.hoteia.qalingo.core.pojo.service.CustomerPojoService;
import fr.hoteia.qalingo.core.ws.service.CustomerWebService;

@Service("customerWebService")
@WebService(endpointInterface = "fr.hoteia.qalingo.core.ws.service.CustomerWebService")
public class CustomerWebServiceImpl implements CustomerWebService {

    @Autowired private CustomerPojoService customerService;

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
