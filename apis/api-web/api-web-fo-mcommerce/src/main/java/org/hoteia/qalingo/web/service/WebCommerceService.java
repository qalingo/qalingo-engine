/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.service;

import javax.servlet.http.HttpServletRequest;

import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.CustomerCredential;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.Order;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.web.mvc.form.ContactForm;
import org.hoteia.qalingo.web.mvc.form.CreateAccountForm;
import org.hoteia.qalingo.web.mvc.form.CustomerAddressForm;
import org.hoteia.qalingo.web.mvc.form.CustomerEditForm;
import org.hoteia.qalingo.web.mvc.form.ForgottenPasswordForm;
import org.hoteia.qalingo.web.mvc.form.ResetPasswordForm;
import org.hoteia.qalingo.web.mvc.form.RetailerContactForm;

public interface WebCommerceService {

	CustomerCredential flagCustomerCredentialWithToken(HttpServletRequest request, RequestData requestData, Customer customer) throws Exception;

	void cancelCustomerCredentialToken(HttpServletRequest request, RequestData requestData, Customer customer) throws Exception;

	void resetCustomerCredential(HttpServletRequest request, RequestData requestData, Customer customer, ResetPasswordForm resetPasswordForm) throws Exception;

	Customer buildAndSaveNewCustomer(HttpServletRequest request, RequestData requestData, Market market, MarketArea marketMode, CreateAccountForm createAccountForm) throws Exception;
	
	Customer updateCurrentCustomer(HttpServletRequest request, RequestData requestData, Market market, MarketArea marketMode, CustomerEditForm customerEditForm) throws Exception;

	Customer activeNewCustomer(HttpServletRequest request, RequestData requestData, Customer customer) throws Exception;

	Customer updateOrSaveAddressCustomer(HttpServletRequest request, RequestData requestData, Market market, MarketArea marketMode, CustomerAddressForm customerAddressForm) throws Exception;
	
	Customer deleteAddressCustomer(HttpServletRequest request, RequestData requestData, String customerAddressId) throws Exception;
	
	Customer addProductSkuToWishlist(HttpServletRequest request, RequestData requestData, String skuCode) throws Exception;
	
	Customer removeProductSkuFromWishlist(HttpServletRequest request, RequestData requestData, String skuCode) throws Exception;
	
	Order buildAndSaveNewOrder(HttpServletRequest request, RequestData requestData, Market market, MarketArea marketMode) throws Exception;
	
	Customer saveNewsletterSubscriptionAndSendEmail(RequestData requestData, String email) throws Exception;

	Customer saveNewsletterUnsubscriptionAndSendEmail(RequestData requestData, String email) throws Exception;

	void buildAndSaveContactMail(RequestData requestData, ContactForm contactForm) throws Exception;

	void buildAndSaveRetailerContactMail(RequestData requestData, RetailerContactForm retailerContactForm) throws Exception;

	void saveAndBuildNewsletterSubscriptionConfirmationMail(RequestData requestData, String email) throws Exception;

	void saveAndBuildNewsletterUnsubscriptionConfirmationMail(RequestData requestData, String email) throws Exception;

	void buildAndSaveCustomerNewAccountMail(RequestData requestData, CreateAccountForm createAccountForm) throws Exception;

	void buildAndSaveCustomerForgottenPasswordMail(RequestData requestData, Customer customer, CustomerCredential customerCredential, ForgottenPasswordForm forgottenPasswordForm) throws Exception;

	void buildAndSaveCustomerResetPasswordConfirmationMail(RequestData requestData, Customer customer) throws Exception;

}
