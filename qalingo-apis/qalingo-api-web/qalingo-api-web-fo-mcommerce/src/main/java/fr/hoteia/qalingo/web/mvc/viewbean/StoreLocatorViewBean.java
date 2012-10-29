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

public class StoreLocatorViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 4454386793514365471L;

	protected String pageTitle;
	protected String textHtml;

	List<StoreViewBean> stores = new ArrayList<StoreViewBean>();
	
	public String getPageTitle() {
		return pageTitle;
	}
	
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
	
	public String getTextHtml() {
		return textHtml;
	}
	
	public void setTextHtml(String textHtml) {
		this.textHtml = textHtml;
	}
	
	public List<StoreViewBean> getStores() {
		return stores;
	}
	
	public void setStores(List<StoreViewBean> stores) {
		this.stores = stores;
	}
}
