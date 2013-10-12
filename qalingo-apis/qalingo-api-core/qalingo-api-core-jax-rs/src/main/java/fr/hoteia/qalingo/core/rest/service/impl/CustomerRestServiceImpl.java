/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.rest.service.impl;

import java.util.List;

import javax.ws.rs.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.rest.pojo.CustomerJsonPojo;
import fr.hoteia.qalingo.core.rest.service.CustomerRestService;
import fr.hoteia.qalingo.core.rest.util.JsonBuilder;
import fr.hoteia.qalingo.core.service.CustomerService;

@Service("customerRestService")
@Transactional(readOnly = true)
public class CustomerRestServiceImpl implements CustomerRestService {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired private CustomerService customerService;

    @Autowired @Qualifier("customerJsonBuilder") private JsonBuilder<Customer, CustomerJsonPojo> jsonFactory;

    /*
     * (non-Javadoc)
     * 
     * @see fr.hoteia.qalingo.core.rest.service.impl.CustomerRestService#getAllCustomers()
     */
    @Override
    public List<CustomerJsonPojo> getAllCustomers() {
        List<Customer> customers = customerService.findCustomers();
        List<CustomerJsonPojo> customerCustomerJsonPojos = jsonFactory.toPojo(customers);
        return customerCustomerJsonPojos;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.hoteia.qalingo.core.rest.service.impl.CustomerRestService#getCustomerById(java.lang.String)
     */
    @Override
    public CustomerJsonPojo getCustomerById(@PathParam("id") final String id) {
        LOG.debug("Fetching customer with id {}", id);

        Customer customer = customerService.getCustomerById(id);
        CustomerJsonPojo customerJsonPojo = jsonFactory.toPojo(customer);
        return customerJsonPojo;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.hoteia.qalingo.core.rest.service.impl.CustomerRestService#saveOrUpdate(fr.hoteia.qalingo.core.rest.pojo.CustomerJsonPojo)
     */
    @Override
    @Transactional
    public void saveOrUpdate(final CustomerJsonPojo customerJsonPojo) throws Exception {
        Customer customer = jsonFactory.fromPojo(customerJsonPojo);
        LOG.debug("Saving customer {}", customer);
        customerService.saveOrUpdateCustomer(customer);
    }

}