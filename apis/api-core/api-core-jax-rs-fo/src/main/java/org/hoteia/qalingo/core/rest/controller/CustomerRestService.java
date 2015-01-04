/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.rest.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.pojo.customer.AddToWishlistPojoRequest;
import org.hoteia.qalingo.core.pojo.customer.AddToWishlistPojoResponse;
import org.hoteia.qalingo.core.pojo.customer.CustomerPojo;
import org.hoteia.qalingo.core.pojo.customer.CustomerPojoResponse;
import org.hoteia.qalingo.core.pojo.customer.CustomerWishlistPojo;
import org.hoteia.qalingo.core.pojo.customer.WishlistPojoRequest;
import org.hoteia.qalingo.core.pojo.customer.WishlistPojoResponse;
import org.hoteia.qalingo.core.service.CustomerService;
import org.hoteia.qalingo.core.service.MarketService;
import org.hoteia.qalingo.core.service.pojo.CustomerPojoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Path("/customer/")
@Component("customerRestService")
public class CustomerRestService {

    @Autowired
    protected MarketService marketService;
    
    @Autowired
    protected CustomerService customerService;
    
    @Autowired
    protected CustomerPojoService customerPojoService;

    @GET
    @Path("account")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerPojoResponse getCustomer() {
        CustomerPojoResponse customerPojoResponse = new CustomerPojoResponse();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentCustomerName = authentication.getName();
            
            if(StringUtils.isNotEmpty(currentCustomerName)){
                CustomerPojo customerPojo = customerPojoService.getCustomerByLoginOrEmail(currentCustomerName);
                customerPojoResponse.setCustomerPojo(customerPojo);
            }
        }
        
        // TODO : ERROR
        
        return customerPojoResponse;
    }
    
    @GET
    @Path("permalink/{permalink}")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerPojoResponse getCustomerByPermalink(@PathParam("permalink") final String permalink) {
        CustomerPojoResponse customerPojoResponse = new CustomerPojoResponse();
        CustomerPojo customerPojo = customerPojoService.getCustomerByPermalink(permalink);
        customerPojoResponse.setCustomerPojo(customerPojo);
        return customerPojoResponse;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(final CustomerPojo customerJsonPojo) throws Exception {
        customerPojoService.saveOrUpdate(customerJsonPojo);
    }
    
    @PUT
    @Path("account")    
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveOrUpdate(final CustomerPojo customerJsonPojo) throws Exception {
        customerPojoService.saveOrUpdate(customerJsonPojo);
    }
    
    @GET
    @Path("wishlist")
    @Produces(MediaType.APPLICATION_JSON)
    public WishlistPojoResponse getWishlist(final WishlistPojoRequest wishlistPojoRequest) {
        WishlistPojoResponse wishlistPojoResponse = new WishlistPojoResponse();
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentCustomerName = authentication.getName();
            
            if(StringUtils.isNotEmpty(currentCustomerName)){
                if(StringUtils.isNotEmpty(wishlistPojoRequest.getMarketAreaCode())){
                    Customer customer = customerService.getCustomerByLoginOrEmail(currentCustomerName);
                    MarketArea marketArea = marketService.getMarketAreaByCode(wishlistPojoRequest.getMarketAreaCode());
                    List<CustomerWishlistPojo> wishlists = customerPojoService.getWishlist(customer, marketArea);
                    wishlistPojoResponse.setWishlists(wishlists);
                    return wishlistPojoResponse;
                    
                } else {
                    // SEND ERREUR
                }
            }
        } else {
            // SEND ERREUR
        }
        return wishlistPojoResponse;
    }
    
    @PUT
    @Path("wishlist")
    @Consumes(MediaType.APPLICATION_JSON)
    public AddToWishlistPojoResponse addProductSkuToWishlist(final AddToWishlistPojoRequest addToWishlistPojoRequest) throws Exception {
        AddToWishlistPojoResponse addToWishlistPojoResponse = new AddToWishlistPojoResponse();
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentCustomerName = authentication.getName();
            
            if(StringUtils.isNotEmpty(currentCustomerName)){
                Customer customer = customerService.getCustomerByLoginOrEmail(currentCustomerName);
                
                if(StringUtils.isNotEmpty(addToWishlistPojoRequest.getMarketAreaCode())){
                    MarketArea marketArea = marketService.getMarketAreaByCode(addToWishlistPojoRequest.getMarketAreaCode());
                    String catalogCategoryCode = addToWishlistPojoRequest.getCatalogCategoryCode();
                    String productSkuCode = addToWishlistPojoRequest.getProductSkuCode();
                    customerPojoService.addProductSkuToWishlist(marketArea, customer, catalogCategoryCode, productSkuCode);
                    
                } else {
                    // SEND ERREUR
                }
                
            }
        } else {
            // SEND ERREUR
        }
        
        return addToWishlistPojoResponse;
    }

}