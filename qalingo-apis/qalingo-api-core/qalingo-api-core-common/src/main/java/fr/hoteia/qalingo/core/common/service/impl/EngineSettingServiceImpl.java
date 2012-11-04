/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
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

import fr.hoteia.qalingo.core.common.dao.EngineSettingDao;
import fr.hoteia.qalingo.core.common.domain.EngineSetting;
import fr.hoteia.qalingo.core.common.domain.EngineSettingValue;
import fr.hoteia.qalingo.core.common.service.EngineSettingService;

@Service("engineSettingService")
@Transactional
public class EngineSettingServiceImpl implements EngineSettingService {

	@Autowired
	private EngineSettingDao engineSettingDao;

	// Engine Setting
	public EngineSetting getEngineSettingById(String id) {
		long engineSettingId = -1;
		try {
			engineSettingId = Long.parseLong(id);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return engineSettingDao.getEngineSettingById(engineSettingId);
	}

	public EngineSetting getEngineSettingByCode(String code) {
		return engineSettingDao.getEngineSettingByCode(code);
	}
	
	public EngineSetting getThemeResourcePrefixPath() {
		return engineSettingDao.getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_CODE_THEME_RESOURCE_PREFIX_PATH);
	}
	
	public EngineSetting getCatalogImageResourcePrefixPath() {
		return engineSettingDao.getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_CODE_CATALOG_RESOURCE_PREFIX_PATH);
	}
	
	public List<EngineSetting> findEngineSettings() {
		return engineSettingDao.findEngineSettings();
	}
	
	public void saveOrUpdateEngineSetting(EngineSetting engineSetting) {
		engineSettingDao.saveEngineSetting(engineSetting);
	}

	public void deleteEngineSetting(EngineSetting engineSetting) {
		engineSettingDao.deleteEngineSetting(engineSetting);
	}
	
	// Engine Setting Value
	public EngineSettingValue getEngineSettingValueById(String id) {
		long engineSettingValueId = -1;
		try {
			engineSettingValueId = Long.parseLong(id);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return engineSettingDao.getEngineSettingValueById(engineSettingValueId);
	}
	
	public void saveOrUpdateEngineSettingValue(EngineSettingValue engineSettingValue) {
		engineSettingDao.saveOrUpdateEngineSettingValue(engineSettingValue);
	}

}
