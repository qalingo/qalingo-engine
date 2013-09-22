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

public class MarketViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 5215345824277047277L;
	
	protected String name;
	protected String img;
	protected String changeContextUrl;

	protected List<MarketAreaViewBean> marketAreas = new ArrayList<MarketAreaViewBean>();

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

	public String getChangeContextUrl() {
		return changeContextUrl;
	}

	public void setChangeContextUrl(String changeContextUrl) {
		this.changeContextUrl = changeContextUrl;
	}
	
	public List<MarketAreaViewBean> getMarketAreas() {
		return marketAreas;
	}
	
	public void setMarketAreas(List<MarketAreaViewBean> marketAreas) {
		this.marketAreas = marketAreas;
	}
	
}
