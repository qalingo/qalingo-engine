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

import static org.hoteia.qalingo.core.pojo.util.mapper.PojoUtil.mapAll;

import java.util.List;

import org.dozer.Mapper;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.pojo.LocalizationPojo;
import org.hoteia.qalingo.core.service.LocalizationService;
import org.hoteia.qalingo.core.service.pojo.LocalizationPojoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("localizationPojoService")
@Transactional(readOnly = true)
public class LocalizationPojoFactory {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired 
    private Mapper dozerBeanMapper;
    
    @Autowired 
    private LocalizationService localizationService;

    public LocalizationPojo getLocalizationById(String localizationId) {
        final Localization localization = localizationService.getLocalizationById(localizationId);
        logger.debug("Found {} localization for id {}", localization, localizationId);
        return localization == null ? null : dozerBeanMapper.map(localization, LocalizationPojo.class);
    }
    
    public LocalizationPojo getLocalizationByCode(String localizationCode) {
        final Localization localization = localizationService.getLocalizationByCode(localizationCode);
        logger.debug("Found {} localization for code {}", localization, localizationCode);
        return localization == null ? null : dozerBeanMapper.map(localization, LocalizationPojo.class);
    }
    
    public List<LocalizationPojo> findAllLocalizations() {
        List<Localization> allLocalizations = localizationService.findLocalizations();
        logger.debug("Found {} localizations", allLocalizations.size());
        return mapAll(dozerBeanMapper, allLocalizations, LocalizationPojo.class);
    }
    
    public List<LocalizationPojo> findLocalizationsByMarketAreaCode(final String marketAreaCode) {
        List<Localization> localizationsByMarketAreaCode = localizationService.findLocalizationsByMarketAreaCode(marketAreaCode);
        logger.debug("Found {} localizations", localizationsByMarketAreaCode.size());
        return mapAll(dozerBeanMapper, localizationsByMarketAreaCode, LocalizationPojo.class);
    }

}