package fr.hoteia.qalingo.web.mvc.controller.oauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import fr.hoteia.qalingo.core.domain.EngineSetting;
import fr.hoteia.qalingo.core.domain.EngineSettingValue;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.enumtype.FoUrls;
import fr.hoteia.qalingo.core.domain.enumtype.OAuthType;
import fr.hoteia.qalingo.web.mvc.controller.AbstractFrontofficeQalingoController;

/**
 * 
 */
@Controller("connectFacebookController")
public class ConnectFacebookController extends AbstractFrontofficeQalingoController {

	protected final Logger LOG = LoggerFactory.getLogger(getClass());

	@RequestMapping("/connect-oauth-facebook.html*")
	public ModelAndView connectFacebook(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		
		// SANITY CHECK
		if(!requestUtil.hasKnownCustomerLogged(request)){
			try {
			    // CLIENT ID
			    EngineSetting clientIdEngineSetting = engineSettingService.geOAuthAppKeyOrId();
			    EngineSettingValue clientIdEngineSettingValue = clientIdEngineSetting.getEngineSettingValue(OAuthType.FACEBOOK.name());
			    
			    // CLIENT SECRET
			    EngineSetting clientSecretEngineSetting = engineSettingService.geOAuthAppSecret();
			    EngineSettingValue clientSecretEngineSettingValue = clientSecretEngineSetting.getEngineSettingValue(OAuthType.FACEBOOK.name());
			    
			    // CLIENT PERMISSIONS
			    EngineSetting permissionsEngineSetting = engineSettingService.geOAuthAppPermissions();
			    EngineSettingValue permissionsEngineSettingValue = permissionsEngineSetting.getEngineSettingValue(OAuthType.FACEBOOK.name());
			    
			    if(clientIdEngineSettingValue != null
			    		&& clientSecretEngineSetting != null
			    		&& permissionsEngineSettingValue != null){
			    	final String contextValue = requestUtil.getCurrentContextNameValue(request);
					final String clientId = clientIdEngineSettingValue.getValue();
					final String clientSecret = clientSecretEngineSettingValue.getValue();
					final String permissions = permissionsEngineSettingValue.getValue();

					Facebook facebook = new FacebookFactory().getInstance();
					facebook.setOAuthAppId(clientId, clientSecret);
					facebook.setOAuthPermissions(permissions);
					String facebookCallBackURL = urlService.buildAbsoluteUrl( currentMarketArea, contextValue, 
													urlService.buildOAuthCallBackUrl( currentMarketArea, OAuthType.FACEBOOK.getPropertyKey().toLowerCase()));
					
					response.sendRedirect(facebook.getOAuthAuthorizationURL(facebookCallBackURL));
			    }

			} catch (Exception e) {
				LOG.error("Connect With " + OAuthType.FACEBOOK.name() + " failed!");
			}
		}

		// DEFAULT FALLBACK VALUE
		if(!response.isCommitted()){
			response.sendRedirect(urlService.generateUrl(FoUrls.LOGIN, requestUtil.getRequestData(request)));
		}
		
		return null;
	}
	
}