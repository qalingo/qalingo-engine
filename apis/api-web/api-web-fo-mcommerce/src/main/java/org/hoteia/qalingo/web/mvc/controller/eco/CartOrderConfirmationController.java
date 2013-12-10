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
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.web.mvc.viewbean.OrderViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("cartOrderConfirmationController")
public class CartOrderConfirmationController extends AbstractMCommerceController {

	@RequestMapping(FoUrls.CART_ORDER_CONFIRMATION_URL)
	public ModelAndView home(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.CART_ORDER_CONFIRMATION.getVelocityPage());

		// SANITY CHECK
//		final Order lastOrder = requestUtil.getLastOrder(request);;
//		if(lastOrder == null){
//			return new ModelAndView(new RedirectView(urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request))));
//		}
		
		final OrderViewBean orderViewBean = frontofficeViewBeanFactory.buildOrderViewBean(requestUtil.getRequestData(request), null);
		modelAndView.addObject(ModelConstants.ORDER_VIEW_BEAN, orderViewBean);
		
        return modelAndView;
	}
	
}