/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.service.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.CustomerAddress;
import fr.hoteia.qalingo.core.domain.CustomerCredential;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.domain.enumtype.FoUrls;
import fr.hoteia.qalingo.core.email.bean.ContactEmailBean;
import fr.hoteia.qalingo.core.email.bean.CustomerForgottenPasswordEmailBean;
import fr.hoteia.qalingo.core.email.bean.CustomerNewAccountConfirmationEmailBean;
import fr.hoteia.qalingo.core.email.bean.CustomerResetPasswordConfirmationEmailBean;
import fr.hoteia.qalingo.core.email.bean.NewsletterEmailBean;
import fr.hoteia.qalingo.core.email.bean.RetailerContactEmailBean;
import fr.hoteia.qalingo.core.service.pojo.RequestData;
import fr.hoteia.qalingo.web.mvc.form.ContactForm;
import fr.hoteia.qalingo.web.mvc.form.CreateAccountForm;
import fr.hoteia.qalingo.web.mvc.form.CustomerAddressForm;
import fr.hoteia.qalingo.web.mvc.form.CustomerEditForm;
import fr.hoteia.qalingo.web.mvc.form.ForgottenPasswordForm;
import fr.hoteia.qalingo.web.mvc.form.ResetPasswordForm;
import fr.hoteia.qalingo.web.mvc.form.RetailerContactForm;
import fr.hoteia.qalingo.web.service.WebCommerceService;

@Service("webCommerceService")
@Transactional
public class WebCommerceServiceImpl extends AbstractWebCommerceServiceImpl implements WebCommerceService {
	
    /**
     * 
     */
	public void resetCustomerCredential(final HttpServletRequest request, final RequestData requestData, final Customer customer, final ResetPasswordForm resetPasswordForm) throws Exception {
		super.resetCustomerCredential(request, requestData, customer, resetPasswordForm.getNewPassword());
	}
	
    /**
     * 
     */
	public Customer buildAndSaveNewCustomer(final HttpServletRequest request, final RequestData requestData, final Market market, final MarketArea marketArea, 
											final CreateAccountForm createAccountForm) throws Exception {
		Customer customer = new Customer();
		
		customer.setLogin(createAccountForm.getEmail());
		customer.setFirstname(createAccountForm.getFirstname());
		customer.setLastname(createAccountForm.getLastname());
		customer.setPassword(securityUtil.encodePassword(createAccountForm.getPassword()));
		
		customer.setEmail(createAccountForm.getEmail());

		CustomerAddress defaultAddress = new CustomerAddress();
		if(StringUtils.isNotEmpty(createAccountForm.getAddressName())){
			defaultAddress.setAddressName(createAccountForm.getAddressName());
		} else {
			defaultAddress.setAddressName(createAccountForm.getAddress1() + "(" + createAccountForm.getCity() + ")");
		}
		defaultAddress.setTitle(createAccountForm.getTitle());
		defaultAddress.setLastname(createAccountForm.getLastname());
		defaultAddress.setFirstname(createAccountForm.getFirstname());
		defaultAddress.setAddress1(createAccountForm.getAddress1());
		defaultAddress.setAddress2(createAccountForm.getAddress2());
		defaultAddress.setAddressAdditionalInformation(createAccountForm.getAddressAdditionalInformation());
		defaultAddress.setPostalCode(createAccountForm.getPostalCode());
		defaultAddress.setCity(createAccountForm.getCity());
		defaultAddress.setStateCode(createAccountForm.getStateCode());
		defaultAddress.setCountryCode(createAccountForm.getCountryCode());
		defaultAddress.setDefaultBilling(true);
		defaultAddress.setDefaultShipping(true);
		Set<CustomerAddress> addresses = new HashSet<CustomerAddress>();
		addresses.add(defaultAddress);
		customer.setAddresses(addresses);
		
		return super.buildAndSaveNewCustomer(request, requestData, market, marketArea, customer);
	}
	
