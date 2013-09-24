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
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.RequestConstants;
import fr.hoteia.qalingo.core.dao.EmailDao;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.Email;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.enumtype.FoUrls;
import fr.hoteia.qalingo.core.email.bean.AbandonedShoppingCartEmailBean;
import fr.hoteia.qalingo.core.email.bean.AbstractEmailBean;
import fr.hoteia.qalingo.core.email.bean.ContactEmailBean;
import fr.hoteia.qalingo.core.email.bean.CustomerForgottenPasswordEmailBean;
import fr.hoteia.qalingo.core.email.bean.CustomerNewAccountConfirmationEmailBean;
import fr.hoteia.qalingo.core.email.bean.CustomerResetPasswordConfirmationEmailBean;
import fr.hoteia.qalingo.core.email.bean.NewsletterEmailBean;
import fr.hoteia.qalingo.core.email.bean.OrderConfirmationEmailBean;
import fr.hoteia.qalingo.core.email.bean.OrderSentConfirmationEmailBean;
import fr.hoteia.qalingo.core.email.bean.RetailerContactEmailBean;
import fr.hoteia.qalingo.core.exception.EmailProcessException;
import fr.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import fr.hoteia.qalingo.core.service.EmailService;
import fr.hoteia.qalingo.core.service.EngineSettingService;
import fr.hoteia.qalingo.core.service.UrlService;
import fr.hoteia.qalingo.core.service.pojo.RequestData;
import fr.hoteia.qalingo.core.util.impl.MimeMessagePreparatorImpl;

@Service("emailService")
@Transactional
public class EmailServiceImpl implements EmailService {

    private static final Logger LOG = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	protected EmailDao emailDao;

	@Autowired
	protected UrlService urlService;
	
	@Autowired
	protected VelocityEngine velocityEngine;
    
	@Autowired
	protected CoreMessageSource coreMessageSource;
	
