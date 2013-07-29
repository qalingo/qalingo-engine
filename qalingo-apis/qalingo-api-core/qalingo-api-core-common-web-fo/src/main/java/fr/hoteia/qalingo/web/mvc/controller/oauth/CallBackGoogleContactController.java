package fr.hoteia.qalingo.web.mvc.controller.oauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.web.mvc.controller.AbstractFrontofficeQalingoController;

/**
 * 
 */
@Controller("callBackGoogleContactController")
public class CallBackGoogleContactController extends AbstractFrontofficeQalingoController {

	protected final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/callback-google-contact.html*")
	public ModelAndView callBackGoogleContact(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
//		try {
//			GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
//			oauthParameters.setOAuthSignatureMethod(Constants.GOOGLE_OAUTH_SIGNATURE_METHOD);
//			oauthParameters.setOAuthConsumerKey(Constants.BOOKOO_CONSUMER_KEY);
//			
//			OAuthRsaSha1Signer signer = new OAuthRsaSha1Signer(RsaKeyUtil.getPrivateKey());
//			GoogleOAuthHelper oauthHelper = new GoogleOAuthHelper(signer);
//			
//			oauthHelper.getOAuthParametersFromCallback(request.getQueryString(), oauthParameters);
//			
//			String accessToken = oauthHelper.getAccessToken(oauthParameters);
//			
//			AuthToken authToken = new AuthToken();
//			authToken.setAuthPortal(Constants.AUTH_PORTAL_GOOGLE);
//			authToken.setAuthScope(Constants.GOOGLE_CONTACT_SCOPE);
//			authToken.setAuthToken(accessToken);
//			if(getUserManager() != null){
//				getUserManager().saveNewAuthToken(authToken);
//				
//				// A AJUSTER - GET ALL CONTACTS
//				GoogleManager googleManager = (GoogleManager) getContext().getBean("googleManager");
//				User currentUser = getUserManager().getCurrentUser();
//				googleManager.saveContactEmailsWillBeSend(currentUser);
//			} else {
//				System.out.println("getUserManager() IS NULL !!!!!!!!!!!!!");
//			}
//			
//			// AVOIR UN HISTORIQUE DES URLS EN CACHE ET REJOUER LA DERNIER
//			String url = ClientUtil.getHomeUrl();
//			response.sendRedirect(url);
//			
//		} catch (Exception e) {
//		LOG.error("Connect With " + OAuthType.FACEBOOK.getPropertyKey() + " failed!");
//		response.sendRedirect(urlService.buildLoginUrl(request, currentMarketArea));
//	}
		return null;
	}

}