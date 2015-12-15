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

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.dao.EmailDao;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.Email;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.email.bean.AbandonedShoppingCartEmailBean;
import org.hoteia.qalingo.core.email.bean.AbstractEmailBean;
import org.hoteia.qalingo.core.email.bean.AdminNotificationEmailBean;
import org.hoteia.qalingo.core.email.bean.ContactEmailBean;
import org.hoteia.qalingo.core.email.bean.CustomerForgottenPasswordEmailBean;
import org.hoteia.qalingo.core.email.bean.CustomerNewAccountConfirmationEmailBean;
import org.hoteia.qalingo.core.email.bean.CustomerResetPasswordConfirmationEmailBean;
import org.hoteia.qalingo.core.email.bean.NewsletterEmailBean;
import org.hoteia.qalingo.core.email.bean.OrderConfirmationEmailBean;
import org.hoteia.qalingo.core.email.bean.OrderSentConfirmationEmailBean;
import org.hoteia.qalingo.core.email.bean.RetailerContactEmailBean;
import org.hoteia.qalingo.core.email.bean.UserForgottenPasswordEmailBean;
import org.hoteia.qalingo.core.email.bean.UserNewAccountConfirmationEmailBean;
import org.hoteia.qalingo.core.email.bean.UserResetPasswordConfirmationEmailBean;
import org.hoteia.qalingo.core.exception.EmailProcessException;
import org.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.util.impl.MimeMessagePreparatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

