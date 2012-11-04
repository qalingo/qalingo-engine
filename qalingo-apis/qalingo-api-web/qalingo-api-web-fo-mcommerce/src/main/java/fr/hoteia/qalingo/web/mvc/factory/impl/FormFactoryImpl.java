/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.factory.impl;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.common.domain.Customer;
import fr.hoteia.qalingo.core.common.domain.CustomerAddress;
import fr.hoteia.qalingo.core.common.domain.CustomerMarketArea;
import fr.hoteia.qalingo.core.common.domain.MarketArea;
import fr.hoteia.qalingo.core.common.service.UrlService;
import fr.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import fr.hoteia.qalingo.core.web.util.RequestUtil;
import fr.hoteia.qalingo.web.mvc.factory.FormFactory;
import fr.hoteia.qalingo.web.mvc.form.CartForm;
import fr.hoteia.qalingo.web.mvc.form.ContactUsForm;
import fr.hoteia.qalingo.web.mvc.form.CreateAccountForm;
import fr.hoteia.qalingo.web.mvc.form.CustomerAddressForm;
import fr.hoteia.qalingo.web.mvc.form.CustomerEditForm;
import fr.hoteia.qalingo.web.mvc.form.FollowUsForm;
import fr.hoteia.qalingo.web.mvc.form.PaymentForm;
import fr.hoteia.qalingo.web.mvc.form.QuickSearchForm;
import fr.hoteia.qalingo.web.mvc.form.SearchForm;

/**
 * 
 */
@Service("formFactory")
public class FormFactoryImpl implements FormFactory {

	@Autowired
	protected CoreMessageSource coreMessageSource;
	
	@Autowired
    protected RequestUtil requestUtil;
	
	@Autowired
    protected UrlService urlService;
	
	public void buildContactUsForm(final HttpServletRequest request, final ModelAndView modelAndView) throws Exception {
		final ContactUsForm contactUsForm = new ContactUsForm();
		String languageCode = requestUtil.getCurrentLocalization(request).getLocaleCode();
		if(languageCode.equals("en")) {
			contactUsForm.setCountry("US");
		} else if(languageCode.equals("fr")) {
			contactUsForm.setCountry("FR");
		}  else if(languageCode.equals("de")) {
			contactUsForm.setCountry("DE");
		}  else if(languageCode.equals("es")) {
			contactUsForm.setCountry("ES");
		}  else if(languageCode.equals("it")) {
			contactUsForm.setCountry("IT");
		}  else if(languageCode.equals("nl")) {
			contactUsForm.setCountry("NL");
		}  else if(languageCode.equals("pt")) {
			contactUsForm.setCountry("PT");
		} 
		
		modelAndView.addObject("contactUsForm", contactUsForm);
	}
	
	public void buildSearchForm(final HttpServletRequest request, final ModelAndView modelAndView) throws Exception {
		final SearchForm searchForm = new SearchForm();
		modelAndView.addObject("searchForm", searchForm);
	}
	
	public void buildQuickSearchForm(final HttpServletRequest request, final ModelAndView modelAndView) throws Exception {
		final QuickSearchForm quickSearchForm = new QuickSearchForm();
		modelAndView.addObject("quickSearchForm", quickSearchForm);
	}
	
	public void buildFollowUsForm(final HttpServletRequest request, final ModelAndView modelAndView) throws Exception {
		final FollowUsForm followUsForm = new FollowUsForm();
		modelAndView.addObject("followUsForm", followUsForm);
	}
	
	public void buildCustomerEditAccountForm(final HttpServletRequest request, final Customer customer, final ModelAndView modelAndView) throws Exception {
		final MarketArea marketMode = requestUtil.getCurrentMarketArea(request);
		final CustomerMarketArea customerMarketContext = customer.getCurrentCustomerMarketArea(marketMode.getCode());
		CustomerEditForm customerEditForm = new CustomerEditForm();
		customerEditForm.setTitle(customer.getTitle());
		customerEditForm.setLastname(customer.getLastname());
		customerEditForm.setFirstname(customer.getFirstname());
		customerEditForm.setEmail(customer.getEmail());

		customerEditForm.setMobile(customerMarketContext.getMobile());
		customerEditForm.setPhone(customerMarketContext.getPhone());
		customerEditForm.setFax(customerMarketContext.getFax());
		customerEditForm.setOptin(customerMarketContext.isOptin());
		
		modelAndView.addObject("customerEditForm", customerEditForm);
	}
	
