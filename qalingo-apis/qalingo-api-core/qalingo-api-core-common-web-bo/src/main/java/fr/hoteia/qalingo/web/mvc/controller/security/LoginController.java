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
import org.springframework.web.servlet.view.RedirectView;

import fr.hoteia.qalingo.core.ModelConstants;
import fr.hoteia.qalingo.core.RequestConstants;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.User;
import fr.hoteia.qalingo.core.domain.enumtype.BoUrls;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractBackofficeQalingoController;
import fr.hoteia.qalingo.web.mvc.viewbean.SecurityViewBean;

/**
 * 
 */
@Controller("loginController")
public class LoginController extends AbstractBackofficeQalingoController {

	@RequestMapping(BoUrls.LOGIN_URL)
	public ModelAndView login(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.LOGIN.getVelocityPage());
		
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();
		
		// SANITY CHECK: User logged
		final User currentUser = requestUtil.getCurrentUser(request);
		if(currentUser != null){
			final String url =  backofficeUrlService.generateUrl(BoUrls.USER_DETAILS, requestUtil.getRequestData(request));
			return new ModelAndView(new RedirectView(url));
		}
		
		// SANITY CHECK : Param from spring-security
		String error = request.getParameter(RequestConstants.REQUEST_PARAM_AUTH_ERROR);
		if(BooleanUtils.toBoolean(error)){
			model.addAttribute(ModelConstants.AUTH_HAS_FAIL, BooleanUtils.toBoolean(error));
			model.addAttribute(ModelConstants.AUTH_ERROR_MESSAGE, getSpecificMessage(ScopeWebMessage.AUTH, "login_or_password_are_wrong", locale));
		}
		
        return modelAndView;
	}
	
	@RequestMapping("/login-check.html*")
	public ModelAndView loginCheck(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndView modelAndView = new ModelAndView("security/login");
		
        return modelAndView;
	}
	
	@ModelAttribute(ModelConstants.SECURITY_VIEW_BEAN)
	protected SecurityViewBean initSecurityViewBean(final HttpServletRequest request, final Model model) throws Exception {
		return viewBeanFactory.buildSecurityViewBean(request, requestUtil.getRequestData(request));
	}
	
}