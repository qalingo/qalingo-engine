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

import org.apache.commons.lang.StringUtils;

public class MenuViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 4594828019275682815L;
	
	protected String name;
	protected String alt;
	protected String cssClass;
	protected String cssIcon;
	protected String url;
	
	protected List<MenuViewBean> subMenus = new ArrayList<MenuViewBean>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlt() {
		if(StringUtils.isEmpty(alt)){
			return getName();
		}
		return alt;
	}
	
	public void setAlt(String alt) {
		this.alt = alt;
	}
	
	public String getCssClass() {
		if(cssClass == null){
			return "";
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
	
	public String getUrl() {
		if(url == null){
			return "#";
		}
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public List<MenuViewBean> getSubMenus() {
		return subMenus;
	}
	
	public boolean hasSubMenu() {
		if(subMenus != null
				&& subMenus.size() > 0){
			return true;
		} else {
			return false;
		}
	}
	
	public void setSubMenus(List<MenuViewBean> subMenus) {
		this.subMenus = subMenus;
	}
	
	public boolean getHasSubMenu(){
		if(subMenus != null
				&& subMenus.size() > 0){
			return true;
		}
		return false;
	}
	
}
