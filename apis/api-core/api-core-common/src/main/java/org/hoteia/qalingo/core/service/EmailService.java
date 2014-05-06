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

import java.util.List;

import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.Email;
import org.hoteia.qalingo.core.email.bean.AbandonedShoppingCartEmailBean;
import org.hoteia.qalingo.core.email.bean.ContactEmailBean;
import org.hoteia.qalingo.core.email.bean.CustomerForgottenPasswordEmailBean;
import org.hoteia.qalingo.core.email.bean.CustomerNewAccountConfirmationEmailBean;
import org.hoteia.qalingo.core.email.bean.CustomerResetPasswordConfirmationEmailBean;
import org.hoteia.qalingo.core.email.bean.NewsletterEmailBean;
import org.hoteia.qalingo.core.email.bean.OrderConfirmationEmailBean;
import org.hoteia.qalingo.core.email.bean.OrderSentConfirmationEmailBean;
import org.hoteia.qalingo.core.email.bean.RetailerContactEmailBean;
import org.hoteia.qalingo.core.pojo.RequestData;

public interface EmailService {

	Email getEmailById(Long id, Object... params);

	List<Long> findIdsForEmailSync(String type, Object... params);

	List<Email> findEmailByStatus(String status, Object... params);
	
	void saveOrUpdateEmail(Email email);

	void deleteEmail(Email email);
	
	void buildAndSaveContactMail(RequestData requestData, String VelocityPath, ContactEmailBean contactEmailBean) throws Exception;

	void buildAndSaveRetailerContactMail(RequestData requestData, Customer customer, String VelocityPath, RetailerContactEmailBean retailerCcontactEmailBean) throws Exception;

	void saveAndBuildNewsletterSubscriptionnConfirmationMail(RequestData requestData, String VelocityPath, NewsletterEmailBean newsletterRegistrationConfirmationEmailBean) throws Exception;

	void saveAndBuildNewsletterUnsubscriptionConfirmationMail(RequestData requestData, String VelocityPath, NewsletterEmailBean newsletterRegistrationConfirmationEmailBean) throws Exception;

	void buildAndSaveCustomerNewAccountMail(RequestData requestData, String VelocityPath, CustomerNewAccountConfirmationEmailBean customerNewAccountConfirmationEmailBean) throws Exception;
	
	void buildAndSaveCustomerForgottenPasswordMail(RequestData requestData, Customer customer, String VelocityPath, CustomerForgottenPasswordEmailBean customerForgottenPasswordEmailBean) throws Exception;

	void buildAndSaveCustomerResetPasswordConfirmationMail(RequestData requestData, Customer customer, String VelocityPath, CustomerResetPasswordConfirmationEmailBean customerResetPasswordConfirmationEmailBean) throws Exception;

	void buildAndSaveNewOrderConfirmationMail(RequestData requestData, Customer customer, String VelocityPath, OrderConfirmationEmailBean orderConfirmationEmailBean) throws Exception;
	
	void buildAndSaveOrderShippedConfirmationMail(RequestData requestData, Customer customer, String VelocityPath, OrderSentConfirmationEmailBean orderSentConfirmationEmailBean) throws Exception;
	
	void buildAndSaveAbandonedShoppingCartMail(RequestData requestData, Customer customer, String velocityPath, AbandonedShoppingCartEmailBean abandonedShoppingCartEmailBean) throws Exception;
	
}