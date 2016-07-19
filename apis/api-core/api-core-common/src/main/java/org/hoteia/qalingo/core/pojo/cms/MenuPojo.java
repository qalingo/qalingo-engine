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

import org.hoteia.qalingo.core.pojo.LocalizationPojo;
import org.hoteia.qalingo.core.pojo.market.MarketAreaPojo;

public class MenuPojo {

    protected Long id;
    protected int version;
    protected String code;
    protected String name;
    protected MarketAreaPojo marketArea;
    protected LocalizationPojo localization;
    protected boolean external;
    protected String fullUrlPath;
    protected String position;
    protected String type;
    protected String params;
	protected int ordering;
	
    protected MenuPojo parentMenu;
    protected Set<MenuPojo> subMenus = new HashSet<MenuPojo>();
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public String getFullUrlPath() {
		return fullUrlPath;
	}
	
	public void setFullUrlPath(String fullUrlPath) {
		this.fullUrlPath = fullUrlPath;
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

	public MenuPojo getParentMenu() {
        return parentMenu;
    }
	
	public void setParentMenu(MenuPojo parentMenu) {
        this.parentMenu = parentMenu;
    }
	
    public String getParentMenuName() {
        if (parentMenu != null) {
            return parentMenu.getName();
        }
        return "";
    }
    
	public Set<MenuPojo> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(Set<MenuPojo> subMenus) {
		this.subMenus = subMenus;
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