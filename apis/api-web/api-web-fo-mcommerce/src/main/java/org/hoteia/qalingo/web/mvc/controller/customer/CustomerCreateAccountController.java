/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.drools.core.util.StringUtils;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.CustomerService;
import org.hoteia.qalingo.core.web.mvc.form.CreateAccountForm;
import org.hoteia.qalingo.core.web.mvc.viewbean.BreadcrumbViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CaptchaViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.springframework.beans.factory.annotation.Autowired;
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
@Controller("customerCreateAccountController")
public class CustomerCreateAccountController extends AbstractCustomerController {

	@Autowired
    protected CustomerService customerService;
	
	@RequestMapping(value = FoUrls.CUSTOMER_CREATE_ACCOUNT_URL, method = RequestMethod.GET)
	public ModelAndView displayCustomerCreateAccount(final HttpServletRequest request, final Model model, @ModelAttribute(ModelConstants.FRONTOFFICE_CREATE_ACCOUNT_FORM) CreateAccountForm createAccountForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.CUSTOMER_CREATE_ACCOUNT.getVelocityPage());
		final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();
        
		// SANITY CHECK: Customer logged
		if(securityUtil.isAuthenticated()){
			final String url = urlService.generateUrl(FoUrls.PERSONAL_DETAILS, requestUtil.getRequestData(request));
			return new ModelAndView(new RedirectView(url));
		}
		
