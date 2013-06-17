/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.service.cache.impl;

import javax.annotation.Resource;

import fr.hoteia.qalingo.core.domain.EngineSetting;
import fr.hoteia.qalingo.core.service.EngineSettingService;
import fr.hoteia.qalingo.core.service.impl.EngineSettingServiceImpl;
import fr.hoteia.qalingo.core.web.cache.util.CommonCacheHelper;
import fr.hoteia.qalingo.core.web.cache.util.WebElementType;

public class CacheEngineSettingServiceImpl extends EngineSettingServiceImpl {

	@Resource(name="engineSettingCacheHelper")
    protected CommonCacheHelper engineSettingCacheHelper;
	
	@Override
	public EngineSetting getEngineSettingByCode(final String code) {
		final WebElementType engineSettingElementType = WebElementType.ENGINE_SETTING;
		final String engineSettingCacheKey = code;
		EngineSetting engineSetting = (EngineSetting) engineSettingCacheHelper.getFromCache(engineSettingElementType, engineSettingCacheKey);
		if(engineSetting == null){
			engineSetting = super.getEngineSettingByCode(code);
			engineSettingCacheHelper.addToCache(engineSettingElementType, engineSettingCacheKey, engineSetting);
		}
		return engineSetting;
	}
	
	@Override
	public EngineSetting getAssetCatalogFilePath() {
		final WebElementType engineSettingElementType = WebElementType.ENGINE_SETTING;
		final String engineSettingCacheKey = EngineSettingService.ENGINE_SETTING_CODE_ASSET_CATALOG_FILE_PATH;
		EngineSetting engineSetting = (EngineSetting) engineSettingCacheHelper.getFromCache(engineSettingElementType, engineSettingCacheKey);
		if(engineSetting == null){
			engineSetting = super.getAssetCatalogFilePath();
			engineSettingCacheHelper.addToCache(engineSettingElementType, engineSettingCacheKey, engineSetting);
		}
		return engineSetting;
	}
	
	@Override
	public EngineSetting getAssetProductMarketingFilePath() {
		final WebElementType engineSettingElementType = WebElementType.ENGINE_SETTING;
		final String engineSettingCacheKey = EngineSettingService.ENGINE_SETTING_CODE_ASSET_PRODUCT_MARKETING_FILE_PATH;
		EngineSetting engineSetting = (EngineSetting) engineSettingCacheHelper.getFromCache(engineSettingElementType, engineSettingCacheKey);
		if(engineSetting == null){
			engineSetting = super.getAssetProductMarketingFilePath();
			engineSettingCacheHelper.addToCache(engineSettingElementType, engineSettingCacheKey, engineSetting);
		}
		return engineSetting;
	}
	
	@Override
	public EngineSetting getAssetPoductSkuFilePath() {
		final WebElementType engineSettingElementType = WebElementType.ENGINE_SETTING;
		final String engineSettingCacheKey = EngineSettingService.ENGINE_SETTING_CODE_ASSET_PROPDUCT_SKU_FILE_PATH;
		EngineSetting engineSetting = (EngineSetting) engineSettingCacheHelper.getFromCache(engineSettingElementType, engineSettingCacheKey);
		if(engineSetting == null){
			engineSetting = super.getAssetPoductSkuFilePath();
			engineSettingCacheHelper.addToCache(engineSettingElementType, engineSettingCacheKey, engineSetting);
		}
		return engineSetting;
	}
	
	@Override
	public EngineSetting getThemeResourcePrefixPath() {
		final WebElementType engineSettingElementType = WebElementType.ENGINE_SETTING;
		final String engineSettingCacheKey = EngineSettingService.ENGINE_SETTING_CODE_THEME_RESOURCE_PREFIX_PATH;
		EngineSetting engineSetting = (EngineSetting) engineSettingCacheHelper.getFromCache(engineSettingElementType, engineSettingCacheKey);
		if(engineSetting == null){
			engineSetting = super.getThemeResourcePrefixPath();
			engineSettingCacheHelper.addToCache(engineSettingElementType, engineSettingCacheKey, engineSetting);
		}
		return engineSetting;
	}

	@Override
	public EngineSetting getCatalogImageResourcePrefixPath() {
		final WebElementType engineSettingElementType = WebElementType.ENGINE_SETTING;
		final String engineSettingCacheKey = EngineSettingService.ENGINE_SETTING_CODE_CATALOG_RESOURCE_PREFIX_PATH;
		EngineSetting engineSetting = (EngineSetting) engineSettingCacheHelper.getFromCache(engineSettingElementType, engineSettingCacheKey);
		if(engineSetting == null){
			engineSetting = super.getCatalogImageResourcePrefixPath();
			engineSettingCacheHelper.addToCache(engineSettingElementType, engineSettingCacheKey, engineSetting);
		}
		return engineSetting;
	}
	
	@Override
	public EngineSetting withEscapeAccent() {
		final WebElementType engineSettingElementType = WebElementType.ENGINE_SETTING;
		final String engineSettingCacheKey = EngineSettingService.ENGINE_SETTING_ESCAPE_ACCENT_FROM_URL;
		EngineSetting engineSetting = (EngineSetting) engineSettingCacheHelper.getFromCache(engineSettingElementType, engineSettingCacheKey);
		if(engineSetting == null){
			engineSetting = super.withEscapeAccent();
			engineSettingCacheHelper.addToCache(engineSettingElementType, engineSettingCacheKey, engineSetting);
		}
		return engineSetting;
	}
	
}