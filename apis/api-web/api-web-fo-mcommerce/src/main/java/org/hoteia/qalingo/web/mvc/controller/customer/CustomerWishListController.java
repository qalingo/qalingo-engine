/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.customer;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerWishlistViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;

/**
 * 
 */
@Controller("customerWishListController")
public class CustomerWishListController extends AbstractCustomerController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(FoUrls.PERSONAL_WISHLIST_URL)
	public ModelAndView customerWishList(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.PERSONAL_WISHLIST.getVelocityPage());
		
		final Customer currentCustomer = requestUtil.getCurrentCustomer(request);
		
		// WE RELOAD THE CUSTOMER FOR THE PERSISTANCE PROXY FILTER 
		// IT AVOIDS LazyInitializationException: could not initialize proxy - no Session
		final Customer reloadedCustomer = customerService.getCustomerByLoginOrEmail(currentCustomer.getLogin());
		
		final CustomerWishlistViewBean customerWishListViewBean = viewBeanFactory.buildCustomerWishlistViewBean(requestUtil.getRequestData(request), reloadedCustomer);
		model.addAttribute("customerWishList", customerWishListViewBean);

        return modelAndView;
	}

	@RequestMapping(FoUrls.WISHLIST_REMOVE_ITEM_URL)
	public ModelAndView removeFromWishlist(final HttpServletRequest request, final Model model) throws Exception {
		final String skuCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE);
		webCommerceService.removeProductSkuFromWishlist(request, requestUtil.getRequestData(request), skuCode);

		final String url = urlService.generateUrl(FoUrls.PERSONAL_WISHLIST, requestUtil.getRequestData(request));
        return new ModelAndView(new RedirectView(url));
	}
	
	@RequestMapping(FoUrls.WISHLIST_ADD_PRODUCT_URL)
	public ModelAndView AddToWishlist(final HttpServletRequest request, final Model model) throws Exception {
		final String skuCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE);
		
		try {
			webCommerceService.addProductSkuToWishlist(request, requestUtil.getRequestData(request), skuCode);
			
		} catch (Exception e) {
			logger.error("Error with the wishlist, skuCode:" + skuCode, e);
		}
		
		final String url = urlService.generateUrl(FoUrls.PERSONAL_WISHLIST, requestUtil.getRequestData(request));
        return new ModelAndView(new RedirectView(url));
	}

}