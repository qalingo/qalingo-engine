package fr.hoteia.qalingo.web.mvc.controller.oauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.web.mvc.controller.AbstractFrontofficeQalingoController;

/**
 * 
 */
@Controller("callBackFacebookController")
public class CallBackFacebookController extends AbstractFrontofficeQalingoController {

	@RequestMapping("/callback-facebook.html*")
	public ModelAndView callBackFacebook(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		try {
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

			String inputToken = request.getParameter("input_token");
			String accessToken = request.getParameter("access_token");
			String expires = request.getParameter("expires");

			String error = request.getParameter("error");
			String errorCode = request.getParameter("error_code");
			String errorDescription = request.getParameter("error_description");
			String errorReason = request.getParameter("error_reason");
			
			final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
			final Market currentMarket = requestUtil.getCurrentMarket(request);
			final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
			final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
			final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
			
			response.sendRedirect(urlService.buildHomeUrl(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}