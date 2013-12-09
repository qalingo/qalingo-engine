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
import javax.validation.Valid;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.web.mvc.viewbean.CartViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.hoteia.qalingo.web.mvc.form.PaymentForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("cartOrderPaymentController")
public class CartOrderPaymentController extends AbstractMCommerceController {

	@RequestMapping(FoUrls.CART_ORDER_PAYMENT_URL)
	public ModelAndView displayOrderPayment(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.CART_ORDER_PAYMENT.getVelocityPage());

		// SANITY CHECK
		final Cart currentCart = requestUtil.getCurrentCart(request);
		int cartItemsCount = currentCart.getCartItems().size();
		if(cartItemsCount == 0){
			return new ModelAndView(new RedirectView(urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request))));
		}
		
        final RequestData requestData = requestUtil.getRequestData(request);
        
		final CartViewBean cartViewBean = frontofficeViewBeanFactory.buildCartViewBean(requestUtil.getRequestData(request), currentCart);
		modelAndView.addObject(ModelConstants.CART_VIEW_BEAN, cartViewBean);
		
		modelAndView.addObject(ModelConstants.PAYMENT_FORM, formFactory.buildPaymentForm(requestData));
		
        return modelAndView;
	}
	
	@RequestMapping(value = FoUrls.CART_ORDER_PAYMENT_URL, method = RequestMethod.POST)
	public ModelAndView submitOrderPayment(final HttpServletRequest request, final HttpServletResponse response, @Valid PaymentForm paymentForm,
								BindingResult result, ModelMap modelMap) throws Exception {
		
		if (result.hasErrors()) {
			return displayOrderPayment(request, response);
		}
		
		// Create and Save a new order
//		webCommerceService.buildAndSaveNewOrder(request, requestUtil.getRequestData(request), currentMarket, currentMarketArea);
		
		final String urlRedirect = urlService.generateUrl(FoUrls.CART_ORDER_CONFIRMATION, requestUtil.getRequestData(request));
        return new ModelAndView(new RedirectView(urlRedirect));
	}

}