/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.web.service.BackofficeUrlService;

@Service("backofficeUrlService")
@Transactional
public class BackofficeUrlServiceImpl implements BackofficeUrlService {

	public String buildHomeUrl() {
		return "/sc/home.html";
	}
	
	public String buildLoginUrl() {
		return "/sc/login.html";
	}
	
	public String buildLogoutUrl() {
		return "/sc/logout-session.html";
	}
	
	public String buildUserDetailsUrl() {
		return "/sc/user-details.html";
	}
	
	public String buildUserDetailsUrl(final String userId) {
		return "/sc/user-details.html?" + Constants.REQUEST_PARAM_USER_ID + "=" + userId;
	}
	
	public String buildUserEditUrl() {
		return "/sc/user-edit.html";
	}
	
	public String buildUserEditUrl(final String userId) {
		return "/sc/user-edit.html?" + Constants.REQUEST_PARAM_USER_ID + "=" + userId;
	}
	
	public String buildUserListUrl() {
		return "/sc/users.html";
	}
	
	public String buildReferenceDataListUrl() {
		return "/sc/reference-datas.html";
	}
	
	public String buildEngineSettingListUrl() {
		return "/sc/engine-settings.html";
	}
	
	public String buildCacheUrl() {
		return "/sc/cache.html";
	}
	
	public String buildBatchUrl() {
		return "/sc/batch.html";
	}
	
	public String buildBatchCustomerUrl() {
		return "/sc/batch-customer.html";
	}
	
	public String buildBatchOrderUrl() {
		return "/sc/batch-order.html";
	}
	
	public String buildBatchEmailUrl() {
		return "/sc/batch-email.html";
	}
	
	public String buildBatchCmsUrl() {
		return "/sc/batch-cms.html";
	}
	
	public String buildBatchStockUrl() {
		return "/sc/batch-stock.html";
	}

	public String buildMonitoringUrl() {
		return "/sc/monitoring.html";
	}
	
	public String buildFaqUrl() {
		return "/sc/faq.html";
	}

	public String buildForbiddenUrl() {
		return "/sc/forbidden.html";
	}
	
	public String buildChangeLanguageUrl(final Localization localization) {
		return "/sc/change-language.html?" + Constants.REQUEST_PARAMETER_LOCALE_CODE + "=" + localization.getCode();
	}
	
	public String buildSpringSecurityCheckUrl() {
		return "/j_spring_security_check";
	}
	
	public String buildForgottenPasswordUrl() {
		return "/sc/forgotten-password.html";
	}
	
	public String buildSearchConfigUrl() {
		return "/sc/search-config.html";
	}
	
	public String buildSearchUrl() {
		return "/sc/search.html";
	}
	
	public String buildSearchEngineSettingUrl() {
		return "/sc/search-engine-setting.html";
	}
	
	public String buildSearchUserUrl() {
		return "/sc/search-user.html";
	}
	
	public String buildSearchBatchUrl() {
		return "/sc/search-batch.html";
	}
	
	public String buildEngineSettingDetailsUrl(final String engineSettingId) {
		return "/sc/engine-setting-details.html?" + Constants.REQUEST_PARAM_ENGINE_SETTING_ID + "=" + engineSettingId;
	}
	
	public String buildEngineSettingValueEditUrl() {
		return "/sc/engine-setting-value-edit.html";
	}

	public String buildEngineSettingValueEditUrl(final String engineSettingValueId) {
		return "/sc/engine-setting-value-edit.html?" + Constants.REQUEST_PARAM_ENGINE_SETTING_VALUE_ID + "=" + engineSettingValueId;
	}

//	protected String getContextPrefixUrl() {
//		String contextPrefixUrl = "";
//		if(request.getRequestURL().toString().contains("localhost")
//				|| request.getRequestURL().toString().contains("127.0.0.1")) {
//			contextPrefixUrl = contextPrefixUrl + request.getContextPath() + "/";
//		} else {
//			contextPrefixUrl = "/";
//		}
//		return contextPrefixUrl;
//	}
}
