/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.web.cache.util.impl;

import org.apache.commons.lang.StringUtils;

import fr.hoteia.qalingo.core.service.EngineSettingService;
import fr.hoteia.qalingo.core.web.cache.util.WebCacheHelper;
import fr.hoteia.qalingo.core.web.cache.util.WebElementType;

/**
 * 
 */
public class WebCacheHelperImpl extends CommonCacheHelperImpl implements WebCacheHelper {
	
	private EngineSettingService engineSettingService;
	
	/**
	 * @return the TTL value for an element.
	 */
	@Override
	public int getElementTimeToLive(final WebElementType elementType) {
		String elementTimeToLiveSetting = engineSettingService.getEngineSettingByCode(EngineSettingService.WEB_CACHE_ELEMENT_TIME_TO_LIVE).getDefaultValue();
		if(StringUtils.isNotEmpty(elementTimeToLiveSetting)){
			return Integer.parseInt(elementTimeToLiveSetting);
		}
		return 3600;
	}
	
	public void setEngineSettingService(EngineSettingService engineSettingService) {
		this.engineSettingService = engineSettingService;
	}

}
