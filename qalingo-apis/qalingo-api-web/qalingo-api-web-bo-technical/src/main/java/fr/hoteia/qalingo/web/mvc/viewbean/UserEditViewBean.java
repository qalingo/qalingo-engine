/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.viewbean;

import java.io.Serializable;

public class UserEditViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -4064606912989921876L;
	
	private Long id;
	private String loginLabel;
	private String firstnameLabel;
	private String lastnameLabel;
	private String emailLabel;
	private String passwordLabel;
	private String activeLabel;
	private String dateCreateLabel;
	private String dateUpdateLabel;
	
	private String backUrl;
	private String cancelLabel;
	private String formSubmitUrl;
	private String submitLabel;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginLabel() {
		return loginLabel;
	}

	public void setLoginLabel(String loginLabel) {
		this.loginLabel = loginLabel;
	}

	public String getFirstnameLabel() {
		return firstnameLabel;
	}

	public void setFirstnameLabel(String firstnameLabel) {
		this.firstnameLabel = firstnameLabel;
	}

	public String getLastnameLabel() {
		return lastnameLabel;
	}

	public void setLastnameLabel(String lastnameLabel) {
		this.lastnameLabel = lastnameLabel;
	}

	public String getEmailLabel() {
		return emailLabel;
	}

	public void setEmailLabel(String emailLabel) {
		this.emailLabel = emailLabel;
	}

	public String getPasswordLabel() {
		return passwordLabel;
	}

	public void setPasswordLabel(String passwordLabel) {
		this.passwordLabel = passwordLabel;
	}

	public String getActiveLabel() {
		return activeLabel;
	}

	public void setActiveLabel(String activeLabel) {
		this.activeLabel = activeLabel;
	}

	public String getDateCreateLabel() {
		return dateCreateLabel;
	}

	public void setDateCreateLabel(String dateCreateLabel) {
		this.dateCreateLabel = dateCreateLabel;
	}

	public String getDateUpdateLabel() {
		return dateUpdateLabel;
	}

	public void setDateUpdateLabel(String dateUpdateLabel) {
		this.dateUpdateLabel = dateUpdateLabel;
	}

	public String getBackUrl() {
		return backUrl;
	}
	
	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}
	
	public String getCancelLabel() {
		return cancelLabel;
	}
	
	public void setCancelLabel(String cancelLabel) {
		this.cancelLabel = cancelLabel;
	}
	
	public String getFormSubmitUrl() {
		return formSubmitUrl;
	}
	
	public void setFormSubmitUrl(String formSubmitUrl) {
		this.formSubmitUrl = formSubmitUrl;
	}
	
	public String getSubmitLabel() {
		return submitLabel;
	}
	
	public void setSubmitLabel(String submitLabel) {
		this.submitLabel = submitLabel;
	}

}