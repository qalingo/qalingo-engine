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

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.fetchplan.market.FetchPlanGraphMarket;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.pojo.cart.CartItemPojo;
import org.hoteia.qalingo.core.pojo.cart.CartPojo;
import org.hoteia.qalingo.core.pojo.cart.FoAddToCartPojo;
import org.hoteia.qalingo.core.pojo.cart.FoCheckoutPojo;
import org.hoteia.qalingo.core.pojo.cart.FoDeliveryMethodInformationPojo;
import org.hoteia.qalingo.core.pojo.cart.FoErrorPojo;
import org.hoteia.qalingo.core.pojo.deliverymethod.DeliveryMethodPojo;
import org.hoteia.qalingo.core.service.MarketService;
import org.hoteia.qalingo.core.service.pojo.CheckoutPojoService;
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
    protected CheckoutPojoService checkoutPojoService;

    @Autowired
    protected MarketService marketService;
    
    @RequestMapping(value = FoUrls.GET_CART_AJAX_URL, method = RequestMethod.GET)
    @ResponseBody
    public FoCheckoutPojo getCart(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final FoCheckoutPojo checkout = new FoCheckoutPojo();
        injectCart(requestData, checkout);
        return checkout;
    }

    @RequestMapping(value = FoUrls.ADD_TO_CART_AJAX_URL, method = RequestMethod.GET)
    @ResponseBody
    public FoAddToCartPojo addProductSkuToCart(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();
        final String catalogCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE);
        final String productSkuCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CART_ITEM_SKU_CODE);
        final String quantity = request.getParameter(RequestConstants.REQUEST_PARAMETER_CART_ITEM_SKU_QUANTITY);
        final FoAddToCartPojo addToCart = new FoAddToCartPojo();
        addToCart.setCheckoutShoppingCartUrl(urlService.generateUrl(FoUrls.CART_DETAILS, requestData));
        try {
            int quantityValue = Integer.parseInt(quantity);
            webManagementService.addToCart(requestData, catalogCategoryCode, productSkuCode, quantityValue);
            
            CartPojo cart = checkoutPojoService.handleCartMapping(requestData.getCart());
            for (Iterator<CartItemPojo> iterator = cart.getCartItems().iterator(); iterator.hasNext();) {
                CartItemPojo cartItem = (CartItemPojo) iterator.next();
                if(cartItem.getProductSku().getCode().equals(productSkuCode)){
                    addToCart.setProductSku(cartItem.getProductSku());
                    addToCart.setQuantity(cartItem.getQuantity());
                }
            }
            
            if(cart != null && cart.getCartItems() != null){
                if (cart.getCartItems().size() == 1) {
                    addToCart.setCheckoutShoppingCartHeaderLabel(getSpecificMessage(ScopeWebMessage.COMMON, "cart_total_summary_label_one_item", locale));
                } else if (cart.getCartItems().size() > 1) {
                    Object[] cartTotalSummaryLabelParams = { cart.getCartItems().size() };
                    addToCart.setCheckoutShoppingCartHeaderLabel(getSpecificMessage(ScopeWebMessage.COMMON, "cart_total_summary_label_many_items", cartTotalSummaryLabelParams, locale));
                } else {
                    addToCart.setCheckoutShoppingCartHeaderLabel(getSpecificMessage(ScopeWebMessage.COMMON, "cart_total_summary_label_no_item", locale));
                }
                
            } else {
                addToCart.setCheckoutShoppingCartHeaderLabel(getSpecificMessage(ScopeWebMessage.COMMON, "cart_total_summary_label_no_item", locale));
            }
            
            addToCart.setCheckoutShoppingCartUrl(urlService.generateUrl(FoUrls.CART_DETAILS, requestData));
            
        } catch (Exception e) {
            logger.error("", e);
            FoErrorPojo error = new FoErrorPojo();
            error.setId("error-add-to-cart-product-sku");
            error.setMessage(e.getMessage());
            addToCart.getErrors().add(error);
            addToCart.setStatuts(false);
            return addToCart;
        }
        return addToCart;
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
        final String deliveryMethodCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CART_DELIVERY_METHOD_CODE);
        final FoCheckoutPojo checkout = new FoCheckoutPojo();
        try {
            final MarketArea marketArea = requestData.getMarketArea();
            final Cart cart = requestData.getCart();
            if(cart.getDeliveryMethods().isEmpty()){
                cart.getDeliveryMethods().add(marketArea.getDeliveryMethod(deliveryMethodCode));
            } else {
                cart.getDeliveryMethods().clear();
                cart.getDeliveryMethods().add(marketArea.getDeliveryMethod(deliveryMethodCode));
            }
            requestUtil.updateCurrentCart(request, cart);
            
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
            CartPojo cart = checkoutPojoService.handleCartMapping(requestData.getCart());
            checkout.setCart(cart);

            MarketArea marketArea = marketService.getMarketAreaByCode(requestData.getMarketArea().getCode(), FetchPlanGraphMarket.getSpecificMarketAreaFetchPlanWithCheckoutData());
            List<DeliveryMethodPojo> availableDeliveryMethods = checkoutPojoService.getAvailableDeliveryMethods(marketArea);
            
            // TODO : SPLIT availableDeliveryMethods by items which matchs : drools ? and display deliveyMethods group by items : customer will choose 2 or more deliveyMethod
            FoDeliveryMethodInformationPojo deliveryMethodInformation = new FoDeliveryMethodInformationPojo();
            deliveryMethodInformation.setAvailableDeliveryMethods(availableDeliveryMethods);
            checkout.getDeliveryMethodInformations().add(deliveryMethodInformation);

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