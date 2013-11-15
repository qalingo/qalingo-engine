/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.io.Serializable;

public class SecurityViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -6903293893445728139L;
	
	private String loginUrl;
	private String submitLoginUrl;
    private String forgottenPasswordUrl;
    
	public String getLoginUrl() {
		return loginUrl;
	}
    
    public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
    
    public String getSubmitLoginUrl() {
	    return submitLoginUrl;
    }
    
    public void setSubmitLoginUrl(String submitLoginUrl) {
	    this.submitLoginUrl = submitLoginUrl;
    }

	public String getForgottenPasswordUrl() {
		return forgottenPasswordUrl;
	}

	public void setForgottenPasswordUrl(String forgottenPasswordUrl) {
		this.forgottenPasswordUrl = forgottenPasswordUrl;
	}

}