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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.CustomerAddress;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.service.CustomerService;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractMCommerceFrontofficeController;
import fr.hoteia.qalingo.web.mvc.form.CustomerAddressForm;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerAddressListViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ValueBean;
import fr.hoteia.qalingo.web.service.WebCommerceService;

/**
 * 
 */
@Controller
public class CustomerAddressController extends AbstractMCommerceFrontofficeController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
    protected CustomerService customerService;
	
	@Autowired
    protected WebCommerceService webCommerceService;
	
	@RequestMapping(value = "/customer-address-list.html*")
	public ModelAndView customerListAddress(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "customer/customer-address-list");
		
		final Customer customer = requestUtil.getCurrentCustomer(request);
		// "customer.address.list";

		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final CustomerAddressListViewBean customerAdressesViewBean = viewBeanFactory.buildCustomerAddressListViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer, customer);
		modelAndView.addObject("customerAdresses", customerAdressesViewBean);

        return modelAndView;
	}
	
	@RequestMapping(value = "/customer-delete-address.html*")
	public ModelAndView customerDeleteAddress(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);

		final String customerAddressId = request.getParameter(Constants.REQUEST_PARAM_CUSTOMER_ADDRESS_ID);
		
		try {
			webCommerceService.deleteAddressCustomer(request, customerAddressId);
			
		} catch (Exception e) {
			LOG.error("Error with the address to edit, customerAddressId:" + customerAddressId, e);
		}

		final String urlRedirect = urlService.buildCustomerAddressListUrl(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
        return new ModelAndView(new RedirectView(urlRedirect));
	}
	
	@RequestMapping(value = "/customer-add-address-form.html*")
	public ModelAndView customerAddAddressForm(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "customer/customer-add-address-form");
		
		// "customer.add.address";

		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final Customer currentCustomer = requestUtil.getCurrentCustomer(request);

		final CustomerAddressListViewBean customerAdressesViewBean = viewBeanFactory.buildCustomerAddressListViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer, currentCustomer);
		modelAndView.addObject("customerAdresses", customerAdressesViewBean);
		
		formFactory.buildCustomerAddressForm(request, modelAndView);

        return modelAndView;
	}

	@RequestMapping(value = "/customer-add-address.html*", method = RequestMethod.GET)
	public ModelAndView customerAddAddress(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "customer/customer-add-address-form");
		
		// "customer.add.address";

		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final Customer currentCustomer = requestUtil.getCurrentCustomer(request);

		final CustomerAddressListViewBean customerAdressesViewBean = viewBeanFactory.buildCustomerAddressListViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer, currentCustomer);
		modelAndView.addObject("customerAdresses", customerAdressesViewBean);
		
		formFactory.buildCustomerAddressForm(request, modelAndView);

        return modelAndView;
	}

	@RequestMapping(value = "/customer-add-address.html*", method = RequestMethod.POST)
	public ModelAndView customerAddAddress(final HttpServletRequest request, final HttpServletResponse response, @Valid CustomerAddressForm customerAddressForm,
								BindingResult result, ModelMap modelMap) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final Customer currentCustomer = requestUtil.getCurrentCustomer(request);

		// "customer.add.address";
		
		if (result.hasErrors()) {
			ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "customer/customer-add-address-form");

			final CustomerAddressListViewBean customerAdressesViewBean = viewBeanFactory.buildCustomerAddressListViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer, currentCustomer);
			modelAndView.addObject("customerAdresses", customerAdressesViewBean);
			
			return modelAndView;
		}
		
		// Save the new address customer
		webCommerceService.buildAndSaveNewAddressCustomer(request, currentMarket, currentMarketArea, customerAddressForm);
		
		final String urlRedirect = urlService.buildCustomerAddressListUrl(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
        return new ModelAndView(new RedirectView(urlRedirect));
	}
	
	@RequestMapping(value = "/customer-edit-address-form.html*")
	public ModelAndView customerEditAddressForm(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "customer/customer-edit-address-form");
		
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final Customer currentCustomer = requestUtil.getCurrentCustomer(request);

		// "customer.edit.address";

		final CustomerAddressListViewBean customerAdressesViewBean = viewBeanFactory.buildCustomerAddressListViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer, currentCustomer);
		modelAndView.addObject("customerAdresses", customerAdressesViewBean);
		
		final String customerAddressId = request.getParameter(Constants.REQUEST_PARAM_CUSTOMER_ADDRESS_ID);
		CustomerAddress customerAddress = null;
		
		try {
			final Customer customer = requestUtil.getCurrentCustomer(request);
			customerAddress = customer.getAddress(new Long(customerAddressId));
			
		} catch (Exception e) {
			LOG.error("Error with the address to edit, customerAddressId:" + customerAddressId, e);
			
			final String urlRedirect = urlService.buildCustomerAddressListUrl(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
	        return new ModelAndView(new RedirectView(urlRedirect));
		}

		// SANITY CHECK : wrong address id for this customer
		if(customerAddress == null){
			final String urlRedirect = urlService.buildCustomerAddressListUrl(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
	        return new ModelAndView(new RedirectView(urlRedirect));
		}

		formFactory.buildCustomerAddressForm(request, modelAndView, customerAddress);

        return modelAndView;
	}
	
	@RequestMapping(value = "/customer-edit-address.html*", method = RequestMethod.GET)
	public ModelAndView customerEditAddress(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "customer/customer-edit-address-form");
		
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final Customer currentCustomer = requestUtil.getCurrentCustomer(request);

		// "customer.edit.address";

		final CustomerAddressListViewBean customerAdressesViewBean = viewBeanFactory.buildCustomerAddressListViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer, currentCustomer);
		modelAndView.addObject("customerAdresses", customerAdressesViewBean);
		

		final String customerAddressId = request.getParameter(Constants.REQUEST_PARAM_CUSTOMER_ADDRESS_ID);
		CustomerAddress customerAddress = null;
		
		try {
			final Customer customer = requestUtil.getCurrentCustomer(request);
			customerAddress = customer.getAddress(new Long(customerAddressId));
			
		} catch (Exception e) {
			LOG.error("Error with the address to edit, customerAddressId:" + customerAddressId, e);
			
			final String urlRedirect = urlService.buildCustomerAddressListUrl(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
	        return new ModelAndView(new RedirectView(urlRedirect));
		}

		// SANITY CHECK : wrong address id for this customer
		if(customerAddress == null){
			final String urlRedirect = urlService.buildCustomerAddressListUrl(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
	        return new ModelAndView(new RedirectView(urlRedirect));
		}

		formFactory.buildCustomerAddressForm(request, modelAndView, customerAddress);

        return modelAndView;
	}

	@RequestMapping(value = "/customer-edit-address.html*", method = RequestMethod.POST)
	public ModelAndView customerEditAddress(final HttpServletRequest request, final HttpServletResponse response, @Valid CustomerAddressForm customerAddressForm,
								BindingResult result, ModelMap modelMap) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final Customer currentCustomer = requestUtil.getCurrentCustomer(request);

		// "customer.edit.address";
		
		if (result.hasErrors()) {
			ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "customer/customer-edit-address-form");

			final CustomerAddressListViewBean customerAdressesViewBean = viewBeanFactory.buildCustomerAddressListViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer, currentCustomer);
			modelAndView.addObject("customerAdresses", customerAdressesViewBean);
			
			return modelAndView;
		}
		
		// Save the new address customer
		webCommerceService.buildAndSaveNewAddressCustomer(request, currentMarket, currentMarketArea, customerAddressForm);
		
		final String urlRedirect = urlService.buildCustomerAddressListUrl(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
        return new ModelAndView(new RedirectView(urlRedirect));
	}
	
    @ModelAttribute("titles")
    public List<ValueBean> getTitles(HttpServletRequest request) throws Exception {
		List<ValueBean> titlesValues = new ArrayList<ValueBean>();
		try {
			final Locale locale = getCurrentLocale(request);
			final ResourceBundle titlesResourceBundle = ResourceBundle.getBundle(Constants.TITLES_RESOURCE_BUNDLE, locale);
			Set<String> titlesKey = titlesResourceBundle.keySet();
			for (Iterator<String> iterator = titlesKey.iterator(); iterator.hasNext();) {
				final String titleKey = (String) iterator.next();
				titlesValues.add(new ValueBean(titleKey.replace("titles.", ""), (String)titlesResourceBundle.getObject(titleKey)));
			}
			
			Collections.sort(titlesValues, new Comparator<ValueBean>() {
				@Override
				public int compare(ValueBean o1, ValueBean o2) {
					return o1.getValue().compareTo(o2.getValue());
				}
			});
		} catch (Exception e) {
			LOG.error("", e);
		}
		return titlesValues;
    }
    
    @ModelAttribute("countries")
    public List<ValueBean> getCountries(HttpServletRequest request) throws Exception {
		List<ValueBean> countriesValues = new ArrayList<ValueBean>();
		try {
			final Locale locale = getCurrentLocale(request);
			final ResourceBundle countriesResourceBundle = ResourceBundle.getBundle(Constants.COUNTRIES_RESOURCE_BUNDLE, locale);
			Set<String> countriesKey = countriesResourceBundle.keySet();
			
			for (Iterator<String> iterator = countriesKey.iterator(); iterator.hasNext();) {
				final String countryKey = (String) iterator.next();
				countriesValues.add(new ValueBean(countryKey.replace("countries.", ""), (String)countriesResourceBundle.getObject(countryKey)));
			}
			
			Collections.sort(countriesValues, new Comparator<ValueBean>() {
				@Override
				public int compare(ValueBean o1, ValueBean o2) {
					return o1.getValue().compareTo(o2.getValue());
				}
			});
		} catch (Exception e) {
			LOG.error("", e);
		}
		return countriesValues;
    }
    
}
