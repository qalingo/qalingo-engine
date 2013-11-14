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

import org.apache.commons.lang.BooleanUtils;
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
	
	public EngineSetting getAssetFileRootPath() {
		return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_CODE_ASSET_FILE_ROOT_PATH);
	}
	
	public EngineSetting getAssetWebRootPath() {
		return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_CODE_ASSET_WEB_ROOT_PATH);
	}
	
	public EngineSetting getAssetCatalogFilePath() {
		return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_CODE_ASSET_CATALOG_FILE_PATH);
	}
	
	public EngineSetting getAssetProductMarketingFilePath() {
		return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_CODE_ASSET_PRODUCT_MARKETING_FILE_PATH);
	}
	
	public EngineSetting getAssetPoductSkuFilePath() {
		return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_CODE_ASSET_PROPDUCT_SKU_FILE_PATH);
	}
	
	public EngineSetting getThemeResourcePrefixPath() {
		return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_CODE_THEME_RESOURCE_PREFIX_PATH);
	}
	
	public EngineSetting withEscapeAccent() {
		return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_ESCAPE_ACCENT_FROM_URL);
	}
	
	public EngineSetting getRetailerMaxScoreValue() {
		return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_CONTEXT_RETAILER_SCORE_MAX);
	}

	public EngineSetting getOAuthAppKeyOrId() {
		return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_OAUTH_APP_KEY_OR_ID);
	}

	public EngineSetting getOAuthAppSecret() {
		return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_OAUTH_APP_SECRET);
	}
	
	public EngineSetting getOAuthAppPermissions() {
		return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_OAUTH_APP_PERMISSIONS);
	}
	
	public EngineSetting getWebTrackingNumber() {
		return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_WEB_TRACKING_NUMBER);
	}
	
	public EngineSetting getWebTrackingName() {
		return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_WEB_TRACKING_NAME);
	}
	
	public EngineSetting getWebMonitoringNumber() {
		return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_WEB_MONITORING_NUMBER);
	}
	
	public EngineSetting getWebMonitoringName() {
		return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_WEB_MONITORING_NAME);
	}
	
	// EMAIL SETTINGS
	
	public EngineSetting getEmailFileMirroringActivated() {
		return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_EMAIL_FILE_MIRRORING_ACTIVATED);
	}

	public boolean getEmailFileMirroringActivated(String context) {
		EngineSetting engineSetting = getEmailFileMirroringActivated();
		boolean emailFileMirroringActivated = false;
		if(engineSetting != null){
			emailFileMirroringActivated = BooleanUtils.toBoolean(engineSetting.getDefaultValue());
			EngineSettingValue engineSettingValue = engineSetting.getEngineSettingValue(context);
			if(engineSettingValue != null){
				emailFileMirroringActivated = BooleanUtils.toBoolean(engineSettingValue.getValue());
			}
		}
		return emailFileMirroringActivated;
	}
	
	public EngineSetting getEmailFileMirroringFolderPath() {
		return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_EMAIL_FILE_MIRRORING_FOLDER_PATH);
	}
	
	public String getEmailFileMirroringFolderPath(String context) {
		EngineSetting engineSetting = getEmailFileMirroringFolderPath();
		String emailFileMirroringFolderPath = "";
		if(engineSetting != null){
			emailFileMirroringFolderPath = engineSetting.getDefaultValue();
			EngineSettingValue engineSettingValue = engineSetting.getEngineSettingValue(context);
			if(engineSettingValue != null){
				emailFileMirroringFolderPath = engineSettingValue.getValue();
			}
		}
		return emailFileMirroringFolderPath;
	}
	
	public EngineSetting getEmailFileMirroringWebPath() {
		return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_EMAIL_FILE_MIRRORING_WEB_PATH);
	}
	
	public String getEmailFileMirroringWebPath(String context) {
		EngineSetting engineSetting = getEmailFileMirroringWebPath();
		String emailFileMirroringWebPath = "";
		if(engineSetting != null){
			emailFileMirroringWebPath = engineSetting.getDefaultValue();
			EngineSettingValue engineSettingValue = engineSetting.getEngineSettingValue(context);
			if(engineSettingValue != null){
				emailFileMirroringWebPath = engineSettingValue.getValue();
			}
		}
		return emailFileMirroringWebPath;
	}
	
	public EngineSetting getEmailFileMirroringExtension() {
		return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_EMAIL_FILE_MIRRORING_EXTENSION);
	}
	
	public String getEmailFileMirroringExtension(String context) {
		EngineSetting engineSetting = getEmailFileMirroringExtension();
		String emailFileMirroringPath = "";
		if(engineSetting != null){
			emailFileMirroringPath = engineSetting.getDefaultValue();
			EngineSettingValue engineSettingValue = engineSetting.getEngineSettingValue(context);
			if(engineSettingValue != null){
				emailFileMirroringPath = engineSettingValue.getValue();
			}
		}
		return emailFileMirroringPath;
	}
}