@Service("emailService")
@Transactional
public class EmailService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static final String CURRENT_DATE = "currentDate";
    public static final String WORDING      = "wording";
    public static final String CUSTOMER     = "customer";

	@Autowired
	protected EmailDao emailDao;

	@Autowired
	protected UrlService urlService;
	
    @Autowired
    protected BackofficeUrlService backofficeUrlService;
	   
	@Autowired
	protected VelocityConfigurer velocityConfigurer;
    
	@Autowired
	protected CoreMessageSource coreMessageSource;
	
	@Autowired
	protected EngineSettingService engineSettingService;
	
	public Email getEmailById(final Long id, Object... params) {
		return emailDao.getEmailById(id, params);
	}

	public List<Email> findEmailByStatus(final String status, Object... params) {
		return emailDao.findEmailByStatus(status, params);
	}
	
    public List<Long> findIdsForEmailSync(Object... params) {
        return emailDao.findIdsForEmailSync(params);
    }
	   
	public List<Long> findIdsForEmailSync(final String type, Object... params) {
		return emailDao.findIdsForEmailSync(type, params);
	}
	
	public Email saveOrUpdateEmail(final Email email) {
	    return emailDao.saveOrUpdateEmail(email);
	}
	
	public Email saveOrUpdateEmail(final Email email, final MimeMessagePreparatorImpl mimeMessagePreparator) throws IOException {
		return emailDao.saveEmail(email, mimeMessagePreparator);
	}
	
	public void deleteEmail(final Email email) {
		emailDao.deleteEmail(email);
	}
	
	public int deleteSendedEmail(final Timestamp before) {
	    return emailDao.deleteSendedEmail(before);
	}
	
	public void handleEmailException(final Email email, final Exception exception) throws IOException {
	    emailDao.handleEmailException(email, exception);
	}
	
    /**
     * @throws Exception 
     */
    public Email buildAndSaveAdminNotification(final RequestData requestData, final String velocityPath, final AdminNotificationEmailBean adminNotificationEmailBean) throws Exception {
        Email email = null;
        try {
            final String contextNameValue = requestData.getContextNameValue();
            final Localization localization = requestData.getMarketAreaLocalization();
            final Locale locale = localization.getLocale();
            
            // SANITY CHECK
            checkEmailAddresses(adminNotificationEmailBean);
            
            Map<String, Object> model = new HashMap<String, Object>();
            DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
            java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
            model.put(CURRENT_DATE, dateFormatter.format(currentDate));
            model.put("adminNotificationEmailBean", adminNotificationEmailBean);
            model.put(WORDING, coreMessageSource.loadWording(Email.WORDING_SCOPE_EMAIL, locale));

            String fromAddress = handleFromAddress(adminNotificationEmailBean.getFromAddress(), contextNameValue);
            String fromName = handleFromName(adminNotificationEmailBean.getFromName(), locale);
            String toEmail = adminNotificationEmailBean.getToEmail();

            MimeMessagePreparatorImpl mimeMessagePreparator = getMimeMessagePreparator(requestData, Email.EMAIl_TYPE_ADMIN_NOTIFICATION, model);
            mimeMessagePreparator.setTo(toEmail);
            mimeMessagePreparator.setFrom(fromAddress);
            mimeMessagePreparator.setFromName(fromName);
            mimeMessagePreparator.setReplyTo(fromAddress);
            mimeMessagePreparator.setSubject(adminNotificationEmailBean.getSubject());
            mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "admin-notification-html-content.vm", model));
            mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "admin-notification-text-content.vm", model));
            
            email = new Email();
            email.setType(Email.EMAIl_TYPE_ADMIN_NOTIFICATION);
            email.setStatus(Email.EMAIl_STATUS_PENDING);
            saveOrUpdateEmail(email, mimeMessagePreparator);
            
        } catch (MailException e) {
            logger.error("Error, can't save the message :", e);
            throw e;
        } catch (VelocityException e) {
            logger.error("Error, can't build the message :", e);
            throw e;
        } catch (IOException e) {
            logger.error("Error, can't serializable the message :", e);
            throw e;
        }
        return email;
    }
    
    /**
     * @throws Exception 
     */
    public Email buildAndSaveContactMail(final RequestData requestData, final String velocityPath, final ContactEmailBean contactEmailBean) throws Exception {
        Email email = null;
        try {
            final String contextNameValue = requestData.getContextNameValue();
        	final Localization localization = requestData.getMarketAreaLocalization();
        	final Locale locale = localization.getLocale();
        	
        	// SANITY CHECK
        	checkEmailAddresses(contactEmailBean);
        	
        	Map<String, Object> model = new HashMap<String, Object>();
        	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        	java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
        	model.put(CURRENT_DATE, dateFormatter.format(currentDate));
        	model.put("contactEmailBean", contactEmailBean);
        	model.put(WORDING, coreMessageSource.loadWording(Email.WORDING_SCOPE_EMAIL, locale));

            String fromAddress = handleFromAddress(contactEmailBean.getFromAddress(), contextNameValue);
            String fromName = handleFromName(contactEmailBean.getFromName(), locale);
            String toEmail = contactEmailBean.getToEmail();

        	MimeMessagePreparatorImpl mimeMessagePreparator = getMimeMessagePreparator(requestData, Email.EMAIl_TYPE_CONTACT, model);
        	mimeMessagePreparator.setTo(toEmail);
        	mimeMessagePreparator.setFrom(fromAddress);
        	mimeMessagePreparator.setFromName(fromName);
        	mimeMessagePreparator.setReplyTo(fromAddress);
        	Object[] parameters = {contactEmailBean.getLastname(), contactEmailBean.getFirstname()};
        	mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.contact.email_subject", parameters, locale));
        	mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "contact-html-content.vm", model));
        	mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "contact-text-content.vm", model));
        	
        	email = new Email();
        	email.setType(Email.EMAIl_TYPE_CONTACT);
        	email.setStatus(Email.EMAIl_STATUS_PENDING);
        	saveOrUpdateEmail(email, mimeMessagePreparator);
        	
        } catch (MailException e) {
        	logger.error("Error, can't save the message :", e);
        	throw e;
        } catch (VelocityException e) {
        	logger.error("Error, can't build the message :", e);
        	throw e;
        } catch (IOException e) {
        	logger.error("Error, can't serializable the message :", e);
        	throw e;
        }
        return email;
    }
    
    /**
     * @throws Exception 
     */
    public Email buildAndSaveRetailerContactMail(final RequestData requestData, final Customer customer, final String velocityPath, 
                                                 final RetailerContactEmailBean retailerContactEmailBean) throws Exception {
        Email email = null;
        try {
            final String contextNameValue = requestData.getContextNameValue();
        	final Localization localization = requestData.getMarketAreaLocalization();
        	final Locale locale = localization.getLocale();
        	
        	// SANITY CHECK
        	checkEmailAddresses(retailerContactEmailBean);
        	
        	Map<String, Object> model = new HashMap<String, Object>();
        	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        	java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
        	model.put(CURRENT_DATE, dateFormatter.format(currentDate));
        	model.put(CUSTOMER, customer);
        	model.put("retailerContactEmailBean", retailerContactEmailBean);
        	model.put(WORDING, coreMessageSource.loadWording(Email.WORDING_SCOPE_EMAIL, locale));

            String fromAddress = handleFromAddress(retailerContactEmailBean.getFromAddress(), contextNameValue);
            String fromName = handleFromName(retailerContactEmailBean.getFromName(), locale);
            String toEmail = retailerContactEmailBean.getToEmail();
            
        	MimeMessagePreparatorImpl mimeMessagePreparator = getMimeMessagePreparator(requestData, Email.EMAIl_TYPE_RETAILER_CONTACT, model);
        	mimeMessagePreparator.setTo(toEmail);
        	mimeMessagePreparator.setFrom(fromAddress);
        	mimeMessagePreparator.setFromName(fromName);
        	mimeMessagePreparator.setReplyTo(fromAddress);
        	Object[] parameters = {retailerContactEmailBean.getLastname(), retailerContactEmailBean.getFirstname()};
        	mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.retailer_contact.email_subject", parameters, locale));
        	mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "retailer-contact-html-content.vm", model));
        	mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "retailer-contact-text-content.vm", model));
        	
        	email = new Email();
        	email.setType(Email.EMAIl_TYPE_RETAILER_CONTACT);
        	email.setStatus(Email.EMAIl_STATUS_PENDING);
        	saveOrUpdateEmail(email, mimeMessagePreparator);
        	
        } catch (MailException e) {
        	logger.error("Error, can't save the message :", e);
        	throw e;
        } catch (VelocityException e) {
        	logger.error("Error, can't build the message :", e);
        	throw e;
        } catch (IOException e) {
        	logger.error("Error, can't serializable the message :", e);
        	throw e;
        }
        return email;
    }
    
    /**
     * @throws Exception 
     */
    public Email buildAndSaveNewsletterSubscriptionConfirmationMail(final RequestData requestData, final String velocityPath, 
    															   final NewsletterEmailBean newsletterEmailBean) throws Exception {
        Email email = null;
        try {
            final String contextNameValue = requestData.getContextNameValue();
        	final MarketArea marketArea = requestData.getMarketArea();
        	final Localization localization = requestData.getMarketAreaLocalization();
        	final Locale locale = localization.getLocale();
        	
        	// SANITY CHECK
        	checkEmailAddresses(newsletterEmailBean);
        	
        	Map<String, Object> model = new HashMap<String, Object>();
          
        	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        	java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
        	model.put(CURRENT_DATE, dateFormatter.format(currentDate));
        	model.put("newsletterEmailBean", newsletterEmailBean);
        	model.put(WORDING, coreMessageSource.loadWording(Email.WORDING_SCOPE_EMAIL, locale));

    		Map<String, String> urlParams = new HashMap<String, String>();
    		urlParams.put(RequestConstants.REQUEST_PARAMETER_NEWSLETTER_EMAIL, URLEncoder.encode(newsletterEmailBean.getToEmail(), Constants.ANSI));
    		urlParams.put(RequestConstants.REQUEST_PARAMETER_MARKET_AREA_CODE, marketArea.getCode());
    		String unsubscribeUrl = urlService.generateUrl(FoUrls.NEWSLETTER_UNREGISTER, requestData, urlParams);
    		String fullUnsubscribeUrl = urlService.buildAbsoluteUrl(requestData, unsubscribeUrl);
    		
        	model.put("unsubscribeUrlOrEmail", fullUnsubscribeUrl);

        	String fromAddress = handleFromAddress(newsletterEmailBean.getFromAddress(), contextNameValue);
            String fromName = handleFromName(newsletterEmailBean.getFromName(), locale);
            String toEmail = newsletterEmailBean.getToEmail();
            
        	MimeMessagePreparatorImpl mimeMessagePreparator = getMimeMessagePreparator(requestData, Email.EMAIl_TYPE_NEWSLETTER_SUBSCRIPTION, model);
        	mimeMessagePreparator.setUnsubscribeUrlOrEmail(fullUnsubscribeUrl);
        	mimeMessagePreparator.setTo(toEmail);
        	mimeMessagePreparator.setFrom(fromAddress);
        	mimeMessagePreparator.setFromName(fromName);
        	mimeMessagePreparator.setReplyTo(fromAddress);
        	Object[] parameters = {};
        	mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.newsletter_subscription.email_subject", parameters, locale));
        	mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "newsletter-subscription-confirmation-html-content.vm", model));
        	mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "newsletter-subscription-confirmation-text-content.vm", model));
        	
        	email = new Email();
        	email.setType(Email.EMAIl_TYPE_NEWSLETTER_SUBSCRIPTION);
        	email.setStatus(Email.EMAIl_STATUS_PENDING);
        	saveOrUpdateEmail(email, mimeMessagePreparator);
        	
        } catch (MailException e) {
        	logger.error("Error, can't save the message :", e);
        	throw e;
        } catch (VelocityException e) {
        	logger.error("Error, can't build the message :", e);
        	throw e;
        } catch (IOException e) {
        	logger.error("Error, can't serializable the message :", e);
        	throw e;
        }
        return email;
    }
    
    /**
     * @throws Exception 
     */
    public Email buildAndSaveNewsletterUnsubscriptionConfirmationMail(final RequestData requestData, final String velocityPath, 
    															      final NewsletterEmailBean newsletterEmailBean) throws Exception {
        Email email = null;
        try {
            final String contextNameValue = requestData.getContextNameValue();
        	final Localization localization = requestData.getMarketAreaLocalization();
        	final Locale locale = localization.getLocale();
        	
        	// SANITY CHECK
        	checkEmailAddresses(newsletterEmailBean);
        	
        	Map<String, Object> model = new HashMap<String, Object>();
          
        	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        	java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
        	model.put(CURRENT_DATE, dateFormatter.format(currentDate));
        	model.put("newsletterEmailBean", newsletterEmailBean);
        	model.put(WORDING, coreMessageSource.loadWording(Email.WORDING_SCOPE_EMAIL, locale));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put(RequestConstants.REQUEST_PARAMETER_NEWSLETTER_EMAIL, URLEncoder.encode(newsletterEmailBean.getToEmail(), Constants.ANSI));
			String subscribeUrl = urlService.generateUrl(FoUrls.NEWSLETTER_REGISTER, requestData, urlParams);
        	model.put("subscribeUrl", urlService.buildAbsoluteUrl(requestData, subscribeUrl));
        	
    		String unsubscribeUrl = urlService.generateUrl(FoUrls.NEWSLETTER_UNREGISTER, requestData, urlParams);
    		String fullUnsubscribeUrl = urlService.buildAbsoluteUrl(requestData, unsubscribeUrl);
    		
        	model.put("unsubscribeUrlOrEmail", fullUnsubscribeUrl);
        	
        	String fromAddress = handleFromAddress(newsletterEmailBean.getFromAddress(), contextNameValue);
            String fromName = handleFromName(newsletterEmailBean.getFromName(), locale);
            String toEmail = newsletterEmailBean.getToEmail();
            
        	MimeMessagePreparatorImpl mimeMessagePreparator = getMimeMessagePreparator(requestData, Email.EMAIl_TYPE_NEWSLETTER_SUBSCRIPTION, model);
//        	mimeMessagePreparator.setUnsubscribeUrlOrEmail(fullUnsubscribeUrl);
        	mimeMessagePreparator.setTo(toEmail);
        	mimeMessagePreparator.setFrom(fromAddress);
        	mimeMessagePreparator.setFromName(fromName);
        	mimeMessagePreparator.setReplyTo(fromAddress);
        	Object[] parameters = {};
        	mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.newsletter_unsubscription.email_subject", parameters, locale));
        	mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "newsletter-unsubscription-confirmation-html-content.vm", model));
        	mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "newsletter-unsubscription-confirmation-text-content.vm", model));
        	
        	email = new Email();
        	email.setType(Email.EMAIl_TYPE_NEWSLETTER_SUBSCRIPTION);
        	email.setStatus(Email.EMAIl_STATUS_PENDING);
        	saveOrUpdateEmail(email, mimeMessagePreparator);
        	
        } catch (MailException e) {
        	logger.error("Error, can't save the message :", e);
        	throw e;
        } catch (VelocityException e) {
        	logger.error("Error, can't build the message :", e);
        	throw e;
        } catch (IOException e) {
        	logger.error("Error, can't serializable the message :", e);
        	throw e;
        }
        return email;
    }
    
    /**
     * @throws Exception 
     */
    public Email buildAndSaveCustomerNewAccountMail(final RequestData requestData, final String velocityPath, 
    											    final CustomerNewAccountConfirmationEmailBean customerNewAccountConfirmationEmailBean) throws Exception {
        Email email = null;
        try {
            final String contextNameValue = requestData.getContextNameValue();
        	final Localization localization = requestData.getMarketAreaLocalization();
        	final Locale locale = localization.getLocale();
        	
        	// SANITY CHECK
        	checkEmailAddresses(customerNewAccountConfirmationEmailBean);
        	
        	Map<String, Object> model = new HashMap<String, Object>();
          
        	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        	java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
        	model.put(CURRENT_DATE, dateFormatter.format(currentDate));
        	model.put("customerNewAccountConfirmationEmailBean", customerNewAccountConfirmationEmailBean);
        	model.put(WORDING, coreMessageSource.loadWording(Email.WORDING_SCOPE_EMAIL, locale));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put(RequestConstants.REQUEST_PARAMETER_NEW_CUSTOMER_VALIDATION_EMAIL, URLEncoder.encode(customerNewAccountConfirmationEmailBean.getEmail(), Constants.ANSI));
			urlParams.put(RequestConstants.REQUEST_PARAMETER_NEW_ACCOUNT_VALIDATION_TOKEN, UUID.randomUUID().toString());
			String resetPasswordUrl = urlService.generateUrl(FoUrls.CUSTOMER_NEW_ACCOUNT_VALIDATION, requestData, urlParams);
        	model.put("newCustomerValidationUrl", urlService.buildAbsoluteUrl(requestData, resetPasswordUrl));

        	String fromAddress = handleFromAddress(customerNewAccountConfirmationEmailBean.getFromAddress(), contextNameValue);
            String fromName = handleFromName(customerNewAccountConfirmationEmailBean.getFromName(), locale);
            String toEmail = customerNewAccountConfirmationEmailBean.getToEmail();
            
        	MimeMessagePreparatorImpl mimeMessagePreparator = getMimeMessagePreparator(requestData, Email.EMAIl_TYPE_NEW_ACCOUNT_CONFIRMATION, model);
        	mimeMessagePreparator.setTo(toEmail);
        	mimeMessagePreparator.setFrom(fromAddress);
        	mimeMessagePreparator.setFromName(fromName);
        	mimeMessagePreparator.setReplyTo(fromAddress);
        	Object[] parameters = {customerNewAccountConfirmationEmailBean.getLastname(), customerNewAccountConfirmationEmailBean.getFirstname()};
        	mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.new_account.email_subject", parameters, locale));
        	mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "new-account-confirmation-html-content.vm", model));
        	mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "new-account-confirmation-text-content.vm", model));
        	
        	email = new Email();
        	email.setType(Email.EMAIl_TYPE_NEW_ACCOUNT_CONFIRMATION);
        	email.setStatus(Email.EMAIl_STATUS_PENDING);
        	saveOrUpdateEmail(email, mimeMessagePreparator);
        	
        } catch (MailException e) {
        	logger.error("Error, can't save the message :", e);
        	throw e;
        } catch (VelocityException e) {
        	logger.error("Error, can't build the message :", e);
        	throw e;
        } catch (IOException e) {
        	logger.error("Error, can't serializable the message :", e);
        	throw e;
        }
        return email;
    }
    
    /**
     * @throws Exception 
     */
    public Email buildAndSaveCustomerForgottenPasswordMail(final RequestData requestData, final Customer customer, final String velocityPath, 
    													   final CustomerForgottenPasswordEmailBean customerForgottenPasswordEmailBean) throws Exception {
        Email email = null;
        try {
            final String contextNameValue = requestData.getContextNameValue();
        	final Localization localization = requestData.getMarketAreaLocalization();
        	final Locale locale = localization.getLocale();
        	
        	// SANITY CHECK
        	checkEmailAddresses(customerForgottenPasswordEmailBean);
        	
        	Map<String, Object> model = new HashMap<String, Object>();
          
        	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        	java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
        	model.put(CURRENT_DATE, dateFormatter.format(currentDate));
        	model.put(CUSTOMER, customer);
        	model.put("customerForgottenPasswordEmailBean", customerForgottenPasswordEmailBean);
        	model.put(WORDING, coreMessageSource.loadWording(Email.WORDING_SCOPE_EMAIL, locale));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put(RequestConstants.REQUEST_PARAMETER_PASSWORD_RESET_EMAIL, URLEncoder.encode(customer.getEmail(), Constants.ANSI));
			urlParams.put(RequestConstants.REQUEST_PARAMETER_PASSWORD_RESET_TOKEN, customerForgottenPasswordEmailBean.getToken());
			String resetPasswordUrl = urlService.generateUrl(FoUrls.RESET_PASSWORD, requestData, urlParams);
        	model.put("activeChangePasswordUrl", urlService.buildAbsoluteUrl(requestData, resetPasswordUrl));
        	
			String canceResetPasswordUrl = urlService.generateUrl(FoUrls.CANCEL_RESET_PASSWORD, requestData, urlParams);
        	model.put("cancelChangePasswordUrl", urlService.buildAbsoluteUrl(requestData, canceResetPasswordUrl));
        	
        	String fromAddress = handleFromAddress(customerForgottenPasswordEmailBean.getFromAddress(), contextNameValue);
            String fromName = handleFromName(customerForgottenPasswordEmailBean.getFromName(), locale);
            String toEmail = customer.getEmail();
            
        	MimeMessagePreparatorImpl mimeMessagePreparator = getMimeMessagePreparator(requestData, Email.EMAIl_TYPE_FORGOTTEN_PASSWORD, model);
        	mimeMessagePreparator.setTo(toEmail);
        	mimeMessagePreparator.setFrom(fromAddress);
        	mimeMessagePreparator.setFromName(fromName);
        	mimeMessagePreparator.setReplyTo(fromAddress);
        	Object[] parameters = {customer.getLastname(), customer.getFirstname()};
        	mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.forgotten_password.email_subject", parameters, locale));
        	mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "forgotten-password-html-content.vm", model));
        	mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "forgotten-password-text-content.vm", model));
        	
        	email = new Email();
        	email.setType(Email.EMAIl_TYPE_FORGOTTEN_PASSWORD);
        	email.setStatus(Email.EMAIl_STATUS_PENDING);
        	saveOrUpdateEmail(email, mimeMessagePreparator);
        	
        } catch (MailException e) {
        	logger.error("Error, can't save the message :", e);
        	throw e;
        } catch (VelocityException e) {
        	logger.error("Error, can't build the message :", e);
        	throw e;
        } catch (IOException e) {
        	logger.error("Error, can't serializable the message :", e);
        	throw e;
        }
        return email;
    }
    
    /**
     * @throws Exception 
     */
    public Email buildAndSaveCustomerResetPasswordConfirmationMail(final RequestData requestData, final Customer customer, final String velocityPath, 
    															   final CustomerResetPasswordConfirmationEmailBean customerResetPasswordConfirmationEmailBean) throws Exception {
        Email email = null;
        try {
            final String contextNameValue = requestData.getContextNameValue();
        	final Localization localization = requestData.getMarketAreaLocalization();
        	final Locale locale = localization.getLocale();
        	
        	// SANITY CHECK
        	checkEmailAddresses(customerResetPasswordConfirmationEmailBean);
        	
        	Map<String, Object> model = new HashMap<String, Object>();
          
        	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        	java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
        	model.put(CURRENT_DATE, dateFormatter.format(currentDate));
        	model.put(CUSTOMER, customer);
        	model.put("customerResetPasswordConfirmationEmailBean", customerResetPasswordConfirmationEmailBean);
        	model.put(WORDING, coreMessageSource.loadWording(Email.WORDING_SCOPE_EMAIL, locale));

			String loginUrl = urlService.generateUrl(FoUrls.LOGIN, requestData);
        	model.put("loginUrl", urlService.buildAbsoluteUrl(requestData, loginUrl));
        	
        	String fromAddress = handleFromAddress(customerResetPasswordConfirmationEmailBean.getFromAddress(), contextNameValue);
            String fromName = handleFromName(customerResetPasswordConfirmationEmailBean.getFromName(), locale);
            String toEmail = customer.getEmail();
            
        	MimeMessagePreparatorImpl mimeMessagePreparator = getMimeMessagePreparator(requestData, Email.EMAIl_TYPE_RESET_PASSWORD_CONFIRMATION, model);
        	mimeMessagePreparator.setTo(toEmail);
        	mimeMessagePreparator.setFrom(fromAddress);
        	mimeMessagePreparator.setFromName(fromName);
        	mimeMessagePreparator.setReplyTo(fromAddress);
        	Object[] parameters = {customer.getLastname(), customer.getFirstname()};
        	mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.reset_password_confirmation.email_subject", parameters, locale));
        	mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "reset-password-confirmation-html-content.vm", model));
        	mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "reset-password-confirmation-text-content.vm", model));
        	
        	email = new Email();
        	email.setType(Email.EMAIl_TYPE_RESET_PASSWORD_CONFIRMATION);
        	email.setStatus(Email.EMAIl_STATUS_PENDING);
        	saveOrUpdateEmail(email, mimeMessagePreparator);
        	
        } catch (MailException e) {
        	logger.error("Error, can't save the message :", e);
        	throw e;
        } catch (VelocityException e) {
        	logger.error("Error, can't build the message :", e);
        	throw e;
        } catch (IOException e) {
        	logger.error("Error, can't serializable the message :", e);
        	throw e;
        }
        return email;
    }
    
    /**
     * @throws Exception 
     */
    public Email buildAndSaveUserNewAccountMail(final RequestData requestData, final String velocityPath, 
                                                final UserNewAccountConfirmationEmailBean userNewAccountConfirmationEmailBean) throws Exception {
        Email email = null;
        try {
            final String contextNameValue = requestData.getContextNameValue();
            final Localization localization = requestData.getMarketAreaLocalization();
            final Locale locale = localization.getLocale();
            
            // SANITY CHECK
            checkEmailAddresses(userNewAccountConfirmationEmailBean);
            
            Map<String, Object> model = new HashMap<String, Object>();
          
            DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
            java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
            model.put(CURRENT_DATE, dateFormatter.format(currentDate));
            model.put("userNewAccountConfirmationEmailBean", userNewAccountConfirmationEmailBean);
            model.put(WORDING, coreMessageSource.loadWording(Email.WORDING_SCOPE_EMAIL, locale));

            Map<String, String> urlParams = new HashMap<String, String>();
            urlParams.put(RequestConstants.REQUEST_PARAMETER_NEW_CUSTOMER_VALIDATION_EMAIL, URLEncoder.encode(userNewAccountConfirmationEmailBean.getEmail(), Constants.ANSI));
            urlParams.put(RequestConstants.REQUEST_PARAMETER_NEW_ACCOUNT_VALIDATION_TOKEN, UUID.randomUUID().toString());
            String resetPasswordUrl = urlService.generateUrl(FoUrls.CUSTOMER_NEW_ACCOUNT_VALIDATION, requestData, urlParams);
            model.put("newUserValidationUrl", urlService.buildAbsoluteUrl(requestData, resetPasswordUrl));

            String fromAddress = handleFromAddress(userNewAccountConfirmationEmailBean.getFromAddress(), contextNameValue);
            String fromName = handleFromName(userNewAccountConfirmationEmailBean.getFromName(), locale);
            String toEmail = userNewAccountConfirmationEmailBean.getToEmail();
            
            MimeMessagePreparatorImpl mimeMessagePreparator = getMimeMessagePreparator(requestData, Email.EMAIl_TYPE_NEW_ACCOUNT_CONFIRMATION, model);
            mimeMessagePreparator.setTo(toEmail);
            mimeMessagePreparator.setFrom(fromAddress);
            mimeMessagePreparator.setFromName(fromName);
            mimeMessagePreparator.setReplyTo(fromAddress);
            Object[] parameters = {userNewAccountConfirmationEmailBean.getLastname(), userNewAccountConfirmationEmailBean.getFirstname()};
            mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.new_account.email_subject", parameters, locale));
            mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "new-account-confirmation-html-content.vm", model));
            mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "new-account-confirmation-text-content.vm", model));
            
            email = new Email();
            email.setType(Email.EMAIl_TYPE_NEW_ACCOUNT_CONFIRMATION);
            email.setStatus(Email.EMAIl_STATUS_PENDING);
            saveOrUpdateEmail(email, mimeMessagePreparator);
            
        } catch (MailException e) {
            logger.error("Error, can't save the message :", e);
            throw e;
        } catch (VelocityException e) {
            logger.error("Error, can't build the message :", e);
            throw e;
        } catch (IOException e) {
            logger.error("Error, can't serializable the message :", e);
            throw e;
        }
        return email;
    }
    
    /**
     * @throws Exception 
     */
    public Email buildAndSaveUserForgottenPasswordMail(final RequestData requestData, final User user, final String velocityPath, 
                                                       final UserForgottenPasswordEmailBean userForgottenPasswordEmailBean) throws Exception {
        Email email = null;
        try {
            final String contextNameValue = requestData.getContextNameValue();
            final Localization localization = requestData.getMarketAreaLocalization();
            final Locale locale = localization.getLocale();
            
            // SANITY CHECK
            checkEmailAddresses(userForgottenPasswordEmailBean);
            
            Map<String, Object> model = new HashMap<String, Object>();
          
            DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
            java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
            model.put(CURRENT_DATE, dateFormatter.format(currentDate));
            model.put(CUSTOMER, user);
            model.put("userForgottenPasswordEmailBean", userForgottenPasswordEmailBean);
            model.put(WORDING, coreMessageSource.loadWording(Email.WORDING_SCOPE_EMAIL, locale));

            Map<String, String> urlParams = new HashMap<String, String>();
            urlParams.put(RequestConstants.REQUEST_PARAMETER_PASSWORD_RESET_EMAIL, URLEncoder.encode(user.getEmail(), Constants.ANSI));
            urlParams.put(RequestConstants.REQUEST_PARAMETER_PASSWORD_RESET_TOKEN, userForgottenPasswordEmailBean.getToken());
            String resetPasswordUrl = backofficeUrlService.generateUrl(BoUrls.RESET_PASSWORD, requestData, urlParams);
            model.put("activeChangePasswordUrl", backofficeUrlService.buildAbsoluteUrl(requestData, resetPasswordUrl));
            
            String canceResetPasswordUrl = backofficeUrlService.generateUrl(BoUrls.CANCEL_RESET_PASSWORD, requestData, urlParams);
            model.put("cancelChangePasswordUrl", backofficeUrlService.buildAbsoluteUrl(requestData, canceResetPasswordUrl));
            
            model.put("userForgottenPasswordEmailBean", userForgottenPasswordEmailBean);

            String fromAddress = handleFromAddress(userForgottenPasswordEmailBean.getFromAddress(), contextNameValue);
            String fromName = handleFromName(userForgottenPasswordEmailBean.getFromName(), locale);
            String toEmail = user.getEmail();
            
            MimeMessagePreparatorImpl mimeMessagePreparator = getMimeMessagePreparator(requestData, Email.EMAIl_TYPE_FORGOTTEN_PASSWORD, model);
            mimeMessagePreparator.setTo(toEmail);
            mimeMessagePreparator.setFrom(fromAddress);
            mimeMessagePreparator.setFromName(fromName);
            mimeMessagePreparator.setReplyTo(fromAddress);
            Object[] parameters = {user.getLastname(), user.getFirstname()};
            mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.forgotten_password.email_subject", parameters, locale));
            mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "forgotten-password-html-content.vm", model));
            mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "forgotten-password-text-content.vm", model));
            
            email = new Email();
            email.setType(Email.EMAIl_TYPE_FORGOTTEN_PASSWORD);
            email.setStatus(Email.EMAIl_STATUS_PENDING);
            saveOrUpdateEmail(email, mimeMessagePreparator);
            
        } catch (MailException e) {
            logger.error("Error, can't save the message :", e);
            throw e;
        } catch (VelocityException e) {
            logger.error("Error, can't build the message :", e);
            throw e;
        } catch (IOException e) {
            logger.error("Error, can't serializable the message :", e);
            throw e;
        }
        return email;
    }
    
    /**
     * @throws Exception 
     */
    public Email buildAndSaveUserResetPasswordConfirmationMail(final RequestData requestData, final User user, final String velocityPath, 
                                                               final UserResetPasswordConfirmationEmailBean userResetPasswordConfirmationEmailBean) throws Exception {
        Email email = null;
        try {
            final String contextNameValue = requestData.getContextNameValue();
            final Localization localization = requestData.getMarketAreaLocalization();
            final Locale locale = localization.getLocale();
            
            // SANITY CHECK
            checkEmailAddresses(userResetPasswordConfirmationEmailBean);
            
            Map<String, Object> model = new HashMap<String, Object>();
          
            DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
            java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
            model.put(CURRENT_DATE, dateFormatter.format(currentDate));
            model.put(CUSTOMER, user);
            model.put("userResetPasswordConfirmationEmailBean", userResetPasswordConfirmationEmailBean);
            model.put(WORDING, coreMessageSource.loadWording(Email.WORDING_SCOPE_EMAIL, locale));

            String loginUrl = backofficeUrlService.generateUrl(BoUrls.LOGIN, requestData);
            model.put("loginUrl", backofficeUrlService.buildAbsoluteUrl(requestData, loginUrl));
            
            String fromAddress = handleFromAddress(userResetPasswordConfirmationEmailBean.getFromAddress(), contextNameValue);
            String fromName = handleFromName(userResetPasswordConfirmationEmailBean.getFromName(), locale);
            String toEmail = user.getEmail();
            
            MimeMessagePreparatorImpl mimeMessagePreparator = getMimeMessagePreparator(requestData, Email.EMAIl_TYPE_RESET_PASSWORD_CONFIRMATION, model);
            mimeMessagePreparator.setTo(toEmail);
            mimeMessagePreparator.setFrom(fromAddress);
            mimeMessagePreparator.setFromName(fromName);
            mimeMessagePreparator.setReplyTo(fromAddress);
            Object[] parameters = {user.getLastname(), user.getFirstname()};
            mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.reset_password_confirmation.email_subject", parameters, locale));
            mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "reset-password-confirmation-html-content.vm", model));
            mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "reset-password-confirmation-text-content.vm", model));
            
            email = new Email();
            email.setType(Email.EMAIl_TYPE_RESET_PASSWORD_CONFIRMATION);
            email.setStatus(Email.EMAIl_STATUS_PENDING);
            saveOrUpdateEmail(email, mimeMessagePreparator);
            
        } catch (MailException e) {
            logger.error("Error, can't save the message :", e);
            throw e;
        } catch (VelocityException e) {
            logger.error("Error, can't build the message :", e);
            throw e;
        } catch (IOException e) {
            logger.error("Error, can't serializable the message :", e);
            throw e;
        }
        return email;
    }
    
    /**
     * @throws Exception 
     */
    public Email buildAndSaveNewOrderConfirmationMail(final RequestData requestData, final Customer customer, final String velocityPath, 
    												  final OrderConfirmationEmailBean orderConfirmationEmailBean) throws Exception {
        Email email = null;
        try {
            final String contextNameValue = requestData.getContextNameValue();
        	final Localization localization = requestData.getMarketAreaLocalization();
        	final Locale locale = localization.getLocale();
        	
        	// SANITY CHECK
        	checkEmailAddresses(orderConfirmationEmailBean);
        	
        	Map<String, Object> model = new HashMap<String, Object>();
          
        	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        	java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
        	model.put(CURRENT_DATE, dateFormatter.format(currentDate));
        	model.put(CUSTOMER, customer);
        	model.put("orderConfirmationEmailBean", orderConfirmationEmailBean);
        	model.put(WORDING, coreMessageSource.loadWording(Email.WORDING_SCOPE_EMAIL, locale));

        	String fromAddress = handleFromAddress(orderConfirmationEmailBean.getFromAddress(), contextNameValue);
            String fromName = handleFromName(orderConfirmationEmailBean.getFromName(), locale);
            String toEmail = customer.getEmail();
            
        	MimeMessagePreparatorImpl mimeMessagePreparator = getMimeMessagePreparator(requestData, Email.EMAIl_TYPE_ORDER_CONFIRMATION, model);
        	mimeMessagePreparator.setTo(toEmail);
        	mimeMessagePreparator.setFrom(fromAddress);
        	mimeMessagePreparator.setFromName(fromName);
        	mimeMessagePreparator.setReplyTo(fromAddress);
        	Object[] parameters = {customer.getLastname(), customer.getFirstname()};
        	mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.order.confirmation_email_subject", parameters, locale));
        	mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "order-confirmation-html-content.vm", model));
        	mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "order-confirmation-text-content.vm", model));
        	
        	email = new Email();
        	email.setType(Email.EMAIl_TYPE_ORDER_CONFIRMATION);
        	email.setStatus(Email.EMAIl_STATUS_PENDING);
        	saveOrUpdateEmail(email, mimeMessagePreparator);
        	
        } catch (MailException e) {
        	logger.error("Error, can't save the message :", e);
        	throw e;
        } catch (VelocityException e) {
        	logger.error("Error, can't build the message :", e);
        	throw e;
        } catch (IOException e) {
        	logger.error("Error, can't serializable the message :", e);
        	throw e;
        }
        return email;
    }
    
    /**
     * @throws Exception 
     */
    public Email buildAndSaveOrderShippedConfirmationMail(final RequestData requestData, final Customer customer, final String velocityPath, 
    													  final OrderSentConfirmationEmailBean orderSentConfirmationEmailBean) throws Exception {
        Email email = null;
        try {
            final String contextNameValue = requestData.getContextNameValue();
        	final Localization localization = requestData.getMarketAreaLocalization();
        	final Locale locale = localization.getLocale();
        	
        	// SANITY CHECK
        	checkEmailAddresses(orderSentConfirmationEmailBean);
        	
        	Map<String, Object> model = new HashMap<String, Object>();
          
        	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        	java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
        	model.put(CURRENT_DATE, dateFormatter.format(currentDate));
        	model.put(CUSTOMER, customer);
        	model.put("orderSentConfirmationEmailBean", orderSentConfirmationEmailBean);
        	model.put(WORDING, coreMessageSource.loadWording(Email.WORDING_SCOPE_EMAIL, locale));

        	String fromAddress = handleFromAddress(orderSentConfirmationEmailBean.getFromAddress(), contextNameValue);
            String fromName = handleFromName(orderSentConfirmationEmailBean.getFromName(), locale);
            String toEmail = customer.getEmail();
            
        	MimeMessagePreparatorImpl mimeMessagePreparator = getMimeMessagePreparator(requestData, Email.EMAIl_TYPE_ORDER_SHIPPED, model);
        	mimeMessagePreparator.setTo(toEmail);
        	mimeMessagePreparator.setFrom(fromAddress);
        	mimeMessagePreparator.setFromName(fromName);
        	mimeMessagePreparator.setReplyTo(fromAddress);
        	Object[] parameters = {customer.getLastname(), customer.getFirstname()};
        	mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.order_shipped.shipped_email_subject", parameters, locale));
        	mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "order-shipped-html-content.vm", model));
        	mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "order-shipped-text-content.vm", model));
        	
        	email = new Email();
        	email.setType(Email.EMAIl_TYPE_ORDER_SHIPPED);
        	email.setStatus(Email.EMAIl_STATUS_PENDING);
        	saveOrUpdateEmail(email, mimeMessagePreparator);
        	
        } catch (MailException e) {
        	logger.error("Error, can't save the message :", e);
        	throw e;
        } catch (VelocityException e) {
        	logger.error("Error, can't build the message :", e);
        	throw e;
        } catch (IOException e) {
        	logger.error("Error, can't serializable the message :", e);
        	throw e;
        }
        return email;
    }
    
    /**
     * @throws Exception 
     */
    public Email buildAndSaveAbandonedShoppingCartMail(final RequestData requestData, final Customer customer, final String velocityPath, 
    												   final AbandonedShoppingCartEmailBean abandonedShoppingCartEmailBean) throws Exception {
        Email email = null;
        try {
            final String contextNameValue = requestData.getContextNameValue();
        	final Localization localization = requestData.getMarketAreaLocalization();
        	final Locale locale = localization.getLocale();
        	
        	// SANITY CHECK
        	checkEmailAddresses(abandonedShoppingCartEmailBean);
        	
        	Map<String, Object> model = new HashMap<String, Object>();
          
        	DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        	java.sql.Timestamp currentDate = new java.sql.Timestamp((new java.util.Date()).getTime());
        	model.put(CURRENT_DATE, dateFormatter.format(currentDate));
        	model.put(CUSTOMER, customer);
        	model.put("abandonedShoppingCartEmailBean", abandonedShoppingCartEmailBean);
        	model.put(WORDING, coreMessageSource.loadWording(Email.WORDING_SCOPE_EMAIL, locale));

        	String fromAddress = handleFromAddress(abandonedShoppingCartEmailBean.getFromAddress(), contextNameValue);
            String fromName = handleFromName(abandonedShoppingCartEmailBean.getFromName(), locale);
            String toEmail = customer.getEmail();
            
        	MimeMessagePreparatorImpl mimeMessagePreparator = getMimeMessagePreparator(requestData, Email.EMAIl_TYPE_ABANDONED_SHOPPING_CART, model);
        	mimeMessagePreparator.setTo(toEmail);
        	mimeMessagePreparator.setFrom(fromAddress);
        	mimeMessagePreparator.setFromName(fromName);
        	mimeMessagePreparator.setReplyTo(fromAddress);
        	Object[] parameters = {customer.getLastname(), customer.getFirstname()};
        	mimeMessagePreparator.setSubject(coreMessageSource.getMessage("email.abandoned_shopping_cart.email_subject", parameters, locale));
        	mimeMessagePreparator.setHtmlContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "abandoned-shopping-cart-html-content.vm", model));
        	mimeMessagePreparator.setPlainTextContent(VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), velocityPath + "abandoned-shopping-cart-text-content.vm", model));
        	
        	email = new Email();
        	email.setType(Email.EMAIl_TYPE_ABANDONED_SHOPPING_CART);
        	email.setStatus(Email.EMAIl_STATUS_PENDING);
        	saveOrUpdateEmail(email, mimeMessagePreparator);
        	
        } catch (MailException e) {
        	logger.error("Error, can't save the message :", e);
        	throw e;
        } catch (VelocityException e) {
        	logger.error("Error, can't build the message :", e);
        	throw e;
        } catch (IOException e) {
        	logger.error("Error, can't serializable the message :", e);
        	throw e;
        }
        return email;
    }
    
    /**
     * @throws Exception 
     */
    public String getEmailFromAddress(final RequestData requestData, final MarketArea marketArea, final String currentContextNameValue, 
                                      final String targetContextNameValue, final String emailType) throws Exception{
        String emailFromAddress = marketArea.getEmailFromAddress(targetContextNameValue, emailType);
        if(StringUtils.isEmpty(emailFromAddress) && StringUtils.isNotEmpty(currentContextNameValue)){
            emailFromAddress = engineSettingService.getDefaultEmailAddress(currentContextNameValue);
        }
        return emailFromAddress;
    }
    
    protected String handleFromAddress(String emailFromAddress, String contextValue){
        if(StringUtils.isEmpty(emailFromAddress)){
            emailFromAddress = engineSettingService.getDefaultEmailAddress(contextValue);
        }
        return emailFromAddress;
    }
    
    protected String handleFromName(String fromName, Locale locale){
        if(StringUtils.isEmpty(fromName)){
            fromName = coreMessageSource.getMessage("email.common.from_name", locale);
        }
        return fromName;
    }
    
    protected MimeMessagePreparatorImpl getMimeMessagePreparator(final RequestData requestData, final String emailType, final Map<String, Object> model) throws Exception{
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
    		String mirroringFilePath = FilenameUtils.separatorsToUnix(emailFileMirroringFolderPath + filePath);
    		mimeMessagePreparator.setMirroringFilePath(mirroringFilePath);
    		
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

    protected void checkEmailAddresses(AbstractEmailBean emailBean) throws EmailProcessException{
		if(StringUtils.isEmpty(emailBean.getFromAddress())){
			throw new EmailProcessException(EmailProcessException.EMAIl_FROM_ADDRESS_IS_EMPTY);
		}
		
		if(StringUtils.isEmpty(emailBean.getToEmail())){
			throw new EmailProcessException(EmailProcessException.EMAIl_TO_ADDRESS_IS_EMPTY);
		}
		
		if(StringUtils.isEmpty(emailBean.getReplyToEmail())){
			throw new EmailProcessException(EmailProcessException.EMAIl_REPLY_TO_ADDRESS_IS_EMPTY);
		}
    }
    
    protected VelocityEngine getVelocityEngine(){
        return velocityConfigurer.getVelocityEngine();
    }
    
}