		final List<String> excludedPatterns = new ArrayList<String>();
        excludedPatterns.add(FoUrls.CUSTOMER_CREATE_ACCOUNT.getUrlWithoutWildcard());
		final String lastUrl = requestUtil.getLastRequestUrl(request, excludedPatterns, urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request)));
		
		modelAndView.addObject(ModelConstants.URL_BACK, lastUrl);
		
		// CAPTCHA
		EngineSetting siteKeyEngineSetting = engineSettingService.getSettingWebCaptchaSiteKey();
		if(siteKeyEngineSetting != null){
	        CaptchaViewBean captchaViewBean = new CaptchaViewBean();
	        captchaViewBean.setSiteKey(siteKeyEngineSetting.getDefaultValue());
	        modelAndView.addObject(ModelConstants.CAPTCHA_VIEW_BEAN, captchaViewBean);
		}
		
		model.addAttribute(ModelConstants.BREADCRUMB_VIEW_BEAN, buildCommonBreadcrumbViewBean(requestData));
        
        return modelAndView;
	}

	@RequestMapping(value = FoUrls.CUSTOMER_CREATE_ACCOUNT_URL, method = RequestMethod.POST)
	public ModelAndView customerCreateAccount(final HttpServletRequest request, final Model model, @Valid @ModelAttribute(ModelConstants.FRONTOFFICE_CREATE_ACCOUNT_FORM) CreateAccountForm createAccountForm,
								BindingResult result) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Market currentMarket = requestData.getMarket();
        final MarketArea currentMarketArea = requestData.getMarketArea();

		// SANITY CHECK: Customer logged
        final Customer currentCustomer = requestData.getCustomer();
		if(currentCustomer != null){
			final String url = urlService.generateRedirectUrl(FoUrls.PERSONAL_DETAILS,requestUtil.getRequestData(request));
			return new ModelAndView(new RedirectView(url));
		}
		
		if (result.hasErrors()) {
			return displayCustomerCreateAccount(request, model, createAccountForm);
		}
		
		final String email = createAccountForm.getEmail();
		final Customer customer = customerService.getCustomerByLoginOrEmail(email);
		if(customer != null){
			final String forgottenPasswordUrl = urlService.generateUrl(FoUrls.FORGOTTEN_PASSWORD, requestUtil.getRequestData(request));
			final Object[] objects = {forgottenPasswordUrl};
			result.rejectValue("email", "fo.customer.error_form_create_account_account_already_exist", objects,"This email customer account already exist! Go on this <a href=\"forgotten-password.html\" alt=\"\">page</a> to get a new password.");
			return displayCustomerCreateAccount(request, model, createAccountForm);
		}

		// Save the new customer
		final Customer newCustomer = webManagementService.buildAndSaveNewCustomer(requestData, currentMarket, currentMarketArea, createAccountForm);

		// Save the email confirmation
		webManagementService.buildAndSaveCustomerNewAccountMail(requestData, newCustomer);

		// Login the new customer
		securityRequestUtil.authenticationCustomer(request, newCustomer);
		
		final String urlRedirect = urlService.generateRedirectUrl(FoUrls.PERSONAL_DETAILS, requestData);
        return new ModelAndView(new RedirectView(urlRedirect));
	}
	
	protected BreadcrumbViewBean buildCommonBreadcrumbViewBean(final RequestData requestData) {
        final Locale locale = requestData.getLocale();
        
        // BREADCRUMB
        BreadcrumbViewBean breadcrumbViewBean = new BreadcrumbViewBean();
        breadcrumbViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_TITLE, FoUrls.CUSTOMER_CREATE_ACCOUNT.getKey(), locale));
        
        List<MenuViewBean> menuViewBeans = breadcrumbViewBean.getMenus();
        MenuViewBean menu = new MenuViewBean();
        menu.setKey(FoUrls.HOME.getKey());
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.HOME.getKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.HOME, requestData));
        menuViewBeans.add(menu);
        
        menu = new MenuViewBean();
        menu.setKey(FoUrls.CUSTOMER_CREATE_ACCOUNT.getKey());
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.CUSTOMER_CREATE_ACCOUNT.getKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.CUSTOMER_CREATE_ACCOUNT, requestData));
        menu.setActive(true);
        menuViewBeans.add(menu);
        
        return breadcrumbViewBean;
	}
	
    @RequestMapping(value = FoUrls.CART_CREATE_ACCOUNT_URL, method = RequestMethod.GET)
    public ModelAndView displayCheckoutCreateAccount(final HttpServletRequest request, final Model model, @ModelAttribute(ModelConstants.FRONTOFFICE_CREATE_ACCOUNT_FORM) CreateAccountForm createAccountForm) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.CUSTOMER_CREATE_ACCOUNT.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        // SANITY CHECK: Customer logged
        final Customer currentCustomer = requestData.getCustomer();
        if(currentCustomer != null){
            final String url = urlService.generateRedirectUrl(FoUrls.CART_DELIVERY, requestUtil.getRequestData(request));
            return new ModelAndView(new RedirectView(url));
        }
        
        final List<String> excludedPatterns = new ArrayList<String>();
        excludedPatterns.add(FoUrls.CUSTOMER_CREATE_ACCOUNT.getUrlWithoutWildcard());
        final String lastUrl = requestUtil.getLastRequestUrl(request, excludedPatterns, urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request)));
        modelAndView.addObject(ModelConstants.URL_BACK, lastUrl);
        
        modelAndView.addObject(ModelConstants.CHECKOUT_STEP, 2);
        
        return modelAndView;
    }

    @RequestMapping(value = FoUrls.CART_CREATE_ACCOUNT_URL, method = RequestMethod.POST)
    public ModelAndView checkoutCreateAccount(final HttpServletRequest request, final Model model, @Valid @ModelAttribute(ModelConstants.FRONTOFFICE_CREATE_ACCOUNT_FORM) CreateAccountForm createAccountForm,
                                BindingResult result) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Market currentMarket = requestData.getMarket();
        final MarketArea currentMarketArea = requestData.getMarketArea();

        // SANITY CHECK: Customer logged
        final Customer currentCustomer = requestData.getCustomer();
        if(currentCustomer != null){
            final String url = urlService.generateRedirectUrl(FoUrls.CART_DELIVERY,requestUtil.getRequestData(request));
            return new ModelAndView(new RedirectView(url));
        }
        
        // "customer.create.account";
        
        if (result.hasErrors()) {
            return displayCustomerCreateAccount(request, model, createAccountForm);
        }
        
        final String email = createAccountForm.getEmail();
        final Customer customer = customerService.getCustomerByLoginOrEmail(email);
        if(customer != null){
            final String forgottenPasswordUrl = urlService.generateUrl(FoUrls.FORGOTTEN_PASSWORD, requestData);
            final Object[] objects = {forgottenPasswordUrl};
            result.rejectValue("email", "error.form.create.account.account.already.exist", objects,"This email customer account already exist! Go on this <a href=\"${0}\" alt=\"\">page</a> to get a new password.");
        }

        // Save the new customer
        final Customer newCustomer = webManagementService.buildAndSaveNewCustomer(requestData, currentMarket, currentMarketArea, createAccountForm);

        // Save the email confirmation
        webManagementService.buildAndSaveCustomerNewAccountMail(requestData, newCustomer);

        // Login the new customer
        securityRequestUtil.authenticationCustomer(request, newCustomer);
        
        final String urlRedirect = urlService.generateRedirectUrl(FoUrls.CART_DELIVERY, requestData);
        return new ModelAndView(new RedirectView(urlRedirect));
    }
    
	@RequestMapping(value = FoUrls.CUSTOMER_NEW_ACCOUNT_VALIDATION_URL, method = RequestMethod.GET)
	public ModelAndView newAccountValidation(final HttpServletRequest request, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();

		String token = request.getParameter(RequestConstants.REQUEST_PARAMETER_NEW_ACCOUNT_VALIDATION_TOKEN);
		if (StringUtils.isEmpty(token)) {
			// ADD ERROR MESSAGE
			String errorMessage = getSpecificMessage(ScopeWebMessage.CUSTOMER, "error_form_new_account_validation_token_is_wrong", locale);
			addErrorMessage(request, errorMessage);
		}
		
		String email = request.getParameter(RequestConstants.REQUEST_PARAMETER_NEW_CUSTOMER_VALIDATION_EMAIL);
		final Customer customer = customerService.getCustomerByLoginOrEmail(email);
		if (customer == null) {
			// ADD ERROR MESSAGE
			String errorMessage = getSpecificMessage(ScopeWebMessage.CUSTOMER, "error_form_new_account_validation_email_or_login_are_wrong", locale);
			addErrorMessage(request, errorMessage);
		}
		
		// Save customer as active
		webManagementService.activeNewCustomer(requestData, customer);

		// ADD SUCCESS MESSAGE
		String successMessage = getSpecificMessage(ScopeWebMessage.CUSTOMER, "form_new_account_validation_success_message", locale);
		addSuccessMessage(request, successMessage);

		final String urlRedirect = urlService.generateRedirectUrl(FoUrls.PERSONAL_DETAILS, requestData);
        return new ModelAndView(new RedirectView(urlRedirect));
	}
	
	/**
	 * 
	 */
    @ModelAttribute(ModelConstants.FRONTOFFICE_CREATE_ACCOUNT_FORM)
	protected CreateAccountForm getCreateAccountForm(final HttpServletRequest request, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
    	return formFactory.buildCreateAccountForm(requestData);
	}
    
}