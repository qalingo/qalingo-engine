/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.service;

import fr.hoteia.qalingo.core.domain.Localization;

public interface BackofficeUrlService {

	String buildHomeUrl();
	
	String buildLoginUrl();
	
	String buildLogoutUrl();
	
	String buildUserDetailsUrl();

	String buildUserDetailsUrl(String userId);

	String buildUserEditUrl();
	
	String buildUserEditUrl(String userId);
	
	String buildUserListUrl();
	
	String buildReferenceDataListUrl();
	
	String buildEngineSettingListUrl();
	
	String buildEngineSettingValueEditUrl();

	String buildEngineSettingValueEditUrl(String engineSettingValueId);
	
	String buildCacheUrl();
	
	String buildBatchUrl();
	
	String buildBatchCustomerUrl();
	
	String buildBatchOrderUrl();
	
	String buildBatchEmailUrl();
	
	String buildBatchCmsUrl();
	
	String buildBatchStockUrl();
	
	String buildMonitoringUrl();
	
	String buildFaqUrl();
	
	String buildForbiddenUrl();
	
	String buildChangeLanguageUrl(Localization localization);
	
	String buildSpringSecurityCheckUrl();
	
	String buildForgottenPasswordUrl();
	
	String buildSearchConfigUrl();
	
	String buildSearchUrl();
	
	String buildSearchEngineSettingUrl();
	
	String buildSearchUserUrl();
	
	String buildSearchBatchUrl();
	
	String buildEngineSettingDetailsUrl(String engineSettingId);
}
