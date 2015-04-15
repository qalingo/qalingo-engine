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

import java.util.ArrayList;
import java.util.List;

public class MarketAreaViewBean extends AbstractViewBean {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -8350224752431629863L;

	// ENTITY
    private String code;
	private String name;
	private String description;
    
    private boolean opened;
	private boolean isDefault;
	private boolean isEcommerce;
	private String theme;
	private String domainName;
	
	private String longitude;
	private String latitude;
	
	private List<LocalizationViewBean> localizations = new ArrayList<LocalizationViewBean>();

    // MENU
	private String changeContextUrl;
	private String homeUrl;
	private boolean active;

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

	public String getDescription() {
    	return description;
    }

	public void setDescription(String description) {
    	this.description = description;
    }

	public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
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

	public List<LocalizationViewBean> getLocalizations() {
		return localizations;
	}
	
	public void setLocalizations(List<LocalizationViewBean> localizations) {
		this.localizations = localizations;
	}
	
    public String getChangeContextUrl() {
        return changeContextUrl;
    }

    public void setChangeContextUrl(String changeContextUrl) {
        this.changeContextUrl = changeContextUrl;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}