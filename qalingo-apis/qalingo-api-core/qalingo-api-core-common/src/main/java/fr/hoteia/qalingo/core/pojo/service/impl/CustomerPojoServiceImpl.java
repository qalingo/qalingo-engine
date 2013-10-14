/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.pojo.service.impl;

import java.util.ArrayList;
import java.util.List;

import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.pojo.CustomerPojo;
import fr.hoteia.qalingo.core.pojo.service.CustomerPojoService;
import fr.hoteia.qalingo.core.pojo.util.mapper.PojoMapper;
import fr.hoteia.qalingo.core.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("customerPojoService")
@Transactional(readOnly = true)
public class CustomerPojoServiceImpl implements CustomerPojoService {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired private CustomerService customerService;

    @Autowired @Qualifier("customerMapper") private PojoMapper<Customer, CustomerPojo> pojoMapper;

    /*
     * (non-Javadoc)
     * 
     * @see fr.hoteia.qalingo.core.rest.service.impl.CustomerRestService#getAllCustomers()
     */
    @Override
    public List<CustomerPojo> getAllCustomers() {
        List<Customer> customers = customerService.findCustomers();
        return new ArrayList<CustomerPojo>(pojoMapper.toPojo(customers));
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.hoteia.qalingo.core.rest.service.impl.CustomerRestService#getCustomerById(java.lang.String)
     */
    @Override
    public CustomerPojo getCustomerById(final String id) {
        LOG.debug("Fetching customer with id {}", id);
        Customer customer = customerService.getCustomerById(id);
        return pojoMapper.toPojo(customer);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.hoteia.qalingo.core.rest.service.impl.CustomerRestService#saveOrUpdate(fr.hoteia.qalingo.core.rest.pojo.CustomerJsonPojo)
     */
    @Override
    @Transactional
    public void saveOrUpdate(final CustomerPojo customerJsonPojo) throws Exception {
        Customer customer = pojoMapper.fromPojo(customerJsonPojo);
        LOG.debug("Saving customer {}", customer);
        customerService.saveOrUpdateCustomer(customer);
    }

}