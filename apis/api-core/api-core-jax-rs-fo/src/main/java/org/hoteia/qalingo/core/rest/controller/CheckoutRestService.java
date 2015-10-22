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

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.CustomerPayment;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.pojo.checkout.CheckoutProcessPojoRequest;
import org.hoteia.qalingo.core.pojo.checkout.CheckoutProcessPojoResponse;
import org.hoteia.qalingo.core.service.CartService;
import org.hoteia.qalingo.core.service.CheckoutService;
import org.hoteia.qalingo.core.service.CustomerService;
import org.hoteia.qalingo.core.service.MarketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Path("/checkout/")
@Component("checkoutRestService")
public class CheckoutRestService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    protected MarketService marketService;
    
    @Autowired
    protected CustomerService customerService;
    
    @Autowired
    protected CartService cartService;
    
    @Autowired
    protected CheckoutService checkoutService;
    
    @POST
    @Path("process-payment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CheckoutProcessPojoResponse addProductSkuToCart(CheckoutProcessPojoRequest checkoutProcessPojoRequest) throws Exception {
        CheckoutProcessPojoResponse checkoutProcessPojoResponse = new CheckoutProcessPojoResponse();
                
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentCustomerName = authentication.getName();
            
            if(StringUtils.isNotEmpty(currentCustomerName)){
                Customer customer = customerService.getCustomerByLoginOrEmail(currentCustomerName);
                MarketArea marketArea = marketService.getMarketAreaByCode(checkoutProcessPojoRequest.getMarketAreaCode());
                
                if(checkoutProcessPojoRequest.getPaymentPojo().isWantSavedPaymentInformations()){
                    // Save payment information
                    final CustomerPayment customerInformation = new CustomerPayment();
                    customerInformation.setPaymentType(checkoutProcessPojoRequest.getPaymentPojo().getPaymentType());
                    customerInformation.setCardHolderName(checkoutProcessPojoRequest.getPaymentPojo().getCardHolderName());
                    customerInformation.setCardNumber(checkoutProcessPojoRequest.getPaymentPojo().getCardNumber());
                    customerInformation.setCardExpMonth(checkoutProcessPojoRequest.getPaymentPojo().getCardExpMonth());
                    customerInformation.setCardExpYear(checkoutProcessPojoRequest.getPaymentPojo().getCardExpYear());
                    customerInformation.setCardCVV(checkoutProcessPojoRequest.getPaymentPojo().getCardCVV());
                    
                    customerInformation.setMarketAreaId(marketArea.getId());
                    
                    customerService.savePaymentInformation(customer, customerInformation);
                }

                Cart cart = cartService.getCartByMarketAreaIdAndCustomerId(marketArea.getId(), customer.getId());
                
                // Create and Save a new order
                checkoutService.checkout(customer, cart);
                
                // WebManagementService.buildAndSaveNewOrderConfirmationMail

            }
        }
        
        return checkoutProcessPojoResponse;
    }
    
}