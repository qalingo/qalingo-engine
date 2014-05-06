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

import org.apache.commons.lang.BooleanUtils;
import org.hoteia.qalingo.core.dao.EngineSettingDao;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.service.EngineSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("engineSettingService")
@Transactional
public class EngineSettingServiceImpl implements EngineSettingService {

    @Autowired
    private EngineSettingDao engineSettingDao;

    // Engine Setting

    public EngineSetting getEngineSettingById(final Long engineSettingId, Object... params) {
        return engineSettingDao.getEngineSettingById(engineSettingId, params);
    }

    public EngineSetting getEngineSettingById(final String rawEngineSettingId, Object... params) {
        long engineSettingId = -1;
        try {
            engineSettingId = Long.parseLong(rawEngineSettingId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getEngineSettingById(engineSettingId, params);
    }

    public EngineSetting getEngineSettingByCode(final String code, Object... params) {
        return engineSettingDao.getEngineSettingByCode(code, params);
    }

    public List<EngineSetting> findEngineSettings(Object... params) {
        return engineSettingDao.findEngineSettings(params);
    }

    public EngineSetting saveOrUpdateEngineSetting(final EngineSetting engineSetting) {
        return engineSettingDao.saveEngineSetting(engineSetting);
    }

    public void deleteEngineSetting(final EngineSetting engineSetting) {
        engineSettingDao.deleteEngineSetting(engineSetting);
    }

    // Engine Setting Value

    public EngineSettingValue getEngineSettingValueById(final Long engineSettingValueId, Object... params) {
        return engineSettingDao.getEngineSettingValueById(engineSettingValueId, params);
    }

    public EngineSettingValue getEngineSettingValueById(final String rawEngineSettingValueId, Object... params) {
        long engineSettingValueId = -1;
        try {
            engineSettingValueId = Long.parseLong(rawEngineSettingValueId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getEngineSettingValueById(engineSettingValueId, params);
    }

    public EngineSettingValue saveOrUpdateEngineSettingValue(final EngineSettingValue engineSettingValue) {
        return engineSettingDao.saveOrUpdateEngineSettingValue(engineSettingValue);
    }

    public String getEngineSettingValueByCode(final String engineSettingCode, final String engineSettingValueCode) {
        EngineSetting engineSetting = getEngineSettingByCode(engineSettingCode);
        if (engineSetting != null) {
            EngineSettingValue engineSettingValue = engineSetting.getEngineSettingValue(engineSettingValueCode);
            if (engineSettingValue != null) {
                return engineSettingValue.getValue();
            } else {
                return engineSetting.getDefaultValue();
            }
        }
        return null;
    }

    public String getEngineSettingDefaultValueByCode(final String engineSettingCode) {
        EngineSetting engineSetting = getEngineSettingByCode(engineSettingCode);
        if (engineSetting != null) {
            return engineSetting.getDefaultValue();
        }
        return null;
    }

    // Common Engine Setting Value

    public EngineSetting getEnvironmentStagingModeEnabled() {
        return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_ENVIRONMENT_STAGING_MODE_ENABLED);
    }

    public EngineSetting getEnvironmentType() {
        return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_ENVIRONMENT_TYPE);
    }

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
    
    public EngineSetting getAssetRetailerAndStoreFilePath() {
        return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_CODE_ASSET_RETAILER_STORE_FILE_PATH);
    }

    public EngineSetting getThemeResourcePrefixPath() {
        return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_CODE_THEME_RESOURCE_PREFIX_PATH);
    }

    public EngineSetting withEscapeAccent() {
        return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_ESCAPE_ACCENT_FROM_URL);
    }

    public EngineSetting getProductMaxScoreValue() {
        return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_CONTEXT_PRODUCT_SCORE_MAX);
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

    // GEOLOC SETTINGS

    public EngineSetting getGeolocCityFilePath() {
        return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_GEOLOC_CITY_DATABASE_PATH);
    }

    public EngineSetting getGeolocCountryFilePath() {
        return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_GEOLOC_COUNTRY_DATABASE_PATH);
    }

    // DOCUMENT SETTINGS

    public EngineSetting getDocumentFileRootPath() {
        return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_DOCUMENT_FILE_FOLDER_PATH);
    }

    public EngineSetting getDocumentWebRootPath() {
        return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_DOCUMENT_FILE_WEB_PATH);
    }

    public EngineSetting getDefaultOrderConfirmationTemplate() {
        return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_DEFAULT_ORDER_CONFIRMATION_TEMPLATE);
    }

    public EngineSetting getDefaultShippingConfirmationTemplate() {
        return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_DEFAULT_SHIPPING_CONFIRMATION_TEMPLATE);
    }

    public EngineSetting getDefaultInvoiceTemplate() {
        return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_DEFAULT_INVOICE_TEMPLATE);
    }

    // EMAIL SETTINGS

    public EngineSetting getEmailFileMirroringActivated() {
        return getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_EMAIL_FILE_MIRRORING_ACTIVATED);
    }

    public boolean getEmailFileMirroringActivated(String context) {
        EngineSetting engineSetting = getEmailFileMirroringActivated();
        boolean emailFileMirroringActivated = false;
        if (engineSetting != null) {
            emailFileMirroringActivated = BooleanUtils.toBoolean(engineSetting.getDefaultValue());
            EngineSettingValue engineSettingValue = engineSetting.getEngineSettingValue(context);
            if (engineSettingValue != null) {
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
        if (engineSetting != null) {
            emailFileMirroringFolderPath = engineSetting.getDefaultValue();
            EngineSettingValue engineSettingValue = engineSetting.getEngineSettingValue(context);
            if (engineSettingValue != null) {
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
        if (engineSetting != null) {
            emailFileMirroringWebPath = engineSetting.getDefaultValue();
            EngineSettingValue engineSettingValue = engineSetting.getEngineSettingValue(context);
            if (engineSettingValue != null) {
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
        if (engineSetting != null) {
            emailFileMirroringPath = engineSetting.getDefaultValue();
            EngineSettingValue engineSettingValue = engineSetting.getEngineSettingValue(context);
            if (engineSettingValue != null) {
                emailFileMirroringPath = engineSettingValue.getValue();
            }
        }
        return emailFileMirroringPath;
    }
}