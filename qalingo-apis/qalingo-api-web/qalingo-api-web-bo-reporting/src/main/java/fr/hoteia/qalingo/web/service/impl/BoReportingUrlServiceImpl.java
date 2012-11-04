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
import fr.hoteia.qalingo.web.service.BoReportingUrlService;

@Service("boReportingUrlService")
@Transactional
public class BoReportingUrlServiceImpl implements BoReportingUrlService {

	public String buildHomeUrl(HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/home.html";
	}
	
	public String buildLoginUrl(HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/login.html";
	}
	
	public String buildLogoutUrl(HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/logout-session.html";
	}
	
	public String buildUserDetailsUrl(HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/user-details.html";
	}
	
	public String buildUserListUrl(HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/users.html";
	}
	
	public String buildEngineSettingUrl(HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/engine-setting.html";
	}
	
	public String buildCacheUrl(HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/cache.html";
	}
	
	public String buildBatchUrl(HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/batch.html";
	}
	
	public String buildForbiddenUrl(HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/forbidden.html";
	}
	
	public String buildChangeLanguageUrl(final HttpServletRequest request, final Localization localization) {
		return getContextPrefixUrl(request) + "sc/change-language.html?" + Constants.REQUEST_PARAMETER_LOCALE_CODE + "=" + localization.getLocaleCode();
	}
	
	public String buildSpringSecurityCheckUrl(HttpServletRequest request) {
		return getContextPrefixUrl(request) + "j_spring_security_check";
	}
	
	public String buildForgottenPasswordUrl(HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/forgotten-password.html";
	}
	
	public String buildSearchUrl(HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/search.html";
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
