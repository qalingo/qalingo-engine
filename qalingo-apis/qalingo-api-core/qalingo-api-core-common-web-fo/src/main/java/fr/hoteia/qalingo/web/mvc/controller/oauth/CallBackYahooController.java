package fr.hoteia.qalingo.web.mvc.controller.oauth;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.domain.EngineSetting;
import fr.hoteia.qalingo.core.domain.EngineSettingValue;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.enumtype.OAuthType;
import fr.hoteia.qalingo.core.service.oauth.OpenIdAuthentication;
import fr.hoteia.qalingo.core.service.oauth.OpenIdException;
import fr.hoteia.qalingo.core.service.oauth.OpenIdService;
import fr.hoteia.qalingo.core.service.oauth.Provider;
import fr.hoteia.qalingo.web.mvc.controller.AbstractFrontofficeQalingoController;

/**
 * 
 */
@Controller("callBackYahooController")
public class CallBackYahooController extends AbstractFrontofficeQalingoController {

	protected final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected OpenIdService openIdService;
	
	@RequestMapping("/callback-yahoo.html*")
	public ModelAndView callBackGoogleContact(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		try {
		    // CLIENT ID
		    EngineSetting clientIdEngineSetting = engineSettingService.geOAuthAppKeyOrId();
		    EngineSettingValue clientIdEngineSettingValue = clientIdEngineSetting.getEngineSettingValue(OAuthType.YAHOO.getPropertyKey());
		    
		    // CLIENT SECRET
		    EngineSetting clientSecretEngineSetting = engineSettingService.geOAuthAppSecret();
		    EngineSettingValue clientSecretEngineSettingValue = clientSecretEngineSetting.getEngineSettingValue(OAuthType.YAHOO.getPropertyKey());
		    
		    // CLIENT PERMISSIONS
		    EngineSetting permissionsEngineSetting = engineSettingService.geOAuthAppKeyOrId();
		    EngineSettingValue permissionsEngineSettingValue = permissionsEngineSetting.getEngineSettingValue(OAuthType.YAHOO.getPropertyKey());
		    
		    if(clientIdEngineSettingValue != null
		    		&& clientSecretEngineSetting != null
		    		&& permissionsEngineSettingValue != null){
				
				final String clientId = clientIdEngineSettingValue.getValue();
				final String clientSecret = clientSecretEngineSettingValue.getValue();
				
				// check sign on result from Google or Yahoo:
				checkNonce(request.getParameter("openid.response_nonce"));
				// get authentication:
				byte[] mac_key = (byte[]) request.getSession().getAttribute(Provider.ATTR_MAC);
				String alias = (String) request.getSession().getAttribute(Provider.ATTR_ALIAS);
				OpenIdAuthentication authentication = openIdService.getAuthentication(request, mac_key, alias);
				storeAuthenticationData(authentication, request.getSession(false));

		    	response.sendRedirect(urlService.buildLoginSuccesUrl(request, currentMarketArea));

		    } else {
		    	response.sendRedirect(urlService.buildLoginUrl(request, currentMarketArea));
		    }
				
		} catch (Exception e) {
			LOG.error("Callback With " + OAuthType.YAHOO.getPropertyKey() + " failed!");
			response.sendRedirect(urlService.buildLoginUrl(request, currentMarketArea));
		}
		return null;
	}
	
	void storeAuthenticationData(OpenIdAuthentication auth, HttpSession session) {
		//setting in session for front end use
		String email = auth.getEmail();
//		GYUser gyUser = new GYUser(auth.getIdentity(),auth.getEmail(),auth.getFullname(),auth.getFirstname(),auth.getLastname(),auth.getGender(),auth.getLanguage());
//		session.setAttribute("gyUser", gyUser);

		//store the user in your database if needed
	}
	   
    void checkNonce(String nonce) {
        // check response_nonce to prevent replay-attack:
        if (nonce==null || nonce.length()<20){
            throw new OpenIdException("Verify failed.");
        }
        // make sure the time of server is correct:
        long nonceTime = getNonceTime(nonce);
        long diff = Math.abs(System.currentTimeMillis() - nonceTime);
        if (diff > Constants.ONE_HOUR){
            throw new OpenIdException("Bad nonce time.");
        }
    }

    long getNonceTime(String nonce) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .parse(nonce.substring(0, 19) + "+0000")
                    .getTime();
        }
        catch(ParseException e) {
            throw new OpenIdException("Bad nonce time.");
        }
    }
    

}