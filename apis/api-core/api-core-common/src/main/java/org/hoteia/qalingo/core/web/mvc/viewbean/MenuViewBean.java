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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class MenuViewBean extends AbstractViewBean implements Serializable {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 4594828019275682815L;

    protected String name;
    protected String alt;
    protected String img;
    protected String url;
    protected boolean active;

    protected String cssClass;
    protected String cssIcon;

    List<MenuViewBean> subMenus = new ArrayList<MenuViewBean>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

}
