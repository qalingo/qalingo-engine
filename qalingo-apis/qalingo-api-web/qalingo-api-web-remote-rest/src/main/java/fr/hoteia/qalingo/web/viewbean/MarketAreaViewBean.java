/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.viewbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MarketAreaViewBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4812482325324665757L;
	
	protected String name;
	protected String img;
	protected String url;

	protected List<LocalizationViewBean> languages = new ArrayList<LocalizationViewBean>();

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
	
	public List<LocalizationViewBean> getLanguages() {
		return languages;
	}
	
	public void setLanguages(List<LocalizationViewBean> languages) {
		this.languages = languages;
	}
	
}
