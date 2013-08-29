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
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import fr.hoteia.qalingo.core.ModelConstants;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.enumtype.FoUrls;
import fr.hoteia.qalingo.core.security.util.SecurityUtil;
import fr.hoteia.qalingo.core.service.CustomerService;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.form.CreateAccountForm;
import fr.hoteia.qalingo.web.service.WebCommerceService;

/**
 * 
 */
@Controller("customerCreateAccountController")
public class CustomerCreateAccountController extends AbstractCustomerController {

	@Autowired
    protected CustomerService customerService;
	
	@Autowired
    protected WebCommerceService webCommerceService;
	
	@Autowired
    protected SecurityUtil securityUtil;
	
	@RequestMapping(value = "/customer-create-account.html*", method = RequestMethod.GET)
	public ModelAndView displayCustomerCreateAccount(final HttpServletRequest request, final Model model, @ModelAttribute("createAccountForm") CreateAccountForm createAccountForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "customer/customer-create-account-form");
		
		// SANITY CHECK: Customer logged
		final Customer currentCustomer = requestUtil.getCurrentCustomer(request);
		if(currentCustomer != null){
			final String url = urlService.generateUrl(FoUrls.PERSONAL_DETAILS, requestUtil.getRequestData(request));
			return new ModelAndView(new RedirectView(url));
		}
		
		// "customer.create.account";

		modelAndView.addObject(ModelConstants.URL_BACK, urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request)));
		
//		formFactory.buildCustomerCreateAccountForm(request, modelAndView);

        return modelAndView;
	}

	@RequestMapping(value = "/customer-create-account.html*", method = RequestMethod.POST)
	public ModelAndView customerCreateAccount(final HttpServletRequest request, final Model model, @Valid @ModelAttribute("createAccountForm") CreateAccountForm createAccountForm,
								BindingResult result) throws Exception {
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);

		// SANITY CHECK: Customer logged
		final Customer currentCustomer = requestUtil.getCurrentCustomer(request);
		if(currentCustomer != null){
			final String url = urlService.generateUrl(FoUrls.PERSONAL_DETAILS,requestUtil.getRequestData(request));
			return new ModelAndView(new RedirectView(url));
		}
		
		// "customer.create.account";
		
		if (result.hasErrors()) {
			return displayCustomerCreateAccount(request, model, createAccountForm);
		}
		
		final String email = createAccountForm.getEmail();
		final Customer customer = customerService.getCustomerByLoginOrEmail(email);
		if(customer != null){
			final String forgottenPasswordUrl = urlService.generateUrl(FoUrls.FORGOTTEN_PASSWORD, requestUtil.getRequestData(request));
			final Object[] objects = {forgottenPasswordUrl};
			result.rejectValue("email", "error.form.create.account.account.already.exist", objects,"This email customer account already exist! Go on this <a href=\"${0}\" alt=\"\">page</a> to get a new password.");
		}

		// Save the new customer
		final Customer newCustomer = webCommerceService.buildAndSaveNewCustomer(request, currentMarket, currentMarketArea, createAccountForm);

		// Save the email confirmation
		webCommerceService.buildAndSaveCustomerNewAccountMail(request, createAccountForm);

		// Login the new customer
		securityUtil.authenticationCustomer(request, newCustomer);
		
		final String urlRedirect = urlService.generateUrl(FoUrls.PERSONAL_DETAILS, requestUtil.getRequestData(request));
        return new ModelAndView(new RedirectView(urlRedirect));
	}
	
	/**
	 * 
	 */
    @ModelAttribute("createAccountForm")
	protected CreateAccountForm getCreateAccountForm(final HttpServletRequest request, final Model model) throws Exception {
    	return formFactory.buildCreateAccountForm(request);
	}
    
}