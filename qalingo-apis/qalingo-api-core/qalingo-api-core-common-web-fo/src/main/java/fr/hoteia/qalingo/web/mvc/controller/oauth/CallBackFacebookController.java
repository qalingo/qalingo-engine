package fr.hoteia.qalingo.web.mvc.controller.oauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.CustomerOAuth;
import fr.hoteia.qalingo.core.domain.EngineSetting;
import fr.hoteia.qalingo.core.domain.EngineSettingValue;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.enumtype.OAuthType;
import fr.hoteia.qalingo.web.mvc.controller.AbstractFrontofficeQalingoController;

/**
 * 
 */
@Controller("callBackFacebookController")
public class CallBackFacebookController extends AbstractFrontofficeQalingoController {

	protected final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/callback-facebook.html*")
	public ModelAndView callBackFacebook(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		try {
			String error = request.getParameter("error");
			if(StringUtils.isNotEmpty(error)){
				String errorCode = request.getParameter("error_code");
				String errorDescription = request.getParameter("error_description");
				String errorReason = request.getParameter("error_reason");
				LOG.warn("Facebook connect fail! errorCode: " + errorCode + ", errorReason: " + errorReason+ ", errorDescription: " + errorDescription);
				
				// REIDRECT page login fail
				
		    	response.sendRedirect(urlService.buildLoginUrl(request, currentMarketArea));
		    	
			} else {

			    // CLIENT ID
			    EngineSetting clientIdEngineSetting = engineSettingService.geOAuthAppKeyOrId();
			    EngineSettingValue clientIdEngineSettingValue = clientIdEngineSetting.getEngineSettingValue(OAuthType.FACEBOOK.getPropertyKey());
			    
			    // CLIENT SECRET
			    EngineSetting clientSecretEngineSetting = engineSettingService.geOAuthAppSecret();
			    EngineSettingValue clientSecretEngineSettingValue = clientSecretEngineSetting.getEngineSettingValue(OAuthType.FACEBOOK.getPropertyKey());
			    
			    // CLIENT PERMISSIONS
			    EngineSetting permissionsEngineSetting = engineSettingService.geOAuthAppKeyOrId();
			    EngineSettingValue permissionsEngineSettingValue = permissionsEngineSetting.getEngineSettingValue(OAuthType.FACEBOOK.getPropertyKey());
			    
			    if(clientIdEngineSettingValue != null
			    		&& clientSecretEngineSetting != null
			    		&& permissionsEngineSettingValue != null){
			    	
					final String clientId = clientIdEngineSettingValue.getValue();
					final String clientSecret = clientSecretEngineSettingValue.getValue();
					final String permissions = permissionsEngineSettingValue.getValue();

					Facebook facebook = new FacebookFactory().getInstance();
					facebook.setOAuthAppId(clientId, clientSecret);
					facebook.setOAuthPermissions(permissions);
					
					// CALLBACK STEP 1 : GET THE CODE AND ASK A TOKEN
					String code = request.getParameter("code");
					AccessToken accessToken = null;
				    String expires = null;
				    
					if(StringUtils.isNotEmpty(code)){
						accessToken = facebook.getOAuthAccessToken(code);
					}

					// CALLBACK STEP 2 : Use the accessToken to ask user information
					String email = null;
					if(accessToken != null
							&& StringUtils.isNotEmpty(accessToken.getToken())){
						
						email = facebook.getEmail();
						
						Customer customer = customerService.getCustomerByLoginOrEmail(email);
						if(customer == null){
							// CREATE AN NEW CUSTOMER
							customer = new Customer();
							customer.setEmail(email);
							customer.setFirstname(facebook.getMe().getFirstName());
							customer.setLastname(facebook.getMe().getLastName());
							
							CustomerOAuth customerOAuth = new CustomerOAuth();
							customerOAuth.setOauthToken(accessToken.getToken());
							customerOAuth.setType(OAuthType.FACEBOOK);
							customerOAuth.setUserId(facebook.getMe().getId());
							customer.getOauthAccesses().add(customerOAuth);
							
							customerService.saveOrUpdateCustomer(customer);
							
						} else {
							// CHECK AND UPDATE THE CUSTOMER/OAUTH
							
						}
						
						// LOGIN
					}

					// STEP 3
					if(StringUtils.isNotEmpty(email)){
						response.sendRedirect(urlService.buildLoginSuccesUrl(request, currentMarketArea));
					}
			    } else {
			    	response.sendRedirect(urlService.buildLoginUrl(request, currentMarketArea));
			    }
			}
			
		} catch (Exception e) {
			LOG.error("Callback With " + OAuthType.FACEBOOK.getPropertyKey() + " failed!");
			response.sendRedirect(urlService.buildLoginUrl(request, currentMarketArea));
		}
		return null;
	}

}