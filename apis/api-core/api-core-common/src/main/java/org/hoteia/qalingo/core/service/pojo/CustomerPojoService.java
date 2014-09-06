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
import java.util.Set;

import org.dozer.Mapper;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.CustomerMarketArea;
import org.hoteia.qalingo.core.domain.CustomerWishlist;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.pojo.customer.CustomerPojo;
import org.hoteia.qalingo.core.pojo.customer.CustomerWishlistPojo;
import org.hoteia.qalingo.core.pojo.util.mapper.PojoUtil;
import org.hoteia.qalingo.core.service.CustomerService;
import org.hoteia.qalingo.core.service.MarketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("customerPojoService")
@Transactional(readOnly = true)
public class CustomerPojoService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Mapper dozerBeanMapper;
    
    @Autowired 
    protected MarketService marketService;
    
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
    
    public CustomerPojo getCustomerByLoginOrEmail(final String usernameOrEmail) {
        Customer customer = customerService.getCustomerByLoginOrEmail(usernameOrEmail);
        logger.debug("Found customer {} for usernameOrEmail {}", customer, usernameOrEmail);
        return customer == null ? null : dozerBeanMapper.map(customer, CustomerPojo.class);
    }

    public CustomerPojo getCustomerByPermalink(final String permalink) {
        Customer customer = customerService.getCustomerByPermalink(permalink);
        logger.debug("Found customer {} for usernameOrEmail {}", customer, permalink);
        return customer == null ? null : dozerBeanMapper.map(customer, CustomerPojo.class);
    }
    
    @Transactional
    public void saveOrUpdate(final CustomerPojo customerJsonPojo) throws Exception {
        Customer customer = dozerBeanMapper.map(customerJsonPojo, Customer.class);
        logger.info("Saving customer {}", customer);
        customerService.saveOrUpdateCustomer(customer);
    }
    
    public List<CustomerWishlistPojo> getWishlist(final Customer customer, final MarketArea marketArea) {
        final CustomerMarketArea customerMarketArea = customer.getCurrentCustomerMarketArea(marketArea.getId());
        Set<CustomerWishlist> wishlistProducts = customerMarketArea.getWishlistProducts();
        List<CustomerWishlistPojo> wishlists = PojoUtil.mapAll(dozerBeanMapper, wishlistProducts, CustomerWishlistPojo.class);
        return wishlists;
    }

    public void addProductSkuToWishlist(MarketArea marketArea, Customer customer, String catalogCategoryCode, String productSkuCode) throws Exception {
        customerService.addProductSkuToWishlist(marketArea, customer, catalogCategoryCode, productSkuCode);
    }
    
}