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
import org.hoteia.qalingo.core.domain.enumtype.OAuthType;
import org.hoteia.qalingo.core.service.openid.OpenIdAuthentication;
import org.hoteia.qalingo.core.service.openid.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("callBackOpenIdController")
public class CallBackOpenIdController extends AbstractOpenIdFrontofficeController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/callback-openid.html*")
	public ModelAndView callBackGoogleContact(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		// SANITY CHECK
		if(!requestUtil.hasKnownCustomerLogged(request)){
			try {
				// check sign on result from Google or Yahoo:
				checkNonce(request.getParameter("openid.response_nonce"));
				// get authentication:
				byte[] mac_key = (byte[]) request.getSession().getAttribute(Utils.ATTR_MAC);
				String alias = (String) request.getSession().getAttribute(Utils.ATTR_ALIAS);
				OpenIdAuthentication authentication = openIdService.getAuthentication(request, mac_key, alias);
				handleAuthenticationData(request, authentication);
		    	response.sendRedirect(urlService.generateUrl(FoUrls.PERSONAL_DETAILS, requestUtil.getRequestData(request)));

			} catch (Exception e) {
				logger.error("Callback With " + OAuthType.YAHOO.getPropertyKey() + " failed!");
			}
		}
		
		// DEFAULT FALLBACK VALUE
		if(!response.isCommitted()){
			response.sendRedirect(urlService.generateUrl(FoUrls.LOGIN, requestUtil.getRequestData(request)));
		}
		
		return null;
	}

}