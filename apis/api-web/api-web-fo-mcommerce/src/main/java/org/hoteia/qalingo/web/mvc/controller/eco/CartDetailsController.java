/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
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
import org.hoteia.qalingo.web.mvc.form.CartForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("cartDetailsController")
public class CartDetailsController extends AbstractMCommerceController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value = FoUrls.CART_DETAILS_URL, method = RequestMethod.GET)
	public ModelAndView displayCartDetails(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.CART_DETAILS.getVelocityPage());

        final RequestData requestData = requestUtil.getRequestData(request);
        final Cart currentCart = requestData.getCart();
        
		final CartViewBean cartViewBean = frontofficeViewBeanFactory.buildViewBeanCart(requestUtil.getRequestData(request), currentCart);
		modelAndView.addObject(ModelConstants.CART_VIEW_BEAN, cartViewBean);

	    modelAndView.addObject(ModelConstants.CHECKOUT_STEP, 1);

		modelAndView.addObject(ModelConstants.CART_FORM, formFactory.buildCartForm(requestData));

        overrideDefaultSeoPageTitleAndMainContentTitle(request, modelAndView, FoUrls.CART_DETAILS.getKey());

        return modelAndView;
	}

    @RequestMapping(value = FoUrls.CART_DETAILS_URL, method = RequestMethod.POST)
    public ModelAndView submitOrderDelivery(final HttpServletRequest request, final HttpServletResponse response, @Valid CartForm cartForm, 
                                            BindingResult result, ModelMap modelMap) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        
        // SANITY CHECK
        final Cart currentCart = requestData.getCart();
        if (currentCart != null && currentCart.getTotalCartItems() == 0) {
            String fallbackUrl = urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request));
            String lastUrl = requestUtil.getLastRequestForEmptyCartUrl(request, fallbackUrl);
            return new ModelAndView(new RedirectView(lastUrl));
        }
        
        String nextUrl = urlService.generateUrl(FoUrls.CART_DELIVERY, requestUtil.getRequestData(request));
        return new ModelAndView(new RedirectView(nextUrl));
    }
    
}