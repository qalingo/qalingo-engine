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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.CustomerService;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.form.CustomerEditForm;
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
@Controller("customerDetailsController")
public class CustomerDetailsController extends AbstractCustomerController {

	@Autowired
    protected CustomerService customerService;
	
	@RequestMapping(value = FoUrls.CUSTOMER_DETAILS_URL, method = RequestMethod.GET)
	public ModelAndView customerDetails(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.CUSTOMER_DETAILS.getVelocityPage());
		
		final String permalink = request.getParameter(RequestConstants.REQUEST_PARAMETER_CUSTOMER_PERMALINK);
		final Customer customer = customerService.getCustomerByPermalink(permalink);
		
		// WE RELOAD THE CUSTOMER FOR THE PERSISTANCE PROXY FILTER 
		// IT AVOIDS LazyInitializationException: could not initialize proxy - no Session
		final Customer reloadedCustomer = customerService.getCustomerByLoginOrEmail(customer.getLogin());
		
		final CustomerViewBean customerView = frontofficeViewBeanFactory.buildViewBeanCustomer(requestUtil.getRequestData(request), reloadedCustomer);
		model.addAttribute(ModelConstants.CUSTOMER_DETAILS_VIEW_BEAN, customerView);
		
        return modelAndView;
	}
	
	@RequestMapping(value = FoUrls.PERSONAL_DETAILS_URL, method = RequestMethod.GET)
	public ModelAndView personalDetails(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.PERSONAL_DETAILS.getVelocityPage());
		
		// Customer is already set by the abstract

        return modelAndView;
	}
	
	@RequestMapping(value = FoUrls.PERSONAL_EDIT_URL, method = RequestMethod.GET)
	public ModelAndView displayPersonalEdit(final HttpServletRequest request, final Model model, @ModelAttribute("customerEditForm") CustomerEditForm customerEditForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.PERSONAL_EDIT.getVelocityPage());
		final RequestData requestData = requestUtil.getRequestData(request);
        final Customer currentCustomer = requestData.getCustomer();
		
		// WE RELOAD THE CUSTOMER FOR THE PERSISTANCE PROXY FILTER 
		// IT AVOIDS LazyInitializationException: could not initialize proxy - no Session
		final Customer reloadedCustomer = customerService.getCustomerByLoginOrEmail(currentCustomer.getLogin());
        
		if(customerEditForm == null 
        		|| customerEditForm.equals(new CustomerEditForm())){
			customerEditForm = formFactory.buildCustomerEditForm(requestData, reloadedCustomer);
			model.addAttribute("customerEditForm", customerEditForm);
		}
		
        return modelAndView;
	}
	
	@RequestMapping(value = FoUrls.PERSONAL_EDIT_URL, method = RequestMethod.POST)
	public ModelAndView submitPersonalEdit(final HttpServletRequest request, @Valid @ModelAttribute("customerEditForm") CustomerEditForm customerEditForm,
								BindingResult result, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Market currentMarket = requestData.getMarket();
        final MarketArea currentMarketArea = requestData.getMarketArea();
		
		if (result.hasErrors()) {
			return displayPersonalEdit(request, model, customerEditForm);
		}
		
		final String newEmail = customerEditForm.getEmail();
        final Customer currentCustomer = requestData.getCustomer();
		final Customer checkCustomer = customerService.getCustomerByLoginOrEmail(newEmail);
		if(checkCustomer != null
				&& !currentCustomer.getEmail().equalsIgnoreCase(newEmail)) {
			final String forgottenPasswordUrl = urlService.generateUrl(FoUrls.FORGOTTEN_PASSWORD, requestData);
			final Object[] objects = {forgottenPasswordUrl};
			result.rejectValue("email", "fo.customer.error_form_create_account_account_already_exist", objects,"This email customer account already exist! Go on this <a href=\"${0}\" alt=\"\">page</a> to get a new password.");
		}

		// Update the customer
		webManagementService.updateCurrentCustomer(requestData, currentMarket, currentMarketArea, customerEditForm);
		
		final String urlRedirect = urlService.generateUrl(FoUrls.PERSONAL_DETAILS, requestData);
        return new ModelAndView(new RedirectView(urlRedirect));
	}
	
    @ModelAttribute
    public void commonValues(HttpServletRequest request, Model model) throws Exception {
    	model.addAttribute(ModelConstants.URL_BACK, urlService.generateUrl(FoUrls.PERSONAL_DETAILS,requestUtil.getRequestData(request)));
    	model.addAttribute(ModelConstants.URL_CUSTOMER_EDIT, urlService.generateUrl(FoUrls.PERSONAL_EDIT, requestUtil.getRequestData(request)));
    }
    
}