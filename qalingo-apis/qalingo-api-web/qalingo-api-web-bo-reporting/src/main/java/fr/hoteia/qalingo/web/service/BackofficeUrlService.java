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
	
	String buildUserEditUrl();
	
	String buildCatalogStatsUrl();
	
	String buildPromotionStatsUrl();
	
	String buildShippingStatsUrl();
	
	String buildOrderStatsUrl();
	
	String buildCustomerStatsUrl();
	
	String buildReportingUrl();
	
	String buildFaqUrl();
	
	String buildForbiddenUrl();
	
	String buildChangeLanguageUrl(Localization localization);
	
	String buildSpringSecurityCheckUrl();
	
	String buildForgottenPasswordUrl();
	
	String buildSearchUrl();
}
