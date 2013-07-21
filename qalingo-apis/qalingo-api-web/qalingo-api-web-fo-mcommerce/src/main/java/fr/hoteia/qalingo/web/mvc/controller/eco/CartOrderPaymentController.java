/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.controller.eco;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import fr.hoteia.qalingo.core.domain.Cart;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import fr.hoteia.qalingo.web.mvc.form.PaymentForm;
import fr.hoteia.qalingo.web.mvc.viewbean.CartViewBean;
import fr.hoteia.qalingo.web.service.WebCommerceService;

/**
 * 
 */
@Controller("cartOrderPaymentController")
public class CartOrderPaymentController extends AbstractMCommerceController {

	@Autowired
    protected WebCommerceService webCommerceService;
	
	@RequestMapping("/cart-order-payment.html*")
	public ModelAndView displayOrderPayment(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "cart/cart-order-payment");

		// SANITY CHECK
		final Cart currentCart = requestUtil.getCurrentCart(request);
		int cartItemsCount = currentCart.getCartItems().size();
		if(cartItemsCount == 0){
			final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
			final Market currentMarket = requestUtil.getCurrentMarket(request);
			final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
			final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
			final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
			return new ModelAndView(new RedirectView(urlService.buildHomeUrl(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer)));
		}
		
		// "shoppingcart.payment";

		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final CartViewBean cartViewBean = viewBeanFactory.buildCartViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer, currentCart);
		modelAndView.addObject("cart", cartViewBean);
		
		modelAndView.addObject("paymentForm", formFactory.buildPaymentForm(request));
		
        return modelAndView;
	}
	
	@RequestMapping(value = "/cart-order-payment.html*", method = RequestMethod.POST)
	public ModelAndView submitOrderPayment(final HttpServletRequest request, final HttpServletResponse response, @Valid PaymentForm paymentForm,
								BindingResult result, ModelMap modelMap) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		
		if (result.hasErrors()) {
			return displayOrderPayment(request, response);
		}
		
		// Create and Save a new order
		webCommerceService.buildAndSaveNewOrder(request, currentMarket, currentMarketArea);
		
		final String urlRedirect = urlService.buildCartOrderConfirmationUrl(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
        return new ModelAndView(new RedirectView(urlRedirect));
	}

}