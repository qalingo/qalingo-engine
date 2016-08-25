/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.controller.oauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.domain.enumtype.OAuthType;
import org.hoteia.qalingo.core.mapper.JsonMapper;
import org.hoteia.qalingo.core.web.resolver.RequestData;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.YahooApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("connectOAuthYahooController")
public class ConnectOAuthYahooController extends AbstractOAuthFrontofficeController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected JsonMapper jsonMapper;
    
	@RequestMapping("/connect-oauth-yahoo.html*")
	public ModelAndView connectYahoo(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final RequestData requestData = requestUtil.getRequestData(request);
		
		// SANITY CHECK
		if(!requestUtil.hasKnownCustomerLogged(request)){
			try {
			    // CLIENT ID
			    EngineSetting clientIdEngineSetting = engineSettingService.getSettingOAuthAppKeyOrId();
			    EngineSettingValue clientIdEngineSettingValue = clientIdEngineSetting.getEngineSettingValue(OAuthType.YAHOO.name());
			    
			    // CLIENT SECRET
			    EngineSetting clientSecretEngineSetting = engineSettingService.getSettingOAuthAppSecret();
			    EngineSettingValue clientSecretEngineSettingValue = clientSecretEngineSetting.getEngineSettingValue(OAuthType.YAHOO.name());
			    
			    if(clientIdEngineSettingValue != null
			    		&& clientSecretEngineSetting != null){
					final String clientId = clientIdEngineSettingValue.getValue();
					final String clientSecret = clientSecretEngineSettingValue.getValue();
					
				    final String yahooCallBackURL = urlService.buildAbsoluteUrl(requestData, urlService.buildOAuthCallBackUrl(requestData, OAuthType.YAHOO.getPropertyKey().toLowerCase()));

			        OAuthService service = new ServiceBuilder()
			        .provider(YahooApi.class)
			        .apiKey(clientId)
			        .apiSecret(clientSecret)
		            .callback(yahooCallBackURL)
		            .build();

			        Token requestToken = service.getRequestToken();
                    request.getSession().setAttribute(YAHOO_OAUTH_REQUEST_TOKEN, requestToken);
                    
                    // Obtain the Authorization URL
                    String authorizationUrl = service.getAuthorizationUrl(requestToken);

                    response.sendRedirect(authorizationUrl);
			    }

			} catch (Exception e) {
				logger.error("Connect With " + OAuthType.YAHOO.name() + " failed!");
			}
		}

		// DEFAULT FALLBACK VALUE
		if(!response.isCommitted()){
			response.sendRedirect(urlService.generateUrl(FoUrls.LOGIN, requestData));
		}
		
		return null;
	}
	
}