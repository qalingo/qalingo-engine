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
@Controller("callBackTwitterController")
public class CallBackTwitterController extends AbstractFrontofficeQalingoController {

	@RequestMapping("/callback-twitter.html*")
	public ModelAndView callBackTwitter(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
//		try {
//
//			Twitter twitter = new Twitter();
//			twitter.setOAuthConsumer("l7dPpmLH1jQZEbfXNitfQ", "ECMXH2UaoSBer09IH6dSul5oNyBxIah5fyiqjsJ4");
//
//			// twitter not use language :(
//			// Language language =
//			// RequestUtil.getCurrentApplicationLanguage(getContext());
//
//			RequestToken requestToken = (RequestToken) getPortalManager().getObjectInCache("RequestToken");
//			String lastOAuthToken = getPortalManager().getParamInCache("oauthToken");
//
//			if (requestToken != null) {
//
//				String currentOAuthToken = null;
//				Enumeration<String> paramNamesRequest = request.getParameterNames();
//				while (paramNamesRequest.hasMoreElements()) {
//					String name = (String) paramNamesRequest.nextElement();
//					if (name.equalsIgnoreCase("oauth_token")) {
//						currentOAuthToken = request.getParameter(name);
//					}
//				}
//
//				if (lastOAuthToken.equalsIgnoreCase(currentOAuthToken)) {
//					AccessToken accessToken = null;
//					try {
//						accessToken = requestToken.getAccessToken();
//					} catch (TwitterException te) {
//						if (401 == te.getStatusCode()) {
//							System.out.println("Unable to get the access token.");
//						} else {
//							te.printStackTrace();
//						}
//					}
//					if (accessToken != null) {
//
//						AuthToken authToken = new AuthToken();
//						authToken.setAuthPortal(Constants.AUTH_PORTAL_TWITTER);
//						authToken.setAuthScope(Constants.TWITTER_NONE_SCOPE);
//						authToken.setAuthToken(accessToken.getToken());
//						authToken.setAuthTokenSecret(accessToken.getTokenSecret());
//
////						AdditionalThirdPartyInformation twitterAccountUser = new AdditionalThirdPartyInformation();
////						twitterAccountUser.setUserId(new Integer(accessToken.getUserId()));
////						twitterAccountUser.setScreenName(accessToken.getScreenName());
////						getUserManager().saveNewAuthToken(authToken, twitterAccountUser);
//					}
//				}
//				// AVOIR UN HISTORIQUE DES URLS EN CACHE ET REJOUER LA DERNIER
//				String url = ClientUtil.getHomeUrl();
//
//				response.sendRedirect(url);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return null;
	}

}