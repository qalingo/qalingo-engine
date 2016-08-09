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

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.annotation.CacheMethodInformation;
import org.hoteia.qalingo.core.annotation.CacheType;
import org.hoteia.qalingo.core.dao.EngineSettingDao;
import org.hoteia.qalingo.core.domain.AbstractCmsEntity;
import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.CmsContent;
import org.hoteia.qalingo.core.domain.CmsContentAsset;
import org.hoteia.qalingo.core.domain.CmsContentBlock;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.util.CoreUtil;
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
    public static final String ENGINE_SETTING_CODE_ASSET_PRODUCT_SKU_FILE_PATH         = "ASSET_PRODUCT_SKU_FILE_PATH";
    public static final String ENGINE_SETTING_CODE_ASSET_PRODUCT_BRAND_FILE_PATH       = "ASSET_PRODUCT_BRAND_FILE_PATH";
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
    public static final String ENGINE_SETTING_DEFAULT_EMAIL_ADDRESS                    = "DEFAULT_EMAIL_ADDRESS";
    
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

    // Web monitoring like: Google reCaptcha
    public static final String ENGINE_SETTING_WEB_CAPTCHA_SITE_KEY                    = "WEB_CAPTCHA_SITE_KEY";
    public static final String ENGINE_SETTING_WEB_CAPTCHA_SECRET_KEY                  = "WEB_CAPTCHA_SECRET_KEY";

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
    public static final String ENGINE_SETTING_GOOGLE_GEOLOC_API_KEY             = "GOOGLE_GEOLOC_API_KEY";
    public static final String ENGINE_SETTING_GOOGLE_GEOLOC_OVER_QUOTA_KEY      = "GOOGLE_GEOLOC_OVER_QUOTA_TIMESTAMP";
    public static final String ENGINE_SETTING_GOOGLE_MAP_API_KEY                = "GOOGLE_MAP_API_KEY";
    public static final String ENGINE_SETTING_GOOGLE_MAP_OVER_QUOTA_KEY         = "GOOGLE_MAP_OVER_QUOTA_TIMESTAMP";

    // LIGTH CMS
    public static final String ENGINE_SETTING_CODE_ASSET_CMS_CONTENT_FILE_PATH   = "ASSET_CMS_CONTENT_FILE_PATH";

    // MANGOPAY
    public static final String ENGINE_SETTING_MANGOPAY_CLIENT_ID                = "MANGOPAY_CLIENT_ID";
    public static final String ENGINE_SETTING_MANGOPAY_CLIENT_PASSWORD          = "MANGOPAY_CLIENT_PASSWORD";
    public static final String ENGINE_SETTING_MANGOPAY_BASE_URL                 = "MANGOPAY_DEFAULT_BASE_URL";
    public static final String ENGINE_SETTING_MANGOPAY_DEFAULT_OWNER_ID         = "MANGOPAY_DEFAULT_OWNER_ID";
    public static final String ENGINE_SETTING_MANGOPAY_DASHBOARD_URL            = "MANGOPAY_DASHBOARD_URL";
    public static final String ENGINE_SETTING_MANGOPAY_PAYOUT_FEES_PERCENTAGE   = "MANGOPAY_PAYOUT_FEES_PERCENTAGE";
    
    public static SimpleDateFormat timestampPattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    
    @Autowired
    protected EngineSettingDao engineSettingDao;

    @Autowired
    protected ProductService productService;

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

    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
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

    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getEngineSettingDefaultValueByCode(final String engineSettingCode) {
        EngineSetting engineSetting = getEngineSettingByCode(engineSettingCode);
        if (engineSetting != null) {
            return engineSetting.getDefaultValue();
        }
        return null;
    }

    // Common Engine Setting Value

    public EngineSetting getSettingEnvironmentStagingModeEnabled() {
        return getEngineSettingByCode(ENGINE_SETTING_ENVIRONMENT_STAGING_MODE_ENABLED);
    }

    public EngineSetting getSettingEnvironmentType() {
        return getEngineSettingByCode(ENGINE_SETTING_ENVIRONMENT_TYPE);
    }

    public EngineSetting getSettingAssetFileRootPath() {
        return getEngineSettingByCode(ENGINE_SETTING_CODE_ASSET_FILE_ROOT_PATH);
    }

    public EngineSetting getSettingAssetWebRootPath() {
        return getEngineSettingByCode(ENGINE_SETTING_CODE_ASSET_WEB_ROOT_PATH);
    }

    public EngineSetting getSettingAssetCatalogFilePath() {
        return getEngineSettingByCode(ENGINE_SETTING_CODE_ASSET_CATALOG_FILE_PATH);
    }

    public EngineSetting getSettingAssetProductMarketingFilePath() {
        return getEngineSettingByCode(ENGINE_SETTING_CODE_ASSET_PRODUCT_MARKETING_FILE_PATH);
    }

    public EngineSetting getSettingAssetProductSkuFilePath() {
        return getEngineSettingByCode(ENGINE_SETTING_CODE_ASSET_PRODUCT_SKU_FILE_PATH);
    }
    
    public EngineSetting getSettingAssetProductBrandFilePath() {
        return getEngineSettingByCode(ENGINE_SETTING_CODE_ASSET_PRODUCT_BRAND_FILE_PATH);
    }
    
    public EngineSetting getSettingAssetRetailerAndStoreFilePath() {
        return getEngineSettingByCode(ENGINE_SETTING_CODE_ASSET_RETAILER_STORE_FILE_PATH);
    }

    public EngineSetting getSettingThemeResourcePrefixPath() {
        return getEngineSettingByCode(ENGINE_SETTING_CODE_THEME_RESOURCE_PREFIX_PATH);
    }

    public EngineSetting getSettingWithEscapeAccent() {
        return getEngineSettingByCode(ENGINE_SETTING_ESCAPE_ACCENT_FROM_URL);
    }

    public EngineSetting getSettingProductMaxScoreValue() {
        return getEngineSettingByCode(ENGINE_SETTING_CONTEXT_PRODUCT_SCORE_MAX);
    }

    public EngineSetting getSettingRetailerMaxScoreValue() {
        return getEngineSettingByCode(ENGINE_SETTING_CONTEXT_RETAILER_SCORE_MAX);
    }

    public EngineSetting getSettingOAuthAppKeyOrId() {
        return getEngineSettingByCode(ENGINE_SETTING_OAUTH_APP_KEY_OR_ID);
    }

    public EngineSetting getSettingOAuthAppSecret() {
        return getEngineSettingByCode(ENGINE_SETTING_OAUTH_APP_SECRET);
    }

    public EngineSetting getSettingOAuthAppPermissions() {
        return getEngineSettingByCode(ENGINE_SETTING_OAUTH_APP_PERMISSIONS);
    }

    public EngineSetting getSettingWebTrackingNumber() {
        return getEngineSettingByCode(ENGINE_SETTING_WEB_TRACKING_NUMBER);
    }

    public EngineSetting getSettingWebTrackingName() {
        return getEngineSettingByCode(ENGINE_SETTING_WEB_TRACKING_NAME);
    }

    public EngineSetting getSettingWebMonitoringNumber() {
        return getEngineSettingByCode(ENGINE_SETTING_WEB_MONITORING_NUMBER);
    }

    public EngineSetting getSettingWebMonitoringName() {
        return getEngineSettingByCode(ENGINE_SETTING_WEB_MONITORING_NAME);
    }
    
    public EngineSetting getSettingWebCaptchaSecretKey() {
        return getEngineSettingByCode(ENGINE_SETTING_WEB_CAPTCHA_SECRET_KEY);
    }

    public EngineSetting getSettingWebCaptchaSiteKey() {
        return getEngineSettingByCode(ENGINE_SETTING_WEB_CAPTCHA_SITE_KEY);
    }

    // GEOLOC SETTINGS

    public EngineSetting getSettingGeolocCityFilePath() {
        return getEngineSettingByCode(ENGINE_SETTING_GEOLOC_CITY_DATABASE_PATH);
    }

    public EngineSetting getSettingGeolocCountryFilePath() {
        return getEngineSettingByCode(ENGINE_SETTING_GEOLOC_COUNTRY_DATABASE_PATH);
    }

    public EngineSetting getSettingGoogleGeolocationApiKey() {
        return getEngineSettingByCode(ENGINE_SETTING_GOOGLE_GEOLOC_API_KEY);
    }
    
    public EngineSetting getSettingGoogleGeolocationApiQuotaTimeStamp() {
        return getEngineSettingByCode(ENGINE_SETTING_GOOGLE_GEOLOC_OVER_QUOTA_KEY);
    }
    
    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getGoogleGeolocationApiKey() throws Exception {
        EngineSetting engineSetting = getSettingGoogleGeolocationApiKey();
        String key = "";
        if (engineSetting != null) {
            key = engineSetting.getDefaultValue();
        }
        return key;
    }
    
    public boolean isGoogleGeolocationApiOverQuotas() throws Exception {
        EngineSetting engineSetting = getSettingGoogleGeolocationApiQuotaTimeStamp();
        String timestamp = null;
        if (engineSetting != null) {
            timestamp = engineSetting.getDefaultValue();
        }
        if(timestamp != null){
            return true;
        }
        return false;
    }
    
    public boolean isGoogleGeolocationApiStillOverQuotas(final Date newDate) throws ParseException {
        EngineSetting engineSetting = getSettingGoogleGeolocationApiQuotaTimeStamp();
        String timestamp = null;
        if (engineSetting != null) {
            timestamp = engineSetting.getDefaultValue();
        }
        if(timestamp != null){
            Date dateOverQuota = timestampPattern.parse(timestamp);
            if(newDate.getTime() > (dateOverQuota.getTime() + Constants.MILLISECONDS_IN_A_DAY.longValue())){
                return false;
            }
            return true;
        }
        return false;
    }
    
    public void flagSettingGoogleGeolocationApiOverQuota() {
        EngineSetting engineSetting = getSettingGoogleGeolocationApiQuotaTimeStamp();
        engineSetting.setDefaultValue(timestampPattern.format(new Date()));
        saveOrUpdateEngineSetting(engineSetting);
    }

    // GOOGLE MAP SETTINGS

    public EngineSetting getSettingGoogleMapApiKey() {
        return getEngineSettingByCode(ENGINE_SETTING_GOOGLE_MAP_API_KEY);
    }
    
    public EngineSetting getSettingGoogleMapApiQuotaTimeStamp() {
        return getEngineSettingByCode(ENGINE_SETTING_GOOGLE_MAP_OVER_QUOTA_KEY);
    }
    
    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getGoogleMapApiKey() throws Exception {
        EngineSetting engineSetting = getSettingGoogleMapApiKey();
        String key = "";
        if (engineSetting != null) {
            key = engineSetting.getDefaultValue();
        }
        return key;
    }
    
    public boolean isGoogleMapApiOverQuotas() throws Exception {
        EngineSetting engineSetting = getSettingGoogleMapApiQuotaTimeStamp();
        String timestamp = null;
        if (engineSetting != null) {
            timestamp = engineSetting.getDefaultValue();
        }
        if(timestamp != null){
            return true;
        }
        return false;
    }
    
    public boolean isGoogleMapApiStillOverQuotas(final Date newDate) throws ParseException {
        EngineSetting engineSetting = getSettingGoogleMapApiQuotaTimeStamp();
        String timestamp = null;
        if (engineSetting != null) {
            timestamp = engineSetting.getDefaultValue();
        }
        if(timestamp != null){
            Date dateOverQuota = timestampPattern.parse(timestamp);
            if(newDate.getTime() > (dateOverQuota.getTime() + Constants.MILLISECONDS_IN_A_DAY.longValue())){
                return false;
            }
            return true;
        }
        return false;
    }
    
    public void flagSettingGoogleMapApiOverQuota() {
        EngineSetting engineSetting = getSettingGoogleMapApiQuotaTimeStamp();
        engineSetting.setDefaultValue(timestampPattern.format(new Date()));
        saveOrUpdateEngineSetting(engineSetting);
    }
    
    // DOCUMENT SETTINGS

    public EngineSetting getSettingDocumentFileRootPath() {
        return getEngineSettingByCode(ENGINE_SETTING_DOCUMENT_FILE_FOLDER_PATH);
    }

    public EngineSetting getSettingDocumentWebRootPath() {
        return getEngineSettingByCode(ENGINE_SETTING_DOCUMENT_FILE_WEB_PATH);
    }

    public EngineSetting getSettingDefaultOrderConfirmationTemplate() {
        return getEngineSettingByCode(ENGINE_SETTING_DEFAULT_ORDER_CONFIRMATION_TEMPLATE);
    }

    public EngineSetting getSettingDefaultShippingConfirmationTemplate() {
        return getEngineSettingByCode(ENGINE_SETTING_DEFAULT_SHIPPING_CONFIRMATION_TEMPLATE);
    }

    public EngineSetting getSettingDefaultInvoiceTemplate() {
        return getEngineSettingByCode(ENGINE_SETTING_DEFAULT_INVOICE_TEMPLATE);
    }

    // EMAIL SETTINGS

    public EngineSetting getSettingEmailFileMirroringActivated() {
        return getEngineSettingByCode(ENGINE_SETTING_EMAIL_FILE_MIRRORING_ACTIVATED);
    }

    public boolean getEmailFileMirroringActivated(String context) {
        EngineSetting engineSetting = getSettingEmailFileMirroringActivated();
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

    public EngineSetting getSettingEmailFileMirroringFolderPath() {
        return getEngineSettingByCode(ENGINE_SETTING_EMAIL_FILE_MIRRORING_FOLDER_PATH);
    }

    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getEmailFileMirroringFolderPath(String context) {
        EngineSetting engineSetting = getSettingEmailFileMirroringFolderPath();
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

    public EngineSetting getSettingEmailFileMirroringWebPath() {
        return getEngineSettingByCode(ENGINE_SETTING_EMAIL_FILE_MIRRORING_WEB_PATH);
    }

    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getEmailFileMirroringWebPath(String context) {
        EngineSetting engineSetting = getSettingEmailFileMirroringWebPath();
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

    public EngineSetting getSettingEmailFileMirroringExtension() {
        return getEngineSettingByCode(ENGINE_SETTING_EMAIL_FILE_MIRRORING_EXTENSION);
    }

    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getEmailFileMirroringExtension(String context) {
        EngineSetting engineSetting = getSettingEmailFileMirroringExtension();
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
    
    public EngineSetting getDefaultEmailAddress() {
        return getEngineSettingByCode(ENGINE_SETTING_DEFAULT_EMAIL_ADDRESS);
    }
    
    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getDefaultEmailAddress(String context) {
        EngineSetting engineSetting = getDefaultEmailAddress();
        String defaultEmailAddress = "";
        if (engineSetting != null) {
            defaultEmailAddress = engineSetting.getDefaultValue();
            EngineSettingValue engineSettingValue = engineSetting.getEngineSettingValue(context);
            if (engineSettingValue != null) {
                defaultEmailAddress = engineSettingValue.getValue();
            }
        }
        return defaultEmailAddress;
    }
    
    // ASSETS
    
    /**
     * 
     */
    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getRootAssetFilePath() throws Exception {
        EngineSetting engineSetting = getSettingAssetFileRootPath();
        String prefixPath = "";
        if (engineSetting != null) {
            prefixPath = engineSetting.getDefaultValue();
        }
        if (prefixPath.endsWith(File.separator)) {
            prefixPath = prefixPath.substring(0, prefixPath.length() - 1);
        }
        return handleFilePath(prefixPath);
    }

    /**
     * 
     */
    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getRootAssetWebPath() throws Exception {
        EngineSetting engineSetting = getSettingAssetWebRootPath();
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
    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getCatalogImageFilePath(String assetType) throws Exception {
        EngineSetting engineSetting = getSettingAssetCatalogFilePath();
        String prefixPath = "";
        if (engineSetting != null) {
            prefixPath = engineSetting.getDefaultValue();
        }
        String rootAssetFilePath = getRootAssetFilePath();
        if (rootAssetFilePath.endsWith("/") && prefixPath.startsWith("/")) {
            rootAssetFilePath = rootAssetFilePath.substring(0, rootAssetFilePath.length() - 1);
        }
        String catalogImageFilePath = rootAssetFilePath + prefixPath + File.separator + assetType + File.separator;
        if (catalogImageFilePath.endsWith(File.separator)) {
            catalogImageFilePath = catalogImageFilePath.substring(0, catalogImageFilePath.length() - 1);
        }
        return handleFilePath(catalogImageFilePath);
    }
    
    /**
     * 
     */
    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getCatalogImageWebPath(final Asset asset) throws Exception {
        EngineSetting engineSetting = getSettingAssetCatalogFilePath();
        String prefixPath = "";
        if (engineSetting != null) {
            prefixPath = engineSetting.getDefaultValue();
        }
        String catalogImageWebPath = getRootAssetWebPath() + prefixPath + "/" + asset.getType() + "/" + asset.getPath();
        return handleWebPath(catalogImageWebPath);
    }
    
    /**
     * 
     */
    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getProductMarketingImageFilePath(final String assetType) throws Exception {
        EngineSetting engineSetting = getSettingAssetProductMarketingFilePath();
        String prefixPath = "";
        if (engineSetting != null) {
            prefixPath = engineSetting.getDefaultValue();
        }
        String rootAssetFilePath = getRootAssetFilePath();
        if (rootAssetFilePath.endsWith(File.separator) && prefixPath.startsWith(File.separator)) {
            rootAssetFilePath = rootAssetFilePath.substring(0, rootAssetFilePath.length() - 1);
        }
        String productMarketingImageFilePath = rootAssetFilePath + prefixPath + File.separator + assetType + File.separator;
        return handleFilePath(productMarketingImageFilePath);
    }
    
    /**
     * 
     */
    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getProductMarketingImageFilePath(final ProductMarketing productMarketing, final String assetType) throws Exception {
        String productMarketingImageFilePath = getProductMarketingImageFilePath(assetType);
        if(productMarketing.getProductBrand() != null){
            productMarketingImageFilePath += productMarketing.getProductBrand().getCode() + File.separator;
        }
        productMarketingImageFilePath += productMarketing.getCode() + File.separator;
        return handleFilePath(productMarketingImageFilePath);
    }
    
    /**
     * 
     */
    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getProductMarketingImageFilePath(final ProductMarketing productMarketing, final String assetType, final String filePath) throws Exception {
        String productMarketingImageFilePath = getProductMarketingImageFilePath(productMarketing, assetType) + filePath;
        return handleFilePath(productMarketingImageFilePath);
    }
    
    /**
     * 
     */
    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getProductMarketingImageWebPath(final Asset asset) throws Exception {
        String productMarketingImageWebPath = getProductMarketingPrefixImageWebPath(asset) + asset.getPath();
        return handleWebPath(productMarketingImageWebPath);
    }
    
    /**
     * 
     */
    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getProductMarketingImageWebPath(final ProductMarketing productMarketing, final Asset asset) throws Exception {
        String productMarketingImageWebPath = getProductMarketingPrefixImageWebPath(asset);
        if (!productMarketingImageWebPath.endsWith("/")) {
            productMarketingImageWebPath += "/";
        }
        if(productMarketing.getProductBrand() != null){
            productMarketingImageWebPath += productMarketing.getProductBrand().getCode() + "/";
        }
        productMarketingImageWebPath += productMarketing.getCode() + "/" + asset.getPath();
        return handleWebPath(productMarketingImageWebPath);
    }
    
    
    protected String getProductMarketingPrefixImageWebPath(final Asset asset) throws Exception{
        EngineSetting engineSetting = getSettingAssetProductMarketingFilePath();
        String prefixPath = "";
        if (engineSetting != null) {
            prefixPath = engineSetting.getDefaultValue();
        }
        return getRootAssetWebPath() + prefixPath + "/"+ asset.getType() + "/";
    }

    /**
     * 
     */
    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getProductSkuImageFilePath(String assetType) throws Exception {
        EngineSetting engineSetting = getSettingAssetProductSkuFilePath();
        String prefixPath = "";
        if (engineSetting != null) {
            prefixPath = engineSetting.getDefaultValue();
        }
        String rootAssetFilePath = getRootAssetFilePath();
        if (rootAssetFilePath.endsWith(File.separator) && prefixPath.startsWith(File.separator)) {
            rootAssetFilePath = rootAssetFilePath.substring(0, rootAssetFilePath.length() - 1);
        }
        String productSkuImageFilePath = rootAssetFilePath + prefixPath + File.separator + assetType + File.separator;
        return handleFilePath(productSkuImageFilePath);
    }
    
    /**
     * 
     */
    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getProductSkuImageFilePath(final ProductSku productSku, final String assetType) throws Exception {
        String productMarketingImageFilePath = getProductSkuImageFilePath(assetType);
        if(productSku.getProductBrand() != null){
            productMarketingImageFilePath += productSku.getProductBrand().getCode() + File.separator;
        }
        productMarketingImageFilePath += productSku.getCode() + File.separator;
        return handleFilePath(productMarketingImageFilePath);
    }
    
    /**
     * 
     */
    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getProductSkuImageFilePath(final ProductSku productSku, final String assetType, final String filePath) throws Exception {
        String productMarketingImageFilePath = getProductSkuImageFilePath(productSku, assetType) + filePath;
        return handleFilePath(productMarketingImageFilePath);
    }
    
    /**
     * 
     */
    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getProductSkuImageWebPath(final Asset asset) throws Exception {
        String productSkuImageWebPath = getProductSkuPrefixImageWebPath(asset) + asset.getPath();
        return handleWebPath(productSkuImageWebPath);
    }

    /**
     * 
     */
    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getProductSkuImageWebPath(final String productSkuCode, final Asset asset) throws Exception {
        ProductSku productSkuByCode = productService.getProductSkuByCode(productSkuCode);
        return getProductSkuImageWebPath(productSkuByCode, asset);
    }
    
    /**
     * 
     */
    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getProductSkuImageWebPath(final ProductSku productSku, final Asset asset) throws Exception {
        String productSkuImageWebPath = getProductSkuPrefixImageWebPath(asset);
        if (!productSkuImageWebPath.endsWith("/")) {
            productSkuImageWebPath += "/";
        }
        Hibernate.initialize(productSku.getProductBrand());
        if(productSku.getProductBrand() != null){
            productSkuImageWebPath += productSku.getProductBrand().getCode() + "/";
        }
        productSkuImageWebPath += productSku.getCode() + "/" + asset.getPath();
        return handleWebPath(productSkuImageWebPath);
    }
    
    protected String getProductSkuPrefixImageWebPath(final Asset asset) throws Exception{
        EngineSetting engineSetting = getSettingAssetProductSkuFilePath();
        String prefixPath = "";
        if (engineSetting != null) {
            prefixPath = engineSetting.getDefaultValue();
        }
        return getRootAssetWebPath() + prefixPath + "/"+ asset.getType() + "/";
    }
    
    /**
     * 
     */
    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getProductBrandImageFilePath(String assetType) throws Exception {
        EngineSetting engineSetting = getSettingAssetProductBrandFilePath();
        String prefixPath = "";
        if (engineSetting != null) {
            prefixPath = engineSetting.getDefaultValue();
        }
        String rootAssetFilePath = getRootAssetFilePath();
        if (rootAssetFilePath.endsWith("/") && prefixPath.startsWith("/")) {
            rootAssetFilePath = rootAssetFilePath.substring(0, rootAssetFilePath.length() - 1);
        }
        String productBrandImageFilePath = rootAssetFilePath + prefixPath + File.separator + assetType + File.separator;
        if (productBrandImageFilePath.endsWith(File.separator)) {
            productBrandImageFilePath = productBrandImageFilePath.substring(0, productBrandImageFilePath.length() - 1);
        }
        return handleFilePath(productBrandImageFilePath);
    }
    
    /**
     * 
     */
    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getProductBrandImageWebPath(final Asset asset) throws Exception {
        EngineSetting engineSetting = getSettingAssetProductBrandFilePath();
        String prefixPath = "";
        if (engineSetting != null) {
            prefixPath = engineSetting.getDefaultValue();
        }
        String productBrandImageWebPath = getRootAssetWebPath() + prefixPath + "/" + asset.getType() + "/" + asset.getPath();
        return handleWebPath(productBrandImageWebPath);
    }
    
    /**
     * 
     */
    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getRetailerOrStoreImageFilePath(String assetType) throws Exception {
        EngineSetting engineSetting = getSettingAssetRetailerAndStoreFilePath();
        String prefixPath = "";
        if (engineSetting != null) {
            prefixPath = engineSetting.getDefaultValue();
        }
        String rootAssetFilePath = getRootAssetFilePath();
        if (rootAssetFilePath.endsWith("/") && prefixPath.startsWith("/")) {
            rootAssetFilePath = rootAssetFilePath.substring(0, rootAssetFilePath.length() - 1);
        }
        String retailerImageFilePath = rootAssetFilePath + prefixPath + File.separator + assetType + File.separator;
        if (retailerImageFilePath.endsWith(File.separator)) {
            retailerImageFilePath = retailerImageFilePath.substring(0, retailerImageFilePath.length() - 1);
        }
        return handleFilePath(retailerImageFilePath);
    }
    
    /**
     * 
     */
    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getRetailerOrStoreImageWebPath(final Asset asset) throws Exception {
        EngineSetting engineSetting = getSettingAssetRetailerAndStoreFilePath();
        String prefixPath = "";
        if (engineSetting != null) {
            prefixPath = engineSetting.getDefaultValue();
        }
        String retailerImageWebPath = getRootAssetWebPath() + prefixPath + "/" + asset.getScopePathValue() + "/" + asset.getType() + "/" + asset.getPath();
        return handleWebPath(retailerImageWebPath);
    }
    
    // CMS
    
    public EngineSetting getSettingAssetCmsContentFilePath() {
        return getEngineSettingByCode(ENGINE_SETTING_CODE_ASSET_CMS_CONTENT_FILE_PATH);
    }
    
    /**
     * 
     */
    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getCmsContentImageFilePath(MarketArea marketArea, CmsContent cmsContent, CmsContentBlock cmsContentBlock, CmsContentAsset asset) throws Exception {
        String assetFileRootPath = getSettingAssetFileRootPath().getDefaultValue();
        assetFileRootPath.replaceAll("\\\\", "/");
        if(assetFileRootPath.endsWith("/")){
            assetFileRootPath = assetFileRootPath.substring(0, assetFileRootPath.length() - 1);
        }
        String assetCmsContentFilePath = getSettingAssetCmsContentFilePath().getDefaultValue();
        assetCmsContentFilePath.replaceAll("\\\\", "/");
        if(assetCmsContentFilePath.endsWith("/")){
            assetCmsContentFilePath = assetCmsContentFilePath.substring(0, assetCmsContentFilePath.length() - 1);
        }
        if(!assetCmsContentFilePath.startsWith("/")){
            assetCmsContentFilePath = "/" + assetCmsContentFilePath;
        }
        
        String absoluteFolderPath = assetFileRootPath + assetCmsContentFilePath + "/" + marketArea.getName().toLowerCase() + "/" + cmsContent.getType().toLowerCase() + "/"  + cmsContent.getCode().toLowerCase();
        if(cmsContentBlock != null){
            if(cmsContentBlock.getCmsContentBlock() != null){
                absoluteFolderPath +=  "/" + cmsContentBlock.getCmsContentBlock().getType() + "/" + cmsContentBlock.getType();
            } else {
                absoluteFolderPath +=  "/" + cmsContentBlock.getType();
            }
        }
        absoluteFolderPath = absoluteFolderPath + "/" + asset.getType().toLowerCase() + "/";
        absoluteFolderPath = absoluteFolderPath.replace("_", "-").replace(" ", "-");
        return handleFilePath(absoluteFolderPath);
    }
    
    /**
     * 
     */
    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getCmsContentImageWebPath(final AbstractCmsEntity cmsContent, final CmsContentAsset asset) throws Exception {
        EngineSetting engineSetting = getSettingAssetCmsContentFilePath();
        String prefixPath = "";
        if (engineSetting != null) {
            prefixPath = engineSetting.getDefaultValue();
        }
        String cmsContentImageWebPath = getRootAssetWebPath() + prefixPath + "/" + cmsContent.getMarketArea().getName().toLowerCase() + "/" + handleFilePath(cmsContent.getType()) + "/";
//        if("HOME".equals(cmsContent.getType()) || "MENU".equals(cmsContent.getType()) || "ARTICLE".equals(cmsContent.getType()) || "PAGE".equals(cmsContent.getType())){
            cmsContentImageWebPath += handleFilePath(cmsContent.getCode()) + "/" + handleFilePath(asset.getType()) + "/" + asset.getPath();
//        } else {
//            cmsContentImageWebPath += handleFilePath(asset.getScopePathValue()) + "/" + handleFilePath(asset.getType()) + "/" + asset.getPath();
//        }
        if (cmsContentImageWebPath.endsWith("/")) {
            cmsContentImageWebPath = cmsContentImageWebPath.substring(0, cmsContentImageWebPath.length() - 1);
        }
        return handleWebPath(cmsContentImageWebPath);
    }
    
    /**
     * 
     */
    @CacheMethodInformation(cacheName="web_cache_engine_setting_value", cacheType=CacheType.CACHE_STRING)
    public String getCmsContentImageWebPath(final AbstractCmsEntity cmsContent, final CmsContentBlock cmsContentBlock, final CmsContentAsset asset) throws Exception {
        EngineSetting engineSetting = getSettingAssetCmsContentFilePath();
        String prefixPath = "";
        if (engineSetting != null) {
            prefixPath = engineSetting.getDefaultValue();
        }
        String cmsContentImageWebPath = getRootAssetWebPath() + prefixPath + "/" + cmsContent.getMarketArea().getName().toLowerCase() + "/" + handleFilePath(cmsContent.getType()) + "/" + handleFilePath(cmsContent.getCode()) + "/";
        if(cmsContentBlock != null){
            if(cmsContentBlock.getCmsContentBlock() != null){
                cmsContentImageWebPath += cmsContentBlock.getCmsContentBlock().getType() + "/" + cmsContentBlock.getType() + "/" + handleFilePath(asset.getType()) + "/" + asset.getPath();
            } else {
                cmsContentImageWebPath += cmsContentBlock.getType() + "/" + handleFilePath(asset.getType()) + "/" + asset.getPath();
            }
        } else {
            cmsContentImageWebPath += handleFilePath(asset.getType()) + "/" + asset.getPath();
        }
        if (cmsContentImageWebPath.endsWith("/")) {
            cmsContentImageWebPath = cmsContentImageWebPath.substring(0, cmsContentImageWebPath.length() - 1);
        }
        return handleWebPath(cmsContentImageWebPath);
    }
    
    protected String handleWebPath(String path){
        if(StringUtils.isNotEmpty(path)){
            return path.replace("_", "-").toLowerCase();
        }
        return path;
    }
    
    protected String handleFilePath(String path){
        String newFilePath = path;
        if(StringUtils.isNotEmpty(newFilePath)){
            newFilePath = newFilePath.replace("_", "-").toLowerCase();
            newFilePath = newFilePath.replace("&", "-").toLowerCase();
            newFilePath = newFilePath.replace("$", "-").toLowerCase();
            newFilePath = newFilePath.replace("%", "-").toLowerCase();
            newFilePath = CoreUtil.cleanDash(newFilePath);
            return newFilePath.toLowerCase();
        }
        return newFilePath;
    }
    
}