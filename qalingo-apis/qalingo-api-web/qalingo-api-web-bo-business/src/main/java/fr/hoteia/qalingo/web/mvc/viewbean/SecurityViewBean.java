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

public class SecurityViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -6903293893445728139L;
	
	private String logoutPageTitle;
	private String logoutPageText;
	
	private String loginPageTitle;
	private String loginPageText;

	private String forbiddenPageTitle;
	private String forbiddenPageText;

	private String timeoutPageTitle;
	private String timeoutPageText;

	private String forgottenPasswordPageTitle;
	private String forgottenPasswordPageText;
	
    private String loginFormTitle;
	private String loginUrl;
    private String loginLabel;
    
    private String forgottenPasswordUrl;
    private String forgottenPasswordLabel;
    private String emailOrLoginLabel;
    private String forgottenPasswordEmailSucces;
    
    private String passwordLabel;
    private String rememberLabel;
    private String submitLabel;
    
    public String getLogoutPageTitle() {
		return logoutPageTitle;
	}

	public void setLogoutPageTitle(String logoutPageTitle) {
		this.logoutPageTitle = logoutPageTitle;
	}

	public String getLogoutPageText() {
		return logoutPageText;
	}

	public void setLogoutPageText(String logoutPageText) {
		this.logoutPageText = logoutPageText;
	}

	public String getLoginPageTitle() {
		return loginPageTitle;
	}

	public void setLoginPageTitle(String loginPageTitle) {
		this.loginPageTitle = loginPageTitle;
	}

	public String getLoginPageText() {
		return loginPageText;
	}

	public void setLoginPageText(String loginPageText) {
		this.loginPageText = loginPageText;
	}

	public String getForbiddenPageTitle() {
		return forbiddenPageTitle;
	}

	public void setForbiddenPageTitle(String forbiddenPageTitle) {
		this.forbiddenPageTitle = forbiddenPageTitle;
	}

	public String getForbiddenPageText() {
		return forbiddenPageText;
	}

	public void setForbiddenPageText(String forbiddenPageText) {
		this.forbiddenPageText = forbiddenPageText;
	}

	public String getTimeoutPageTitle() {
		return timeoutPageTitle;
	}

	public void setTimeoutPageTitle(String timeoutPageTitle) {
		this.timeoutPageTitle = timeoutPageTitle;
	}

	public String getTimeoutPageText() {
		return timeoutPageText;
	}

	public void setTimeoutPageText(String timeoutPageText) {
		this.timeoutPageText = timeoutPageText;
	}

	public String getForgottenPasswordPageTitle() {
		return forgottenPasswordPageTitle;
	}
	
	public void setForgottenPasswordPageTitle(String forgottenPasswordPageTitle) {
		this.forgottenPasswordPageTitle = forgottenPasswordPageTitle;
	}
	
	public String getForgottenPasswordPageText() {
		return forgottenPasswordPageText;
	}
	
	public void setForgottenPasswordPageText(String forgottenPasswordPageText) {
		this.forgottenPasswordPageText = forgottenPasswordPageText;
	}
	
	public String getLoginFormTitle() {
		return loginFormTitle;
	}
	
	public void setLoginFormTitle(String loginFormTitle) {
		this.loginFormTitle = loginFormTitle;
	}
	
	public String getLoginUrl() {
		return loginUrl;
	}
    
    public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLoginLabel() {
		return loginLabel;
	}

	public void setLoginLabel(String loginLabel) {
		this.loginLabel = loginLabel;
	}

	public String getForgottenPasswordUrl() {
		return forgottenPasswordUrl;
	}

	public void setForgottenPasswordUrl(String forgottenPasswordUrl) {
		this.forgottenPasswordUrl = forgottenPasswordUrl;
	}

	public String getForgottenPasswordLabel() {
		return forgottenPasswordLabel;
	}

	public void setForgottenPasswordLabel(String forgottenPasswordUrl) {
		this.forgottenPasswordLabel = forgottenPasswordUrl;
	}

	public String getEmailOrLoginLabel() {
		return emailOrLoginLabel;
	}
	
	public void setEmailOrLoginLabel(String emailOrLoginLabel) {
		this.emailOrLoginLabel = emailOrLoginLabel;
	}
	
	public String getForgottenPasswordEmailSucces() {
		return forgottenPasswordEmailSucces;
	}
	
	public void setForgottenPasswordEmailSucces(String forgottenPasswordEmailSucces) {
		this.forgottenPasswordEmailSucces = forgottenPasswordEmailSucces;
	}
	
	public String getPasswordLabel() {
		return passwordLabel;
	}

	public void setPasswordLabel(String passwordLabel) {
		this.passwordLabel = passwordLabel;
	}

	public String getRememberLabel() {
		return rememberLabel;
	}

	public void setRememberLabel(String rememberLabel) {
		this.rememberLabel = rememberLabel;
	}

	public String getSubmitLabel() {
		return submitLabel;
	}

	public void setSubmitLabel(String submitLabel) {
		this.submitLabel = submitLabel;
	}
    
}