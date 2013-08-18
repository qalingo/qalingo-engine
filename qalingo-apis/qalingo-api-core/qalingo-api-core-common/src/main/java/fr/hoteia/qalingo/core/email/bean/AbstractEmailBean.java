/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.email.bean;

import java.io.Serializable;

public abstract class AbstractEmailBean implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 5417215634608783739L;
    
	private String fromEmail;
	private String replyToEmail;
	private String toEmail;
	
	public String getFromEmail() {
	    return fromEmail;
    }
	
	public void setFromEmail(String fromEmail) {
	    this.fromEmail = fromEmail;
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