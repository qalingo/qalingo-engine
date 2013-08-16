/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.service.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;

import fr.hoteia.qalingo.core.dao.EmailDao;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.Email;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.email.bean.ContactUsEmailBean;
import fr.hoteia.qalingo.core.email.bean.CustomerForgottenPasswordEmailBean;
import fr.hoteia.qalingo.core.email.bean.CustomerNewAccountConfirmationEmailBean;
import fr.hoteia.qalingo.core.email.bean.NewsletterRegistrationConfirmationEmailBean;
import fr.hoteia.qalingo.core.email.bean.OrderConfirmationEmailBean;
import fr.hoteia.qalingo.core.email.bean.OrderSentConfirmationEmailBean;
import fr.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import fr.hoteia.qalingo.core.service.EmailService;
import fr.hoteia.qalingo.core.util.impl.MimeMessagePreparatorImpl;

@Service("emailService")
@Transactional
public class EmailServiceImpl implements EmailService {

    private static final Logger LOG = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
    private EmailDao emailDao;

	@Autowired
    private VelocityEngine velocityEngine;
    
	@Autowired
	protected CoreMessageSource coreMessageSource;
	
	public Email getEmailById(Long id) {
		return emailDao.getEmailById(id);
	}

	public List<Email> findEmailByStatus(String status) {
		return emailDao.findEmailByStatus(status);
	}
	
	public List<Long> findIdsForEmailSync(String type) {
		return emailDao.findIdsForEmailSync(type);
	}
	
	public void saveOrUpdateEmail(Email email) {
		emailDao.saveOrUpdateEmail(email);
	}
	
	public void saveOrUpdateEmail(Email email, final MimeMessagePreparatorImpl mimeMessagePreparator) throws IOException {
		emailDao.saveEmail(email, mimeMessagePreparator);
	}
	
	public void deleteEmail(Email email) {
		emailDao.deleteEmail(email);
	}
	
