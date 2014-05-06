/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.controller.openid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.openid.Association;
import org.hoteia.qalingo.core.service.openid.Endpoint;
import org.hoteia.qalingo.core.service.openid.OpenProvider;
import org.hoteia.qalingo.core.service.openid.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("connectYahooController")
public class ConnectYahooController extends AbstractOpenIdFrontofficeController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping("/connect-openid-yahoo.html*")
	public ModelAndView connectGoogle(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final RequestData requestData = requestUtil.getRequestData(request);
		
		// SANITY CHECK
		if(!requestUtil.hasKnownCustomerLogged(request)){
			try {
				openIdService.setRealm(urlService.buildDomainePathUrl(requestData));
				String openIdCallBackURL = urlService.buildOpenIdCallBackUrl(requestData);
				openIdService.setReturnTo(urlService.buildAbsoluteUrl(requestData, openIdCallBackURL));
	        
				Endpoint endpoint = openIdService.lookupEndpoint(OpenProvider.YAHOO.getPropertyKey().toLowerCase());
	            Association association = openIdService.lookupAssociation(endpoint);
	            request.getSession().setAttribute(Utils.ATTR_MAC, association.getRawMacKey());
	            request.getSession().setAttribute(Utils.ATTR_ALIAS, endpoint.getAlias());
	            String url = openIdService.getAuthenticationUrl(endpoint, association);
	            response.sendRedirect(url);
					
			} catch (Exception e) {
				logger.error("Connect With " + OpenProvider.YAHOO.getPropertyKey() + " failed!");
			}
		}

		// DEFAULT FALLBACK VALUE
		if(!response.isCommitted()){
			response.sendRedirect(urlService.generateUrl(FoUrls.LOGIN, requestData));
		}
		
		return null;
	}
	
}