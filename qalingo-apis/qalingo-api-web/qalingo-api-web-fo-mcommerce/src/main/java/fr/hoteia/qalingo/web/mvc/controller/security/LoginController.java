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

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import fr.hoteia.qalingo.core.ModelConstants;
import fr.hoteia.qalingo.core.RequestConstants;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import fr.hoteia.qalingo.web.mvc.viewbean.SecurityViewBean;

/**
 * 
 */
@Controller("loginController")
public class LoginController extends AbstractMCommerceController {

	@RequestMapping("/login.html*")
	public ModelAndView login(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "security/login");
		
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final Locale locale = currentLocalization.getLocale();
		
		// SANITY CHECK : Param from spring-security
		String error = request.getParameter(RequestConstants.REQUEST_PARAM_AUTH_ERROR);
		if(BooleanUtils.toBoolean(error)){
			model.addAttribute(ModelConstants.AUTH_HAS_FAIL, BooleanUtils.toBoolean(error));
			model.addAttribute(ModelConstants.AUTH_ERROR_MESSAGE, getSpecificMessage(ScopeWebMessage.AUTH, "login_or_password_are_wrong", locale));
		}
		
		if(getCustomer() != null){
			final String urlRedirect = urlService.buildHomeUrl(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
	        return new ModelAndView(new RedirectView(urlRedirect));
		}
		//  "login";

		SecurityViewBean security = viewBeanFactory.buildSecurityViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
		modelAndView.addObject("security", security);
		
        return modelAndView;
	}
	
	@RequestMapping("/login-check.html*")
	public ModelAndView loginCheck(final HttpServletRequest request, final Model model) throws Exception {
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