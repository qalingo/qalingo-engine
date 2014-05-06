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

import java.util.List;

import org.hoteia.qalingo.core.dao.LocalizationDao;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.service.LocalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("localizationService")
@Transactional
public class LocalizationServiceImpl implements LocalizationService {

    @Autowired
    private LocalizationDao localizationDao;

    public Localization getLocalizationById(final Long localizationId, Object... params) {
        return localizationDao.getLocalizationById(localizationId);
    }

    public Localization getLocalizationById(final String rawLocalizationId, Object... params) {
        long localizationId = -1;
        try {
            localizationId = Long.parseLong(rawLocalizationId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getLocalizationById(localizationId, params);
    }

    public Localization getLocalizationByCode(final String code, Object... params) {
        return localizationDao.getLocalizationByCode(code, params);
    }

    public List<Localization> findLocalizations(Object... params) {
        return localizationDao.findLocalizations(params);
    }

    public List<Localization> findLocalizationsByMarketAreaCode(final String marketAreaCode, Object... params) {
        return localizationDao.findLocalizationsByMarketAreaCode(marketAreaCode, params);
    }

    public void saveOrUpdateLocalization(final Localization localization) {
        localizationDao.saveOrUpdateLocalization(localization);
    }

    public void deleteLocalization(final Localization localization) {
        localizationDao.deleteLocalization(localization);
    }

}