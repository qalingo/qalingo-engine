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
import javax.validation.Valid;

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
import org.springframework.web.servlet.view.RedirectView;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.ModelConstants;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.security.util.SecurityUtil;
import fr.hoteia.qalingo.core.service.CustomerService;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import fr.hoteia.qalingo.web.mvc.form.CreateAccountForm;
import fr.hoteia.qalingo.web.mvc.viewbean.ValueBean;
import fr.hoteia.qalingo.web.service.WebCommerceService;

/**
 * 
 */
@Controller("customerCreateAccountController")
public class CustomerCreateAccountController extends AbstractMCommerceController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
    protected CustomerService customerService;
	
	@Autowired
    protected WebCommerceService webCommerceService;
	
	@Autowired
    protected SecurityUtil securityUtil;
	
	@RequestMapping(value = "/customer-create-account.html*", method = RequestMethod.GET)
	public ModelAndView displayCustomerCreateAccount(final HttpServletRequest request, final Model model, @ModelAttribute("createAccountForm") CreateAccountForm createAccountForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "customer/customer-create-account-form");
		
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		
		// SANITY CHECK: Customer logged
		final Customer currentCustomer = requestUtil.getCurrentCustomer(request);
		if(currentCustomer != null){
			final String url = urlService.buildCustomerDetailsUrl(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
			return new ModelAndView(new RedirectView(url));
		}
		
		// "customer.create.account";

		modelAndView.addObject(ModelConstants.URL_BACK, urlService.buildHomeUrl(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer));
		
//		formFactory.buildCustomerCreateAccountForm(request, modelAndView);

        return modelAndView;
	}

	@RequestMapping(value = "/customer-create-account.html*", method = RequestMethod.POST)
	public ModelAndView customerCreateAccount(final HttpServletRequest request, final Model model, @Valid @ModelAttribute("createAccountForm") CreateAccountForm createAccountForm,
								BindingResult result) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);

		// SANITY CHECK: Customer logged
		final Customer currentCustomer = requestUtil.getCurrentCustomer(request);
		if(currentCustomer != null){
			final String url = urlService.buildCustomerDetailsUrl(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
			return new ModelAndView(new RedirectView(url));
		}
		
		// "customer.create.account";
		
		if (result.hasErrors()) {
			return displayCustomerCreateAccount(request, model, createAccountForm);
		}
		
		final String email = createAccountForm.getEmail();
		final Customer customer = customerService.getCustomerByLoginOrEmail(email);
		if(customer != null){
			final String forgottenPasswordUrl = urlService.buildForgottenPasswordUrl(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
			final Object[] objects = {forgottenPasswordUrl};
			result.rejectValue("email", "error.form.create.account.account.already.exist", objects,"This email customer account already exist! Go on this <a href=\"${0}\" alt=\"\">page</a> to get a new password.");
		}

		// Save the new customer
		final Customer newCustomer = webCommerceService.buildAndSaveNewCustomer(request, currentMarket, currentMarketArea, createAccountForm);

		// Save the email confirmation
		webCommerceService.buildAndSaveCustomerNewAccountMail(request, createAccountForm);

		// Login the new customer
		securityUtil.authenticationCustomer(request, newCustomer);
		
		final String urlRedirect = urlService.buildCustomerDetailsUrl(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
        return new ModelAndView(new RedirectView(urlRedirect));
	}
	
	/**
	 * 
	 */
    @ModelAttribute("createAccountForm")
	protected CreateAccountForm getCreateAccountForm(final HttpServletRequest request, final Model model) throws Exception {
    	return formFactory.buildCreateAccountForm(request);
	}
    
    @ModelAttribute("titles")
    public List<ValueBean> getTitles(final HttpServletRequest request) throws Exception {
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
    public List<ValueBean> getCountries(final HttpServletRequest request) throws Exception {
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