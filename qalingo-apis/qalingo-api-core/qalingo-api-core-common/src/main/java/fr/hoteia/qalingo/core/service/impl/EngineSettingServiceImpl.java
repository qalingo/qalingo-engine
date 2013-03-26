/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.EngineSettingDao;
import fr.hoteia.qalingo.core.domain.EngineSetting;
import fr.hoteia.qalingo.core.domain.EngineSettingValue;
import fr.hoteia.qalingo.core.service.EngineSettingService;

@Service("engineSettingService")
@Transactional
public class EngineSettingServiceImpl implements EngineSettingService {

	@Autowired
	private EngineSettingDao engineSettingDao;

	// Engine Setting
	public EngineSetting getEngineSettingById(final String id) {
		long engineSettingId = -1;
		try {
			engineSettingId = Long.parseLong(id);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return engineSettingDao.getEngineSettingById(engineSettingId);
	}

	public EngineSetting getEngineSettingByCode(final String code) {
		return engineSettingDao.getEngineSettingByCode(code);
	}
	
	public List<EngineSetting> findEngineSettings() {
		return engineSettingDao.findEngineSettings();
	}
	
	public void saveOrUpdateEngineSetting(final EngineSetting engineSetting) {
		engineSettingDao.saveEngineSetting(engineSetting);
	}

	public void deleteEngineSetting(final EngineSetting engineSetting) {
		engineSettingDao.deleteEngineSetting(engineSetting);
	}
	
	// Engine Setting Value
	public EngineSettingValue getEngineSettingValueById(final String id) {
		long engineSettingValueId = -1;
		try {
			engineSettingValueId = Long.parseLong(id);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return engineSettingDao.getEngineSettingValueById(engineSettingValueId);
	}
	
	public void saveOrUpdateEngineSettingValue(final EngineSettingValue engineSettingValue) {
		engineSettingDao.saveOrUpdateEngineSettingValue(engineSettingValue);
	}
	
	public String getEngineSettingValueByCode(final String engineSettingCode, final String engineSettingValueCode) {
		EngineSetting engineSetting = getEngineSettingByCode(engineSettingCode);
		if(engineSetting != null){
			EngineSettingValue engineSettingValue  = engineSetting.getEngineSettingValue(engineSettingValueCode);
			if(engineSettingValue != null){
				return engineSettingValue.getValue();
			} else {
				return engineSetting.getDefaultValue();
			}
		}
		return null;
	}
	
	public String getEngineSettingDefaultValueByCode(final String engineSettingCode) {
		EngineSetting engineSetting = getEngineSettingByCode(engineSettingCode);
		if(engineSetting != null){
			return engineSetting.getDefaultValue();
		}
		return null;
	}
	
	// Common Engine Setting Value
	
	public EngineSetting getThemeResourcePrefixPath() {
		return engineSettingDao.getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_CODE_THEME_RESOURCE_PREFIX_PATH);
	}
	
	public EngineSetting getCatalogImageResourcePrefixPath() {
		return engineSettingDao.getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_CODE_CATALOG_RESOURCE_PREFIX_PATH);
	}
	

}