    /**
     * @see fr.hoteia.qalingo.core.service.EmailService#buildAndSaveContactMail(Localization localization, Customer customer, String velocityPath, ContactUsEmailBean contactUsEmailBean)
     */
    public void buildAndSaveContactMail(final Localization localization, final Customer customer, final String velocityPath, final ContactUsEmailBean contactUsEmailBean) {
        try {
        	final Locale locale = localization.getLocale();
        	
        	Map<String, Object> model = new HashMap<String, Object>();
        	String fromEmail = contactUsEmailBean.getFromEmail();
        	String toEmail = contactUsEmailBean.getToEmail();
          
        	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        	java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
        	model.put("currentDate", dateFormatter.format(currentDate));
        	model.put("customer", customer);
        	model.put("contactUsEmailBean", contactUsEmailBean);

        	MimeMessagePreparatorImpl mimeMessagePreparator = new MimeMessagePreparatorImpl();
        	mimeMessagePreparator.setTo(toEmail);
        	mimeMessagePreparator.setFrom(fromEmail);
        	mimeMessagePreparator.setReplyTo(fromEmail);
        	Object[] parameters = {contactUsEmailBean.getLastname(), contactUsEmailBean.getFirstname()};
        	mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.contact.email_subject", parameters, locale));
        	mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "contact-html-content.vm", model));
        	mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "contact-text-content.vm", model));
        	
        	Email email = new Email();
        	email.setType(Email.EMAIl_TYPE_CONTACT);
        	email.setStatus(Email.EMAIl_STATUS_PENDING);
        	saveOrUpdateEmail(email, mimeMessagePreparator);
        	
        } catch (MailException e) {
        	LOG.error("Error, can't save the message :", e);
        } catch (VelocityException e) {
        	LOG.error("Error, can't build the message :", e);
        } catch (IOException e) {
        	LOG.error("Error, can't serializable the message :", e);
        }
    }
    
    /**
     * @see fr.hoteia.qalingo.core.service.EmailService#saveAndBuildNewsletterRegistrationConfirmationMail(Localization localization, Customer customer, String velocityPath, NewsletterRegistrationConfirmationEmailBean newsletterRegistrationConfirmationEmailBean)
     */
    public void saveAndBuildNewsletterRegistrationConfirmationMail(final Localization localization, final Customer customer, final String velocityPath, final NewsletterRegistrationConfirmationEmailBean newsletterRegistrationConfirmationEmailBean) {
        try {
        	final Locale locale = localization.getLocale();
        	
        	Map<String, Object> model = new HashMap<String, Object>();
          
        	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        	java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
        	model.put("currentDate", dateFormatter.format(currentDate));
        	model.put("customer", customer);
        	model.put("newsletterRegistrationConfirmationEmailBean", newsletterRegistrationConfirmationEmailBean);

        	String fromEmail = newsletterRegistrationConfirmationEmailBean.getFromEmail();
        	MimeMessagePreparatorImpl mimeMessagePreparator = new MimeMessagePreparatorImpl();
        	mimeMessagePreparator.setTo(customer.getEmail());
        	mimeMessagePreparator.setFrom(fromEmail);
        	mimeMessagePreparator.setReplyTo(fromEmail);
        	Object[] parameters = {customer.getLastname(), customer.getFirstname()};
        	mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.newsletter.registration_confirmation_email_subject", parameters, locale));
        	mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "newsletter-registration-confirmation-html-content.vm", model));
        	mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "newsletter-registration-confirmation-text-content.vm", model));
        	
        	Email email = new Email();
        	email.setType(Email.EMAIl_TYPE_NEWSLETTER_REGISTRATION_CONFIRMATION);
        	email.setStatus(Email.EMAIl_STATUS_PENDING);
        	saveOrUpdateEmail(email, mimeMessagePreparator);
        	
        } catch (MailException e) {
        	LOG.error("Error, can't save the message :", e);
        } catch (VelocityException e) {
        	LOG.error("Error, can't build the message :", e);
        } catch (IOException e) {
        	LOG.error("Error, can't serializable the message :", e);
        }
    }
    
    /**
     * @see fr.hoteia.qalingo.core.service.EmailService#buildAndSaveCustomerNewAccountMail(Localization localization, Customer customer, String velocityPath, CustomerNewAccountConfirmationEmailBean customerNewAccountConfirmationEmailBean)
     */
    public void buildAndSaveCustomerNewAccountMail(final Localization localization, final Customer customer, final String velocityPath, final CustomerNewAccountConfirmationEmailBean customerNewAccountConfirmationEmailBean) {
        try {
        	final Locale locale = localization.getLocale();
        	
        	Map<String, Object> model = new HashMap<String, Object>();
          
        	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        	java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
        	model.put("currentDate", dateFormatter.format(currentDate));
        	model.put("customer", customer);
        	model.put("customerNewAccountConfirmationEmailBean", customerNewAccountConfirmationEmailBean);

        	String fromEmail = customerNewAccountConfirmationEmailBean.getFromEmail();
        	MimeMessagePreparatorImpl mimeMessagePreparator = new MimeMessagePreparatorImpl();
        	mimeMessagePreparator.setTo(customer.getEmail());
        	mimeMessagePreparator.setFrom(fromEmail);
        	mimeMessagePreparator.setReplyTo(fromEmail);
        	Object[] parameters = {customer.getLastname(), customer.getFirstname()};
        	mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.customer.new_account_confirmation_email_subject", parameters, locale));
        	mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "new-account-confirmation-html-content.vm", model));
        	mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "new-account-confirmation-text-content.vm", model));
        	
        	Email email = new Email();
        	email.setType(Email.EMAIl_TYPE_NEW_ACCOUNT_CONFIRMATION);
        	saveOrUpdateEmail(email, mimeMessagePreparator);
        	
        } catch (MailException e) {
        	LOG.error("Error, can't save the message :", e);
        } catch (VelocityException e) {
        	LOG.error("Error, can't build the message :", e);
        } catch (IOException e) {
        	LOG.error("Error, can't serializable the message :", e);
        }
    }
    
    /**
     * @see fr.hoteia.qalingo.core.service.EmailService#buildAndSaveCustomerForgottenPasswordMail(Localization localization, Customer customer, String velocityPath, CustomerForgottenPasswordEmailBean customerForgottenPasswordEmailBean)
     */
    public void buildAndSaveCustomerForgottenPasswordMail(final Localization localization, final Customer customer, final String velocityPath, final CustomerForgottenPasswordEmailBean customerForgottenPasswordEmailBean) {
        try {
        	final Locale locale = localization.getLocale();
        	
        	Map<String, Object> model = new HashMap<String, Object>();
          
        	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        	java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
        	model.put("currentDate", dateFormatter.format(currentDate));
        	model.put("customer", customer);
        	model.put("customerForgottenPasswordEmailBean", customerForgottenPasswordEmailBean);

        	String fromEmail = customerForgottenPasswordEmailBean.getFromEmail();
        	MimeMessagePreparatorImpl mimeMessagePreparator = new MimeMessagePreparatorImpl();
        	mimeMessagePreparator.setTo(customer.getEmail());
        	mimeMessagePreparator.setFrom(fromEmail);
        	mimeMessagePreparator.setReplyTo(fromEmail);
        	Object[] parameters = {customer.getLastname(), customer.getFirstname()};
        	mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.customer.forgotten_password_email_subject", parameters, locale));
        	mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "forgotten-password-html-content.vm", model));
        	mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "forgotten-password-text-content.vm", model));
        	
        	Email email = new Email();
        	email.setType(Email.EMAIl_TYPE_FORGOTTEN_PASSWORD);
        	email.setStatus(Email.EMAIl_STATUS_PENDING);
        	saveOrUpdateEmail(email, mimeMessagePreparator);
        	
        } catch (MailException e) {
        	LOG.error("Error, can't save the message :", e);
        } catch (VelocityException e) {
        	LOG.error("Error, can't build the message :", e);
        } catch (IOException e) {
        	LOG.error("Error, can't serializable the message :", e);
        }
    }
    
    /**
     * @see fr.hoteia.qalingo.core.service.EmailService#buildAndSaveNewOrderConfirmationMail(Localization localization, Customer customer, String velocityPath, OrderConfirmationEmailBean orderConfirmationEmailBean)
     */
    public void buildAndSaveNewOrderConfirmationMail(final Localization localization, final Customer customer, final String velocityPath, final OrderConfirmationEmailBean orderConfirmationEmailBean) {
        try {
        	final Locale locale = localization.getLocale();
        	
        	Map<String, Object> model = new HashMap<String, Object>();
          
        	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        	java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
        	model.put("currentDate", dateFormatter.format(currentDate));
        	model.put("customer", customer);
        	model.put("orderConfirmationEmailBean", orderConfirmationEmailBean);

        	String fromEmail = orderConfirmationEmailBean.getFromEmail();
        	MimeMessagePreparatorImpl mimeMessagePreparator = new MimeMessagePreparatorImpl();
        	mimeMessagePreparator.setTo(customer.getEmail());
        	mimeMessagePreparator.setFrom(fromEmail);
        	mimeMessagePreparator.setReplyTo(fromEmail);
        	Object[] parameters = {customer.getLastname(), customer.getFirstname()};
        	mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.order.confirmation_email_subject", parameters, locale));
        	mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "order-confirmation-html-content.vm", model));
        	mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "order-confirmation-text-content.vm", model));
        	
        	Email email = new Email();
        	email.setType(Email.EMAIl_TYPE_ORDER_CONFIRMATION);
        	email.setStatus(Email.EMAIl_STATUS_PENDING);
        	saveOrUpdateEmail(email, mimeMessagePreparator);
        	
        } catch (MailException e) {
        	LOG.error("Error, can't save the message :", e);
        } catch (VelocityException e) {
        	LOG.error("Error, can't build the message :", e);
        } catch (IOException e) {
        	LOG.error("Error, can't serializable the message :", e);
        }
    }
    
    /**
     * @see fr.hoteia.qalingo.core.service.EmailService#buildAndSaveOrderSentConfirmationMail(Localization localization, Customer customer, String velocityPath, OrderSentConfirmationEmailBean orderSentConfirmationEmailBean)
     */
    public void buildAndSaveOrderSentConfirmationMail(final Localization localization, final Customer customer, final String velocityPath, final OrderSentConfirmationEmailBean orderSentConfirmationEmailBean) {
        try {
        	final Locale locale = localization.getLocale();
        	
        	Map<String, Object> model = new HashMap<String, Object>();
          
        	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        	java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
        	model.put("currentDate", dateFormatter.format(currentDate));
        	model.put("customer", customer);
        	model.put("orderSentConfirmationEmailBean", orderSentConfirmationEmailBean);

        	String fromEmail = orderSentConfirmationEmailBean.getFromEmail();
        	MimeMessagePreparatorImpl mimeMessagePreparator = new MimeMessagePreparatorImpl();
        	mimeMessagePreparator.setTo(customer.getEmail());
        	mimeMessagePreparator.setFrom(fromEmail);
        	mimeMessagePreparator.setReplyTo(fromEmail);
        	Object[] parameters = {customer.getLastname(), customer.getFirstname()};
        	mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.order.ship_email_subject", parameters, locale));
        	mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "order-sent-html-content.vm", model));
        	mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "order-sent-text-content.vm", model));
        	
        	Email email = new Email();
        	email.setType(Email.EMAIl_TYPE_ORDER_SENT);
        	email.setStatus(Email.EMAIl_STATUS_PENDING);
        	saveOrUpdateEmail(email, mimeMessagePreparator);
        	
        } catch (MailException e) {
        	LOG.error("Error, can't save the message :", e);
        } catch (VelocityException e) {
        	LOG.error("Error, can't build the message :", e);
        } catch (IOException e) {
        	LOG.error("Error, can't serializable the message :", e);
        }
    }
    
}