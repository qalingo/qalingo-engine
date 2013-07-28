package fr.hoteia.qalingo.web.mvc.controller.oauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	protected final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/callback-facebook.html*")
	public ModelAndView callBackFacebook(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		try {
			String error = request.getParameter("error");
			if(StringUtils.isNotEmpty(error)){
				String errorCode = request.getParameter("error_code");
				String errorDescription = request.getParameter("error_description");
				String errorReason = request.getParameter("error_reason");
				LOG.warn("Facebook connect fail! errorCode: " + errorCode + ", errorReason: " + errorReason+ ", errorDescription: " + errorDescription);
				
				// REIDRECT page login fail
				
			} else {
				// STEP 1
				String code = request.getParameter("code");
				if(StringUtils.isNotEmpty(code)){
					// save "code"
					String facebookCallBackURL = "http://fo-marketplace.dev.opentailor.com/sc/callback-facebook.html";
					String clientSecret = "8043b0a7dd05e56d1ff87ed28f0b4432";
					String clientId = "405673062885284";
					String accessTokenUrl = "https://graph.facebook.com/oauth/access_token?client_id=" + clientId + "&redirect_uri=" + facebookCallBackURL + "&client_secret=" + clientSecret + "&code=" + code;
					
					response.sendRedirect(accessTokenUrl);
				}

				// STEP 2
				String accessToken = request.getParameter("access_token");
				if(StringUtils.isNotEmpty(accessToken)){
					String customerInformationUrl = "https://graph.facebook.com/me?access_token=" + accessToken;
					response.sendRedirect(customerInformationUrl);
				}

				// STEP 3
				String userId = request.getParameter("id");
				if(StringUtils.isNotEmpty(userId)){
				
					final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
					response.sendRedirect(urlService.buildCustomerDetailsUrl(request, currentMarketArea));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}