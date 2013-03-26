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
	
	public String buildUserEditUrl() {
		return "/sc/user-edit.html";
	}
	
	public String buildCatalogStatsUrl() {
		return "/sc/catalog-stats.html";
	}
	
	public String buildPromotionStatsUrl() {
		return "/sc/promotion-stats.html";
	}
	
	public String buildShippingStatsUrl() {
		return "/sc/shipping-stats.html";
	}
	
	public String buildOrderStatsUrl() {
		return "/sc/order-stats.html";
	}
	
	public String buildCustomerStatsUrl() {
		return "/sc/customer-stats.html";
	}
	
	public String buildReportingUrl() {
		return "/sc/reporting.html";
	}
	
	public String buildFaqUrl() {
		return "/sc/faq.html";
	}
	
	public String buildChangeLanguageUrl(final Localization localization) {
		return "/sc/change-language.html?" + Constants.REQUEST_PARAMETER_LOCALE_CODE + "=" + localization.getCode();
	}
	
	public String buildSpringSecurityCheckUrl() {
		return "/j_spring_security_check";
	}
	
	public String buildForbiddenUrl() {
		return "/sc/forbidden.html";
	}
	
	public String buildForgottenPasswordUrl() {
		return "/sc/forgotten-password.html";
	}
	
	public String buildSearchUrl() {
		return "/sc/search.html";
	}

//	protected String getContextPrefixUrl(final ) {
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
