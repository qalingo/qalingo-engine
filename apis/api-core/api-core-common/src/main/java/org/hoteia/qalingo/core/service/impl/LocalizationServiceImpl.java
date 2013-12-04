/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
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

	public Localization getLocalizationById(String rawLocalizationId) {
		long LocalizationId = -1;
		try {
			LocalizationId = Long.parseLong(rawLocalizationId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return localizationDao.getLocalizationById(LocalizationId);
	}
	
	public Localization getLocalizationByCode(final String code) {
		return localizationDao.getLocalizationByCode(code);
	}

	public List<Localization> findLocalizations() {
		return localizationDao.findLocalizations();
	}
	
    public List<Localization> findLocalizationsByMarketAreaCode(final String marketAreaCode) {
        return localizationDao.findLocalizationsByMarketAreaCode(marketAreaCode);
    }

	public void saveOrUpdateLocalization(Localization localization) {
		localizationDao.saveOrUpdateLocalization(localization);
	}

	public void deleteLocalization(Localization localization) {
		localizationDao.deleteLocalization(localization);
	}

}