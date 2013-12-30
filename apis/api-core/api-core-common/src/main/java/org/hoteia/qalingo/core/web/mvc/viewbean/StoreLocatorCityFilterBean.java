package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StoreLocatorCityFilterBean extends AbstractViewBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8286353003242432122L;
	
	private String name;
	private String code;
	private List<StoreViewBean> stores;

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

	public List<StoreViewBean> getStores() {
		return stores;
	}

	public void addStore(StoreViewBean store) {
		if(this.stores == null){
			this.stores = new ArrayList<StoreViewBean>();
		}
		this.stores.add(store);
	}

}
