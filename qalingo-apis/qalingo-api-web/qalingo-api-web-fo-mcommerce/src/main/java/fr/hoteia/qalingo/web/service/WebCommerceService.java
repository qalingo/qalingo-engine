/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.service;

import javax.servlet.http.HttpServletRequest;

import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.CustomerCredential;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.Order;
import fr.hoteia.qalingo.web.mvc.form.ContactForm;
import fr.hoteia.qalingo.web.mvc.form.CreateAccountForm;
import fr.hoteia.qalingo.web.mvc.form.CustomerAddressForm;
import fr.hoteia.qalingo.web.mvc.form.CustomerEditForm;
import fr.hoteia.qalingo.web.mvc.form.FollowUsForm;
import fr.hoteia.qalingo.web.mvc.form.ForgottenPasswordForm;
import fr.hoteia.qalingo.web.mvc.form.ResetPasswordForm;
import fr.hoteia.qalingo.web.mvc.form.RetailerContactForm;

public interface WebCommerceService {

	CustomerCredential flagCustomeCredentialWithToken(HttpServletRequest request, Customer customer) throws Exception;
	
	void resetCustomeCredential(HttpServletRequest request, Customer customer, ResetPasswordForm resetPasswordForm) throws Exception;

	Customer buildAndSaveNewCustomer(HttpServletRequest request, Market market, MarketArea marketMode, CreateAccountForm createAccountForm) throws Exception;
	
	Customer updateCurrentCustomer(HttpServletRequest request, Market market, MarketArea marketMode, CustomerEditForm customerEditForm) throws Exception;
	
	Customer updateOrSaveAddressCustomer(HttpServletRequest request, Market market, MarketArea marketMode, CustomerAddressForm customerAddressForm) throws Exception;
	
	Customer deleteAddressCustomer(HttpServletRequest request, String customerAddressId) throws Exception;
	
	Customer addProductSkuToWishlist(HttpServletRequest request, String skuCode) throws Exception;
	
	Customer removeProductSkuFromWishlist(HttpServletRequest request, String skuCode) throws Exception;
	
	Order buildAndSaveNewOrder(HttpServletRequest request, Market market, MarketArea marketMode) throws Exception;
	
	void buildAndSaveContactMail(HttpServletRequest request, ContactForm contactForm) throws Exception;

	void buildAndSaveRetailerContactMail(HttpServletRequest request, RetailerContactForm retailerContactForm) throws Exception;

	void saveAndBuildNewsletterRegistrationConfirmationMail(HttpServletRequest request, FollowUsForm followUsForm) throws Exception;

	void buildAndSaveCustomerNewAccountMail(HttpServletRequest request, CreateAccountForm createAccountForm) throws Exception;

	void buildAndSaveCustomerForgottenPasswordMail(HttpServletRequest request, Customer customer, CustomerCredential customerCredential, ForgottenPasswordForm forgottenPasswordForm) throws Exception;

	void buildAndSaveCustomerResetPasswordConfirmationMail(HttpServletRequest request, Customer customer) throws Exception;

}
