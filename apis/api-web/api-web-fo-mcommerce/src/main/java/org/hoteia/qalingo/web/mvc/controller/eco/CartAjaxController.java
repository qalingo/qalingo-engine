/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 */
package org.hoteia.qalingo.web.mvc.controller.eco;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.*;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.exception.ProductAlreadyExistInWishlistException;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.catalog.FetchPlanGraphProduct;
import org.hoteia.qalingo.core.fetchplan.market.FetchPlanGraphMarket;
import org.hoteia.qalingo.core.i18n.enumtype.I18nKeyValueUniverse;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.FoMessagePojo;
import org.hoteia.qalingo.core.pojo.cart.*;
import org.hoteia.qalingo.core.pojo.deliverymethod.FoDeliveryMethodPojo;
import org.hoteia.qalingo.core.service.*;
import org.hoteia.qalingo.core.service.pojo.CatalogPojoService;
import org.hoteia.qalingo.core.service.pojo.CheckoutPojoService;
import org.hoteia.qalingo.core.web.resolver.RequestData;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 *
 */
@Controller("cartAjaxController")
public class CartAjaxController extends AbstractMCommerceController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected ProductService productService;

    @Autowired
    protected DeliveryMethodService deliveryMethodService;

    @Autowired
    protected CheckoutPojoService checkoutPojoService;

    @Autowired
    protected CatalogPojoService catalogPojoService;

    @Autowired
    protected RetailerService retailerService;

    @Autowired
    protected TaxService taxService;

    @Autowired
    protected CartService cartService;

    @RequestMapping(value = FoUrls.ADD_TO_WISHLIST_AJAX_URL, method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public FoAddToWishlistPojo addProductSkuToWishlist(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Localization localization = requestData.getMarketAreaLocalization();
        final Locale locale = requestData.getLocale();
        String catalogCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE);

        // SANITY CHECK
        if ("undefined".equalsIgnoreCase(catalogCategoryCode)) {
            catalogCategoryCode = null;
        }

        final String productSkuCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE);

        final FoAddToWishlistPojo addToWishlist = new FoAddToWishlistPojo();

        // INJECT PRODUCT SKU
        final ProductSku productSku = productService.getProductSkuByCode(productSkuCode, FetchPlanGraphProduct.productSkuDisplayFetchPlan());
        addToWishlist.setProductSku(catalogPojoService.buildProductSku(productSku));

        addToWishlist.getProductSku().setDefaultPackshotImage(buildDefaultAsset(requestData, productSku));

        addToWishlist.setWishListDetailsUrl(urlService.generateUrl(FoUrls.PERSONAL_WISHLIST, requestData));
        try {
            webManagementService.addProductSkuToWishlist(requestData, catalogCategoryCode, productSkuCode);

            FoMessagePojo successMessage = new FoMessagePojo();
            successMessage.setId("success-add-to-wishlist-product-sku");
            Object[] messageParams = {productSku.getI18nName(localization.getCode())};
            successMessage.setMessage(getSpecificMessage(ScopeWebMessage.WISHLIST, "add_to_wishlist_success_message", messageParams, locale));
            addToWishlist.getSuccessMessages().add(successMessage);
            return addToWishlist;

        } catch (ProductAlreadyExistInWishlistException e) {
            logger.error("", e);
            FoMessagePojo errorMessage = new FoMessagePojo();
            Object[] messageParams = {productSku.getI18nName(localization.getCode())};
            errorMessage.setMessage(getSpecificMessage(ScopeWebMessage.ERROR, ProductAlreadyExistInWishlistException.MESSAGE_KEY, messageParams, locale));
            addToWishlist.getErrorMessages().add(errorMessage);
            addToWishlist.setStatus(false);
            return addToWishlist;

        } catch (Exception e) {
            logger.error("", e);
            FoMessagePojo errorMessage = new FoMessagePojo();
            errorMessage.setId("error-add-to-wishlist-product-sku");
            Object[] messageParams = {productSku.getI18nName(localization.getCode())};
            errorMessage.setMessage(getSpecificMessage(ScopeWebMessage.WISHLIST, "add_to_wishlist_error_message", messageParams, locale));
            addToWishlist.getErrorMessages().add(errorMessage);
            addToWishlist.setStatus(false);
            return addToWishlist;
        }
    }

    @RequestMapping(value = FoUrls.GET_CART_AJAX_URL, method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public FoCheckoutPojo getCart(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final FoCheckoutPojo checkout = new FoCheckoutPojo();
        injectCart(requestData, checkout);
        return checkout;
    }

    @RequestMapping(value = FoUrls.ADD_TO_CART_AJAX_URL, method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public FoAddToCartPojo addProductSkuToCart(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Localization localization = requestData.getMarketAreaLocalization();
        final Locale locale = requestData.getLocale();
        String catalogCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE);
        final String productSkuCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE);
        final String quantity = request.getParameter(RequestConstants.REQUEST_PARAMETER_MULTIPLE_ADD_TO_CART_QUANTITY);

        // SANITY CHECK
        if ("undefined".equalsIgnoreCase(catalogCategoryCode)) {
            catalogCategoryCode = null;
        }

        final FoAddToCartPojo addToCart = new FoAddToCartPojo();

        // INJECT PRODUCT SKU
        final ProductSku productSku = productService.getProductSkuByCode(productSkuCode);
        addToCart.setProductSku(catalogPojoService.buildProductSku(productSku));

        addToCart.getProductSku().setDefaultPackshotImage(buildDefaultAsset(requestData, productSku));

        addToCart.setCheckoutShoppingCartUrl(urlService.generateUrl(FoUrls.CART_DETAILS, requestData));

        try {
            int quantityValue = 1;
            if (StringUtils.isNotEmpty(quantity)) {
                quantityValue = Integer.parseInt(quantity);
            }
            webManagementService.addToCart(requestData, catalogCategoryCode, productSkuCode, quantityValue);

            Cart cart = requestData.getCart();
            if (cart != null && cart.getCartItems() != null) {
                for (CartItem cartItem : cart.getCartItems()) {
                    if (cartItem.getProductSku().getCode().equals(productSkuCode)) {
                        addToCart.setQuantity(cartItem.getQuantity());
                    }
                }
                if (cart.getCartItems().size() == 1) {
                    addToCart.setCheckoutShoppingCartHeaderLabel(getSpecificMessage(ScopeWebMessage.COMMON, "cart_total_summary_label_one_item", locale));
                } else if (cart.getCartItems().size() > 1) {
                    Object[] cartTotalSummaryLabelParams = {cart.getCartItems().size()};
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
            Object[] messageParams = {productSku.getI18nName(localization.getCode())};
            successMessage.setMessage(getSpecificMessage(ScopeWebMessage.CHECKOUT_SHOPPING_CART, "add_to_cart_success_message", messageParams, locale));
            addToCart.getSuccessMessages().add(successMessage);
            return addToCart;

        } catch (Exception e) {
            addErrorMessage(requestData, addToCart, productSku, e);
            return addToCart;
        }
    }

    @RequestMapping(value = FoUrls.ADD_TO_CART_FROM_STORE_AJAX_URL, method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public FoAddToCartPojo addProductSkuFromStoreToCart(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Localization localization = requestData.getMarketAreaLocalization();
        final Locale locale = requestData.getLocale();
        String catalogCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE);
        final String productSkuCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE);
        final String quantity = request.getParameter(RequestConstants.REQUEST_PARAMETER_MULTIPLE_ADD_TO_CART_QUANTITY);
        final String storeCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_MULTIPLE_ADD_TO_CART_STORE_CODE);

        // SANITY CHECK
        if ("undefined".equalsIgnoreCase(catalogCategoryCode)) {
            catalogCategoryCode = null;
        }

        final FoAddToCartPojo addToCart = new FoAddToCartPojo();

        // INJECT PRODUCT SKU
        final ProductSku productSku = productService.getProductSkuByCode(productSkuCode);
        addToCart.setProductSku(catalogPojoService.buildProductSku(productSku));
        addToCart.getProductSku().setDefaultPackshotImage(buildDefaultAsset(requestData, productSku));
        addToCart.setCheckoutShoppingCartUrl(urlService.generateUrl(FoUrls.CART_DETAILS, requestData));

        try {
            int quantityValue = 1;
            if (StringUtils.isNotEmpty(quantity)) {
                quantityValue = Integer.parseInt(quantity);
            }
            Store store = retailerService.getStoreByCode(storeCode);
            webManagementService.addToCart(requestData, store, catalogCategoryCode, productSkuCode, quantityValue);

            Cart cart = requestData.getCart();
            if(cart != null && cart.getCartItems() != null){
                for (CartItem cartItem : cart.getCartItems()) {
                    if (cartItem.getProductSku().getCode().equals(productSkuCode)) {
                        addToCart.setQuantity(cartItem.getQuantity());
                    }
                }
                if (cart.getCartItems().size() == 1) {
                    addToCart.setCheckoutShoppingCartHeaderLabel(getSpecificMessage(ScopeWebMessage.COMMON, "cart_total_summary_label_one_item", locale));
                } else if (cart.getCartItems().size() > 1) {
                    Object[] cartTotalSummaryLabelParams = {cart.getCartItems().size()};
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
            Object[] messageParams = {productSku.getI18nName(localization.getCode())};
            successMessage.setMessage(getSpecificMessage(ScopeWebMessage.CHECKOUT_SHOPPING_CART, "add_to_cart_success_message", messageParams, locale));
            addToCart.getSuccessMessages().add(successMessage);
            return addToCart;

        } catch (Exception e) {
            addErrorMessage(requestData, addToCart, productSku, e);
            return addToCart;
        }
    }

    @RequestMapping(value = FoUrls.UPDATE_CART_ITEM_AJAX_URL, method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public FoCheckoutPojo updateItemQuantity(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final String productSkuCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CART_ITEM_SKU_CODE);
        final String quantity = request.getParameter(RequestConstants.REQUEST_PARAMETER_CART_ITEM_SKU_QUANTITY);
        final String storeCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_MULTIPLE_ADD_TO_CART_STORE_CODE);
        final FoCheckoutPojo checkout = new FoCheckoutPojo();
        try {
            Store store = retailerService.getStoreByCode(storeCode);
            int quantityValue = Integer.parseInt(quantity);
            webManagementService.updateCart(requestData, store, null, productSkuCode, quantityValue);

        } catch (Exception e) {
            addErrorMessage(checkout, e, "error-update-quantity", e.getMessage());
            return checkout;
        }
        injectCart(requestData, checkout);
        return checkout;
    }

    @RequestMapping(value = FoUrls.DELETE_CART_ITEM_AJAX_URL, method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public FoCheckoutPojo deleteItem(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final String productSkuCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CART_ITEM_SKU_CODE);
        final String storeCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_MULTIPLE_ADD_TO_CART_STORE_CODE);
        final FoCheckoutPojo checkout = new FoCheckoutPojo();
        try {
            Store store = retailerService.getStoreByCode(storeCode);
            webManagementService.deleteCartItem(requestData, store, productSkuCode);

            final Cart cart = requestData.getCart();
            if (cart != null && cart.getTotalCartItems() == 0) {
                FoMessagePojo errorMessage = new FoMessagePojo();
                errorMessage.setId("warning-empty-cart");
                errorMessage.setMessage("Your cart is empty");
                checkout.getErrorMessages().add(errorMessage);
            }

        } catch (Exception e) {
            addErrorMessage(checkout, e, "error-delete-item", e.getMessage());
            return checkout;
        }
        injectCart(requestData, checkout);
        return checkout;
    }

    @RequestMapping(value = FoUrls.APPLY_PROMO_CODE_AJAX_URL, method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public FoCheckoutPojo applyPromoCode(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final FoCheckoutPojo checkout = new FoCheckoutPojo();
        injectCart(requestData, checkout);
        return checkout;
    }

    @RequestMapping(value = FoUrls.SET_SHIPPING_ADDRESS_AJAX_URL, method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public FoCheckoutPojo setShippingAddress(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final String customerShippingAddressGuid = request.getParameter(RequestConstants.REQUEST_PARAMETER_CART_SHIPPING_ADDRESS_GUID);
        final FoCheckoutPojo checkout = new FoCheckoutPojo();
        try {
            webManagementService.setShippingAddress(requestData, customerShippingAddressGuid);
        } catch (Exception e) {
            addErrorMessage(checkout, e, "error-set-shipping-address", e.getMessage());
            return checkout;
        }
        injectCart(requestData, checkout);
        return checkout;
    }

    @RequestMapping(value = FoUrls.SET_BILLING_ADDRESS_AJAX_URL, method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public FoCheckoutPojo setBillingAddress(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final String customerBillingAddressGuid = request.getParameter(RequestConstants.REQUEST_PARAMETER_CART_BILLING_ADDRESS_GUID);
        final FoCheckoutPojo checkout = new FoCheckoutPojo();
        try {
            webManagementService.setBillingAddress(requestData, customerBillingAddressGuid);
        } catch (Exception e) {
            addErrorMessage(checkout, e, "error-set-billing-address", e.getMessage());
            return checkout;
        }
        injectCart(requestData, checkout);
        return checkout;
    }

    @RequestMapping(value = FoUrls.SET_DELIVERY_METHOD_AJAX_URL, method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public FoCheckoutPojo setDeliveryMethod(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final String deliveryMethodCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CART_DELIVERY_METHOD_CODE);
        final FoCheckoutPojo checkout = new FoCheckoutPojo();
        try {
            webManagementService.setDeliveryMethod(requestData, deliveryMethodCode);
        } catch (Exception e) {
            addErrorMessage(checkout, e, "error-set-delivery-method", e.getMessage());
            return checkout;
        }
        injectCart(requestData, checkout);
        return checkout;
    }

    protected void injectCart(RequestData requestData, FoCheckoutPojo checkout) {
        try {
            Cart cart = requestData.getCart();
            if (cart == null) {
                return;
            }
            List<FoDeliveryMethodPojo> deliveryMethods = buildDeliveryMethods(requestData, cart.getCurrency().getId(), cart.getDeliveryMethods());
            checkout.getDeliveryMethods().addAll(deliveryMethods);

            FoCartPojo cartPojo = buildCart(requestData, cart);
            if (cart.getDeliveryMethods() != null && cart.getDeliveryMethods().size() > 0 && deliveryMethods.size() == 1) {
                cartPojo.setDeliveryMethod(deliveryMethods.get(0));
            } else if ((cart.getDeliveryMethods() == null || cart.getDeliveryMethods().size() == 0) && deliveryMethods.size() == 1) {
                cartPojo.setDeliveryMethod(deliveryMethods.get(0));
                webManagementService.setDeliveryMethod(requestData, deliveryMethods.get(0).getCode());
            }

            checkout.setCart(cartPojo);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            FoMessagePojo errorMessage = new FoMessagePojo();
            errorMessage.setId("error-cart");
            errorMessage.setMessage(e.getMessage());
            checkout.getErrorMessages().add(errorMessage);
            checkout.setStatus(false);
        }
    }

    private FoCartPojo buildCart(RequestData requestData, Cart cart) throws Exception {
        final Long marketAreaId = requestData.getMarketArea().getId();
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        FoCartPojo cartPojo = new FoCartPojo();
        cartPojo.setBillingAddressId(cart.getBillingAddressId());
        cartPojo.setShippingAddressId(cart.getShippingAddressId());
        int totalCartItems = 0;
        Set<FoCartItemPojo> cartItemPojos = new HashSet<FoCartItemPojo>();
        for (CartItem cartItem : cart.getCartItems()) {
            FoCartItemPojo cartItemPojo = new FoCartItemPojo();
            cartItemPojo.setId(cartItem.getId());
            ProductSku productSku = cartItem.getProductSku();
            productSku = productService.getProductSkuById(productSku.getId(), new FetchPlan(productSkuFetchPlans));
            cartItemPojo.setName(productSku.getI18nName(localizationCode));
            Store store = retailerService.getStoreById(cartItem.getStoreId());
            cartItemPojo.setStoreCode(store.getCode());
            cartItemPojo.setDescription(store.getName());
            int quantity = cartItem.getQuantity();
            totalCartItems += quantity;
            cartItemPojo.setQuantity(quantity);
            cartItemPojo.setPrice(cartService.getCartItemPriceWithTaxesWithStandardCurrencySign(cartItem, marketAreaId, cart.getTaxes()));
            cartItemPojo.setSkuCode(productSku.getCode());
            cartItemPojo.setTotalPrice(cartService.getCartItemTotalPriceWithTaxesWithStandardCurrencySign(cartItem, marketAreaId, cart.getTaxes()));
            cartItemPojo.setSummaryImage(buildDefaultAsset(requestData, productSku));
            cartItemPojo.setUrl(urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestData, cartItem.getCatalogCategory(), cartItem.getProductMarketing(), productSku));
            cartItemPojos.add(cartItemPojo);
        }
        cartPojo.setCartItems(cartItemPojos);
        cartPojo.setTotalCartItems(totalCartItems);
        cartPojo.setTotalPrice(cartService.getCartTotalWithStandardCurrencySign(cart));
        String deliveryPrice;
        if (BigDecimal.ZERO.compareTo(cartService.getDeliveryMethodTotal(cart)) == 0) {
            deliveryPrice = "Gratuit";
        } else {
            deliveryPrice = cartService.getDeliveryMethodTotalWithStandardCurrencySign(cart);
        }
        cartPojo.setDeliveryPrice(deliveryPrice);
        cartPojo.setSubTotalPrice(cartService.getCartItemTotalWithTaxesWithStandardCurrencySign(cart));
        cartPojo.setTvaPrice(cartService.getTaxTotalWithStandardCurrencySign(cart));
        return cartPojo;
    }

    private List<FoDeliveryMethodPojo> buildDeliveryMethods(RequestData requestData, Long currencyId, Set<DeliveryMethod> deliveryMethodSelected) throws Exception {
        final Long marketAreaId = requestData.getMarketArea().getId();
        final List<DeliveryMethod> deliveryMethods = deliveryMethodService.findDeliveryMethodsByMarketAreaId(marketAreaId);
        List<FoDeliveryMethodPojo> deliveryMethodPojos = new ArrayList<FoDeliveryMethodPojo>(deliveryMethods.size());
        for (DeliveryMethod deliveryMethod : deliveryMethods) {
            FoDeliveryMethodPojo methodPojo = new FoDeliveryMethodPojo();
            methodPojo.setCode(deliveryMethod.getCode());
            methodPojo.setDescription(deliveryMethod.getDescription());
            String deliveryTimeType = coreMessageSource.getSpecificMessage(I18nKeyValueUniverse.FO.getPropertyKey(), ScopeWebMessage.COMMON.getPropertyKey(), deliveryMethod.getDeliveryTimeType(), requestData.getLocale());
            methodPojo.setDeliveryTime(deliveryMethod.getDeliveryTimeValue() + " " + deliveryTimeType);
            if (BigDecimal.ZERO.compareTo(deliveryMethod.getPrice(currencyId)) == 0) {
                methodPojo.setPrice("Gratuit");
            } else {
                methodPojo.setPrice(deliveryMethod.getPriceWithStandardCurrencySign(currencyId));
            }
            if (deliveryMethodSelected != null && deliveryMethodSelected.contains(deliveryMethod)) {
                methodPojo.setSelected(true);
            }
            deliveryMethodPojos.add(methodPojo);
        }
        return deliveryMethodPojos;
    }

    protected String buildDefaultAsset(final RequestData requestData, final ProductSku productSku) throws Exception {
        // TEMPORARY FIX : ASSET
        Set<Asset> assets = productSku.getAssets();
        Asset defaultAsset = null;
        if (assets != null) {
            for (Asset asset : assets) {
                if ("PACKSHOT".equalsIgnoreCase(asset.getType())
                        && asset.isDefault()) {
                    defaultAsset = asset;
                }
            }
            if (defaultAsset == null
                    && assets.iterator().hasNext()) {
                defaultAsset = assets.iterator().next();
            }
        }
        if (defaultAsset == null && productSku.getProductMarketing() != null && Hibernate.isInitialized(productSku.getProductMarketing())) {
            if (productSku.getProductMarketing().getAssets() != null && Hibernate.isInitialized(productSku.getProductMarketing().getAssets())) {
                assets = productSku.getProductMarketing().getAssets();
                for (Asset asset : assets) {
                    if ("PACKSHOT".equalsIgnoreCase(asset.getType())
                            && asset.isDefault()) {
                        defaultAsset = asset;
                    }
                }
                if (defaultAsset == null
                        && assets.iterator().hasNext()) {
                    defaultAsset = assets.iterator().next();
                }
            }
        }
        if (defaultAsset == null) {
            defaultAsset = new Asset();
            defaultAsset.setType("default");
            defaultAsset.setPath("default-product.png");
        }
        return urlService.buildAbsoluteUrl(requestData, buildAssetPath(productSku, defaultAsset));
    }

    protected String buildAssetPath(final ProductSku productSku, final Asset defaultAsset) throws Exception {
        return engineSettingService.getProductSkuImageWebPath(defaultAsset);
    }

    protected void addErrorMessage(RequestData requestData, FoAddToCartPojo addToCart, ProductSku productSku, Exception e) {
        logger.error(e.getMessage(), e);
        final Localization localization = requestData.getMarketAreaLocalization();
        final Locale locale = requestData.getLocale();
        FoMessagePojo errorMessage = new FoMessagePojo();
        errorMessage.setId("error-add-to-cart-product-sku");
        Object[] messageParams = {productSku.getI18nName(localization.getCode())};
        errorMessage.setMessage(getSpecificMessage(ScopeWebMessage.CHECKOUT_SHOPPING_CART, "add_to_cart_error_message", messageParams, locale));
        addToCart.getErrorMessages().add(errorMessage);
        addToCart.setStatus(false);
    }

    protected void addErrorMessage(FoCheckoutPojo checkout, Exception e, String id, String message) {
        logger.error(e.getMessage(), e);
        FoMessagePojo errorMessage = new FoMessagePojo();
        errorMessage.setId(id);
        errorMessage.setMessage(message);
        checkout.getErrorMessages().add(errorMessage);
        checkout.setStatus(false);
    }
}
