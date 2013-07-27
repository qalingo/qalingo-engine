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
import java.util.ArrayList;
import java.util.List;

public class MarketAreaViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -8350224752431629863L;
	
	protected String name;
	protected String description;
	protected String code;
	protected String url;
	
	private boolean isDefault;
	private boolean isEcommerce;
	private String theme;
	private String domainName;
	
	private String longitude;
	private String latitude;
	
	protected List<LocalizationViewBean> languages = new ArrayList<LocalizationViewBean>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
    	return description;
    }

	public void setDescription(String description) {
    	this.description = description;
    }

	public String getCode() {
    	return code;
    }

	public void setCode(String code) {
    	this.code = code;
    }

	public String getUrl() {
    	return url;
    }

	public void setUrl(String url) {
    	this.url = url;
    }

	public boolean isDefault() {
    	return isDefault;
    }

	public void setDefault(boolean isDefault) {
    	this.isDefault = isDefault;
    }

	public boolean isEcommerce() {
    	return isEcommerce;
    }

	public void setEcommerce(boolean isEcommerce) {
    	this.isEcommerce = isEcommerce;
    }

	public String getTheme() {
    	return theme;
    }

	public void setTheme(String theme) {
    	this.theme = theme;
    }

	public String getDomainName() {
    	return domainName;
    }

	public void setDomainName(String domainName) {
    	this.domainName = domainName;
    }

	public String getLongitude() {
    	return longitude;
    }

	public void setLongitude(String longitude) {
    	this.longitude = longitude;
    }

	public String getLatitude() {
    	return latitude;
    }

	public void setLatitude(String latitude) {
    	this.latitude = latitude;
    }

	public List<LocalizationViewBean> getLanguages() {
		return languages;
	}
	
	public void setLanguages(List<LocalizationViewBean> languages) {
		this.languages = languages;
	}
	
}