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
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.drools.core.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.ModelConstants;
import fr.hoteia.qalingo.core.RequestConstants;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.CustomerCredential;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.enumtype.FoUrls;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import fr.hoteia.qalingo.web.mvc.form.ForgottenPasswordForm;
import fr.hoteia.qalingo.web.mvc.form.ResetPasswordForm;
import fr.hoteia.qalingo.web.mvc.viewbean.SecurityViewBean;

/**
 * 
 */
@Controller("forgottenPasswordController")
public class ForgottentPasswordController extends AbstractMCommerceController {

	@RequestMapping(value = FoUrls.FORGOTTEN_PASSWORD_URL, method = RequestMethod.GET)
	public ModelAndView displayForgottenPassword(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.FORGOTTEN_PASSWORD.getVelocityPage());
		
		modelAndView.addObject("formForgottenPassword", new ForgottenPasswordForm());
		
        return modelAndView;
	}
	
	@RequestMapping(value = FoUrls.FORGOTTEN_PASSWORD_URL, method = RequestMethod.POST)
	public ModelAndView forgottenPassword(final HttpServletRequest request, final HttpServletResponse response, @Valid ForgottenPasswordForm forgottenPasswordForm,
			BindingResult result, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "security/forgotten-password-success");

		if (result.hasErrors()) {
			return displayForgottenPassword(request, response, modelMap);
		}
		
		final Customer customer = customerService.getCustomerByLoginOrEmail(forgottenPasswordForm.getEmailOrLogin());
		if (customer == null) {
			return displayForgottenPassword(request, response, modelMap);
		}
		
		// FLAG THE CREDENTIAL WITH A TOKEN
		CustomerCredential customerCredential = webCommerceService.flagCustomeCredentialWithToken(request, customer);
		
		webCommerceService.buildAndSaveCustomerForgottenPasswordMail(request, customer, customerCredential, forgottenPasswordForm);
		
        return modelAndView;
	}
	
	@RequestMapping(value = FoUrls.RESET_PASSWORD_URL, method = RequestMethod.GET)
	public ModelAndView resetPassword(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.RESET_PASSWORD.getVelocityPage());
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();

		String token = request.getParameter(RequestConstants.REQUEST_PARAMETER_PASSWORD_RESET_TOKEN);
		if (StringUtils.isEmpty(token)) {
			// ADD ERROR MESSAGE
			String errorMessage = getSpecificMessage(ScopeWebMessage.AUTH, "reset_password_token_is_wrong", locale);
			addErrorMessage(request, errorMessage);
		}
		
		String email = request.getParameter(RequestConstants.REQUEST_PARAMETER_PASSWORD_RESET_EMAIL);
		final Customer customer = customerService.getCustomerByLoginOrEmail(email);
		if (customer == null) {
			// ADD ERROR MESSAGE
			String errorMessage = getSpecificMessage(ScopeWebMessage.AUTH, "reset_password_login_or_email_are_wrong", locale);
			addErrorMessage(request, errorMessage);
		}
		
        return modelAndView;
	}
	
	@RequestMapping(value = FoUrls.RESET_PASSWORD_URL, method = RequestMethod.POST)
	public ModelAndView resetPassword(final HttpServletRequest request, final HttpServletResponse response, @Valid ResetPasswordForm resetPasswordForm,
			BindingResult result, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.RESET_PASSWORD.getVelocityPage());

		if (result.hasErrors()) {
			return displayForgottenPassword(request, response, modelMap);
		}
		
		final Customer customer = customerService.getCustomerByLoginOrEmail(resetPasswordForm.getEmail());
		if (customer == null) {
			// ADD ERROR
			result.rejectValue("email", "fo.auth.error_form_reset_password_email_doesnt_exist");
			return displayForgottenPassword(request, response, modelMap);
		}
		
		webCommerceService.flagCustomeCredentialWithToken(request, customer);

		webCommerceService.buildAndSaveCustomerResetPasswordConfirmationMail(request, customer);
		
        return modelAndView;
	}
	
	@ModelAttribute(ModelConstants.SECURITY_VIEW_BEAN)
	protected SecurityViewBean initSecurity(final HttpServletRequest request, final Model model) throws Exception {
		return viewBeanFactory.buildSecurityViewBean(requestUtil.getRequestData(request));
	}
	
}