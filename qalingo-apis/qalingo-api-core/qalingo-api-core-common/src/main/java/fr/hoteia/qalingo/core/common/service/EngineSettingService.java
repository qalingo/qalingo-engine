/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.service;

import java.util.List;

import fr.hoteia.qalingo.core.common.domain.EngineSetting;
import fr.hoteia.qalingo.core.common.domain.EngineSettingValue;

public interface EngineSettingService {

	// TODO : enum or not ?
	public final static String ENGINE_SETTING_CODE_THEME_RESOURCE_PREFIX_PATH = "THEME_RESOURCE_PREFIX_PATH";
	public final static String ENGINE_SETTING_CODE_CATALOG_RESOURCE_PREFIX_PATH = "CATALOG_RESOURCE_PREFIX_PATH";
	public final static String WEB_CACHE_ELEMENT_TIME_TO_LIVE = "WEB_CACHE_ELEMENT_TIME_TO_LIVE";
		
	public final static String ENGINE_SETTING_CONTEXT_BO_BUSINESS  = "BO_BUSINESS";
	public final static String ENGINE_SETTING_CONTEXT_BO_REPORTING = "BO_REPORTING";
	public final static String ENGINE_SETTING_CONTEXT_BO_TECHNICAL = "BO_TECHNICAL";
	public final static String ENGINE_SETTING_CONTEXT_FO_MCOMMERCE = "FO_MCOMMERCE";
	public final static String ENGINE_SETTING_CONTEXT_FO_PREHOME   = "FO_PREHOME";

	public final static String ENGINE_SETTING_CODE_SPRING_BATCH_URL = "SPRING_BATCH_URL";
	public final static String ENGINE_SETTING_CONTEXT_CMS			= "CMS";
	public final static String ENGINE_SETTING_CONTEXT_CRM			= "CRM";
	public final static String ENGINE_SETTING_CONTEXT_ERP			= "ERP";
	public final static String ENGINE_SETTING_CONTEXT_NOTIFICATION	= "NOTIFICATION";

	public final static String ENGINE_SETTING_CODE_SOLR			= "SOLR";
	public final static String ENGINE_SETTING_CONTEXT_MASTER	= "MASTER";
	
	// Engine Setting
	EngineSetting getEngineSettingById(String id);

	EngineSetting getEngineSettingByCode(String code);

	EngineSetting getThemeResourcePrefixPath();

	EngineSetting getCatalogImageResourcePrefixPath();
	
	List<EngineSetting> findEngineSettings();

	void saveOrUpdateEngineSetting(EngineSetting engineSetting);
	
	void deleteEngineSetting(EngineSetting engineSetting);
	
	// Engine Setting Value
	EngineSettingValue getEngineSettingValueById(String id);

	void saveOrUpdateEngineSettingValue(EngineSettingValue engineSettingValue);

}
