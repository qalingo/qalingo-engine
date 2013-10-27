/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.service;

import java.util.List;

import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.Email;
import fr.hoteia.qalingo.core.email.bean.AbandonedShoppingCartEmailBean;
import fr.hoteia.qalingo.core.email.bean.ContactEmailBean;
import fr.hoteia.qalingo.core.email.bean.CustomerForgottenPasswordEmailBean;
import fr.hoteia.qalingo.core.email.bean.CustomerNewAccountConfirmationEmailBean;
import fr.hoteia.qalingo.core.email.bean.CustomerResetPasswordConfirmationEmailBean;
import fr.hoteia.qalingo.core.email.bean.NewsletterEmailBean;
import fr.hoteia.qalingo.core.email.bean.OrderConfirmationEmailBean;
import fr.hoteia.qalingo.core.email.bean.OrderSentConfirmationEmailBean;
import fr.hoteia.qalingo.core.email.bean.RetailerContactEmailBean;
import fr.hoteia.qalingo.core.pojo.RequestData;

public interface EmailService {

	Email getEmailById(Long id);

	List<Long> findIdsForEmailSync(String type);

	List<Email> findEmailByStatus(String status);
	
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