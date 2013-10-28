/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.web.service;

import fr.hoteia.qalingo.core.domain.enumtype.BoUrls;
import fr.hoteia.qalingo.core.pojo.RequestData;

public interface BackofficeUrlService {

	String buildChangeLanguageUrl(RequestData requestData) throws Exception;
	
	String buildChangeContextUrl(RequestData requestData) throws Exception;

	String generateUrl(BoUrls url, RequestData requestData);

	String generateUrl(BoUrls url, RequestData requestData, Object... params);
	
	String buildSpringSecurityCheckUrl(RequestData requestData) throws Exception;
}