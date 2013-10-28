/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.service.pojo.impl;

import static fr.hoteia.qalingo.core.pojo.util.mapper.PojoUtil.mapAll;

import java.util.List;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.pojo.customer.CustomerPojo;
import fr.hoteia.qalingo.core.service.CustomerService;
import fr.hoteia.qalingo.core.service.pojo.CustomerPojoService;

@Service("customerPojoService")
@Transactional(readOnly = true)
public class CustomerPojoServiceImpl implements CustomerPojoService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DozerBeanMapper mapper;
    
    @Autowired
    private CustomerService customerService;

    @Override
    public List<CustomerPojo> getAllCustomers() {
        List<Customer> customers = customerService.findCustomers();
        logger.debug("Found {} customers", customers.size());
        return mapAll(mapper, customers, CustomerPojo.class);
    }

    @Override
    public CustomerPojo getCustomerById(final String id) {
        Customer customer = customerService.getCustomerById(id);
        logger.debug("Found customer {} for id {}", customer, id);
        return customer == null ? null : mapper.map(customer, CustomerPojo.class);
    }

    @Override
    @Transactional
    public void saveOrUpdate(final CustomerPojo customerJsonPojo) throws Exception {
        Customer customer = mapper.map(customerJsonPojo, Customer.class);
        logger.info("Saving customer {}", customer);
        customerService.saveOrUpdateCustomer(customer);
    }

}