	public void buildCustomerCreateAccountForm(final HttpServletRequest request, final ModelAndView modelAndView) throws Exception {
		final CreateAccountForm createAccountForm = new CreateAccountForm();
		String languageCode = requestUtil.getCurrentLocalization(request).getLocaleCode();
		if(languageCode.equals("en")) {
			createAccountForm.setCountryCode("US");
		} else if(languageCode.equals("fr")) {
			createAccountForm.setCountryCode("FR");
		}  else if(languageCode.equals("de")) {
			createAccountForm.setCountryCode("DE");
		}  else if(languageCode.equals("es")) {
			createAccountForm.setCountryCode("ES");
		}  else if(languageCode.equals("it")) {
			createAccountForm.setCountryCode("IT");
		}  else if(languageCode.equals("nl")) {
			createAccountForm.setCountryCode("NL");
		}  else if(languageCode.equals("pt")) {
			createAccountForm.setCountryCode("PT");
		} 
		
		modelAndView.addObject("createAccountForm", createAccountForm);
	}
	
	public void buildCustomerAddressForm(final HttpServletRequest request, final ModelAndView modelAndView) throws Exception {
		final CustomerAddressForm customerAddressForm = new CustomerAddressForm();
		String languageCode = requestUtil.getCurrentLocalization(request).getLocaleCode();
		if(languageCode.equals("en")) {
			customerAddressForm.setCountryCode("US");
		} else if(languageCode.equals("fr")) {
			customerAddressForm.setCountryCode("FR");
		}  else if(languageCode.equals("de")) {
			customerAddressForm.setCountryCode("DE");
		}  else if(languageCode.equals("es")) {
			customerAddressForm.setCountryCode("ES");
		}  else if(languageCode.equals("it")) {
			customerAddressForm.setCountryCode("IT");
		}  else if(languageCode.equals("nl")) {
			customerAddressForm.setCountryCode("NL");
		}  else if(languageCode.equals("pt")) {
			customerAddressForm.setCountryCode("PT");
		} 
		
		modelAndView.addObject("customerAddressForm", customerAddressForm);
	}
	
	public void buildCustomerAddressForm(final HttpServletRequest request, final ModelAndView modelAndView, final CustomerAddress customerAddress) throws Exception {
		final CustomerAddressForm customerAddressForm = new CustomerAddressForm();
		
		customerAddressForm.setAddressName(customerAddress.getAddressName());
		customerAddressForm.setTitle(customerAddress.getTitle());
		customerAddressForm.setLastname(customerAddress.getLastname());
		customerAddressForm.setFirstname(customerAddress.getFirstname());
	    
		customerAddressForm.setAddress1(customerAddress.getAddress1());
		customerAddressForm.setAddress2(customerAddress.getAddress2());
		customerAddressForm.setAddressAdditionalInformation(customerAddress.getAddressAdditionalInformation());
		customerAddressForm.setPostalCode(customerAddress.getPostalCode());
		customerAddressForm.setCity(customerAddress.getCity());
		customerAddressForm.setCountyCode(customerAddress.getCountyCode());
		customerAddressForm.setCountryCode(customerAddress.getCountryCode());
	    
		modelAndView.addObject("customerAddressForm", customerAddressForm);
	}
	
	public void buildCartForm(final HttpServletRequest request, final ModelAndView modelAndView) throws Exception {
		final CartForm cartForm = new CartForm();
		Customer customer = requestUtil.getCurrentCustomer(request);
		if(customer != null) {
			 Set<CustomerAddress> addresses = customer.getAddresses();
			 for (Iterator<CustomerAddress> iterator = addresses.iterator(); iterator.hasNext();) {
				CustomerAddress customerAddress = (CustomerAddress) iterator.next();
				if(customerAddress.isDefaultBilling()) {
					cartForm.setBillingAddressId(customerAddress.getId().toString());
				}
				if(customerAddress.isDefaultShipping()) {
					cartForm.setShippingAddressId(customerAddress.getId().toString());
				}
			}
		}
		
		modelAndView.addObject("cartForm", cartForm);
	}
	
	public void buildPaymentForm(final HttpServletRequest request, final ModelAndView modelAndView) throws Exception {
		final PaymentForm paymentForm = new PaymentForm();
		modelAndView.addObject("paymentForm", paymentForm);
	}
}