	@Autowired
	protected EngineSettingService engineSettingService;
	
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
     * @throws Exception 
     * @see fr.hoteia.qalingo.core.service.EmailService#buildAndSaveContactMail(Localization localization, Customer customer, String velocityPath, ContactEmailBean contactEmailBean)
     */
    public void buildAndSaveContactMail(final RequestData requestData, final String velocityPath, final ContactEmailBean contactEmailBean) throws Exception {
        try {
        	final Localization localization = requestData.getLocalization();
        	final Locale locale = localization.getLocale();
        	
        	// SANITY CHECK
        	checkEmailAddresses(contactEmailBean);
        	
        	Map<String, Object> model = new HashMap<String, Object>();
        	String fromEmail = contactEmailBean.getFromEmail();
        	String toEmail = contactEmailBean.getToEmail();
          
        	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        	java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
        	model.put("currentDate", dateFormatter.format(currentDate));
        	model.put("contactEmailBean", contactEmailBean);
        	model.put("wording", coreMessageSource.loadWording(Email.WORDING_SCOPE_EMAIL, locale));

        	MimeMessagePreparatorImpl mimeMessagePreparator = getMimeMessagePreparator(requestData, Email.EMAIl_TYPE_CONTACT, model);
        	mimeMessagePreparator.setTo(toEmail);
        	mimeMessagePreparator.setFrom(fromEmail);
        	mimeMessagePreparator.setFromName(coreMessageSource.getMessage("email.common.from_name", locale));
        	mimeMessagePreparator.setReplyTo(fromEmail);
        	Object[] parameters = {contactEmailBean.getLastname(), contactEmailBean.getFirstname()};
        	mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.contact.email_subject", parameters, locale));
        	mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "contact-html-content.vm", model));
        	mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "contact-text-content.vm", model));
        	
        	mimeMessagePreparator.getHtmlContent();
        	
        	Email email = new Email();
        	email.setType(Email.EMAIl_TYPE_CONTACT);
        	email.setStatus(Email.EMAIl_STATUS_PENDING);
        	saveOrUpdateEmail(email, mimeMessagePreparator);
        	
        } catch (MailException e) {
        	LOG.error("Error, can't save the message :", e);
        	throw e;
        } catch (VelocityException e) {
        	LOG.error("Error, can't build the message :", e);
        	throw e;
        } catch (IOException e) {
        	LOG.error("Error, can't serializable the message :", e);
        	throw e;
        }
    }
    
    /**
     * @see fr.hoteia.qalingo.core.service.EmailService#buildAndSaveRetailerContactMail(Localization localization, Customer customer, String velocityPath, RetailerContactEmailBean retailerContactEmailBean)
     */
    public void buildAndSaveRetailerContactMail(final RequestData requestData, final Customer customer, final String velocityPath, final RetailerContactEmailBean retailerContactEmailBean) throws Exception {
        try {
        	final Localization localization = requestData.getLocalization();
        	final Locale locale = localization.getLocale();
        	
        	// SANITY CHECK
        	checkEmailAddresses(retailerContactEmailBean);
        	
        	Map<String, Object> model = new HashMap<String, Object>();
        	String fromEmail = retailerContactEmailBean.getFromEmail();
        	String toEmail = retailerContactEmailBean.getToEmail();
          
        	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        	java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
        	model.put("currentDate", dateFormatter.format(currentDate));
        	model.put("customer", customer);
        	model.put("retailerContactEmailBean", retailerContactEmailBean);
        	model.put("wording", coreMessageSource.loadWording(Email.WORDING_SCOPE_EMAIL, locale));

        	MimeMessagePreparatorImpl mimeMessagePreparator = getMimeMessagePreparator(requestData, Email.EMAIl_TYPE_RETAILER_CONTACT, model);
        	mimeMessagePreparator.setTo(toEmail);
        	mimeMessagePreparator.setFrom(fromEmail);
        	mimeMessagePreparator.setFromName(coreMessageSource.getMessage("email.common.from_name", locale));
        	mimeMessagePreparator.setReplyTo(fromEmail);
        	Object[] parameters = {retailerContactEmailBean.getLastname(), retailerContactEmailBean.getFirstname()};
        	mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.retailer_contact.email_subject", parameters, locale));
        	mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "retailer-contact-html-content.vm", model));
        	mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "retailer-contact-text-content.vm", model));
        	
        	Email email = new Email();
        	email.setType(Email.EMAIl_TYPE_RETAILER_CONTACT);
        	email.setStatus(Email.EMAIl_STATUS_PENDING);
        	saveOrUpdateEmail(email, mimeMessagePreparator);
        	
        } catch (MailException e) {
        	LOG.error("Error, can't save the message :", e);
        	throw e;
        } catch (VelocityException e) {
        	LOG.error("Error, can't build the message :", e);
        	throw e;
        } catch (IOException e) {
        	LOG.error("Error, can't serializable the message :", e);
        	throw e;
        }
    }
    
    /**
     * @see fr.hoteia.qalingo.core.service.EmailService#saveAndBuildNewsletterSubscriptionnConfirmationMail(Localization localization, Customer customer, String velocityPath, NewsletterEmailBean newsletterEmailBean)
     */
    public void saveAndBuildNewsletterSubscriptionnConfirmationMail(final RequestData requestData, final String velocityPath, 
    															   final NewsletterEmailBean newsletterEmailBean) throws Exception {
        try {
        	final MarketArea marketArea = requestData.getMarketArea();
        	final Localization localization = requestData.getLocalization();
        	final Locale locale = localization.getLocale();
        	
        	// SANITY CHECK
        	checkEmailAddresses(newsletterEmailBean);
        	
        	Map<String, Object> model = new HashMap<String, Object>();
          
        	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        	java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
        	model.put("currentDate", dateFormatter.format(currentDate));
        	model.put("newsletterEmailBean", newsletterEmailBean);
        	model.put("wording", coreMessageSource.loadWording(Email.WORDING_SCOPE_EMAIL, locale));

    		Map<String, String> urlParams = new HashMap<String, String>();
    		urlParams.put(RequestConstants.REQUEST_PARAMETER_NEWSLETTER_EMAIL, URLEncoder.encode(newsletterEmailBean.getToEmail(), Constants.ANSI));
    		urlParams.put(RequestConstants.REQUEST_PARAMETER_MARKET_AREA_CODE, marketArea.getCode());
    		String unsubscribeUrl = urlService.generateUrl(FoUrls.NEWSLETTER_UNREGISTER, requestData, urlParams);
    		String fullUnsubscribeUrl = urlService.buildAbsoluteUrl(requestData, unsubscribeUrl);
    		
        	model.put("unsubscribeUrlOrEmail", fullUnsubscribeUrl);

        	String fromEmail = newsletterEmailBean.getFromEmail();
        	MimeMessagePreparatorImpl mimeMessagePreparator = getMimeMessagePreparator(requestData, Email.EMAIl_TYPE_NEWSLETTER_SUBSCRIPTION, model);
        	mimeMessagePreparator.setUnsubscribeUrlOrEmail(fullUnsubscribeUrl);
        	mimeMessagePreparator.setTo(newsletterEmailBean.getToEmail());
        	mimeMessagePreparator.setFrom(fromEmail);
        	mimeMessagePreparator.setFromName(coreMessageSource.getMessage("email.common.from_name", locale));
        	mimeMessagePreparator.setReplyTo(fromEmail);
        	Object[] parameters = {};
        	mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.newsletter_subscription.email_subject", parameters, locale));
        	mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "newsletter-subscription-confirmation-html-content.vm", model));
        	mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "newsletter-subscription-confirmation-text-content.vm", model));
        	
        	Email email = new Email();
        	email.setType(Email.EMAIl_TYPE_NEWSLETTER_SUBSCRIPTION);
        	email.setStatus(Email.EMAIl_STATUS_PENDING);
        	saveOrUpdateEmail(email, mimeMessagePreparator);
        	
        } catch (MailException e) {
        	LOG.error("Error, can't save the message :", e);
        	throw e;
        } catch (VelocityException e) {
        	LOG.error("Error, can't build the message :", e);
        	throw e;
        } catch (IOException e) {
        	LOG.error("Error, can't serializable the message :", e);
        	throw e;
        }
    }
    
    /**
     * @see fr.hoteia.qalingo.core.service.EmailService#saveAndBuildNewsletterUnsubscriptionConfirmationMail(Localization localization, Customer customer, String velocityPath, NewsletterEmailBean newsletterEmailBean)
     */
    public void saveAndBuildNewsletterUnsubscriptionConfirmationMail(final RequestData requestData, final String velocityPath, 
    															   final NewsletterEmailBean newsletterEmailBean) throws Exception {
        try {
        	final Localization localization = requestData.getLocalization();
        	final Locale locale = localization.getLocale();
        	
        	// SANITY CHECK
        	checkEmailAddresses(newsletterEmailBean);
        	
        	Map<String, Object> model = new HashMap<String, Object>();
          
        	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        	java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
        	model.put("currentDate", dateFormatter.format(currentDate));
        	model.put("newsletterEmailBean", newsletterEmailBean);
        	model.put("wording", coreMessageSource.loadWording(Email.WORDING_SCOPE_EMAIL, locale));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put(RequestConstants.REQUEST_PARAMETER_NEWSLETTER_EMAIL, URLEncoder.encode(newsletterEmailBean.getToEmail(), Constants.ANSI));
			String subscribeUrl = urlService.generateUrl(FoUrls.NEWSLETTER_REGISTER, requestData, urlParams);
        	model.put("subscribeUrl", urlService.buildAbsoluteUrl(requestData, subscribeUrl));
        	
    		String unsubscribeUrl = urlService.generateUrl(FoUrls.NEWSLETTER_UNREGISTER, requestData, urlParams);
    		String fullUnsubscribeUrl = urlService.buildAbsoluteUrl(requestData, unsubscribeUrl);
    		
        	model.put("unsubscribeUrlOrEmail", fullUnsubscribeUrl);
        	
        	String fromEmail = newsletterEmailBean.getFromEmail();
        	MimeMessagePreparatorImpl mimeMessagePreparator = getMimeMessagePreparator(requestData, Email.EMAIl_TYPE_NEWSLETTER_SUBSCRIPTION, model);
        	mimeMessagePreparator.setUnsubscribeUrlOrEmail(fullUnsubscribeUrl);
        	mimeMessagePreparator.setTo(newsletterEmailBean.getToEmail());
        	mimeMessagePreparator.setFrom(fromEmail);
        	mimeMessagePreparator.setFromName(coreMessageSource.getMessage("email.common.from_name", locale));
        	mimeMessagePreparator.setReplyTo(fromEmail);
        	Object[] parameters = {};
        	mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.newsletter_unsubscription.email_subject", parameters, locale));
        	mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "newsletter-unsubscription-confirmation-html-content.vm", model));
        	mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "newsletter-unsubscription-confirmation-text-content.vm", model));
        	
        	Email email = new Email();
        	email.setType(Email.EMAIl_TYPE_NEWSLETTER_SUBSCRIPTION);
        	email.setStatus(Email.EMAIl_STATUS_PENDING);
        	saveOrUpdateEmail(email, mimeMessagePreparator);
        	
        } catch (MailException e) {
        	LOG.error("Error, can't save the message :", e);
        	throw e;
        } catch (VelocityException e) {
        	LOG.error("Error, can't build the message :", e);
        	throw e;
        } catch (IOException e) {
        	LOG.error("Error, can't serializable the message :", e);
        	throw e;
        }
    }
    
    /**
     * @see fr.hoteia.qalingo.core.service.EmailService#buildAndSaveCustomerNewAccountMail(Localization localization, Customer customer, String velocityPath, CustomerNewAccountConfirmationEmailBean customerNewAccountConfirmationEmailBean)
     */
    public void buildAndSaveCustomerNewAccountMail(final RequestData requestData, final String velocityPath, 
    											   final CustomerNewAccountConfirmationEmailBean customerNewAccountConfirmationEmailBean) throws Exception {
        try {
        	final Localization localization = requestData.getLocalization();
        	final Locale locale = localization.getLocale();
        	
        	// SANITY CHECK
        	checkEmailAddresses(customerNewAccountConfirmationEmailBean);
        	
        	Map<String, Object> model = new HashMap<String, Object>();
          
        	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        	java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
        	model.put("currentDate", dateFormatter.format(currentDate));
        	model.put("customerNewAccountConfirmationEmailBean", customerNewAccountConfirmationEmailBean);
        	model.put("wording", coreMessageSource.loadWording(Email.WORDING_SCOPE_EMAIL, locale));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put(RequestConstants.REQUEST_PARAMETER_NEW_CUSTOMER_VALIDATION_EMAIL, URLEncoder.encode(customerNewAccountConfirmationEmailBean.getEmail(), Constants.ANSI));
			urlParams.put(RequestConstants.REQUEST_PARAMETER_NEW_CUSTOMER_VALIDATION_TOKEN, UUID.randomUUID().toString());
			String resetPasswordUrl = urlService.generateUrl(FoUrls.CUSTOMER_NEW_ACCOUNT_VALIDATION, requestData, urlParams);
        	model.put("newCustomerValidationUrl", urlService.buildAbsoluteUrl(requestData, resetPasswordUrl));

        	String fromEmail = customerNewAccountConfirmationEmailBean.getFromEmail();
        	MimeMessagePreparatorImpl mimeMessagePreparator = getMimeMessagePreparator(requestData, Email.EMAIl_TYPE_NEW_ACCOUNT_CONFIRMATION, model);
        	mimeMessagePreparator.setTo(customerNewAccountConfirmationEmailBean.getToEmail());
        	mimeMessagePreparator.setFrom(fromEmail);
        	mimeMessagePreparator.setFromName(coreMessageSource.getMessage("email.common.from_name", locale));
        	mimeMessagePreparator.setReplyTo(fromEmail);
        	Object[] parameters = {customerNewAccountConfirmationEmailBean.getLastname(), customerNewAccountConfirmationEmailBean.getFirstname()};
        	mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.new_account.email_subject", parameters, locale));
        	mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "new-account-confirmation-html-content.vm", model));
        	mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "new-account-confirmation-text-content.vm", model));
        	
        	Email email = new Email();
        	email.setType(Email.EMAIl_TYPE_NEW_ACCOUNT_CONFIRMATION);
        	email.setStatus(Email.EMAIl_STATUS_PENDING);
        	saveOrUpdateEmail(email, mimeMessagePreparator);
        	
        } catch (MailException e) {
        	LOG.error("Error, can't save the message :", e);
        	throw e;
        } catch (VelocityException e) {
        	LOG.error("Error, can't build the message :", e);
        	throw e;
        } catch (IOException e) {
        	LOG.error("Error, can't serializable the message :", e);
        	throw e;
        }
    }
    
    /**
     * @throws Exception 
     * @see fr.hoteia.qalingo.core.service.EmailService#buildAndSaveCustomerForgottenPasswordMail(Localization localization, Customer customer, String velocityPath, CustomerForgottenPasswordEmailBean customerForgottenPasswordEmailBean)
     */
    public void buildAndSaveCustomerForgottenPasswordMail(final RequestData requestData, final Customer customer, final String velocityPath, 
    													  final CustomerForgottenPasswordEmailBean customerForgottenPasswordEmailBean) throws Exception {
        try {
        	final Localization localization = requestData.getLocalization();
        	final Locale locale = localization.getLocale();
        	
        	// SANITY CHECK
        	checkEmailAddresses(customerForgottenPasswordEmailBean);
        	
        	Map<String, Object> model = new HashMap<String, Object>();
          
        	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        	java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
        	model.put("currentDate", dateFormatter.format(currentDate));
        	model.put("customer", customer);
        	model.put("customerForgottenPasswordEmailBean", customerForgottenPasswordEmailBean);
        	model.put("wording", coreMessageSource.loadWording(Email.WORDING_SCOPE_EMAIL, locale));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put(RequestConstants.REQUEST_PARAMETER_PASSWORD_RESET_EMAIL, URLEncoder.encode(customer.getEmail(), Constants.ANSI));
			urlParams.put(RequestConstants.REQUEST_PARAMETER_PASSWORD_RESET_TOKEN, customerForgottenPasswordEmailBean.getToken());
			String resetPasswordUrl = urlService.generateUrl(FoUrls.RESET_PASSWORD, requestData, urlParams);
        	model.put("activeChangePasswordUrl", urlService.buildAbsoluteUrl(requestData, resetPasswordUrl));
        	
			String canceResetPasswordUrl = urlService.generateUrl(FoUrls.CANCEL_RESET_PASSWORD, requestData, urlParams);
        	model.put("cancelChangePasswordUrl", urlService.buildAbsoluteUrl(requestData, canceResetPasswordUrl));
        	
        	model.put("customerForgottenPasswordEmailBean", customerForgottenPasswordEmailBean);

        	String fromEmail = customerForgottenPasswordEmailBean.getFromEmail();
        	MimeMessagePreparatorImpl mimeMessagePreparator = getMimeMessagePreparator(requestData, Email.EMAIl_TYPE_FORGOTTEN_PASSWORD, model);
        	mimeMessagePreparator.setTo(customer.getEmail());
        	mimeMessagePreparator.setFrom(fromEmail);
        	mimeMessagePreparator.setFromName(coreMessageSource.getMessage("email.common.from_name", locale));
        	mimeMessagePreparator.setReplyTo(fromEmail);
        	Object[] parameters = {customer.getLastname(), customer.getFirstname()};
        	mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.forgotten_password.email_subject", parameters, locale));
        	mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "forgotten-password-html-content.vm", model));
        	mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "forgotten-password-text-content.vm", model));
        	
        	Email email = new Email();
        	email.setType(Email.EMAIl_TYPE_FORGOTTEN_PASSWORD);
        	email.setStatus(Email.EMAIl_STATUS_PENDING);
        	saveOrUpdateEmail(email, mimeMessagePreparator);
        	
        } catch (MailException e) {
        	LOG.error("Error, can't save the message :", e);
        	throw e;
        } catch (VelocityException e) {
        	LOG.error("Error, can't build the message :", e);
        	throw e;
        } catch (IOException e) {
        	LOG.error("Error, can't serializable the message :", e);
        	throw e;
        }
    }
    
    /**
     * @see fr.hoteia.qalingo.core.service.EmailService#buildAndSaveCustomerResetPasswordConfirmationMail(Localization localization, Customer customer, String velocityPath, CustomerResetPasswordConfirmationEmailBean customerResetPasswordConfirmationEmailBean)
     */
    public void buildAndSaveCustomerResetPasswordConfirmationMail(final RequestData requestData, final Customer customer, final String velocityPath, 
    															  final CustomerResetPasswordConfirmationEmailBean customerResetPasswordConfirmationEmailBean) throws Exception {
        try {
        	final Localization localization = requestData.getLocalization();
        	final Locale locale = localization.getLocale();
        	
        	// SANITY CHECK
        	checkEmailAddresses(customerResetPasswordConfirmationEmailBean);
        	
        	Map<String, Object> model = new HashMap<String, Object>();
          
        	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        	java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
        	model.put("currentDate", dateFormatter.format(currentDate));
        	model.put("customer", customer);
        	model.put("customerResetPasswordConfirmationEmailBean", customerResetPasswordConfirmationEmailBean);
        	model.put("wording", coreMessageSource.loadWording(Email.WORDING_SCOPE_EMAIL, locale));

			String loginUrl = urlService.generateUrl(FoUrls.LOGIN, requestData);
        	model.put("loginUrl", urlService.buildAbsoluteUrl(requestData, loginUrl));
        	
        	String fromEmail = customerResetPasswordConfirmationEmailBean.getFromEmail();
        	MimeMessagePreparatorImpl mimeMessagePreparator = getMimeMessagePreparator(requestData, Email.EMAIl_TYPE_RESET_PASSWORD_CONFIRMATION, model);
        	mimeMessagePreparator.setTo(customer.getEmail());
        	mimeMessagePreparator.setFrom(fromEmail);
        	mimeMessagePreparator.setFromName(coreMessageSource.getMessage("email.common.from_name", locale));
        	mimeMessagePreparator.setReplyTo(fromEmail);
        	Object[] parameters = {customer.getLastname(), customer.getFirstname()};
        	mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.reset_password_confirmation.email_subject", parameters, locale));
        	mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "reset-password-confirmation-html-content.vm", model));
        	mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "reset-password-confirmation-text-content.vm", model));
        	
        	Email email = new Email();
        	email.setType(Email.EMAIl_TYPE_RESET_PASSWORD_CONFIRMATION);
        	email.setStatus(Email.EMAIl_STATUS_PENDING);
        	saveOrUpdateEmail(email, mimeMessagePreparator);
        	
        } catch (MailException e) {
        	LOG.error("Error, can't save the message :", e);
        	throw e;
        } catch (VelocityException e) {
        	LOG.error("Error, can't build the message :", e);
        	throw e;
        } catch (IOException e) {
        	LOG.error("Error, can't serializable the message :", e);
        	throw e;
        }
    }
    
    /**
     * @see fr.hoteia.qalingo.core.service.EmailService#buildAndSaveNewOrderConfirmationMail(Localization localization, Customer customer, String velocityPath, OrderConfirmationEmailBean orderConfirmationEmailBean)
     */
    public void buildAndSaveNewOrderConfirmationMail(final RequestData requestData, final Customer customer, final String velocityPath, 
    												 final OrderConfirmationEmailBean orderConfirmationEmailBean) throws Exception {
        try {
        	final Localization localization = requestData.getLocalization();
        	final Locale locale = localization.getLocale();
        	
        	// SANITY CHECK
        	checkEmailAddresses(orderConfirmationEmailBean);
        	
        	Map<String, Object> model = new HashMap<String, Object>();
          
        	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        	java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
        	model.put("currentDate", dateFormatter.format(currentDate));
        	model.put("customer", customer);
        	model.put("orderConfirmationEmailBean", orderConfirmationEmailBean);
        	model.put("wording", coreMessageSource.loadWording(Email.WORDING_SCOPE_EMAIL, locale));

        	String fromEmail = orderConfirmationEmailBean.getFromEmail();
        	MimeMessagePreparatorImpl mimeMessagePreparator = getMimeMessagePreparator(requestData, Email.EMAIl_TYPE_ORDER_CONFIRMATION, model);
        	mimeMessagePreparator.setTo(customer.getEmail());
        	mimeMessagePreparator.setFrom(fromEmail);
        	mimeMessagePreparator.setFromName(coreMessageSource.getMessage("email.common.from_name", locale));
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
        	throw e;
        } catch (VelocityException e) {
        	LOG.error("Error, can't build the message :", e);
        	throw e;
        } catch (IOException e) {
        	LOG.error("Error, can't serializable the message :", e);
        	throw e;
        }
    }
    
    /**
     * @see fr.hoteia.qalingo.core.service.EmailService#buildAndSaveOrderShippedConfirmationMail(Localization localization, Customer customer, String velocityPath, OrderSentConfirmationEmailBean orderSentConfirmationEmailBean)
     */
    public void buildAndSaveOrderShippedConfirmationMail(final RequestData requestData, final Customer customer, final String velocityPath, 
    													 final OrderSentConfirmationEmailBean orderSentConfirmationEmailBean) throws Exception {
        try {
        	final Localization localization = requestData.getLocalization();
        	final Locale locale = localization.getLocale();
        	
        	// SANITY CHECK
        	checkEmailAddresses(orderSentConfirmationEmailBean);
        	
        	Map<String, Object> model = new HashMap<String, Object>();
          
        	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        	java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
        	model.put("currentDate", dateFormatter.format(currentDate));
        	model.put("customer", customer);
        	model.put("orderSentConfirmationEmailBean", orderSentConfirmationEmailBean);
        	model.put("wording", coreMessageSource.loadWording(Email.WORDING_SCOPE_EMAIL, locale));

        	String fromEmail = orderSentConfirmationEmailBean.getFromEmail();
        	MimeMessagePreparatorImpl mimeMessagePreparator = getMimeMessagePreparator(requestData, Email.EMAIl_TYPE_ORDER_SHIPPED, model);
        	mimeMessagePreparator.setTo(customer.getEmail());
        	mimeMessagePreparator.setFrom(fromEmail);
        	mimeMessagePreparator.setFromName(coreMessageSource.getMessage("email.common.from_name", locale));
        	mimeMessagePreparator.setReplyTo(fromEmail);
        	Object[] parameters = {customer.getLastname(), customer.getFirstname()};
        	mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.order_shipped.shipped_email_subject", parameters, locale));
        	mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "order-shipped-html-content.vm", model));
        	mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "order-shipped-text-content.vm", model));
        	
        	Email email = new Email();
        	email.setType(Email.EMAIl_TYPE_ORDER_SHIPPED);
        	email.setStatus(Email.EMAIl_STATUS_PENDING);
        	saveOrUpdateEmail(email, mimeMessagePreparator);
        	
        } catch (MailException e) {
        	LOG.error("Error, can't save the message :", e);
        	throw e;
        } catch (VelocityException e) {
        	LOG.error("Error, can't build the message :", e);
        	throw e;
        } catch (IOException e) {
        	LOG.error("Error, can't serializable the message :", e);
        	throw e;
        }
    }
    
    /**
     * @see fr.hoteia.qalingo.core.service.EmailService#buildAndSaveOrderShippedConfirmationMail(Localization localization, Customer customer, String velocityPath, AbandonedShoppingCartEmailBean abandonedShoppingCartEmailBean)
     */
    public void buildAndSaveAbandonedShoppingCartMail(final RequestData requestData, final Customer customer, final String velocityPath, 
    												  final AbandonedShoppingCartEmailBean abandonedShoppingCartEmailBean) throws Exception {
        try {
        	final Localization localization = requestData.getLocalization();
        	final Locale locale = localization.getLocale();
        	
        	// SANITY CHECK
        	checkEmailAddresses(abandonedShoppingCartEmailBean);
        	
        	Map<String, Object> model = new HashMap<String, Object>();
          
        	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        	java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
        	model.put("currentDate", dateFormatter.format(currentDate));
        	model.put("customer", customer);
        	model.put("abandonedShoppingCartEmailBean", abandonedShoppingCartEmailBean);
        	model.put("wording", coreMessageSource.loadWording(Email.WORDING_SCOPE_EMAIL, locale));

        	String fromEmail = abandonedShoppingCartEmailBean.getFromEmail();
        	MimeMessagePreparatorImpl mimeMessagePreparator = getMimeMessagePreparator(requestData, Email.EMAIl_TYPE_ABANDONED_SHOPPING_CART, model);
        	mimeMessagePreparator.setTo(customer.getEmail());
        	mimeMessagePreparator.setFrom(fromEmail);
        	mimeMessagePreparator.setFromName(coreMessageSource.getMessage("email.common.from_name", locale));
        	mimeMessagePreparator.setReplyTo(fromEmail);
        	Object[] parameters = {customer.getLastname(), customer.getFirstname()};
        	mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.abandoned_shopping_cart.email_subject", parameters, locale));
        	mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "abandoned-shopping-cart-html-content.vm", model));
        	mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityPath + "abandoned-shopping-cart-text-content.vm", model));
        	
        	Email email = new Email();
        	email.setType(Email.EMAIl_TYPE_ABANDONED_SHOPPING_CART);
        	email.setStatus(Email.EMAIl_STATUS_PENDING);
        	saveOrUpdateEmail(email, mimeMessagePreparator);
        	
        } catch (MailException e) {
        	LOG.error("Error, can't save the message :", e);
        	throw e;
        } catch (VelocityException e) {
        	LOG.error("Error, can't build the message :", e);
        	throw e;
        } catch (IOException e) {
        	LOG.error("Error, can't serializable the message :", e);
        	throw e;
        }
    }

    private MimeMessagePreparatorImpl getMimeMessagePreparator(final RequestData requestData, final String emailType, final Map<String, Object> model) throws Exception{
    	MimeMessagePreparatorImpl mimeMessagePreparator = new MimeMessagePreparatorImpl();
    	boolean emailFileMirroringActivated = engineSettingService.getEmailFileMirroringActivated(emailType);
    	if(emailFileMirroringActivated){
    		mimeMessagePreparator.setMirroringActivated(emailFileMirroringActivated);
    		
    		String emailFileMirroringExtension = engineSettingService.getEmailFileMirroringExtension(emailType);
    		String filePath = emailType.toLowerCase() + System.getProperty ("file.separator") + UUID.randomUUID() + "-" + UUID.randomUUID() + emailFileMirroringExtension;
    		// FILE SYSTEM FOLDER PATH
    		String emailFileMirroringFolderPath = engineSettingService.getEmailFileMirroringFolderPath(emailType);
    		if(!emailFileMirroringFolderPath.endsWith("/")){
    			emailFileMirroringFolderPath = emailFileMirroringFolderPath + "/";
    		}
    		mimeMessagePreparator.setMirroringFilePath(emailFileMirroringFolderPath + filePath);
    		
    		// MIRRORING WEB URL IN EMAIL
    		String emailFileMirroringWebPath = engineSettingService.getEmailFileMirroringWebPath(emailType);
    		if(!emailFileMirroringWebPath.endsWith("/")){
    			emailFileMirroringWebPath = emailFileMirroringWebPath + "/";
    		}
    		String webFileRelativeUrl = emailFileMirroringWebPath + filePath;
    		model.put("mirroringWebPath", urlService.buildAbsoluteUrl(requestData, webFileRelativeUrl));
    	}
    	return mimeMessagePreparator;
    }

    	
    private void checkEmailAddresses(AbstractEmailBean emailBean) throws EmailProcessException{
		if(StringUtils.isEmpty(emailBean.getFromEmail())){
			throw new EmailProcessException(EmailProcessException.EMAIl_FROM_ADDRESS_IS_EMPTY);
		}
		
		if(StringUtils.isEmpty(emailBean.getToEmail())){
			throw new EmailProcessException(EmailProcessException.EMAIl_TO_ADDRESS_IS_EMPTY);
		}
		
		if(StringUtils.isEmpty(emailBean.getReplyToEmail())){
			throw new EmailProcessException(EmailProcessException.EMAIl_REPLY_TO_ADDRESS_IS_EMPTY);
		}
    }
    
}