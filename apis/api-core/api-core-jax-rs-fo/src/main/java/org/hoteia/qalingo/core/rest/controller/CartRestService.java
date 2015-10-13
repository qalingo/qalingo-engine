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
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.pojo.cart.AddToCartPojoRequest;
import org.hoteia.qalingo.core.pojo.cart.AddToCartPojoResponse;
import org.hoteia.qalingo.core.pojo.cart.AddressCartPojoRequest;
import org.hoteia.qalingo.core.pojo.cart.CartPojo;
import org.hoteia.qalingo.core.pojo.cart.CommonCartPojoResponse;
import org.hoteia.qalingo.core.pojo.cart.DeliveryMethodCartPojoRequest;
import org.hoteia.qalingo.core.pojo.cart.GetCartPojoRequest;
import org.hoteia.qalingo.core.pojo.cart.PromoCodeCartPojoRequest;
import org.hoteia.qalingo.core.pojo.cart.UpdateItemQuantityCartPojoRequest;
import org.hoteia.qalingo.core.service.CartService;
import org.hoteia.qalingo.core.service.CustomerService;
import org.hoteia.qalingo.core.service.MarketService;
import org.hoteia.qalingo.core.service.pojo.CheckoutPojoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Path("/cart/")
@Component("cartRestService")
public class CartRestService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    protected MarketService marketService;
    
    @Autowired
    protected CartService cartService;
    
    @Autowired
    protected CustomerService customerService;
    
    @Autowired
    protected CheckoutPojoService checkoutPojoService;
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CommonCartPojoResponse getCart(GetCartPojoRequest getCartPojoRequest) {
        CommonCartPojoResponse commonCartPojoResponse = new CommonCartPojoResponse();
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentCustomerName = authentication.getName();
            
            if(StringUtils.isNotEmpty(currentCustomerName)){
                Customer customer = customerService.getCustomerByLoginOrEmail(currentCustomerName);

                if(StringUtils.isNotEmpty(getCartPojoRequest.getMarketAreaCode())){
                    MarketArea marketArea = marketService.getMarketAreaByCode(getCartPojoRequest.getMarketAreaCode());
                    try {
                        CartPojo cartPojo = checkoutPojoService.getCart(marketArea, customer);
                        commonCartPojoResponse.setCart(cartPojo);
                    } catch (Exception e) {
                        // TODO SEND ERREUR
                    }
                }
            } else {
                // TODO SEND ERREUR
            }
        } else {
            // TODO SEND ERREUR
        }
        return commonCartPojoResponse;
    }
    
    @POST
    @Path("items")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AddToCartPojoResponse addProductSkuToCart(AddToCartPojoRequest addToCartPojoRequest) throws Exception {
        AddToCartPojoResponse addToCartPojoResponse = new AddToCartPojoResponse();
        
        Cart cart;
        if(StringUtils.isNotEmpty(addToCartPojoRequest.getMarketAreaCode())){
            MarketArea marketArea = marketService.getMarketAreaByCode(addToCartPojoRequest.getMarketAreaCode());
            
            if(StringUtils.isNotEmpty(addToCartPojoRequest.getCartId())){
                cart = cartService.getCartById(addToCartPojoRequest.getCartId());
                String catalogCategoryCode = addToCartPojoRequest.getCatalogCategoryCode();
                String productSkuCode = addToCartPojoRequest.getProductSkuCode();
                int quantity = addToCartPojoRequest.getQuantity();

                checkoutPojoService.addProductSkuToCart(cart, marketArea.getCatalogCode(), catalogCategoryCode, productSkuCode, quantity);
                
            } else {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (!(authentication instanceof AnonymousAuthenticationToken)) {
                    String currentCustomerName = authentication.getName();
                    
                    if(StringUtils.isNotEmpty(currentCustomerName)){
                        Customer customer = customerService.getCustomerByLoginOrEmail(currentCustomerName);
                        cart = cartService.newCustomerCart(marketArea, customer);
                    } else {
                        cart = cartService.newGuestCart(marketArea);
                    }
                } else {
                    cart = cartService.newGuestCart(marketArea);
                }
                
                String catalogCategoryCode = addToCartPojoRequest.getCatalogCategoryCode();
                String productSkuCode = addToCartPojoRequest.getProductSkuCode();
                int quantity = addToCartPojoRequest.getQuantity();

                checkoutPojoService.addProductSkuToCart(cart, marketArea.getCatalog().getCode(), catalogCategoryCode, productSkuCode, quantity);
            }
            
        }
        return addToCartPojoResponse;
    }
    
    @PUT
    @Path("items")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CommonCartPojoResponse updateProductSkuQuantityToCart(UpdateItemQuantityCartPojoRequest updateItemQuantityCartPojoRequest) throws Exception {
        CommonCartPojoResponse commonCartPojoResponse = new CommonCartPojoResponse();
        
        if(StringUtils.isNotEmpty(updateItemQuantityCartPojoRequest.getCartId())){
            Cart cart = cartService.getCartById(updateItemQuantityCartPojoRequest.getCartId());
            String productSkuCode = updateItemQuantityCartPojoRequest.getProductSkuCode();
            int quantity = updateItemQuantityCartPojoRequest.getQuantity();
            checkoutPojoService.updateCartItem(cart, productSkuCode, quantity);
                    
        } else {
            // TODO SEND ERREUR
        }
        return commonCartPojoResponse;
    }
    
    @DELETE
    @Path("items")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CommonCartPojoResponse deleteProductSkuQuantityToCart(UpdateItemQuantityCartPojoRequest updateItemQuantityCartPojoRequest) throws Exception {
        CommonCartPojoResponse commonCartPojoResponse = new CommonCartPojoResponse();
        
        if(StringUtils.isNotEmpty(updateItemQuantityCartPojoRequest.getCartId())){
            Cart cart = cartService.getCartById(updateItemQuantityCartPojoRequest.getCartId());
            String productSkuCode = updateItemQuantityCartPojoRequest.getProductSkuCode();
            checkoutPojoService.deleteCartItem(cart, productSkuCode);
                    
        } else {
            // TODO SEND ERREUR
        }
        return commonCartPojoResponse;
    }
    
    @POST
    @Path("promocode")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CommonCartPojoResponse applyPromoCode(PromoCodeCartPojoRequest promoCodeCartPojoRequest) throws Exception {
        CommonCartPojoResponse commonCartPojoResponse = new CommonCartPojoResponse();
        
        if (StringUtils.isNotEmpty(promoCodeCartPojoRequest.getCartId())) {
            Cart cart = cartService.getCartById(promoCodeCartPojoRequest.getCartId());
            String promoCode = promoCodeCartPojoRequest.getPromoCode();

            // TODO MANAGE promoCode

        } else {
            // TODO SEND ERREUR
        }
        return commonCartPojoResponse;
    }

    @POST
    @Path("shipping-address")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CommonCartPojoResponse setShippingAddress(AddressCartPojoRequest addressCartPojoRequest) throws Exception {
        CommonCartPojoResponse commonCartPojoResponse = new CommonCartPojoResponse();
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentCustomerName = authentication.getName();
            
            if(StringUtils.isNotEmpty(currentCustomerName)){
                Customer customer = customerService.getCustomerByLoginOrEmail(currentCustomerName);

                if(StringUtils.isNotEmpty(addressCartPojoRequest.getCartId())){
                    Cart cart = cartService.getCartById(addressCartPojoRequest.getCartId());
                    String customerAddressId = addressCartPojoRequest.getCustomerAddressId();
                    checkoutPojoService.setShippingAddress(cart, customer, customerAddressId);
                    
                } else {
                    // TODO SEND ERREUR
                }
                
            }
        } else {
            // TODO SEND ERREUR
        }
        return commonCartPojoResponse;
    }
    
    @POST
    @Path("billing-address")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CommonCartPojoResponse setBillingAddress(AddressCartPojoRequest addressCartPojoRequest) throws Exception {
        CommonCartPojoResponse commonCartPojoResponse = new CommonCartPojoResponse();
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentCustomerName = authentication.getName();
            
            if(StringUtils.isNotEmpty(currentCustomerName)){
                Customer customer = customerService.getCustomerByLoginOrEmail(currentCustomerName);

                if(StringUtils.isNotEmpty(addressCartPojoRequest.getCartId())){
                    Cart cart = cartService.getCartById(addressCartPojoRequest.getCartId());
                    String customerAddressId = addressCartPojoRequest.getCustomerAddressId();
                    checkoutPojoService.setBillingAddress(cart, customer, customerAddressId);
                    
                } else {
                    // TODO SEND ERREUR
                }
                
            }
        } else {
            // TODO SEND ERREUR
        }
        return commonCartPojoResponse;
    }

    @POST
    @Path("delivery-method")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CommonCartPojoResponse setDeliveryMethod(DeliveryMethodCartPojoRequest deliveryMethodCartPojoRequest) throws Exception {
        CommonCartPojoResponse commonCartPojoResponse = new CommonCartPojoResponse();
        
        if(StringUtils.isNotEmpty(deliveryMethodCartPojoRequest.getCartId())){
            Cart cart = cartService.getCartById(deliveryMethodCartPojoRequest.getCartId());
            String deliveryMethodCode = deliveryMethodCartPojoRequest.getDeliveryMethodCode();
            checkoutPojoService.setDeliveryMethod(cart, deliveryMethodCode);
                    
        } else {
            // TODO SEND ERREUR
        }
        return commonCartPojoResponse;
    }
    
}