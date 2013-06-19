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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.domain.Cart;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractMCommerceFrontofficeController;
import fr.hoteia.qalingo.web.mvc.viewbean.CartViewBean;

/**
 * 
 */
@Controller
public class CartDetailsController extends AbstractMCommerceFrontofficeController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/add-to-cart.html*")
	public ModelAndView addToCart(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final String skuCode = request.getParameter(Constants.REQUEST_PARAM_PRODUCT_SKU_CODE);
		try {
			requestUtil.updateCurrentCart(request, skuCode, 1);
			
		} catch (Exception e) {
			LOG.error("Error to add product sku to cart, skuCode:" + skuCode, e);
		}
		
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final String url = urlService.buildCartDetailsUrl(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
		
        return new ModelAndView(new RedirectView(url));
	}

	@RequestMapping("/remove-from-cart.html*")
	public ModelAndView removeFromCart(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		
		final String skuCode = request.getParameter(Constants.REQUEST_PARAM_PRODUCT_SKU_CODE);
		requestUtil.removeCartItemFromCurrentCart(request, skuCode);

		return new ModelAndView(new RedirectView(urlService.buildCartDetailsUrl(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer)));
	}
	
	@RequestMapping("/cart-details.html*")
	public ModelAndView cartDetails(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "cart/cart-details");

		// SANITY CHECK: Empty cart
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
		
		// "shoppingcart.details";

		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final CartViewBean cartViewBean = viewBeanFactory.buildCartViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer, currentCart);
		modelAndView.addObject("cart", cartViewBean);
		
		formFactory.buildCartForm(request, modelAndView);

        return modelAndView;
	}

}