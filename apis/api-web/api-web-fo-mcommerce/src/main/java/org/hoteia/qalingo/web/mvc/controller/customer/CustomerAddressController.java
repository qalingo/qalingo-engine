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

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.CustomerAddress;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.CustomerService;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerAddressListViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.form.CustomerAddressForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Controller("customerAddressController")
public class CustomerAddressController extends AbstractCustomerController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
    protected CustomerService customerService;
	
	@RequestMapping(FoUrls.PERSONAL_ADDRESS_LIST_URL)
	public ModelAndView customerListAddress(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.PERSONAL_ADDRESS_LIST.getVelocityPage());
		final RequestData requestData = requestUtil.getRequestData(request);
        final Customer currentCustomer = requestData.getCustomer();
		
		// WE RELOAD THE CUSTOMER FOR THE PERSISTANCE PROXY FILTER 
		// IT AVOIDS LazyInitializationException: could not initialize proxy - no Session
		final Customer reloadedCustomer = customerService.getCustomerByLoginOrEmail(currentCustomer.getLogin());
		
		final CustomerAddressListViewBean customerAdressesViewBean = frontofficeViewBeanFactory.buildViewBeanCustomerAddressList(requestUtil.getRequestData(request), reloadedCustomer);
		model.addAttribute("customerAdresses", customerAdressesViewBean);

        return modelAndView;
	}
	
	@RequestMapping(FoUrls.PERSONAL_DELETE_ADDRESS_URL)
	public ModelAndView customerDeleteAddress(final HttpServletRequest request, final Model model) throws Exception {
		final String customerAddressId = request.getParameter(RequestConstants.REQUEST_PARAMETER_CUSTOMER_ADDRESS_GUID);
		
		try {
			webManagementService.deleteAddressCustomer( requestUtil.getRequestData(request), customerAddressId);
			
		} catch (Exception e) {
			logger.error("Error with the address to edit, customerAddressId:" + customerAddressId, e);
		}

		final String urlRedirect = urlService.generateUrl(FoUrls.PERSONAL_DELETE_ADDRESS, requestUtil.getRequestData(request));
        return new ModelAndView(new RedirectView(urlRedirect));
	}
	
	@RequestMapping(value = FoUrls.PERSONAL_ADD_ADDRESS_URL, method = RequestMethod.GET)
	public ModelAndView displayCustomerAddAddress(final HttpServletRequest request, final Model model, @ModelAttribute("customerAddressForm") CustomerAddressForm customerAddressForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.PERSONAL_ADD_ADDRESS.getVelocityPage());
		final RequestData requestData = requestUtil.getRequestData(request);
        final Customer currentCustomer = requestData.getCustomer();
		
		// WE RELOAD THE CUSTOMER FOR THE PERSISTANCE PROXY FILTER 
		// IT AVOIDS LazyInitializationException: could not initialize proxy - no Session
		final Customer reloadedCustomer = customerService.getCustomerByLoginOrEmail(currentCustomer.getLogin());
		
		final CustomerAddressListViewBean customerAdressesViewBean = frontofficeViewBeanFactory.buildViewBeanCustomerAddressList(requestUtil.getRequestData(request), reloadedCustomer);
		model.addAttribute("customerAdresses", customerAdressesViewBean);
		
        return modelAndView;
	}

	@RequestMapping(value = FoUrls.PERSONAL_ADD_ADDRESS_URL, method = RequestMethod.POST)
	public ModelAndView submitCustomerAddAddress(final HttpServletRequest request, @Valid @ModelAttribute("customerAddressForm") CustomerAddressForm customerAddressForm,
								BindingResult result, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Market currentMarket = requestData.getMarket();
        final MarketArea currentMarketArea = requestData.getMarketArea();

		if (result.hasErrors()) {
			return displayCustomerAddAddress(request, model, customerAddressForm);
		}
		
		// Save the new address customer
		webManagementService.updateOrSaveAddressCustomer(requestData,  currentMarket, currentMarketArea, customerAddressForm);
		
		final String urlRedirect = urlService.generateUrl(FoUrls.PERSONAL_ADD_ADDRESS,requestUtil.getRequestData(request));
        return new ModelAndView(new RedirectView(urlRedirect));
	}
	
	@RequestMapping(value = FoUrls.PERSONAL_EDIT_ADDRESS_URL, method = RequestMethod.GET)
	public ModelAndView displayCustomerEditAddress(final HttpServletRequest request, final Model model, @ModelAttribute("customerAddressForm") CustomerAddressForm customerAddressForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.PERSONAL_EDIT_ADDRESS.getVelocityPage());
		final RequestData requestData = requestUtil.getRequestData(request);
        final Customer currentCustomer = requestData.getCustomer();
		
		// WE RELOAD THE CUSTOMER FOR THE PERSISTANCE PROXY FILTER 
		// IT AVOIDS LazyInitializationException: could not initialize proxy - no Session
		final Customer reloadedCustomer = customerService.getCustomerByLoginOrEmail(currentCustomer.getLogin());

		final CustomerAddressListViewBean customerAdressesViewBean = frontofficeViewBeanFactory.buildViewBeanCustomerAddressList(requestUtil.getRequestData(request), reloadedCustomer);
		model.addAttribute("customerAdresses", customerAdressesViewBean);
		

		String customerAddressId = request.getParameter(RequestConstants.REQUEST_PARAMETER_CUSTOMER_ADDRESS_GUID);
		if(StringUtils.isEmpty(customerAddressId)){
			customerAddressId = customerAddressForm.getIdOrGuid();
		}
		
		CustomerAddress customerAddress = null;
		try {
			customerAddress = reloadedCustomer.getAddress(new Long(customerAddressId));
			
		} catch (Exception e) {
			logger.error("Error with the address to edit, customerAddressId:" + customerAddressId, e);
			final String urlRedirect = urlService.generateUrl(FoUrls.PERSONAL_ADDRESS_LIST, requestUtil.getRequestData(request));
	        return new ModelAndView(new RedirectView(urlRedirect));
		}

		// SANITY CHECK : wrong address id for this customer
		if(customerAddress == null){
			final String urlRedirect = urlService.generateUrl(FoUrls.PERSONAL_ADDRESS_LIST,requestUtil.getRequestData(request));
	        return new ModelAndView(new RedirectView(urlRedirect));
		}
		
		if(customerAddressForm == null 
        		|| customerAddressForm.equals(new CustomerAddressForm())){
			customerAddressForm = formFactory.buildCustomerAddressForm(requestData, customerAddress);
			model.addAttribute("customerAddressForm", customerAddressForm);
		}

        return modelAndView;
	}

	@RequestMapping(value = FoUrls.PERSONAL_EDIT_ADDRESS_URL, method = RequestMethod.POST)
	public ModelAndView submitCustomerEditAddress(final HttpServletRequest request, @Valid @ModelAttribute("customerAddressForm") CustomerAddressForm customerAddressForm,
												  BindingResult result, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Market currentMarket = requestData.getMarket();
        final MarketArea currentMarketArea = requestData.getMarketArea();

		if (result.hasErrors()) {
			return displayCustomerEditAddress(request, model, customerAddressForm);
		}
		
		// Save the new address customer
		webManagementService.updateOrSaveAddressCustomer(requestData, currentMarket, currentMarketArea, customerAddressForm);
		
		final String urlRedirect = urlService.generateUrl(FoUrls.PERSONAL_ADDRESS_LIST,requestUtil.getRequestData(request));
        return new ModelAndView(new RedirectView(urlRedirect));
	}

    @ModelAttribute
    public void commonValues(HttpServletRequest request, Model model) throws Exception {
    	model.addAttribute(ModelConstants.URL_BACK, urlService.generateUrl(FoUrls.PERSONAL_DETAILS,requestUtil.getRequestData(request)));
    	model.addAttribute(ModelConstants.URL_CUSTOMER_ADDRESS_ADD, urlService.generateUrl(FoUrls.PERSONAL_ADD_ADDRESS,requestUtil.getRequestData(request)));
    	model.addAttribute(ModelConstants.URL_CUSTOMER_ADDRESS_EDIT, urlService.generateUrl(FoUrls.PERSONAL_EDIT_ADDRESS, requestUtil.getRequestData(request)));
    }

}