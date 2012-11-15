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

	public String buildHomeUrl(HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/home.html";
	}
	
	public String buildLoginUrl(HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/login.html";
	}
	
	public String buildLogoutUrl(HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/logout-session.html";
	}
	
	public String buildUserDetailsUrl(final HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/user-details.html";
	}
	
	public String buildUserEditUrl(final HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/user-edit.html";
	}
	
	public String buildCatalogStatsUrl(HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/catalog-stats.html";
	}
	
	public String buildPromotionStatsUrl(HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/promotion-stats.html";
	}
	
	public String buildShippingStatsUrl(HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/shipping-stats.html";
	}
	
	public String buildOrderStatsUrl(HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/order-stats.html";
	}
	
	public String buildCustomerStatsUrl(HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/customer-stats.html";
	}
	
	public String buildReportingUrl(HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/reporting.html";
	}
	
	public String buildFaqUrl(HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/faq.html";
	}
	
	public String buildChangeLanguageUrl(final HttpServletRequest request, final Localization localization) {
		return getContextPrefixUrl(request) + "sc/change-language.html?" + Constants.REQUEST_PARAMETER_LOCALE_CODE + "=" + localization.getLocaleCode();
	}
	
	public String buildSpringSecurityCheckUrl(HttpServletRequest request) {
		return getContextPrefixUrl(request) + "j_spring_security_check";
	}
	
	public String buildForbiddenUrl(HttpServletRequest request) {
		return getContextPrefixUrl(request) + "sc/forbidden.html";
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
