/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.form;

/**
 * 
 * 
 */
public class ForgottenPasswordForm {
	
    private String emailOrLogin;
    
	public String getEmailOrLogin() {
		return emailOrLogin;
	}
	
	public void setEmailOrLogin(String emailOrLogin) {
		this.emailOrLogin = emailOrLogin;
	}

}