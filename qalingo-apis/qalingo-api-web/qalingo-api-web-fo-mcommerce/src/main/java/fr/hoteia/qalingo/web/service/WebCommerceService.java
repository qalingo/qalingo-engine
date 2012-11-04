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

import fr.hoteia.qalingo.core.common.domain.Customer;
import fr.hoteia.qalingo.core.common.domain.Market;
import fr.hoteia.qalingo.core.common.domain.MarketArea;
import fr.hoteia.qalingo.core.common.domain.Order;
import fr.hoteia.qalingo.web.mvc.form.ContactUsForm;
import fr.hoteia.qalingo.web.mvc.form.CreateAccountForm;
import fr.hoteia.qalingo.web.mvc.form.CustomerAddressForm;
import fr.hoteia.qalingo.web.mvc.form.CustomerEditForm;
import fr.hoteia.qalingo.web.mvc.form.FollowUsForm;
import fr.hoteia.qalingo.web.mvc.form.ForgottenPasswordForm;

public interface WebCommerceService {

	Customer buildAndSaveNewCustomer(HttpServletRequest request, Market market, MarketArea marketMode, CreateAccountForm createAccountForm) throws Exception;
	
	Customer updateCurrentCustomer(HttpServletRequest request, Market market, MarketArea marketMode, CustomerEditForm customerEditForm) throws Exception;
	
	Customer buildAndSaveNewAddressCustomer(HttpServletRequest request, Market market, MarketArea marketMode, CustomerAddressForm customerAddressForm) throws Exception;
	
	Customer deleteAddressCustomer(HttpServletRequest request, String customerAddressId) throws Exception;
	
	Customer addProductSkuToWishlist(HttpServletRequest request, String skuCode) throws Exception;
	
	Customer removeProductSkuFromWishlist(HttpServletRequest request, String skuCode) throws Exception;
	
	Order buildAndSaveNewOrder(HttpServletRequest request, Market market, MarketArea marketMode) throws Exception;
	
	void buildAndSaveContactUsMail(HttpServletRequest request, ContactUsForm contactUsForm) throws Exception;
	
	void saveAndBuildNewsletterRegistrationConfirmationMail(HttpServletRequest request, FollowUsForm followUsForm) throws Exception;

	void buildAndSaveCustomerForgottenPasswordMail(HttpServletRequest request, ForgottenPasswordForm forgottenPasswordForm) throws Exception;

	void buildAndSaveCustomerNewAccountMail(HttpServletRequest request, CreateAccountForm createAccountForm) throws Exception;

}
