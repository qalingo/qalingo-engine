package fr.hoteia.qalingo.web.mvc.controller.oauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.domain.EngineSetting;
import fr.hoteia.qalingo.core.domain.EngineSettingValue;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.enumtype.OAuthType;
import fr.hoteia.qalingo.core.service.oauth.Association;
import fr.hoteia.qalingo.core.service.oauth.Endpoint;
import fr.hoteia.qalingo.core.service.oauth.OpenIdService;
import fr.hoteia.qalingo.core.service.oauth.Provider;
import fr.hoteia.qalingo.web.mvc.controller.AbstractFrontofficeQalingoController;

/**
 * 
 */
@Controller("connectYahooController")
public class ConnectYahooController extends AbstractFrontofficeQalingoController {

	protected final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	protected OpenIdService openIdService;
	
	@RequestMapping("/connect-yahoo.html*")
	public ModelAndView connectGoogle(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		try {
		    // CLIENT ID
//		    EngineSetting clientIdEngineSetting = engineSettingService.geOAuthAppKeyOrId();
//		    EngineSettingValue clientIdEngineSettingValue = clientIdEngineSetting.getEngineSettingValue(OAuthType.YAHOO.getPropertyKey());
//		    
//		    // CLIENT SECRET
//		    EngineSetting clientSecretEngineSetting = engineSettingService.geOAuthAppSecret();
//		    EngineSettingValue clientSecretEngineSettingValue = clientSecretEngineSetting.getEngineSettingValue(OAuthType.YAHOO.getPropertyKey());
//		    
//		    // CLIENT PERMISSIONS
//		    EngineSetting permissionsEngineSetting = engineSettingService.geOAuthAppKeyOrId();
//		    EngineSettingValue permissionsEngineSettingValue = permissionsEngineSetting.getEngineSettingValue(OAuthType.YAHOO.getPropertyKey());
//		    
//		    if(clientIdEngineSettingValue != null
//		    		&& clientSecretEngineSetting != null
//		    		&& permissionsEngineSettingValue != null){
				
//				final String clientId = clientIdEngineSettingValue.getValue();
//				final String clientSecret = clientSecretEngineSettingValue.getValue();

				openIdService.setRealm("localhost:21080/fo-marketplace");
				String yahooCallBackURL = urlService.buildOAuthCallBackUrl(request, currentMarketArea, OAuthType.FACEBOOK.getPropertyKey().toLowerCase());
				openIdService.setReturnTo(yahooCallBackURL);
	        
				Endpoint endpoint = openIdService.lookupEndpoint(OAuthType.YAHOO.getPropertyKey().toLowerCase());
	            Association association = openIdService.lookupAssociation(endpoint);
	            request.getSession().setAttribute(Provider.ATTR_MAC, association.getRawMacKey());
	            request.getSession().setAttribute(Provider.ATTR_ALIAS, endpoint.getAlias());
	            String url = openIdService.getAuthenticationUrl(endpoint, association);
	            response.sendRedirect(url);
				
//		    } else {
//		    	response.sendRedirect(urlService.buildLoginUrl(request, currentMarketArea));
//		    }
		    
		} catch (Exception e) {
			LOG.error("Connect With " + OAuthType.YAHOO.getPropertyKey() + " failed!");
			response.sendRedirect(urlService.buildLoginUrl(request, currentMarketArea));
		}
		return null;
	}
	
}