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

import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.CustomerAddress;
import fr.hoteia.qalingo.core.domain.CustomerMarketArea;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import fr.hoteia.qalingo.core.service.UrlService;
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
	
	public ContactUsForm buildContactUsForm(final HttpServletRequest request) throws Exception {
		final ContactUsForm contactUsForm = new ContactUsForm();
		String languageCode = requestUtil.getCurrentLocalization(request).getCode();
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
		
		return contactUsForm;
	}
	
	public SearchForm buildSearchForm(final HttpServletRequest request) throws Exception {
		final SearchForm searchForm = new SearchForm();
		return searchForm;
	}
	
	public QuickSearchForm buildQuickSearchForm(final HttpServletRequest request) throws Exception {
		final QuickSearchForm quickSearchForm = new QuickSearchForm();
		return quickSearchForm;
	}
	
	public FollowUsForm buildFollowUsForm(final HttpServletRequest request) throws Exception {
		final FollowUsForm followUsForm = new FollowUsForm();
		return followUsForm;
	}
	
	public CreateAccountForm buildCreateAccountForm(final HttpServletRequest request) throws Exception {
		final CreateAccountForm createAccountForm = new CreateAccountForm();
		String languageCode = requestUtil.getCurrentLocalization(request).getCode();
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
		
		return createAccountForm;
	}
	
	public CustomerEditForm buildCustomerEditForm(final HttpServletRequest request, final Customer customer) throws Exception {
		final MarketArea marketArea = requestUtil.getCurrentMarketArea(request);
		CustomerEditForm customerEditForm = new CustomerEditForm();
		if(customer != null){
			customerEditForm.setTitle(customer.getTitle());
			customerEditForm.setLastname(customer.getLastname());
			customerEditForm.setFirstname(customer.getFirstname());
			customerEditForm.setEmail(customer.getEmail());

			final CustomerMarketArea customerMarketContext = customer.getCurrentCustomerMarketArea(marketArea.getCode());
			if(customerMarketContext != null){
				customerEditForm.setMobile(customerMarketContext.getMobile());
				customerEditForm.setPhone(customerMarketContext.getPhone());
				customerEditForm.setFax(customerMarketContext.getFax());
				customerEditForm.setOptin(customerMarketContext.isOptin());
			}
		}
		return customerEditForm;
	}
	
	public CustomerAddressForm buildCustomerAddressForm(final HttpServletRequest request, final CustomerAddress customerAddress) throws Exception {
		final CustomerAddressForm customerAddressForm = new CustomerAddressForm();
		
		String languageCode = requestUtil.getCurrentLocalization(request).getCode();
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
		
		if(customerAddress != null){
			customerAddressForm.setAddressName(customerAddress.getAddressName());
			customerAddressForm.setTitle(customerAddress.getTitle());
			customerAddressForm.setLastname(customerAddress.getLastname());
			customerAddressForm.setFirstname(customerAddress.getFirstname());
		    
			customerAddressForm.setAddress1(customerAddress.getAddress1());
			customerAddressForm.setAddress2(customerAddress.getAddress2());
			customerAddressForm.setAddressAdditionalInformation(customerAddress.getAddressAdditionalInformation());
			customerAddressForm.setPostalCode(customerAddress.getPostalCode());
			customerAddressForm.setCity(customerAddress.getCity());
			customerAddressForm.setStateCode(customerAddress.getStateCode());
			customerAddressForm.setAreaCode(customerAddress.getAreaCode());
			customerAddressForm.setCountryCode(customerAddress.getCountryCode());
		}
	    
		return customerAddressForm;
	}
	
	public CartForm buildCartForm(final HttpServletRequest request) throws Exception {
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
		return cartForm;
	}
	
	public PaymentForm buildPaymentForm(final HttpServletRequest request) throws Exception {
		final PaymentForm paymentForm = new PaymentForm();
		return paymentForm;
	}
}