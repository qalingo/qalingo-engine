/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.common.dao.LocalizationDao;
import fr.hoteia.qalingo.core.common.domain.Localization;
import fr.hoteia.qalingo.core.common.service.LocalizationService;

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
	
	public Localization getLocalizationByLocaleCode(final String localeCode) {
		return localizationDao.getLocalizationByLocaleCode(localeCode);
	}

	public List<Localization> findLocalization(Localization criteria) {
		return localizationDao.findByExample(criteria);
	}

	public void saveOrUpdateLocalization(Localization localization) {
		localizationDao.saveOrUpdateLocalization(localization);
	}

	public void deleteLocalization(Localization localization) {
		localizationDao.deleteLocalization(localization);
	}

}