	public Customer updateCurrentCustomer(final HttpServletRequest request, final RequestData requestData, final Market market, final MarketArea marketArea, final CustomerEditForm customerEditForm) throws Exception {
		String customerLogin = requestUtil.getCurrentCustomerLogin(request);
		Customer customer = customerService.getCustomerByLoginOrEmail(customerLogin);
		
		customer.setLogin(customerEditForm.getEmail());
		customer.setFirstname(customerEditForm.getFirstname());
		customer.setLastname(customerEditForm.getLastname());
		
		return super.updateCurrentCustomer(request, requestData, customer);
	}
	
	public Customer activeNewCustomer(HttpServletRequest request, RequestData requestData, Customer customer) throws Exception {
		customer.setValidated(true);
		return super.updateCurrentCustomer(request, requestData, customer);
	}
	
	public Customer updateOrSaveAddressCustomer(final HttpServletRequest request, final RequestData requestData, final Market market, final MarketArea marketArea, final CustomerAddressForm customerAddressForm) throws Exception {
		String customerLogin = requestUtil.getCurrentCustomerLogin(request);
		Customer customer = customerService.getCustomerByLoginOrEmail(customerLogin);
		
		CustomerAddress customerAddress = new CustomerAddress();
		String addressId = customerAddressForm.getIdOrGuid();
		if(StringUtils.isNotEmpty(addressId)){
			customerAddress = customer.getAddress(new Long(addressId));
		}
		
		if(StringUtils.isNotEmpty(customerAddressForm.getAddressName())){
			customerAddress.setAddressName(customerAddressForm.getAddressName());
		} else {
			customerAddress.setAddressName(customerAddressForm.getAddress1() + "(" + customerAddressForm.getCity() + ")");
		}
		customerAddress.setAddressName(customerAddressForm.getAddressName());
		customerAddress.setTitle(customerAddressForm.getTitle());
		customerAddress.setLastname(customerAddressForm.getLastname());
		customerAddress.setFirstname(customerAddressForm.getFirstname());
		customerAddress.setAddress1(customerAddressForm.getAddress1());
		customerAddress.setAddress2(customerAddressForm.getAddress2());
		customerAddress.setAddressAdditionalInformation(customerAddressForm.getAddressAdditionalInformation());
		customerAddress.setPostalCode(customerAddressForm.getPostalCode());
		customerAddress.setCity(customerAddressForm.getCity());
		customerAddress.setStateCode(customerAddressForm.getStateCode());
		customerAddress.setCountryCode(customerAddressForm.getCountryCode());
		customerAddress.setDefaultBilling(true);
		customerAddress.setDefaultShipping(true);
		
		if(StringUtils.isEmpty(addressId)){
			customer.getAddresses().add(customerAddress);
		}
		
		return super.updateOrSaveAddressCustomer(request, requestData, market, marketArea, customer);
	}
	
	public Customer deleteAddressCustomer(final HttpServletRequest request, final RequestData requestData, final String customerAddressId) throws Exception {
		String customerLogin = requestUtil.getCurrentCustomerLogin(request);
		Customer customer = customerService.getCustomerByLoginOrEmail(customerLogin);
		CustomerAddress customerAddress = customer.getAddress(new Long(customerAddressId));
		
		// SANITY CHECK : wrong address id for this customer
		if(customerAddress == null){
			throw new Exception(""); 
		}
		
		Set<CustomerAddress> customerAddresses = customer.getAddresses();
		
		// SANITY CHECK : min address equal zero
		if(customerAddresses.size() < 2){
			throw new Exception(""); 
		}
		
		for (Iterator<CustomerAddress> iterator = customerAddresses.iterator(); iterator.hasNext();) {
			CustomerAddress customerAddressIterator = (CustomerAddress) iterator.next();
			if(customerAddressIterator.getId().equals(new Long(customerAddressId))){
				customerAddress = customerAddressIterator;
			}
		}
		customer.getAddresses().remove(customerAddress);
		
		return super.deleteAddressCustomer(request, requestData, customerAddressId);
	}
	
	@Override
	public Customer saveNewsletterSubscriptionAndSendEmail(final RequestData requestData, final String email) throws Exception {
		Customer customer = super.saveNewsletterSubscription(requestData, email);
		saveAndBuildNewsletterSubscriptionConfirmationMail(requestData, email);
		return customer;
    }
	
