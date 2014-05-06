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

public class StoreLocatorCountryFilterBean extends AbstractViewBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5109758686236128069L;
	private String name;
	private String code;
	private List<StoreLocatorCityFilterBean> cities;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<StoreLocatorCityFilterBean> getCities() {
		return cities;
	}

	public void addCity(StoreLocatorCityFilterBean city) {
		if(this.cities == null){
			this.cities = new ArrayList<StoreLocatorCityFilterBean>();
		}
		this.cities.add(city);
	}
}
