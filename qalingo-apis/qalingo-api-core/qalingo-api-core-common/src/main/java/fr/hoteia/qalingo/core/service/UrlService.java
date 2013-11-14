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

import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.enumtype.FoUrls;
import fr.hoteia.qalingo.core.service.pojo.RequestData;

public interface UrlService {

	String buildCustomerDetailsUrl(RequestData requestData, String permalink) throws Exception;
	
	String buildAddThisUrl(String shareCode, String absoluteUrl) throws Exception;
	
	String buildChangeLanguageUrl(RequestData requestData) throws Exception;
	
	String buildChangeContextUrl(RequestData requestData) throws Exception;
	
	String buildOAuthConnectUrl(RequestData requestData, String socialNetworkCode) throws Exception;
	
	String buildOAuthCallBackUrl(RequestData requestData, String socialNetworkCode) throws Exception;

	String buildOpenIdConnectUrl(RequestData requestData, String socialNetworkCode) throws Exception;
	
	String buildOpenIdCallBackUrl(RequestData requestData) throws Exception;

	String buildAbsoluteUrl(RequestData requestData, String relativeUrl) throws Exception;

	String buildDomainePathUrl(RequestData requestData) throws Exception;
	
	String generateUrl(FoUrls url, RequestData requestData, Object... params);
	
	String buildSpringSecurityCheckUrl(RequestData requestData) throws Exception;

}