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

public class SocialNetworkFeedItemViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
    private static final long serialVersionUID = -884183184329106541L;
    
	protected String type;
	protected String text;
	protected String url;
	protected String date;
	
	public String getType() {
    	return type;
    }
	
	public void setType(String type) {
    	this.type = type;
    }
	
	public String getText() {
    	return text;
    }
	
	public void setText(String text) {
    	this.text = text;
    }
	
	public String getUrl() {
    	return url;
    }
	
	public void setUrl(String url) {
    	this.url = url;
    }
	
	public String getDate() {
    	return date;
    }
	
	public void setDate(String date) {
    	this.date = date;
    }

}