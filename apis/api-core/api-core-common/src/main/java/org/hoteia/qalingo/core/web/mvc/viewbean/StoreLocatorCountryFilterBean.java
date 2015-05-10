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
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class StoreLocatorCountryFilterBean extends AbstractViewBean {
    
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
	
    public void sortCities() {
        if(cities != null){
            List<StoreLocatorCityFilterBean> sortedObjects = new LinkedList<StoreLocatorCityFilterBean>(cities);
            Collections.sort(sortedObjects, new Comparator<StoreLocatorCityFilterBean>() {
                @Override
                public int compare(StoreLocatorCityFilterBean o1, StoreLocatorCityFilterBean o2) {
                    if (o1 != null && o2 != null) {
                        int compare = o1.getName().compareTo(o2.getName());
                        if (compare != 0) {
                            return compare;
                        }
                        return o1.getName().compareTo(o2.getName());
                    }
                    return 0;
                }
            });
            for (Iterator<StoreLocatorCityFilterBean> iterator = sortedObjects.iterator(); iterator.hasNext();) {
                StoreLocatorCityFilterBean bean = (StoreLocatorCityFilterBean) iterator.next();
                bean.sortStores();
            }
            this.cities = sortedObjects;
        }
    }
    
}