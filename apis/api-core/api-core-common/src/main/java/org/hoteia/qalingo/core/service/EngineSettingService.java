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

import org.apache.commons.lang.BooleanUtils;
import org.hoteia.qalingo.core.dao.EngineSettingDao;
import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("engineSettingService")
@Transactional
public class EngineSettingService {

    // TODO : enum or not for tall this key ?
    
    public static final String ENGINE_SETTING_ENVIRONMENT_STAGING_MODE_ENABLED         = "ENVIRONMENT_STAGING_MODE_ENABLED";
    public static final String ENGINE_SETTING_ENVIRONMENT_TYPE                         = "ENVIRONMENT_TYPE";

    public static final String ENGINE_SETTING_CODE_ASSET_FILE_ROOT_PATH                = "ASSET_FILE_ROOT_PATH";
    public static final String ENGINE_SETTING_CODE_ASSET_WEB_ROOT_PATH                 = "ASSET_WEB_ROOT_PATH";
    public static final String ENGINE_SETTING_CODE_ASSET_CATALOG_FILE_PATH             = "ASSET_CATALOG_FILE_PATH";
    public static final String ENGINE_SETTING_CODE_ASSET_PRODUCT_MARKETING_FILE_PATH   = "ASSET_PRODUCT_MARKETING_FILE_PATH";
    public static final String ENGINE_SETTING_CODE_ASSET_PROPDUCT_SKU_FILE_PATH        = "ASSET_PROPDUCT_SKU_FILE_PATH";
    public static final String ENGINE_SETTING_CODE_ASSET_RETAILER_STORE_FILE_PATH      = "ASSET_RETAILER_STORE_FILE_PATH";

    public static final String ENGINE_SETTING_CODE_THEME_RESOURCE_PREFIX_PATH  = "THEME_RESOURCE_PREFIX_PATH";
    public static final String WEB_CACHE_ELEMENT_TIME_TO_LIVE                  = "WEB_CACHE_ELEMENT_TIME_TO_LIVE";

    public static final String ENGINE_SETTING_ESCAPE_ACCENT_FROM_URL   = "ESCAPE_ACCENT_FROM_URL";

    public static final String ENGINE_SETTING_CODE_SPRING_BATCH_URL    = "SPRING_BATCH_URL";
    public static final String ENGINE_SETTING_CONTEXT_CMS              = "CMS";
    public static final String ENGINE_SETTING_CONTEXT_CRM              = "CRM";
    public static final String ENGINE_SETTING_CONTEXT_ERP              = "ERP";
    public static final String ENGINE_SETTING_CONTEXT_NOTIFICATION     = "NOTIFICATION";

    public static final String ENGINE_SETTING_CODE_SOLR           = "SOLR";
    public static final String ENGINE_SETTING_SOLR_MASTER_CONTEXT = "MASTER";
    
    // Document order
    public static final String ENGINE_SETTING_DOCUMENT_FILE_FOLDER_PATH         = "DOCUMENT_FILE_FOLDER_PATH";
    public static final String ENGINE_SETTING_DOCUMENT_FILE_WEB_PATH            = "DOCUMENT_FILE_WEB_PATH";
    
    public static final String ENGINE_SETTING_DEFAULT_ORDER_CONFIRMATION_TEMPLATE      = "DEFAULT_ORDER_CONFIRMATION_TEMPLATE";
    public static final String ENGINE_SETTING_DEFAULT_SHIPPING_CONFIRMATION_TEMPLATE   = "DEFAULT_SHIPPING_CONFIRMATION_TEMPLATE";
    public static final String ENGINE_SETTING_DEFAULT_INVOICE_TEMPLATE                 = "DEFAULT_INVOICE_TEMPLATE";

    // Email file mirroring
    public static final String ENGINE_SETTING_EMAIL_FILE_MIRRORING_ACTIVATED           = "EMAIL_FILE_MIRRORING_ACTIVATED";
    public static final String ENGINE_SETTING_EMAIL_FILE_MIRRORING_FOLDER_PATH         = "EMAIL_FILE_MIRRORING_FOLDER_PATH";
    public static final String ENGINE_SETTING_EMAIL_FILE_MIRRORING_WEB_PATH            = "EMAIL_FILE_MIRRORING_FOLDER_WEB";
    public static final String ENGINE_SETTING_EMAIL_FILE_MIRRORING_EXTENSION           = "EMAIL_FILE_MIRRORING_EXTENSION";
    
