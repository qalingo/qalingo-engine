/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.CustomerCredential;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.OrderCustomer;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.web.mvc.form.ContactForm;
import org.hoteia.qalingo.web.mvc.form.CreateAccountForm;
import org.hoteia.qalingo.web.mvc.form.CustomerAddressForm;
import org.hoteia.qalingo.web.mvc.form.CustomerEditForm;
import org.hoteia.qalingo.web.mvc.form.ForgottenPasswordForm;
import org.hoteia.qalingo.web.mvc.form.PaymentForm;
import org.hoteia.qalingo.web.mvc.form.ResetPasswordForm;
import org.hoteia.qalingo.web.mvc.form.RetailerContactForm;

public interface WebManagementService {

    void addToCart(RequestData requestData, String catalogCategoryCode, String productSkuCode, int quantity) throws Exception;

    void updateCart(RequestData requestData, String catalogCategoryCode, String productSkuCode, int quantity) throws Exception;

    void updateCart(RequestData requestData, String productSkuCode, int quantity) throws Exception;
    
    void updateCart(RequestData requestData, Customer customer) throws Exception;
    
    void updateCart(RequestData requestData, Long billingAddressId, Long shippingAddressId) throws Exception;

    void deleteCartItem(RequestData requestData, String productSkuCode) throws Exception;

    void cleanCart(RequestData requestData) throws Exception;
    
	CustomerCredential flagCustomerCredentialWithToken(RequestData requestData, Customer customer) throws Exception;

	void cancelCustomerCredentialToken(RequestData requestData, Customer customer) throws Exception;

	void resetCustomerCredential(RequestData requestData, Customer customer, ResetPasswordForm resetPasswordForm) throws Exception;

	Customer buildAndSaveNewCustomer(RequestData requestData, Market market, MarketArea marketMode, CreateAccountForm createAccountForm) throws Exception;
	
	Customer updateCurrentCustomer(RequestData requestData, Market market, MarketArea marketMode, CustomerEditForm customerEditForm) throws Exception;

	Customer activeNewCustomer(RequestData requestData, Customer customer) throws Exception;

	Customer updateOrSaveAddressCustomer(RequestData requestData, Market market, MarketArea marketMode, CustomerAddressForm customerAddressForm) throws Exception;
	
	Customer deleteAddressCustomer(RequestData requestData, String customerAddressId) throws Exception;
	
	Customer addProductSkuToWishlist(RequestData requestData, String skuCode) throws Exception;
	
	Customer removeProductSkuFromWishlist(RequestData requestData, String skuCode) throws Exception;

    void savePaymentInformation(RequestData requestData, PaymentForm paymentForm) throws Exception;

	OrderCustomer buildAndSaveNewOrder(RequestData requestData) throws Exception;
	
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
