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

public class ShareOptionViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -5387532593045575454L;
	
	protected String code;
	protected String name;
	protected String linkColor;
	protected String linkLabel;
	protected String url;

	public String getCode() {
    	return code;
    }

	public void setCode(String code) {
    	this.code = code;
    }

	public String getName() {
    	return name;
    }

	public void setName(String name) {
    	this.name = name;
    }

	public String getLinkColor() {
	    return linkColor;
    }
	
	public void setLinkColor(String linkColor) {
	    this.linkColor = linkColor;
    }
	
	public String getLinkLabel() {
	    return linkLabel;
    }
	
	public void setLinkLabel(String linkLabel) {
	    this.linkLabel = linkLabel;
    }
	
	public String getUrl() {
	    return url;
    }
	
	public void setUrl(String url) {
	    this.url = url;
    }
	
}