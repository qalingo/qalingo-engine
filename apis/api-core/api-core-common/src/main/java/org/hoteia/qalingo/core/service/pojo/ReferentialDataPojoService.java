/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.pojo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.dozer.Mapper;
import org.hoteia.qalingo.core.pojo.ReferentialDataPojo;
import org.hoteia.qalingo.core.service.ReferentialDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("referentialDataPojoService")
@Transactional(readOnly = true)
public class ReferentialDataPojoService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected Mapper dozerBeanMapper;
    
    @Autowired
    protected ReferentialDataService referentialDataService;
    
    public ReferentialDataPojo getTitleByLocale(final String titleCode, final Locale locale) {
        final String value = referentialDataService.getTitleByLocale(titleCode, locale);
        logger.debug("Found {} title value", value);
        return buildReferentialDataPojo(locale.toString(), titleCode, value);
    }
    
    public List<ReferentialDataPojo> getTitlesByLocale(final Locale locale) {
        Map<String, String> values = referentialDataService.getTitlesByLocale(locale);
        logger.debug("Found {} title values", values.size());
        return buildReferentialDataPojos(locale.toString(), values);
    }
    
    public ReferentialDataPojo getCountryByLocale(final String countryCode, final Locale locale) {
        final String value = referentialDataService.getCountryByLocale(countryCode, locale);
        logger.debug("Found {} country value", value);
        return buildReferentialDataPojo(locale.toString(), countryCode, value);
    }
    
    public List<ReferentialDataPojo> getCountriesByLocale(final Locale locale) {
        final Map<String, String> values = referentialDataService.getCountriesByLocale(locale);
        logger.debug("Found {} countries value", values.size());
        return buildReferentialDataPojos(locale.toString(), values);
    }
    
    public ReferentialDataPojo getStateByLocale(final String stateCode, final Locale locale) {
        final String value = referentialDataService.getStateByLocale(stateCode, locale);
        logger.debug("Found {} state value", value);
        return buildReferentialDataPojo(locale.toString(), stateCode, value);
    }
    
    public List<ReferentialDataPojo> getStatesByLocale(final Locale locale) {
        final Map<String, String> values = referentialDataService.getStatesByLocale(locale);
        logger.debug("Found {} state values", values.size());
        return buildReferentialDataPojos(locale.toString(), values);
    }
    
    public ReferentialDataPojo getAreaByLocale(final String areaCode, final Locale locale) {
        final String value = referentialDataService.getAreaByLocale(areaCode, locale);
        logger.debug("Found {} area value ", value);
        return buildReferentialDataPojo(locale.toString(), areaCode, value);
    }
    
    public List<ReferentialDataPojo> getAreasByLocale(final Locale locale) {
        final Map<String, String> values = referentialDataService.getAreasByLocale(locale);
        logger.debug("Found {} area values", values.size());
        return buildReferentialDataPojos(locale.toString(), values);
    }
    
    protected List<ReferentialDataPojo> buildReferentialDataPojos(String locale, Map<String, String> values){
        List<ReferentialDataPojo> referentialDataPojos = new ArrayList<ReferentialDataPojo>();
        for (Iterator<String> iterator = values.keySet().iterator(); iterator.hasNext();) {
            String code = (String) iterator.next();
            String value = (String) values.get(code);
            referentialDataPojos.add(buildReferentialDataPojo(locale, code, value));
        }
        return referentialDataPojos;
    }
    
    protected ReferentialDataPojo buildReferentialDataPojo(String locale, String code, String value){
        ReferentialDataPojo referentialDataPojo = new ReferentialDataPojo(code, locale, value);
        return referentialDataPojo;
    }
    
}