    // COUNT ITEM BY PAGE - PAGE SIZE
    public static final String ENGINE_SETTING_CODE_COUNT_ITEM_BY_PAGE                  = "COUNT_ITEM_BY_PAGE";
    public static final String ENGINE_SETTING_CONTEXT_BO_TECHNICAL_ENGINE_SETTING_LIST = "BO_TECHNICAL_ENGINE_SETTING_LIST";
    public static final String ENGINE_SETTING_CONTEXT_BO_TECHNICAL_USER_LIST           = "BO_TECHNICAL_USER_LIST";
    public static final String ENGINE_SETTING_CONTEXT_BO_TECHNICAL_CACHE_LIST          = "BO_TECHNICAL_CACHE_LIST";
    public static final String ENGINE_SETTING_CONTEXT_BO_TECHNICAL_BATCH_LIST          = "BO_TECHNICAL_BATCH_LIST";

    // Web tracking like: Google Analytics
    public static final String ENGINE_SETTING_WEB_TRACKING_NUMBER                      = "WEB_TRACKING_NUMBER";
    public static final String ENGINE_SETTING_WEB_TRACKING_NAME                        = "WEB_TRACKING_NAME";

    // Web monitoring like: Pingdom
    public static final String ENGINE_SETTING_WEB_MONITORING_NUMBER                    = "WEB_MONITORING_NUMBER";
    public static final String ENGINE_SETTING_WEB_MONITORING_NAME                      = "WEB_MONITORING_NAME";
    
    // OAUTH : FACEBOOK, TWITTER, GOOGLE, etc
    public static final String ENGINE_SETTING_OAUTH_APP_KEY_OR_ID                      = "OAUTH_APP_KEY_OR_ID";
    public static final String ENGINE_SETTING_OAUTH_APP_SECRET                         = "OAUTH_APP_SECRET";
    public static final String ENGINE_SETTING_OAUTH_APP_PERMISSIONS                    = "OAUTH_APP_PERMISSIONS";

    // MAX USER LOGIN SUCCES TO LOG
    public static final String ENGINE_SETTING_MAX_USER_CONNECTION_LOG      = "MAX_USER_CONNECTION_LOG";
    public static final String ENGINE_SETTING_MAX_CUSTOMER_CONNECTION_LOG  = "MAX_CUSTOMER_CONNECTION_LOG";

    // SCORE
    public static final String ENGINE_SETTING_CONTEXT_STAR_SCORE_MAX       = "STAR_SCORE_MAX";
    public static final String ENGINE_SETTING_CONTEXT_PRODUCT_SCORE_MAX    = "PRODUCT_QUALITY_OF_SERVICE";
    public static final String ENGINE_SETTING_CONTEXT_RETAILER_SCORE_MAX   = "RETAILER_QUALITY_OF_SERVICE";

