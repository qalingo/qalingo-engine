/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.security;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
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

import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.CustomerCredential;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.web.mvc.viewbean.SecurityViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.hoteia.qalingo.web.mvc.form.ForgottenPasswordForm;
import org.hoteia.qalingo.web.mvc.form.ResetPasswordForm;

/**
 * 
 */
@Controller("forgottenPasswordController")
public class ForgottentPasswordController extends AbstractMCommerceController {

	@RequestMapping(value = FoUrls.FORGOTTEN_PASSWORD_URL, method = RequestMethod.GET)
	public ModelAndView displayForgottenPassword(final HttpServletRequest request, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.FORGOTTEN_PASSWORD.getVelocityPage());
		
		modelAndView.addObject("formForgottenPassword", new ForgottenPasswordForm());
		
        return modelAndView;
	}
	
	@RequestMapping(value = FoUrls.FORGOTTEN_PASSWORD_URL, method = RequestMethod.POST)
	public ModelAndView forgottenPassword(final HttpServletRequest request, @Valid @ModelAttribute("forgottenPasswordForm") ForgottenPasswordForm forgottenPasswordForm,
			BindingResult result, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.FORGOTTEN_PASSWORD_SUCCESS_VELOCITY_PAGE);

		if (result.hasErrors()) {
			return displayForgottenPassword(request, modelMap);
		}
		
		final Customer customer = customerService.getCustomerByLoginOrEmail(forgottenPasswordForm.getEmailOrLogin());
		if (customer == null) {
			initMessageError(result, null, getWordingMap(request), "forgottenPasswordForm", "emailOrLogin", "fo.auth.error_form_reset_password_email_doesnt_exist");
			return displayForgottenPassword(request, modelMap);
		}
		
		if (customer != null
				&& customer.isAnonymous()) {
			initMessageError(result, null, getWordingMap(request), "forgottenPasswordForm", "emailOrLogin", "fo.auth.error_form_reset_password_customer_is_not_active");
			return displayForgottenPassword(request, modelMap);
		}
		
		// FLAG THE CREDENTIAL WITH A TOKEN
		CustomerCredential customerCredential = webCommerceService.flagCustomerCredentialWithToken(request, requestUtil.getRequestData(request), customer);
		
		webCommerceService.buildAndSaveCustomerForgottenPasswordMail(requestUtil.getRequestData(request), customer, customerCredential, forgottenPasswordForm);
		
        return modelAndView;
	}
	
	@RequestMapping(value = FoUrls.RESET_PASSWORD_URL, method = RequestMethod.GET)
	public ModelAndView displayResetPassword(final HttpServletRequest request, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.RESET_PASSWORD.getVelocityPage());
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();

		String token = request.getParameter(RequestConstants.REQUEST_PARAMETER_PASSWORD_RESET_TOKEN);
		if (StringUtils.isEmpty(token)) {
			// ADD ERROR MESSAGE
			String errorMessage = getSpecificMessage(ScopeWebMessage.AUTH, "error_form_reset_password_token_is_wrong", locale);
			addErrorMessage(request, errorMessage);
		}
		
		String email = request.getParameter(RequestConstants.REQUEST_PARAMETER_PASSWORD_RESET_EMAIL);
		final Customer customer = customerService.getCustomerByLoginOrEmail(email);
		if (customer == null) {
			// ADD ERROR MESSAGE
			String errorMessage = getSpecificMessage(ScopeWebMessage.AUTH, "error_form_reset_password_email_or_login_are_wrong", locale);
			addErrorMessage(request, errorMessage);
		}
		
		if (!customer.getCurrentCredential().getResetToken().equals(token)) {
			// ADD ERROR MESSAGE
			String errorMessage = getSpecificMessage(ScopeWebMessage.AUTH, "error_form_reset_password_token_is_wrong", locale);
			addErrorMessage(request, errorMessage);
		}
		
        return modelAndView;
	}
	
	@RequestMapping(value = FoUrls.RESET_PASSWORD_URL, method = RequestMethod.POST)
	public ModelAndView resetPassword(final HttpServletRequest request, @Valid @ModelAttribute("resetPasswordForm") ResetPasswordForm resetPasswordForm,
			BindingResult result, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.RESET_PASSWORD_SUCCESS_VELOCITY_PAGE);

		if (result.hasErrors()) {
			return displayResetPassword(request, modelMap);
		}
		
		final Customer customer = customerService.getCustomerByLoginOrEmail(resetPasswordForm.getEmail());
		if (customer == null) {
			// ADD ERROR
			initMessageError(result, null, getWordingMap(request), "forgottenPasswordForm", "emailOrLogin", "fo.auth.error_form_reset_password_email_doesnt_exist");
			return displayResetPassword(request, modelMap);
		}
		
		if(!customer.getCurrentCredential().getResetToken().equals(resetPasswordForm.getToken())){
			// ADD ERROR
			initMessageError(result, null, getWordingMap(request), "forgottenPasswordForm", "confirmNewPassword", "fo.auth.error.form_reset_password_token_is_wrong");
			return displayResetPassword(request, modelMap);
		}
		
		if(!resetPasswordForm.getNewPassword().equals(resetPasswordForm.getConfirmNewPassword())){
			// ADD ERROR
			initMessageError(result, null, getWordingMap(request), "forgottenPasswordForm", "confirmNewPassword", "fo.auth.error_form_reset_password_confirm_password_is_wrong");
			return displayResetPassword(request, modelMap);
		}
		
		webCommerceService.resetCustomerCredential(request, requestUtil.getRequestData(request), customer, resetPasswordForm);

		webCommerceService.buildAndSaveCustomerResetPasswordConfirmationMail(requestUtil.getRequestData(request), customer);
		
		webCommerceService.cancelCustomerCredentialToken(request, requestUtil.getRequestData(request), customer);

        return modelAndView;
	}
	
	@RequestMapping(value = FoUrls.CANCEL_RESET_PASSWORD_URL, method = RequestMethod.GET)
	public ModelAndView cancelResetPassword(final HttpServletRequest request, ModelMap modelMap) throws Exception {
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
		
		// CANCEL TOKEN
		webCommerceService.cancelCustomerCredentialToken(request, requestUtil.getRequestData(request), customer);
		// ADD INFO/WARNING MESSAGE
		request.getSession().setAttribute(Constants.INFO_MESSAGE, getSpecificMessage(ScopeWebMessage.AUTH, "reset_password_is_cancel", locale));
		
		final String urlRedirect = urlService.generateUrl(FoUrls.LOGIN, requestUtil.getRequestData(request));
        return new ModelAndView(new RedirectView(urlRedirect));
	}
	
	/**
	 * 
	 */
    @ModelAttribute("forgottenPasswordForm")
	protected ForgottenPasswordForm getForgottenPasswordForm(final HttpServletRequest request, final Model model) throws Exception {
    	return new ForgottenPasswordForm();
	}
    
	/**
	 * 
	 */
    @ModelAttribute("resetPasswordForm")
	protected ResetPasswordForm getResetPasswordForm(final HttpServletRequest request, final Model model) throws Exception {
    	ResetPasswordForm resetPasswordForm = new ResetPasswordForm();
		String token = request.getParameter(RequestConstants.REQUEST_PARAMETER_PASSWORD_RESET_TOKEN);
		resetPasswordForm.setToken(token);
		String email = request.getParameter(RequestConstants.REQUEST_PARAMETER_PASSWORD_RESET_EMAIL);
		resetPasswordForm.setEmail(email);
    	return resetPasswordForm;
	}
    
	@ModelAttribute(ModelConstants.SECURITY_VIEW_BEAN)
	protected SecurityViewBean initSecurity(final HttpServletRequest request, final Model model) throws Exception {
		return viewBeanFactory.buildSecurityViewBean(requestUtil.getRequestData(request));
	}
	
}