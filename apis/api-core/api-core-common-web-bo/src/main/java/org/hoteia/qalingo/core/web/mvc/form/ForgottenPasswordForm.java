/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * 
 */
public class ForgottenPasswordForm {
	
	@NotEmpty(message = "error.form.forgotten.password.email.empty")
	@Email(message = "error.form.forgotten.password.email.is.not.valid")
    private String emailOrLogin;
    
	public String getEmailOrLogin() {
		return emailOrLogin;
	}
	
	public void setEmailOrLogin(String emailOrLogin) {
		this.emailOrLogin = emailOrLogin;
	}

}