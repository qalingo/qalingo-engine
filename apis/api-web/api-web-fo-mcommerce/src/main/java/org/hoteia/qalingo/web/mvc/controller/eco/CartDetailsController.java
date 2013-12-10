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

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.web.mvc.viewbean.CartViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("cartDetailsController")
public class CartDetailsController extends AbstractMCommerceController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(FoUrls.CART_ADD_PRODUCT_URL)
	public ModelAndView addToCart(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final String skuCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE);
        final RequestData requestData = requestUtil.getRequestData(request);
		try {
			requestUtil.updateCurrentCart(requestData, skuCode, 1);
			
		} catch (Exception e) {
			logger.error("Error to add product sku to cart, skuCode:" + skuCode, e);
		}
		
		final String url = urlService.generateUrl(FoUrls.CART_DETAILS, requestUtil.getRequestData(request));
		
        return new ModelAndView(new RedirectView(url));
	}

	@RequestMapping(FoUrls.CART_REMOVE_ITEM_URL)
	public ModelAndView removeFromCart(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final String skuCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE);
		requestUtil.removeCartItemFromCurrentCart(request, skuCode);

		return new ModelAndView(new RedirectView(urlService.generateUrl(FoUrls.CART_DETAILS, requestUtil.getRequestData(request))));
	}
	
	@RequestMapping(FoUrls.CART_DETAILS_URL)
	public ModelAndView displayCartDetails(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.CART_DETAILS.getVelocityPage());

		// SANITY CHECK: Empty cart
		final Cart currentCart = requestUtil.getCurrentCart(request);
		int cartItemsCount = currentCart.getCartItems().size();
		if(cartItemsCount == 0){
			return new ModelAndView(new RedirectView(urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request))));
		}

        final RequestData requestData = requestUtil.getRequestData(request);
        
		final CartViewBean cartViewBean = frontofficeViewBeanFactory.buildCartViewBean(requestUtil.getRequestData(request), currentCart);
		modelAndView.addObject(ModelConstants.CART_VIEW_BEAN, cartViewBean);
		
		modelAndView.addObject(ModelConstants.CART_FORM, formFactory.buildCartForm(requestData));

        return modelAndView;
	}

}