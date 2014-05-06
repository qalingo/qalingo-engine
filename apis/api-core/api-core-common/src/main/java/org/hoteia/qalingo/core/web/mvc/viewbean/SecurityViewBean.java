/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SecurityViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -6903293893445728139L;
	
	private String loginUrl;
	private String submitLoginUrl;
    private String forgottenPasswordUrl;
    private String resetPasswordUrl;

    // OAUTH / OPENID URLS
    private Map<String, String> urls = new HashMap<String, String>();
    
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
	
	public String getResetPasswordUrl() {
	    return resetPasswordUrl;
    }
	
	public void setResetPasswordUrl(String resetPasswordUrl) {
	    this.resetPasswordUrl = resetPasswordUrl;
    }
	
	public Map<String, String> getUrls() {
	    return urls;
    }
	
	public void setUrls(Map<String, String> urls) {
	    this.urls = urls;
    }
    
}