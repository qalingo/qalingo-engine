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

public class FooterMenuViewBean extends AbstractViewBean implements Serializable {
	
	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -1579441776291839252L;
	
	protected String name;
	protected String img;
	protected String url;
	protected boolean isExternal = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isExternal() {
    	return isExternal;
    }

	public void setExternal(boolean isExternal) {
    	this.isExternal = isExternal;
    }
	
}
