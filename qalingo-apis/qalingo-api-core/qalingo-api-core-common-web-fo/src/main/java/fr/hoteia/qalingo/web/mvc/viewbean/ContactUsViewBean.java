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

public class ContactUsViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 3367453489367832074L;
	
    private String backUrl;
    
    private String successMessage;
    private String failMessage;
    
	public String getBackUrl() {
		return backUrl;
	}
	
	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}
	
	public String getSuccessMessage() {
		return successMessage;
	}
	
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}
	
	public String getFailMessage() {
		return failMessage;
	}
	
	public void setFailMessage(String failMessage) {
		this.failMessage = failMessage;
	}
	
}