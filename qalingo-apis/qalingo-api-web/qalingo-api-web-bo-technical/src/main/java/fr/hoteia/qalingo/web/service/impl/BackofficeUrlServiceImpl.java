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

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.common.domain.Localization;
import fr.hoteia.qalingo.web.service.BackofficeUrlService;

@Service("backofficeUrlService")
@Transactional
public class BackofficeUrlServiceImpl implements BackofficeUrlService {

	public String buildHomeUrl(final HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/home.html";
	}
	
	public String buildLoginUrl(final HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/login.html";
	}
	
	public String buildLogoutUrl(final HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/logout-session.html";
	}
	
	public String buildUserDetailsUrl(final HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/user-details.html";
	}
	
	public String buildUserDetailsUrl(final HttpServletRequest request, final String userId) {
		return getContextPrefixUrl(request) + "sc/user-details.html?" + Constants.REQUEST_PARAM_USER_ID + "=" + userId;
	}
	
	public String buildUserEditUrl(final HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/user-edit.html";
	}
	
	public String buildUserEditUrl(final HttpServletRequest request, final String userId) {
		return getContextPrefixUrl(request) + "sc/user-edit.html?" + Constants.REQUEST_PARAM_USER_ID + "=" + userId;
	}
	
	public String buildUserListUrl(final HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/users.html";
	}
	
	public String buildEngineSettingListUrl(final HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/engine-settings.html";
	}
	
	public String buildCacheUrl(final HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/cache.html";
	}
	
	public String buildBatchUrl(final HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/batch.html";
	}
	
	public String buildBatchCustomerUrl(final HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/batch-customer.html";
	}
	
	public String buildBatchOrderUrl(final HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/batch-order.html";
	}
	
	public String buildBatchEmailUrl(final HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/batch-email.html";
	}
	
	public String buildBatchCmsUrl(final HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/batch-cms.html";
	}
	
	public String buildBatchStockUrl(final HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/batch-stock.html";
	}

	public String buildMonitoringUrl(final HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/monitoring.html";
	}
	
	public String buildFaqUrl(final HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/faq.html";
	}

	public String buildForbiddenUrl(final HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/forbidden.html";
	}
	
	public String buildChangeLanguageUrl(final HttpServletRequest request, final Localization localization) {
		return getContextPrefixUrl(request) + "sc/change-language.html?" + Constants.REQUEST_PARAMETER_LOCALE_CODE + "=" + localization.getLocaleCode();
	}
	
	public String buildSpringSecurityCheckUrl(final HttpServletRequest request) {
		return getContextPrefixUrl(request) + "j_spring_security_check";
	}
	
	public String buildForgottenPasswordUrl(final HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/forgotten-password.html";
	}
	
	public String buildSearchConfigUrl(final HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/search-config.html";
	}
	
	public String buildSearchUrl(final HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/search.html";
	}
	
	public String buildSearchEngineSettingUrl(final HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/search-engine-setting.html";
	}
	
	public String buildSearchUserUrl(final HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/search-user.html";
	}
	
	public String buildSearchBatchUrl(final HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/search-batch.html";
	}
	
	public String buildEngineSettingDetailsUrl(final HttpServletRequest request, final String engineSettingId) {
		return getContextPrefixUrl(request) + "sc/engine-setting-details.html?" + Constants.REQUEST_PARAM_ENGINE_SETTING_ID + "=" + engineSettingId;
	}
	
	public String buildEngineSettingValueEditUrl(final HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/engine-setting-value-edit.html";
	}

	public String buildEngineSettingValueEditUrl(final HttpServletRequest request, final String engineSettingValueId) {
		return getContextPrefixUrl(request) + "sc/engine-setting-value-edit.html?" + Constants.REQUEST_PARAM_ENGINE_SETTING_VALUE_ID + "=" + engineSettingValueId;
	}

	protected String getContextPrefixUrl(final HttpServletRequest request) {
		String contextPrefixUrl = "";
		if(request.getRequestURL().toString().contains("localhost")
				|| request.getRequestURL().toString().contains("127.0.0.1")) {
			contextPrefixUrl = contextPrefixUrl + request.getContextPath() + "/";
		} else {
			contextPrefixUrl = "/";
		}
		return contextPrefixUrl;
	}
}
