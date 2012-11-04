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
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import fr.hoteia.qalingo.core.common.domain.Customer;
import fr.hoteia.qalingo.core.common.domain.Localization;
import fr.hoteia.qalingo.core.common.domain.Market;
import fr.hoteia.qalingo.core.common.domain.MarketArea;
import fr.hoteia.qalingo.core.common.domain.MarketPlace;
import fr.hoteia.qalingo.core.common.domain.Retailer;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractQalingoController;
import fr.hoteia.qalingo.web.mvc.form.CustomerEditForm;
import fr.hoteia.qalingo.web.service.WebCommerceService;

/**
 * 
 */
@Controller
public class CustomerDetailsController extends AbstractQalingoController {

	@Autowired
    protected WebCommerceService webCommerceService;
	
	@RequestMapping(value = "/customer-details.html*", method = RequestMethod.GET)
	public ModelAndView customerDetails(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "customer/customer-details");
		
		final String titleKeyPrefixSufix = "customer.details";
		initPage(request, response, modelAndView, titleKeyPrefixSufix);
		final Customer customer = requestUtil.getCurrentCustomer(request);
		modelAndViewFactory.initCustomerDetailsAccountModelAndView(request, modelAndView, customer);

        return modelAndView;
	}
	
	@RequestMapping(value = "/customer-edit-form.html*")
	public ModelAndView customerEditForm(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "customer/customer-details-form");
		
		final Customer customer = requestUtil.getCurrentCustomer(request);
		final String titleKeyPrefixSufix = "customer.details";
		initPage(request, response, modelAndView, titleKeyPrefixSufix);
		modelAndViewFactory.initCustomerDetailsAccountModelAndView(request, modelAndView, customer);
		formFactory.buildCustomerEditAccountForm(request, customer, modelAndView);

        return modelAndView;
	}
	
	@RequestMapping(value = "/customer-edit.html*", method = RequestMethod.GET)
	public ModelAndView customerEdit(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "customer/customer-details");
		
		final String titleKeyPrefixSufix = "customer.details";
		initPage(request, response, modelAndView, titleKeyPrefixSufix);
		final Customer customer = requestUtil.getCurrentCustomer(request);
		modelAndViewFactory.initCustomerDetailsAccountModelAndView(request, modelAndView, customer);

        return modelAndView;
	}
	
	@RequestMapping(value = "/customer-edit.html*", method = RequestMethod.POST)
	public ModelAndView customerEdit(final HttpServletRequest request, final HttpServletResponse response, @Valid CustomerEditForm customerEditForm,
								BindingResult result, ModelMap modelMap) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		
		final String titleKeyPrefixSufix = "customer.details";

		if (result.hasErrors()) {
			ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "customer/customer-details-form");
			modelAndViewFactory.initCustomerCreateAccountModelAndView(request, modelAndView);
			initPage(request, response, modelAndView, titleKeyPrefixSufix);
			return modelAndView;
		}
		
		final String newEmail = customerEditForm.getEmail();
		final Customer currentCustomer = requestUtil.getCurrentCustomer(request);
		final Customer checkCustomer = customerService.getCustomerByLoginOrEmail(newEmail);
		if(checkCustomer != null
				&& !currentCustomer.getEmail().equalsIgnoreCase(newEmail)) {
			final String forgottenPasswordUrl = urlService.buildForgottenPasswordUrl(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
			final Object[] objects = {forgottenPasswordUrl};
			result.rejectValue("email", "error.form.customer.create.account.account.already.exist", objects,"This email customer account already exist! Go on this <a href=\"${0}\" alt=\"\">page</a> to get a new password.");
		}

		// Update the customer
		webCommerceService.updateCurrentCustomer(request, currentMarket, currentMarketArea, customerEditForm);
		
		final String urlRedirect = urlService.buildCustomerDetailsUrl(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
        return new ModelAndView(new RedirectView(urlRedirect));
	}

}
