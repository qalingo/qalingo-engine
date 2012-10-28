/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
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

public class MenuViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 4594828019275682815L;
	
	protected String name;
	protected String alt;
	protected String img;
	protected String url;
	
	List<MenuViewBean> subMenus = new ArrayList<MenuViewBean>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlt() {
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
	
	public List<MenuViewBean> getSubMenus() {
		return subMenus;
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
