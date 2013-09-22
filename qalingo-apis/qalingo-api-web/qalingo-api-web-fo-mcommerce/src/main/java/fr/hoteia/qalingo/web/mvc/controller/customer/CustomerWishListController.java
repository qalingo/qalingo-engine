/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.controller.customer;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.RequestConstants;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.enumtype.FoUrls;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.core.web.servlet.view.RedirectView;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerWishlistViewBean;

/**
 * 
 */
@Controller("customerWishListController")
public class CustomerWishListController extends AbstractCustomerController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(FoUrls.PERSONAL_WISHLIST_URL)
	public ModelAndView customerWishList(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.PERSONAL_WISHLIST.getVelocityPage());
		
		final Customer customer = requestUtil.getCurrentCustomer(request);
		final CustomerWishlistViewBean customerWishListViewBean = viewBeanFactory.buildCustomerWishlistViewBean(requestUtil.getRequestData(request), customer);
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
			LOG.error("Error with the wishlist, skuCode:" + skuCode, e);
		}
		
		final String url = urlService.generateUrl(FoUrls.PERSONAL_WISHLIST, requestUtil.getRequestData(request));
        return new ModelAndView(new RedirectView(url));
	}

}