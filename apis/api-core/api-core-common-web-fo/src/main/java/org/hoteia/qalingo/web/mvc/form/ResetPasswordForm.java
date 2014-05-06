/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.form;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import org.hoteia.qalingo.core.web.validation.annotation.TwoFieldsEquals;

/**
 * 
 * 
 */
public class ResetPasswordForm implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = -4398822690434901496L;

	@NotEmpty(message = "fo.auth.error_form_reset_password_email_empty")
	private String email;
	
	@NotEmpty(message = "fo.auth.error_form_reset_password_token_empty")
	private String token;
	
	@NotEmpty(message = "fo.auth.error_form_reset_password_new_password_empty")
	private String newPassword;
	
	@NotEmpty(message = "fo.auth.error_form_reset_password_confirm_password_empty")
    @TwoFieldsEquals(highlightFieldNames={"newPassword"}, message="fo.auth.error_form_reset_password_confirm_password_is_wrong")
	private String confirmNewPassword;

	public String getEmail() {
	    return email;
    }
	
	public void setEmail(String email) {
	    this.email = email;
    }
	
	public String getToken() {
	    return token;
    }
	
	public void setToken(String token) {
	    this.token = token;
    }

	public String getNewPassword() {
    	return newPassword;
    }

	public void setNewPassword(String newPassord) {
    	this.newPassword = newPassord;
    }

	public String getConfirmNewPassword() {
    	return confirmNewPassword;
    }

	public void setConfirmNewPassword(String confirmNewPassword) {
    	this.confirmNewPassword = confirmNewPassword;
    }
	
	
}