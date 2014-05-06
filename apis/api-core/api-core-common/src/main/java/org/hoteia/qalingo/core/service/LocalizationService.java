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

import java.util.List;

import org.hoteia.qalingo.core.domain.Localization;

public interface LocalizationService {

    Localization getLocalizationById(Long localizationId, Object... params);

    Localization getLocalizationById(String localizationId, Object... params);
	
	Localization getLocalizationByCode(String localeCode, Object... params);
	
	List<Localization> findLocalizations(Object... params);
	
	List<Localization> findLocalizationsByMarketAreaCode(String marketAreaCode, Object... params);
	
	void saveOrUpdateLocalization(Localization localization);
	
	void deleteLocalization(Localization localization);

}