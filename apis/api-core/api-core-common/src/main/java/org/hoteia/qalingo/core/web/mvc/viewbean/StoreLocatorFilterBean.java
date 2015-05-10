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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StoreLocatorFilterBean extends AbstractViewBean {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 5515180433042585447L;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private List<StoreLocatorCountryFilterBean> countries;

	public List<StoreLocatorCountryFilterBean> getCountries() {
		return countries;
	}

	public void addCountry(StoreLocatorCountryFilterBean country) {
		if(this.countries == null){
			this.countries = new ArrayList<StoreLocatorCountryFilterBean>();
		}
		this.countries.add(country);
	}	

    public void sortCountries() {
        if(countries != null){
            List<StoreLocatorCountryFilterBean> sortedObjects = new LinkedList<StoreLocatorCountryFilterBean>(countries);
            Collections.sort(sortedObjects, new Comparator<StoreLocatorCountryFilterBean>() {
                @Override
                public int compare(StoreLocatorCountryFilterBean o1, StoreLocatorCountryFilterBean o2) {
                    if (o1 != null && o1.getName() != null
                            && o2 != null) {
                        int compare = o1.getName().compareTo(o2.getName());
                        return compare;
                    }
                    return 0;
                }
            });
            for (Iterator<StoreLocatorCountryFilterBean> iterator = sortedObjects.iterator(); iterator.hasNext();) {
                StoreLocatorCountryFilterBean bean = (StoreLocatorCountryFilterBean) iterator.next();
                bean.sortCities();
            }            
            this.countries = sortedObjects;
        }
    }
    
}