    // GEOLOC
    public static final String ENGINE_SETTING_GEOLOC_CITY_DATABASE_PATH         = "CITY_DATABASE_PATH";
    public static final String ENGINE_SETTING_GEOLOC_COUNTRY_DATABASE_PATH      = "COUNTRY_DATABASE_PATH";

    
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
        return getEngineSettingByCode(ENGINE_SETTING_ENVIRONMENT_STAGING_MODE_ENABLED);
    }

    public EngineSetting getEnvironmentType() {
        return getEngineSettingByCode(ENGINE_SETTING_ENVIRONMENT_TYPE);
    }

    public EngineSetting getAssetFileRootPath() {
        return getEngineSettingByCode(ENGINE_SETTING_CODE_ASSET_FILE_ROOT_PATH);
    }

    public EngineSetting getAssetWebRootPath() {
        return getEngineSettingByCode(ENGINE_SETTING_CODE_ASSET_WEB_ROOT_PATH);
    }

    public EngineSetting getAssetCatalogFilePath() {
        return getEngineSettingByCode(ENGINE_SETTING_CODE_ASSET_CATALOG_FILE_PATH);
    }

    public EngineSetting getAssetProductMarketingFilePath() {
        return getEngineSettingByCode(ENGINE_SETTING_CODE_ASSET_PRODUCT_MARKETING_FILE_PATH);
    }

    public EngineSetting getAssetPoductSkuFilePath() {
        return getEngineSettingByCode(ENGINE_SETTING_CODE_ASSET_PROPDUCT_SKU_FILE_PATH);
    }
    
    public EngineSetting getAssetRetailerAndStoreFilePath() {
        return getEngineSettingByCode(ENGINE_SETTING_CODE_ASSET_RETAILER_STORE_FILE_PATH);
    }

    public EngineSetting getThemeResourcePrefixPath() {
        return getEngineSettingByCode(ENGINE_SETTING_CODE_THEME_RESOURCE_PREFIX_PATH);
    }

    public EngineSetting withEscapeAccent() {
        return getEngineSettingByCode(ENGINE_SETTING_ESCAPE_ACCENT_FROM_URL);
    }

    public EngineSetting getProductMaxScoreValue() {
        return getEngineSettingByCode(ENGINE_SETTING_CONTEXT_PRODUCT_SCORE_MAX);
    }

    public EngineSetting getRetailerMaxScoreValue() {
        return getEngineSettingByCode(ENGINE_SETTING_CONTEXT_RETAILER_SCORE_MAX);
    }

    public EngineSetting getOAuthAppKeyOrId() {
        return getEngineSettingByCode(ENGINE_SETTING_OAUTH_APP_KEY_OR_ID);
    }

    public EngineSetting getOAuthAppSecret() {
        return getEngineSettingByCode(ENGINE_SETTING_OAUTH_APP_SECRET);
    }

    public EngineSetting getOAuthAppPermissions() {
        return getEngineSettingByCode(ENGINE_SETTING_OAUTH_APP_PERMISSIONS);
    }

    public EngineSetting getWebTrackingNumber() {
        return getEngineSettingByCode(ENGINE_SETTING_WEB_TRACKING_NUMBER);
    }

    public EngineSetting getWebTrackingName() {
        return getEngineSettingByCode(ENGINE_SETTING_WEB_TRACKING_NAME);
    }

    public EngineSetting getWebMonitoringNumber() {
        return getEngineSettingByCode(ENGINE_SETTING_WEB_MONITORING_NUMBER);
    }

    public EngineSetting getWebMonitoringName() {
        return getEngineSettingByCode(ENGINE_SETTING_WEB_MONITORING_NAME);
    }

    // GEOLOC SETTINGS

    public EngineSetting getGeolocCityFilePath() {
        return getEngineSettingByCode(ENGINE_SETTING_GEOLOC_CITY_DATABASE_PATH);
    }

    public EngineSetting getGeolocCountryFilePath() {
        return getEngineSettingByCode(ENGINE_SETTING_GEOLOC_COUNTRY_DATABASE_PATH);
    }

    // DOCUMENT SETTINGS

    public EngineSetting getDocumentFileRootPath() {
        return getEngineSettingByCode(ENGINE_SETTING_DOCUMENT_FILE_FOLDER_PATH);
    }

    public EngineSetting getDocumentWebRootPath() {
        return getEngineSettingByCode(ENGINE_SETTING_DOCUMENT_FILE_WEB_PATH);
    }

    public EngineSetting getDefaultOrderConfirmationTemplate() {
        return getEngineSettingByCode(ENGINE_SETTING_DEFAULT_ORDER_CONFIRMATION_TEMPLATE);
    }

    public EngineSetting getDefaultShippingConfirmationTemplate() {
        return getEngineSettingByCode(ENGINE_SETTING_DEFAULT_SHIPPING_CONFIRMATION_TEMPLATE);
    }

    public EngineSetting getDefaultInvoiceTemplate() {
        return getEngineSettingByCode(ENGINE_SETTING_DEFAULT_INVOICE_TEMPLATE);
    }

    // EMAIL SETTINGS

    public EngineSetting getEmailFileMirroringActivated() {
        return getEngineSettingByCode(ENGINE_SETTING_EMAIL_FILE_MIRRORING_ACTIVATED);
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
        return getEngineSettingByCode(ENGINE_SETTING_EMAIL_FILE_MIRRORING_FOLDER_PATH);
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
        return getEngineSettingByCode(ENGINE_SETTING_EMAIL_FILE_MIRRORING_WEB_PATH);
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
        return getEngineSettingByCode(ENGINE_SETTING_EMAIL_FILE_MIRRORING_EXTENSION);
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
    
    // ASSETS
    
    /**
     * 
     */
    public String getRootAssetFilePath() throws Exception {
        EngineSetting engineSetting = getAssetFileRootPath();
        String prefixPath = "";
        if (engineSetting != null) {
            prefixPath = engineSetting.getDefaultValue();
        }
        if (prefixPath.endsWith("/")) {
            prefixPath = prefixPath.substring(0, prefixPath.length() - 1);
        }
        return prefixPath;
    }

    /**
     * 
     */
    public String getRootAssetWebPath() throws Exception {
        EngineSetting engineSetting = getAssetWebRootPath();
        String prefixPath = "";
        if (engineSetting != null) {
            prefixPath = engineSetting.getDefaultValue();
        }
        if (prefixPath.endsWith("/")) {
            prefixPath = prefixPath.substring(0, prefixPath.length() - 1);
        }
        return prefixPath;
    }

    /**
     * 
     */
    public String getCatalogImageWebPath(final Asset asset) throws Exception {
        EngineSetting engineSetting = getAssetCatalogFilePath();
        String prefixPath = "";
        if (engineSetting != null) {
            prefixPath = engineSetting.getDefaultValue();
        }
        String catalogImageWebPath = getRootAssetWebPath() + prefixPath + "/" + asset.getType().toLowerCase() + "/" + asset.getPath();
        if (catalogImageWebPath.endsWith("/")) {
            catalogImageWebPath = catalogImageWebPath.substring(0, catalogImageWebPath.length() - 1);
        }
        return catalogImageWebPath;
    }

    /**
     * 
     */
    public String getProductMarketingImageWebPath(final Asset asset) throws Exception {
        EngineSetting engineSetting = getAssetProductMarketingFilePath();
        String prefixPath = "";
        if (engineSetting != null) {
            prefixPath = engineSetting.getDefaultValue();
        }
        String productMarketingImageWebPath = getRootAssetWebPath() + prefixPath + "/" + asset.getType().toLowerCase() + "/" + asset.getPath();
        if (productMarketingImageWebPath.endsWith("/")) {
            productMarketingImageWebPath = productMarketingImageWebPath.substring(0, productMarketingImageWebPath.length() - 1);
        }
        return productMarketingImageWebPath;
    }

    /**
     * 
     */
    public String getProductSkuImageWebPath(final Asset asset) throws Exception {
        EngineSetting engineSetting = getAssetPoductSkuFilePath();
        String prefixPath = "";
        if (engineSetting != null) {
            prefixPath = engineSetting.getDefaultValue();
        }
        String productSkuImageWebPath = getRootAssetWebPath() + prefixPath + "/" + asset.getType().toLowerCase() + "/" + asset.getPath();
        if (productSkuImageWebPath.endsWith("/")) {
            productSkuImageWebPath = productSkuImageWebPath.substring(0, productSkuImageWebPath.length() - 1);
        }
        return productSkuImageWebPath;
    }
    
    /**
     * 
     */
    public String getRetailerOrStoreImageWebPath(final Asset asset) throws Exception {
        EngineSetting engineSetting = getAssetRetailerAndStoreFilePath();
        String prefixPath = "";
        if (engineSetting != null) {
            prefixPath = engineSetting.getDefaultValue();
        }
        String storeImageWebPath = getRootAssetWebPath() + prefixPath + "/" + asset.getScope().toLowerCase() + "/" + asset.getType().toLowerCase() + "/" + asset.getPath();
        if (storeImageWebPath.endsWith("/")) {
            storeImageWebPath = storeImageWebPath.substring(0, storeImageWebPath.length() - 1);
        }
        return storeImageWebPath;
    }
    
}