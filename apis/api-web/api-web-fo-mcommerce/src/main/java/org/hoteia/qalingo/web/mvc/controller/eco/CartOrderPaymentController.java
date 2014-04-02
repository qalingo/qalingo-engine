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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.AbstractPaymentGateway;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.web.mvc.viewbean.CartViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.PaymentMethodViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.hoteia.qalingo.web.mvc.form.PaymentForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("cartOrderPaymentController")
public class CartOrderPaymentController extends AbstractMCommerceController {

	@RequestMapping(value = FoUrls.CART_ORDER_PAYMENT_URL, method = RequestMethod.GET)
	public ModelAndView displayOrderPayment(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.CART_ORDER_PAYMENT.getVelocityPage());

        final RequestData requestData = requestUtil.getRequestData(request);

        // SANITY CHECK: EMPTY CART
		final Cart currentCart = requestData.getCart();
		if(currentCart != null && currentCart.getTotalCartItems() == 0){
			return new ModelAndView(new RedirectView(urlService.generateUrl(FoUrls.CART_DETAILS, requestUtil.getRequestData(request))));
		}

        // SANITY CHECK: DELIVERY METHODS
        if(currentCart != null && currentCart.getDeliveryMethods().isEmpty()){
            return new ModelAndView(new RedirectView(urlService.generateUrl(FoUrls.CART_DELIVERY, requestUtil.getRequestData(request))));
        }

        // SANITY CHECK: ADDRESSES
        if(currentCart != null && (currentCart.getBillingAddressId() == null || currentCart.getShippingAddressId() == null)){
            return new ModelAndView(new RedirectView(urlService.generateUrl(FoUrls.CART_DELIVERY, requestUtil.getRequestData(request))));
        }

		final CartViewBean cartViewBean = frontofficeViewBeanFactory.buildViewBeanCart(requestUtil.getRequestData(request), currentCart);
		// HIDE PROMO CODE PART
		cartViewBean.setWithPromoCode(false);
        cartViewBean.setWithItemQuantityActions(false);

		modelAndView.addObject(ModelConstants.CART_VIEW_BEAN, cartViewBean);
		
		modelAndView.addObject(ModelConstants.CHECKOUT_STEP, 4);
		
        modelAndView.addObject(ModelConstants.PAYMENT_FORM, formFactory.buildPaymentForm(requestData));
		modelAndView.addObject(ModelConstants.PAYMENT_FORM, formFactory.buildPaymentForm(requestData));
		
        overrideDefaultSeoPageTitleAndMainContentTitle(request, modelAndView, FoUrls.CART_ORDER_PAYMENT.getKey());

        return modelAndView;
	}
	
	@RequestMapping(value = FoUrls.CART_ORDER_PAYMENT_URL, method = RequestMethod.POST)
	public ModelAndView submitOrderPayment(final HttpServletRequest request, final HttpServletResponse response, @Valid PaymentForm paymentForm,
								BindingResult result, ModelMap modelMap) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea marketArea = requestData.getMarketArea();
        
	       // SANITY CHECK
        final Cart currentCart = requestData.getCart();
        if(currentCart.getTotalCartItems() == 0){
            return new ModelAndView(new RedirectView(urlService.generateUrl(FoUrls.CART_DETAILS, requestUtil.getRequestData(request))));
        }
        
		if (result.hasErrors()) {
			return displayOrderPayment(request, response);
		}
		
		if(marketArea.withSavedPaymentInformation()
		        && paymentForm.isWantSavedPaymentInformations()){
	        // Create and Save a new order
	        webManagementService.savePaymentInformation(requestUtil.getRequestData(request), paymentForm);
		}
		
		// Create and Save a new order
		webManagementService.buildAndSaveNewOrder(requestUtil.getRequestData(request));
		
		final String urlRedirect = urlService.generateUrl(FoUrls.CART_ORDER_CONFIRMATION, requestUtil.getRequestData(request));
        return new ModelAndView(new RedirectView(urlRedirect));
	}
	
    @ModelAttribute(ModelConstants.PAYMENT_METHODS_VIEW_BEAN)
    public List<PaymentMethodViewBean> getPaymentMethods(HttpServletRequest request) {
        List<PaymentMethodViewBean> paymentMethodViewBeans = new ArrayList<PaymentMethodViewBean>();
        try {
            final RequestData requestData = requestUtil.getRequestData(request);
            final MarketArea marketArea = requestData.getMarketArea();
            final Set<AbstractPaymentGateway> paymentGateways = marketArea.getPaymentGateways();
            for (Iterator<AbstractPaymentGateway> iterator = paymentGateways.iterator(); iterator.hasNext();) {
                final AbstractPaymentGateway paymentGateway = (AbstractPaymentGateway) iterator.next();
                paymentMethodViewBeans.add(frontofficeViewBeanFactory.buildViewBeanPaymentMethod(requestData, paymentGateway));
            }

            Collections.sort(paymentMethodViewBeans, new Comparator<PaymentMethodViewBean>() {
                @Override
                public int compare(PaymentMethodViewBean o1, PaymentMethodViewBean o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        } catch (Exception e) {
            logger.error("", e);
        }
        return paymentMethodViewBeans;
    }

}