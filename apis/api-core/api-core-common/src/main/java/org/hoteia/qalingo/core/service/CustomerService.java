/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import java.util.Date;
import java.util.List;

import org.hoteia.qalingo.core.dao.CustomerDao;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.CustomerCredential;
import org.hoteia.qalingo.core.domain.CustomerGroup;
import org.hoteia.qalingo.core.domain.CustomerMarketArea;
import org.hoteia.qalingo.core.domain.CustomerPaymentInformation;
import org.hoteia.qalingo.core.domain.CustomerToken;
import org.hoteia.qalingo.core.domain.CustomerWishlist;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.exception.ProductAlreadyExistInWishlistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("customerService")
public class CustomerService {

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

    public Customer addProductSkuToWishlist(final MarketArea marketArea, Customer customer, final String catalogCategoryCode, final String productSkuCode) throws Exception {
        final CustomerMarketArea customerMarketArea = customer.getCurrentCustomerMarketArea(marketArea.getId());
        CustomerWishlist customerWishlist = customerMarketArea.getCustomerWishlistByProductSkuCode(productSkuCode);
        if(customerWishlist == null){
            customerWishlist = new CustomerWishlist();
            customerWishlist.setCustomerMarketAreaId(customerMarketArea.getId());
            customerWishlist.setCatalogCategoryCode(catalogCategoryCode);
            customerWishlist.setProductSkuCode(productSkuCode);
            customerWishlist.setPosition(customerMarketArea.getWishlistProducts().size() + 1);
            customerMarketArea.getWishlistProducts().add(customerWishlist);
            customer.getCustomerMarketAreas().add(customerMarketArea);
            customer = saveOrUpdateCustomer(customer);
            
        } else {
            // Wishlist for this product sku code already exist
            throw new ProductAlreadyExistInWishlistException(); 
        }
        
        return customer;
    }
    
    public Customer removeProductSkuFromWishlist(final MarketArea marketArea, Customer customer, final String productSkuCode) throws Exception {
        final CustomerMarketArea customerMarketArea = customer.getCurrentCustomerMarketArea(marketArea.getId());
        CustomerWishlist customerWishlist = customerMarketArea.getCustomerWishlistByProductSkuCode(productSkuCode);
        if(customerWishlist != null){
            customerMarketArea.getWishlistProducts().remove(customerWishlist);
            customer.getCustomerMarketAreas().add(customerMarketArea);
            customer = saveOrUpdateCustomer(customer);
        }
        
        return customer;
    }
    
    public Customer savePaymentInformation(final Customer customer, final CustomerPaymentInformation customerPaymentInformation) throws Exception {
        customerPaymentInformation.setDateCreate(new Date());
        customerPaymentInformation.setDateUpdate(new Date());
        customer.getPaymentInformations().add(customerPaymentInformation);
        return saveOrUpdateCustomer(customer);
    }
    
    public Customer saveOrUpdateCustomer(final Customer customer) throws Exception {
        return customerDao.saveOrUpdateCustomer(customer);
    }

    public CustomerMarketArea saveOrUpdateCustomerMarketArea(final CustomerMarketArea customerMarketArea) throws Exception {
        return customerDao.saveOrUpdateCustomerMarketArea(customerMarketArea);
    }
    
    public void deleteCustomer(final Customer customer) {
        customerDao.deleteCustomer(customer);
    }
    
    public CustomerGroup getCustomerGroupById(final Long customerGroupId, Object... params) {
        return customerDao.getCustomerGroupById(customerGroupId, params);
    }
    
    public CustomerGroup getCustomerGroupById(final String rawCustomerGroupId, Object... params) {
        long customerGroupId = -1;
        try {
            customerGroupId = Long.parseLong(rawCustomerGroupId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getCustomerGroupById(customerGroupId, params);
    }

    // CUSTOMER GROUP
    
    public CustomerGroup getCustomerGroupByCode(final String code, Object... params) {
        return customerDao.getCustomerGroupByCode(code, params);
    }

    public void saveOrUpdateCustomerGroup(final CustomerGroup customerGroup) {
        customerDao.saveOrUpdateCustomerGroup(customerGroup);
    }

    public void deleteCustomerGroup(final CustomerGroup customerGroup) {
        customerDao.deleteCustomerGroup(customerGroup);
    }

    // CREDENTIAL

    public CustomerCredential saveOrUpdateCustomerCredential(final CustomerCredential customerCredential) throws Exception {
        return customerDao.saveOrUpdateCustomerCredential(customerCredential);
    }

    // TOKEN

    public CustomerToken saveOrUpdateCustomerToken(final CustomerToken customerToken) throws Exception {
        return customerDao.saveOrUpdateCustomerToken(customerToken);
    }
    
}