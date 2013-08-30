package fr.hoteia.qalingo.web.mvc.controller.oauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.domain.EngineSetting;
import fr.hoteia.qalingo.core.domain.EngineSettingValue;
import fr.hoteia.qalingo.core.domain.enumtype.FoUrls;
import fr.hoteia.qalingo.core.domain.enumtype.OAuthType;
import fr.hoteia.qalingo.core.service.pojo.RequestData;

/**
 * 
 */
@Controller("connectTwitterController")
public class ConnectTwitterController extends AbstractOAuthFrontofficeController {

	protected final Logger LOG = LoggerFactory.getLogger(getClass());

	@RequestMapping("/connect-oauth-twitter.html*")
	public ModelAndView connectTwitter(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final RequestData requestData = requestUtil.getRequestData(request);
		
		// SANITY CHECK
		if(!requestUtil.hasKnownCustomerLogged(request)){
			try {
			    // CLIENT ID
			    EngineSetting clientIdEngineSetting = engineSettingService.getOAuthAppKeyOrId();
			    EngineSettingValue clientIdEngineSettingValue = clientIdEngineSetting.getEngineSettingValue(OAuthType.TWITTER.name());
			    
			    // CLIENT SECRET
			    EngineSetting clientSecretEngineSetting = engineSettingService.getOAuthAppSecret();
			    EngineSettingValue clientSecretEngineSettingValue = clientSecretEngineSetting.getEngineSettingValue(OAuthType.TWITTER.name());
			    
			    // CLIENT PERMISSIONS
			    EngineSetting permissionsEngineSetting = engineSettingService.getOAuthAppPermissions();
			    EngineSettingValue permissionsEngineSettingValue = permissionsEngineSetting.getEngineSettingValue(OAuthType.TWITTER.name());
			    
			    if(clientIdEngineSettingValue != null
			    		&& clientSecretEngineSetting != null
			    		&& permissionsEngineSettingValue != null){
					final String clientId = clientIdEngineSettingValue.getValue();
					final String clientSecret = clientSecretEngineSettingValue.getValue();
					
					final String twitterCallBackURL = urlService.buildAbsoluteUrl(requestData, urlService.buildOAuthCallBackUrl(requestData, OAuthType.TWITTER.getPropertyKey().toLowerCase()));

				    OAuthService service = new ServiceBuilder()
                    .provider(TwitterApi.class)
                    .apiKey(clientId)
                    .apiSecret(clientSecret)
                    .callback(twitterCallBackURL)
                    .build();
					
				    Token requestToken = service.getRequestToken();
				    request.getSession().setAttribute(TWITTER_OAUTH_REQUEST_TOKEN, requestToken);
				    
					// Obtain the Authorization URL
					String authorizationUrl = service.getAuthorizationUrl(requestToken);

					response.sendRedirect(authorizationUrl);
			    }

			} catch (Exception e) {
				LOG.error("Connect With " + OAuthType.TWITTER.name() + " failed!");
			}
		}

		// DEFAULT FALLBACK VALUE
		if(!response.isCommitted()){
			response.sendRedirect(urlService.generateUrl(FoUrls.LOGIN, requestData));
		}
		
		return null;
	}
	
}