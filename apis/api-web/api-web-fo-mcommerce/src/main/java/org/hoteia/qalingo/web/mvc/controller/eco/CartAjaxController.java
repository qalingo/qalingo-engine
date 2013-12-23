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

import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.pojo.cart.CartPojo;
import org.hoteia.qalingo.core.pojo.cart.FoCheckoutPojo;
import org.hoteia.qalingo.core.pojo.cart.FoErrorPojo;
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

    @RequestMapping(value = FoUrls.GET_CART_AJAX_URL, method = RequestMethod.GET)
    @ResponseBody
    public FoCheckoutPojo getCart(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final FoCheckoutPojo checkout = new FoCheckoutPojo();
        injectCart(requestData, checkout);
        return checkout;
    }

    @RequestMapping(value = FoUrls.UPDATE_CART_ITEM_AJAX_URL, method = RequestMethod.GET)
    @ResponseBody
    public FoCheckoutPojo updateItemQuantity(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final String productSkuCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CART_ITEM_SKU_CODE);
        final String quantity = request.getParameter(RequestConstants.REQUEST_PARAMETER_CART_ITEM_SKU_QUANTITY);
        final FoCheckoutPojo checkout = new FoCheckoutPojo();
        try {
            int quantityValue = Integer.parseInt(quantity);
            webManagementService.updateCart(requestData, productSkuCode, quantityValue);
        } catch (Exception e) {
            logger.error("", e);
            FoErrorPojo error = new FoErrorPojo();
            error.setId("error-update-quantity");
            error.setMessage(e.getMessage());
            checkout.getErrors().add(error);
            checkout.setStatuts(false);
            return checkout;
        }
        injectCart(requestData, checkout);
        return checkout;
    }
    
    @RequestMapping(value = FoUrls.DELETE_CART_ITEM_AJAX_URL, method = RequestMethod.GET)
    @ResponseBody
    public FoCheckoutPojo deleteItem(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final String productSkuCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CART_ITEM_SKU_CODE);
        final FoCheckoutPojo checkout = new FoCheckoutPojo();
        try {
            webManagementService.deleteCartItem(requestData, productSkuCode);
            
            final Cart cart = requestData.getCart();
            if(cart != null
                    && cart.getTotalCartItems() == 0){
                FoErrorPojo error = new FoErrorPojo();
                error.setId("warning-empty-cart");
                error.setMessage("Your cart is empty");
                checkout.getErrors().add(error);
            }
            
        } catch (Exception e) {
            logger.error("", e);
            FoErrorPojo error = new FoErrorPojo();
            error.setId("error-delete-item");
            error.setMessage(e.getMessage());
            checkout.getErrors().add(error);
            checkout.setStatuts(false);
            return checkout;
        }
        injectCart(requestData, checkout);
        return checkout;
    }
    
    @RequestMapping(value = FoUrls.APPLY_PROMO_CODE_AJAX_URL, method = RequestMethod.GET)
    @ResponseBody
    public FoCheckoutPojo applyPromoCode(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final FoCheckoutPojo checkout = new FoCheckoutPojo();
        injectCart(requestData, checkout);
        return checkout;
    }
    
    @RequestMapping(value = FoUrls.SET_SHIPPING_ADDRESS_AJAX_URL, method = RequestMethod.GET)
    @ResponseBody
    public FoCheckoutPojo setShippingAddress(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final String shippingAddressGuid = request.getParameter(RequestConstants.REQUEST_PARAMETER_CART_SHIPPING_ADDRESS_GUID);
        final FoCheckoutPojo checkout = new FoCheckoutPojo();
        try {
//            webManagementService.deleteCartItem(requestData, productSkuCode);
        } catch (Exception e) {
            logger.error("", e);
            FoErrorPojo error = new FoErrorPojo();
            error.setId("error-set-shipping-address");
            error.setMessage(e.getMessage());
            checkout.getErrors().add(error);
            checkout.setStatuts(false);
            return checkout;
        }
        injectCart(requestData, checkout);
        return checkout;
    }
    
    @RequestMapping(value = FoUrls.SET_BILLING_ADDRESS_AJAX_URL, method = RequestMethod.GET)
    @ResponseBody
    public FoCheckoutPojo setBillingAddress(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final String billingAddressGuid = request.getParameter(RequestConstants.REQUEST_PARAMETER_CART_BILLING_ADDRESS_GUID);
        final FoCheckoutPojo checkout = new FoCheckoutPojo();
        try {
//            webManagementService.deleteCartItem(requestData, productSkuCode);
        } catch (Exception e) {
            logger.error("", e);
            FoErrorPojo error = new FoErrorPojo();
            error.setId("error-set-billing-address");
            error.setMessage(e.getMessage());
            checkout.getErrors().add(error);
            checkout.setStatuts(false);
            return checkout;
        }
        injectCart(requestData, checkout);
        return checkout;
    }
    
    @RequestMapping(value = FoUrls.SET_DELIVERY_METHOD_AJAX_URL, method = RequestMethod.GET)
    @ResponseBody
    public FoCheckoutPojo setDeliveryMethod(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final String deliveryMethodCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_DELIVERY_METHOD_CODE);
        final FoCheckoutPojo checkout = new FoCheckoutPojo();
        try {
//            webManagementService.deleteCartItem(requestData, productSkuCode);
        } catch (Exception e) {
            logger.error("", e);
            FoErrorPojo error = new FoErrorPojo();
            error.setId("error-set-delivery-method");
            error.setMessage(e.getMessage());
            checkout.getErrors().add(error);
            checkout.setStatuts(false);
            return checkout;
        }
        injectCart(requestData, checkout);
        return checkout;
    }
    
    private void injectCart(final RequestData requestData, final FoCheckoutPojo checkout){
        try {
            CartPojo cart = (CartPojo) cartPojoService.handleCartMapping(requestData.getCart());
            checkout.setCart(cart);
            
        } catch (Exception e) {
            logger.error("", e);
            FoErrorPojo error = new FoErrorPojo();
            error.setId("error-cart");
            error.setMessage(e.getMessage());
            checkout.getErrors().add(error);
            checkout.setStatuts(false);
        }
    }

}