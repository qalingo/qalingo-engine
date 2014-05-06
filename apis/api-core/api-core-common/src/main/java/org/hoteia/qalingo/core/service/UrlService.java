/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import java.util.Locale;

import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;

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

    String getSeoSegmentMain(Locale locale) throws Exception;

    String generateUrl(FoUrls url, RequestData requestData, Object... params);

    String generateUrl(String urlWithoutWildcard, boolean isSEO, RequestData requestData, Object... params);

    String buildSpringSecurityCheckUrl(RequestData requestData) throws Exception;

}