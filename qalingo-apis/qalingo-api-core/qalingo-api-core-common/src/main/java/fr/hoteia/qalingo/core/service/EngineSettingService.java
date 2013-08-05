/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.service;

import java.util.List;

import fr.hoteia.qalingo.core.domain.EngineSetting;
import fr.hoteia.qalingo.core.domain.EngineSettingValue;

public interface EngineSettingService {

	// TODO : enum or not ?
	public final String ENGINE_SETTING_CODE_ASSET_FILE_ROOT_PATH				= "ASSET_FILE_ROOT_PATH";
	public final String ENGINE_SETTING_CODE_ASSET_WEB_ROOT_PATH					= "ASSET_WEB_ROOT_PATH";
	public final String ENGINE_SETTING_CODE_ASSET_CATALOG_FILE_PATH				= "ASSET_CATALOG_FILE_PATH";
	public final String ENGINE_SETTING_CODE_ASSET_PRODUCT_MARKETING_FILE_PATH	= "ASSET_PRODUCT_MARKETING_FILE_PATH";
	public final String ENGINE_SETTING_CODE_ASSET_PROPDUCT_SKU_FILE_PATH		= "ASSET_PROPDUCT_SKU_FILE_PATH";

	public final String ENGINE_SETTING_CODE_THEME_RESOURCE_PREFIX_PATH	= "THEME_RESOURCE_PREFIX_PATH";
	public final String WEB_CACHE_ELEMENT_TIME_TO_LIVE					= "WEB_CACHE_ELEMENT_TIME_TO_LIVE";

	public final String ENGINE_SETTING_ESCAPE_ACCENT_FROM_URL	= "ESCAPE_ACCENT_FROM_URL";

	public final String ENGINE_SETTING_CODE_SPRING_BATCH_URL	= "SPRING_BATCH_URL";
	public final String ENGINE_SETTING_CONTEXT_CMS				= "CMS";
	public final String ENGINE_SETTING_CONTEXT_CRM				= "CRM";
	public final String ENGINE_SETTING_CONTEXT_ERP				= "ERP";
	public final String ENGINE_SETTING_CONTEXT_NOTIFICATION		= "NOTIFICATION";

	public final String ENGINE_SETTING_CODE_SOLR		= "SOLR";
	public final String ENGINE_SETTING_CONTEXT_MASTER	= "MASTER";
	
	// COUNT ITEM BY PAGE - PAGE SIZE
	public final String ENGINE_SETTING_CODE_COUNT_ITEM_BY_PAGE					= "COUNT_ITEM_BY_PAGE";
	public final String ENGINE_SETTING_CONTEXT_BO_TECHNICAL_ENGINE_SETTING_LIST	= "BO_TECHNICAL_ENGINE_SETTING_LIST";
	public final String ENGINE_SETTING_CONTEXT_BO_TECHNICAL_USER_LIST			= "BO_TECHNICAL_USER_LIST";
	public final String ENGINE_SETTING_CONTEXT_BO_TECHNICAL_CACHE_LIST			= "BO_TECHNICAL_CACHE_LIST";
	public final String ENGINE_SETTING_CONTEXT_BO_TECHNICAL_BATCH_LIST			= "BO_TECHNICAL_BATCH_LIST";

	// OAUTH : FACEBOOK, TWITTER, GOOGLE, etc
	public final String ENGINE_SETTING_OAUTH_APP_KEY_OR_ID						= "OAUTH_APP_KEY_OR_ID";
	public final String ENGINE_SETTING_OAUTH_APP_SECRET							= "OAUTH_APP_SECRET";
	public final String ENGINE_SETTING_OAUTH_APP_PERMISSIONS					= "OAUTH_APP_PERMISSIONS";

	// MAX USER LOGIN SUCCES TO LOG
	public final String ENGINE_SETTING_MAX_USER_CONNECTION_LOG		= "MAX_USER_CONNECTION_LOG";
	public final String ENGINE_SETTING_MAX_CUSTOMER_CONNECTION_LOG	= "MAX_CUSTOMER_CONNECTION_LOG";

	// SCORE
	public final String ENGINE_SETTING_CONTEXT_STAR_SCORE_MAX		= "STAR_SCORE_MAX";
	public final String ENGINE_SETTING_CONTEXT_RETAILER_SCORE_MAX	= "RETAILER_QUALITY_OF_SERVICE";

	// Engine Setting
	
	EngineSetting getEngineSettingById(String id);

	EngineSetting getEngineSettingByCode(String code);

	List<EngineSetting> findEngineSettings();

	void saveOrUpdateEngineSetting(EngineSetting engineSetting);
	
	void deleteEngineSetting(EngineSetting engineSetting);
	
	// Engine Setting Value
	
	EngineSettingValue getEngineSettingValueById(String id);

	void saveOrUpdateEngineSettingValue(EngineSettingValue engineSettingValue);
	
	String getEngineSettingValueByCode(String engineSettingCode, String engineSettingValueCode);
	
	String getEngineSettingDefaultValueByCode(String engineSettingCode);

	// Common Engine Setting Value
	
	EngineSetting getAssetFileRootPath();

	EngineSetting getAssetWebRootPath();

	EngineSetting getAssetCatalogFilePath();

	EngineSetting getAssetProductMarketingFilePath();
	
	EngineSetting getAssetPoductSkuFilePath();
	
	EngineSetting getThemeResourcePrefixPath();

	EngineSetting withEscapeAccent();
	
	EngineSetting getRetailerMaxScoreValue();
	
	EngineSetting geOAuthAppKeyOrId();
	
	EngineSetting geOAuthAppSecret();
	
	EngineSetting geOAuthAppPermissions();
	
}