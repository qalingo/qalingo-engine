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

import org.apache.commons.lang3.StringUtils;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.exception.ProductAlreadyExistInWishlistException;
import org.hoteia.qalingo.core.fetchplan.market.FetchPlanGraphMarket;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.pojo.cart.CartItemPojo;
import org.hoteia.qalingo.core.pojo.cart.CartPojo;
import org.hoteia.qalingo.core.pojo.cart.FoAddToCartPojo;
import org.hoteia.qalingo.core.pojo.cart.FoAddToWishlistPojo;
import org.hoteia.qalingo.core.pojo.cart.FoCheckoutPojo;
import org.hoteia.qalingo.core.pojo.cart.FoDeliveryMethodInformationPojo;
import org.hoteia.qalingo.core.pojo.cart.FoMessagePojo;
import org.hoteia.qalingo.core.pojo.deliverymethod.DeliveryMethodPojo;
import org.hoteia.qalingo.core.service.MarketService;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.service.pojo.CatalogPojoService;
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
    protected ProductService productService;
    
    @Autowired
    protected CheckoutPojoService checkoutPojoService;

    @Autowired
    protected CatalogPojoService catalogPojoService;
    
    @Autowired
    protected MarketService marketService;
    
    @RequestMapping(value = FoUrls.ADD_TO_WISHLIST_AJAX_URL, method = RequestMethod.GET)
    @ResponseBody
    public FoAddToWishlistPojo addProductSkuToWishlist(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Localization localization = requestData.getMarketAreaLocalization();
        final Locale locale = requestData.getLocale();
        final String productSkuCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE);
        
        final FoAddToWishlistPojo addToWishlist = new FoAddToWishlistPojo();

        // INJECT PRODUCT SKU
        final ProductSku productSku = productService.getProductSkuByCode(productSkuCode);
        addToWishlist.setProductSku(catalogPojoService.buildProductSku(productSku));

        addToWishlist.setWishListDetailsUrl(urlService.generateUrl(FoUrls.PERSONAL_WISHLIST, requestData));
        try {
            webManagementService.addProductSkuToWishlist(requestData, productSkuCode);
            
            FoMessagePojo successMessage = new FoMessagePojo();
            successMessage.setId("success-add-to-wishlist-product-sku");
            Object[] messageParams = { productSku.getI18nName(localization.getCode()) };
            successMessage.setMessage(getSpecificMessage(ScopeWebMessage.WISHLIST, "add_to_wishlist_success_message", messageParams, locale));
            addToWishlist.getSuccessMessages().add(successMessage);
            return addToWishlist;
            
        } catch (ProductAlreadyExistInWishlistException e) {
            logger.error("", e);
            FoMessagePojo errorMessage = new FoMessagePojo();
            Object[] messageParams = { productSku.getI18nName(localization.getCode()) };
            errorMessage.setMessage(getSpecificMessage(ScopeWebMessage.ERROR, ProductAlreadyExistInWishlistException.MESSAGE_KEY, messageParams, locale));
            addToWishlist.getErrorMessages().add(errorMessage);
            addToWishlist.setStatuts(false);
            return addToWishlist;
            
        } catch (Exception e) {
            logger.error("", e);
            FoMessagePojo errorMessage = new FoMessagePojo();
            errorMessage.setId("error-add-to-wishlist-product-sku");
            Object[] messageParams = { productSku.getI18nName(localization.getCode()) };
            errorMessage.setMessage(getSpecificMessage(ScopeWebMessage.WISHLIST, "add_to_wishlist_error_message", messageParams, locale));
            addToWishlist.getErrorMessages().add(errorMessage);
            addToWishlist.setStatuts(false);
            return addToWishlist;
        }
    }
    
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
        final Localization localization = requestData.getMarketAreaLocalization();
        final Locale locale = requestData.getLocale();
        final String catalogCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE);
        final String productSkuCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE);
        final String quantity = request.getParameter(RequestConstants.REQUEST_PARAMETER_MULTIPLE_ADD_TO_CART_QUANTITY);
        
        final FoAddToCartPojo addToCart = new FoAddToCartPojo();
        
        // INJECT PRODUCT SKU
        final ProductSku productSku = productService.getProductSkuByCode(productSkuCode);
        addToCart.setProductSku(catalogPojoService.buildProductSku(productSku));

        addToCart.setCheckoutShoppingCartUrl(urlService.generateUrl(FoUrls.CART_DETAILS, requestData));
        try {
            int quantityValue = 1;
            if(StringUtils.isNotEmpty(quantity)){
                quantityValue = Integer.parseInt(quantity);
            }
            webManagementService.addToCart(requestData, catalogCategoryCode, productSkuCode, quantityValue);
            
            CartPojo cart = checkoutPojoService.handleCartMapping(requestData.getCart());
            for (Iterator<CartItemPojo> iterator = cart.getCartItems().iterator(); iterator.hasNext();) {
                CartItemPojo cartItem = (CartItemPojo) iterator.next();
                if(cartItem.getProductSku().getCode().equals(productSkuCode)){
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
            
            FoMessagePojo successMessage = new FoMessagePojo();
            successMessage.setId("success-add-to-cart-product-sku");
            Object[] messageParams = { productSku.getI18nName(localization.getCode()) };
            successMessage.setMessage(getSpecificMessage(ScopeWebMessage.CHECKOUT_SHOPPING_CART, "add_to_cart_success_message", messageParams, locale));
            addToCart.getSuccessMessages().add(successMessage);
            return addToCart;
            
        } catch (Exception e) {
            logger.error("", e);
            FoMessagePojo errorMessage = new FoMessagePojo();
            errorMessage.setId("error-add-to-cart-product-sku");
            Object[] messageParams = { productSku.getI18nName(localization.getCode()) };
            errorMessage.setMessage(getSpecificMessage(ScopeWebMessage.CHECKOUT_SHOPPING_CART, "add_to_cart_error_message", messageParams, locale));
            addToCart.getErrorMessages().add(errorMessage);
            addToCart.setStatuts(false);
            return addToCart;
        }
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
            FoMessagePojo errorMessage = new FoMessagePojo();
            errorMessage.setId("error-update-quantity");
            errorMessage.setMessage(e.getMessage());
            checkout.getErrorMessages().add(errorMessage);
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
                FoMessagePojo errorMessage = new FoMessagePojo();
                errorMessage.setId("warning-empty-cart");
                errorMessage.setMessage("Your cart is empty");
                checkout.getErrorMessages().add(errorMessage);
            }
            
        } catch (Exception e) {
            logger.error("", e);
            FoMessagePojo errorMessage = new FoMessagePojo();
            errorMessage.setId("error-delete-item");
            errorMessage.setMessage(e.getMessage());
            checkout.getErrorMessages().add(errorMessage);
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
            FoMessagePojo errorMessage = new FoMessagePojo();
            errorMessage.setId("error-set-shipping-address");
            errorMessage.setMessage(e.getMessage());
            checkout.getErrorMessages().add(errorMessage);
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
            FoMessagePojo errorMessage = new FoMessagePojo();
            errorMessage.setId("error-set-billing-address");
            errorMessage.setMessage(e.getMessage());
            checkout.getErrorMessages().add(errorMessage);
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
            FoMessagePojo errorMessage = new FoMessagePojo();
            errorMessage.setId("error-set-delivery-method");
            errorMessage.setMessage(e.getMessage());
            checkout.getErrorMessages().add(errorMessage);
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

            MarketArea marketArea = marketService.getMarketAreaByCode(requestData.getMarketArea().getCode(), FetchPlanGraphMarket.specificMarketAreaFetchPlanWithCheckoutData());
            List<DeliveryMethodPojo> availableDeliveryMethods = checkoutPojoService.getAvailableDeliveryMethods(marketArea);
            
            // TODO : SPLIT availableDeliveryMethods by items which matchs : drools ? and display deliveyMethods group by items : customer will choose 2 or more deliveyMethod
            FoDeliveryMethodInformationPojo deliveryMethodInformation = new FoDeliveryMethodInformationPojo();
            deliveryMethodInformation.setAvailableDeliveryMethods(availableDeliveryMethods);
            checkout.getDeliveryMethodInformations().add(deliveryMethodInformation);

        } catch (Exception e) {
            logger.error("", e);
            FoMessagePojo errorMessage = new FoMessagePojo();
            errorMessage.setId("error-cart");
            errorMessage.setMessage(e.getMessage());
            checkout.getErrorMessages().add(errorMessage);
            checkout.setStatuts(false);
        }
    }

}