/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.pojo;

import java.util.List;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.pojo.customer.CustomerPojo;
import org.hoteia.qalingo.core.pojo.util.mapper.PojoUtil;
import org.hoteia.qalingo.core.service.CustomerService;
import org.hoteia.qalingo.core.service.pojo.CustomerPojoFactory;

@Service("customerPojoService")
@Transactional(readOnly = true)
public class CustomerPojoFactory {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Mapper dozerBeanMapper;
    
    @Autowired
    private CustomerService customerService;

    public List<CustomerPojo> getAllCustomers() {
        List<Customer> customers = customerService.findCustomers();
        logger.debug("Found {} customers", customers.size());
        return PojoUtil.mapAll(dozerBeanMapper, customers, CustomerPojo.class);
    }

    public CustomerPojo getCustomerById(final String id) {
        Customer customer = customerService.getCustomerById(id);
        logger.debug("Found customer {} for id {}", customer, id);
        return customer == null ? null : dozerBeanMapper.map(customer, CustomerPojo.class);
    }

    @Transactional
    public void saveOrUpdate(final CustomerPojo customerJsonPojo) throws Exception {
        Customer customer = dozerBeanMapper.map(customerJsonPojo, Customer.class);
        logger.info("Saving customer {}", customer);
        customerService.saveOrUpdateCustomer(customer);
    }

}