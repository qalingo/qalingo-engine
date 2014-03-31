package org.hoteia.qalingo.core.web.mvc.controller.oauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.domain.AttributeDefinition;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.CustomerAttribute;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.domain.enumtype.CustomerNetworkOrigin;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.domain.enumtype.OAuthType;
import org.hoteia.qalingo.core.mapper.JsonMapper;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.security.util.SecurityUtil;
import org.hoteia.qalingo.core.service.AttributeService;
import org.hoteia.tools.scribe.mapping.oauth.twitter.json.pojo.UserPojo;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * 
 */
@Controller("callBackTwitterController")
public class CallBackTwitterController extends AbstractOAuthFrontofficeController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected AttributeService attributeService;
	
	@Autowired
    protected SecurityUtil securityUtil;

    @Autowired
    protected JsonMapper jsonMapper;
    
	@RequestMapping("/callback-oauth-twitter.html*")
	public ModelAndView callBackTwitter(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final RequestData requestData = requestUtil.getRequestData(request);
		
		// SANITY CHECK
		if(!requestUtil.hasKnownCustomerLogged(request)){
			try {

			    // CLIENT ID
			    EngineSetting clientIdEngineSetting = engineSettingService.getOAuthAppKeyOrId();
			    EngineSettingValue clientIdEngineSettingValue = clientIdEngineSetting.getEngineSettingValue(OAuthType.TWITTER.name());
			    
			    // CLIENT SECRET
			    EngineSetting clientSecretEngineSetting = engineSettingService.getOAuthAppSecret();
			    EngineSettingValue clientSecretEngineSettingValue = clientSecretEngineSetting.getEngineSettingValue(OAuthType.TWITTER.name());
			    
			    // CLIENT PERMISSIONS
			    EngineSetting permissionsEngineSetting = engineSettingService.getOAuthAppPermissions();
			    EngineSettingValue permissionsEngineSettingValue = permissionsEngineSetting.getEngineSettingValue(OAuthType.TWITTER.name());
			    
			    if(clientIdEngineSettingValue != null
			    		&& clientSecretEngineSetting != null
			    		&& permissionsEngineSettingValue != null){
					final String clientId = clientIdEngineSettingValue.getValue();
					final String clientSecret = clientSecretEngineSettingValue.getValue();

					final String twitterCallBackURL = urlService.buildAbsoluteUrl(requestData, urlService.buildOAuthCallBackUrl(requestData, OAuthType.TWITTER.getPropertyKey().toLowerCase()));

				    OAuthService service = new ServiceBuilder()
                    .provider(TwitterApi.class)
                    .apiKey(clientId)
                    .apiSecret(clientSecret)
                    .callback(twitterCallBackURL)
                    .build();
				    
					final String code = request.getParameter(REQUEST_PARAM_OAUTH_VERIFIER);
					if(StringUtils.isNotEmpty(code)) {
						Verifier verifier = new Verifier(code);
						Token requestToken = (Token) request.getSession().getAttribute(TWITTER_OAUTH_REQUEST_TOKEN);
						
						Token accessToken = service.getAccessToken(requestToken, verifier);
						OAuthRequest oauthRequest = new OAuthRequest(Verb.GET, TWITTER_URL);
					    service.signRequest(accessToken, oauthRequest);
					    Response oauthResponse = oauthRequest.send();
					    int responseCode = oauthResponse.getCode();
					    String responseBody = oauthResponse.getBody();
					    
					    if(responseCode == 200){
					    	handleAuthenticationData(request, response, requestData, OAuthType.TWITTER, responseBody);
					    } else {
							logger.error("Callback With " + OAuthType.TWITTER.name() + " failed!");
					    }
					} else {
						logger.error("Callback With " + OAuthType.TWITTER.name() + " failed!");
					}
					
			    }
					
			} catch (Exception e) {
				logger.error("Callback With " + OAuthType.TWITTER.name() + " failed!");
			}
		}
		
		// DEFAULT FALLBACK VALUE
		if(!response.isCommitted()){
			response.sendRedirect(urlService.generateUrl(FoUrls.LOGIN, requestData));
		}

		return null;
	}
	
	protected void handleAuthenticationData(HttpServletRequest request, HttpServletResponse response, RequestData requestData, OAuthType type, String jsonData) throws Exception {
		UserPojo userPojo = null;
		try {
			userPojo = jsonMapper.getJsonMapper().readValue(jsonData, UserPojo.class);
		} catch (JsonGenerationException e) {
			logger.error(e.getMessage());
		} catch (JsonMappingException e) {
			logger.error(e.getMessage());
		}
		if(userPojo != null){
			// TWITTER DOESN'T WANT TO SHARE EMAIL -> WE DON'T EXPOSE THIS CONNECT OPTION - WITHOUT EMAIL WE WILL SAVE DUPLICATES WITH CORE CREATE ACCOUNT PROCESS 
			// AND WE DON'T WANT ASK TO THE NEW CUSTOMER TO CONFIRM HIS EMAIL BY A FORM
			final String email = ""; 

			final String firstName = userPojo.getName();
			final String lastName = userPojo.getName();
			final String locale = userPojo.getLang();
			Customer customer = customerService.getCustomerByLoginOrEmail(email);
			
			if(customer == null){
				// CREATE A NEW CUSTOMER
				customer = new Customer();
				setCommonCustomerInformation(request, customer);

				customer.setLogin(email);
				customer.setPassword(securityUtil.generateAndEncodePassword());
				customer.setEmail(email);
				customer.setFirstname(firstName);
				customer.setLastname(lastName);
				
				customer.setNetworkOrigin(CustomerNetworkOrigin.TWITTER);

				CustomerAttribute attribute = new CustomerAttribute();
				AttributeDefinition attributeDefinition = attributeService.getAttributeDefinitionByCode(CustomerAttribute.CUSTOMER_ATTRIBUTE_SCREENAME);
				attribute.setAttributeDefinition(attributeDefinition);
				String screenName = "";
				if(StringUtils.isNotEmpty(lastName)){
					if(StringUtils.isNotEmpty(lastName)){
						screenName = lastName;
						if(screenName.length() > 1){
							screenName = screenName.substring(0, 1);
						}
						if(!screenName.endsWith(".")){
							screenName = screenName + ". ";
						}
					}
				}
				screenName = screenName + firstName;
				attribute.setStringValue(screenName);
				customer.getAttributes().add(attribute);
				
				if(StringUtils.isNotEmpty(locale)){
					customer.setDefaultLocale(locale);
				}
				
				customerService.saveOrUpdateCustomer(customer);
			}

			// Redirect to the details page
			if(StringUtils.isNotEmpty(customer.getEmail())){
				
				// Login the new customer
				securityUtil.authenticationCustomer(request, customer);
				
				// Update the customer session
				requestUtil.updateCurrentCustomer(request, customer);

				response.sendRedirect(urlService.generateUrl(FoUrls.PERSONAL_DETAILS, requestData));
			}
			
		}
	}
	
}