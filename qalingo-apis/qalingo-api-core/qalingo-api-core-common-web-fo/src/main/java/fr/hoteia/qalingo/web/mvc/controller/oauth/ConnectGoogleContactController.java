package fr.hoteia.qalingo.web.mvc.controller.oauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.enumtype.OAuthType;
import fr.hoteia.qalingo.web.mvc.controller.AbstractFrontofficeQalingoController;

/**
 * 
 */
@Controller("connectGoogleContactController")
public class ConnectGoogleContactController extends AbstractFrontofficeQalingoController {

	protected final Logger LOG = LoggerFactory.getLogger(getClass());

	@RequestMapping("/connect-google-contact.html*")
	public ModelAndView connectGoogle(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		try {
//			String googleContactCallBackURL = ServerUtil.getRootPath(request, getPortalManager()) + Constants.BOOKOO_GOOGLE_CONTACT_CALLBACK_PATH;
//			
//			GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
//			oauthParameters.setOAuthSignatureMethod(Constants.GOOGLE_OAUTH_SIGNATURE_METHOD);
//			oauthParameters.setOAuthConsumerKey(Constants.BOOKOO_CONSUMER_KEY);
//			oauthParameters.setOAuthCallback(googleContactCallBackURL);
//			oauthParameters.setScope(Constants.GOOGLE_CONTACT_SCOPE);
//
//			OAuthRsaSha1Signer signer = new OAuthRsaSha1Signer(RsaKeyUtil.getPrivateKey());
//
//			GoogleOAuthHelper oauthHelper = new GoogleOAuthHelper(signer);
//			oauthHelper.getUnauthorizedRequestToken(oauthParameters);
//
//			String approvalPageUrl = oauthHelper.createUserAuthorizationUrl(oauthParameters);
//
//			ServerUtil.addHeaderToResponseForOAuthGoogle(response, Constants.BOOKOO_CONSUMER_KEY, googleContactCallBackURL);
//
//			Language language = RequestUtil.getCurrentApplicationLanguage(request, getContext());
//				
//			String url = response.encodeRedirectURL(approvalPageUrl + "&hd=default&hl=" + language.getCode().toLowerCase());
//			
//			response.sendRedirect(url);                

		} catch (Exception e) {
			LOG.error("Connect With " + OAuthType.GOOGLE_CONTACT.getPropertyKey() + " failed!");
			response.sendRedirect(urlService.buildLoginUrl(request, currentMarketArea));
		}
		
		return null;
	}
	
}