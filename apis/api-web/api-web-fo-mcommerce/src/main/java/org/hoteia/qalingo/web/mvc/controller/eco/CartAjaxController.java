/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.eco;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.pojo.cart.CartPojo;
import org.hoteia.qalingo.core.pojo.cart.CheckoutPojo;
import org.hoteia.qalingo.core.service.pojo.CartPojoService;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 */
@Controller("cartAjaxController")
public class CartAjaxController extends AbstractMCommerceController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected CartPojoService cartPojoService;

    @RequestMapping(value = "/get-cart.json", method = RequestMethod.GET)
    @ResponseBody
    public CheckoutPojo getCart(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final CheckoutPojo checkout = new CheckoutPojo();
        checkout.setStatuts(true);
        CartPojo cart = (CartPojo) cartPojoService.handleCartMapping(requestData.getCart());
        checkout.setCart(cart);
        return checkout;
    }

    @RequestMapping(value = "/update-item-quantity.json", method = RequestMethod.GET)
    @ResponseBody
    public CheckoutPojo updateItemQuantity(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final CheckoutPojo checkout = new CheckoutPojo();
        checkout.setStatuts(true);
        CartPojo cart = (CartPojo) cartPojoService.handleCartMapping(requestData.getCart());
        checkout.setCart(cart);
        return checkout;
    }
    
    @RequestMapping(value = "/delete-item.json", method = RequestMethod.GET)
    @ResponseBody
    public CheckoutPojo deleteItem(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final CheckoutPojo checkout = new CheckoutPojo();
        checkout.setStatuts(true);
        CartPojo cart = (CartPojo) cartPojoService.handleCartMapping(requestData.getCart());
        checkout.setCart(cart);
        return checkout;
    }
    
    @RequestMapping(value = "/set-shipping-address.json", method = RequestMethod.GET)
    @ResponseBody
    public CheckoutPojo setShippingAddress(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final CheckoutPojo checkout = new CheckoutPojo();
        checkout.setStatuts(true);
        CartPojo cart = (CartPojo) cartPojoService.handleCartMapping(requestData.getCart());
        checkout.setCart(cart);
        return checkout;
    }
    
    @RequestMapping(value = "/set-billing-address.json", method = RequestMethod.GET)
    @ResponseBody
    public CheckoutPojo setBillingAddress(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final CheckoutPojo checkout = new CheckoutPojo();
        checkout.setStatuts(true);
        CartPojo cart = (CartPojo) cartPojoService.handleCartMapping(requestData.getCart());
        checkout.setCart(cart);
        return checkout;
    }
    
    @RequestMapping(value = "/set-delivery-method.json", method = RequestMethod.GET)
    @ResponseBody
    public CheckoutPojo setDeliveryMethod(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final CheckoutPojo checkout = new CheckoutPojo();
        checkout.setStatuts(true);
        CartPojo cart = (CartPojo) cartPojoService.handleCartMapping(requestData.getCart());
        checkout.setCart(cart);
        return checkout;
    }

}