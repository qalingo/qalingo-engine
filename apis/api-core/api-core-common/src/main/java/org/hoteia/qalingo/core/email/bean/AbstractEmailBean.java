/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.email.bean;

import java.io.Serializable;

public abstract class AbstractEmailBean implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 5417215634608783739L;
    
	private String fromAddress;
    private String fromName;
	private String replyToEmail;
	private String toEmail;
	
	public String getFromAddress() {
	    return fromAddress;
    }
	
	public void setFromAddress(String fromAddress) {
	    this.fromAddress = fromAddress;
    }
	
	public String getFromName() {
        return fromName;
    }
	
	public void setFromName(String fromName) {
        this.fromName = fromName;
    }
	
	public String getReplyToEmail() {
	    return replyToEmail;
    }
	
	public void setReplyToEmail(String replyToEmail) {
	    this.replyToEmail = replyToEmail;
    }
	
	public String getToEmail() {
	    return toEmail;
    }
	
	public void setToEmail(String toEmail) {
	    this.toEmail = toEmail;
    }
	
}