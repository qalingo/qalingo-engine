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

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * 
 */
public class ForgottenPasswordForm {

	@NotEmpty(message = "fo.auth.error_form_forgotten_password_email_empty")
	@Email(message = "fo.auth.error_form_forgotten_password_email_is_not_valid")
	private String emailOrLogin;

	public String getEmailOrLogin() {
		return emailOrLogin;
	}

	public void setEmailOrLogin(String emailOrLogin) {
		this.emailOrLogin = emailOrLogin;
	}

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((emailOrLogin == null) ? 0 : emailOrLogin.hashCode());
	    return result;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null)
		    return false;
	    if (getClass() != obj.getClass())
		    return false;
	    ForgottenPasswordForm other = (ForgottenPasswordForm) obj;
	    if (emailOrLogin == null) {
		    if (other.emailOrLogin != null)
			    return false;
	    } else if (!emailOrLogin.equals(other.emailOrLogin))
		    return false;
	    return true;
    }

	@Override
    public String toString() {
	    return "ForgottenPasswordForm [emailOrLogin=" + emailOrLogin + "]";
    }

}