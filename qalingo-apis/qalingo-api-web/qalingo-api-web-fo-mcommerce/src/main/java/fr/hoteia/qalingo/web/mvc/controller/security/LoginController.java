/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.controller.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractMCommerceFrontofficeController;

/**
 * 
 */
@Controller
public class LoginController extends AbstractMCommerceFrontofficeController {

	@RequestMapping("/login.html*")
	public ModelAndView login(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "security/login");
		
		if(getCustomer() != null){
			final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
			final Market currentMarket = requestUtil.getCurrentMarket(request);
			final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
			final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
			final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
			final String urlRedirect = urlService.buildHomeUrl(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
	        return new ModelAndView(new RedirectView(urlRedirect));
		}
		
		//  "login";
		modelAndViewFactory.initLoginModelAndView(request, modelAndView);
		
        return modelAndView;
	}
	
	@RequestMapping("/login-check.html*")
	public ModelAndView loginCheck(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView("security/login");
		
		if(getCustomer() != null){
			final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
			final Market currentMarket = requestUtil.getCurrentMarket(request);
			final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
			final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
			final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
			final String urlRedirect = urlService.buildHomeUrl(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
	        return new ModelAndView(new RedirectView(urlRedirect));
		}
		
		// "header.title.login";
		
        return modelAndView;
	}
	
}