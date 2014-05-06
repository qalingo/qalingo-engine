/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.service.ReferentialDataService;

@Service("referentialDataService")
@Transactional
public class ReferentialDataServiceImpl implements ReferentialDataService {

	Map<Locale, Map<String, String>> titlesByLocale = new HashMap<Locale, Map<String, String>>();

	Map<Locale, Map<String, String>> countriesByLocale = new HashMap<Locale, Map<String, String>>();

	public String getTitleByLocale(final String titleCode, final Locale locale) {
		Map<String, String> titlesResourceByLocale = getTitlesByLocale(locale);
		String title = titlesResourceByLocale.get(titleCode);
		if(StringUtils.isEmpty(title)){
			title = titlesResourceByLocale.get(Constants.TITLE_MESSAGE_PREFIX + titleCode);
		}
		return title;
	}

	public Map<String, String> getTitlesByLocale(final Locale locale) {
		Map<String, String> titlesMapByLocale = titlesByLocale.get(locale);
		if(titlesMapByLocale == null){
			ResourceBundle titlesBundleByLocale = getTitlesBundleByLocale(locale);
			if(titlesBundleByLocale != null){
				titlesByLocale.put(locale, buildMap(titlesBundleByLocale));
				titlesMapByLocale = titlesByLocale.get(locale);
			} else {
				titlesMapByLocale = getTitlesByLocale(Locale.ENGLISH);
			}
		}
		return titlesMapByLocale;
	}
	
	public String getCountryByLocale(final String countryCode, final Locale locale) {
		Map<String, String> countriesResourceBundle = getCountriesByLocale(locale);
		String country = countriesResourceBundle.get(countryCode);
		if(StringUtils.isEmpty(country)){
			country = countriesResourceBundle.get(Constants.COUNTRY_MESSAGE_PREFIX + countryCode);
		}
		return country;
	}

	public Map<String, String> getCountriesByLocale(final Locale locale) {
		Map<String, String> countriesMapByLocale = countriesByLocale.get(locale);
		if(countriesMapByLocale == null){
			ResourceBundle countriesBundleByLocale = getCountriesBundleByLocale(locale);
			if(countriesBundleByLocale != null){
				countriesByLocale.put(locale, buildMap(countriesBundleByLocale));
				countriesMapByLocale = countriesByLocale.get(locale);
			} else {
				countriesMapByLocale = getCountriesByLocale(Locale.ENGLISH);
			}
		}
		return countriesMapByLocale;
	}

	private ResourceBundle getTitlesBundleByLocale(final Locale locale) {
		return ResourceBundle.getBundle(Constants.TITLES_RESOURCE_BUNDLE, locale);
	}
	
	private ResourceBundle getCountriesBundleByLocale(final Locale locale) {
		return ResourceBundle.getBundle(Constants.COUNTRIES_RESOURCE_BUNDLE, locale);
	}

	private Map<String, String> buildMap(final ResourceBundle bundleByLocale){
		Map<String, String> mapByLocale = new HashMap<String, String>();
		Set<String> keyList = bundleByLocale.keySet();
		for (Iterator<String> iterator = keyList.iterator(); iterator.hasNext();) {
			final String key = (String) iterator.next();
			mapByLocale.put(key, (String)bundleByLocale.getObject(key));
		}
		return mapByLocale;
	}

}