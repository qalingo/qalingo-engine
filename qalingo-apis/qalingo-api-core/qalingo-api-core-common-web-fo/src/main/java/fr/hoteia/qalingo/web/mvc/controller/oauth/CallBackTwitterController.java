package fr.hoteia.qalingo.web.mvc.controller.oauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import fr.hoteia.qalingo.core.domain.EngineSetting;
import fr.hoteia.qalingo.core.domain.EngineSettingValue;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.enumtype.OAuthType;
import fr.hoteia.qalingo.web.mvc.controller.AbstractFrontofficeQalingoController;

/**
 * 
 */
@Controller("callBackTwitterController")
public class CallBackTwitterController extends AbstractFrontofficeQalingoController {

	protected final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/callback-twitter.html*")
	public ModelAndView callBackTwitter(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		try {

		    // CLIENT ID
		    EngineSetting clientIdEngineSetting = engineSettingService.geOAuthAppKeyOrId();
		    EngineSettingValue clientIdEngineSettingValue = clientIdEngineSetting.getEngineSettingValue(OAuthType.TWITTER.getPropertyKey());
		    
		    // CLIENT SECRET
		    EngineSetting clientSecretEngineSetting = engineSettingService.geOAuthAppSecret();
		    EngineSettingValue clientSecretEngineSettingValue = clientSecretEngineSetting.getEngineSettingValue(OAuthType.TWITTER.getPropertyKey());
		    
		    // CLIENT PERMISSIONS
		    EngineSetting permissionsEngineSetting = engineSettingService.geOAuthAppKeyOrId();
		    EngineSettingValue permissionsEngineSettingValue = permissionsEngineSetting.getEngineSettingValue(OAuthType.TWITTER.getPropertyKey());
		    
		    if(clientIdEngineSettingValue != null
		    		&& clientSecretEngineSetting != null
		    		&& permissionsEngineSettingValue != null){
				
				final String clientId = clientIdEngineSettingValue.getValue();
				final String clientSecret = clientSecretEngineSettingValue.getValue();
				
				Twitter twitter = TwitterFactory.getSingleton();
				twitter.setOAuthConsumer(clientId, clientSecret);
				
		    	response.sendRedirect(urlService.buildLoginSuccesUrl(request, currentMarketArea));

		    } else {
		    	response.sendRedirect(urlService.buildLoginUrl(request, currentMarketArea));
		    }
				
		} catch (Exception e) {
			LOG.error("Callback With " + OAuthType.TWITTER.getPropertyKey() + " failed!");
			response.sendRedirect(urlService.buildLoginUrl(request, currentMarketArea));
		}
		return null;
	}

}