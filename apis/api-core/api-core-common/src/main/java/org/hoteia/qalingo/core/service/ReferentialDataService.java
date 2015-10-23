/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.dao.ReferentialDataDao;
import org.hoteia.qalingo.core.domain.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("referentialDataService")
@Transactional
public class ReferentialDataService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    protected ReferentialDataDao referentialDataDao;
    
	Map<Locale, Map<String, String>> titlesByLocale = new HashMap<Locale, Map<String, String>>();

    Map<Locale, Map<String, String>> countriesByLocale = new HashMap<Locale, Map<String, String>>();

    Map<Locale, Map<String, String>> statesByLocale = new HashMap<Locale, Map<String, String>>();

    Map<Locale, Map<String, String>> areasByLocale = new HashMap<Locale, Map<String, String>>();

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

    public String getStateByLocale(final String stateCode, final Locale locale) {
        Map<String, String> statesResourceBundle = getStatesByLocale(locale);
        String state = statesResourceBundle.get(stateCode);
        if(StringUtils.isEmpty(state)){
            state = statesResourceBundle.get(Constants.STATE_MESSAGE_PREFIX + stateCode);
        }
        return state;
    }

    public Map<String, String> getStatesByLocale(final Locale locale) {
        Map<String, String> statesMapByLocale = statesByLocale.get(locale);
        if(statesMapByLocale == null){
            ResourceBundle statesBundleByLocale = getStatesBundleByLocale(locale);
            if(statesBundleByLocale != null){
                statesByLocale.put(locale, buildMap(statesBundleByLocale));
                statesMapByLocale = statesByLocale.get(locale);
            } else {
                statesMapByLocale = getStatesByLocale(Locale.ENGLISH);
            }
        }
        return statesMapByLocale;
    }
    
    public String getAreaByLocale(final String areaCode, final Locale locale) {
        Map<String, String> areasResourceBundle = getAreasByLocale(locale);
        String area = areasResourceBundle.get(areaCode);
        if(StringUtils.isEmpty(area)){
            area = areasResourceBundle.get(Constants.AREA_MESSAGE_PREFIX + areaCode);
        }
        return area;
    }

    public Map<String, String> getAreasByLocale(final Locale locale) {
        Map<String, String> areasMapByLocale = areasByLocale.get(locale);
        if(areasMapByLocale == null){
            ResourceBundle areasBundleByLocale = getAreasBundleByLocale(locale);
            if(areasBundleByLocale != null){
                areasByLocale.put(locale, buildMap(areasBundleByLocale));
                areasMapByLocale = areasByLocale.get(locale);
            } else {
                areasMapByLocale = getAreasByLocale(Locale.ENGLISH);
            }
        }
        return areasMapByLocale;
    }
    
	private ResourceBundle getTitlesBundleByLocale(final Locale locale) {
		return ResourceBundle.getBundle(Constants.TITLES_RESOURCE_BUNDLE, locale);
	}
	
	private ResourceBundle getCountriesBundleByLocale(final Locale locale) {
		return ResourceBundle.getBundle(Constants.COUNTRIES_RESOURCE_BUNDLE, locale);
	}
	
    private ResourceBundle getAreasBundleByLocale(final Locale locale) {
        return ResourceBundle.getBundle(Constants.AREAS_RESOURCE_BUNDLE, locale);
    }
    
    private ResourceBundle getStatesBundleByLocale(final Locale locale) {
        return ResourceBundle.getBundle(Constants.STATES_RESOURCE_BUNDLE, locale);
    }

	private Map<String, String> buildMap(final ResourceBundle bundleByLocale){
		Map<String, String> mapByLocale = new HashMap<String, String>();
		Set<String> keyList = bundleByLocale.keySet();
		for (Iterator<String> iterator = keyList.iterator(); iterator.hasNext();) {
			final String key = (String) iterator.next();
			try {
				mapByLocale.put(key, new String (bundleByLocale.getString(key).getBytes("ISO-8859-1"), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				logger.error("", e);
			}
		}
		return mapByLocale;
	}
	
	// TAG
	
    public Tag getTagById(final Long tagId, Object... params) {
        return referentialDataDao.getTagById(tagId, params);
    }

    public Tag getTagById(final String rawTagId, Object... params) {
        long tagId = -1;
        try {
            tagId = Long.parseLong(rawTagId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getTagById(tagId, params);
    }

    public Tag getTagByCode(final String tagCode, Object... params) {
        return referentialDataDao.getTagByCode(tagCode, params);
    }

    public List<Tag> findAllTags(Object... params) {
        return referentialDataDao.findAllTags(params);
    }

    public Tag saveOrUpdateTag(final Tag tag) {
        return referentialDataDao.saveOrUpdateTag(tag);
    }
    
    public void deleteTag(final Tag tag) {
        referentialDataDao.deleteTag(tag);
    }

}