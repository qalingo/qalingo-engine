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
@Controller("connectTwitterController")
public class ConnectTwitterController extends AbstractFrontofficeQalingoController {

	@RequestMapping("/connect-twitter.html*")
	public ModelAndView connectTwitter(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		try {

//			Twitter twitter = new Twitter();
//			twitter.setOAuthConsumer("l7dPpmLH1jQZEbfXNitfQ", "ECMXH2UaoSBer09IH6dSul5oNyBxIah5fyiqjsJ4");
//
//		    RequestToken requestToken = twitter.getOAuthRequestToken();
//
//			getPortalManager().setObjectInCache("RequestToken", requestToken);
//
//			String urlCallBackTwitter = requestToken.getAuthorizationURL();
//			String[] oauth_token = urlCallBackTwitter.split("oauth_token");
//			String oauthToken = oauth_token[1].substring(1, oauth_token[1].length());
//			
//			getPortalManager().setParamInCache("oauthToken", oauthToken);
//
//		    // twitter not use language :(
//			// Language language = RequestUtil.getCurrentApplicationLanguage(getContext());

//			String url = response.encodeRedirectURL(urlCallBackTwitter);

//			response.sendRedirect(url);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
        return null;
	}

}
