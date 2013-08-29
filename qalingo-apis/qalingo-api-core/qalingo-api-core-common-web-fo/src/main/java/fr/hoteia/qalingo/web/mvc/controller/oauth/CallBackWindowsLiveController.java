package fr.hoteia.qalingo.web.mvc.controller.oauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LiveApi;
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

import fr.hoteia.qalingo.core.domain.AttributeDefinition;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.CustomerAttribute;
import fr.hoteia.qalingo.core.domain.EngineSetting;
import fr.hoteia.qalingo.core.domain.EngineSettingValue;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.enumtype.CustomerNetworkOrigin;
import fr.hoteia.qalingo.core.domain.enumtype.FoUrls;
import fr.hoteia.qalingo.core.domain.enumtype.OAuthType;
import fr.hoteia.qalingo.core.security.util.SecurityUtil;
import fr.hoteia.qalingo.core.service.AttributeService;
import org.hoteia.tools.scribe.mapping.oauth.windowslive.json.pojo.UserPojo;

/**
 * 
 */
@Controller("callBackWindowsLiveController")
public class CallBackWindowsLiveController extends AbstractOAuthFrontofficeController {

	protected final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected AttributeService attributeService;
	
	@Autowired
    protected SecurityUtil securityUtil;
	
	@RequestMapping("/callback-oauth-windows-live.html*")
	public ModelAndView callBackWindowsLive(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		
		// SANITY CHECK
		if(!requestUtil.hasKnownCustomerLogged(request)){
			try {

			    // CLIENT ID
			    EngineSetting clientIdEngineSetting = engineSettingService.getOAuthAppKeyOrId();
			    EngineSettingValue clientIdEngineSettingValue = clientIdEngineSetting.getEngineSettingValue(OAuthType.WINDOWS_LIVE.name());
			    
			    // CLIENT SECRET
			    EngineSetting clientSecretEngineSetting = engineSettingService.getOAuthAppSecret();
			    EngineSettingValue clientSecretEngineSettingValue = clientSecretEngineSetting.getEngineSettingValue(OAuthType.WINDOWS_LIVE.name());
			    
			    // CLIENT PERMISSIONS
			    EngineSetting permissionsEngineSetting = engineSettingService.getOAuthAppPermissions();
			    EngineSettingValue permissionsEngineSettingValue = permissionsEngineSetting.getEngineSettingValue(OAuthType.WINDOWS_LIVE.name());
			    
			    if(clientIdEngineSettingValue != null
			    		&& clientSecretEngineSetting != null
			    		&& permissionsEngineSettingValue != null){
			    	final String contextValue = requestUtil.getCurrentContextNameValue(request);
					final String clientId = clientIdEngineSettingValue.getValue();
					final String clientSecret = clientSecretEngineSettingValue.getValue();
					final String permissions = permissionsEngineSettingValue.getValue();

					final String windowsLiveCallBackURL = urlService.buildAbsoluteUrl( currentMarketArea, contextValue, 
							urlService.buildOAuthCallBackUrl(requestUtil.getRequestData(request), OAuthType.WINDOWS_LIVE.getPropertyKey().toLowerCase()));

				    OAuthService service = new ServiceBuilder()
                    .provider(LiveApi.class)
                    .apiKey(clientId)
                    .apiSecret(clientSecret)
                    .scope(permissions)
                    .callback(windowsLiveCallBackURL)
                    .build();
				    
					final String code = request.getParameter("code");
					if(StringUtils.isNotEmpty(code)){
						Verifier verifier = new Verifier(code);
						Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
						OAuthRequest oauthRequest = new OAuthRequest(Verb.GET, LIVE_ME_URL);
					    service.signRequest(accessToken, oauthRequest);
					    Response oauthResponse = oauthRequest.send();
					    int responseCode = oauthResponse.getCode();
					    String responseBody = oauthResponse.getBody();
					    
					    if(responseCode == 200){
					    	handleAuthenticationData(request, response, currentMarketArea, OAuthType.WINDOWS_LIVE, responseBody);
					    } else {
							LOG.error("Callback With " + OAuthType.WINDOWS_LIVE.name() + " failed!");
					    }
					} else {
						LOG.error("Callback With " + OAuthType.WINDOWS_LIVE.name() + " failed!");
					}
					
			    }
					
			} catch (Exception e) {
				LOG.error("Callback With " + OAuthType.WINDOWS_LIVE.name() + " failed!");
			}
		}
		
		// DEFAULT FALLBACK VALUE
		if(!response.isCommitted()){
			response.sendRedirect(urlService.generateUrl(FoUrls.LOGIN, requestUtil.getRequestData(request)));
		}

		return null;
	}
	
	protected void handleAuthenticationData(HttpServletRequest request, HttpServletResponse response, MarketArea currentMarketArea, OAuthType type, String jsonData) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		UserPojo userPojo = null;
		try {
			userPojo = mapper.readValue(jsonData, UserPojo.class);
		} catch (JsonGenerationException e) {
			LOG.error(e.getMessage());
		} catch (JsonMappingException e) {
			LOG.error(e.getMessage());
		}
		if(userPojo != null){
			final String email = userPojo.getEmails().getPreferred();
			final String firstName = userPojo.getFirstName();
			final String lastName = userPojo.getLastName();
			final String gender = userPojo.getGender();
			final String locale = userPojo.getLocale();
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
				if(StringUtils.isNotEmpty(gender)){
					customer.setGender(gender);
				}
				
				customer.setNetworkOrigin(CustomerNetworkOrigin.WINDOWS_LIVE);
				
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
				customer.getCustomerAttributes().add(attribute);
				
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

				response.sendRedirect(urlService.generateUrl(FoUrls.PERSONAL_DETAILS, requestUtil.getRequestData(request)));
			}
			
		}
	}

}