	@Override
	public Customer saveNewsletterUnsubscriptionAndSendEmail(final RequestData requestData, final String email) throws Exception {
		Customer customer = super.saveNewsletterUnsubscription(requestData, email);
		saveAndBuildNewsletterUnsubscriptionConfirmationMail(requestData, email);
		return customer;
    }
	
    /**
     * 
     */
	public void buildAndSaveContactMail(final RequestData requestData, final ContactForm contactForm) throws Exception {
		final MarketArea marketArea = requestData.getMarketArea();
		final String contextNameValue = requestData.getContextNameValue();

		final ContactEmailBean contactEmailBean = new ContactEmailBean();
		BeanUtils.copyProperties(contactForm, contactEmailBean);
		contactEmailBean.setFromEmail(marketArea.getEmailFrom(contextNameValue));
		contactEmailBean.setToEmail(marketArea.getEmailContact(contextNameValue));
		contactEmailBean.setReplyToEmail(marketArea.getEmailFrom(contextNameValue));
		
		super.buildAndSaveContactMail(requestData, contactEmailBean);
	}
	
    /**
     * 
     */
	public void buildAndSaveRetailerContactMail(final RequestData requestData, final RetailerContactForm retailerContactForm) throws Exception {
		final MarketArea marketArea = requestData.getMarketArea();
		final Retailer retailer = requestData.getRetailer();
		final String contextNameValue = requestData.getContextNameValue();

		final Retailer retailerToContact = retailerService.getRetailerByCode(marketArea.getId(), retailer.getId(), retailerContactForm.getRetailerCode());
		
		final RetailerContactEmailBean retailerContactEmailBean = new RetailerContactEmailBean();
		BeanUtils.copyProperties(retailerContactForm, retailerContactEmailBean);
		retailerContactEmailBean.setFromEmail(marketArea.getEmailFrom(contextNameValue));
		retailerContactEmailBean.setReplyToEmail(marketArea.getEmailFrom(contextNameValue));
		retailerContactEmailBean.setToEmail(retailerToContact.getDefaultAddress().getEmail());
		
		super.buildAndSaveRetailerContactMail(requestData, retailerToContact, retailerContactEmailBean);
	}
	
    /**
     * 
     */
	public void saveAndBuildNewsletterSubscriptionConfirmationMail(final RequestData requestData, final String email) throws Exception {
		final MarketArea marketArea = requestData.getMarketArea();
		final String contextNameValue = requestData.getContextNameValue();

		final NewsletterEmailBean newsletterEmailBean = new NewsletterEmailBean();
		newsletterEmailBean.setFromEmail(marketArea.getEmailFrom(contextNameValue));
		newsletterEmailBean.setReplyToEmail(marketArea.getEmailFrom(contextNameValue));
		newsletterEmailBean.setToEmail(email);
		
		super.saveAndBuildNewsletterSubscriptionConfirmationMail(requestData, newsletterEmailBean);
	}
	
    /**
     * 
     */
	public void saveAndBuildNewsletterUnsubscriptionConfirmationMail(final RequestData requestData, final String email) throws Exception {
		final MarketArea marketArea = requestData.getMarketArea();
		final String contextNameValue = requestData.getContextNameValue();

		final NewsletterEmailBean newsletterEmailBean = new NewsletterEmailBean();
		newsletterEmailBean.setFromEmail(marketArea.getEmailFrom(contextNameValue));
		newsletterEmailBean.setReplyToEmail(marketArea.getEmailFrom(contextNameValue));
		newsletterEmailBean.setToEmail(email);
		
		super.saveAndBuildNewsletterUnsubscriptionConfirmationMail(requestData, newsletterEmailBean);
	}
	
