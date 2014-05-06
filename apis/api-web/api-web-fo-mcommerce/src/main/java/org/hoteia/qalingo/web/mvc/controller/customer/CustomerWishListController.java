/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
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

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
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
        final RequestData requestData = requestUtil.getRequestData(request);

		final Customer customer = requestData.getCustomer();
		
		// WE RELOAD THE CUSTOMER FOR THE PERSISTANCE PROXY FILTER 
		// IT AVOIDS LazyInitializationException: could not initialize proxy - no Session
		final Customer reloadedCustomer = customerService.getCustomerByCode(customer.getCode());
		
		final CustomerWishlistViewBean customerWishListViewBean = frontofficeViewBeanFactory.buildViewBeanCustomerWishlist(requestUtil.getRequestData(request), reloadedCustomer);
		model.addAttribute(ModelConstants.CUSTOMER_WISHLIST_VIEW_BEAN, customerWishListViewBean);

        return modelAndView;
	}

	@RequestMapping(FoUrls.WISHLIST_REMOVE_ITEM_URL)
	public ModelAndView removeFromWishlist(final HttpServletRequest request, final Model model) throws Exception {
		final String skuCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE);
		final RequestData requestData = requestUtil.getRequestData(request);
		webManagementService.removeProductSkuFromWishlist(requestData, skuCode);

		final String url = urlService.generateUrl(FoUrls.PERSONAL_WISHLIST, requestData);
        return new ModelAndView(new RedirectView(url));
	}
	
	@RequestMapping(FoUrls.WISHLIST_ADD_PRODUCT_URL)
	public ModelAndView AddToWishlist(final HttpServletRequest request, final Model model) throws Exception {
		final String skuCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE);
		final RequestData requestData = requestUtil.getRequestData(request);
		try {
			webManagementService.addProductSkuToWishlist(requestData, skuCode);
			
		} catch (Exception e) {
			logger.error("Error with the wishlist, skuCode:" + skuCode, e);
		}
		
		final String url = urlService.generateUrl(FoUrls.PERSONAL_WISHLIST, requestData);
        return new ModelAndView(new RedirectView(url));
	}

}