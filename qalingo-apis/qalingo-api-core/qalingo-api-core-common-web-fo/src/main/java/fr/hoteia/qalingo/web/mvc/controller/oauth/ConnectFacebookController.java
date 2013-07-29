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
import fr.hoteia.qalingo.core.domain.enumtype.OAuthType;
import fr.hoteia.qalingo.web.mvc.controller.AbstractFrontofficeQalingoController;

/**
 * 
 */
@Controller("connectFacebookController")
public class ConnectFacebookController extends AbstractFrontofficeQalingoController {

	protected final Logger LOG = LoggerFactory.getLogger(getClass());

	@RequestMapping("/connect-facebook.html*")
	public ModelAndView connectFacebook(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		try {
		    // CLIENT ID
		    EngineSetting clientIdEngineSetting = engineSettingService.geOAuthAppKeyOrId();
		    EngineSettingValue clientIdEngineSettingValue = clientIdEngineSetting.getEngineSettingValue(OAuthType.FACEBOOK.getPropertyKey());
		    
		    // CLIENT SECRET
		    EngineSetting clientSecretEngineSetting = engineSettingService.geOAuthAppSecret();
		    EngineSettingValue clientSecretEngineSettingValue = clientSecretEngineSetting.getEngineSettingValue(OAuthType.FACEBOOK.getPropertyKey());
		    
		    // CLIENT PERMISSIONS
		    EngineSetting permissionsEngineSetting = engineSettingService.geOAuthAppKeyOrId();
		    EngineSettingValue permissionsEngineSettingValue = permissionsEngineSetting.getEngineSettingValue(OAuthType.FACEBOOK.getPropertyKey());
		    
		    if(clientIdEngineSettingValue != null
		    		&& clientSecretEngineSetting != null
		    		&& permissionsEngineSettingValue != null){

				final String clientId = clientIdEngineSettingValue.getValue();
				final String clientSecret = clientSecretEngineSettingValue.getValue();
				final String permissions = permissionsEngineSettingValue.getValue();

				Facebook facebook = new FacebookFactory().getInstance();
				facebook.setOAuthAppId(clientId, clientSecret);
				facebook.setOAuthPermissions(permissions);
				String facebookCallBackURL = urlService.buildOAuthCallBackUrl(request, currentMarketArea, OAuthType.FACEBOOK.getPropertyKey().toLowerCase());
				
				response.sendRedirect(facebook.getOAuthAuthorizationURL(facebookCallBackURL));
		    } else {
		    	response.sendRedirect(urlService.buildLoginUrl(request, currentMarketArea));
		    }

		} catch (Exception e) {
			LOG.error("Connect With " + OAuthType.FACEBOOK.getPropertyKey() + " failed!");
			response.sendRedirect(urlService.buildLoginUrl(request, currentMarketArea));
		}
		return null;
	}
	
}