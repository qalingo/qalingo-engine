/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.controller.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.domain.UserCredential;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.web.mvc.controller.AbstractBackofficeQalingoController;
import org.hoteia.qalingo.core.web.mvc.form.ForgottenPasswordForm;
import org.hoteia.qalingo.core.web.mvc.form.ResetPasswordForm;
import org.hoteia.qalingo.core.web.mvc.viewbean.BreadcrumbViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SecurityViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("forgottentPasswordController")
public class ForgottentPasswordController extends AbstractBackofficeQalingoController {

	@RequestMapping(value = BoUrls.FORGOTTEN_PASSWORD_URL, method = RequestMethod.GET)
	public ModelAndView displayForgottenPassword(final HttpServletRequest request, Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.FORGOTTEN_PASSWORD.getVelocityPage());
		
		modelAndView.addObject("formForgottenPassword", new ForgottenPasswordForm());
		
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.FORGOTTEN_PASSWORD_URL, method = RequestMethod.POST)
	public ModelAndView forgottenPassword(final HttpServletRequest request, @Valid ForgottenPasswordForm forgottenPasswordForm,
			BindingResult result, Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "security/forgotten-password-success");
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();
        
        if (result.hasErrors()) {
            return displayForgottenPassword(request, model);
        }
        
        final User user = userService.getUserByLoginOrEmail(forgottenPasswordForm.getEmailOrLogin());
        if (user == null) {
            addMessageError(result, null, ModelConstants.FORGOTTEN_PASSWORD_FORM, "emailOrLogin", getSpecificMessage(ScopeWebMessage.AUTH, "error_form_reset_password_email_doesnt_exist", locale));
            return displayForgottenPassword(request, model);
        }
        
        // FLAG THE CREDENTIAL WITH A TOKEN
        UserCredential userCredential = webBackofficeService.flagUserCredentialWithToken(requestData, user);
        
        webBackofficeService.buildAndSaveUserForgottenPasswordMail(requestData, user, userCredential, forgottenPasswordForm);

        // SEO
        overrideDefaultMainContentTitle(request, modelAndView, BoUrls.FORGOTTEN_PASSWORD.getKey());

        // BREADCRUMB
        model.addAttribute(ModelConstants.BREADCRUMB_VIEW_BEAN, buildBreadcrumbViewBean(requestData));
        
        return modelAndView;
	}
	
    @RequestMapping(value = BoUrls.RESET_PASSWORD_URL, method = RequestMethod.GET)
    public ModelAndView displayResetPassword(final HttpServletRequest request, final Model model) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.RESET_PASSWORD.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();

        String token = request.getParameter(RequestConstants.REQUEST_PARAMETER_PASSWORD_RESET_TOKEN);
        if (StringUtils.isEmpty(token)) {
            // ADD ERROR MESSAGE
            String errorMessage = getSpecificMessage(ScopeWebMessage.AUTH, "error_form_reset_password_token_is_wrong", locale);
            addErrorMessage(request, errorMessage);
        }
        
        String email = request.getParameter(RequestConstants.REQUEST_PARAMETER_PASSWORD_RESET_EMAIL);
        final User user = userService.getUserByLoginOrEmail(email);
        if (user == null) {
            // ADD ERROR MESSAGE
            model.addAttribute(ModelConstants.AUTH_HAS_FAIL, true);
            String errorMessage = getSpecificMessage(ScopeWebMessage.AUTH, "error_form_reset_password_email_or_login_are_wrong", locale);
            addErrorMessage(request, errorMessage);
        }
        
        if (!user.getCurrentCredential().getResetToken().equals(token)) {
            // ADD ERROR MESSAGE
            model.addAttribute(ModelConstants.AUTH_HAS_FAIL, true);
            String errorMessage = getSpecificMessage(ScopeWebMessage.AUTH, "error_form_reset_password_token_is_wrong", locale);
            addErrorMessage(request, errorMessage);
        }
        
        overrideDefaultMainContentTitle(request, modelAndView, BoUrls.RESET_PASSWORD.getKey());

        model.addAttribute(ModelConstants.BREADCRUMB_VIEW_BEAN, buildBreadcrumbViewBean(requestData));

        return modelAndView;
    }
    
    @RequestMapping(value = BoUrls.RESET_PASSWORD_URL, method = RequestMethod.POST)
    public ModelAndView resetPassword(final HttpServletRequest request, @Valid @ModelAttribute(ModelConstants.RESET_PASSWORD_FORM) ResetPasswordForm resetPasswordForm,
                                      BindingResult result, final Model model) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.RESET_PASSWORD_SUCCESS_VELOCITY_PAGE);
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();
        
        if (result.hasErrors()) {
            return displayResetPassword(request, model);
        }
        
        final User user = userService.getUserByLoginOrEmail(resetPasswordForm.getEmail());
        if (user == null) {
            // ADD ERROR
            addMessageError(result, null, ModelConstants.FORGOTTEN_PASSWORD_FORM, "emailOrLogin",  getSpecificMessage(ScopeWebMessage.AUTH, "error_form_reset_password_email_doesnt_exist", locale));
            return displayResetPassword(request, model);
        }
        
        if(!user.getCurrentCredential().getResetToken().equals(resetPasswordForm.getToken())){
            // ADD ERROR
            addMessageError(result, null, ModelConstants.FORGOTTEN_PASSWORD_FORM, "confirmNewPassword",  getSpecificMessage(ScopeWebMessage.AUTH, "error.form_reset_password_token_is_wrong", locale));
            return displayResetPassword(request, model);
        }
        
        if(!resetPasswordForm.getNewPassword().equals(resetPasswordForm.getConfirmNewPassword())){
            // ADD ERROR
            addMessageError(result, null, ModelConstants.FORGOTTEN_PASSWORD_FORM, "confirmNewPassword",  getSpecificMessage(ScopeWebMessage.AUTH, "error_form_reset_password_confirm_password_is_wrong", locale));
            return displayResetPassword(request, model);
        }
        
        webBackofficeService.resetUserCredential(requestData, user, resetPasswordForm);

        webBackofficeService.buildAndSaveUserResetPasswordConfirmationMail(requestData, user);
        
        return modelAndView;
    }
    
    @RequestMapping(value = BoUrls.CANCEL_RESET_PASSWORD_URL, method = RequestMethod.GET)
    public ModelAndView cancelResetPassword(final HttpServletRequest request, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();

        String token = request.getParameter(RequestConstants.REQUEST_PARAMETER_PASSWORD_RESET_TOKEN);
        if (StringUtils.isEmpty(token)) {
            // ADD ERROR MESSAGE
            String errorMessage = getSpecificMessage(ScopeWebMessage.AUTH, "reset_password_token_is_wrong", locale);
            addErrorMessage(request, errorMessage);
        }
        
        String email = request.getParameter(RequestConstants.REQUEST_PARAMETER_PASSWORD_RESET_EMAIL);
        final User user = userService.getUserByLoginOrEmail(email);
        if (user == null) {
            // ADD ERROR MESSAGE
            String errorMessage = getSpecificMessage(ScopeWebMessage.AUTH, "reset_password_login_or_email_are_wrong", locale);
            addErrorMessage(request, errorMessage);
        }
        
        // CANCEL TOKEN
        webBackofficeService.cancelUserCredentialToken(requestData, user);
        
        // ADD INFO/WARNING MESSAGE
        request.getSession().setAttribute(Constants.INFO_MESSAGE, getSpecificMessage(ScopeWebMessage.AUTH, "reset_password_is_cancel", locale));
        
        final String urlRedirect = backofficeUrlService.generateRedirectUrl(BoUrls.LOGIN, requestUtil.getRequestData(request));
        return new ModelAndView(new RedirectView(urlRedirect));
    }
    
    /**
     * 
     */
    @ModelAttribute(ModelConstants.FORGOTTEN_PASSWORD_FORM)
    protected ForgottenPasswordForm getForgottenPasswordForm(final HttpServletRequest request, final Model model) throws Exception {
        return new ForgottenPasswordForm();
    }
    
    /**
     * 
     */
    @ModelAttribute(ModelConstants.RESET_PASSWORD_FORM)
    protected ResetPasswordForm getResetPasswordForm(final HttpServletRequest request, final Model model) throws Exception {
        ResetPasswordForm resetPasswordForm = new ResetPasswordForm();
        String token = request.getParameter(RequestConstants.REQUEST_PARAMETER_PASSWORD_RESET_TOKEN);
        resetPasswordForm.setToken(token);
        String email = request.getParameter(RequestConstants.REQUEST_PARAMETER_PASSWORD_RESET_EMAIL);
        resetPasswordForm.setEmail(email);
        return resetPasswordForm;
    }
	
	@ModelAttribute(ModelConstants.SECURITY_VIEW_BEAN)
	protected SecurityViewBean initSecurityViewBean(final HttpServletRequest request, final Model model) throws Exception {
		return backofficeViewBeanFactory.buildViewBeanSecurity(requestUtil.getRequestData(request));
	}
	
    protected BreadcrumbViewBean buildBreadcrumbViewBean(final RequestData requestData) {
        final Locale locale = requestData.getLocale();

        // BREADCRUMB
        BreadcrumbViewBean breadcrumbViewBean = new BreadcrumbViewBean();
        breadcrumbViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_TITLE, "forgotten_password", locale));

        List<MenuViewBean> menuViewBeans = new ArrayList<MenuViewBean>();
        MenuViewBean menu = new MenuViewBean();
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, BoUrls.HOME.getMessageKey(), locale));
        menu.setUrl(backofficeUrlService.generateUrl(BoUrls.HOME, requestData));
        menuViewBeans.add(menu);

        menu = new MenuViewBean();
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_TITLE, "forgotten_password", locale));
        menu.setUrl(backofficeUrlService.generateUrl(BoUrls.FORGOTTEN_PASSWORD, requestData));
        menu.setActive(true);
        menuViewBeans.add(menu);

        breadcrumbViewBean.setMenus(menuViewBeans);
        return breadcrumbViewBean;
    }
	
}