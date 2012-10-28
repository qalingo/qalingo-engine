/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.service;

import java.util.List;

import fr.hoteia.qalingo.core.common.domain.Customer;
import fr.hoteia.qalingo.core.common.domain.Email;
import fr.hoteia.qalingo.core.common.domain.Localization;
import fr.hoteia.qalingo.core.email.bean.ContactUsEmailBean;
import fr.hoteia.qalingo.core.email.bean.CustomerForgottenPasswordEmailBean;
import fr.hoteia.qalingo.core.email.bean.CustomerNewAccountConfirmationEmailBean;
import fr.hoteia.qalingo.core.email.bean.OrderConfirmationEmailBean;
import fr.hoteia.qalingo.core.email.bean.OrderSentConfirmationEmailBean;
import fr.hoteia.qalingo.core.email.bean.NewsletterRegistrationConfirmationEmailBean;

public interface EmailService {

	Email getEmailById(Long id);

	List<Long> findIdsForEmailSync();

	List<Email> findEmailByStatus(String status);
	
	void saveOrUpdateEmail(Email email);

	void deleteEmail(Email email);
	
	void buildAndSaveContactUsMail(Localization localization, Customer customer, String VelocityPath, ContactUsEmailBean contactUsEmailBean);
	
	void saveAndBuildNewsletterRegistrationConfirmationMail(Localization localization, Customer customer, String VelocityPath, NewsletterRegistrationConfirmationEmailBean newsletterRegistrationConfirmationEmailBean);
	
	void buildAndSaveCustomerNewAccountMail(Localization localization, Customer customer, String VelocityPath, CustomerNewAccountConfirmationEmailBean customerNewAccountConfirmationEmailBean);
	
	void buildAndSaveCustomerForgottenPasswordMail(Localization localization, Customer customer, String VelocityPath, CustomerForgottenPasswordEmailBean customerForgottenPasswordEmailBean);
	
	void buildAndSaveNewOrderConfirmationMail(Localization localization, Customer customer, String VelocityPath, OrderConfirmationEmailBean orderConfirmationEmailBean);
	
	void buildAndSaveOrderSentConfirmationMail(Localization localization, Customer customer, String VelocityPath, OrderSentConfirmationEmailBean orderSentConfirmationEmailBean);
	
}
