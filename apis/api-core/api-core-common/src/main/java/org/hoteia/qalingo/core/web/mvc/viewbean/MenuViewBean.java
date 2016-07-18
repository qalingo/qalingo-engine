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

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;

public class MenuViewBean extends AbstractViewBean {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 4594828019275682815L;

    protected String key;
    protected String name;
    protected String description;
    
    protected String i18nName;

    protected String alt;
    protected String img;
    protected String url;
    protected String type;
    
    protected boolean external;
    protected boolean active;
    protected boolean catalog = false;

    protected int ordering;
    
    protected String cssClass;
    protected String cssIcon;

    protected List<MenuViewBean> subMenus = new ArrayList<MenuViewBean>();

    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
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

    public String getI18nName() {
        return i18nName;
    }
    
    public void setI18nName(String i18nName) {
        this.i18nName = i18nName;
    }
    
    public String getAlt() {
        if (StringUtils.isEmpty(alt)) {
            return getName();
        }
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
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

    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public boolean isHome() {
        if(key != null
                && FoUrls.HOME.getKey().equalsIgnoreCase(key)){
            return true;
        }
        return false;
    }
    
    public boolean isExternal() {
        return external;
    }

    public void setExternal(boolean external) {
        this.external = external;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isCatalog() {
        return catalog;
    }

    public void setCatalog(boolean catalog) {
        this.catalog = catalog;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

    public String getCssClass() {
       if(isActive()){
           return cssClass + " active";
       }
       return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }
    
    public String getCssIcon() {
        return cssIcon;
    }

    public void setCssIcon(String cssIcon) {
        this.cssIcon = cssIcon;
    }

    public List<MenuViewBean> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<MenuViewBean> subMenus) {
        this.subMenus = subMenus;
    }

    public boolean getHasSubMenu() {
        if (subMenus != null && subMenus.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "MenuViewBean [key=" + key + ", name=" + name + ", description=" + description + ", alt=" + alt + ", img=" + img + ", url=" + url + ", type=" + type + ", external=" + external
                + ", active=" + active + ", catalog=" + catalog + ", ordering=" + ordering + ", cssClass=" + cssClass + ", cssIcon=" + cssIcon + ", subMenus=" + subMenus + "]";
    }

}