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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.ModelConstants;
import fr.hoteia.qalingo.core.RequestConstants;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.enumtype.FoUrls;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.core.web.servlet.view.RedirectView;
import fr.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import fr.hoteia.qalingo.web.mvc.viewbean.SecurityViewBean;

/**
 * 
 */
@Controller("loginController")
public class LoginController extends AbstractMCommerceController {

	@RequestMapping(FoUrls.LOGIN_URL)
	public ModelAndView login(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.LOGIN.getVelocityPage());
		
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();

		// SANITY CHECK: Customer logged
		final Customer currentCustomer = requestUtil.getCurrentCustomer(request);
		if(currentCustomer != null){
			final String url = urlService.generateUrl(FoUrls.PERSONAL_DETAILS, requestUtil.getRequestData(request));
			return new ModelAndView(new RedirectView(url));
		}
		
		// SANITY CHECK : Param from spring-security
		String error = request.getParameter(RequestConstants.REQUEST_PARAMETER_AUTH_ERROR);
		if(BooleanUtils.toBoolean(error)){
			model.addAttribute(ModelConstants.AUTH_HAS_FAIL, BooleanUtils.toBoolean(error));
			model.addAttribute(ModelConstants.AUTH_ERROR_MESSAGE, getSpecificMessage(ScopeWebMessage.AUTH, "login_or_password_are_wrong", locale));
		}
		
        return modelAndView;
	}
	
	@RequestMapping(FoUrls.LOGIN_CHECK_URL)
	public ModelAndView loginCheck(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndView modelAndView = new ModelAndView(FoUrls.LOGIN.getVelocityPage());
		
		final Customer currentCustomer = requestUtil.getCurrentCustomer(request);
		if(currentCustomer != null){
			final String urlRedirect = urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request));
	        return new ModelAndView(new RedirectView(urlRedirect));
		}
		
        return modelAndView;
	}
	
	@ModelAttribute(ModelConstants.SECURITY_VIEW_BEAN)
	protected SecurityViewBean initSecurityViewBean(final HttpServletRequest request, final Model model) throws Exception {
		return viewBeanFactory.buildSecurityViewBean(requestUtil.getRequestData(request));
	}
	
}