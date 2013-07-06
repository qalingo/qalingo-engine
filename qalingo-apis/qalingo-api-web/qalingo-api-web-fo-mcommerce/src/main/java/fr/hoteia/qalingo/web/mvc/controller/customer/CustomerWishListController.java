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
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerWishlistViewBean;
import fr.hoteia.qalingo.web.service.WebCommerceService;

/**
 * 
 */
@Controller
public class CustomerWishListController extends AbstractMCommerceController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
    protected WebCommerceService webCommerceService;
	
	@RequestMapping("/customer-wishlist.html*")
	public ModelAndView customerWishList(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "customer/customer-wishlist");
		
		// "customer.wishlist";
		final Customer customer = requestUtil.getCurrentCustomer(request);
		
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final CustomerWishlistViewBean customerWishListViewBean = viewBeanFactory.buildCustomerWishlistViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer, customer);
		modelAndView.addObject("customerWishList", customerWishListViewBean);

        return modelAndView;
	}

	@RequestMapping("/remove-from-wishlist.html*")
	public ModelAndView removeFromWishlist(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		
		final String skuCode = request.getParameter(Constants.REQUEST_PARAM_PRODUCT_SKU_CODE);
		webCommerceService.removeProductSkuFromWishlist(request, skuCode);

		final String url = urlService.buildCustomerWishlistUrl(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
        return new ModelAndView(new RedirectView(url));
	}
	
	@RequestMapping("/add-to-wishlist.html*")
	public ModelAndView AddToWishlist(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		
		final String skuCode = request.getParameter(Constants.REQUEST_PARAM_PRODUCT_SKU_CODE);
		
		try {
			webCommerceService.addProductSkuToWishlist(request, skuCode);
			
		} catch (Exception e) {
			LOG.error("Error with the wishlist, skuCode:" + skuCode, e);
		}
		
		final String url = urlService.buildCustomerWishlistUrl(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
        return new ModelAndView(new RedirectView(url));
	}

}