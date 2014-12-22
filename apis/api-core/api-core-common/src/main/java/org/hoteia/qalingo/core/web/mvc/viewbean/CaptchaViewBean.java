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

public class CaptchaViewBean implements Serializable {

	/**
	 * Generated UID
	 */
    private static final long serialVersionUID = -8451145951601852753L;
	
    protected String siteKey;
	protected String secretKey;
	
	public String getSiteKey() {
        return siteKey;
    }
	
	public void setSiteKey(String siteKey) {
        this.siteKey = siteKey;
    }

	public String getSecretKey() {
        return secretKey;
    }
	
	public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public String toString() {
        return "CaptchaViewBean [siteKey=" + siteKey + ", secretKey=" + secretKey + "]";
    }
	
}