    /**
     * 
     */
	public void buildAndSaveCustomerNewAccountMail(final RequestData requestData, final CreateAccountForm createAccountForm) throws Exception {
		final MarketArea marketArea = requestData.getMarketArea();
		final String contextNameValue = requestData.getContextNameValue();

		final CustomerNewAccountConfirmationEmailBean customerNewAccountConfirmationEmailBean = new CustomerNewAccountConfirmationEmailBean();
		BeanUtils.copyProperties(createAccountForm, customerNewAccountConfirmationEmailBean);
		customerNewAccountConfirmationEmailBean.setFromEmail(marketArea.getEmailFrom(contextNameValue));
		customerNewAccountConfirmationEmailBean.setReplyToEmail(marketArea.getEmailFrom(contextNameValue));
		customerNewAccountConfirmationEmailBean.setToEmail(createAccountForm.getEmail());

		customerNewAccountConfirmationEmailBean.setTitle(createAccountForm.getTitle());
		customerNewAccountConfirmationEmailBean.setFirstname(createAccountForm.getFirstname());
		customerNewAccountConfirmationEmailBean.setLastname(createAccountForm.getLastname());
		customerNewAccountConfirmationEmailBean.setEmail(createAccountForm.getEmail());
		
		customerNewAccountConfirmationEmailBean.setCustomerDetailsUrl(urlService.buildAbsoluteUrl(requestData, urlService.generateUrl(FoUrls.PERSONAL_DETAILS, requestData)));
		
		super.buildAndSaveCustomerNewAccountMail(requestData, customerNewAccountConfirmationEmailBean);
	}
	
    /**
     * 
     */
	public void buildAndSaveCustomerForgottenPasswordMail(final RequestData requestData, final Customer customer, final CustomerCredential customerCredential, final ForgottenPasswordForm forgottenPasswordForm) throws Exception {
		final MarketArea marketArea = requestData.getMarketArea();
		final String contextNameValue = requestData.getContextNameValue();

		final CustomerForgottenPasswordEmailBean customerForgottenPasswordEmailBean = new CustomerForgottenPasswordEmailBean();
		BeanUtils.copyProperties(forgottenPasswordForm, customerForgottenPasswordEmailBean);
		customerForgottenPasswordEmailBean.setFromEmail(marketArea.getEmailFrom(contextNameValue));
		customerForgottenPasswordEmailBean.setReplyToEmail(marketArea.getEmailFrom(contextNameValue));
		customerForgottenPasswordEmailBean.setToEmail(customer.getEmail());
		customerForgottenPasswordEmailBean.setToken(customerCredential.getResetToken());
		
		customerForgottenPasswordEmailBean.setTitle(customer.getTitle());
		customerForgottenPasswordEmailBean.setFirstname(customer.getFirstname());
		customerForgottenPasswordEmailBean.setLastname(customer.getLastname());
		customerForgottenPasswordEmailBean.setEmail(customer.getEmail());
		
		super.buildAndSaveCustomerForgottenPasswordMail(requestData, customer, customerCredential, customerForgottenPasswordEmailBean);
	}
	
    /**
     * 
     */
	public void buildAndSaveCustomerResetPasswordConfirmationMail(final RequestData requestData, final Customer customer) throws Exception {
		final MarketArea marketArea = requestData.getMarketArea();
		final String contextNameValue = requestData.getContextNameValue();

		final CustomerResetPasswordConfirmationEmailBean customerResetPasswordConfirmationEmailBean = new CustomerResetPasswordConfirmationEmailBean();
		customerResetPasswordConfirmationEmailBean.setFromEmail(marketArea.getEmailFrom(contextNameValue));
		customerResetPasswordConfirmationEmailBean.setReplyToEmail(marketArea.getEmailFrom(contextNameValue));
		customerResetPasswordConfirmationEmailBean.setToEmail(customer.getEmail());
		
		customerResetPasswordConfirmationEmailBean.setTitle(customer.getTitle());
		customerResetPasswordConfirmationEmailBean.setFirstname(customer.getFirstname());
		customerResetPasswordConfirmationEmailBean.setLastname(customer.getLastname());
		customerResetPasswordConfirmationEmailBean.setEmail(customer.getEmail());
		
		customerResetPasswordConfirmationEmailBean.setCustomerDetailsUrl(urlService.buildAbsoluteUrl(requestData, urlService.generateUrl(FoUrls.PERSONAL_DETAILS, requestData)));
		
		super.buildAndSaveCustomerResetPasswordConfirmationMail(requestData, customer);
	}

}