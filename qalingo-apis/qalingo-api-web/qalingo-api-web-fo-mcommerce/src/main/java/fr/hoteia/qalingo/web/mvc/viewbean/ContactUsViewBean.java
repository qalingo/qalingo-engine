/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.viewbean;

import java.io.Serializable;

public class ContactUsViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 3367453489367832074L;
	
	private String lastnameLabel;
    private String firstnameLabel;
    private String countryLabel;
    private String emailLabel;
    private String phoneLabel;
    private String faxLabel;
    private String mobileLabel;
    private String websiteLabel;
    private String subjectLabel;
    private String messageLabel;

    private String submitLabel;
    private String cancelLabel;
    
    private String backUrl;
    
    private String successMessage;
    private String failMessage;
    
	public String getLastnameLabel() {
		return lastnameLabel;
	}
	
	public void setLastnameLabel(String lastnameLabel) {
		this.lastnameLabel = lastnameLabel;
	}
	
	public String getFirstnameLabel() {
		return firstnameLabel;
	}
	
	public void setFirstnameLabel(String firstnameLabel) {
		this.firstnameLabel = firstnameLabel;
	}
	
	public String getCountryLabel() {
		return countryLabel;
	}
	
	public void setCountryLabel(String countryLabel) {
		this.countryLabel = countryLabel;
	}
	
	public String getEmailLabel() {
		return emailLabel;
	}
	public void setEmailLabel(String emailLabel) {
		this.emailLabel = emailLabel;
	}
	
	public String getPhoneLabel() {
		return phoneLabel;
	}
	
	public void setPhoneLabel(String phoneLabel) {
		this.phoneLabel = phoneLabel;
	}
	
	public String getFaxLabel() {
		return faxLabel;
	}
	
	public void setFaxLabel(String faxLabel) {
		this.faxLabel = faxLabel;
	}
	
	public String getMobileLabel() {
		return mobileLabel;
	}
	
	public void setMobileLabel(String mobileLabel) {
		this.mobileLabel = mobileLabel;
	}
	
	public String getWebsiteLabel() {
		return websiteLabel;
	}
	
	public void setWebsiteLabel(String websiteLabel) {
		this.websiteLabel = websiteLabel;
	}
	
	public String getSubjectLabel() {
		return subjectLabel;
	}
	
	public void setSubjectLabel(String subjectLabel) {
		this.subjectLabel = subjectLabel;
	}
	
	public String getMessageLabel() {
		return messageLabel;
	}
	
	public void setMessageLabel(String messageLabel) {
		this.messageLabel = messageLabel;
	}

	public String getSubmitLabel() {
		return submitLabel;
	}

	public void setSubmitLabel(String submitLabel) {
		this.submitLabel = submitLabel;
	}

	public String getCancelLabel() {
		return cancelLabel;
	}

	public void setCancelLabel(String cancelLabel) {
		this.cancelLabel = cancelLabel;
	}
	
	public String getBackUrl() {
		return backUrl;
	}
	
	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}
	
	public String getSuccessMessage() {
		return successMessage;
	}
	
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}
	
	public String getFailMessage() {
		return failMessage;
	}
	
	public void setFailMessage(String failMessage) {
		this.failMessage = failMessage;
	}
	
}