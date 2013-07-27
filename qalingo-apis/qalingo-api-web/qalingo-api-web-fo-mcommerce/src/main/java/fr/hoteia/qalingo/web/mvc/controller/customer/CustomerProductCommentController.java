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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerProductCommentsViewBean;
import fr.hoteia.qalingo.web.service.WebCommerceService;

/**
 * 
 */
@Controller("customerProductCommentController")
public class CustomerProductCommentController extends AbstractMCommerceController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
    protected WebCommerceService webCommerceService;
	
	@RequestMapping("/customer-product-comment-list.html*")
	public ModelAndView customerPRoductComments(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "customer/customer-product-comment-list");
		
		// "customer.product.comment";
		final Customer customer = requestUtil.getCurrentCustomer(request);
		
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final CustomerProductCommentsViewBean customerProductCommentsViewBean = viewBeanFactory.buildCustomerProductCommentsViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer, customer);
		model.addAttribute("customerProductComments", customerProductCommentsViewBean);

        return modelAndView;
	}

	@RequestMapping("/remove-customer-product-comment.html*")
	public ModelAndView removeProductComment(final HttpServletRequest request, final Model model) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		
		// TODO

		final String url = urlService.buildCustomerProductCommentUrl(request, currentMarketArea);
        return new ModelAndView(new RedirectView(url));
	}
	
	@RequestMapping("/add-customer-product-comment.html*")
	public ModelAndView addProductComment(final HttpServletRequest request, final Model model) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		
		// TODO
		
		final String url = urlService.buildCustomerProductCommentUrl(request, currentMarketArea);
        return new ModelAndView(new RedirectView(url));
	}

}