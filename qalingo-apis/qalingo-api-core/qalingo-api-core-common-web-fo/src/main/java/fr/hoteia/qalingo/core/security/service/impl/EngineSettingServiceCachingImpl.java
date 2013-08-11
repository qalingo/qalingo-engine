/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.security.service.impl;

import java.util.List;

import javax.annotation.Resource;

import fr.hoteia.qalingo.core.domain.EngineSetting;
import fr.hoteia.qalingo.core.domain.EngineSettingValue;
import fr.hoteia.qalingo.core.service.impl.EngineSettingServiceImpl;
import fr.hoteia.qalingo.core.web.cache.util.WebCacheHelper;
import fr.hoteia.qalingo.core.web.cache.util.WebElementType;

public class EngineSettingServiceCachingImpl extends EngineSettingServiceImpl {

	@Resource(name = "engineSettingCacheHelper")
	protected WebCacheHelper engineSettingCacheHelper;
	
	@Override
	public EngineSetting getEngineSettingById(final String id) {
		final WebElementType engineSettingElementType = WebElementType.ENGINE_SETTING;
		String engineSettingPrefixCacheKey = engineSettingCacheHelper.buildPrefixKey(engineSettingElementType);
		String engineSettingCacheKey = engineSettingPrefixCacheKey + "_" + id;
		EngineSetting engineSetting = (EngineSetting) engineSettingCacheHelper.getFromCache(engineSettingElementType, engineSettingCacheKey);
		if(engineSetting == null){
			engineSetting = super.getEngineSettingById(id);
			engineSettingCacheHelper.addToCache(engineSettingElementType, engineSettingCacheKey, engineSetting);
		}
		return engineSetting;
	}

	@Override
	public EngineSetting getEngineSettingByCode(final String code) {
		final WebElementType engineSettingElementType = WebElementType.ENGINE_SETTING;
		String engineSettingPrefixCacheKey = engineSettingCacheHelper.buildPrefixKey(engineSettingElementType);
		String engineSettingCacheKey = engineSettingPrefixCacheKey + "_" + code;
		EngineSetting engineSetting = (EngineSetting) engineSettingCacheHelper.getFromCache(engineSettingElementType, engineSettingCacheKey);
		if(engineSetting == null){
			engineSetting = super.getEngineSettingByCode(code);
			engineSettingCacheHelper.addToCache(engineSettingElementType, engineSettingCacheKey, engineSetting);
		}
		return engineSetting;
	}
	
	@Override
	public List<EngineSetting> findEngineSettings() {
		final WebElementType engineSettingElementType = WebElementType.ENGINE_SETTING;
		String engineSettingPrefixCacheKey = engineSettingCacheHelper.buildPrefixKey(engineSettingElementType);
		String engineSettingCacheKey = engineSettingPrefixCacheKey + "_ALL";
		List<EngineSetting> engineSettings = (List<EngineSetting>) engineSettingCacheHelper.getFromCache(engineSettingElementType, engineSettingCacheKey);
		if(engineSettings == null){
			engineSettings = super.findEngineSettings();
			engineSettingCacheHelper.addToCache(engineSettingElementType, engineSettingCacheKey, engineSettings);
		}
		return engineSettings;
	}
	
	// Engine Setting Value
	
	@Override
	public EngineSettingValue getEngineSettingValueById(final String id) {
		final WebElementType engineSettingElementType = WebElementType.ENGINE_SETTING;
		String engineSettingPrefixCacheKey = engineSettingCacheHelper.buildPrefixKey(engineSettingElementType);
		String engineSettingCacheKey = engineSettingPrefixCacheKey + "_VALUE_" + id;
		EngineSettingValue engineSettingValue = (EngineSettingValue) engineSettingCacheHelper.getFromCache(engineSettingElementType, engineSettingCacheKey);
		if(engineSettingValue == null){
			engineSettingValue = super.getEngineSettingValueById(id);
			engineSettingCacheHelper.addToCache(engineSettingElementType, engineSettingCacheKey, engineSettingValue);
		}
		return engineSettingValue;
	}
	
}