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

import fr.hoteia.qalingo.core.ModelConstants;
import fr.hoteia.qalingo.core.RequestConstants;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.enumtype.FoUrls;
import fr.hoteia.qalingo.core.service.CustomerService;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.core.web.servlet.view.RedirectView;
import fr.hoteia.qalingo.web.mvc.form.CustomerEditForm;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerViewBean;

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
		
		final CustomerViewBean customerView = viewBeanFactory.buildCustomerViewBean(requestUtil.getRequestData(request), reloadedCustomer);
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
		
		final Customer currentCustomer = requestUtil.getCurrentCustomer(request);
		
		// WE RELOAD THE CUSTOMER FOR THE PERSISTANCE PROXY FILTER 
		// IT AVOIDS LazyInitializationException: could not initialize proxy - no Session
		final Customer reloadedCustomer = customerService.getCustomerByLoginOrEmail(currentCustomer.getLogin());

		if(customerEditForm == null 
        		|| customerEditForm.equals(new CustomerEditForm())){
			customerEditForm = formFactory.buildCustomerEditForm(request, reloadedCustomer);
			model.addAttribute("customerEditForm", customerEditForm);
		}
		
        return modelAndView;
	}
	
	@RequestMapping(value = FoUrls.PERSONAL_EDIT_URL, method = RequestMethod.POST)
	public ModelAndView submitPersonalEdit(final HttpServletRequest request, @Valid @ModelAttribute("customerEditForm") CustomerEditForm customerEditForm,
								BindingResult result, final Model model) throws Exception {
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		
		if (result.hasErrors()) {
			return displayPersonalEdit(request, model, customerEditForm);
		}
		
		final String newEmail = customerEditForm.getEmail();
		final Customer currentCustomer = requestUtil.getCurrentCustomer(request);
		final Customer checkCustomer = customerService.getCustomerByLoginOrEmail(newEmail);
		if(checkCustomer != null
				&& !currentCustomer.getEmail().equalsIgnoreCase(newEmail)) {
			final String forgottenPasswordUrl = urlService.generateUrl(FoUrls.FORGOTTEN_PASSWORD, requestUtil.getRequestData(request));
			final Object[] objects = {forgottenPasswordUrl};
			result.rejectValue("email", "fo.customer.error_form_create_account_account_already_exist", objects,"This email customer account already exist! Go on this <a href=\"${0}\" alt=\"\">page</a> to get a new password.");
		}

		// Update the customer
		webCommerceService.updateCurrentCustomer(request, requestUtil.getRequestData(request), currentMarket, currentMarketArea, customerEditForm);
		
		final String urlRedirect = urlService.generateUrl(FoUrls.PERSONAL_DETAILS, requestUtil.getRequestData(request));
        return new ModelAndView(new RedirectView(urlRedirect));
	}
	
    @ModelAttribute
    public void commonValues(HttpServletRequest request, Model model) throws Exception {
    	model.addAttribute(ModelConstants.URL_BACK, urlService.generateUrl(FoUrls.PERSONAL_DETAILS,requestUtil.getRequestData(request)));
    	model.addAttribute(ModelConstants.URL_CUSTOMER_EDIT, urlService.generateUrl(FoUrls.PERSONAL_EDIT, requestUtil.getRequestData(request)));
    }
    
}