/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.cms;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hoteia.qalingo.core.domain.CmsMenu;
import org.hoteia.qalingo.core.pojo.LocalizationPojo;
import org.hoteia.qalingo.core.pojo.market.MarketAreaPojo;

public class CmsContentPojo {

    protected Long id;
    protected int version;
    protected String code;
    protected String app;
    protected String title;
    protected MarketAreaPojo marketArea;
    protected LocalizationPojo localization;
    protected boolean external;
    protected String fullURlPath;
    protected String position;
    protected String type;
    protected String params;
	protected int ordering;
    protected boolean active;
	
	protected CmsMenu menu;
    protected Set<CmsContentPojo> crossCmsContents = new HashSet<CmsContentPojo>();

	protected String editUrl;
	protected String detailsUrl;

    protected Date datePublish;
    protected Date dateCreate;
	protected Date dateUpdate;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getApp() {
		return app;
	}
	
	public void setApp(String app) {
		this.app = app;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public MarketAreaPojo getMarketArea() {
		return marketArea;
	}
	
	public void setMarketArea(MarketAreaPojo marketArea) {
		this.marketArea = marketArea;
	}
	
	public String getMarketAreaName() {
		if(marketArea != null){
			return marketArea.getName();
		}
		return "";
	}
	
	public LocalizationPojo getLocalization() {
        return localization;
    }
	
	public void setLocalization(LocalizationPojo localization) {
        this.localization = localization;
    }

    public String getLocalizationName() {
        if (localization != null) {
            return localization.getName();
        }
        return "";
    }
	   
	public boolean isExternal() {
		return external;
	}

	public void setExternal(boolean external) {
		this.external = external;
	}
	
	public String getFullURlPath() {
		return fullURlPath;
	}
	
	public void setFullURlPath(String fullURlPath) {
		this.fullURlPath = fullURlPath;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public int getOrdering() {
		return ordering;
	}

	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public CmsMenu getMenu() {
		return menu;
	}

	public void setMenu(CmsMenu menu) {
		this.menu = menu;
	}
    
    public Set<CmsContentPojo> getCrossCmsContents() {
        return crossCmsContents;
    }
    
    public void setCrossCmsContents(Set<CmsContentPojo> crossCmsContents) {
        this.crossCmsContents = crossCmsContents;
    }

    public String getEditUrl() {
		return editUrl;
	}
    
    public void setEditUrl(String editUrl) {
		this.editUrl = editUrl;
	}
	
	public String getDetailsUrl() {
		return detailsUrl;
	}
	
	public void setDetailsUrl(String detailsUrl) {
		this.detailsUrl = detailsUrl;
	}
	
	public Date getDatePublish() {
        return datePublish;
    }
	
	public void setDatePublish(Date datePublish) {
        this.datePublish = datePublish;
    }
	
	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

}