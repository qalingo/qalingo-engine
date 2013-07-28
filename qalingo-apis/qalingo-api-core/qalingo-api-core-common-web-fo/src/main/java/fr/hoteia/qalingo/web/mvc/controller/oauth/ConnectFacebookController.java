package fr.hoteia.qalingo.web.mvc.controller.oauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.web.mvc.controller.AbstractFrontofficeQalingoController;

/**
 * 
 */
@Controller("connectFacebookController")
public class ConnectFacebookController extends AbstractFrontofficeQalingoController {

	@RequestMapping("/connect-facebook.html*")
	public ModelAndView connectFacebook(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		try {
			String facebookCallBackURL = "http://fo-marketplace.dev.opentailor.com/sc/callback-facebook.html";
			
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
			
			/*
				It also has the following optional parameters:
					- state. An arbitrary unique string created by your app to guard against Cross-site Request Forgery.
					- response_type. Determines whether the response data included when the redirect back to the app occurs is in URL parameters or fragments. See the Confirming Identity section to choose which type your app should use. This can be one of: •code. Response data is included as URL parameters and contains code parameter (an encrypted string unique to each login request). This is the default behaviour if this parameter is not specified. It's most useful when your server will be handling the token.
					- token. Response data is included as a URL fragment and contains an access token. Desktop apps must use this setting for response_type. This is most useful when the client will be handling the token.
					- code%20token. Response data is included as a URL fragment and contains both an access token and the code parameter.
	
					- scope. A comma separated list of Permissions to request from the person using your app.
			 */
			
			String url = "https://www.facebook.com/dialog/oauth?client_id=405673062885284&redirect_uri=" + facebookCallBackURL;
			
			response.sendRedirect(url);                

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}